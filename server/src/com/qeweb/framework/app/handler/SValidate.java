package com.qeweb.framework.app.handler;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.prop.Range;
import com.qeweb.framework.common.utils.VCUtil;
import com.qeweb.framework.exception.ExceptionUtil;
import com.qeweb.framework.manager.BOManager;

/**
 *
 * SValidate细粒度组件服务器端校，
 * 它为了将细粒度组件服务器端校与struts action分离而建立。
 *
 */
public class SValidate {
	
	/**
	 * 
	 * @param finegrainedId    细粒度组件的Id
	 * @see com.qeweb.framework.pal.ViewComponent
	 * @param value 细粒度组件的值
	 * @param rangeClass range类名
	 * 
	 * @return 校验信息，如果通过校验返回空，否则返回校验提示信息
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	final static public String getValidateMessage(String finegrainedId, String value, String rangeClass) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		BusinessObject bo = BOManager.createBO(VCUtil.getBoBind(finegrainedId));
		BOProperty bop = bo.getBOP(VCUtil.getBopBind(finegrainedId));
		bop.setValue(value);
		Range range = (Range) Class.forName(rangeClass).newInstance();
		bop.addRange(range);
		
		try {
			return bop.validate() ? "" : bop.getMessage();
		} catch (Exception e) {
			ExceptionUtil.handle(bop, e);
		}
		
		return "";
	}
}
