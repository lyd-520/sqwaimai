package com.roy.sqwaimai.busi.service;

import com.alibaba.fastjson.JSON;
import com.roy.sqwaimai.busi.config.AppConfiguration;
import com.roy.sqwaimai.core.entity.vo.City;
import com.roy.sqwaimai.core.entity.vo.CityInfo;
import com.roy.sqwaimai.core.service.PositionService;
import com.roy.sqwaimai.core.util.HttpClients;
import com.roy.sqwaimai.core.util.Maps;
import org.apache.dubbo.config.annotation.DubboService;
import org.nutz.mapl.Mapl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Service
@DubboService
public class PositionServiceImpl extends MongoService implements PositionService {
    private Logger logger = LoggerFactory.getLogger(PositionServiceImpl.class);
    @Autowired
    private AppConfiguration appConfiguration;

    /**
     * 根据ip获取所属城市id和名称
     * @param ip
     * @return
     */
    public City guessCity(String ip){
        CityInfo cityInfo = getPostion(ip);
        if(cityInfo!=null) {
            Map map = findByName(cityInfo.getCity());
            City city = new City();
            city.setId(Integer.valueOf(map.get("id").toString()));
            city.setName(map.get("name").toString());
            return city;
        }
        return null;
    }
    public CityInfo getPostion(String ip) {
        logger.info("根据ip:{}获取城市信息",ip);
        Map<String, String> map = Maps.newHashMap();
        map.put("ip", ip);
        map.put("key", appConfiguration.getTencentKey());
        Map result = null;
        try {
            String str = HttpClients.get(appConfiguration.getQqApiUrl() + "location/v1/ip", map);
            result = JSON.parseObject(str);
        } catch (Exception e) {
            logger.error("获取地理位置异常", e);
        }
        if (result == null || Integer.valueOf(result.get("status").toString()) != 0) {
            try {
                map.put("key", appConfiguration.getTencentKey2());
                String str = HttpClients.get(appConfiguration.getQqApiUrl() + "location/v1/ip", map);
                result = JSON.parseObject(str);
            } catch (Exception e) {
                logger.error("获取地理位置异常", e);
            }
        }
        if (result == null || Integer.valueOf(result.get("status").toString()) != 0) {
            try {
                map.put("key", appConfiguration.getTencentKey3());
                String str = HttpClients.get(appConfiguration.getQqApiUrl() + "location/v1/ip", map);
                result = JSON.parseObject(str);;
            } catch (Exception e) {
                logger.error("获取地理位置异常", e);
            }

        }
        if (Integer.valueOf(result.get("status").toString()) == 0) {
            Map resultData = (Map) result.get("result");

            String lat = String.valueOf(Mapl.cell(resultData, "location.lat"));
            String lng = String.valueOf(Mapl.cell(resultData, "location.lng"));
            String city = (String) Mapl.cell(resultData, "ad_info.city");
            city = city.replace("市", "");
            CityInfo cityInfo = new CityInfo();
            cityInfo.setCity(city);
            cityInfo.setLat(lat);
            cityInfo.setLng(lng);
            return cityInfo;
        }else{
            logger.error("获取地址失败："+result);
        }
        return null;
    }
    public List searchPlace(String cityName, String keyword) {
        logger.info("获取地址信息:{}，{}",cityName,keyword);
        Map<String, String> params = Maps.newHashMap();
        params.put("key", appConfiguration.getTencentKey());
        try {
            params.put("keyword", URLEncoder.encode(keyword,"utf-8"));
        } catch (UnsupportedEncodingException e) {
           logger.error(e.getMessage(),e);
        }
        try {
            params.put("boundary", "region(" + URLEncoder.encode(cityName,"utf-8") + ",0)");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(),e);
        }
        params.put("page_size", "10");
        try {
            String str = HttpClients.get(appConfiguration.getQqApiUrl() + "place/v1/search", params);
            Map result = JSON.parseObject(str);;
            if (Integer.valueOf(result.get("status").toString()).intValue() == 0) {
                return (List) result.get("data");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return null;

    }

    public Map findById(Integer id) {
        Map cities = mongoRepository.findOne("cities");
        Map<String, List> data = (Map) cities.get("data");
        Map result = null;
        for (Map.Entry<String, List> entry : data.entrySet()) {
            List list = entry.getValue();
            for (int i = 0; i < list.size(); i++) {
                Map rec = (Map) list.get(i);
                if (id == Double.valueOf(rec.get("id").toString()).intValue()) {
                    result = rec;
                    break;
                }
            }
        }
        return result;
    }

    public Map findByName(String cityName) {
        Map cities = mongoRepository.findOne("cities");
        Map<String, List> data = (Map) cities.get("data");
        Map result = null;
        for (Map.Entry<String, List> entry : data.entrySet()) {
            List list = entry.getValue();
            for (int i = 0; i < list.size(); i++) {
                Map rec = (Map) list.get(i);
                if (cityName.equals(rec.get("name"))) {
                    result = rec;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 根据经纬度坐标获取位置信息
     *
     * @param geohash
     * @return
     */

    public Map pois(String geohash) {
        logger.info("获取地址信息:{}",geohash);
        Map<String, String> map = Maps.newHashMap();
        map.put("location", geohash);
        map.put("key", appConfiguration.getTencentKey());
        Map result = Maps.newHashMap();
        try {
            String str = HttpClients.get(appConfiguration.getQqApiUrl() + "geocoder/v1", map);
            Map response = JSON.parseObject(str);
            if ("0".equals(response.get("status").toString())) {
                result.put("address", Mapl.cell(response,"result.address"));
                result.put("city", Mapl.cell(response, "result.address_component.city"));
                result.put("name", Mapl.cell(response, "result.formatted_addresses.recommend"));
                result.put("latitude", Mapl.cell(response, "result.location.lat"));
                result.put("longitude", Mapl.cell(response, "result.location.lng"));
            }else{
                logger.error("获取地理位置信息失败:{}",str);
            }

        } catch (Exception e) {
            logger.error("获取地理位置异常", e);
        }
        return result;
    }

    public Map<String,String> getDistance(String from,String to){
        Map<String,String> params = Maps.newHashMap(
                "ak",appConfiguration.getBaiduKey(),
                "output","json",
                "origins",from,
                "destinations",to
        );
        try {
            //使用百度地图api获取距离值：
            //routematrix/v2/riding 骑行
            //routematrix/v2/driving 开车
            //routematrix/v2/walking 步行
            String str = HttpClients.get(appConfiguration.getBaiduApiUrl() + "routematrix/v2/riding", params);
            Map response = JSON.parseObject(str);;
            if("0".equals(response.get("status").toString())){
              Map result =  Maps.newHashMap(
                      "distance",Mapl.cell(response,"result[0].distance.text"),
                      "duration",Mapl.cell(response,"result[0].duration.text")
              );
              return result;
            }

        }catch (Exception e){
            logger.error("通过百度获取配送距离失败",e);
        }
        return null;
    }

    @Override
    public Map findCities() {
        return mongoRepository.findOne("cities");
    }
}
