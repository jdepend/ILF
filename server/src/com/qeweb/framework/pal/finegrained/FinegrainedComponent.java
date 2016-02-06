package com.qeweb.framework.pal.finegrained;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.ExtendsBusinessObject;
import com.qeweb.framework.bc.prop.BCRange;
import com.qeweb.framework.bc.sysbop.StatusBOP;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.VCUtil;
import com.qeweb.framework.pal.Schema;
import com.qeweb.framework.pal.SchemaPool;
import com.qeweb.framework.pal.ViewComponent;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.coarsegrained.Form;

/**
 * 细粒度组件基类,绑定bop
 *
 */
abstract public class FinegrainedComponent extends ViewComponent {
	/*
	 * 是否有扩展
	 * 如果细粒度组件的扩展类型不为空,则细粒度组件为范围型展示.
	 * 例:如果表示时间的细粒度组件dateField没有设置扩展类型,其展现形式是仅画出一个日期控件;
	 * 	  如果为dateField设置了扩展类型,其展现形式是两个日期控件: startDate - endDate.
	 */
	private boolean hasExpend;

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

	//细粒度组件右侧的选择按钮,通过该按钮可以弹出选择框
	private SourceBtn sourceBtn;

	//是否必填
	private Boolean required = null;

	//是否需要校验
	private boolean validateable = true;
	
	//页面元素显示方案,主要实现了组件的显示风格定义
	private Schema schema;
	
	//排列位置
	private String align;
	
	//细粒度组件在container中合并的单元格列数
	private int collSpan = 1;
	
	/**
	 * 初始化, 执行BOP的init方法,以便设置bop的值/状态/范围
	 */
	public void init() {
		BOProperty bop = getBc();
		if(bop != null) {
			bop.init();
			if(align == null && bop instanceof StatusBOP) 
				setAlign("center");
		}
	}

	/**
	 * 是否启用"查询用范围"
	 * @return
	 */
	public boolean isQueryRange() {
		//仅有粗粒度组件容器form才能开启"查询用范围"
		if(getParent() instanceof Form)
			return ((Form) getParent()).isQueryRange();

		return false;
	}

	/**
	 * 是否启用bop关联
	 * @return
	 */
	public boolean isEnableBopRelation() {
		//仅有粗粒度组件容器form才能禁用bop关联
		if(getParent() instanceof Form)
			return ((Form) getParent()).isEnableBopRelation();

		return true;
	}
	
	/**
	 * 是否必填
	 * @return
	 */
	public boolean isRequired() {
		if(getBc() == null)
			return false;

		if(required != null)
			return required;

		BCRange range;
		if(isQueryRange())
			range = getBc().getQueryRange();
		else
			range = getBc().getRange();

		return range != null && range.isRequired();
	}
	
	/**
	 * 上次修改的历史
	 * @return
	 */
	public BOProperty getHistoryBC() {
		BusinessObject parentBC = getParent().getBc();
		if(!(parentBC instanceof ExtendsBusinessObject))
			return null;
		
		ExtendsBusinessObject history = ((ExtendsBusinessObject) parentBC).getHistory();
		if (history != null)
			return history.getBOP(getBcId());
		else
			return null;
	}

	/**
	 * 加载细粒度组件的schema(高亮显示, 字体样式, 状态图片等信息)
	 */
	public final void loadSchema(){
		if(getBc() != null && getBc().isHighlight() && schema == null)
			setSchema(getSchemaFromConfig());
	}
	
	/**
	 * 从配置中读取对应页面的对应BC的Schema配置
	 * @return Schema
	 */
	private Schema getSchemaFromConfig() {
		String containerId = VCUtil.getContainerTagId(getName());
		String bindBO = VCUtil.getBoBind(getName());
		String bindBOP = VCUtil.getBopBind(getName());
		String sourcePage = Envir.getRequestURI();

		return SchemaPool.getSchema(containerId, bindBO, bindBOP, sourcePage);
	}

	@Override
	public BOProperty getBc() {
		return getParent().getBc().getBOP(getBcId());
	}
	
	@Override
	public String getText() {
		//此处仅判断text != null, 如果text == "", 表示强制不使用fieldLabel
		String text = getName();
		if(super.getText() != null)
			text = AppLocalization.getLocalization(super.getText());
		else if(getBc() != null)
			text = getLocalName();
		
		return StringUtils.getUnescapedText(text);
	}

	@Override
	public Container getParent() {
		return (Container)super.getParent();
	}

	/**
	 * 组件的为空时显示在组件上的提示文字
	 * @return
	 */
	public String getEmptyValue() {
		return getValue().getEmptyValue();
	}
	
	@Override
	public void free() {
		hasExpend = false;
		groupName = null;
		sourceBtn = null;
		required = null;
		schema = null;
		align = null;
		collSpan = 1;
		validateable = true;

		super.free();
	}
		
	public boolean isValidateable() {
		return validateable;
	}

	public void setValidateable(boolean validateable) {
		this.validateable = validateable;
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

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public void setSchema(Schema schema) {
		this.schema = schema;
	}

	/**
	 * 获取BC在该业务显示的Schema
	 * @return
	 */
	public Schema getSchema() {
		if(getBc() == null)
			return null;

		return schema;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}
	public boolean isHasExpend() {
		return hasExpend;
	}

	public void setHasExpend(boolean hasExpend) {
		this.hasExpend = hasExpend;
	}

	public int getCollSpan() {
		return collSpan;
	}

	public void setCollSpan(int collSpan) {
		this.collSpan = collSpan;
	}
	
}
