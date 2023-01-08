package com.roy.sqwaimai.core.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class FrontUser extends BaseMongoEntity {
    @Id
    private String _id;
    private String username;
    private String password;
    private Long user_id;
    /**
     * 小程序openid
     */
    private String miniappOpenid;

}