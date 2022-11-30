package com.roy.sqwaimai.service.task;


import com.roy.sqwaimai.bean.entity.system.TaskLog;
import com.roy.sqwaimai.dao.system.TaskLogRepository;
import com.roy.sqwaimai.service.BaseService;
import org.springframework.stereotype.Service;

/**
 * 定时任务日志服务类
 * @author  enilu
 * @date 2019-08-13
 */
@Service
public class TaskLogService extends BaseService<TaskLog,Long, TaskLogRepository> {
}
