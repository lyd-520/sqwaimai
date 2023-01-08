package com.roy.sqwaimai.core.service;

import com.roy.sqwaimai.core.entity.Address;

import java.util.List;

public interface AddressService{

    List<Address> getAddressByUserId(Long userId);

    void save(Address address);

    void deleteUserAddress(Long userId, Long addressId) ;

    Address findOne(Long id) ;
}
