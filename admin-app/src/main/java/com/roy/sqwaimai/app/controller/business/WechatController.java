package com.roy.sqwaimai.app.controller.business;

import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.bean.entity.front.FrontUser;
import com.roy.sqwaimai.bean.entity.front.FrontUserInfo;
import com.roy.sqwaimai.bean.entity.front.Ids;
import com.roy.sqwaimai.bean.vo.business.CityInfo;
import com.roy.sqwaimai.bean.vo.front.Rets;
import com.roy.sqwaimai.bean.vo.wechat.MiniProgramUserInfo;
import com.roy.sqwaimai.cache.TokenCache;
import com.roy.sqwaimai.dao.MongoRepository;
import com.roy.sqwaimai.service.front.IdsService;
import com.roy.sqwaimai.service.front.PositionService;
import com.roy.sqwaimai.service.wechat.WechatService;
import com.roy.sqwaimai.utils.DateUtil;
import com.roy.sqwaimai.utils.MD5;
import com.roy.sqwaimai.utils.Maps;
import com.roy.sqwaimai.utils.StringUtils;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.mapl.Mapl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * 微信相关服务
 */
@RestController
@RequestMapping("/wechat")
public class WechatController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(WechatController.class);
    @Autowired
    private WechatService wechatService;
    @Autowired
    private MongoRepository mongoRepository;
    @Autowired
    private IdsService idsService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private TokenCache tokenCache;

    /**
     * 小程序登录
     * @param code
     * @return
     */
    @GetMapping("/code2Session")
    public Object code2Session(@RequestParam("code") String code) {
        MiniProgramUserInfo ret = wechatService.code2Session(code);
        if (ret != null &&  ret.getErrcode()==0) {
            logger.info("wechat miniapp user info:{}",Json.toJson(ret));
            Map params = Maps.newHashMap("miniappOpenid", ret.getOpenid());
            FrontUser frontUser = mongoRepository.findOne(FrontUser.class, params);
            FrontUserInfo userInfo = null;
            if (frontUser == null) {
                frontUser = new FrontUser();
                frontUser.setMiniappOpenid(ret.getOpenid());
                frontUser.setUser_id(idsService.getId(Ids.USER_ID));
                frontUser.setUsername("wxuser_"+ DateUtil.getAllTime());

                mongoRepository.save(frontUser);
                 userInfo = new FrontUserInfo();
                userInfo.setId(frontUser.getUser_id());
                userInfo.setUser_id(frontUser.getUser_id());
                userInfo.setRegiste_time(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));
                String ip = getIp();
                if(StringUtils.isNotEmpty(ip)) {
                    CityInfo cityInfo = positionService.getPostion(ip);
                    userInfo.setCity(cityInfo.getCity());
                }
                mongoRepository.save(userInfo);
                Object result = Mapl.merge(frontUser, userInfo);
                setSession("currentUser", result);
            }else{
                frontUser.setMiniappOpenid(ret.getOpenid());
                mongoRepository.update(frontUser);
                userInfo = mongoRepository.findOne(FrontUserInfo.class, Maps.newHashMap("user_id",frontUser.getUser_id()));

            }
            Object result =  Mapl.merge(frontUser, userInfo);
            logger.info("userInfo:{}",Json.toJson(result));
            String token = MD5.getMD5String(Json.toJson(result, JsonFormat.compact()));
            setSession("currentUser", result);
            logger.info("token:{}",token);
            tokenCache.put(token,frontUser.getUser_id());
            Map responseMap = (Map) Mapl.toMaplist(ret);
            responseMap.put("token",token);
            responseMap.put("userName",frontUser.getUsername());
            responseMap.put("user_id",frontUser.getUser_id());
            return Rets.success(responseMap);
        }
        return Rets.failure(ret.getErrmsg());

    }
}
