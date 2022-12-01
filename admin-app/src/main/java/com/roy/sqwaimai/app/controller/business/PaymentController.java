package com.roy.sqwaimai.app.controller.business;

import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.bean.vo.front.Rets;
import com.roy.sqwaimai.dao.MongoRepository;
import com.roy.sqwaimai.service.front.PaymentService;
import com.roy.sqwaimai.utils.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ：microapp.store
 * @date ：Created in 2019/10/24 15:58
 */
@RestController
@RequestMapping("/payapi/payment")
public class PaymentController extends BaseController {

    @Resource
    private PaymentService paymentService;

    @RequestMapping(value = "/queryOrder",method = RequestMethod.GET)
    public Object queryOrder(@RequestParam("merchantId") Long merchantId,
                             @RequestParam("merchantOrderNo") String merchantOrderNo,
                             @RequestParam("source") String source,
                             @RequestParam("userId") Long userId,
                             @RequestParam("version") String version){
        return Rets.success(
                Maps.newHashMap(
                        "status", 0,
                        "type","PAY_FAILED",
                        "message","暂不开放支付功能"
                )
        );
    }
    @RequestMapping(value = "/queryOrder",method = RequestMethod.POST)
    public Object payOrder(@RequestParam("merchantOrderNo") String merchantOrderNo){
        if(paymentService.payOrder(merchantOrderNo)){
            return Rets.success("支付完成");
        }else{
            return Rets.success("支付异常，请联系管理员");
        }
    }
}
