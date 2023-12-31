package com.roy.sqwaimai.warpper;

import com.roy.sqwaimai.service.system.impl.ConstantFactory;
import com.roy.sqwaimai.utils.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 用户管理的包装类
 */
public class UserWarpper extends BaseControllerWarpper {

    public UserWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
//        map.put("sexName", ConstantFactory.me().getSexName((Integer) map.get("sex")));
        if(StringUtils.isNotNullOrEmpty(map.get("roleid"))) {
            map.put("roleName", ConstantFactory.me().getRoleName((String) map.get("roleid")));
        }
        if(StringUtils.isNotNullOrEmpty(map.get("deptid"))) {
            map.put("deptName", ConstantFactory.me().getDeptName((Long) map.get("deptid")));
        }
        map.put("statusName", ConstantFactory.me().getStatusName((Integer) map.get("status")));

    }

}
