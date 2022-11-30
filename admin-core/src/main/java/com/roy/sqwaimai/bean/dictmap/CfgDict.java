package com.roy.sqwaimai.bean.dictmap;


import com.roy.sqwaimai.bean.dictmap.base.AbstractDictMap;

/**
 * 字典map
 */
public class CfgDict extends AbstractDictMap {

    @Override
    public void init() {
        put("id","参数id");
        put("cfgName","参数名称");
        put("cfgDesc","备注");
    }

    @Override
    protected void initBeWrapped() {

    }
}
