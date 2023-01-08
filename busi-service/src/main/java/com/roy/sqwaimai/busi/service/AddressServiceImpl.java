package com.roy.sqwaimai.busi.service;

import com.roy.sqwaimai.core.entity.Address;
import com.roy.sqwaimai.core.service.AddressService;
import com.roy.sqwaimai.core.util.Maps;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DubboService
public class AddressServiceImpl extends MongoService implements AddressService {

    public List<Address> getAddressByUserId(Long userId){
        return mongoRepository.findAll(Address.class,"user_id",userId);
    }

    public void save(Address address){
        mongoRepository.save(address);
    }

    public void deleteUserAddress(Long userId, Long addressId) {
        mongoRepository.delete("addresses", Maps.newHashMap("user_id",userId,"id",addressId));
    }

    public Address findOne(Long id) {
        return mongoRepository.findOne(Address.class,id);
    }
}
