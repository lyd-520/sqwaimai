package com.roy.sqwaimai.service.system;

import com.roy.sqwaimai.bean.entity.system.User;
import com.roy.sqwaimai.cache.impl.EhcacheDao;
import com.roy.sqwaimai.dao.system.UserRepository;
import com.roy.sqwaimai.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService  extends BaseService<User,Long, UserRepository> {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EhcacheDao ehcacheDao;

    public User findByAccount(String account) {
        //由于：@Cacheable标注的方法，如果其所在的类实现了某一个接口，那么该方法也必须出现在接口里面，否则cache无效。
        //具体的原因是， Spring把实现类装载成为Bean的时候，会用代理包装一下，所以从Spring Bean的角度看，只有接口里面的方法是可见的，其它的都隐藏了，自然课看不到实现类里面的非接口方法，@Cacheable不起作用。
        //所以这里手动控制缓存
        User user =  ehcacheDao.hget(EhcacheDao.SESSION,account,User.class);
        if(user!=null){
            return user;
        }
        user = userRepository.findByAccount(account);
        ehcacheDao.hset(EhcacheDao.SESSION,account,user);
        return user;
    }

}
