package com.qeweb.framework.common.dataisland;

import java.util.Collection;

import org.jdom.Element;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.constant.ConstantDataIsland;
import com.qeweb.framework.common.constant.ConstantSplit;
import com.qeweb.framework.common.pageflow.ContextUtil;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.XMLUtil;

public class XMLDataIslandHelper implements ConstantDataIsland {

	final static public void appendHead(StringBuilder sbr) {
		appendStartTag(sbr, DATAISLAND);
		appendAttr(sbr, SESSION_TICKET, ContextUtil.getSessionTicket());
		appendAttr(sbr, TOKEN_TICKET, ContextUtil.getTokenTicket());
		appendAttr(sbr, SOURCEPAGE, Envir.getRequestURI());
		appendEndTag(sbr);
	}
	
	final static public void appendTail(StringBuilder sbr) {
		appendEndTag(sbr, DATAISLAND);
	}
	
	/**
	 * 为dom节点的属性(attrName)添加值(attrValue), 该方法将改变sbr参数.
	 * <br>.
	 * 例: 
	 * sbr = "<table";
	 * appendAttr(sbr, "style", "width:100%");
	 * 结果:  
	 * sbr = "<table style='width:100%' ";
	 * @param sbr  
	 * @param attrName
	 * @param attrValue
	 */
	final static public void appendAttr(StringBuilder sbr, String attrName, String attrValue) {
		if(StringUtils.isNotEmpty(attrValue))
			sbr.append(" ").append(attrName).append("='").append(attrValue).append("' ");
	}
	
	final static public void appendAttr(StringBuilder sbr, String attrName, long attrValue) {
		appendAttr(sbr, attrName, attrValue + "");
	}
	
	final static public void appendAttr(StringBuilder sbr, String attrName) {
		sbr.append(" ").append(attrName).append("='' ");
	}
	
	final static public void appendAttr(StringBuilder sbr, String attrName, Collection<String> colls) {
		if(ContainerUtil.isNull(colls))
			return;
		
		appendAttr(sbr, attrName, StringUtils.revertColl2Str(colls, ConstantSplit.COMMA_SPLIT));
	}
	
	final static public void appendAttr(StringBuilder sbr, String attrName, String attrValue, boolean isAppend) {
		if(isAppend)
			appendAttr(sbr, attrName, attrValue);
	}
	
	final static public void appendAttr(StringBuilder sbr, String attrName, boolean isAppend) {
		if(isAppend)
			appendAttr(sbr, attrName, "true");
	}
	
	/**
	 * 添加dom节点的开始标签, 该方法将改变sbr参数.
	 * 例：
	 * XMLDataIslandHelper.appendStartTag(sbr, "operate");
	 * 输出：&ltoperate
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
	
	final static public void appendSingleEndTag(StringBuilder sbr) {
		sbr.append("/>");
	}
	
	final static public void appendStartSingleTag(StringBuilder sbr, String tagName) {
		sbr.append("<").append(tagName).append(">");
	}
	
	final static public void appendContent(StringBuilder sbr, String content) {
		sbr.append(encodeContent(content));
	}
	
	final static public void appendContentWithCData(StringBuilder sbr, String tagName, String content) {
		appendStartSingleTag(sbr, tagName);
		appendContent(sbr, content);
		appendEndTag(sbr, tagName);
	}
	
	final static public void appendContent(StringBuilder sbr, String tagName, String content) {
		appendStartSingleTag(sbr, tagName);
		sbr.append(content);
		appendEndTag(sbr, tagName);
	}
	
	/**
	 * boEl的BO_OPERATIONSTATUS 是否是init
	 * @param boEl
	 * @return
	 */
	final static public boolean isInitStatus(Element boEl) {
		return StringUtils.isEmpty(boEl.getAttributeValue(BO_OPERATIONSTATUS))
				|| StringUtils.isEqual(STATEMACHINE_INIT, boEl.getAttributeValue(BO_OPERATIONSTATUS));
	}
	
	final static public String encodeContent(String content) {
		if(StringUtils.isNotEmpty(content))
			return addCDATA(XMLUtil.encode(content));
		return "";
	}
	
	final static public String addCDATA(String content) {
		return "<![CDATA[" + content + "]]>";
	}
}
