package com.roy.sqwaimai.bean.dictmap;


import com.roy.sqwaimai.bean.dictmap.base.AbstractDictMap;

public class LogDict extends AbstractDictMap {

    @Override
    public void init() {
        put("tips","备注");
    }

    @Override
    protected void initBeWrapped() {

    }
}
