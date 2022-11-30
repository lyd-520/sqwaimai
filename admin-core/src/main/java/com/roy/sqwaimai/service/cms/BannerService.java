package com.roy.sqwaimai.service.cms;

import com.roy.sqwaimai.bean.entity.cms.Banner;
import com.roy.sqwaimai.bean.enumeration.cms.BannerTypeEnum;
import com.roy.sqwaimai.bean.vo.offcialsite.BannerVo;
import com.roy.sqwaimai.dao.cms.BannerRepository;
import com.roy.sqwaimai.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService extends BaseService<Banner,Long, BannerRepository> {
    @Autowired
    private BannerRepository bannerRepository;

    /**
     * 查询首页banner数据
     * @return
     */
    public BannerVo queryIndexBanner(){
    return queryBanner(BannerTypeEnum.INDEX.getValue());
    }

    public BannerVo queryBanner(String type){
        BannerVo banner = new BannerVo();
        List<Banner> bannerList = bannerRepository.findAllByType(type);
        banner.setIndex(0);
        banner.setList(bannerList);
        return  banner;
    }
}
