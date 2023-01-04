package com.roy.sqwaimai.service.front;

import com.roy.sqwaimai.bean.entity.front.Food;
import com.roy.sqwaimai.bean.entity.front.Ids;
import com.roy.sqwaimai.bean.entity.front.Shop;
import com.roy.sqwaimai.bean.entity.front.SpecFood;
import com.roy.sqwaimai.bean.vo.business.FoodVo;
import com.roy.sqwaimai.bean.vo.business.SpecVo;
import com.roy.sqwaimai.security.AccountInfo;
import com.roy.sqwaimai.security.JwtUtil;
import com.roy.sqwaimai.service.MongoService;
import com.roy.sqwaimai.utils.*;
import com.roy.sqwaimai.utils.factory.Page;
import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FoodService extends MongoService {

    @Autowired
    private IdsService idsService;

    public void updateFoodRemark(Food food){
        Food old = mongoRepository.findOne(Food.class, "item_id", food.getItem_id());
        old.setState(food.getState());
        old.setAuditRemark(food.getAuditRemark());
        mongoRepository.update(old);
    }

    public Food findOne(Long foodId) {
        return mongoRepository.findOne(Food.class, "item_id", foodId);
    }

    public void updateFood(FoodVo food) {
        List<SpecVo> specVoList = Json.fromJsonAsList(SpecVo.class, food.getSpecsJson());
        List<SpecFood> specList = Lists.newArrayList();
        List specifications = Lists.newArrayList();
//        List<String> specificationValues = Lists.newArrayList();
        for (SpecVo specVo : specVoList) {
            SpecFood specFood = new SpecFood();
            specFood.setName(specVo.getSpecs());
            specFood.setPrice(Double.valueOf(specVo.getPrice()));
            specFood.setPacking_fee(Double.valueOf(specVo.getPacking_fee()));
            specFood.setSpecs_name(specVo.getSpecs());
            specList.add(specFood);

            specifications.add(specFood.getName());
        }
//        specifications.add(Maps.newHashMap("name", "规格", "values", specificationValues));

        Food old = mongoRepository.findOne(Food.class, "item_id", food.getId());
        old.setName(food.getName());
        old.setPinyin_name(StringUtils.getPingYin(food.getName()));
        old.setDescription(food.getDescript());
        old.setCategory_id(food.getIdMenu());
        old.setImage_path(food.getImagePath());
        old.setSpecfoods(specList);
        old.setSpecifications(specifications);

        AccountInfo accountInfo = JwtUtil.getAccountInfo();
        if (!accountInfo.isMgr()) {
            old.setState(Food.STATE_ING);
        } else {
            old.setState(Food.STATE_YES);
        }
        mongoRepository.update(old);
    }

    public void addFood(FoodVo foodVo) {
        Food food = new Food();
        BeanUtil.copyProperties(foodVo, food);
        food.setRestaurant_id(foodVo.getIdShop());
        List<SpecVo> specVoList = Json.fromJsonAsList(SpecVo.class, foodVo.getSpecsJson());
        List<SpecFood> specList = Lists.newArrayList();
        List specifications = Lists.newArrayList();
        for (SpecVo specVo : specVoList) {
            SpecFood specFood = new SpecFood();
            specFood.setName(specVo.getSpecs());
            specFood.setPinyin_name(StringUtils.getPingYin(specVo.getSpecs()));
            specFood.setPrice(Double.valueOf(specVo.getPrice()));
            specFood.setPacking_fee(Double.valueOf(specVo.getPacking_fee()));
            specFood.setSpecs_name(specVo.getSpecs());
            specList.add(specFood);

            specifications.add(specFood.getName());
        }
        food.setSpecifications(specifications);

        List<String> attributes = Json.fromJsonAsList(String.class, foodVo.getAttributesJson());
        List<Map> attributes1 = Lists.newArrayList();
        for (String attribute : attributes) {
            switch (attribute) {
                case "新":
                    attributes1.add(Maps.newHashMap(
                            "icon_color", "5ec452",
                            "icon_name", "新"
                    ));

                    break;
                case "招牌":
                    attributes1.add(Maps.newHashMap(
                            "icon_color", "f07373",
                            "icon_name", "'招牌'"
                    ));
                    break;
            }

        }
        food.setAttributes(attributes1);
        food.setDescription(foodVo.getDescript());
        food.setSpecfoods(specList);
        food.setItem_id(idsService.getId(Ids.ITEM_ID));
        setTips(food);
        food.setPinyin_name(StringUtils.getPingYin(food.getName()));
        food.setSatisfy_rate(0.0);
        food.setSatisfy_count(500.0);
        food.setRating(0.0);
        AccountInfo accountInfo = JwtUtil.getAccountInfo();
        if (!accountInfo.isMgr()) {
            food.setState(Food.STATE_ING);
        } else {
            food.setState(Food.STATE_YES);
        }
        mongoRepository.save(food);
    }

    private void setTips(Food food) {
        Double ratingCount =0.0;
        Double monthSales =0.0;
        food.setRating_count(ratingCount.intValue());
        food.setMonth_sales(monthSales.intValue());
        food.setTips(ratingCount.intValue() + "评价 月售" + monthSales.intValue() + "份");
    }

    public void listPagedFood(Page<Food> page, String state, String name, Long restaurantId) {
        AccountInfo accountInfo = JwtUtil.getAccountInfo();
        Map params = Maps.newHashMap();
        Map<Long, Shop> shopNameMap = Maps.newHashMap();

        if (StringUtils.isNotEmpty(state)) {
            params.put("state", state);
        }
        if (StringUtils.isNotEmpty(name)) {
            params.put("name", name);
        }
        if (Constants.USER_TYPE_MGR.equals(accountInfo.getUserType())) {
            if (StringUtils.isNullOrEmpty(restaurantId) || "undefined".equals(restaurantId)) {
                page = mongoRepository.queryPage(page, Food.class, params);
            } else {
                params.put("restaurant_id", restaurantId);
                page = mongoRepository.queryPage(page, Food.class, params);
            }
        } else if (Constants.USER_TYPE_SHOP.equals(accountInfo.getUserType())) {
            params.put("restaurant_id", accountInfo.getUserId());
            page = mongoRepository.queryPage(page, Food.class, params);
        }
        List<Food> foods = page.getRecords();
        for(int i=0;i<foods.size();i++){
            Shop shop = getShopName(shopNameMap,foods.get(i).getRestaurant_id());
            foods.get(i).setShopName(shop.getName());
            foods.get(i).setShopAddress(shop.getAddress());
        }
    }

    private Shop getShopName(Map<Long, Shop> shopNameMap,Long shopId){
        Shop shop = shopNameMap.get(shopId);
        if(shop==null){
            shop = mongoRepository.findOne(Shop.class,shopId);
            if(shop!=null){
                shopNameMap.put(shopId,shop);
            }
        }
        return shop;
    }
}
