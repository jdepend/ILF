package com.qeweb.framework.pal.handler.bean;

import com.qeweb.framework.pal.PageContextInfo;

/**
 * ContainerBean 存储粗粒度组件标签的基本信息
 */
public class ContainerBean {
	
    private String style;							// css样式
    private String id;								//粗料度组件的id
    private String bind;							//绑定的bo对象名称
    private String text;							//国际化标识
    private String param;							//是否需要接受参数
	private String layout;							//布局管理器
	private String icon;							//组件的图标
	private boolean header;							//是否显示头部
	private PageContextInfo pageContextInfo;		//上下文信息
	
	public String getStyle() {
		return style;
	}
	public String getLayout() {
		return layout;
	}
	public void setLayout(String layout) {
		this.layout = layout;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBind() {
		return bind;
	}
	public void setBind(String bind) {
		this.bind = bind;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public PageContextInfo getPageContextInfo() {
		return pageContextInfo;
	}
	public void setPageContextInfo(PageContextInfo pageContextInfo) {
		this.pageContextInfo = pageContextInfo;
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
