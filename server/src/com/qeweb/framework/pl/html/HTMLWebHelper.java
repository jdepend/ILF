package com.qeweb.framework.pl.html;


import com.qeweb.framework.bc.prop.Status;
import com.qeweb.framework.common.constant.ConstantAppProp;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pl.PLWebHelper;
import com.qeweb.framework.pl.html.finegrained.text.HTMLHidden;

/**
 * HTMLWebHelper为HTML的PL层提供了统一和简便的字符串拼装方法
 *
 */
public class HTMLWebHelper {

	final static public void appendHead(StringBuilder sbr, FinegrainedComponent fc) {
		appendStartTag(sbr, "div");
		appendAttr(sbr, "id", fc.getId());
		appendAttr(sbr, "style", "display:none", fc.getBc().getStatus().isHidden());
		appendEndTag(sbr);
		
		//添加必填项标记及细粒度组件展示名称, 隐藏域和label不需要添加
		if(fc instanceof HTMLHidden)
			return;
		
		if(StringUtils.isNotEmpty(fc.getText())) {
			appendStartTag(sbr, "div");
			String style = "float:left;text-align:right;";
			
			if(fc.getWidth() > 0) 
				style += "width:" + fc.getWidth() + "px";
			
			appendAttr(sbr, "style", style);
			appendEndTag(sbr);
			
			appendContent(sbr, PLWebHelper.requiredTextFlag(fc));
			appendContent(sbr, fc.getText() + ConstantAppProp.LABEL_SPLIT);
			appendTail(sbr);
		}
	}
	
	final static public void appendTail(StringBuilder sbr) {
		appendEndTag(sbr, "div");
	}
	
	/**
	 * 为dom节点的属性(attrName)添加值(attrValue), 该方法将改变sbr参数.
	 * <br>
	 * 如: 传递 style, width:100%, 则结果是: style='width:100%' 
	 * @param sbr  
	 * @param attrName
	 * @param attrValue
	 */
	final static public void appendAttr(StringBuilder sbr, String attrName, String attrValue) {
		if(StringUtils.isNotEmpty(attrValue))
			sbr.append(" ").append(attrName).append("='").append(attrValue).append("' ");
	}
	
	final static public void appendAttr(StringBuilder sbr, String attrName, String attrValue, boolean isAppend) {
		if(isAppend)
			appendAttr(sbr, attrName, attrValue);
	}
	
	final static public void appendAttr(StringBuilder sbr, String attrName, int attrValue) {
		sbr.append(" ").append(attrName).append("=").append(attrValue);
	}
	
	final static public void appendAttr(StringBuilder sbr, String attrValue, boolean isAppend) {
		if(isAppend)
			sbr.append(" ").append(attrValue).append(" ");
	}
	
	/**
	 * 添加dom节点的开始标签, 该方法将改变sbr参数.
	 * <br>
	 * appendStartTag
	 * @param sbr
	 * @param tagName
	 */
	final static public void appendStartTag(StringBuilder sbr, String tagName) {
		sbr.append("<").append(tagName);
	}
	
	final static public void appendEndTag(StringBuilder sbr, String tagName) {
		sbr.append("</").append(tagName).append(">");
	}
	
	final static public void appendEndTag(StringBuilder sbr) {
		sbr.append(">");
	}
	
	/**
	 * 带斜线的结束标签 ，即"/>"
	 * @param sbr
	 */
	final static public void appendSingleEndTag(StringBuilder sbr) {
		sbr.append("/>");
	}
	
	final static public void appendStartSingleTag(StringBuilder sbr, String tagName) {
		sbr.append("<").append(tagName).append(">");
	}
	
	final static public void appendContent(StringBuilder sbr, String content) {
		sbr.append(content);
	}
	
	/**
	 * 补空白td
	 * @return
	 */
	final static public String addSpaceTd() {
		return "<td></td>";
	}
	
	/**
	 * 获取页面控件的宽度
	 */
	final static public String getFcDefaultWidth() {
		return "55%";
	}
	
	/**
	 * 添加状态, 对于HTML的状态, 这里仅添加是否可交互/是否只读, 隐藏状态在appendHead中添加
	 * @param sbr
	 * @param status
	 */
	final static public void appendStatus(StringBuilder sbr, Status status) {
		appendAttr(sbr, "disabled", "disabled", status.isDisable());
		appendAttr(sbr, "readonly", "readonly", status.isReadonly());
	}
	
	final static public void appendNewObject(StringBuilder sbr, String objName, String obj) {
		sbr.append("var ").append(objName).append("= new ").append(obj);
	}
}
