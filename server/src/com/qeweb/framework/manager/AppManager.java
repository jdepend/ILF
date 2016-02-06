package com.qeweb.framework.manager;

import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.dataisland.DataIsland;
import com.qeweb.framework.common.dataisland.XMLDataIsland;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.ViewComponent;

public class AppManager {
	/**
	 * 创建Vc
	 * @param clasz
	 * @return ViewComponent
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	final static public ViewComponent createVC(Class clasz) {
//		Class claszX = VCManager.getVCClassFactory().getVCClass(clasz);
//		return (ViewComponent)QewebInstancePool.getInstance().getObject(claszX == null ? clasz : claszX);
		return VCManager.getVCFactory().createVC(clasz);
	}

	/**
	 * 创建Vc
	 * @param displayType
	 * @param clasz
	 * @return ViewComponent
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	final static public ViewComponent createVC(String displayType, Class clasz) {
//		Class claszX = VCManager.getVCClassFactory().getVCClass(clasz);
//		return (ViewComponent)QewebInstancePool.getInstance().getObject(claszX == null ? clasz : claszX);
		return VCManager.getVCFactory(displayType).createVC(clasz);
	}
	
	/**
	 * 释放Vc
	 * @param clasz
	 * @return void
	 */
	final static public void freeVC(Object object) {
//		QewebInstancePool.getInstance().freeObject(object);
	}

	/**
	 * 创建数据岛
	 * @return DataIsland
	 */
	final static public DataIsland createDataIsland(){
		if(StringUtils.isEqual(Constant.DATAISLANDTYPE_XML, AppConfig.getPropValue(AppConfig.PROPFILE_DATAISLANDTYPE)))
			return new XMLDataIsland();
		else
			//return new JSONDataIsland();
			return null;
	}

}
