package com.roy.sqwaimai.bean.dictmap;

import com.roy.sqwaimai.bean.dictmap.base.AbstractDictMap;

/**
 * 字典map
 */
public class TaskDict extends AbstractDictMap {

    @Override
    public void init() {
        put("taskId","任务id");
        put("name","任务名称");
    }

    @Override
    protected void initBeWrapped() {

    }
}
