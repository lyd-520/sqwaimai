package com.roy.sqwaimai.bean.vo.offcialsite;

import com.roy.sqwaimai.bean.entity.cms.Banner;
import lombok.Data;

import java.util.List;

@Data
public class BannerVo {
    private Integer index = 0;
    private List<Banner> list;

}
