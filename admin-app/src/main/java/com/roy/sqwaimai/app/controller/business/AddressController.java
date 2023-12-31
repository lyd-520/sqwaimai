package com.roy.sqwaimai.app.controller.business;

import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.bean.enumeration.BizExceptionEnum;
import com.roy.sqwaimai.bean.exception.ApplicationException;
import com.roy.sqwaimai.core.entity.vo.front.Rets;
import com.roy.sqwaimai.core.entity.Address;
import com.roy.sqwaimai.core.entity.Ids;
import com.roy.sqwaimai.core.entity.vo.City;
import com.roy.sqwaimai.core.service.AddressService;
import com.roy.sqwaimai.core.service.IdsService;
import com.roy.sqwaimai.core.service.PositionService;
import com.roy.sqwaimai.utils.ToolUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/address")
public class AddressController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(AddressController.class);
    @DubboReference
    private IdsService idsService;
    @DubboReference
    private PositionService positionService;

    @DubboReference
    private AddressService addressService;

    @RequestMapping(value = "/queryUserAddress/{user_id}",method = RequestMethod.GET)
    public Object address(@PathVariable("user_id") Long userId){
        return Rets.success(addressService.getAddressByUserId(userId));
    }
    @RequestMapping(value = "/saveUserAddress/{user_id}",method =  RequestMethod.POST)
    public Object save(@PathVariable("user_id") Long userId){
        City city = positionService.guessCity(getIp());
        Address address = getRequestPayload(Address.class);
        address.setUser_id(userId);
        address.setCity_id(city.getId());
        address.setId(idsService.getId(Ids.ADDRESS_ID));
        addressService.save(address);
        return Rets.success("添加地址成功");
    }
    @RequestMapping(value = "/deleteuseraddress/{user_id}/{address_id}",method =  RequestMethod.DELETE)
    public Object delete(@PathVariable("user_id") Long userId, @PathVariable("address_id") Long addressId){
        addressService.deleteUserAddress(userId,addressId);
        return Rets.success("删除地址成功");
    }

    @RequestMapping(value="/getaddressbyid/{id}",method = RequestMethod.GET)
    public Object get(@PathVariable Long id){
        logger.info("id:{}",id);
        if (ToolUtil.isEmpty(id)) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
        return Rets.success(addressService.findOne(id));
    }
}
