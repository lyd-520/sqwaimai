package com.roy.sqwaimai.service.system.impl;

import com.roy.sqwaimai.bean.constant.cache.CacheKey;
import com.roy.sqwaimai.bean.constant.state.ManagerStatus;
import com.roy.sqwaimai.bean.constant.state.MenuStatus;
import com.roy.sqwaimai.bean.entity.system.*;
import com.roy.sqwaimai.bean.vo.SpringContextHolder;
import com.roy.sqwaimai.dao.system.*;
import com.roy.sqwaimai.service.system.IConstantFactory;
import com.roy.sqwaimai.utils.Convert;
import com.roy.sqwaimai.utils.StringUtils;
import com.roy.sqwaimai.utils.cache.TimeCacheMap;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
/**
 * 常量的生产工厂
 */
@Component
@DependsOn("springContextHolder")
@CacheConfig
public class ConstantFactory implements IConstantFactory {
    public static TimeCacheMap<String, String> cache = new TimeCacheMap<String, String>(3600, 2);
    private RoleRepository roleRepository = SpringContextHolder.getBean(RoleRepository.class);
    private DeptRepository deptRepository = SpringContextHolder.getBean(DeptRepository.class);
    private DictRepository dictRepository = SpringContextHolder.getBean(DictRepository.class);
    private UserRepository userRepository = SpringContextHolder.getBean(UserRepository.class);
    private MenuRepository menuRepository = SpringContextHolder.getBean(MenuRepository.class);
    private NoticeRepository sysNoticeRepository = SpringContextHolder.getBean(NoticeRepository.class);

    public static IConstantFactory me() {
        return SpringContextHolder.getBean("constantFactory");
    }

    public String get(String key) {
        return cache.get(key);
    }

    public void set(String key, String val) {
        cache.put(key, val);

    }

    /**
     * 根据用户id获取用户名称
     */
    @Override
    public String getUserNameById(Long userId) {
        String val = get(CacheKey.SYS_USER_NAME + userId);
        if (StringUtils.isNotEmpty(val)) {
            return val;
        }

        User user = getUser(userId);
        if (user != null) {
            val = user.getName();
            set(CacheKey.SYS_USER_NAME + userId, val);
            return val;
        }
        return "--";
    }

    private User getUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user;
        }
        return null;
    }

    /**
     * 通过角色ids获取角色名称
     */
    @Override
    public String getRoleName(String roleIds) {
        String val = get(CacheKey.ROLES_NAME + roleIds);
        if (StringUtils.isNotEmpty(val)) {
            return val;
        }
        Integer[] roles = Convert.toIntArray(roleIds);
        StringBuilder sb = new StringBuilder();
        for (Integer role : roles) {
            Role roleObj = getRole(Long.valueOf(role));
            if (StringUtils.isNotNullOrEmpty(roleObj) && StringUtils.isNotEmpty(roleObj.getName())) {
                sb.append(roleObj.getName()).append(",");
            }
        }
        val = StringUtils.removeSuffix(sb.toString(), ",");
        set(CacheKey.ROLES_NAME + roleIds, val);
        return val;
    }

    /**
     * 通过角色id获取角色名称
     */
    @Override
    public String getSingleRoleName(Long roleId) {
        if (0 == roleId) {
            return "--";
        }
        Role roleObj = getRole(roleId);
        if (StringUtils.isNotNullOrEmpty(roleObj) && StringUtils.isNotEmpty(roleObj.getName())) {
            return roleObj.getName();
        }
        return "";
    }

    /**
     * 获取部门名称
     */
    @Override
    public String getDeptName(Long deptId) {
        if (deptId == null) {
            return null;
        }
        String val = get(CacheKey.DEPT_NAME + deptId);
        if (StringUtils.isNotEmpty(val)) {
            return val;
        }
        Dept dept = getDept(deptId);
        if (StringUtils.isNotNullOrEmpty(dept) && StringUtils.isNotEmpty(dept.getFullname())) {
            val = dept.getFullname();
            set(CacheKey.DEPT_NAME + deptId, val);
            return val;
        }
        return "";
    }

    /**
     * 获取菜单名称
     */
    @Override
    public String getMenuName(Long menuId) {

        Menu menu = getMenu(menuId);
        if (menu == null) {
            return "";
        } else {
            return menu.getName();
        }
    }

    /**
     * 获取菜单名称通过编号
     */
    @Override
    public String getMenuNameByCode(String code) {

        Menu menu = menuRepository.findByCode(code);
        if (menu == null) {
            return "";
        } else {
            return menu.getName();
        }
    }

    /**
     * 获取用户登录状态
     */
    @Override
    public String getStatusName(Integer status) {
        return ManagerStatus.valueOf(status);
    }

    /**
     * 获取菜单状态
     */
    @Override
    public String getMenuStatusName(Integer status) {
        return MenuStatus.valueOf(status);
    }

    /**
     * 查询字典
     */
    @Override
    public List<Dict> findInDict(Long id) {
        return dictRepository.findByPid(id);

    }

    @Override
    public Role getRole(Long id) {
        Optional<Role> optional = roleRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }
    @Override
    public Dept getDept(Long id) {
        Optional<Dept> optional = deptRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }
    @Override
    public Menu getMenu(Long id) {
        Optional<Menu> optiona = menuRepository.findById(id);
        if (optiona.isPresent()) {
            return optiona.get();
        }
        return null;
    }
    @Override
    public Notice getNotice(Long id) {
        Optional<Notice> optional = sysNoticeRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

}
