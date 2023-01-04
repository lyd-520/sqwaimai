package com.roy.sqwaimai.app.controller.business;

import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.bean.vo.front.Rets;
import com.roy.sqwaimai.service.front.DeliveryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/api/delivery")
public class DeliveryController extends BaseController {

    @Resource
    private DeliveryService deliveryService;

    @RequestMapping(value = "/delivery_modes",method = RequestMethod.GET)
    public Object list(@RequestParam(value = "latitude",required = false) String latitude,
                       @RequestParam(value = "longitude",required = false) String longitude){
        return Rets.success(deliveryService.findAll());
    }
}
