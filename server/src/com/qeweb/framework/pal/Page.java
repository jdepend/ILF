package com.qeweb.framework.pal;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.app.pageflow.ExeBOMethod;
import com.qeweb.framework.app.permissions.CheckPermissions;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.ia.TipMessage;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.dataisland.DataIsland;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.pageflow.ContextUtil;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.VCUtil;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.log.IQewebLog;
import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.coarsegrained.Form;
import com.qeweb.framework.pal.coarsegrained.Table;
import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pal.decorativeview.ContainerRelationGroup;
import com.qeweb.framework.pal.style.PageStyle;
import com.qeweb.framework.pal.style.interpreter.Style;


/**
 * 页面管理器Page,不绑定Vc,主要实现了粗粒度组件的布局和生成页面的虚拟表
 *
 */
abstract public class Page extends ViewComponent {
	//page中的所有粗粒度组件
	private List<Container> containerList = new LinkedList<Container>();
	//在页面顶端显示的全局按钮
	private List<CommandButton> headBtnList = new LinkedList<CommandButton>();
	//在页面底端显示的全局按钮
	private List<CommandButton> footBtnList = new LinkedList<CommandButton>();
	//所有全局按钮 commandList = headBtnList + footBtnList
	private List<CommandButton> commandList = new LinkedList<CommandButton>();
	//粗粒度组件关联关系
	private ContainerRelationGroup containerRelationGroup = null;
	/*
	 * 当页面元素全部加载后自动点击的按钮标识, 以英文逗号分隔.
	 * 如:
	 * <qeweb:page onload='btn1.method1,btn2.method1'>
	 * 		<qeweb:form id='form1' bind='bo'>
	 * 			<qeweb:commandButton name='btn1' operate='method1'/>
	 * 			<qeweb:commandButton name='btn2' operate='method2'/>
	 * 		</qeweb:form>
	 * </qeweb:page>
	 * 当页面加载后脚本引擎将自动触发按钮的onclick,即自动执行method1和method2方法.
	 */
	private String onLoad;
	//是否需要接受参数
	protected String param;
	//页面标题
	private String title;
	//样式管理器表达式
	private String styleStr;
	//布局管理器表达式
	private String layoutStr;

	/*
	 * 页面层级，终端展示时使用，当level为0时title不显示后退按钮；
	 * 否则显示后退按钮
	 */
	private String level;

    /**
     * 本页引用的js文件
     */
    private String javascript;

	/**
	 * 画出整个页面信息
	 */
	@Override
	public void paint() {
		init();
		paintContent();
		paintDataIsland();
		paintReturnMsg();
		getPageContextInfo().write("</body>");
		getPageContextInfo().write("</html>");
	}

	public void init() {
		//创建事务中间层
		ExeBOMethod exe = (ExeBOMethod) SpringConstant.getCTX().getBean("ExeBOMethod");
		/*
		 * 1.区分一个Page中所有跳转后执行方法的事务类别<br>
		 * 2.根据区分结果执行各种事务统一执行方法，并向组件中回填执行结果<br>
		 * 3.加载page及粗粒度组件中的DDT<br>
		 * 4.初始化整个页面中包含的所有细粒度组件<br>
		 * 该方法被Page中的init方法调用<br>
		 *
		 * <ul>执行事务的优先级:
		 * <li>1.初始化所有粗粒组件包含的细粒度组件，然后加载DDT，
		 * <li>2.自定义可提交事务，
		 * <li>3.全局可提交事务，
		 * <li>4.自定义只读事务，
		 * <li>5.全局只读事务。
		 * <ul>注意：
		 * <li>如果方法隶属于不同优先级事务, 则按照事务的优先级执行;
		 * <li>如果方法隶属于相同优先级事务, 则按照页面组件的顺序执行.
		 */
		try{
			exe.distinguishBCExeMethodType(this);
			exe.loadData();
			exe.executeCustomSubmitMethods();
			exe.executeSubmitMethods();
			exe.executeCustomReadOnlyMethods();
			exe.executeReadOnlyMethods();
		} catch (BOException e){
			ContextUtil.setTipMsg(e.getErrMessage());
			IQewebLog qewebLog = (IQewebLog) SpringConstant.getCTX().getBean("qewebLog");
			qewebLog.errorLog(e.getErrMessage(), e.getContext(), e);
		}
	}

	@Override
	public void loadData(Object data) {
		if(data == null) {
			return;
		}
		else if(data instanceof Page) {
			com.qeweb.framework.common.Page page = (com.qeweb.framework.common.Page) data;
			if(page.getTotalCount() > 0 && ContainerUtil.isNotNull(page.getBOList().get(0).getOperateMap()))
				setBc((BusinessObject) page.getBOList().get(0));
		}
		else if(data instanceof BusinessObject){
			BusinessObject bo = (BusinessObject) data;
			if(bo != null && ContainerUtil.isNotNull(bo.getOperateMap())) {
				getBc().setBcList(bo.getBcList());
				getBc().setOperateMap(bo.getOperateMap());
			}
		}
		else {
			return;
		}
	}

	public void loadDDT(){}

	public List<Container> getContainerList() {
		return containerList;
	}

	public void setContainerList(List<Container> containerList) {
		this.containerList = containerList;
	}

	public ContainerRelationGroup getContainerRelationGroup() {
		return containerRelationGroup;
	}

	public void setContainerRelationGroup(
			ContainerRelationGroup containerRelationGroup) {
		this.containerRelationGroup = containerRelationGroup;
	}

	/**
	 * 画出数据岛
	 * @return
	 */
	private void paintDataIsland(){
		//创建数据岛对象
		DataIsland dataIsland = AppManager.createDataIsland();
		String diStr = dataIsland.createDataIsland(this);

        getPageContextInfo().write("<div id='dataIsland' style='display:none'>"+diStr+"</div>");
		//getPageContextInfo().write("<input type='hidden' id='dataIsland' layout='" + StringUtils.isNotEmpty(getLayoutStr()) + "' value=\"" + diStr + "\"/>");
		//粗粒度组件关联关系
		getPageContextInfo().write("<input type='hidden' id='groupString' value=\"" + dataIsland.createConRelationIsland(getContainerRelationGroup()) + "\"/>");
	}


	/**
	 * 画出返回的提示信息
	 */
	private void paintReturnMsg() {
		getPageContextInfo().write("<input type='hidden' id='returnMsg' value=\"" + ContextUtil.getReturnMsg() + "\"/>");
		ContextUtil.remvoeReturnMsg();
	}

	/**
	 * 画出页面内容
	 */
	public void paintContent() {
		if(ContainerUtil.isNull(getContainerList()))
			return;

		setStyle();
		paintTitle();
		paintBodyStart();
		BusinessObject bo = getBc();
		if (bo instanceof TipMessage) {
			TipMessage tipMessage = (TipMessage) bo;
			paintTipMessage(tipMessage.getTipMessageTitle(), tipMessage.getTipMessageContent());
		}
		paintHeadButton();
		paintContainer();
		paintFootButton();
		paintContainerRelation();
		patinBodyEnd();
	}

	protected void paintIncludeScript() {}

	abstract protected void paintBodyStart();

	/**
	 * 画出标题
	 */
	abstract protected void paintTitle();

	/**
	 * 引入页面样式
	 */
	protected void setStyle() {
		PageStyle pageStyle = new PageStyle(styleStr);
		Style style = pageStyle.getStyle();
		if(style == null)
			return;

		for(Container container : getContainerList()) {
			if(container instanceof Form)
				container.setStyleContent(style.getFormStyleContent());
			else if(container instanceof Table)
				container.setStyleContent(style.getTableStyleContent());
		}
	}

	/**
	 * 页面加载时的提示信息, 通常可以用来实现消息提醒功能.
	 * @param title			提示消息标题
	 * @param content		提示消息内容
	 */
	abstract protected void paintTipMessage(String title, String content);
		
	
	/**
	 * 画出页面顶部的page级控制组件
	 */
	protected void paintHeadButton() {
		for(CommandButton btn : getHeadBtnList()) {
			btn.paint();
		}
	}

	/**
	 * 画出页面底部的page级控制组件
	 */
	protected void paintFootButton() {
		for(CommandButton btn : getFootBtnList()) {
			btn.paint();
		}
	}
	
	/**
	 * 画出粗粒度组件
	 */
	protected void paintContainer() {
		//绘制顺序
		for(Container container : getContainerList()) {
			container.paint();
		}
	}

	abstract protected void patinBodyEnd();

	/**
	 * 构造粗粒度组件的关联
	 */
	abstract protected void paintContainerRelation();

	/**
	 * 在page中添加全局commandButton.
	 * commandList = headBtnList + footBtnList
	 * @param commandButton
	 * @param pageSign 页面的唯一标识
	 * <br>
	 * 通过bottonName和pageSign控制操作级权限
	 */
	public void addCommandButton(CommandButton commandButton, String pageSign){
		//判断是否有操作级权限
		if(CheckPermissions.hasOperatePermission(VCUtil.getButtonTagName(commandButton.getName()), pageSign)) {
			commandList.add(commandButton);
		}
	}
	
	/**
	 * 画在页面顶部的全局按钮
	 * @param commandButton
	 * @param pageSign 页面的唯一标识
	 * <br>
	 * 通过bottonName和pageSign控制操作级权限
	 */
	public void addHeadButton(CommandButton commandButton, String pageSign){
		//判断是否有操作级权限
		if(CheckPermissions.hasOperatePermission(VCUtil.getButtonTagName(commandButton.getName()), pageSign)) {
			headBtnList.add(commandButton);
		}
	}
	
	/**
	 * 画在页面底部的全局按钮
	 * @param commandButton
	 * @param pageSign 页面的唯一标识
	 * <br>
	 * 通过bottonName和pageSign控制操作级权限
	 */
	public void addFooterButton(CommandButton commandButton, String pageSign){
		//判断是否有操作级权限
		if(CheckPermissions.hasOperatePermission(VCUtil.getButtonTagName(commandButton.getName()), pageSign)) {
			footBtnList.add(commandButton);
		}
	}

	
	public void free() {
		this.layoutStr = null;
		this.param = null;
		this.title = null;
		this.styleStr = null;
		this.onLoad = null;
		this.level = null;
        this.javascript = null;

		if(containerList != null)
			containerList.clear();
		if(commandList != null)
			commandList.clear();
		if(headBtnList != null)
			headBtnList.clear();
		if(footBtnList != null)
			footBtnList.clear();

		if(containerRelationGroup != null && containerRelationGroup.getRelationGroupMap() != null)
			containerRelationGroup.getRelationGroupMap().clear();
		setContainerRelationGroup(null);

		super.free();
	}

	public String getOnLoad() {
		return onLoad;
	}

	public void setOnLoad(String onLoad) {
		this.onLoad = onLoad;
	}

	public String getTitle() {
		return StringUtils.isNotEmpty(title) ? AppLocalization.getLocalization(title) : "";
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Override
	public BusinessObject getBc() {
		return (BusinessObject) super.getBc();
	}

	public List<CommandButton> getCommandList() {
		return commandList;
	}

	public void setCommandList(List<CommandButton> commandList) {
		this.commandList = commandList;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getStyleStr() {
		return styleStr;
	}

	public void setStyleStr(String styleStr) {
		this.styleStr = styleStr;
	}

	public String getLayoutStr() {
		return layoutStr;
	}

	public void setLayoutStr(String layoutStr) {
		this.layoutStr = layoutStr;
	}

    public String getJavascript() {
        return javascript;
    }

    public void setJavascript(String javascript) {
        this.javascript = javascript;
    }

    public List<CommandButton> getHeadBtnList() {
		return headBtnList;
	}

	public void setHeadBtnList(List<CommandButton> headBtnList) {
		this.headBtnList = headBtnList;
	}

	public List<CommandButton> getFootBtnList() {
		return footBtnList;
	}

	public void setFootBtnList(List<CommandButton> footBtnList) {
		this.footBtnList = footBtnList;
	}
}
