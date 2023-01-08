package com.roy.sqwaimai.busi.service;

import com.alibaba.fastjson.JSONArray;
import com.roy.sqwaimai.core.entity.Food;
import com.roy.sqwaimai.core.entity.Ids;
import com.roy.sqwaimai.core.entity.Shop;
import com.roy.sqwaimai.core.entity.SpecFood;
import com.roy.sqwaimai.core.entity.sys.AccountInfo;
import com.roy.sqwaimai.core.entity.vo.FoodVo;
import com.roy.sqwaimai.core.entity.vo.SpecVo;
import com.roy.sqwaimai.core.query.Page;
import com.roy.sqwaimai.core.service.FoodService;
import com.roy.sqwaimai.core.util.Constants;
import com.roy.sqwaimai.core.util.Lists;
import com.roy.sqwaimai.core.util.Maps;
import com.roy.sqwaimai.core.util.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@Service
@DubboService
public class FoodServiceImpl extends MongoService implements FoodService {

    @Autowired
    private IdsServiceImpl idsService;

    public void updateFoodRemark(Food food){
        Food old = mongoRepository.findOne(Food.class, "item_id", food.getItem_id());
        old.setState(food.getState());
        old.setAuditRemark(food.getAuditRemark());
        mongoRepository.update(old);
    }

    public Food findOne(Long foodId) {
        return mongoRepository.findOne(Food.class, "item_id", foodId);
    }

    public void updateFood(FoodVo food,AccountInfo accountInfo) {
        List<SpecVo> specVoList = JSONArray.parseArray(food.getSpecsJson(), SpecVo.class);
        List<SpecFood> specList = Lists.newArrayList();
        List specifications = Lists.newArrayList();
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

        if (!accountInfo.isMgr()) {
            old.setState(Food.STATE_ING);
        } else {
            old.setState(Food.STATE_YES);
        }
        mongoRepository.update(old);
    }

    public void addFood(FoodVo foodVo, AccountInfo accountInfo) {
        Food food = new Food();
        try {
            BeanUtils.copyProperties(food,foodVo);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        food.setRestaurant_id(foodVo.getIdShop());
        List<SpecVo> specVoList = JSONArray.parseArray(foodVo.getSpecsJson(),SpecVo.class);
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

        //TODO: 这个attrbute是干嘛的？
        List<String> attributes = JSONArray.parseArray(foodVo.getAttributesJson(), String.class);
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

    public void listPagedFood(Page<Food> page, String state, String name, Long restaurantId,AccountInfo accountInfo) {
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
