package com.qeweb.framework.pl.ext;

import java.util.Comparator;
import java.util.Map;

public class NodeComparator implements Comparator<Map<String, Object>> {

    @Override
    public int compare(Map<String, Object> map1, Map<String, Object> map2) {
        int flag = 0;
        if(map1.get("sort") != null && map2.get("sort") != null)
            flag = ((Integer) map1.get("sort")).compareTo((Integer)map2.get("sort"));
        return flag;
    }
}
