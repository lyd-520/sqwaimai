package com.roy.sqwaimai.dao.system;

import com.roy.sqwaimai.bean.entity.system.LoginLog;
import com.roy.sqwaimai.dao.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface LoginLogRepository extends BaseRepository<LoginLog,Long> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = "delete from t_sys_login_log")
    int clear();
}
