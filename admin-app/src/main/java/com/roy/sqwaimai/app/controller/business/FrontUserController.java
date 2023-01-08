package com.roy.sqwaimai.app.controller.business;

import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.core.entity.vo.front.Rets;
import com.roy.sqwaimai.cache.TokenCache;
import com.roy.sqwaimai.core.entity.FrontUser;
import com.roy.sqwaimai.core.entity.vo.LoginVo;
import com.roy.sqwaimai.core.service.FrontUserService;
import com.roy.sqwaimai.service.system.FileService;
import com.roy.sqwaimai.utils.CaptchaCode;
import com.roy.sqwaimai.core.util.MD5;
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
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class FrontUserController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(FrontUserController.class);
    @Autowired
    private TokenCache tokenCache;
    @Resource
    private FileService fileService;
    @DubboReference
    private FrontUserService frontUserService;

    @RequestMapping(value="/getUserById",method = RequestMethod.GET)
    public Object getUser(@RequestParam("user_id") Long userId) {
        Object result = frontUserService.findUserInfo(userId);
        return Rets.success(result);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@RequestBody LoginVo loginVo) {
        String captchCode = tokenCache.get(loginVo.getCaptchCodeId(), String.class);
        if (!Strings.equals(loginVo.getCaptchaCode(), captchCode)) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_CAPTCHA", "message", "验证码不正确"));
        }
        FrontUser user = frontUserService.findUserByName(loginVo.getUsername());
        if (user == null) {//新用户直接完成注册
            FrontUser frontUser = frontUserService.registerUser(loginVo,getIp());
            Object result = frontUserService.findUserInfo(frontUser.getUser_id());
            setSession("currentUser",result);
            return frontUser;
        } else { //旧用户验证密码是否正确
            String newPassword = MD5.getMD5String(MD5.getMD5String(loginVo.getPassword()).substring(2, 7) + MD5.getMD5String(loginVo.getPassword()));
            if (newPassword.equals(user.getPassword())) {
                Object result = frontUserService.findUserInfo(user.getUser_id());
                setSession("currentUser",result);
                return result;
            } else {
                return Rets.failure(Maps.newHashMap("type", "ERROR_PASSWORD", "message", "密码错误"));
            }

        }
    }

    @RequestMapping(value = "/signout", method = RequestMethod.GET)
    public Object signOut() {
        getRequest().getSession().removeAttribute("currentUser");
        return Rets.success();
    }

    @RequestMapping(value = "/changepassword", method = RequestMethod.POST)
    public Object changePassword(@RequestParam("username") String userName,
                                 @RequestParam("oldpassWord") String oldPassword,
                                 @RequestParam("newpassword") String newPassword,
                                 @RequestParam("confirmpassword") String confirmPassword,
                                 @RequestParam("captcha_code") String captchaCode) {

        String captch = (String) getSession(CaptchaCode.CAPTCH_KEY);
        if (!Strings.equals(captchaCode, captch)) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_CAPTCHA", "message", "验证码不正确"));
        }
        FrontUser user = frontUserService.findUserByName(userName);
        if (user == null) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_QUERY", "message", "用户不存在"));
        }
        if (!Strings.equals(oldPassword, Strings.sNull(user.getPassword()))) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_QUERY", "message", "原密码错误"));
        }
        if (Strings.equals(newPassword, confirmPassword)) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_QUERY", "message", "新密码不一致"));
        }

        user.setPassword(newPassword);
        frontUserService.updateUser(user);
        return Rets.success();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{id}/avatar")
    @ResponseBody
    public Object uploadAvatar(@PathVariable("id") Long userId, @RequestParam("file") MultipartFile file){
        Map<String,Object> result = Maps.newHashMap();
        result.put("status",0);
        String image_path = fileService.saveAvatar(file);
        if(StringUtils.isNotEmpty(image_path)){
            result.put("status",1);
            result.put("image_path",file.getOriginalFilename());
        }
        frontUserService.updateAvatar(userId,file.getOriginalFilename());
        return result;
    }
}
