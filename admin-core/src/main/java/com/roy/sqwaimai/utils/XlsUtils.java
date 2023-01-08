package com.roy.sqwaimai.utils;

import com.roy.sqwaimai.core.util.DateUtil;

import java.util.Date;

public class XlsUtils {
    public String dateFmt(Date date, String fmt) {
        if (date == null) {
            return "";
        }
        return DateUtil.formatDate(date,fmt);
    }
}
