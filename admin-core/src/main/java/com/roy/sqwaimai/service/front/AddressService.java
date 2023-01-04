package com.roy.sqwaimai.service.front;

import com.roy.sqwaimai.bean.entity.front.Address;
import com.roy.sqwaimai.service.MongoService;
import com.roy.sqwaimai.utils.Maps;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService extends MongoService {

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
