package com.qeweb.framework.pal.coarsegrained;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.constant.ConstantBOMethod;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.impconfig.common.VCType;
import com.qeweb.framework.impconfig.mdt.MDTContext;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.Var_Page;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.simplevc.container.SimpleContainer;
import com.qeweb.framework.manager.DisplayType;
import com.qeweb.framework.pal.ViewComponent;
import com.qeweb.framework.pal.coarsegrained.helper.DDTHelper;
import com.qeweb.framework.pal.coarsegrained.helper.MDTHelper;
import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;

/**
 * 粗粒度组件--表单
 *
 */
public abstract class Form extends Container {

	private boolean queryRange = false;			//是否启用"查询用范围"
	private boolean enableBopRelation = true;	//是否启用bop关联
	
	
	@Override
	public void loadData(Object data) throws BOException {
		if(data == null) {
			return;
		}
		else if(data instanceof Page) {
			Page page = (Page) data;
			if(page.getTotalCount() == 1)
				initBOP((BusinessObject) page.getBOList().get(0));
			else if(page.getTotalCount() > 1)
				throw new BOException("表单接收参数后,返回的结果集大于1");
				
		}
		else if(data instanceof BusinessObject) {
			initBOP((BusinessObject) data);
		}
		else {
			return;
		}
	}
	
	/**
	 * 加载DDT方案中的细粒度组件,这些细粒度组件绑定弹性域,不在页面的标签中.
	 * @return
	 */
	@Override
	public void loadDDT() {
		//来自标签的细粒度组件
		Map<String, FinegrainedComponent> staticList = getFcList();
		//来自DDTschema 的细粒度组件
		Map<String, FinegrainedComponent> dynamicList = DDTHelper.getDDTFC(this);
		if(ContainerUtil.isNull(dynamicList) || ContainerUtil.isNull(staticList))
			return;

		Map<String, ViewComponent> vcList = new LinkedHashMap<String, ViewComponent>();
		vcList.putAll(DDTHelper.getSortFCList(staticList, dynamicList));
		vcList.putAll(getButtonList());
		setVcList(vcList);
	}

	@Override
	public void loadMDT() {
		if(ContainerUtil.isNull(getVcList()))
			return;
		
		Var_Page varPage = new Var_Page(Envir.getRequestURI(), getId(), getBcId(), VCType.VC_TYPE_FORM);

		//受全局变量影响的组件
		SimpleContainer simpleContainer = null;
		try {
			simpleContainer = varPage.getRelateContainer(MDTContext.getVarMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(simpleContainer == null)
			return;
		
		//变量当前值影响表单的状态
		if(simpleContainer.isDisable() != null)
			getBc().getStatus().setDisable(simpleContainer.isDisable());
		if(simpleContainer.isHidden() != null)
			getBc().getStatus().setHidden(simpleContainer.isHidden());
		if(simpleContainer.isReadonly() != null)
			getBc().getStatus().setReadonly(simpleContainer.isReadonly());
		
		//变量影响表单中的细粒度组件状态
		MDTHelper.var2FC(getFcList(), simpleContainer);
		//变量影响表单中的控制组件状态
		MDTHelper.var2Btn(getButtonList(), simpleContainer, getId(), getBcId());
	}

	/**
	 * 表单中最大的细粒度组件label的宽度(细粒度组件国际化文字的宽度)
	 * @return
	 */
	public float getMaxLabelWidth() {
		//默认最大宽度是120F
		float maxWidth = 120F;
		Map<String, FinegrainedComponent> fcList = getFcList();
		for(FinegrainedComponent fc : fcList.values()) {
			if(fc.getWidth() > maxWidth)
				maxWidth = fc.getWidth();
		}
		
		return maxWidth;
	}
	
	/**
	 * 是否添加"保存查询条件"按钮.
	 * 当配置了自动添加"保存查询条件"功能时, 将会在查询表单中额外添加两个按钮.
	 * <ul>
	 * 判断查询表单的3个必要条件: 
	 * <li>	表单中同时存在"查询"和"重置"按钮;
	 * <li>	表单中其它按钮的operate不能是sysSaveCase或sysOpenCase;
	 * <li> 表单中其它按钮的operate不能是sysSaveCase或sysOpenCase;
	 * @return
	 */
	public boolean hasSaveCaseBtn() {
		if(DisplayType.isSaveCase()) {
			Map<String, CommandButton> buttonList = getButtonList();
			boolean queryFlag = false, resetFlag = false;
			for (CommandButton btn : buttonList.values()) {
				if(ConstantBOMethod.isSysSaveCase(btn.getOperate()))
					return false;
				if(ConstantBOMethod.isQuery(btn.getOperate()))
					queryFlag = true;
				else if(ConstantBOMethod.isSysReset(btn.getOperate()))
					resetFlag = true;
			}
			
			return queryFlag && resetFlag;
		}
		
		return false;
	}

	@Override
	public void free() {
		queryRange = false;
		enableBopRelation = true;
		super.free();
	}
	
	public boolean isQueryRange() {
		return queryRange;
	}

	public void setQueryRange(boolean queryRange) {
		this.queryRange = queryRange;
	}

	public boolean isEnableBopRelation() {
		return enableBopRelation;
	}

	public void setEnableBopRelation(boolean enableBopRelation) {
		this.enableBopRelation = enableBopRelation;
	}
}
