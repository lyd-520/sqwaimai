package com.roy.sqwaimai.bean.vo.business;

import com.roy.sqwaimai.bean.entity.front.Food;
import lombok.Data;

import java.util.List;

/**
 * 接收食品信息参数
 */
@Data
public class FoodVo  extends Food {
    private Long id;
    private String name;
    private String descript;
    private Long idMenu;
    private String imagePath;
    private Long idShop;
    private List<SpecVo> specs;
    private String specsJson;
    private String attributesJson;
    private Long category_id;


}
