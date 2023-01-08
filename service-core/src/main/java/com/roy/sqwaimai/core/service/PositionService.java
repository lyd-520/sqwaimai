package com.roy.sqwaimai.core.service;

import com.roy.sqwaimai.core.entity.vo.City;
import com.roy.sqwaimai.core.entity.vo.CityInfo;

import java.util.List;
import java.util.Map;

public interface PositionService {

    City guessCity(String ip);
    CityInfo getPostion(String ip);
    List searchPlace(String cityName, String keyword);

    Map findById(Integer id);

    Map findByName(String cityName);

    Map pois(String geohash);

    Map<String,String> getDistance(String from,String to);

    Map findCities();
}
