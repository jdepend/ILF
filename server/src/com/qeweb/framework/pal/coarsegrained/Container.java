package com.qeweb.framework.pal.coarsegrained;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.qeweb.framework.app.permissions.CheckPermissions;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.VCUtil;
import com.qeweb.framework.pal.ViewComponent;
import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.finegrained.text.Hidden;

/**
 * 粗粒度组件类,一个粗粒度组件绑定一个Busniess object
 *
 */
public abstract class Container extends ViewComponent {
	//布局管理器表达式
	private String layoutStr;
	//样式css
	private String styleContent;
	
	//粗粒度组件中的组件列表, 包括粗粒度组件、细粒度组件 、按钮
	private Map<String, ViewComponent> vcList = new LinkedHashMap<String, ViewComponent>();

	//页面初始加载时，用来判断粗粒度组件是否接收另一个页面传递的参数
	private String param;

	//是否回填值，用于控制弹出页面是否画出选择按钮
	private boolean isFill;
	
	//组件的图标
	private String icon;
	
	//是否显示头部
	private boolean header;

	/**
	 * 将container中的所有组件初始化
	 */
	public void init() {
		if(ContainerUtil.isNotNull(getFcList())) {
			for(Entry<String, FinegrainedComponent> entry : getFcList().entrySet()) {
				entry.getValue().init();
			}
		}
	}
	
	/**
	 * 加载组件的schema(高亮显示, 字体样式, 状态图片等信息)
	 */
	public final void loadSchema(){
		if(ContainerUtil.isNotNull(getFcList())) {
			for(Entry<String, FinegrainedComponent> entry : getFcList().entrySet()) {
				entry.getValue().loadSchema();
			}
		}
	}
	
	/**
	 * 加载DDT方案. 子类需覆写该方法.
	 * @return
	 */
	public void loadDDT(){}
	
	/**
	 * 加载MDT方案. 子类需覆写该方法.
	 */
	public void loadMDT(){}

	
	/**
	 * 获取所有细粒度组件
	 *
	 * @return Map<细粒度组件bind, 细粒度组件>
	 */
	public Map<String, FinegrainedComponent> getFcList() {
		Map<String, FinegrainedComponent> fcList = new LinkedHashMap<String, FinegrainedComponent>();
		Map<String, FinegrainedComponent> hiddenFcList = new LinkedHashMap<String, FinegrainedComponent>();
		for (String bind : vcList.keySet()) {
			ViewComponent vc = vcList.get(bind);
			if(vc instanceof Hidden)
				hiddenFcList.put(bind, (Hidden)vc);
			else if(vc instanceof FinegrainedComponent)
				fcList.put(bind, (FinegrainedComponent)vc);
		}

		//将hidden控件添加到fcList末尾
		if(ContainerUtil.isNotNull(hiddenFcList))
			fcList.putAll(hiddenFcList);
		
		return fcList;
	}

	/**
	 * 获取所有逻辑控制组件
	 *
	 * @return Map<按钮operate, commandButton>
	 */
	public Map<String, CommandButton> getButtonList() {
		Map<String, CommandButton> buttonList = new LinkedHashMap<String, CommandButton>();

		for (String bind : vcList.keySet()) {
			ViewComponent vc = vcList.get(bind);
			if(vc instanceof CommandButton)
				buttonList.put(bind, (CommandButton)vc);
		}

		return buttonList;
	}

	/**
	 * 获取用<qeweb:expend>标签修饰的按钮，
	 * 在表格中，这类按钮属于表格级按钮
	 * @return
	 */
	public List<CommandButton> getNoExpendBtnList() {
		List<CommandButton> result = new LinkedList<CommandButton>();

		for (CommandButton btn : getButtonList().values()) {
			if (!btn.isHasExpand())
				result.add(btn);
		}

		return result;
	}

	/**
	 * 获取未用<qeweb:expend>标签修饰的按钮，
	 * 在表格中，这类按钮属于行级按钮
	 * @return
	 */
	public List<CommandButton> getExpendBtnList() {
		List<CommandButton> result = new LinkedList<CommandButton>();

		for (CommandButton btn : getButtonList().values()) {
			if (btn.isHasExpand())
				result.add(btn);
		}

		return result;
	}

	/**
	 * 在container中添加commandButton
	 * @param commandButton
	 * @param pageSign 页面的唯一标识
	 * <br>
	 * 通过bottonName和pageSign控制操作级权限
	 */
	public void addCommandButton(CommandButton commandButton, String pageSign){
		//判断是否有操作级权限
		if(CheckPermissions.hasOperatePermission(
				VCUtil.getButtonTagName(commandButton.getName()), pageSign)) {
			vcList.put(commandButton.getName(), commandButton);
		}
	}

	/**
	 * 在container中添加粗粒度组件或细粒度组件
	 * @param bcId
	 * @param vc
	 */
	public void addVC(String bcId, ViewComponent vc){
		this.vcList.put(bcId, vc);
	}

	/**
	 * 初始化页面中存在的bop，以便为这些BOP设置值、状态、范围
	 * @param bo
	 */
	protected void initBOP(BusinessObject bo){
		boolean readOnly = bo.getStatus().isReadonly();
		boolean disable = bo.getStatus().isDisable();
		//仅初始化页面中存在的bop
		for(String bopName : getFcList().keySet()) {
			if(bo.getBOP(bopName) != null){
				BOHelper.copyMyself(bo.getBOP(bopName), readOnly, disable);
			}
		}
		setBc(bo);
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

	public void free() {
		if(vcList != null){
			vcList.clear();
		}

		this.isFill = false;
		this.layoutStr = null;
		this.param = null;
		this.styleContent = null;
		this.icon = null;
		this.header = false;
		super.free();
	}

	
	public void setLayoutStr(String layoutStr) {
		this.layoutStr = StringUtils.removeAllSpace(layoutStr);
	}

	public String getLayoutStr() {
		return layoutStr;
	}

	public Map<String, ViewComponent> getVcList() {
		return vcList;
	}
	public void setVcList(Map<String, ViewComponent> vcList) {
		this.vcList = vcList;
	}

	@Override
	public BusinessObject getBc() {
		return (BusinessObject) super.getBc();
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public boolean isFill() {
		return isFill;
	}

	public void setFill(boolean isFill) {
		this.isFill = isFill;
	}

	public String getStyleContent() {
		return styleContent;
	}

	public void setStyleContent(String styleContent) {
		this.styleContent = styleContent;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isHeader() {
		return header;
	}

	public void setHeader(boolean header) {
		this.header = header;
	}
	
	
}
