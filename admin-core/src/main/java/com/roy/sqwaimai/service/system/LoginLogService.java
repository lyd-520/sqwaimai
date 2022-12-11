package com.roy.sqwaimai.service.system;

import com.roy.sqwaimai.bean.entity.system.LoginLog;
import com.roy.sqwaimai.dao.system.LoginLogRepository;
import com.roy.sqwaimai.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class LoginLogService extends BaseService<LoginLog,Long, LoginLogRepository> {

}
