package com.qeweb.framework.pl.ext.finegrained.other;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.sysbop.FileBOP;
import com.qeweb.framework.bc.sysbop.MultiFileBOP;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.utils.BoOperateUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.Schema;
import com.qeweb.framework.pal.finegrained.other.Anchor;
import com.qeweb.framework.pl.ext.ExtWebHelper;

/**
 * 细粒度组件anchor的ext封装实现
 *
 *
 */
public class ExtAnchor extends Anchor {

	@Override
	public void paintInOther() {
		StringBuilder sbr = new StringBuilder();

		ExtWebHelper.appendAttr(sbr, "fieldLabel", getText());
		ExtWebHelper.appendAttr(sbr, "viewlabel", getText());
		//非Ext属性，仅使用在布局时识别超链接时使用
		ExtWebHelper.appendAttr(sbr, "type", "anchor");
		ExtWebHelper.appendAttr(sbr, "id", getId());
		ExtWebHelper.appendAttr(sbr, "name", getName());
		if(getHistoryBC() != null)
			ExtWebHelper.appendAttr(sbr, "historyText", getHistoryBC().toText());  
		BOProperty bop = BoOperateUtil.getRealBop(getBc());
		if(bop instanceof OperateBOP){
			ExtWebHelper.appendAttr(sbr, "html", "<a id=\"" + getId() + "_anchor\" href=\"#\">"+ getBc().toText() + "</a>");
		}  
		else if(bop instanceof MultiFileBOP){
			String html = "";
			for(FileBOP itemBop : ((MultiFileBOP) bop).getFiles()){
				html += "<a href=\"" + itemBop.toLink() + "\" target=\"" + getTarget() + "\" >"+ itemBop.toText() + "</a> ";
			}
			ExtWebHelper.appendAttr(sbr, "html", html);
		}
		else if(StringUtils.isNotEmpty(getBc().toLink())){
			ExtWebHelper.appendAttr(sbr, "html", "<a href=\"" + getBc().toLink() + "\" target=\"" + getTarget() + "\" >" + getBc().toText() + "</a>");
		}
		
		ExtWebHelper.appendStatus(sbr, getStatus());
		Schema schema = getSchema();
		if(schema != null) {
			ExtWebHelper.appendAttr(sbr, "labelStyle", schema.getStyle());
		}
		else if(getStyle() != null) {
			ExtWebHelper.appendAttr(sbr, "labelStyle", getStyle());
		}
		ExtWebHelper.appendObjectTail(sbr, "style", "projectStyle.getAnchorStyle()");
		getPageContextInfo().write(sbr.toString());
	}

	@Override
	public void paintInTable() {
		StringBuilder sbr = new StringBuilder();
		ExtWebHelper.appendSingleValueHead(sbr, "fieldLabel", this);
		ExtWebHelper.appendAttr(sbr, "viewlabel", getText());
		ExtWebHelper.appendAttr(sbr, "id", getId());
		ExtWebHelper.appendAttr(sbr, "name", getName());
		ExtWebHelper.appendAttr(sbr, "xtype", "textfield");

		if(getBc() == null)
			getPageContextInfo().write(ExtWebHelper.removeEnd(sbr));

		ExtWebHelper.appendAttr(sbr, "value", getBc().toText());
		if(isValidateable())
			ExtWebHelper.appendAttr(sbr, "vtype", "bopRange");
		ExtWebHelper.appendStatus(sbr, getStatus());

		getPageContextInfo().write(ExtWebHelper.removeEnd(sbr));
	}
}
