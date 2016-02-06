package com.qeweb.framework.manager;

import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.StringUtils;


/**
 * BCManager
 *
 */
public class BOManager {

	/**
	 * 根据boBind获取bo并执行BO的init方法(初始化所有的BOP)
	 * @param boBind
	 * @return
	 */
	final public static BusinessObject createBO(String boBind) {
		BusinessObject bo = getBOInstance(boBind);
		if(bo != null)
			bo.init();
		
		return bo;
	}
	
	/**
	 * 根据boBind获取bo
	 * @param boBind
	 * @return
	 */
	final public static BusinessObject getBOInstance(String boBind) {
		if(StringUtils.isEmpty(boBind))
			return null;
		
		BusinessObject bo = (BusinessObject) SpringConstant.getCTX().getBean(boBind);
		return bo;
	}
	
	/**
	 * 根据boType获取bo
	 * @param boType
	 * @return
	 */
	final public static BusinessObject createBOByType(Class<?> boType) {
		BusinessObject bo = null;
		try {
			bo = (BusinessObject) boType.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		
		return bo;
	}
}
