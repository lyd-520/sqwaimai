package com.roy.sqwaimai.core.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document(collection = "explains")
public class Explain extends BaseMongoEntity {
    @Id
    private String _id;
    private Map<String,String> data;
    
}