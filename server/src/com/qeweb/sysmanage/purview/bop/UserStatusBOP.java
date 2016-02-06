package com.qeweb.sysmanage.purview.bop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.internation.AppLocalization;

/**
 * 用户状态
 */
public class UserStatusBOP extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4255102619914261147L;
	public static int USING = 1;		//启用
	public static int UNUSING = 0;		//禁用
	
	public void init() {
		EnumRange range = new EnumRange();
		range.getResult().put(USING + "", 
				AppLocalization.getLocalization("com.qeweb.sysmanage.purview.bo.UserBO.userStatus.range.using"));
		range.getResult().put(UNUSING + "", 
				AppLocalization.getLocalization("com.qeweb.sysmanage.purview.bo.UserBO.userStatus.range.unusing"));
		
		addRange(range);
		//默认为启用
		setValue(USING + "");
	}
}
