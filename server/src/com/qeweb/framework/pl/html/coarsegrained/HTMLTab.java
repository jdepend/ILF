package com.qeweb.framework.pl.html.coarsegrained;

import java.util.Map;

import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.pal.ViewComponent;
import com.qeweb.framework.pal.coarsegrained.Sheet;
import com.qeweb.framework.pal.coarsegrained.Tab;
import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pl.JSTemplate;
import com.qeweb.framework.pl.html.HTMLWebHelper;

public class HTMLTab extends Tab {

	@Override
	public void paint() {
		StringBuilder sbr = new StringBuilder();
		
		HTMLWebHelper.appendStartTag(sbr, "div");
		HTMLWebHelper.appendAttr(sbr, "class", "usual");
		HTMLWebHelper.appendEndTag(sbr);
		
		HTMLWebHelper.appendStartTag(sbr, "form");
		HTMLWebHelper.appendAttr(sbr, "id", getId());
		HTMLWebHelper.appendAttr(sbr, "name", getName());
		HTMLWebHelper.appendEndTag(sbr);
		//添加tab头
		HTMLWebHelper.appendStartSingleTag(sbr, "ul");
		
		for(Sheet sheet : getSheetList()) {			
			HTMLWebHelper.appendStartTag(sbr, "li");
			HTMLWebHelper.appendAttr(sbr, "style", "width:" + 100 / getSheetList().size() + "%");
			HTMLWebHelper.appendEndTag(sbr);
			HTMLWebHelper.appendStartTag(sbr, "a");
			HTMLWebHelper.appendAttr(sbr, "href", "#" + sheet.getId());
			HTMLWebHelper.appendEndTag(sbr);
			HTMLWebHelper.appendContent(sbr, sheet.getText());
			HTMLWebHelper.appendEndTag(sbr, "a");
			HTMLWebHelper.appendEndTag(sbr, "li");
		}
		HTMLWebHelper.appendEndTag(sbr, "ul");
		getPageContextInfo().write(sbr.toString());
		
		//添加每个sheet的内容
		for(Sheet sheet : getSheetList()) {
			sbr = new StringBuilder();
			HTMLWebHelper.appendStartTag(sbr, "div");
			HTMLWebHelper.appendAttr(sbr, "id", sheet.getId());
			HTMLWebHelper.appendEndTag(sbr);
			getPageContextInfo().write(sbr.toString());
			
			for(ViewComponent vc : sheet.getVcList()) {
				getPageContextInfo().write("<p>");
				vc.paint();
				getPageContextInfo().write("</p>");
			}
			
			getPageContextInfo().write("</div>");
		}
		
		Map<String, CommandButton> btnMap = getButtonList();
		if(!ContainerUtil.isNull(btnMap)) {
			getPageContextInfo().write("<br>");
			for(String key : btnMap.keySet()) {
				btnMap.get(key).paint();
			}
		}
		
		sbr = new StringBuilder();
		HTMLWebHelper.appendEndTag(sbr, "form");
		HTMLWebHelper.appendEndTag(sbr, "div");
		
		getPageContextInfo().write(JSTemplate.getJsContent("$('#" + getId() + " ul').idTabs();"));
	}
}
