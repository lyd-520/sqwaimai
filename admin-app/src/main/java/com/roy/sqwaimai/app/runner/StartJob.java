package com.roy.sqwaimai.app.runner;

import com.roy.sqwaimai.bean.entity.system.Task;
import com.roy.sqwaimai.bean.vo.QuartzJob;
import com.roy.sqwaimai.core.query.SearchFilter;
import com.roy.sqwaimai.service.task.JobService;
import com.roy.sqwaimai.service.task.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 初始化定时任务
 */
@Component
public class StartJob implements ApplicationRunner {

    @Autowired
    private JobService jobService;

    @Resource
    private TaskService taskService;

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        log.info("start Job >>>>>>>>>>>>>>>>>>>>>>>");
//        List<QuartzJob> joblist = jobService.getTaskList();

        List<Task> tasks = taskService.queryAll(SearchFilter.build("disabled", SearchFilter.Operator.EQ,false));
//        List<QuartzJob> jobs = new ArrayList<>();
        for (Task task : tasks) {
            jobService.addJob(jobService.getJob(task));
//            joblist.add(getJob(task));
        }

//        for (QuartzJob quartzJob : joblist) {
//            jobService.addJob(quartzJob);
//        }
    }


}
