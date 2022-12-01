package com.roy.sqwaimai.app.controller.business;

import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.bean.entity.front.FrontUser;
import com.roy.sqwaimai.bean.entity.front.FrontUserInfo;
import com.roy.sqwaimai.bean.entity.front.Ids;
import com.roy.sqwaimai.bean.vo.SpringContextHolder;
import com.roy.sqwaimai.bean.vo.business.CityInfo;
import com.roy.sqwaimai.bean.vo.business.LoginVo;
import com.roy.sqwaimai.bean.vo.front.Rets;
import com.roy.sqwaimai.cache.TokenCache;
import com.roy.sqwaimai.dao.MongoRepository;
import com.roy.sqwaimai.service.front.IdsService;
import com.roy.sqwaimai.service.front.PositionService;
import com.roy.sqwaimai.service.system.FileService;
import com.roy.sqwaimai.utils.*;
import org.nutz.lang.Strings;
import org.nutz.mapl.Mapl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/users")
public class User2Controller extends BaseController {
    private Logger logger = LoggerFactory.getLogger(User2Controller.class);
    @Autowired
    private MongoRepository mongoRepository;
    @Autowired
    private IdsService idsService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private TokenCache tokenCache;
    @Resource
    private FileService fileService;

    @RequestMapping(value = "/v1/user", method = RequestMethod.GET)
    public Object getUser() {
        Object ret = getSession("currentUser");
        if (ret == null) {
            String token = getRequest().getParameter("token");
            logger.info("token:{}", token);
            if (StringUtils.isNotEmpty(token)) {
                Long userId = SpringContextHolder.getBean(TokenCache.class).get(token, Long.class);
                logger.info("userId:{}", userId);
                FrontUser frontUser = mongoRepository.findOne(FrontUser.class, "user_id", userId);
                FrontUserInfo userInfo = mongoRepository.findOne(FrontUserInfo.class, Maps.newHashMap("user_id", userId));
                return Mapl.merge(frontUser, userInfo);
            }
        }
        logger.error("获取用户信息失败");
        return null;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Object getUser(@RequestParam("user_id") Long userId) {
        Map user = mongoRepository.findOne("users", "user_id", userId);
        Map userInfo = mongoRepository.findOne("userinfos", "user_id", Long.valueOf(user.get("user_id").toString()));
        Object result = Mapl.merge(user, userInfo);
        return Rets.success(result);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return mongoRepository.findAll("userinfos");
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Object count() {
        return Rets.success("count", 2);
    }

    @RequestMapping(value = "/v2/login", method = RequestMethod.POST)
    public Object login(@RequestBody LoginVo loginVo) {
        String captchCode = tokenCache.get(loginVo.getCaptchCodeId(), String.class);
        if (!Strings.equals(loginVo.getCaptchaCode(), captchCode)) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_CAPTCHA", "message", "验证码不正确"));
        }
        Map user = mongoRepository.findOne("users", "username", loginVo.getUsername());
        String newPassword = MD5.getMD5String(MD5.getMD5String(loginVo.getPassword()).substring(2, 7) + MD5.getMD5String(loginVo.getPassword()));
        if (user == null) {
            FrontUser frontUser = new FrontUser();
            frontUser.setUser_id(idsService.getId(Ids.USER_ID));
            frontUser.setUsername(loginVo.getUsername());
            frontUser.setPassword(newPassword);
            mongoRepository.save(frontUser);
            FrontUserInfo userInfo = new FrontUserInfo();
            userInfo.setId(frontUser.getUser_id());
            userInfo.setUser_id(frontUser.getUser_id());
            userInfo.setRegiste_time(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));
            String ip = getIp();
            CityInfo cityInfo = positionService.getPostion(ip);
            userInfo.setCity(cityInfo.getCity());
            userInfo.setUsername(frontUser.getUsername());
            mongoRepository.save(userInfo);
            Object result = Mapl.merge(frontUser, userInfo);
            setSession("currentUser", result);
            return frontUser;
        } else {
            if (newPassword.equals(user.get("password"))) {
                Map userInfo = mongoRepository.findOne("userinfos", "user_id", Long.valueOf(user.get("user_id").toString()));
                Object result = Mapl.merge(user, userInfo);
                setSession("currentUser", result);
                return result;
            } else {
                return Rets.failure(Maps.newHashMap("type", "ERROR_PASSWORD", "message", "密码错误"));
            }

        }


    }

    @RequestMapping(value = "/v2/signout", method = RequestMethod.GET)
    public Object signOut() {
        getRequest().getSession().removeAttribute("currentUser");
        return Rets.success();
    }

    @RequestMapping(value = "/v2/changepassword", method = RequestMethod.POST)
    public Object changePassword(@RequestParam("username") String userName,
                                 @RequestParam("oldpassWord") String oldPassword,
                                 @RequestParam("newpassword") String newPassword,
                                 @RequestParam("confirmpassword") String confirmPassword,
                                 @RequestParam("captcha_code") String captchaCode) {

        String captch = (String) getSession(CaptchaCode.CAPTCH_KEY);
        if (!Strings.equals(captchaCode, captch)) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_CAPTCHA", "message", "验证码不正确"));
        }
        Map user = mongoRepository.findOne("users", "username", userName);
        if (user == null) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_QUERY", "message", "用户不存在"));
        }
        if (!Strings.equals(oldPassword, Strings.sNull(user.get("password")))) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_QUERY", "message", "原密码错误"));
        }
        if (Strings.equals(newPassword, confirmPassword)) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_QUERY", "message", "新密码不一致"));
        }

        user.put("password", newPassword);
        mongoRepository.update(Long.valueOf(user.get("id").toString()), "users", user);

        return Rets.success();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{id}/avatar")
    @ResponseBody
    public Object uploadAvatar(@PathVariable("id") Long userId, @RequestParam("file") MultipartFile file){
        Map<String,Object> result = Maps.newHashMap();
        result.put("status",0);
        String image_path = fileService.saveAvatar(userId,file);
        if(StringUtils.isNotEmpty(image_path)){
            result.put("status",1);
            result.put("image_path",file.getOriginalFilename());
        }
        Map userInfo = mongoRepository.findOne("userinfos", "user_id", userId);
        userInfo.put("avatar",file.getOriginalFilename());
        mongoRepository.update(Long.valueOf(userInfo.get("id").toString()), "userinfos", userInfo);
        return result;
    }
}
