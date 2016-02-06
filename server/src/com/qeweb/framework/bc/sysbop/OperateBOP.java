package com.qeweb.framework.bc.sysbop;

import java.util.HashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.common.internation.AppLocalization;

/**
 * 按钮对应的BOP
 * <p>
 * 按钮也可以绑定一个BOP, 此时按钮也具备了值/状态/范围.
 * <br>
 * 通常可以在表格或表单中设置按钮的状态, 以便在某些时候隐藏按钮.
 * <br>
 * 同样, 一个按钮也可以与其它按钮或细粒度组件关联.这种关联通常表现为按钮的状态发生变化.
 * <br>
 * 由于超链接与按钮具有相似的功能, 故超链接也可以绑定OperateBOP.
 * </p>
 * <p> 
 * SelectMdBOP, ModifyMdBOP, EmptyBOP, AllMdBOP 是OperateBOP的几种扩展.
 * </p>
 */
public class OperateBOP extends BOProperty {

	private static final long serialVersionUID = 1673094833275688659L;

	//bop是否需要提交, 如果submit==false, 表示不需要提交, 不开启前台校验功能.
	//如果按钮的operate指定了方法, 方法照常执行
	//通常为返回按钮的bop设置submit=false
	private boolean submit = true;
	
	//当按钮执行方法后是否要刷新控件, 默认会刷新控件
	private boolean fresh = true;

	//按钮的行为, 仅当OperateBOP赋予超链接控件时需要设定.
	//如果设定了operate, 则点击超链接时将执行operate指定的方法, 该方法将被GA截获.
	//多个行为用逗号分隔.
	private String operate;
	
	/*
	 * 当点击table级别按钮或page级别按钮时的提示模式, 可选择值有六种： modify/select/empty/adaptive/all/空, 默认为空
	 * 具体表现是: 
	 * 1. 当saveMod为empty时, 点击table级按钮不提示任何信息,直接将请求发送到后台，如果需要提示，则需要显示地设置isConfirm属性；
	 * 2. 当saveMod=modify时, 仅将表格中修改的数据发送到后台, 仅当表格设置了可修改单元格时modify模式才有实际作用;
	 * 3. 当saveMod=select时, 仅将选中的数据传输至后台;
	 * 	 仅当表格中有选择框时才有实际作用. table级别的delete方法即使不设置select也将弹出提示.
	 * 4. 当saveMod=adaptive时, 无论是否未选中任何数据, 请求都将发送到后台, 未选中时不会传输表格中的任何数据, 选中时会将选中数据传输至后台.
	 * 5. 当saveMod=all或空时, 将把页面或表格中的所有数据转递至后台.
	 * 
	 * 注: 当operateBOP属于全局按钮时, saveMod可以针对具体的表格组件设置, 格式如:"table1.modify,table2.select,table3.empty".
	 * 
	 * 关于传输到后台的数据：
	 * 1. empty不会将表格中的任何数据传输至后台；
	 * 2. modify仅将修改过的数据传输至后台；
	 * 3. select仅将选中的数据传输至后台；
	 * 4. 空，将传递表格中或整个页面的所有数据到后台，
	 * 		table级别按钮的空模式通常在动态添加行和弹出返回值时使用；
	 * 		page级别按钮的空模式通常在全局按钮时使用;
	 * 5. adaptive未选中时不会传输表格中的任何数据，选中时会选中数据传输至后台；
	 * 6. all将把页面的所有数据转递至后台.
	 */
	private String saveMod;
	final static public String SAVEMOD_MODIFY = "modify";
	final static public String SAVEMOD_SELECT = "select";
	final static public String SAVEMOD_EMPTY = "empty";
	final static public String SAVEMOD_ADAPTIVE = "adaptive";
	final static public String SAVEMOD_ALL = "all";
	
	/*
	 * 按钮绑定方法的saveMod.
	 * 当一个按钮绑定多个方法时, 可以为每个方法单独设置saveMod, 此时,方法的saveMod将覆盖按钮的saveMod.
	 * 例:
	 * <qeweb:commandButton bind='btn' operate='opt1,opt2'/>
	 * 按钮对应operateBtnBOP, 如果设置了:
	 * operateBtnBOP.setSaveMod(SAVEMOD_ALL);
	 * operateBtnBOP.addOptMod('opt2', SAVEMOD_SELECT);
	 * 则opt2方法使用SAVEMOD_SELECT模式, opt1方法使用SAVEMOD_ALL模式.
	 */
	private Map<String, String> operateMod;
	
	/*
	 * 是否弹出confirm,
	 * 可选值有true/false/""(空)
	 * 1.当hasConfirm==true时,明确告知按钮弹出confirm;
	 * 2.当hasConfirm==false时,明确告知按钮不弹出confirm;
	 * 3.当confirmMsg不为空时,按钮自动将hasConfirm设置为true,提示内容为confirmMsg中的信息;
	 * 4.当不设置confirm时,采用平台默认操作：除delete方法外,均不弹出confirm
	 */
	private boolean hasConfirm;
	//confirm的提示信息
	private String confirmMsg;
	
	/*
	 * confirmOperate指定一个返回String类型的方法名, 该方法是按钮所在BO中的方法, 其参数是void, bo 或 boList.
	 * 如果confirmOperate不为空, 当点击按钮时, 将先执行 confirmOperate对应的方法, 其返回值将填充到confirm.
	 * confirmOperate在弹出confirmMsg后执行.
	 * 这通常用于保存前的校验操作,
	 * 例如: 当修改了订单编码时, 该订单的所有订单行将全部删除, 此时点击按钮时应当先判断是否修改了订单编码, 
	 * 再给出"修改订单编码后其订单明细将全部删除, 是否继续修改?"的提示.
	 */
	private String confirmOperate;
	
	/**
	 * 按钮对应的BOP
	 * <p>
	 * 按钮也可以绑定一个BOP, 此时按钮也具备了值/状态/范围.
	 * <br>
	 * 通常可以在表格或表单中设置按钮的状态, 以便在某些时候隐藏按钮.
	 * <br>
	 * 同样, 一个按钮也可以与其它按钮或细粒度组件关联.这种关联通常表现为按钮的状态发生变化.
	 * <br>
	 * 由于超链接与按钮具有相似的功能, 故超链接也可以绑定OperateBOP.
	 * </p>
	 * <p> 
	 * SelectMdBOP, ModifyMdBOP, EmptyBOP, AllMdBOP 是OperateBOP的几种扩展.
	 * </p>
	 */
	public OperateBOP() {}
	
	/**
	 * @param operate 按钮的行为, 仅当OperateBOP赋予超链接控件时需要设定.
	 * 如果设定了operate, 则点击超链接时将执行operate指定的方法, 该方法将被GA截获.
	 * 多个行为用逗号分隔.
	 */
	public OperateBOP(String operate) {
		this.operate = operate;
	}
	
	/**
	 * 
	 * 当点击table级别按钮或page级别按钮时的提示模式, 可选择值有六种： modify/select/empty/adaptive/all/空, 默认为空
	 * 具体表现是:
	 * <ul> 
	 * <li>1. 当saveMod为empty时, 点击table级按钮不提示任何信息,直接将请求发送到后台，如果需要提示，则需要显示地设置isConfirm属性；
	 * <li>2. 当saveMod=modify时, 如果表格中的数据未修改, 将提示"没有修改数据", 请求不会发送到后台,
	 * 	  仅当表格设置了可修改单元格时modify才有实际作用;
	 * <li>3. 当saveMod=select时, 将判断是否选中了数据, 如果未选中任何数据, 将提示"请至少选择一条数据", 请求不会发送到后台,
	 * 	 仅当表格中有选择框时才有实际作用. table级别的delete方法即使不设置select也将弹出提示.
	 * <li>4. 当saveMod=adaptive时, 无论是否未选中任何数据, 请求都将发送到后台, 未选中时不会传输表格中的任何数据, 选中时会将选中数据传输至后台.
	 * <li>5. 当saveMod=all时, 仅对table有效,将把页面中的所有数据转递至后台.
	 * <li>6. 当operateBOP属于全局按钮时, saveMod可以针对具体的粗粒度组件设置, 格式如:"table1.modify,table2.select,table3.empty".
	 * </ul>
	 * 
	 * 关于传输到后台的数据：
	 * <ul>
	 * <li>1. empty不会将表格中的任何数据传输至后台；
	 * <li>2. modify仅将修改过的数据传输至后台；
	 * <li>3. select仅将选中的数据传输至后台；
	 * <li>4. 空，将传递表格中或整个页面的所有数据到后台，
	 * 		table级别按钮的空模式通常在动态添加行和弹出返回值时使用；
	 * 		page级别按钮的空模式同通常在全局按钮时使用;
	 * <li>5. adaptive未选中时不会传输表格中的任何数据，选中时会将选中数据传输至后台；
	 * <li>6. all仅对table和全局按钮有效,将把页面中的所有数据转递至后台.
	 */
	public void setSaveMod(String saveMod) {
		this.saveMod = saveMod;
	}
	
	/**
	 * 为按钮绑定的方法设置saveMod.
	 * 当一个按钮绑定多个方法时, 可以为每个方法单独设置saveMod, 此时,方法的saveMod将覆盖按钮的saveMod.
	 * @param operate	按钮绑定的方法名
	 * @param saveMod	保存模式
	 */
	public void addOptMod(String operate, String saveMod) {
		if(operateMod == null)
			operateMod = new HashMap<String, String>();
		
		operateMod.put(operate, saveMod);
	}
	
	/**
	 * 设置empty模式
	 */
	public void setEmptyMod() {
		this.saveMod = SAVEMOD_EMPTY;
	}
	
	/**
	 * 为containerId指代的组件设置empty模式
	 * @param containerId
	 */
	public void setEmptyMod(String containerId) {
		this.saveMod = getSM_Empty(containerId);
	}
	
	/**
	 * 设置modify模式
	 */
	public void setModifyMod() {
		this.saveMod = SAVEMOD_MODIFY;
	}
	
	/**
	 * 为containerId指代的组件设置modify模式
	 * @param containerId
	 */
	public void setModifyMod(String containerId) {
		this.saveMod = getSM_Modify(containerId);
	}
	
	/**
	 * 设置select模式
	 */
	public void setSelectMod() {
		this.saveMod = SAVEMOD_SELECT;
	}
	
	/**
	 * 为containerId指代的组件设置select模式
	 * @param containerId
	 */
	public void setSelectMod(String containerId) {
		this.saveMod = getSM_Select(containerId);
	}
	
	/**
	 * 为containerId指代的组件设置all模式
	 * @param containerId
	 */
	public void setAllMod(String containerId) {
		this.saveMod = getSM_All(containerId);
	}
	
	/**
	 * 设置all模式
	 */
	public void setAllMod() {
		this.saveMod = SAVEMOD_ALL;
	}
	
	/**
	 * 为containerId指代的组件设置adaptive模式
	 * @param containerId
	 */
	public void setAdaptiveMod(String containerId) {
		this.saveMod = getSM_Adaptive(containerId);
	}
	
	/**
	 * 设置adaptive模式
	 */
	public void setAdaptiveMod() {
		this.saveMod = SAVEMOD_ADAPTIVE;
	}
	
	/**
	 * getSM_Modify,getSM_Select,getSM_Empty  供全局按钮使用.
	 * <br>
	 * 全局按钮可控制整个页面的所有粗粒度组件的saveMod
	 * @param containerId
	 * @return
	 */
	final static public String getSM_Modify(String containerId) {
		return containerId + "." + SAVEMOD_MODIFY;
	}
	
	final static public String getSM_Select(String containerId) {
		return containerId + "." + SAVEMOD_SELECT;
	}
	
	final static public String getSM_Empty(String containerId) {
		return containerId + "." + SAVEMOD_EMPTY;
	}
	
	final static public String getSM_Adaptive(String containerId) {
		return containerId + "." + SAVEMOD_ADAPTIVE;
	}
	
	final static public String getSM_All(String containerId) {
		return containerId + "." + SAVEMOD_ALL;
	}
	
	@Override
	public void free() {
		super.free();
		submit = true;
		fresh = true;
		operate = null;
		saveMod = null;
		hasConfirm = false;
		confirmMsg = null;
		if(operateMod != null) {
			operateMod.clear();
			operateMod = null;
		}
	}
	
	public boolean isFresh() {
		return fresh;
	}

	/**
	 * 当按钮执行方法后是否要刷新控件, 默认会刷新控件
	 * @param fresh
	 */
	public void setFresh(boolean fresh) {
		this.fresh = fresh;
	}

	public boolean isSubmit() {
		return submit;
	}

	/**
	 * bop是否需要提交, 如果submit==false, 表示不需要提交, 不开启前台校验功能.
		如果按钮的operate指定了方法, 方法照常执行
		通常为返回按钮的bop设置submit=false
	 * @param submit
	 */
	public void setSubmit(boolean submit) {
		this.submit = submit;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}
	
	public String getSaveMod() {
		return saveMod;
	}
	
	public boolean isHasConfirm() {
		return hasConfirm;
	}

	public void setHasConfirm(boolean hasConfirm) {
		this.hasConfirm = hasConfirm;
	}

	public String getConfirmMsg() {
		return AppLocalization.getLocalization(confirmMsg);
	}

	public void setConfirmMsg(String confirmMsg) {
		setHasConfirm(true);
		this.confirmMsg = confirmMsg;
	}

	public String getConfirmOperate() {
		return confirmOperate;
	}

	public void setConfirmOperate(String confirmOperate) {
		this.confirmOperate = confirmOperate;
	}

	public Map<String, String> getOperateMod() {
		return operateMod;
	}

	public void setOperateMod(Map<String, String> operateMod) {
		this.operateMod = operateMod;
	}
	
}
