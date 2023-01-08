package com.roy.sqwaimai.app.controller.business;

import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.core.entity.vo.front.Rets;
import com.roy.sqwaimai.core.service.PaymentService;
import com.roy.sqwaimai.utils.Maps;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payapi/payment")
public class PaymentController extends BaseController {

    @DubboReference
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
    @RequestMapping(value = "/payOrder",method = RequestMethod.POST)
    public Object payOrder(@RequestBody Map<String,String> params){
        String merchantOrderNo = params.get("merchantOrderNo");
        if(paymentService.payOrder(merchantOrderNo)){
            return Rets.success("支付完成");
        }else{
            return Rets.success("支付异常，请联系管理员");
        }
    }
}
