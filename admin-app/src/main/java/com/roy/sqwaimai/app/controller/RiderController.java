package com.roy.sqwaimai.app.controller;

import com.roy.sqwaimai.bean.constant.factory.PageFactory;
import com.roy.sqwaimai.bean.entity.front.*;
import com.roy.sqwaimai.bean.entity.front.sub.OrderFee;
import com.roy.sqwaimai.bean.vo.business.CityInfo;
import com.roy.sqwaimai.bean.vo.business.LoginVo;
import com.roy.sqwaimai.bean.vo.front.Rets;
import com.roy.sqwaimai.cache.TokenCache;
import com.roy.sqwaimai.dao.MongoRepository;
import com.roy.sqwaimai.service.front.IdsService;
import com.roy.sqwaimai.service.front.PositionService;
import com.roy.sqwaimai.service.system.CfgService;
import com.roy.sqwaimai.service.system.FileService;
import com.roy.sqwaimai.utils.*;
import com.roy.sqwaimai.utils.factory.Page;
import com.roy.sqwaimai.utils.gps.Distance;
import org.nutz.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rider")
public class RiderController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(RiderController.class);

    @Resource
    private MongoRepository mongoRepository;
    @Autowired
    private IdsService idsService;
    @Autowired
    private TokenCache tokenCache;
    @Autowired
    private PositionService positionService;
    @Resource
    private FileService fileService;
    @Resource
    private CfgService cfgService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public Object login(@RequestBody LoginVo loginVo) {
//    public Object login(@RequestParam("username") String username,@RequestParam("password") String password) {
    public Object login(LoginVo loginVo) {
//        LoginVo loginVo = new LoginVo();
        String captchCode = tokenCache.get(loginVo.getCaptchCodeId(), String.class);
        if (!Strings.equals(loginVo.getCaptchaCode(), captchCode)) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_CAPTCHA", "message", "验证码不正确"));
        }
        Map rider = mongoRepository.findOne("riders", "rider_name", loginVo.getUsername());
//        String newPassword = MD5.getMD5String(MD5.getMD5String(loginVo.getPassword()).substring(2, 7) + MD5.getMD5String(loginVo.getPassword()));
        String newPassword = CryptUtils.encodePassword(loginVo.getPassword());
        if (rider == null) {
            FrontRider frontRider = new FrontRider();
            frontRider.setRider_id(idsService.getId(Ids.RIDER_ID));
            frontRider.setRider_name(loginVo.getUsername());
            frontRider.setPassword(newPassword);
            mongoRepository.save(frontRider);

            FrontRiderInfo riderInfo = new FrontRiderInfo();
            riderInfo.setRider_id(frontRider.getRider_id());
            riderInfo.setId(frontRider.getRider_id());
            riderInfo.setRegiste_time(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));
            riderInfo.setBalance(0.00);
            riderInfo.setOrder_count(0);
            riderInfo.setBalance_amount(0.00);
            String ip = getIp();
            CityInfo cityInfo = positionService.getPostion(ip);
            riderInfo.setCity(cityInfo.getCity());
            riderInfo.setRider_name(frontRider.getRider_name());

            mongoRepository.save(riderInfo);
            setSession("currentRider", riderInfo);
            return riderInfo;
        } else {
            if (newPassword.equals(rider.get("password"))) {
                Map riderInfo = mongoRepository.findOne("ridersinfo", "rider_id", Long.valueOf(rider.get("rider_id").toString()));
                setSession("currentRider", riderInfo);
                return riderInfo;
            } else {
                return Rets.failure(Maps.newHashMap("type", "ERROR_PASSWORD", "message", "密码错误"));
            }
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{id}/avatar")
    public Object uploadAvatar(@PathVariable("id") Long userId, @RequestParam("file") MultipartFile file){
        Map<String,Object> result = Maps.newHashMap();
        result.put("status",0);
        if(null != fileService.getByName(file.getOriginalFilename())){
            result.put("status",2);
            return result;
        }
        String image_path = fileService.saveAvatar(userId,file);
        if(StringUtils.isNotEmpty(image_path)){
            result.put("status",1);
            result.put("image_path",file.getOriginalFilename());
        }
        Map riderInfo = mongoRepository.findOne("ridersinfo", "rider_id", userId);
        riderInfo.put("avatar",file.getOriginalFilename());
        mongoRepository.update(Long.valueOf(riderInfo.get("rider_id").toString()), "ridersinfo", riderInfo);
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
        Map user = mongoRepository.findOne("riders", "rider_name", userName);
//        String oldDecodedPassword = MD5.getMD5String(MD5.getMD5String(oldPassword).substring(2, 7) + MD5.getMD5String(oldPassword));
        String oldDecodedPassword = CryptUtils.encodePassword(oldPassword);

        if (user == null) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_QUERY", "message", "用户不存在"));
        }
        if (!Strings.equals(oldDecodedPassword, Strings.sNull(user.get("password")))) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_QUERY", "message", "原密码错误"));
        }
        if (!Strings.equals(newPassword, confirmPassword)) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_QUERY", "message", "新密码不一致"));
        }

        String newEncodedPassword = CryptUtils.encodePassword(newPassword);
        user.put("password", newEncodedPassword);
        mongoRepository.update(Long.valueOf(user.get("rider_id").toString()), "riders", user);

        return Rets.success();
    }

    @RequestMapping(method = RequestMethod.POST, value = "{riderid}/updatemobile/{mobile}")
    public Object uploadAvatar(@PathVariable("riderid") Long userId, @PathVariable("mobile") String mobile){
        Map riderInfo = mongoRepository.findOne("ridersinfo", "rider_id", userId);
        riderInfo.put("mobile",mobile);
        mongoRepository.update(Long.valueOf(riderInfo.get("rider_id").toString()), "ridersinfo", riderInfo);
        return Rets.success("updated");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/online")
    public Object riderOnLine(@RequestParam("riderid") String riderid){
        FrontRiderInfo riderInfo = mongoRepository.findOne(FrontRiderInfo.class, "rider_id", Long.parseLong(riderid));
        //修改骑手状态
        riderInfo.setWork_status(FrontRiderInfo.STATUS_ONLINE);
        mongoRepository.update(riderInfo);
        return riderInfo;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/offline")
    public Object riderOffLine(@RequestParam("riderid") String riderid){
        FrontRiderInfo riderInfo = mongoRepository.findOne(FrontRiderInfo.class, "rider_id", Long.parseLong(riderid));
        //有派送中的订单，不允许下线。
        if(riderInfo.getSending_order_id()>0){
            return Maps.newHashMap("error","订单["+riderInfo.getSending_order_id()+"]派送中。请先派送完订单。");
        }else{
            riderInfo.setWork_status(FrontRiderInfo.STATUS_OFFLINE);
            mongoRepository.update(riderInfo);
        }
        return riderInfo;
    }

    /**
     *
     * @param userId
     * @param params
     * @return 返回Page对象，将订单信息渲染到页面。
     * 返回的对象不包含records属性，则前端页面不显示订单信息。
     */
    @RequestMapping(value = "/{user_id}/orders", method = RequestMethod.POST)
    public Object orders(@PathVariable("user_id") Long userId,
                         @RequestParam("limit")  int limit,
                         @RequestParam(value = "latitude",required = false) Double latitude,
                         @RequestParam(value = "longitude",required = false) Double longitude) {

        FrontRiderInfo frontRiderInfo = mongoRepository.findOne(FrontRiderInfo.class, "rider_id", userId);
        if(null == frontRiderInfo){
            return "骑手信息不存在";
        }else if(FrontRiderInfo.STATUS_ONLINE != frontRiderInfo.getWork_status()){
            return "请先上线";
        }
        
        //查询完成付款单的订单
        //没有指定位置，就随意查询未付款订单
        if(latitude<=0.00 || longitude <=0.00){
            Map<String,Object> orderqueryParam = Maps.newHashMap();
            orderqueryParam.put("status_code",Order.STATUS_PAID);
            Page<Order> page = new PageFactory<Order>().defaultPage();
            return Rets.success(mongoRepository.queryPage(page, Order.class, orderqueryParam));
        }else{
            Page page = new PageFactory().rawPage();
            List<Map> queryOrders = Lists.newArrayList();
            //传了指定位置，先找附近的商店，作为订单的起点
            // 查找附近的商店
            String cfgrange = cfgService.getCfgValue("system.search.range");
            GeoResults<Map> geoResults = mongoRepository.near(Double.valueOf(longitude), Double.valueOf(latitude), "shops", Maps.newHashMap(),Integer.parseInt(cfgrange));
            //从这些商店中找出十条订单
            if (geoResults != null) {
                List<GeoResult<Map>> geoResultList = geoResults.getContent();
                for (int i = 0; i < geoResultList.size(); i++) {

                    Map shop = geoResultList.get(i).getContent();
                    Distance distance = new Distance(Double.valueOf(longitude), Double.valueOf(latitude),
                            Double.valueOf(shop.get("longitude").toString()), Double.valueOf(shop.get("latitude").toString()));
                    List<Map> orders = mongoRepository.findAll("orders", "status_code",Order.STATUS_PAID,"restaurant_id",shop.get("id"));
                    for (Map order : orders) {
                        order.put("distance", distance.getDistance().intValue());
                    }
                    queryOrders.addAll(orders);
                    //最多只展示10条订单
                    if(queryOrders.size()>=limit){
                        break;
                    }
                }
            }
            page.setTotal(queryOrders.size());
            page.setRecords(queryOrders);
            return Rets.success(page);
        }
    }

    @RequestMapping(value = "restaurants", method = RequestMethod.GET)
    public Object restaurants(@RequestParam(value = "latitude", required = false) String latitude,
                              @RequestParam(value = "longitude", required = false) String longitude,
                              @RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "restaurant_category_ids", required = false) Long[] categoryIds) {


        Map<String, Object> params = Maps.newHashMap();
        if (StringUtils.isNotEmpty(name)) {
            params.put("name", name);
        }
        params.put("disabled",0);

        if (StringUtils.isEmpty(latitude) || "undefined".equals(latitude)
                || StringUtils.isEmpty(longitude) || "undefined".equals(longitude)) {
            Page<Shop> page = new PageFactory<Shop>().defaultPage();
            return Rets.success(mongoRepository.queryPage(page, Shop.class, params));
        } else {
            //查询指定经纬度范围内的餐厅
            if (categoryIds != null && categoryIds.length > 0) {
                Map map = (Map) mongoRepository.findOne(categoryIds[0], "categories");
                if (map != null) {
                    params.put("category", map.get("name").toString());
                }
            }
            GeoResults<Map> geoResults = mongoRepository.near(Double.valueOf(longitude), Double.valueOf(latitude), "shops", params);

            Page<Map> page = new PageFactory<Map>().defaultPage();
            if (geoResults != null) {
                List<GeoResult<Map>> geoResultList = geoResults.getContent();
                List list = Lists.newArrayList();
                for (int i = 0; i < geoResultList.size(); i++) {
                    Map map = geoResultList.get(i).getContent();

                    Distance distance = new Distance(Double.valueOf(longitude), Double.valueOf(latitude),
                            Double.valueOf(map.get("longitude").toString()), Double.valueOf(map.get("latitude").toString()));
                    map.put("distance", distance.getDistance().intValue());

                    map.put("order_lead_time", "30分钟");
                    list.add(map);
                }

                page.setTotal(list.size());
                page.setRecords(list);
            }
            return Rets.success(page);
        }
    }
    //骑手抢单。并发设计，防止多个骑手抢同一个单
    private Map<Long,Object> orderLockCache = Maps.newHashMap();
    private Object cacheLock = new Object();

    @RequestMapping(value = "checkOrder", method = RequestMethod.POST)
    public Object checkOrder(@RequestParam("userid") long userid,
                             @RequestParam("orderid") long orderid){
        //在rider表中记录骑手的订单ID。 要防止同一个订单ID被多个骑手注册
        //同步锁，防止高并发时，后面的线程覆盖前面线程的锁对象。
        synchronized (cacheLock){
            if(!orderLockCache.containsKey(orderid)){
                orderLockCache.put(orderid,new Object());
            }
        }
        //只锁订单ID对应的锁对象，防止针对同一个订单的竞争。不同订单不需要竞争。
        synchronized (orderLockCache.get(orderid)){
            Order order = mongoRepository.findOne(Order.class, "id", orderid);
            FrontRiderInfo frontRiderInfo = mongoRepository.findOne(FrontRiderInfo.class, "rider_id", userid);
            if(order.getStatus_code() == Order.STATUS_DELIVERYING){
                return Maps.newHashMap("error","当前订单已由其他骑手派送。请刷新后重新抢单。");
            }else if(frontRiderInfo.getSending_order_id()>0){
                return Maps.newHashMap("error","当前派单中。请先派送完编号为["+frontRiderInfo.getSending_order_id()+"]的订单");
            }else{
                frontRiderInfo.setSending_order_id(orderid);
                frontRiderInfo.setWork_status(FrontRiderInfo.STATUS_SENDING);
                mongoRepository.update(frontRiderInfo);
                order.setStatus_code(Order.STATUS_DELIVERYING);
                order.setStatus_title(Order.getStatusCodeStr(Order.STATUS_DELIVERYING));
                mongoRepository.update(order);
                return Rets.success(frontRiderInfo);
            }
        }
    }
    //订单送达
    @RequestMapping(value= "/sendorder",method = RequestMethod.POST)
    public Object sendOrder(@RequestParam("userid") long userid,
                            @RequestParam("orderid") long orderid){
        //更新订单  订单状态，送达时间
        Order order = mongoRepository.findOne(Order.class, "id", orderid);
        order.setStatus_code(Order.STATUS_DELIVERED);
        order.setStatus_title(Order.getStatusCodeStr(Order.STATUS_DELIVERED));
        Date now = new Date();
        order.setDeliver_time(now.getTime());
        order.setFormatted_deliver_at(DateUtil.format(now, "yyyy-MM-dd HH:mm"));
        mongoRepository.update(order);
        //更新骑手 更新派送订单 更新结算
        FrontRiderInfo frontRiderInfo = mongoRepository.findOne(FrontRiderInfo.class, "id", userid);
        frontRiderInfo.setSending_order_id(-1L);
        frontRiderInfo.setWork_status(FrontRiderInfo.STATUS_OFFLINE);
        frontRiderInfo.setOrder_count(frontRiderInfo.getOrder_count()+1);

        Double deliverFee = order.getBasket().getDeliver_fee().getPrice();
        frontRiderInfo.setBalance(frontRiderInfo.getBalance()+deliverFee);
        frontRiderInfo.setBalance_amount(frontRiderInfo.getBalance_amount()+deliverFee);
        mongoRepository.update(frontRiderInfo);

        return Rets.success(frontRiderInfo);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/clearbalance")
    public Object clearbalance(@RequestParam("userid") String riderid){
        FrontRiderInfo riderInfo = mongoRepository.findOne(FrontRiderInfo.class, "rider_id", Long.parseLong(riderid));
        riderInfo.setBalance(0.00);
        mongoRepository.update(riderInfo);
        return Rets.success(riderInfo);
    }
}
