package com.qeweb.framework.impconfig.mdt.pageflow.bop.pfpoprelations;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.utils.BoOperateUtil;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 页面流节点状态BOP, 是否弹出目标页面
 */
public class PFAttrIsPopBOP extends PFAttrStatusBOP {

	private static final long serialVersionUID = 4979316221457878972L;
	
	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new LinkedList<BusinessComponent>();
		result.add(new PFAttrDialogWidthBOP());
		result.add(new PFAttrDialogHeightBOP());
		result.add(new PFAttrDialogTitleBOP());
		
		return result;
	}
	
	/**
	 * 是否弹出目标页面
	 * @return true 弹出
	 */
	final public boolean isPop() {
		return StringUtils.isEqual(YES, getValue().getValue());
	}
	
	public BusinessComponent query(BOProperty sourceBop) {
		BOProperty bop = BoOperateUtil.getRealBop(sourceBop);
		if(!(bop instanceof PFAttrIsClosePage))
			return null;


		if(((PFAttrIsClosePage) bop).isClose()){
			getStatus().setHidden(true);
			setValue("");
		}
		else{
			getStatus().setHidden(false);
		}
			
		return this;
	}
}
