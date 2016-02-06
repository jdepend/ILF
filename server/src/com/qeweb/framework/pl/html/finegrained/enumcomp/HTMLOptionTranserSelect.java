package com.qeweb.framework.pl.html.finegrained.enumcomp;

import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.Status;
import com.qeweb.framework.pal.finegrained.enumcomp.OptionTranserSelect;
import com.qeweb.framework.pl.html.HTMLWebHelper;

public class HTMLOptionTranserSelect extends OptionTranserSelect {
	
	@Override
	public void paint() {
		BOProperty bop = getBc();
		StringBuilder sbr = new StringBuilder();
		
		String sourceId = getId() + "_source";
		String targetId = getId() + "_target";
		
		HTMLWebHelper.appendHead(sbr, this);
		
		HTMLWebHelper.appendStartTag(sbr, "table");
		HTMLWebHelper.appendAttr(sbr, "width", "90%");
		HTMLWebHelper.appendEndTag(sbr);
		HTMLWebHelper.appendStartTag(sbr, "tr");
		HTMLWebHelper.appendAttr(sbr, "width", "100%");
		HTMLWebHelper.appendEndTag(sbr);
		HTMLWebHelper.appendStartTag(sbr, "td");
		HTMLWebHelper.appendAttr(sbr, "width", "45%");
		HTMLWebHelper.appendEndTag(sbr);
		//source multiple select
		HTMLWebHelper.appendStartTag(sbr, "select");
		HTMLWebHelper.appendAttr(sbr, "name", getName());
		HTMLWebHelper.appendAttr(sbr, "id", sourceId);
		HTMLWebHelper.appendStatus(sbr, bop.getStatus());
		HTMLWebHelper.appendAttr(sbr, "size", "10");
		HTMLWebHelper.appendAttr(sbr, "style", "width:100%");
		HTMLWebHelper.appendAttr(sbr, "multiple", "multiple");
		HTMLWebHelper.appendEndTag(sbr);
		addOptions(sbr, getSourceOptions(bop));
		HTMLWebHelper.appendEndTag(sbr, "select");
		HTMLWebHelper.appendEndTag(sbr, "td");
		//button
		HTMLWebHelper.appendStartTag(sbr, "td");
		HTMLWebHelper.appendAttr(sbr, "width", "10%");
		HTMLWebHelper.appendEndTag(sbr);
		addButton(sbr, "&gt", getFunAdd(sourceId, targetId), bop.getStatus());
		addButton(sbr, "&gt;&gt", getFunAddAll(sourceId, targetId), bop.getStatus());
		addButton(sbr, "&lt", getFunAdd(targetId, sourceId), bop.getStatus());
		addButton(sbr, "&lt;&lt", getFunAddAll(targetId, sourceId), bop.getStatus());
		HTMLWebHelper.appendEndTag(sbr, "td");
		//target multiple select
		HTMLWebHelper.appendStartTag(sbr, "td");
		HTMLWebHelper.appendAttr(sbr, "width", "45%");
		HTMLWebHelper.appendEndTag(sbr);
		HTMLWebHelper.appendStartTag(sbr, "select");
		HTMLWebHelper.appendAttr(sbr, "name", getName());
		HTMLWebHelper.appendAttr(sbr, "id", targetId);
		HTMLWebHelper.appendStatus(sbr, bop.getStatus());
		HTMLWebHelper.appendAttr(sbr, "size", "10");
		HTMLWebHelper.appendAttr(sbr, "style", "width:100%");
		HTMLWebHelper.appendAttr(sbr, "multiple", "multiple");
		HTMLWebHelper.appendEndTag(sbr);
		addOptions(sbr, getTargetOptions(bop));
		HTMLWebHelper.appendEndTag(sbr, "select");
		HTMLWebHelper.appendEndTag(sbr, "td");
		
		HTMLWebHelper.appendEndTag(sbr, "tr");
		HTMLWebHelper.appendEndTag(sbr, "table");
		
		HTMLWebHelper.appendTail(sbr);
		getPageContextInfo().write(sbr.toString());
	}

	/**
	 * 添加双向选择的内容
	 * @param sbr
	 * @param bop
	 */
	private void addOptions(StringBuilder sbr, Map<String, String> store) {
		for (String key : store.keySet()) {
			HTMLWebHelper.appendStartTag(sbr, "option");
			HTMLWebHelper.appendAttr(sbr, "value", key);
			HTMLWebHelper.appendEndTag(sbr);
			HTMLWebHelper.appendContent(sbr, store.get(key));
			HTMLWebHelper.appendEndTag(sbr, "option");
		}
	}
	
	/**
	 * 双向选择按钮触发的函数OptTrans.add
	 * @param sourceId
	 * @param targetId
	 * @return
	 */
	private String getFunAdd(String sourceId, String targetId) {
		return "OptTrans.add('" + sourceId + "','" + targetId + "')";
	}
	
	/**
	 * 双向选择按钮触发的函数OptTrans.addAll
	 * @param sourceId
	 * @param targetId
	 * @return
	 */
	private String getFunAddAll(String sourceId, String targetId) {
		return "OptTrans.addAll('" + sourceId + "','" + targetId + "')";
	}
	
	/**
	 * 添加双向选择按钮
	 * @param sbr
	 * @param value
	 * @param onclick
	 * @param status
	 */
	private void addButton(StringBuilder sbr, String text, String onclick, Status status) {
		HTMLWebHelper.appendStartSingleTag(sbr, "p");
		HTMLWebHelper.appendStartTag(sbr, "input");
		HTMLWebHelper.appendStatus(sbr, status);
		HTMLWebHelper.appendAttr(sbr, "width", "10%");
		HTMLWebHelper.appendAttr(sbr, "type", "button");
		HTMLWebHelper.appendAttr(sbr, "value", text);
		HTMLWebHelper.appendAttr(sbr, "style", "width:100%");
		sbr.append("onclick=").append(onclick);
		HTMLWebHelper.appendEndTag(sbr);
		HTMLWebHelper.appendEndTag(sbr, "p");
	}
}
