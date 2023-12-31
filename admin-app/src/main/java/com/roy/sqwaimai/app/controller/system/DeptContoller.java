package com.roy.sqwaimai.app.controller.system;

import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.bean.core.BussinessLog;
import com.roy.sqwaimai.bean.dictmap.DeptDict;
import com.roy.sqwaimai.bean.entity.system.Dept;
import com.roy.sqwaimai.bean.enumeration.BizExceptionEnum;
import com.roy.sqwaimai.bean.enumeration.Permission;
import com.roy.sqwaimai.bean.exception.ApplicationException;
import com.roy.sqwaimai.core.entity.vo.front.Rets;
import com.roy.sqwaimai.bean.vo.node.DeptNode;
import com.roy.sqwaimai.service.system.DeptService;
import com.roy.sqwaimai.service.system.LogObjectHolder;
import com.roy.sqwaimai.utils.ToolUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/dept")
public class DeptContoller extends BaseController {
    private Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private DeptService deptService;
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @RequiresPermissions(value = {Permission.DEPT})
    public Object list(){
        List<DeptNode> list = deptService.queryAllNode();
        return Rets.success(list);
    }
    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "编辑部门", key = "simplename", dict = com.roy.sqwaimai.bean.dictmap.DeptDict.class)
    @RequiresPermissions(value = {Permission.DEPT_EDIT})
    public Object save(@ModelAttribute @Valid Dept dept){
        if (ToolUtil.isOneEmpty(dept, dept.getSimplename())) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
        if(dept.getId()!=null){
            Dept old = deptService.get(dept.getId());
            LogObjectHolder.me().set(old);
            old.setPid(dept.getPid());
            old.setSimplename(dept.getSimplename());
            old.setFullname(dept.getFullname());
            old.setNum(dept.getNum());
            old.setTips(dept.getTips());
            deptService.deptSetPids(old);
            deptService.update(old);
        }else {
            deptService.deptSetPids(dept);
            deptService.insert(dept);
        }
        return Rets.success();
    }
    @RequestMapping(method = RequestMethod.DELETE)
    @BussinessLog(value = "删除部门", key = "id", dict = DeptDict.class)
    @RequiresPermissions(value = {Permission.DEPT_DEL})
    public Object remove(@RequestParam Long id){
        logger.info("id:{}",id);
        if (ToolUtil.isEmpty(id)) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
        deptService.deleteDept(id);
        return Rets.success();
    }
}
