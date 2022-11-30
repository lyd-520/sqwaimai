package com.roy.sqwaimai.app.controller.front.officialsite;

import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.bean.entity.cms.Article;
import com.roy.sqwaimai.bean.enumeration.cms.BannerTypeEnum;
import com.roy.sqwaimai.bean.enumeration.cms.ChannelEnum;
import com.roy.sqwaimai.bean.vo.front.Rets;
import com.roy.sqwaimai.bean.vo.offcialsite.BannerVo;
import com.roy.sqwaimai.bean.vo.offcialsite.Product;
import com.roy.sqwaimai.service.cms.ArticleService;
import com.roy.sqwaimai.service.cms.BannerService;
import com.roy.sqwaimai.utils.factory.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/offcialsite/case")
public class CaseController extends BaseController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private ArticleService articleService;

    @RequestMapping(method = RequestMethod.GET)
    public Object index() {
        Map<String, Object> dataMap = new HashMap<>(2);

        BannerVo banner = bannerService.queryBanner(BannerTypeEnum.CASE.getValue());
        dataMap.put("banner", banner);

        List<Product> products = new ArrayList<>();
        Page<Article> articlePage = articleService.query(1, 10, ChannelEnum.PRODUCT.getId());
        for (Article article : articlePage.getRecords()) {
            products.add(new Product(article.getId(), article.getTitle(), article.getImg()));
        }
        dataMap.put("caseList", products);


        Map map = new HashMap(1);

        map.put("data", dataMap);
        return Rets.success(map);

    }
}