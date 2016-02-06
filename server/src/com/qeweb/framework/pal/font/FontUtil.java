package com.qeweb.framework.pal.font;

import com.qeweb.framework.common.utils.StringUtils;

/**
 * 字体工具类
 */
public class FontUtil {
    /**
     * 计算字符串的像素宽度
     * @param 需要截取的字符串
     * @return
     */
    public static int getStrWidth(String str){
    	if(StringUtils.isEmpty(str))
    		return 0;
    	
    	return 12 * str.length() + 15;
    }
}
