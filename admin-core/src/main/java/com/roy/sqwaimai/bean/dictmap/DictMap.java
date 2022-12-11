package com.roy.sqwaimai.bean.dictmap;


import com.roy.sqwaimai.bean.dictmap.base.AbstractDictMap;

/**
 * 字典map
 */
public class DictMap extends AbstractDictMap {

    @Override
    public void init() {
        put("dictId","字典名称");
        put("dictName","字典名称");
        put("dictValues","字典内容");
    }

    @Override
    protected void initBeWrapped() {

    }
}
