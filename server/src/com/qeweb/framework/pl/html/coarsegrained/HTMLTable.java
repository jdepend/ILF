package com.qeweb.framework.pl.html.coarsegrained;

import java.util.List;

import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.pal.coarsegrained.Table;
import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.finegrained.text.Hidden;
import com.qeweb.framework.pl.PLWebHelper;
import com.qeweb.framework.pl.html.HTMLWebHelper;

public class HTMLTable extends Table {

	@Override
	public void paint() {
		StringBuilder sbr = new StringBuilder();
		HTMLWebHelper.appendStartTag(sbr, "div");
		HTMLWebHelper.appendAttr(sbr, "id", getId());
		HTMLWebHelper.appendAttr(sbr, "name", getName());
		HTMLWebHelper.appendAttr(sbr, "xtype", "table");
		HTMLWebHelper.appendEndTag(sbr);
		getPageContextInfo().write(sbr.toString());

		paintTableBody();
		paintPageBar();

		sbr = new StringBuilder();
		HTMLWebHelper.appendEndTag(sbr, "div");
		getPageContextInfo().write(sbr.toString());
	}
	
	protected String getDivClass(){
		return "active_pro_infor";
	}
	
	protected String getTableClass(){
		return null;
	}

	private void paintTableBody() {
		paintTableBtn();
		StringBuilder sbr = new StringBuilder();
		HTMLWebHelper.appendStartTag(sbr, "table");
		HTMLWebHelper.appendAttr(sbr, "class", getDivClass());
		HTMLWebHelper.appendAttr(sbr, "border", "0px");
		HTMLWebHelper.appendAttr(sbr, "width", "100%");
		HTMLWebHelper.appendEndTag(sbr);
		getPageContextInfo().write(sbr.toString());

		paintTableHead();

		sbr = new StringBuilder();
		HTMLWebHelper.appendEndTag(sbr, "table");
		getPageContextInfo().write(sbr.toString());
	}
	
	/**
	 * 画出翻页区域
	 */
	private void paintPageBar() {
		StringBuilder sbr = new StringBuilder();
		sbr.append("<br>");
		HTMLWebHelper.appendStartTag(sbr, "div");
		HTMLWebHelper.appendAttr(sbr, "align", "center");
		HTMLWebHelper.appendEndTag(sbr);
		HTMLWebHelper.appendEndTag(sbr, "div");
		getPageContextInfo().write(sbr.toString());
	}

	private void paintTableHead() {
		StringBuilder sbr = new StringBuilder();
		//selectionModel
		if(isCheckBoxMod() || isRadioMod()){
			HTMLWebHelper.appendStartTag(sbr, "th");
			HTMLWebHelper.appendAttr(sbr, "width", "30");
			HTMLWebHelper.appendEndTag(sbr);
			HTMLWebHelper.appendStartTag(sbr, "input");
			HTMLWebHelper.appendAttr(sbr, "type", getSelectionModel());
			HTMLWebHelper.appendAttr(sbr, "onclick", "selectAll(this,\""+getId()+"\");");
			HTMLWebHelper.appendEndTag(sbr);
			HTMLWebHelper.appendEndTag(sbr, "th");
		}
		//行级按钮的title
		if(ContainerUtil.isNotNull(getExpendBtnList())) {
			HTMLWebHelper.appendStartTag(sbr, "th");
			HTMLWebHelper.appendAttr(sbr, "width", PLWebHelper.getOperateWidth(getExpendBtnList()));
			HTMLWebHelper.appendEndTag(sbr);
			HTMLWebHelper.appendStartTag(sbr, "div");
			HTMLWebHelper.appendAttr(sbr, "style", "color:red");
			HTMLWebHelper.appendAttr(sbr, "align", "center");
			HTMLWebHelper.appendEndTag(sbr);
			HTMLWebHelper.appendContent(sbr, AppLocalization.getLocalization("table.operate"));
			HTMLWebHelper.appendEndTag(sbr, "div");
			HTMLWebHelper.appendEndTag(sbr, "th");
		}
		//其他列的title
		for (FinegrainedComponent fc : getDisplayFields().values()) {
			HTMLWebHelper.appendStartTag(sbr, "th");
			if (fc instanceof Hidden)
				HTMLWebHelper.appendAttr(sbr, "style", "display:none");
			HTMLWebHelper.appendEndTag(sbr);
			HTMLWebHelper.appendContent(sbr, fc.getText());
			HTMLWebHelper.appendEndTag(sbr, "th");
		}

		getPageContextInfo().write(sbr.toString());
	}

	/**
	 * 画出table级按钮
	 */
	private void paintTableBtn() {
		List<CommandButton> btnList = getNoExpendBtnList();

		getPageContextInfo().write("<div align='right'>");
		paintSMBtn();
		for(CommandButton btn : btnList) {
			btn.paint();
		}
		getPageContextInfo().write("</div>");
	}

	/**
	 * 画出selectionModel 按钮
	 */
	private void paintSMBtn() {
		if((isCheckBoxMod() || isRadioMod()) && isFill()){
			getPageContextInfo().write(
					"<input type='button' name='select' onclick='doSelect(\""
							+ getId()
							+ "\");' value='"
							+ AppLocalization.getLocalization("select")
							+ "'>");
		}
	}
}
