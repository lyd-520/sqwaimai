package com.roy.sqwaimai.busi.service;

import com.roy.sqwaimai.core.entity.FrontRider;
import com.roy.sqwaimai.core.entity.FrontRiderInfo;
import com.roy.sqwaimai.core.entity.Ids;
import com.roy.sqwaimai.core.entity.vo.CityInfo;
import com.roy.sqwaimai.core.entity.vo.LoginVo;
import com.roy.sqwaimai.core.service.RiderService;
import com.roy.sqwaimai.core.util.CryptUtils;
import com.roy.sqwaimai.core.util.DateUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
@DubboService
public class RiderServiceImpl extends MongoService implements RiderService {

    @Autowired
    private IdsServiceImpl idsService;
    @Autowired
    private PositionServiceImpl positionService;

    public FrontRiderInfo clearBalance(String riderid) {
        FrontRiderInfo riderInfo = mongoRepository.findOne(FrontRiderInfo.class, "rider_id", Long.parseLong(riderid));
        riderInfo.setBalance(0.00);
        mongoRepository.update(riderInfo);
        return riderInfo;
    }

    public FrontRider findRiderByName(String userName) {
        return mongoRepository.findOne(FrontRider.class,"rider_name",userName);
    }

    public FrontRiderInfo registerRider(LoginVo loginVo, String ip) {
        FrontRider frontRider = new FrontRider();
        frontRider.setRider_id(idsService.getId(Ids.RIDER_ID));
        frontRider.setRider_name(loginVo.getUsername());
        String newPassword = CryptUtils.encodePassword(loginVo.getPassword());
        frontRider.setPassword(newPassword);
        mongoRepository.save(frontRider);

        FrontRiderInfo riderInfo = new FrontRiderInfo();
        riderInfo.setRider_id(frontRider.getRider_id());
        riderInfo.setId(frontRider.getRider_id());
        riderInfo.setRegiste_time(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));
        riderInfo.setBalance(0.00);
        riderInfo.setOrder_count(0);
        riderInfo.setBalance_amount(0.00);
        CityInfo cityInfo = positionService.getPostion(ip);
        riderInfo.setCity(cityInfo.getCity());
        riderInfo.setRider_name(frontRider.getRider_name());
        mongoRepository.save(riderInfo);

        return riderInfo;
    }

    public FrontRiderInfo findRiderInfo(Long rider_id) {
        return mongoRepository.findOne(FrontRiderInfo.class, "rider_id", rider_id);
    }

    public void updateAvatar(Long userId, String originalFilename) {
        Map riderInfo = mongoRepository.findOne("ridersinfo", "rider_id", userId);
        riderInfo.put("avatar",originalFilename);
        mongoRepository.update(Long.valueOf(riderInfo.get("rider_id").toString()), "ridersinfo", riderInfo);
    }

    public void updateRider(FrontRider rider) {
//        mongoRepository.update(Long.valueOf(user.get("rider_id").toString()), "riders", user);
        mongoRepository.update(rider);
    }

    public FrontRiderInfo riderOnline(String riderid) {
        FrontRiderInfo riderInfo = mongoRepository.findOne(FrontRiderInfo.class, "rider_id", Long.parseLong(riderid));
        //修改骑手状态
        riderInfo.setWork_status(FrontRiderInfo.STATUS_ONLINE);
        mongoRepository.update(riderInfo);
        return riderInfo;
    }

    public void updateRiderInfo(FrontRiderInfo riderInfo) {
        mongoRepository.update(riderInfo);
    }

    public void updateRiderMobile(Long userId, String mobile) {
        Map riderInfo = mongoRepository.findOne("ridersinfo", "rider_id", userId);
        riderInfo.put("mobile",mobile);
        mongoRepository.update(Long.valueOf(riderInfo.get("rider_id").toString()), "ridersinfo", riderInfo);
    }
}
