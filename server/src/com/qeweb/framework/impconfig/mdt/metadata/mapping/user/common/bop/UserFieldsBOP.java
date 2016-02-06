package com.qeweb.framework.impconfig.mdt.metadata.mapping.user.common.bop;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.utils.BoOperateUtil;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.user.common.bo.UserBOExpend;

/**
 * UserFieldsBOP将深度列出UserBOExpend 所有属性为BusinessObject 的属性名
 */
public class UserFieldsBOP extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5275787522589229754L;
	
	@Override
	public void init() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		EnumRange.addPlease(map);
		EnumRange range = new EnumRange();
		
		UserBOExpend userBOExtend = UserBOExpend.getInstance();
		if(userBOExtend == null) {
			range.setResult(map);
			addRange(range);
			return;
		}
		
		List<String> subBONameList = BoOperateUtil.getAllSubBOName(userBOExtend.getClass());
		if(ContainerUtil.isNotNull(subBONameList)) {
			for(String subBOName : subBONameList) {
				map.put(subBOName, subBOName);
			}
		}
		
		range.setResult(map);
		addRange(range);
	}
}
