package com.roy.sqwaimai.busi.service;

import com.roy.sqwaimai.core.entity.Ids;
import com.roy.sqwaimai.core.entity.Order;
import com.roy.sqwaimai.core.entity.Shop;
import com.roy.sqwaimai.core.entity.sys.AccountInfo;
import com.roy.sqwaimai.core.entity.vo.CityInfo;
import com.roy.sqwaimai.core.entity.vo.ShopVo;
import com.roy.sqwaimai.core.query.Page;
import com.roy.sqwaimai.core.service.ShopService;
import com.roy.sqwaimai.core.util.*;
import com.roy.sqwaimai.core.util.gps.Distance;
import org.apache.dubbo.config.annotation.DubboService;
import org.nutz.json.Json;
import org.nutz.lang.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

@Service
@DubboService
public class ShopServiceImpl extends MongoService implements ShopService {
    @Autowired
    private IdsServiceImpl idsService;
    @Autowired
    private PositionServiceImpl positionService;

    public Object findOne(Long id) {
        return mongoRepository.findOne(id, "shops");
    }

    public Page<Shop> adminlistShop(Page<Shop> page, String name, String state,AccountInfo accountInfo) {
        if (Constants.USER_TYPE_SHOP.equals(accountInfo.getUserType())) {
            page = mongoRepository.queryPage(page, Shop.class, Maps.newHashMap("id", accountInfo.getUserId()));
        }else {
            Map<String, Object> params = Maps.newHashMap();
            if (StringUtils.isNotEmpty(name)) {
                params.put("name", name);
            }
            if (StringUtils.isNotEmpty(state)) {
                params.put("state", state);
            }
            page = mongoRepository.queryPage(page, Shop.class, params);
        }
        List<Shop> list = page.getRecords();
        for(Shop shop:list){
            shop.setPassword(null);
        }
        page.setRecords(list);
        return page;
    }

    public Page<Shop> clientListShop(Page<Shop> page, String name, String latitude, String longitude, Long[] categoryIds) {
        Map<String, Object> params = Maps.newHashMap();
        if (StringUtils.isNotEmpty(name)) {
            params.put("name", name);
        }
        params.put("disabled",0);

        if (StringUtils.isEmpty(latitude) || "undefined".equals(latitude)
                || StringUtils.isEmpty(longitude) || "undefined".equals(longitude)) {
            return mongoRepository.queryPage(page, Shop.class, params);
        } else {
            //查询指定经纬度范围内的餐厅
            if (categoryIds != null && categoryIds.length > 0) {
                Map map = (Map) mongoRepository.findOne(categoryIds[0], "categories");
                if (map != null) {
                    params.put("category", map.get("name").toString());
                }
            }
            GeoResults<Map> geoResults = mongoRepository.near(Double.valueOf(longitude), Double.valueOf(latitude), "shops", params);
//            Page<Map> page = new PageFactory<Map>().defaultPage();
            if (geoResults != null) {
                List<GeoResult<Map>> geoResultList = geoResults.getContent();
                List list = Lists.newArrayList();
                for (int i = 0; i < geoResultList.size(); i++) {
                    Map map = geoResultList.get(i).getContent();

                    Distance distance = new Distance(Double.valueOf(longitude), Double.valueOf(latitude),
                            Double.valueOf(map.get("longitude").toString()), Double.valueOf(map.get("latitude").toString()));
                    map.put("distance", distance.getDistance().intValue());

                    map.put("order_lead_time", "30分钟");
                    list.add(map);
                }

                page.setTotal(list.size());
                page.setRecords(list);
            }
            return page;
        }
    }

    public long countShop() {
        return mongoRepository.count(Shop.class);
    }

    public void deleteShop(Long id) {
        mongoRepository.delete(id, "shops");
    }

    public void auditShop(Shop shop) {
        Map<String, Object> updateMap = new HashMap<>(4);
        updateMap.put("auditRemark", shop.getAuditRemark());
        updateMap.put("state", shop.getState());
        mongoRepository.update(shop.getId(), "shops", updateMap);
    }

    public void stopShop(Shop shop) {
        Map<String, Object> updateMap = new HashMap<>(2);
        updateMap.put("disabled", shop.getDisabled());
        mongoRepository.update(shop.getId(), "shops", updateMap);
    }

    public void updateShop(Shop shop,AccountInfo accountInfo) {
//        AccountInfo accountInfo = JwtUtil.getAccountInfo();

        Map<String, Object> updateMap = new HashMap<>(16);
        updateMap.put("name", Strings.sNull(shop.getName()));
        updateMap.put("address", Strings.sNull(shop.getAddress()));
        updateMap.put("description", Strings.sNull(shop.getDescription()));
        updateMap.put("category", Strings.sNull(shop.getCategory()));
        updateMap.put("phone", Strings.sNull(shop.getPhone()));
        updateMap.put("rating", Double.valueOf(shop.getRating()));
        updateMap.put("recent_order_num", shop.getRecent_order_num());
        updateMap.put("image_path", shop.getImage_path());
        updateMap.put("platform_rate",shop.getPlatform_rate());

        if(Constants.USER_TYPE_SHOP.equals(accountInfo.getUserType())){
            //商户自己修改需要审核
            updateMap.put("state", Shop.STATE_ING);
        }else{
            //管理员修改直接审核通过
            updateMap.put("state", Shop.STATE_YES);
        }
        mongoRepository.update(shop.getId(), "shops", updateMap);
    }

    public Shop addShop(ShopVo shopVo, String ip) {
        Shop shop = new Shop();
        BeanUtils.copyProperties(shopVo, shop);
        shop.setId(idsService.getId(Ids.RESTAURANT_ID));
        shop.setDisabled(1);
        List activities = Json.fromJson(List.class, shopVo.getActivitiesJson());
        int index = 0;
        for (int i = 0; i < activities.size(); i++) {
            Map activity = (Map) activities.get(i);
            String iconName = activity.get("icon_name").toString();
            switch (iconName) {
                case "减":
                    activity.put("icon_color", "f07373");
                    activity.put("id", index++);
                    break;
                case "特":
                    activity.put("icon_color", "EDC123");
                    activity.put("id", index++);
                    break;
                case "新":
                    activity.put("icon_color ", "70bc46");
                    activity.put("id", index++);
                    break;
                case "领":
                    activity.put("icon_color ", "E3EE0D");
                    activity.put("id ", index++);
                    break;
                default:
                    break;
            }
        }
        shop.setActivities(activities);

        List<Map> supports = new ArrayList<Map>(4);
        if (shopVo.getBao()) {
            supports.add(buildSupport("已加入“外卖保”计划，食品安全有保障", "999999", "保", 7, "外卖保"));
        }
        if (shopVo.getZhun()) {
            supports.add(buildSupport("准时必达，超时秒赔", "57A9FF", "准", 9, "准时达"));
        }
        if (shopVo.getPiao()) {
            supports.add(buildSupport("该商家支持开发票，请在下单时填写好发票抬头", "999999", "票", 4, "开发票"));
        }
        shop.setSupports(supports);
        shop.setIs_new(shopVo.getNews());

        if (shopVo.getDeliveryMode()) {
            Map<String, Object> deliveryMode = Maps.newHashMap("color", "57A9FF", "id", 1, "is_solid", true, "text", "蜂鸟专送");
            shop.setDelivery_mode(deliveryMode);
        }
        Map<String, String> tips = new HashMap<String, String>(2);
        tips.put("tips", "配送费约￥" + shopVo.getFloat_delivery_fee());
        shop.setPiecewise_agent_fee(tips);
        List<String> openingHours = new ArrayList<String>();
        if (Strings.isNotBlank(shopVo.getStartTime()) &&
                Strings.isNotBlank(shopVo.getEndTime())) {
            openingHours.add(shopVo.getStartTime() + "/" + shopVo.getEndTime());
        } else {
            openingHours.add("08:30/20:30");
        }

        shop.setOpening_hours(openingHours);

        Map<String, String> license = new HashMap<>();
        if (Strings.isNotBlank(Strings.sNull(shopVo.getBusiness_license_image()))) {
            license.put("business_license_image", shopVo.getBusiness_license_image());
        }
        if (Strings.isNotBlank(shopVo.getCatering_service_license_image())) {
            license.put("catering_service_license_image", shopVo.getCatering_service_license_image());
        }
        shop.setLicense(license);
        Map<String, String> identification = Maps.newHashMap("company_name", "",
                "identificate_agency", "",
                "identificate_date", "",
                "legal_person", "",
                "licenses_date", "",
                "licenses_number", "",
                "licenses_scope", "",
                "operation_period", "",
                "registered_address", "",
                "registered_number", "");
        shop.setIdentification(identification);


        CityInfo cityInfo = positionService.getPostion(ip);
        if (cityInfo != null) {
            shop.setLatitude(Double.valueOf(cityInfo.getLat()));
            shop.setLongitude(Double.valueOf(cityInfo.getLng()));
            List locations = new LinkedList();
            locations.add(Double.valueOf(cityInfo.getLng()));
            locations.add(Double.valueOf(cityInfo.getLat()));
            shop.setLocation(locations);
        }
        shop.setPassword("123456");
        shop.setState(Shop.STATE_YES);
        shop.setUnliquidatedAmount("0");
        shop.setTotalAmount("0");
        mongoRepository.save(shop);
        return shop;
    }

    private Map<String, Object> buildSupport(String description, String iconColor, String iconName, Integer id, String name) {
        Map<String, Object> map = new HashMap<String, Object>(8);
        map.put("description", description);
        map.put("icon_color", iconColor);
        map.put("icon_name", iconName);
        map.put("id", id);
        map.put("name", name);
        return map;
    }

    public List<Map> searchNearShop(String geoHash, String keyWord) {
        String[] geoHashArr = geoHash.split(",");
        String longitude = geoHashArr[1];
        String latitude = geoHashArr[0];
        Map<String, Object> params = Maps.newHashMap("name", keyWord);
        GeoResults<Map> geoResults = mongoRepository.near(Double.valueOf(longitude), Double.valueOf(latitude),
                "shops", params);
        List<GeoResult<Map>> geoResultList = geoResults.getContent();
        List<Map> list = Lists.newArrayList();
        for (int i = 0; i < geoResultList.size(); i++) {
            Map map = geoResultList.get(i).getContent();
            Distance distance = new Distance(Double.valueOf(longitude), Double.valueOf(latitude),
                    Double.valueOf(map.get("longitude").toString()), Double.valueOf(map.get("latitude").toString()));
            map.put("distance", distance.getDistance());
            list.add(map);
        }
        return list;
    }
    //返回平台抽成。
    public BigDecimal checnOutAvaiable(Long id) {
        Shop shop = mongoRepository.findOne(Shop.class,id);
        String unliquidatedAmount = shop.getUnliquidatedAmount();
        String totalAmount = shop.getTotalAmount();
        if(StringUtils.isEmpty(totalAmount)){
            totalAmount = "0";
        }
        //平台抽成
        String platformAmount =  new BigDecimal(unliquidatedAmount)
                .multiply(new BigDecimal(shop.getPlatform_rate())).divide(new BigDecimal(100),2, BigDecimal.ROUND_HALF_UP).toPlainString();
        String shopAmount = new BigDecimal(unliquidatedAmount).subtract(new BigDecimal(platformAmount)).toPlainString();
        totalAmount = new BigDecimal(totalAmount).add(new BigDecimal(shopAmount)).toPlainString()+"";
        shop.setTotalAmount(totalAmount);
        shop.setUnliquidatedAmount("0");
        mongoRepository.update(shop);
        return new BigDecimal(platformAmount);
    }

    public void addAvailable(Order order) {
        Shop shop = mongoRepository.findOne(Shop.class,order.getRestaurant_id());
        Map updateMap = Maps.newHashMap();
        String unliquidatedAmount = shop.getUnliquidatedAmount();
        if(StringUtils.isEmpty(unliquidatedAmount)){
            unliquidatedAmount = "0";
        }
        unliquidatedAmount = new BigDecimal(unliquidatedAmount).add(new BigDecimal(order.getTotal_amount())).intValue()+"";
        updateMap.put("unliquidatedAmount",unliquidatedAmount);
        mongoRepository.update(shop.getId(),"shops",updateMap);
    }

    @Override
    public Shop findOneByName(String username, String password) {
        return mongoRepository.findOne(Shop.class, Maps.newHashMap("name", username, "password", password));
    }

    @Override
    public void updateShopPassword(Long shopId, String password) {
        mongoRepository.update(shopId,"shops",Maps.newHashMap("password",password));
    }
}
