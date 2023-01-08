package com.roy.sqwaimai.busi.service;

import com.roy.sqwaimai.core.entity.FrontUser;
import com.roy.sqwaimai.core.entity.FrontUserInfo;
import com.roy.sqwaimai.core.entity.Ids;
import com.roy.sqwaimai.core.entity.vo.CityInfo;
import com.roy.sqwaimai.core.entity.vo.LoginVo;
import com.roy.sqwaimai.core.service.FrontUserService;
import com.roy.sqwaimai.core.util.DateUtil;
import com.roy.sqwaimai.core.util.MD5;
import org.apache.dubbo.config.annotation.DubboService;
import org.nutz.mapl.Mapl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
@DubboService
public class FrontUserServiceImpl extends MongoService implements FrontUserService {

    @Autowired
    private IdsServiceImpl idsService;
    @Autowired
    private PositionServiceImpl positionService;

    public void updateAvatar(Long userId, String originalFilename) {
        FrontUserInfo frontUserInfo = mongoRepository.findOne(FrontUserInfo.class, "user_id", userId);
        frontUserInfo.setAvatar(originalFilename);
        mongoRepository.update(frontUserInfo);
    }

    public FrontUser findUserByName(String userName){
        return mongoRepository.findOne(FrontUser.class,"username",userName);
    }

    public void updateUser(FrontUser user) {
        mongoRepository.update(user);
    }

    public Object findUserInfo(Long userId){
        Map user = mongoRepository.findOne("users", "user_id", userId);
        Map userInfo = mongoRepository.findOne("userinfos", "user_id", userId);
        return Mapl.merge(user, userInfo);
    }

    public FrontUser registerUser(LoginVo loginVo,String clientIp) {
        String newPassword = MD5.getMD5String(MD5.getMD5String(loginVo.getPassword()).substring(2, 7) + MD5.getMD5String(loginVo.getPassword()));
        FrontUser frontUser = new FrontUser();
        frontUser.setUser_id(idsService.getId(Ids.USER_ID));
        frontUser.setUsername(loginVo.getUsername());
        frontUser.setPassword(newPassword);
        mongoRepository.save(frontUser);
        FrontUserInfo userInfo = new FrontUserInfo();
        userInfo.setId(frontUser.getUser_id());
        userInfo.setUser_id(frontUser.getUser_id());
        userInfo.setRegiste_time(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));
        CityInfo cityInfo = positionService.getPostion(clientIp);
        userInfo.setCity(cityInfo.getCity());
        userInfo.setUsername(frontUser.getUsername());
        mongoRepository.save(userInfo);
        return frontUser;
    }
}
