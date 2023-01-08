package com.roy.sqwaimai.app.controller.business;

import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.core.entity.vo.front.Rets;
import com.roy.sqwaimai.core.entity.Carts;
import com.roy.sqwaimai.core.entity.sub.OrderItem;
import com.roy.sqwaimai.core.service.CartService;
import com.roy.sqwaimai.core.service.RemarkService;
import com.roy.sqwaimai.utils.Lists;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/api/carts")
public class CartController extends BaseController {
    @DubboReference
    private CartService cartService;
    @DubboReference
    private RemarkService remarkService;

    //确认订单
    @RequestMapping(value = "/checkorder", method = RequestMethod.POST)
    public Object checkout(HttpServletRequest request) {

        Map data = getRequestPayload(Map.class);
        String from = data.get("geohash").toString();
        Long restaurantId = Long.valueOf(data.get("restaurant_id").toString());
        List<Map> entities = (List<Map>) data.get("entities");
        List<OrderItem> orderEntities = Lists.newArrayList();

        for (Map entity : entities) {
            OrderItem orderItem = new OrderItem();
            orderItem.setId(Long.parseLong(entity.get("id").toString()));
            orderItem.setName(entity.get("name").toString());
            orderItem.setPacking_fee(Double.valueOf(entity.get("packing_fee").toString()));
            orderItem.setPrice(Double.valueOf(entity.get("price").toString()));
            orderItem.setQuantity((Integer)entity.get("quantity"));
            orderItem.setSpecs(entity.get("specs").toString());
            orderItem.setStock((int)entity.get("stock"));

            orderEntities.add(orderItem);
        }

        Carts carts = cartService.checkOut(from,restaurantId,orderEntities);
        return Rets.success(carts);
    }

    /**
     * TODO 备注简化处理
     * @param cartId
     * @param sig
     * @return
     */
    @RequestMapping(value = "/{cart_id}/remarks", method = RequestMethod.GET)
    public Object remarks(@PathVariable("cart_id") Long cartId, @RequestParam(value = "sig", required = false) String sig) {
        return Rets.success(remarkService.findOne());
    }
}
