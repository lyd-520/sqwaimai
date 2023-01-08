package com.roy.sqwaimai.app.controller.system;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.bean.constant.Const;
import com.roy.sqwaimai.bean.core.BussinessLog;
import com.roy.sqwaimai.bean.dictmap.RoleDict;
import com.roy.sqwaimai.bean.entity.system.Role;
import com.roy.sqwaimai.bean.entity.system.User;
import com.roy.sqwaimai.bean.enumeration.BizExceptionEnum;
import com.roy.sqwaimai.bean.enumeration.Permission;
import com.roy.sqwaimai.bean.exception.ApplicationException;
import com.roy.sqwaimai.core.entity.vo.front.Rets;
import com.roy.sqwaimai.bean.vo.node.Node;
import com.roy.sqwaimai.bean.vo.node.ZTreeNode;
import com.roy.sqwaimai.service.system.LogObjectHolder;
import com.roy.sqwaimai.service.system.RoleService;
import com.roy.sqwaimai.service.system.UserService;
import com.roy.sqwaimai.service.system.impl.ConstantFactory;
import com.roy.sqwaimai.utils.BeanUtil;
import com.roy.sqwaimai.utils.Convert;
import com.roy.sqwaimai.utils.Maps;
import com.roy.sqwaimai.utils.ToolUtil;
import com.roy.sqwaimai.warpper.RoleWarpper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @RequiresPermissions(value = {Permission.ROLE})
    public Object list(String name){
        List roles = null;
        if(Strings.isNullOrEmpty(name)) {
            roles =  roleService.queryAll();
        }else{
            roles = roleService.findByName(name);
        }
        return Rets.success(new RoleWarpper(BeanUtil.objectsToMaps(roles)).warp());
    }

    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "编辑角色", key = "name", dict = RoleDict.class)
    @RequiresPermissions(value = {Permission.ROLE_EDIT})
    public Object save(@Valid Role role){
        if(role.getId()==null){
            roleService.insert(role);
        }else {
            roleService.update(role);
        }
        return Rets.success();
    }
    @RequestMapping(method = RequestMethod.DELETE)
    @BussinessLog(value = "删除角色", key = "roleId", dict = RoleDict.class)
    @RequiresPermissions(value = {Permission.ROLE_DEL})
    public Object remove(@RequestParam Long roleId){
        logger.info("id:{}",roleId);
        if (ToolUtil.isEmpty(roleId)) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
        if(roleId.intValue()<2){
            return Rets.failure("不能删除初始角色");
        }

        //不能删除超级管理员角色
        if(roleId.equals(Const.ADMIN_ROLE_ID)){
            throw new ApplicationException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }
        //缓存被删除的角色名称
        LogObjectHolder.me().set(ConstantFactory.me().getSingleRoleName(roleId));
        roleService.delRoleById(roleId);
        return Rets.success();
    }

    @RequestMapping(value = "/savePermisson",method = RequestMethod.POST)
    @BussinessLog(value = "配置角色权限", key = "roleId", dict = RoleDict.class)
    @RequiresPermissions(value = {Permission.ROLE_EDIT})
    public Object setAuthority(Long roleId, String
            permissions) {
        if (ToolUtil.isOneEmpty(roleId)) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
        roleService.setAuthority(roleId, permissions);
        return Rets.success();
    }


    /**
     * 获取角色树
     */
    @RequestMapping(value = "/roleTreeListByIdUser", method = RequestMethod.GET)
    @RequiresPermissions(value = {Permission.ROLE})
    public Object roleTreeListByIdUser(Long idUser) {
        User user = userService.get(idUser);
        String roleIds = user.getRoleid();
        List<ZTreeNode> roleTreeList = null;
        if (ToolUtil.isEmpty(roleIds)) {
            roleTreeList = roleService.roleTreeList();
        } else {
            Long[] roleArray = Convert.toLongArray(",", roleIds);
            roleTreeList = roleService.roleTreeListByRoleId(roleArray);

        }
        List<Node> list = roleService.generateRoleTree(roleTreeList);
        List<Long> checkedIds = Lists.newArrayList();
        for (ZTreeNode zTreeNode : roleTreeList) {
            if (zTreeNode.getChecked() != null && zTreeNode.getChecked()) {
                checkedIds.add(zTreeNode.getId());
            }
        }
        return Rets.success(Maps.newHashMap("treeData", list, "checkedIds", checkedIds));
    }
}
