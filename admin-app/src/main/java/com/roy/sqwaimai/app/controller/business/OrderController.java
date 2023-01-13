package com.roy.sqwaimai.app.controller.business;

import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.core.entity.vo.front.Rets;
import com.roy.sqwaimai.core.entity.FrontRiderInfo;
import com.roy.sqwaimai.core.entity.Order;
import com.roy.sqwaimai.core.entity.sys.AccountInfo;
import com.roy.sqwaimai.core.entity.vo.OrderVo;
import com.roy.sqwaimai.core.query.Page;
import com.roy.sqwaimai.core.service.FrontRiderService;
import com.roy.sqwaimai.core.service.OrderService;
import com.roy.sqwaimai.core.service.ShopService;
import com.roy.sqwaimai.security.JwtUtil;
import com.roy.sqwaimai.utils.HttpKit;
import com.roy.sqwaimai.utils.PageFactory;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.Semaphore;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderController extends BaseController {
    @DubboReference
    private OrderService orderService;
    @DubboReference
    private ShopService shopService;
    @DubboReference
    private FrontRiderService frontRiderService;

    @RequestMapping(value = "/userorders/{user_id}", method = RequestMethod.GET)
    public Object orders(@PathVariable("user_id") Long userId,@RequestParam(value = "orderStatus",required = false) int orderStatus) {
        Page<Order> page = new PageFactory<Order>().defaultPage();
        page = orderService.queryPageUserOrder(page,userId,orderStatus);

        return Rets.success(page);
    }

    @RequestMapping(value = "/getlist", method = RequestMethod.GET)
    public Object list(@PathVariable(value = "restaurant_id", required = false) Long restaurantId,
                       @PathVariable(value = "id", required = false) Long orderId) {
        Page<Order> page = new PageFactory<Order>().defaultPage();
        AccountInfo accountInfo = JwtUtil.getAccountInfo();
        page = orderService.queryPageOrder(page,restaurantId,orderId,accountInfo);
        return Rets.success(page);
    }

    @RequestMapping(value = "/getOrder",method = RequestMethod.GET)
    public Object getOrder(
            @RequestParam(value = "orderid", required = false) Long orderid) {
        Map orderInfo = orderService.getFullOrderInfo(orderid);
        return Rets.success(orderInfo);
    }

    @RequestMapping(value = "/getOrderById",method = RequestMethod.GET)
    public Object riderGetOrder(
            @RequestParam(value = "orderid", required = false) Long orderid) {
        Order order = orderService.getOrder(orderid);
        return Rets.success(order);
    }
    //骑手抢单。并发设计，防止多个骑手抢同一个单
    @RequestMapping(value = "/ridercheckOrder", method = RequestMethod.POST)
    public Object checkOrder(@RequestParam("userid") long userid,
                             @RequestParam("orderid") long orderid){
        Object result = frontRiderService.checkOrder(orderid, userid);
        if(result instanceof FrontRiderInfo){
            return Rets.success(result);
        }
        return result;
    }

    //订单送达
    @RequestMapping(value= "/ridersendorder",method = RequestMethod.POST)
    public Object sendOrder(@RequestParam("userid") long userid,
                            @RequestParam("orderid") long orderid){
        //更新订单  订单状态，送达时间
        Order order = orderService.sendOrder(orderid);
        //更新骑手 更新派送订单 更新结算
        FrontRiderInfo frontRiderInfo = frontRiderService.orderSended(order,userid);
        return Rets.success(frontRiderInfo);
    }

    /**
     * 修改订单状态
     * @param orderId
     * @param status
     * @return
     */
    @RequestMapping(value = "/updateOrderStatus", method = RequestMethod.POST)
    public Object updateOrderStatus(
            @RequestParam(value = "id", required = false) Long orderId,
            @RequestParam(value = "status", required = false) Integer status) {
        Order order = orderService.updateOrderStatus(orderId,status);
        return Rets.success(order);
    }

    /**
     * 手机端用户确认完成订单
     * 将当前订单金额加入到商户未结算金额
     * @param userId
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/finishuserorder/{user_id}/{orderId}", method = RequestMethod.GET)
    public Object finishorder(@PathVariable("user_id") Long userId,
                              @PathVariable("orderId") Long orderId) {
//        Order order = mongoRepository.findOne(Order.class, orderId);
        Order order = orderService.getOrder(orderId);
        if(order.getUser_id().intValue()!=userId.intValue()){
            return Rets.failure("无权操作该订单");
        }
        orderService.finishOrder(order);
        //订单金额加入到商铺未结算金额
        shopService.addAvailable(order);
        return Rets.success(order);
    }

    /**
     * 取消订单
     * 1，订单设置为取消状态
     * @param userId
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/canceluserorder/{user_id}/{orderId}", method = RequestMethod.GET)
    public Object cancelOrder(@PathVariable("user_id") Long userId,
                              @PathVariable("orderId") Long orderId) {
//        Order order = mongoRepository.findOne(Order.class, orderId);
        Order order = orderService.getOrder(orderId);
        if(order.getUser_id().intValue()!=userId.intValue()){
            return Rets.failure("无权操作该订单");
        }
        orderService.cancelOrder(order);
        return Rets.success(order);
    }

    @RequestMapping(value = "/snapshotuserorder/{user_id}/{orderId}", method = RequestMethod.GET)
    public Object snapshotOrder(@PathVariable("user_id") Long userId,
                              @PathVariable("orderId") Long orderId) {
//        Order order = mongoRepository.findOne(Order.class, orderId);
        Order order = orderService.getOrder(orderId);
        if(order.getUser_id().intValue()!=userId.intValue()){
            return Rets.failure("无权操作该订单");
        }
        return Rets.success(order);
    }

    @PostMapping(value = "/cartorder/{userId}/{cartId}")
    public Object save(@PathVariable("userId") Long userId, @PathVariable("cartId") Long cartId) {
        OrderVo orderVo = getRequestPayload(OrderVo.class);
        //从购物车生成订单
        Order order = orderService.generateOrder(userId,cartId,orderVo);
        return Rets.success(order);
    }
}
