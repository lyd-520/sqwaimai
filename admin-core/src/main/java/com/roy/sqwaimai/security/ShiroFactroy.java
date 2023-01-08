package com.roy.sqwaimai.security;

import com.roy.sqwaimai.bean.constant.state.ManagerStatus;
import com.roy.sqwaimai.bean.core.ShiroUser;
import com.roy.sqwaimai.bean.entity.system.Role;
import com.roy.sqwaimai.bean.entity.system.User;
import com.roy.sqwaimai.bean.vo.SpringContextHolder;
import com.roy.sqwaimai.cache.TokenCache;
import com.roy.sqwaimai.core.entity.Shop;
import com.roy.sqwaimai.core.util.Constants;
import com.roy.sqwaimai.dao.system.MenuRepository;
import com.roy.sqwaimai.dao.system.RoleRepository;
import com.roy.sqwaimai.dao.system.UserRepository;
import com.roy.sqwaimai.service.system.impl.ConstantFactory;
import com.roy.sqwaimai.utils.Convert;
import com.roy.sqwaimai.utils.HttpKit;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@DependsOn("springContextHolder")
@Transactional(readOnly = true)
public class ShiroFactroy     {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TokenCache tokenCache;

    public static ShiroFactroy me() {
        return SpringContextHolder.getBean(ShiroFactroy.class);
    }


    public User user(String account) {

        User user = userRepository.findByAccount(account);

        // 账号不存在
        if (null == user) {
            throw new CredentialsException();
        }
        // 账号被冻结
        if (user.getStatus() != ManagerStatus.OK.getCode()) {
            throw new LockedAccountException();
        }
        return user;
    }


    public ShiroUser shiroUser(User user) {
        ShiroUser shiroUser = tokenCache.getUser(HttpKit.getToken());
        if(shiroUser!=null){
            return shiroUser;
        }
        shiroUser = new ShiroUser();
        shiroUser.setId(Long.valueOf(user.getId()));            // 账号id
        shiroUser.setAccount(user.getAccount());// 账号
        shiroUser.setDeptId(user.getDeptid());    // 部门id
        shiroUser.setDeptName(ConstantFactory.me().getDeptName(user.getDeptid()));// 部门名称
        shiroUser.setName(user.getName());        // 用户名称
        shiroUser.setPassword(user.getPassword());
        Long[] roleArray = Convert.toLongArray(",", user.getRoleid());
        List<Long> roleList = new ArrayList<>();
        List<String> roleNameList = new ArrayList<>();
        List<String> roleCodeList = new ArrayList<>();
        Set<String> permissions = new HashSet<String>();
        Set<String> resUrls = new HashSet<>();
        for (Long roleId : roleArray) {
            roleList.add(roleId);
            Role role = roleRepository.getOne(roleId);
            roleNameList.add(role.getName());
            roleCodeList.add(role.getTips());
            permissions.addAll(menuRepository.getResCodesByRoleId(roleId));
            resUrls.addAll(menuRepository.getResUrlsByRoleId(roleId));

        }
        shiroUser.setRoleList(roleList);
        shiroUser.setRoleNames(roleNameList);
        shiroUser.setRoleCodes(roleCodeList);
        shiroUser.setPermissions(permissions);
        shiroUser.setUrls(resUrls);
        tokenCache.setUser(HttpKit.getToken(),shiroUser);
        return shiroUser;
    }

    public ShiroUser shiroUser(Shop shop) {
        ShiroUser shiroUser = tokenCache.getUser(HttpKit.getToken());
        if(shiroUser!=null){
            return shiroUser;
        }
        shiroUser = new ShiroUser();
        shiroUser.setId(Long.valueOf(shop.getId()));            // 账号id
        shiroUser.setAccount(shop.getName());// 账号
//        shiroUser.setDeptId(user.getDeptid());    // 部门id
//        shiroUser.setDeptName(ConstantFactory.me().getDeptName(user.getDeptid()));// 部门名称
        shiroUser.setName(shop.getName());        // 用户名称
        shiroUser.setPassword(shop.getPassword());
        Long[] roleArray = new Long[]{Constants.ROLE_ID_SHOP};
        List<Long> roleList = new ArrayList<>();
        List<String> roleNameList = new ArrayList<>();
        List<String> roleCodeList = new ArrayList<>();
        Set<String> permissions = new HashSet<>();
        Set<String> resUrls = new HashSet<>();
        for (Long roleId : roleArray) {
            roleList.add(roleId);
            Role role = roleRepository.getOne(roleId);
            roleNameList.add(role.getName());
            roleCodeList.add(role.getTips());
            permissions.addAll(menuRepository.getResCodesByRoleId(roleId));
            resUrls.addAll(menuRepository.getResUrlsByRoleId(roleId));

        }
        shiroUser.setRoleList(roleList);
        shiroUser.setRoleNames(roleNameList);
        shiroUser.setRoleCodes(roleCodeList);
        shiroUser.setPermissions(permissions);
        shiroUser.setUrls(resUrls);
        tokenCache.setUser(HttpKit.getToken(),shiroUser);
        return shiroUser;
    }

    public SimpleAuthenticationInfo info(ShiroUser shiroUser, User user, String realmName) {
        String credentials = user.getPassword();
        // 密码加盐处理
        String source = user.getSalt();
        ByteSource credentialsSalt = new Md5Hash(source);
        return new SimpleAuthenticationInfo(shiroUser, credentials, credentialsSalt, realmName);
    }

}
