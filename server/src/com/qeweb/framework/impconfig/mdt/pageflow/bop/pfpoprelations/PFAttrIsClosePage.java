package com.qeweb.framework.impconfig.mdt.pageflow.bop.pfpoprelations;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 页面流节点状态BOP, 是否关闭弹出页面
 */
public class PFAttrIsClosePage extends PFAttrStatusBOP {

	private static final long serialVersionUID = 1527627146482459042L;
	
	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new LinkedList<BusinessComponent>();
		result.add(new PFAttrIsPopBOP());
		result.add(new PFAttrDialogWidthBOP());
		result.add(new PFAttrDialogHeightBOP());
		result.add(new PFAttrDialogTitleBOP());
		
		return result;
	}
	
	/**
	 * 是否关闭页面
	 * @return true 关闭
	 */
	final public boolean isClose() {
		return StringUtils.isEqual(YES, getValue().getValue());
	}
}
