package com.roy.sqwaimai.app.security;

import com.roy.sqwaimai.bean.core.ShiroUser;
import com.roy.sqwaimai.core.entity.Shop;
import com.roy.sqwaimai.core.entity.sys.AccountInfo;
import com.roy.sqwaimai.core.service.ShopService;
import com.roy.sqwaimai.core.util.Constants;
import com.roy.sqwaimai.security.JwtToken;
import com.roy.sqwaimai.security.JwtUtil;
import com.roy.sqwaimai.security.ShiroFactroy;
import com.roy.sqwaimai.service.system.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ApiRealm extends AuthorizingRealm {

    private Logger logger = LogManager.getLogger(getClass());
    @Autowired
    private UserService userService;
    @Autowired
    private ShiroFactroy shiroFactroy;

    @DubboReference
    private ShopService shopService;
    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        AccountInfo accountInfo = JwtUtil.getAccountInfo();
        ShiroUser user = null;
        if (accountInfo != null && !accountInfo.isMgr()) {
//            Shop shop = mongoRepository.findOne(Shop.class, Maps.newHashMap("name", accountInfo.getUsername(), "password", accountInfo.getPassword()));
            Shop shop = shopService.findOneByName( accountInfo.getUsername(),accountInfo.getPassword());
            user = shiroFactroy.shiroUser(shop);
        } else {
            String username = JwtUtil.getUsername(principals.toString());
            user = shiroFactroy.shiroUser(userService.findByAccount(username));
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(user.getRoleCodes());
        Set<String> permission = user.getPermissions();
        simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        AccountInfo accountInfo = JwtUtil.getAccountInfo(token);
        if (accountInfo == null) {
            throw new AuthenticationException("token invalid");
        }
        ShiroUser userBean = null;
        if (Constants.USER_TYPE_MGR.equals(accountInfo.getUserType())) {
            userBean = ShiroFactroy.me().shiroUser(userService.findByAccount(accountInfo.getUsername()));
        } else if (Constants.USER_TYPE_SHOP.equals(accountInfo.getUserType())) {
//            userBean = ShiroFactroy.me().shiroUser(mongoRepository.findOne(Shop.class, Maps.newHashMap("name", accountInfo.getUsername(), "password", accountInfo.getPassword())));
            userBean = ShiroFactroy.me().shiroUser(shopService.findOneByName( accountInfo.getUsername(),accountInfo.getPassword()));
        }
        if (userBean == null) {
            throw new AuthenticationException("User didn't existed!");
        }

        if (!JwtUtil.verify(token, accountInfo.getUsername(), userBean.getPassword())) {
            throw new AuthenticationException("Username or password error");
        }

        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }
}
