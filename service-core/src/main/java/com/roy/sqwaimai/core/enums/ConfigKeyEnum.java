package com.roy.sqwaimai.core.enums;

public enum ConfigKeyEnum {
    /**
     * 系统默认上传路径
     */
    SYSTEM_FILE_UPLOAD_PATH("system.file.upload.path"),
    /**
     * 系统名称
     */
    SYSTEM_APP_NAME("system.app.name"),
    /**
     * 平台盈利额
     */
    SYSTEM_PLATFORM_TOTAL_AMOUNT("system.platform.total.amount"),
    /**
     * 查找范围。骑手查找订单的范围
     */
    SYSTEM_SEARCH_RANGE("system.search.range"),
    /**
     * 服务IP。根据IP定位服务的城市。默认长沙
     */
    SYSTEM_SERVER_IP("system.server.ip");



    private String value;

    ConfigKeyEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
