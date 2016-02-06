package com.qeweb.framework.pal;

import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.Range;
import com.qeweb.framework.bc.prop.Status;
import com.qeweb.framework.bc.prop.Value;
import com.qeweb.framework.exception.BOException;

/**
 * 展示组件类 .<br>
 * ViewComponent是pal层的基类,所有粗粒度组件/细粒度组件/控制组件均是一个Vc ,一个Vc 绑定一个Bc.
 * <br>
 * 1.粗粒度组件和控制组件的bc可窄化为bo;
 * <br>
 * 2.细粒度组件的bc可窄化为bop;
 *
 */
public abstract class ViewComponent {
	/*
	 * 控件的Id.
	 * 1.如果是粗粒度组件,id对应标签的id属性.
	 * 例：
	 * <qeweb:form bind=’bo’ id=’name1’>标签，id为name1
	 *
	 * 2.如果是细粒度组件,id对应containerId-containerBind-bind
	 * 例：
	 *	<qeweb:form id=’formId’ bind=’bo’>
	 *		<qeweb:textField bind=’bop’/>
	 *	</qeweb:form>
	 * 其id为  formId-bo-bop
	 * 3.如果细粒度组件在table弹出框中, 则细粒度组件ID: containerId-containerBind-bopbind-operate
	 * 例：
	 *	<qeweb:form id=’formId’ bind=’bo’ display='insert'>
	 *		<qeweb:textField bind=’bop’/>
	 *	</qeweb:form>
	 *  其id为  formId-bo-bop-insert
	 *
	 * 4.如果是控制组件,id对应containerId-containerBind-operate
	 * 例:
	 *	<qeweb:form id=’formId’ bind=’bo’>
	 *		<qeweb:commandButton name=’btnName’ operate=’boMethod’/>
	 *	</qeweb:form>,
	 * 其id为  formId-bo-boMethod
	 *
	 * 5.如果控制组件直接包含在<qeweb:page>中,id对应pageBind-operate
	 * 例:
	 *  <qeweb:page bind='bo'>
	 *  	<qeweb:commandButton name=’btnName’ operate=’boMethod’/>
	 *  </qeweb:page>
	 *  其id为  bo-boMethod
	 */
	private String id;

	/*
	 * 控件的Name属性
	 *
	 * 1.如果是粗粒度组件,name对应id-bind.
	 * 例：
	 * <qeweb:form bind=’bo’ id=’name1’>标签，name为name1-bo.
	 *
	 *
	 * 2.如果是细粒度组件,name对应containerId-containerBind-bind
	 * 例：
	 *	<qeweb:form id=’formId’ bind=’bo’>
	 *		<qeweb:textField group=’textGroup’ bind=’bop’/>
	 *	</qeweb:form>,
	 * 其name为  formId-bo-bop
	 *
	 *
	 * 3.如果是控制组件,name对应containerId-containerBind-btnName
	 * 例1：
	 *	<qeweb:form id=’formId’ bind=’bo’>
	 *		<qeweb:commandButton name=’btnName’ operate=’boMethod’/>
	 *	</qeweb:form>,
	 * 其name为 formId-bo-btnName
	 *
	 * 4.如果控制组件直接包含在<qeweb:page>中,id对应pageBind-btnName
	 * 例:
	 *  <qeweb:page bind='bo'>
	 *  	<qeweb:commandButton name=’btnName’ operate=’boMethod’/>
	 *  </qeweb:page>
	 *  其id为  bo-btnName
	 */
	private String name;

	/*
	 * VC 控件的bind或operate.
	 *
	 * 1.粗粒度组件和page可以通过bcId获得其绑定的bo;
	 * 2.细粒度组件可以通过bcId获得其绑定的bop;
	 * 3.控制组件可以通过bcId获得其绑定的boMethod.
	 */
	private String bcId;

	//当前组件显示在prevBCID对应的组件前, 用于布局排序
	private String prevBCID;

	/*
	 * 展示组件绑定的业务组件.
	 *
	 * 1.粗粒度组件和控制组件的bc可窄化为bo;
	 * 2.细粒度组件的bc可窄化为bop;
	 */
	private BusinessComponent bc;

	//vc宽度
	private float width = 0F;

	//vc高度
	private float height = 0F;

	//父容器
	private ViewComponent parent = null;

	//上下文信息
	private PageContextInfo pageContextInfo;

	/*
	 * 粗粒度和细粒度组件的Label文字
	 * 1.text不为空，则显示国际化后的text
	 * 2.text为空,显示国际化后的bc的name
	 */
	protected String text;

	/**
	 * 画出该Vc
	 */
	abstract public void paint();

	/**
	 * 释放内存
	 */
	public void free() {
		if(getBc() != null)
			getBc().free();
		this.id = null;
		this.name = null;
		this.bcId = null;
		this.bc = null;
		this.prevBCID = null;
		this.height = 0f;
		this.width = 0f;
		this.parent = null;
		this.text = null;
		this.pageContextInfo = null;
	}

	/**
	 * 加载上下文跳转的数据
	 * @param data
	 */
	public void loadData(Object data) throws BOException{}
	
	/**
	 * 组件的注释信息
	 * @return
	 */
	public String getTipText() {
		if(getBc() == null)
			return "";
		
		return getBc().getValue().getTipValue();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setLocalName(String localName) {
		if(getBc() != null)
			getBc().setLocalName(localName);
	}
	
	public String getLocalName() {
		if(getBc() != null)
			return getBc().getLocalName();
		
		return "";
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getBcId() {
		return bcId;
	}

	public void setBcId(String bcId) {
		this.bcId = bcId;
	}

	public BusinessComponent getBc() {
		return bc;
	}

	public void setBc(BusinessComponent bc) {
		this.bc = bc;
	}
	
	public Status getStatus() {
		BusinessComponent bc = getBc();
		return bc != null ? bc.getStatus() : null;
	}
	
	public String getStyle() {
		Status status = getStatus();
		return status != null ? status.getStyle() : null;
	}
	
	public Range getReange() {
		BusinessComponent bc = getBc();
		return bc != null ? bc.getRange() : null;
	}
	
	public Value getValue() {
		BusinessComponent bc = getBc();
		return bc != null ? bc.getValue() : null;
	}

	public PageContextInfo getPageContextInfo() {
		return pageContextInfo;
	}

	public void setPageContextInfo(PageContextInfo pageContextInfo) {
		this.pageContextInfo = pageContextInfo;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public ViewComponent getParent() {
		return parent;
	}

	public void setParent(ViewComponent parent) {
		this.parent = parent;
	}

	public String getPrevBCID() {
		return prevBCID;
	}

	public void setPrevBCID(String prevBCID) {
		this.prevBCID = prevBCID;
	}

}
