package com.roy.sqwaimai.core.service;

import com.roy.sqwaimai.core.entity.FrontUser;
import com.roy.sqwaimai.core.entity.vo.LoginVo;

public interface FrontUserService{

    void updateAvatar(Long userId, String originalFilename);

    FrontUser findUserByName(String userName);

    void updateUser(FrontUser user);

    Object findUserInfo(Long userId);

    FrontUser registerUser(LoginVo loginVo,String clientIp);
}
