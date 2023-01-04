package com.roy.sqwaimai.app.controller.business;

import com.google.common.base.Strings;
import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.bean.vo.business.City;
import com.roy.sqwaimai.bean.vo.business.CityInfo;
import com.roy.sqwaimai.bean.vo.front.Rets;
import com.roy.sqwaimai.dao.MongoRepository;
import com.roy.sqwaimai.service.front.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class PositionController extends BaseController {

    @Autowired
    private MongoRepository mongoRepository;
    @Autowired
    private PositionService positionService;

    @RequestMapping(value = "/api/cities",method = RequestMethod.GET)
    public Object cities(@RequestParam("type") String type, HttpServletRequest request) {
        Map cities = mongoRepository.findOne("cities");
        Map data = (Map) cities.get("data");
        switch (type){
            case "guess":
                CityInfo cityInfo = positionService.getPostion(getIp());
                if(cityInfo!=null) {
                    String city = cityInfo.getCity();
                    if (Strings.isNullOrEmpty(city)) {
                        return Rets.failure();
                    }
                    return Rets.success(positionService.findByName(city));
                }else{
                    Rets.success();
                }

            case "hot":

                return Rets.success(data.get("hotCities"));

            case "group":
                return  Rets.success(data);


            default:
                    break;


        }
        return Rets.failure();

    }
    @RequestMapping(value = "/api/cities/{id}",method = RequestMethod.GET)
    public Object getCity(@PathVariable("id") Integer id){
        return Rets.success(positionService.findById(id));
    }

    @RequestMapping(value = "/api/pois",method = RequestMethod.GET)
    public Object getPoiByCityAndKeyword(@RequestParam(value = "type",defaultValue = "search") String type,
                                         @RequestParam(value = "city_id",required = false) Integer cityId,
                                         @RequestParam(value = "keyword") String keyword){
        String cityName = null;
        if(cityId==null){
            City city = positionService.guessCity(getIp());
            cityName = city.getName();
        }else {
            Map map = positionService.findById(cityId);
            cityName = map.get("name").toString();
        }
        return Rets.success(positionService.searchPlace(cityName, keyword));

    }

    @RequestMapping(value = "/position/pois",method = RequestMethod.GET)
    public Object getPoiByGeoHash(@RequestParam("geohash") String geoHash){
        return Rets.success(positionService.pois(geoHash));
    }
}
