package com.roy.sqwaimai.service.front;

import com.roy.sqwaimai.bean.entity.front.Food;
import com.roy.sqwaimai.bean.entity.front.Ids;
import com.roy.sqwaimai.bean.entity.front.Menu;
import com.roy.sqwaimai.service.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class FrontMenuService extends MongoService {

    @Resource
    private FoodService foodService;
    @Autowired
    private IdsService idsService;

    public void updateMenuFood(Long foodId){
        Food food = foodService.findOne(foodId);
        Menu menu = mongoRepository.findOne(Menu.class, food.getCategory_id());
        //Menu菜单在添加食品时添加菜单时就已经维护了进去。这里正常不会出现menu为空的情况。
//        if(null != menu){
            List<Food> foods = menu.getFoods();
            for (Food rec : foods) {
                if (rec.getItem_id().intValue() == food.getItem_id().intValue()) {
                    foods.remove(rec);
                    break;
                }
            }
            menu.getFoods().add(food);
            mongoRepository.update(menu);
//        }else{
//            Menu newMenu = new Menu();
//            mongoRepository.save(newMenu);
//        }
    }

    public void deleteMenuFood(Long foodId) {
        Food oldfood = foodService.findOne(foodId);
        Menu menu = mongoRepository.findOne(Menu.class, oldfood.getCategory_id());
        List<Food> foods = menu.getFoods();
        for (Food rec : foods) {
            if (rec.getItem_id().intValue() == oldfood.getItem_id().intValue()) {
                foods.remove(rec);
                break;
            }
        }
        mongoRepository.update(menu);
    }

    public List<Map> listShopMenu(Long restaurantId) {
        return mongoRepository.findAll("menus", "restaurant_id", restaurantId);
    }

    public Object getMenuById(Long menuId) {
        return mongoRepository.findOne(menuId, "menus");
    }

    public void saveInitMenu(Menu menu) {
        menu.setId(idsService.getId(Ids.CATEGORY_ID));
        //进行处理后保存
        mongoRepository.save(menu);
    }
}
