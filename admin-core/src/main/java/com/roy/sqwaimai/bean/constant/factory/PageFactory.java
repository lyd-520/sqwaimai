package com.roy.sqwaimai.bean.constant.factory;

import com.roy.sqwaimai.bean.constant.state.Order;
import com.roy.sqwaimai.utils.HttpKit;
import com.roy.sqwaimai.utils.ToolUtil;
import com.roy.sqwaimai.utils.factory.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;

/**
 * BootStrap Table默认的分页参数创建
 */
public class PageFactory<T> {

    public Page<T> defaultPage() {
        HttpServletRequest request = HttpKit.getRequest();
        //每页多少条数据
        int limit = Integer.valueOf(request.getParameter("limit"));
        String pageNum = request.getParameter("page");
        //每页的偏移量(本页当前有多少条)
        int offset = 0;
        if (StringUtils.isNotEmpty(pageNum)) {
            offset = (Integer.valueOf(pageNum) - 1) * limit;
        } else {

            offset = Integer.valueOf(request.getParameter("offset"));
        }
        //排序字段名称
        String sortName = request.getParameter("sort");
        //asc或desc(升序或降序)
        String order = request.getParameter("order");
        if (ToolUtil.isEmpty(sortName)) {
            Page<T> page = new Page<>((offset / limit + 1), limit);
            return page;
        } else {
            Page<T> page = new Page<>((offset / limit + 1), limit, sortName);
            if (Order.ASC.getDes().equals(order)) {
                Sort sort = Sort.by(Sort.Direction.ASC, order);
                page.setSort(sort);
            } else {
                Sort sort = Sort.by(Sort.Direction.DESC, order);
                page.setSort(sort);

            }
            return page;
        }
    }

    public Page<T> rawPage(){
        return new Page<T>();
    }
}
