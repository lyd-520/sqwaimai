package com.roy.sqwaimai.bean.constant.state;

/**
 * 数据库排序
 */
public enum QueryOrder {

    ASC("asc"), DESC("desc");

    private String des;

    QueryOrder(String des) {
        this.des = des;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
