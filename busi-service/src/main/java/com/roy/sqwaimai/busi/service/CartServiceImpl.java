package com.roy.sqwaimai.busi.service;

import com.roy.sqwaimai.core.entity.*;
import com.roy.sqwaimai.core.entity.sub.ExtraFee;
import com.roy.sqwaimai.core.entity.sub.OrderItem;
import com.roy.sqwaimai.core.service.CartService;
import com.roy.sqwaimai.core.util.Lists;
import com.roy.sqwaimai.core.util.Maps;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@DubboService
public class CartServiceImpl extends MongoService implements CartService {

    @Autowired
    private IdsServiceImpl idsService;
    @Autowired
    private PositionServiceImpl positionService;

    public Carts checkOut(String from, Long restaurantId, List<OrderItem> entities) {

        Carts carts = new Carts();
        List<Payment> paymentList = mongoRepository.findAll(Payment.class);
        Shop shop = mongoRepository.findOne(Shop.class, restaurantId);
        String to = shop.getLatitude() + "," + shop.getLongitude();
        Map distance = positionService.getDistance(from, to);
        String deliver_time = distance != null ? distance.get("duration").toString() : "";
        carts.setDelivery_reach_time(deliver_time);
        carts.setId(idsService.getId(Ids.CART_ID));
        carts.setPayments(paymentList);
        carts.setSig(String.valueOf(Math.ceil(Math.random() * 1000000)));
        carts.setInvoice(Maps.newHashMap("status_text", "不需要开发票", "is_available", true));

        CartInfo cartInfo = new CartInfo();
        cartInfo.setId(carts.getId());
        cartInfo.setShop_id(restaurantId);
        cartInfo.setShop_info(shop);
        cartInfo.setDeliver_amount(shop.getFloat_delivery_fee());
        List items = new ArrayList();
        BigDecimal total = new BigDecimal(0);
        List<ExtraFee> extraList = Lists.newArrayList();
        ExtraFee extraFee = new ExtraFee("","",0.00,1,0);
        for (int i = 0; i < entities.size(); i++) {
            OrderItem item = entities.get(i);

            Double amount= item.getPacking_fee() * item.getQuantity();
            if(0!= item.getPacking_fee()){
                extraFee = new ExtraFee(item.getName()+"打包费","打包费" + "-" + item.getSpecs(),amount,1+item.getQuantity(),0);
                //包装费
                total  = total.add(new BigDecimal(extraFee.getPrice()));
            }
            //商品价格
            total = total.add(new BigDecimal(item.getPrice()*item.getQuantity()));
            items.add(item);
        }
        //配送费
        total = total.add(new BigDecimal(cartInfo.getDeliver_amount()));
        extraList.add(extraFee);
        cartInfo.setExtra(extraList);

        cartInfo.setTotal(total.toPlainString());
        cartInfo.setItems(items);
        carts.setCartInfo(cartInfo);

        mongoRepository.save(carts, "carts");
        return carts;
    }
}
