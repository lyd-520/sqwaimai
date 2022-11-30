package com.roy.sqwaimai.app.controller.business;

import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.bean.entity.front.Explain;
import com.roy.sqwaimai.bean.vo.front.Rets;
import com.roy.sqwaimai.dao.MongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created  on 2019/10/10
 *
 *@Author enilu.cn
 */
@RestController
public class ExplainController extends BaseController {
    @Autowired
    private MongoRepository mongoRepository;
    @RequestMapping(value="/v3/profile/explain",method = RequestMethod.GET)
    public Object getData(){
        Explain explain = mongoRepository.findOne(Explain.class);
        return Rets.success(explain);
    }
    
}