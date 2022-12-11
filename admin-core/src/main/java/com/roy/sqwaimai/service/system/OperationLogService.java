package com.roy.sqwaimai.service.system;

import com.roy.sqwaimai.bean.entity.system.OperationLog;
import com.roy.sqwaimai.dao.system.OperationLogRepository;
import com.roy.sqwaimai.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class OperationLogService extends BaseService<OperationLog,Long, OperationLogRepository> {

}
