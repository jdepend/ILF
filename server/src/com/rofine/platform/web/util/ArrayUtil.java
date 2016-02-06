package com.rofine.platform.web.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by wangdg on 2015/12/15.
 */
public class ArrayUtil {

    /**
     * 对象转换成Array
     *
     * @param obj 需要转换的对象
     * @return 对象的属性数组
     */
    public static List toArray(Object obj) {
        List values = new ArrayList();
        Field[] fields = obj.getClass().getDeclaredFields();
        Object value;
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                value = field.get(obj);
                if(value instanceof Collection){
                    values.add(toArray((Collection)value));
                }else {
                    values.add(value);
                }
            }
            return values;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return values;
        }
    }

    /**
     * 将对象集合转换成Array
     *
     * @param list 需要转换的对象集合
     * @return 对象的属性数组
     */
    public static List toArray(Collection list) {
        List rtnList = new ArrayList();
        for (Object obj : list) {
            rtnList.add(toArray(obj));
        }
        return rtnList;
    }
}
