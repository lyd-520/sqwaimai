package com.roy.sqwaimai.bean.enumeration;

public enum MessageTemplateEnum {
    /**
     * 注册验证码
     */
    REGISTER_CODE("REGISTER_CODE","注册验证码");
    private String code;
    private String name;
    MessageTemplateEnum(String code,String name){
        this.name = name;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }}
