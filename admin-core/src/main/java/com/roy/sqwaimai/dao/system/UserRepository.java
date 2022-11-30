package com.roy.sqwaimai.dao.system;


import com.roy.sqwaimai.bean.entity.system.User;
import com.roy.sqwaimai.dao.BaseRepository;

public interface UserRepository extends BaseRepository<User,Long> {
    User findByAccount(String account);

    User findByAccountAndStatusNot(String account, Integer status);
}
