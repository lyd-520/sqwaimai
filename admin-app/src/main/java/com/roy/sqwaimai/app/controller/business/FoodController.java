package com.roy.sqwaimai.app.controller.business;

import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.core.entity.vo.front.Rets;
import com.roy.sqwaimai.core.entity.Food;
import com.roy.sqwaimai.core.entity.sys.AccountInfo;
import com.roy.sqwaimai.core.entity.vo.FoodVo;
import com.roy.sqwaimai.core.query.Page;
import com.roy.sqwaimai.core.service.FoodService;
import com.roy.sqwaimai.core.service.FrontMenuService;
import com.roy.sqwaimai.security.JwtUtil;
import com.roy.sqwaimai.utils.PageFactory;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/food")
public class FoodController extends BaseController {

    @DubboReference
    private FoodService foodService;

    @DubboReference
    private FrontMenuService frontMenuService;

    private Logger logger = LoggerFactory.getLogger(FoodController.class);

    @RequestMapping(value = "/addfood", method = RequestMethod.POST)
    public Object add(@Valid @ModelAttribute FoodVo foodVo) {
        AccountInfo accountInfo = JwtUtil.getAccountInfo();
        foodService.addFood(foodVo,accountInfo);
        return Rets.success();
    }

    @RequestMapping(value = "/listfoods", method = RequestMethod.GET)
    public Object list(@RequestParam(value = "state", required = false) String state,
                       @RequestParam(value = "name", required = false) String name,
                       @RequestParam(value = "restaurant_id", required = false) Long restaurantId) {
        Page<Food> page = new PageFactory<Food>().defaultPage();
        AccountInfo accountInfo = JwtUtil.getAccountInfo();
        foodService.listPagedFood(page,state,name,restaurantId,accountInfo);
        return Rets.success(page);
    }

    @GetMapping(value = "/queryfood/{item_id}")
    public Object get(@PathVariable("item_id") Long id) {
        Food food = foodService.findOne(id);
        return Rets.success(food);
    }

    @RequestMapping(value = "/updatefood", method = RequestMethod.POST)
    public Object update(@ModelAttribute @Valid FoodVo food) {
        AccountInfo accountInfo = JwtUtil.getAccountInfo();
        //更新食品信息
        foodService.updateFood(food,accountInfo);
        //更新菜单 修改食品时删除菜单，食品审批通过时添加菜单。
        frontMenuService.deleteMenuFood(food.getId());
        return Rets.success();
    }

    /**
     * 审核食品，通过，则加入菜单
     * @param food
     * @return
     */
    @RequestMapping(value = "/auditFood", method = RequestMethod.POST)
    public Object auditFood(@ModelAttribute Food food) {
        //更新食品审批描述
        foodService.updateFoodRemark(food);
        if (Food.STATE_YES.equals(food.getState())) {
            //更新菜单
            frontMenuService.updateMenuFood(food.getItem_id());
        }
        return Rets.success();
    }
}
