package com.roy.sqwaimai.app.controller.business;

import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.bean.vo.front.Rets;
import com.roy.sqwaimai.dao.MongoRepository;
import com.roy.sqwaimai.service.front.PaymentService;
import com.roy.sqwaimai.utils.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

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
