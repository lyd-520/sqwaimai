package com.roy.sqwaimai.service.front;

import com.roy.sqwaimai.bean.constant.factory.PageFactory;
import com.roy.sqwaimai.bean.entity.front.*;
import com.roy.sqwaimai.bean.entity.front.sub.*;
import com.roy.sqwaimai.bean.vo.business.OrderVo;
import com.roy.sqwaimai.security.AccountInfo;
import com.roy.sqwaimai.security.JwtUtil;
import com.roy.sqwaimai.service.MongoService;
import com.roy.sqwaimai.service.system.CfgService;
import com.roy.sqwaimai.utils.Constants;
import com.roy.sqwaimai.utils.DateUtil;
import com.roy.sqwaimai.utils.Lists;
import com.roy.sqwaimai.utils.Maps;
import com.roy.sqwaimai.utils.factory.Page;
import com.roy.sqwaimai.utils.gps.Distance;
import org.nutz.mapl.Mapl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService extends MongoService {
    @Autowired
    private IdsService idsService;
    @Resource
    private CfgService cfgService;

    public Page<Order> queryPageUserOrder(Page<Order> page, Long userId,int orderStatus) {
        HashMap<String, Object> params = Maps.newHashMap("user_id", userId);
        if(0 < orderStatus){
            params.put("status_code",orderStatus);
        }
        return mongoRepository.queryPage(page, Order.class, params);
    }

    public Page<Order> queryPageOrder(Page<Order> page, Long restaurantId, Long orderId) {
        Map<String, Object> params = Maps.newHashMap();
        AccountInfo accountInfo = JwtUtil.getAccountInfo();
        if (Constants.USER_TYPE_MGR.equals(accountInfo.getUserType())) {
            if (restaurantId != null) {
                params.put("restaurant_id", restaurantId);
            }
            if (orderId != null) {
                params.put("id", orderId);
            }
        } else if (Constants.USER_TYPE_SHOP.equals(accountInfo.getUserType())) {
            params.put("restaurant_id", accountInfo.getUserId());
        }
        return mongoRepository.queryPage(page, Order.class, params);
    }

    public Map getFullOrderInfo(Long orderid) {
        Order order = mongoRepository.findOne(Order.class, orderid);
        OrderBasket basket = order.getBasket();
        List<OrderItem> items = basket.getItems();
        List<Map> orderItems = Lists.newArrayList();
        for(OrderItem orderItem:items){
            Map item = Maps.newHashMap();
            Food food = mongoRepository.findOne(Food.class,Maps.newHashMap("item_id",orderItem.getId()));
            if(food!=null) {
                item.put("foodName",  orderItem.getName() );
                item.put("image_path", food.getImage_path());
            }else{
                item.put("foodName",orderItem.getName());
            }
            item.put("price",orderItem.getPrice());
            item.put("quantity",orderItem.getQuantity());
            item.put("spec",orderItem.getName());
            item.put("packingFee",orderItem.getPacking_fee()==null?0:orderItem.getPacking_fee());
            item.put("totalPrice",orderItem.getPrice()*orderItem.getQuantity()+(orderItem.getPacking_fee()==null?0:orderItem.getPacking_fee()));
            orderItems.add(item);
        }
        Address address = mongoRepository.findOne(Address.class,order.getAddress_id());
        FrontUser user = mongoRepository.findOne(FrontUser.class,Maps.newHashMap("user_id",order.getUser_id()));
        return Maps.newHashMap(
                "order",order,
                "address",address,
                "user",user,
                "orderItems",orderItems
        );
    }

    public Order getOrder(Long orderid) {
        return mongoRepository.findOne(Order.class, orderid);
    }

    public Order updateOrderStatus(Long orderId, Integer status) {
        Order order = mongoRepository.findOne(Order.class, orderId);
        order.setStatus_code(status);
        order.setStatus_title(Order.getStatusCodeStr(status));
        mongoRepository.update(order);
        return order;
    }

    public void cancelOrder(Order order) {
        order.setStatus_code(Order.STATUS_CANCEL);
        order.setStatus_title(Order.getStatusCodeStr(Order.STATUS_CANCEL));
        mongoRepository.update(order);
    }

    public void finishOrder(Order order) {
        order.setStatus_code(Order.STATUS_FINISHED);
        order.setStatus_title(Order.getStatusCodeStr(Order.STATUS_FINISHED));
        mongoRepository.update(order);
    }

    public Order sendOrder(long orderid) {
        Order order = mongoRepository.findOne(Order.class, "id", orderid);
        order.setStatus_code(Order.STATUS_DELIVERED);
        order.setStatus_title(Order.getStatusCodeStr(Order.STATUS_DELIVERED));
        Date now = new Date();
        order.setDeliver_time(now.getTime());
        order.setFormatted_deliver_at(DateUtil.format(now, "yyyy-MM-dd HH:mm"));
        mongoRepository.update(order);
        return order;
    }

    public Order generateOrder(Long userId, Long cartId, OrderVo orderVo) {
        //获取购物车信息
        Carts cart = mongoRepository.findOne(Carts.class, cartId);
        Date createTime = new Date();
        Shop shop = mongoRepository.findOne(Shop.class, cart.getCartInfo().getShop_id());
        Order order = new Order();
        order.setId(idsService.getId(Ids.ORDER_ID));
        order.setRestaurant_id(shop.getId());
        order.setRestaurant_name(shop.getName());
        order.setRestaurant_image_url(shop.getImage_path());
        OrderShopAddress orderShopAddress = new OrderShopAddress();
        orderShopAddress.setId(shop.getId());
        orderShopAddress.setName(shop.getName());
        orderShopAddress.setImage_url(shop.getImage_path());
        orderShopAddress.setLatitude(shop.getLatitude());
        orderShopAddress.setLongitude(shop.getLongitude());
        order.setOrderShopAddress(orderShopAddress);

        order.setFormatted_create_at(DateUtil.format(createTime, "yyyy-MM-dd HH:mm"));
        order.setOrder_time(createTime.getTime());
        order.setTime_pass(900);
        OrderBasket basket = order.getBasket();
        basket.setItems(cart.getCartInfo().getItems());
        if (!cart.getCartInfo().getExtra().isEmpty()) {
//            OrderFee orderFee = (OrderFee) Mapl.maplistToObj(cart.getCartInfo().getExtra().get(0), OrderFee.class);
//            OrderFee orderFee = cart.getCartInfo().getExtra().get(0);
//            ExtraFee extraFee = cart.getCartInfo().getExtra().get(0);

//            orderFee.setDelevery(cart.getCartInfo().getDeliver_amount());

            OrderFee orderFee = basket.getOrderFee();
            orderFee.setDelevery(cart.getCartInfo().getDeliver_amount());
            basket.setOrderFee(orderFee);
//            basket.setDeliver_fee(cart.getCart().getDeliver_amount());
        }
        order.setBasket(basket);
        order.setStatus_code(Order.STATUS_INIT);
        order.setStatus_title(Order.getStatusCodeStr(Order.STATUS_INIT));
        order.setTotal_amount(Double.valueOf(cart.getCartInfo().getTotal()).intValue());
        order.setTotal_quantity(basket.getItems().size());
        order.setUnique_id(order.getId());
        order.setUser_id(userId);

        order.setAddress_id(Long.valueOf(orderVo.getAddress_id()));
        Address address = mongoRepository.findOne(Address.class,"id", Long.valueOf(orderVo.getAddress_id()));
        OrderAddress orderAddress = new OrderAddress();
        orderAddress.setId( address.getId());
        orderAddress.setAddress(address.getAddress());
        orderAddress.setPhone(address.getPhone());
        orderAddress.setBakupphone(address.getPhone_bk());
        orderAddress.setName(address.getName());
        orderAddress.setSt_geohash(address.getSt_geohash());
        orderAddress.setUser_id(address.getUser_id());
        orderAddress.setCity_id(address.getCity_id());
        orderAddress.setSex(address.getSex());
        order.setOrder_address(orderAddress);
        mongoRepository.save(order);
        return order;
    }

    public Object queryPagePaidOrder(Page<Order> page) {
        Map<String,Object> orderqueryParam = Maps.newHashMap();
        orderqueryParam.put("status_code",Order.STATUS_PAID);
        return mongoRepository.queryPage(page,Order.class,orderqueryParam);
    }

    public Page queryNearByOrder(Double longitude, Double latitude, int limit) {
        Page page = new PageFactory().rawPage();
        List<Map> queryOrders = Lists.newArrayList();
        //传了指定位置，先找附近的商店，作为订单的起点
        // 查找附近的商店
        String cfgrange = cfgService.getCfgValue("system.search.range");
        GeoResults<Map> geoResults = mongoRepository.near(Double.valueOf(longitude), Double.valueOf(latitude), "shops", Maps.newHashMap(),Integer.parseInt(cfgrange));
        //从这些商店中找出十条订单
        if (geoResults != null) {
            List<GeoResult<Map>> geoResultList = geoResults.getContent();
            for (int i = 0; i < geoResultList.size(); i++) {

                Map shop = geoResultList.get(i).getContent();
                Distance distance = new Distance(Double.valueOf(longitude), Double.valueOf(latitude),
                        Double.valueOf(shop.get("longitude").toString()), Double.valueOf(shop.get("latitude").toString()));
                List<Map> orders = mongoRepository.findAll("orders", "status_code",Order.STATUS_PAID,"restaurant_id",shop.get("id"));
                for (Map order : orders) {
                    order.put("distance", distance.getDistance().intValue());
                }
                queryOrders.addAll(orders);
                //最多只展示10条订单
                if(queryOrders.size()>=limit){
                    break;
                }
            }
        }
        page.setTotal(queryOrders.size());
        page.setRecords(queryOrders);
        return page;
    }
}
