package com.roy.sqwaimai.core.entity.vo;

import com.roy.sqwaimai.core.entity.Shop;
import lombok.Data;

import java.io.Serializable;

@Data
public class ShopVo extends Shop implements Serializable {

    private String activitiesJson;
    private Boolean deliveryMode;
    private Boolean news;
    private Boolean bao;
    private Boolean zhun;
    private Boolean piao;
    private String image_path;
    private String business_license_image;
    private String catering_service_license_image;
    private String startTime;
    private String endTime;
}