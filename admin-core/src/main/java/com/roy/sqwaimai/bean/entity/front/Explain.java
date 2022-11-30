package com.roy.sqwaimai.bean.entity.front;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * Created  on 2019/10/10
 *
 *@Author enilu.cn
 */
@Data
@Document(collection = "explains")
public class Explain extends BaseMongoEntity{
    @Id
    private String _id;
    private Map<String,String> data;
    
}