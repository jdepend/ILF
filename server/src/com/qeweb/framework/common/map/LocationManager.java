package com.qeweb.framework.common.map;

import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.map.baidumap.BaiduLocation;
import com.qeweb.framework.common.map.googlemap.GoogleLocation;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 获取location
 *
 */
public class LocationManager {

	/**
	 *
	 * @return
	 */
	public Location getLocation() {
		if(StringUtils.isEqual(AppConfig.DISPLAY_MAP_TYPE_BAIDU, AppConfig.getPropValue(AppConfig.DISPLAY_MAP_TYPE)))
			return new BaiduLocation();
		else
			return new GoogleLocation();
	}
}
