package com.roy.sqwaimai.app.controller.system;

import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.bean.constant.Const;
import com.roy.sqwaimai.bean.constant.state.ManagerStatus;
import com.roy.sqwaimai.bean.core.BussinessLog;
import com.roy.sqwaimai.bean.dictmap.UserDict;
import com.roy.sqwaimai.bean.dto.UserDto;
import com.roy.sqwaimai.bean.entity.system.User;
import com.roy.sqwaimai.bean.enumeration.BizExceptionEnum;
import com.roy.sqwaimai.bean.enumeration.Permission;
import com.roy.sqwaimai.bean.exception.ApplicationException;
import com.roy.sqwaimai.core.entity.vo.front.Rets;
import com.roy.sqwaimai.core.factory.UserFactory;
import com.roy.sqwaimai.core.query.Page;
import com.roy.sqwaimai.core.query.SearchFilter;
import com.roy.sqwaimai.core.util.MD5;
import com.roy.sqwaimai.service.system.UserService;
import com.roy.sqwaimai.utils.*;
import com.roy.sqwaimai.warpper.UserWarpper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @RequiresPermissions(value = {Permission.USER})
    public Object list(@RequestParam(required = false) String account,
                       @RequestParam(required = false) String name){
        Page page = new PageFactory().defaultPage();
        if(StringUtils.isNotEmpty(name)){
            page.addFilter(SearchFilter.build("name", SearchFilter.Operator.LIKE, name));
        }
        if(StringUtils.isNotEmpty(account)){
            page.addFilter(SearchFilter.build("account", SearchFilter.Operator.LIKE, account));
        }
        page.addFilter(SearchFilter.build("status",SearchFilter.Operator.GT,0));
        page = userService.queryPage(page);
        List list = (List) new UserWarpper(BeanUtil.objectsToMaps(page.getRecords())).warp();
        page.setRecords(list);
        return Rets.success(page);
    }
    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "编辑管理员", key = "name", dict = UserDict.class)
    @RequiresPermissions(value = {Permission.USER_EDIT})
    public Object save(@Valid UserDto user, BindingResult result){
        if(user.getId()==null) {
            // 判断账号是否重复
            User theUser = userService.findByAccount(user.getAccount());
            if (theUser != null) {
                throw new ApplicationException(BizExceptionEnum.USER_ALREADY_REG);
            }
            // 完善账号信息
            user.setSalt(ToolUtil.getRandomString(5));
            user.setPassword(MD5.md5(user.getPassword(), user.getSalt()));
            user.setStatus(ManagerStatus.OK.getCode());
            userService.insert(UserFactory.createUser(user, new User()));
        }else{
            User oldUser = userService.get(user.getId());
            userService.update(UserFactory.updateUser(user,oldUser));
        }
        return Rets.success();
    }

    @BussinessLog(value = "删除管理员", key = "userId", dict = UserDict.class)
    @RequestMapping(method = RequestMethod.DELETE)
    @RequiresPermissions(value = {Permission.USER_DEL})
    public Object remove(@RequestParam Long userId){
        if (ToolUtil.isEmpty(userId)) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
        if(userId.intValue()<=2){
            return Rets.failure("不能删除初始用户");
        }
        User user = userService.get(userId);
        user.setStatus(ManagerStatus.DELETED.getCode());
        userService.update(user);
        return Rets.success();
    }
    @BussinessLog(value="设置用户角色",key="userId",dict=UserDict.class)
    @RequestMapping(value="/setRole",method = RequestMethod.GET)
    @RequiresPermissions(value = {Permission.USER_EDIT})
    public Object setRole(@RequestParam("userId") Long userId, @RequestParam("roleIds") String roleIds) {
        if (ToolUtil.isOneEmpty(userId, roleIds)) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能修改超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new ApplicationException(BizExceptionEnum.CANT_CHANGE_ADMIN);
        }
        User user = userService.get(userId);
        user.setRoleid(roleIds);
        userService.update(user);
        return Rets.success();
    }
}
