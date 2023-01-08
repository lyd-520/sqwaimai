package com.roy.sqwaimai.app.controller.cms;

import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.bean.core.BussinessLog;
import com.roy.sqwaimai.bean.dictmap.CommonDict;
import com.roy.sqwaimai.bean.entity.cms.Banner;
import com.roy.sqwaimai.bean.enumeration.Permission;
import com.roy.sqwaimai.core.entity.vo.front.Rets;
import com.roy.sqwaimai.core.query.SearchFilter;
import com.roy.sqwaimai.service.cms.BannerService;
import com.roy.sqwaimai.utils.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * banner管理
 */
@RestController
@RequestMapping("/banner")
public class BannerMgrController extends BaseController {
    @Autowired
    private BannerService bannerService;

    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "编辑banner", key = "title", dict = CommonDict.class)
    @RequiresPermissions(value = {Permission.BANNER_EDIT})
    public Object save(@ModelAttribute @Valid Banner banner) {
        if(banner.getId()==null){
            bannerService.insert(banner);
        }else {
            bannerService.update(banner);
        }
        return Rets.success();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @BussinessLog(value = "删除banner", key = "id", dict = CommonDict.class)
    @RequiresPermissions(value = {Permission.BANNER_DEL})
    public Object remove(Long id) {
        bannerService.delete(id);
        return Rets.success();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions(value = {Permission.BANNER})
    public Object list(@RequestParam(required = false) String title) {
        SearchFilter filter = null;
        if(StringUtils.isNotEmpty(title)){
             filter =  SearchFilter.build("title", SearchFilter.Operator.LIKE,title);
        }
        List<Banner> list = bannerService.queryAll(filter);
        return Rets.success(list);
    }
}
