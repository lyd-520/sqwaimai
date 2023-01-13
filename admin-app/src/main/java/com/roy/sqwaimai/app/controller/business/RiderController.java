package com.roy.sqwaimai.app.controller.business;

import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.core.entity.vo.front.Rets;
import com.roy.sqwaimai.cache.TokenCache;
import com.roy.sqwaimai.core.entity.FrontRider;
import com.roy.sqwaimai.core.entity.FrontRiderInfo;
import com.roy.sqwaimai.core.entity.vo.LoginVo;
import com.roy.sqwaimai.core.query.Page;
import com.roy.sqwaimai.core.service.OrderService;
import com.roy.sqwaimai.core.service.RiderService;
import com.roy.sqwaimai.service.system.FileService;
import com.roy.sqwaimai.core.util.CryptUtils;
import com.roy.sqwaimai.utils.Maps;
import com.roy.sqwaimai.utils.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.nutz.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.Map;

@RestController
@RequestMapping("/api/rider")
public class RiderController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(RiderController.class);

    @Autowired
    private TokenCache tokenCache;
    @Resource
    private FileService fileService;
    @DubboReference
    private RiderService riderService;
    @DubboReference
    private OrderService orderService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(LoginVo loginVo) {
        String captchCode = tokenCache.get(loginVo.getCaptchCodeId(), String.class);
        if (!Strings.equals(loginVo.getCaptchaCode(), captchCode)) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_CAPTCHA", "message", "验证码不正确"));
        }
        FrontRider rider = riderService.findRiderByName(loginVo.getUsername());
        if (null == rider) {
            FrontRiderInfo riderInfo = riderService.registerRider(loginVo,getIp());
            setSession("currentRider", riderInfo);
            return riderInfo;
        } else {
            String newPassword = CryptUtils.encodePassword(loginVo.getPassword());
            if (newPassword.equals(rider.getPassword())) {
                FrontRiderInfo riderInfo = riderService.findRiderInfo(rider.getRider_id());
                riderInfo.setWork_status(FrontRiderInfo.STATUS_OFFLINE);
                setSession("currentRider", riderInfo);
                return riderInfo;
            } else {
                return Rets.failure(Maps.newHashMap("type", "ERROR_PASSWORD", "message", "密码错误"));
            }
        }
    }

    @RequestMapping(value = "/signout", method = RequestMethod.GET)
    public Object signOut() {
        getRequest().getSession().removeAttribute("currentRider");
        return Rets.success();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{id}/avatar")
    public Object uploadAvatar(@PathVariable("id") Long userId, @RequestParam("file") MultipartFile file){
        Map<String,Object> result = Maps.newHashMap();
        result.put("status",0);
        if(null != fileService.getByName(file.getOriginalFilename())){
            result.put("status",2);
            return result;
        }
        String image_path = fileService.saveAvatar(file);
        if(StringUtils.isNotEmpty(image_path)){
            result.put("status",1);
            result.put("image_path",file.getOriginalFilename());
        }
        riderService.updateAvatar(userId,file.getOriginalFilename());
        return result;
    }

    @RequestMapping(value = "/changepassword", method = RequestMethod.POST)
    public Object changePassword(@RequestParam("username") String userName,
                                 @RequestParam("oldpassWord") String oldPassword,
                                 @RequestParam("newpassword") String newPassword,
                                 @RequestParam("confirmpassword") String confirmPassword,
                                 @RequestParam("captcha_code") String captchaCode,
                                 @RequestParam("captchaId") String captchaId
    ) {
        String captch = tokenCache.get(captchaId, String.class);
        if (!Strings.equals(captchaCode, captch)) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_CAPTCHA", "message", "验证码不正确"));
        }
        FrontRider rider = riderService.findRiderByName(userName);
        String oldDecodedPassword = CryptUtils.encodePassword(oldPassword);

        if (null == rider) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_QUERY", "message", "用户不存在"));
        }
        if (!Strings.equals(oldDecodedPassword, Strings.sNull(rider.getPassword()))) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_QUERY", "message", "原密码错误"));
        }
        if (!Strings.equals(newPassword, confirmPassword)) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_QUERY", "message", "新密码不一致"));
        }

        String newEncodedPassword = CryptUtils.encodePassword(newPassword);
        rider.setPassword(newEncodedPassword);
        riderService.updateRider(rider);
        return Rets.success();
    }

    @RequestMapping(method = RequestMethod.POST, value = "{riderid}/updatemobile/{mobile}")
    public Object updatemobile(@PathVariable("riderid") Long userId, @PathVariable("mobile") String mobile){
        riderService.updateRiderMobile(userId,mobile);
        return Rets.success("updated");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/online")
    public Object riderOnLine(@RequestParam("riderid") String riderid){
        FrontRiderInfo riderInfo = riderService.riderOnline(riderid);
        return Rets.success(riderInfo);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/offline")
    public Object riderOffLine(@RequestParam("riderid") String riderid){
        FrontRiderInfo riderInfo = riderService.findRiderInfo(Long.parseLong(riderid));
        //有派送中的订单，不允许下线。
        if(riderInfo.getSending_order_id()>0){
            return Maps.newHashMap("error","订单["+riderInfo.getSending_order_id()+"]派送中。请先派送完订单。");
        }else{
            riderInfo.setWork_status(FrontRiderInfo.STATUS_OFFLINE);
            riderService.updateRiderInfo(riderInfo);
            return Rets.success(riderInfo);
        }
    }

    //骑手查询订单
    @RequestMapping(value = "/{user_id}/orders", method = RequestMethod.POST)
    public Object orders(@PathVariable("user_id") Long userId,
                         @RequestParam("limit")  int limit,
                         @RequestParam(value = "latitude",required = false) Double latitude,
                         @RequestParam(value = "longitude",required = false) Double longitude) {

        FrontRiderInfo frontRiderInfo = riderService.findRiderInfo(userId);
        if(null == frontRiderInfo){
            return "骑手信息不存在";
        }else if(FrontRiderInfo.STATUS_ONLINE != frontRiderInfo.getWork_status()){
            return "请先上线";
        }
        //查询完成付款单的订单
        //没有指定位置，就随意查询未付款订单
        if(latitude<=0.00 || longitude <=0.00){
            return Rets.success(orderService.queryPagePaidOrder());
        }else{
            Page<Map> page = orderService.queryNearByOrder(longitude,latitude,limit);
            page.getRecords().sort((o1, o2) -> (int)o1.get("distance") - (int)o2.get("distance"));
            return Rets.success(orderService.queryNearByOrder(longitude,latitude,limit));
        }
    }

//    @RequestMapping(value = "/restaurants", method = RequestMethod.GET)
//    public Object restaurants(@RequestParam(value = "latitude", required = false) String latitude,
//                              @RequestParam(value = "longitude", required = false) String longitude,
//                              @RequestParam(value = "name", required = false) String name,
//                              @RequestParam(value = "restaurant_category_ids", required = false) Long[] categoryIds) {
//
//
//        Map<String, Object> params = Maps.newHashMap();
//        if (StringUtils.isNotEmpty(name)) {
//            params.put("name", name);
//        }
//        params.put("disabled",0);
//
//        if (StringUtils.isEmpty(latitude) || "undefined".equals(latitude)
//                || StringUtils.isEmpty(longitude) || "undefined".equals(longitude)) {
//            Page<Shop> page = new PageFactory<Shop>().defaultPage();
//            return Rets.success(mongoRepository.queryPage(page, Shop.class, params));
//        } else {
//            //查询指定经纬度范围内的餐厅
//            if (categoryIds != null && categoryIds.length > 0) {
//                Map map = (Map) mongoRepository.findOne(categoryIds[0], "categories");
//                if (map != null) {
//                    params.put("category", map.get("name").toString());
//                }
//            }
//            GeoResults<Map> geoResults = mongoRepository.near(Double.valueOf(longitude), Double.valueOf(latitude), "shops", params);
//
//            Page<Map> page = new PageFactory<Map>().defaultPage();
//            if (geoResults != null) {
//                List<GeoResult<Map>> geoResultList = geoResults.getContent();
//                List list = Lists.newArrayList();
//                for (int i = 0; i < geoResultList.size(); i++) {
//                    Map map = geoResultList.get(i).getContent();
//
//                    Distance distance = new Distance(Double.valueOf(longitude), Double.valueOf(latitude),
//                            Double.valueOf(map.get("longitude").toString()), Double.valueOf(map.get("latitude").toString()));
//                    map.put("distance", distance.getDistance().intValue());
//
//                    map.put("order_lead_time", "30分钟");
//                    list.add(map);
//                }
//
//                page.setTotal(list.size());
//                page.setRecords(list);
//            }
//            return Rets.success(page);
//        }
//    }

    @RequestMapping(method = RequestMethod.POST, value = "/clearbalance")
    public Object clearbalance(@RequestParam("userid") String riderid){
        FrontRiderInfo riderInfo = riderService.clearBalance(riderid);
        return Rets.success(riderInfo);
    }
}
