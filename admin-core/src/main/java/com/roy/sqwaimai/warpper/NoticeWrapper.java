package com.roy.sqwaimai.warpper;


import com.roy.sqwaimai.service.system.impl.ConstantFactory;

import java.util.Map;

/**
 * 部门列表的包装
 */
public class NoticeWrapper extends BaseControllerWarpper {

    public NoticeWrapper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        Long creater = (Long) map.get("createBy");
        map.put("createrName", ConstantFactory.me().getUserNameById(creater));
    }

}
