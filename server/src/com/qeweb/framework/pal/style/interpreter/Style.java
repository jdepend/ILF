package com.qeweb.framework.pal.style.interpreter;

import com.qeweb.framework.common.utils.StringUtils;


/**
 * CSS样式类
 *
 */
public class Style {
	
	private String formStyleContent;		//表单样式
	private String tableStyleContent;		//表格样式
	private String css;						//覆盖默认样式单的css文件名
	//css 样式内容分隔符
	final private String CSS_CONTENT_SEPARATOR = ",";
	//css样式分隔符
	final private String CSS_SEPARATOR = ";";
	
	public String getFormStyleContent() {
		if(StringUtils.isNotEmpty(formStyleContent))
			return formStyleContent.replaceAll(CSS_CONTENT_SEPARATOR, CSS_SEPARATOR);
		
		return formStyleContent;
	}
	
	public void setFormStyleContent(String formStyleContent) {
		if(StringUtils.isEmpty(this.formStyleContent))
			this.formStyleContent = formStyleContent + CSS_SEPARATOR;
		else 
			this.formStyleContent += CSS_CONTENT_SEPARATOR + formStyleContent + CSS_SEPARATOR;
	}
	
	public String getTableStyleContent() {
		if(StringUtils.isNotEmpty(tableStyleContent))
			return tableStyleContent.replaceAll(CSS_CONTENT_SEPARATOR, CSS_SEPARATOR);
		
		return tableStyleContent;
	}
	
	public void setTableStyleContent(String tableStyleContent) {
		if(StringUtils.isEmpty(this.tableStyleContent))
			this.tableStyleContent = tableStyleContent;
		else 
			this.tableStyleContent += CSS_CONTENT_SEPARATOR + tableStyleContent;
	}
	
	public String getCss() {
		return css;
	}
	
	public void setCss(String css) {
		this.css = css;
	}
}
