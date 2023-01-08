package com.roy.sqwaimai.core.util;

import org.apache.commons.beanutils.BeanUtils;

import java.util.*;

/**
 * 集合工具类
 */
public final class Lists {

    private Lists() {
    }

    public static <V> List<V> newArrayList(V... vs) {
        List<V> list = new ArrayList<V>();
        for (V v : vs) {
            list.add(v);
        }
        return list;
    }
}
