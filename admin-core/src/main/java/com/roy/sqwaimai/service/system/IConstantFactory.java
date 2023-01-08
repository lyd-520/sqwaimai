package com.roy.sqwaimai.service.system;

import com.roy.sqwaimai.bean.entity.system.*;

import java.util.List;

/**
 * 常量生产工厂的接口
 */
public interface IConstantFactory {

    /**
     * 根据用户id获取用户名称
     */
    String getUserNameById(Long userId);

    /**
     * 通过角色ids获取角色名称
     */
    String getRoleName(String roleIds);

    /**
     * 通过角色id获取角色名称
     */
    String getSingleRoleName(Long roleId);

    /**
     * 获取部门名称
     */
    String getDeptName(Long deptId);

    /**
     * 获取菜单名称
     */
    String getMenuName(Long menuId);

    /**
     * 获取菜单名称通过编号
     */
    String getMenuNameByCode(String code);

    /**
     * 获取用户登录状态
     */
    String getStatusName(Integer status);

    /**
     * 获取菜单状态
     */
    String getMenuStatusName(Integer status);

    /**
     * 查询字典
     */
    List<Dict> findInDict(Long id);

    Role getRole(Long id) ;
    Dept getDept(Long id);
    Menu getMenu(Long id) ;

    Notice getNotice(Long id);
}
