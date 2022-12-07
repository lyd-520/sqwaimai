package com.roy.sqwaimai.bean.entity.front;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "ridersinfo")
public class FrontRiderInfo extends BaseMongoEntity{
    public static final Integer STATUS_OFFLINE=0; //下线状态
    public static final Integer STATUS_ONLINE=1; //上线状态，无订单
    public static final Integer STATUS_SENDING=2; //派单状态
    @Id
    private String _id;
    private String rider_name;

    private Long rider_id;
    private Long id;
    private String city;
    private String registe_time;
    private String mobile;

    private Double balance=0.00;
    private Integer order_count=0;
    private Double balance_amount=0.00;

    private Integer work_status= FrontRiderInfo.STATUS_OFFLINE;
    private Long sending_order_id = -1L;
    private String avatar="avatar.jpg";

    private Double longitude; //位置经度
    private Double latitude; //位置维度
}
