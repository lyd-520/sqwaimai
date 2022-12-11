package com.roy.sqwaimai.app.controller.business;

import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.bean.entity.front.Address;
import com.roy.sqwaimai.bean.entity.front.Ids;
import com.roy.sqwaimai.bean.enumeration.BizExceptionEnum;
import com.roy.sqwaimai.bean.exception.ApplicationException;
import com.roy.sqwaimai.bean.vo.business.City;
import com.roy.sqwaimai.bean.vo.front.Rets;
import com.roy.sqwaimai.dao.MongoRepository;
import com.roy.sqwaimai.service.front.IdsService;
import com.roy.sqwaimai.service.front.PositionService;
import com.roy.sqwaimai.utils.Maps;
import com.roy.sqwaimai.utils.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(AddressController.class);
    @Autowired
    private MongoRepository mongoRepository;
    @Autowired
    private IdsService idsService;
    @Autowired
    private PositionService positionService;
    @RequestMapping(value = "/v1/users/{user_id}/addresses",method = RequestMethod.GET)
    public Object address(@PathVariable("user_id") Long userId){
        return Rets.success(mongoRepository.findAll(Address.class,"user_id",userId));
    }
    @RequestMapping(value = "/v1/users/{user_id}/addresses",method =  RequestMethod.POST)
    public Object save(@PathVariable("user_id") Long userId){
        City city = positionService.guessCity(getIp());
        Address address = getRequestPayload(Address.class);
        address.setUser_id(userId);
        address.setCity_id(city.getId());
        address.setId(idsService.getId(Ids.ADDRESS_ID));
        mongoRepository.save(address);
        return Rets.success("添加地址成功");
    }
    @RequestMapping(value = "/v1/users/{user_id}/addresses/{address_id}",method =  RequestMethod.DELETE)
    public Object delete(@PathVariable("user_id") Long userId, @PathVariable("address_id") Long addressId){
        mongoRepository.delete("addresses", Maps.newHashMap("user_id",userId,"id",addressId));
        return Rets.success("删除地址成功");
    }

    @RequestMapping(value="/address/{id}",method = RequestMethod.GET)
    public Object get(@PathVariable Long id){
        logger.info("id:{}",id);
        if (ToolUtil.isEmpty(id)) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
         return Rets.success(mongoRepository.findOne(Address.class,id));
    }
}
