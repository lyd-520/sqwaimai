package com.roy.sqwaimai.app.controller.business;

import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.bean.constant.factory.PageFactory;
import com.roy.sqwaimai.bean.entity.front.Ids;
import com.roy.sqwaimai.bean.entity.front.Menu;
import com.roy.sqwaimai.bean.entity.front.Ratings;
import com.roy.sqwaimai.bean.entity.front.Shop;
import com.roy.sqwaimai.bean.entity.system.Cfg;
import com.roy.sqwaimai.bean.enumeration.ConfigKeyEnum;
import com.roy.sqwaimai.bean.vo.business.CityInfo;
import com.roy.sqwaimai.bean.vo.business.ShopVo;
import com.roy.sqwaimai.bean.vo.front.Rets;
import com.roy.sqwaimai.dao.MongoRepository;
import com.roy.sqwaimai.security.AccountInfo;
import com.roy.sqwaimai.security.JwtUtil;
import com.roy.sqwaimai.service.front.*;
import com.roy.sqwaimai.service.system.CfgService;
import com.roy.sqwaimai.utils.*;
import com.roy.sqwaimai.utils.factory.Page;
import com.roy.sqwaimai.utils.gps.Distance;
import org.nutz.json.Json;
import org.nutz.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/shop")
public class ShopController extends BaseController {
    @Resource
    private ShopService shopService;
    @Resource
    private RatingService ratingService;
    @Resource
    private FrontMenuService frontMenuService;
    @Resource
    private CategoryService categoryService;

    @RequestMapping(value = "/queryshop/{id}", method = RequestMethod.GET)
    public Object getShop(@PathVariable("id") Long id) {
        return Rets.success( shopService.findOne(id));
    }

    /**
     * 后台管理查询商户列表
     *
     * @param name        商铺名称
     * @param state       审核状态
     * @param categoryIds
     * @return
     */
    @RequestMapping(value = "/listShop", method = RequestMethod.GET)
    public Object listShop(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "restaurant_category_ids[]", required = false) Long[] categoryIds) {
        Page<Shop> page = new PageFactory<Shop>().defaultPage();
        shopService.adminlistShop(page,name,state);
        return Rets.success(page);
    }

    /**
     * 用户端查询商铺列表
     *
     * @param latitude
     * @param longitude
     * @param name        商铺名称
     * @param categoryIds
     * @return
     */
    @RequestMapping(value = "/restaurants", method = RequestMethod.GET)
    public Object restaurants(@RequestParam(value = "latitude", required = false) String latitude,
                              @RequestParam(value = "longitude", required = false) String longitude,
                              @RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "restaurant_category_ids", required = false) Long[] categoryIds) {
        Page page = new PageFactory().defaultPage();
        shopService.clientListShop(page,name,latitude,longitude,categoryIds);
        return Rets.success(page);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Object countShop() {
        long count = shopService.countShop();
        return Rets.success("count", count);
    }

    @RequestMapping(value = "/deleteshop/{id}", method = RequestMethod.DELETE)
    public Object deleteShop(@PathVariable("id") Long id) {
        shopService.deleteShop(id);
        return Rets.success();
    }

    /**
     * 审核商铺
     *
     * @param shop
     * @return
     */
    @RequestMapping(value = "/auditShop", method = RequestMethod.POST)
    public Object auditShop(@ModelAttribute Shop shop) {
        shopService.auditShop(shop);
        return Rets.success();
    }

    /**
     * 停用/启用商铺
     *
     * @param shop
     * @return
     */
    @RequestMapping(value = "/stopShop", method = RequestMethod.POST)
    public Object stopShop(@ModelAttribute Shop shop) {
        shopService.stopShop(shop);
        return Rets.success();
    }

    /**
     * 编辑商铺
     *
     * @param shop
     * @return
     */
    @RequestMapping(value = "/updateshop", method = RequestMethod.POST)
    public Object updateShop(@ModelAttribute @Valid Shop shop) {
        shopService.updateShop(shop);
        return Rets.success();
    }

    @RequestMapping(value = "/addShop", method = RequestMethod.POST)
    public Object addShop(@ModelAttribute @Valid ShopVo shopVo) {
        Shop shop = shopService.addShop(shopVo,getIp());
        ratingService.initRating(shop);
        return Rets.success();
    }

    @RequestMapping(value = "/addcategory", method = RequestMethod.POST)
    public Object addCategory(@Valid @ModelAttribute Menu menu) {
        frontMenuService.saveInitMenu(menu);
        return Rets.success();
    }

//    @RequestMapping(value = "/v2/restaurant/category", method = RequestMethod.GET)
    @RequestMapping(value = "/listcategory", method = RequestMethod.GET)
    public Object categories() {
        return Rets.success(categoryService.findAll());
    }

    @RequestMapping(value = "/getcategory/{id}", method = RequestMethod.GET)
    public Object getCategory(@PathVariable("id") Long restaurantId) {
//        List list = mongoRepository.findAll("menus", "restaurant_id", restaurantId);
        List<Map> list = frontMenuService.listShopMenu(restaurantId);
        return Rets.success("category_list", list);
    }


    @RequestMapping(value = "/getmenu/{id}", method = RequestMethod.GET)
    public Object getMenus(@PathVariable("id") Long menuId) {
        return Rets.success(frontMenuService.getMenuById(menuId));
    }

    @RequestMapping(value = "/getmenu", method = RequestMethod.GET)
    public Object getMenu(@RequestParam("restaurant_id") Long restaurantId) {
        List<Map> list = frontMenuService.listShopMenu(restaurantId);
        return Rets.success(list);
    }

    /**
     * 结算
     * @param shopId
     * @return
     */
    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public Object check(@RequestParam("id") Long shopId) {
        shopService.checnOutAvaiable(shopId);
        return Rets.success();
    }

    @RequestMapping(value = "/searchShop", method = RequestMethod.GET)
    public Object restaurants(@RequestParam("geohash") String geoHash, @RequestParam("keyword") String keyWord) {
        List<Map> list = shopService.searchNearShop(geoHash,keyWord);
        return Rets.success(list);
    }
}
