package com.roy.sqwaimai.service.task.job;

import com.alibaba.fastjson.JSON;
import com.roy.sqwaimai.bean.entity.system.Cfg;
import com.roy.sqwaimai.service.system.CfgService;
import com.roy.sqwaimai.service.task.JobExecuter;
import com.roy.sqwaimai.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HelloJob extends JobExecuter {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CfgService cfgService;
    @Override
    public void execute(Map<String, Object> dataMap) throws Exception {
        Cfg cfg = cfgService.get(1L);
        cfg.setCfgDesc("update by "+ DateUtil.getTime());
        cfgService.update(cfg);
        logger.info("hello :"+JSON.toJSONString(dataMap));
    }
}
