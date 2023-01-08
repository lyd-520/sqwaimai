package com.roy.sqwaimai.core.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "riders")
public class FrontRider extends BaseMongoEntity {
    @Id
    private String _id;
    private String rider_name;
    private String password;
    private Long rider_id;
}
