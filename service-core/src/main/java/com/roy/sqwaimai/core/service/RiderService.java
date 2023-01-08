package com.roy.sqwaimai.core.service;

import com.roy.sqwaimai.core.entity.FrontRider;
import com.roy.sqwaimai.core.entity.FrontRiderInfo;
import com.roy.sqwaimai.core.entity.vo.LoginVo;

public interface RiderService{

    FrontRiderInfo clearBalance(String riderid);

    FrontRider findRiderByName(String userName);

    FrontRiderInfo registerRider(LoginVo loginVo, String ip);

    FrontRiderInfo findRiderInfo(Long rider_id) ;

    void updateAvatar(Long userId, String originalFilename);

    void updateRider(FrontRider rider);

    FrontRiderInfo riderOnline(String riderid);

    void updateRiderInfo(FrontRiderInfo riderInfo);

    void updateRiderMobile(Long userId, String mobile);
}
