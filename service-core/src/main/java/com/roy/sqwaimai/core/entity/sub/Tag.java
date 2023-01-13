package com.roy.sqwaimai.core.entity.sub;

import lombok.Data;

import java.io.Serializable;

@Data
public class Tag implements Serializable {
    private Integer count = 0;
    private String name;
    private Boolean unsatisfied=false;
}
