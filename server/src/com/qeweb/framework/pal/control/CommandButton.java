package com.qeweb.framework.pal.control;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.constant.ConstantBOMethod;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.VCUtil;
import com.qeweb.framework.pal.ViewComponent;
import com.qeweb.framework.pal.finegrained.SourceBtn;

/**
 * 逻辑控制按钮, 它包含在一个粗粒度组件内, 负责执行BO的方法与页面跳转<br>
 * 当负责执行BO操作时，需要使用operate属性，绑定的操作有4种：<br>
 * 1、绑定该粗粒度组件对应Bo的一个方法,即绑定一个boMethod；<br>
 * 2、绑定其它BO的方法；<br>
 * 3、同时绑定粗粒度组件对应Bo的一个或多个方法和其它BO的一个或多个方法，
 * 		形如：method1,method2,bo1.method1,bo1.method2,bo2.method2<br>
 * 4、按钮可以绑定一个BOP(可选),用于控制按钮的值、状态、范围
 *
 * 当负责页面跳转时，无需使用operate属性，跳转到另一个页面后将默认执行对应BO的
 * query方法；如果测试使用operate属性，则跳转到另一个页面后仅执行对应BO的operate。
 * <br>
 *
 * 如果使用operate属性，当点击一个按钮时，会把按钮所属的BO或BOList传至GA，GA会执行相应BO
 * 的operate同名方法，如果operate不是query方法，则该方法中仅可以包含三种类型的参数：void、BO、BOList
 */
public abstract class CommandButton extends ViewComponent {

	/*
	 * 如果逻辑控件的hasExpand == true, 则逻辑控件将以特殊的方式展现出来, 并且仅控制一个bo.
	 * 如: 在table中, hasExpand == true 的按钮将表示行级按钮, hasExpand == false的按钮表示表格级按钮. 
	 */
	private boolean hasExpand;

	/*
	 * 按钮绑定的boMethod,该属性非必填.
	 * 如果不添加该属性,则表示单纯的进行页面跳转.
	 */
	private String operate;

	/*
	 * 组件的组名
	 * 用于组合控件，组合控件通常是按钮+细粒度组件。
	 * 一个组合控件中的所有组件有相同的组名，表示通过按钮执行某方法后，其结果会影响组名相同细粒度组件。
	 * 如下所示是一个上传控件，上传后同时现实文件名与图片：
	 * <qeweb:textField bind='fileName' groupName='uploadGroup' />
	 * <qeweb:img bind='fileName' groupName='uploadGroup' />
	 * <qeweb:commandButton name='upload' operate="upload" groupName='uploadGroup' />
	 */
	private String groupName;

	//如果按钮有sourceBtn扩展，则该按钮变为选择按钮，其功能同细粒度组件的<qeweb:source>标签
	private SourceBtn sourceBtn;
	
	//按钮的图标
	private String icon;
	
	@Override
	public OperateBOP getBc() {
		BusinessObject bo = (BusinessObject)getParent().getBc();
		OperateBOP bop = bo.getOperateMap().get(VCUtil.getButtonTagName(getName()));
		if(bop == null) {
			bop = new OperateBOP();
			bo.getOperateMap().put(VCUtil.getButtonTagName(getName()), bop);
		}
		setBc(bop);

		return (OperateBOP)super.getBc();
	}

	@Override
	public String getText() {
		String localizationKey = "";
		if(StringUtils.isNotEmpty(super.getText()))
			localizationKey = super.getText();
		else if(ConstantBOMethod.isQewebMethod(getOperate()))
			localizationKey = getOperate();
		else if(StringUtils.isNotEmpty(getLocalName()))
			return getLocalName();
		else
			localizationKey = BOHelper.getRealClass(getParent().getBc()).getName() + "."
				+ VCUtil.getButtonTagName(getName());

		return StringUtils.getUnescapedText(AppLocalization.getLocalization(localizationKey));
	}

	/**
	 * 获取insert按钮ID，用于调取系统insert弹出框
	 * @return
	 */
	public String getInsertBtnId(){
		return VCUtil.createFinegrainedID(this.getParent().getName(), ConstantBOMethod.BO_INSERT);
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public boolean isHasExpand() {
		return hasExpand;
	}

	public void setHasExpand(boolean hasExpand) {
		this.hasExpand = hasExpand;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public SourceBtn getSourceBtn() {
		return sourceBtn;
	}

	public void setSourceBtn(SourceBtn sourceBtn) {
		this.sourceBtn = sourceBtn;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public void free() {
		this.operate = null;
		this.groupName = null;
		this.hasExpand = false;
		this.sourceBtn = null;
		this.icon = null;
		
		super.free();
	}
}
