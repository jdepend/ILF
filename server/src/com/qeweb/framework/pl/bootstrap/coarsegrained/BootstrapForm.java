package com.qeweb.framework.pl.bootstrap.coarsegrained;

import com.qeweb.framework.common.constant.ConstantURL;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.MathUtil;
import com.qeweb.framework.pal.PageContextInfo;
import com.qeweb.framework.pal.coarsegrained.Form;
import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.layout.others.Layout;
import com.qeweb.framework.pal.layout.others.LayoutFactory;
import com.qeweb.framework.pal.layout.others.interpreter.Cell;
import com.qeweb.framework.pl.html.HTMLWebHelper;

import java.util.Map;

public class BootstrapForm extends Form {
	@Override
	public void paint() {
		PageContextInfo out = getPageContextInfo();
		StringBuilder sbr = new StringBuilder();

		HTMLWebHelper.appendStartTag(sbr, "div");
		HTMLWebHelper.appendAttr(sbr, "id", getId());
		HTMLWebHelper.appendAttr(sbr, "class", getDivClass());
		HTMLWebHelper.appendEndTag(sbr);

		HTMLWebHelper.appendStartSingleTag(sbr, "div");
		HTMLWebHelper.appendContent(sbr, getText());
		HTMLWebHelper.appendEndTag(sbr, "div");

		HTMLWebHelper.appendStartSingleTag(sbr, "div");
		HTMLWebHelper.appendStartTag(sbr, "form");
		HTMLWebHelper.appendAttr(sbr, "name", getName());
		HTMLWebHelper.appendAttr(sbr, "action", out.getContextPath()
				+ ConstantURL.CONTAINER_SUBMIT);
		if (getBc().getStatus().isHidden())
			HTMLWebHelper.appendAttr(sbr, "style", "display:none");
		HTMLWebHelper.appendAttr(sbr, "method", "post");
		HTMLWebHelper.appendEndTag(sbr);
		HTMLWebHelper.appendStartTag(sbr, "table");
		HTMLWebHelper.appendAttr(sbr, "width", "100%");
		HTMLWebHelper.appendAttr(sbr, "cellspacing", "0");
		HTMLWebHelper.appendAttr(sbr, "cellpadding", "0");
		HTMLWebHelper.appendAttr(sbr, "border", "1");
		HTMLWebHelper.appendEndTag(sbr);
		out.write(sbr.toString());

		paintFCList();
		paintBtnList();

		sbr = new StringBuilder();
		HTMLWebHelper.appendEndTag(sbr, "table");
		HTMLWebHelper.appendEndTag(sbr, "form");
		HTMLWebHelper.appendEndTag(sbr, "div");

		HTMLWebHelper.appendEndTag(sbr, "div");
		out.write(sbr.toString());
	}

	protected String getDivClass() {
		return "active_pro_infor";
	}

	private void paintBtnList() {
		if (ContainerUtil.isNotNull(getButtonList())) {
			PageContextInfo out = getPageContextInfo();
			StringBuilder sbr = new StringBuilder();
			HTMLWebHelper.appendStartTag(sbr, "td");
			HTMLWebHelper.appendAttr(sbr, "align", "right");
			Layout layout = LayoutFactory.createLayout(getLayoutStr(), LayoutFactory.LAYOUTTYPE_FORM);
			HTMLWebHelper.appendAttr(sbr, "colspan", layout.getColumns());
			HTMLWebHelper.appendEndTag(sbr);
			out.write(sbr.toString());
			Map<String, CommandButton> buttonList = getButtonList();
			for (String bind : buttonList.keySet()) {
				CommandButton commandButton = buttonList.get(bind);
				commandButton.paint();
			}
			sbr = new StringBuilder();
			HTMLWebHelper.appendEndTag(sbr, "td");
			out.write(sbr.toString());
		}
	}

	private void paintFCList() {
		if (ContainerUtil.isNull(getFcList()))
			return;

		PageContextInfo out = getPageContextInfo();
		Layout layout = LayoutFactory.createLayout(getLayoutStr(), LayoutFactory.LAYOUTTYPE_FORM);
		Map<String, Cell> cellMap = layout.getCellMap();

		float maxLabelWidth = getMaxLabelWidth();
		// 表单列数
		int columns = layout.getColumns();
		// 列宽度
		double tdWidht = MathUtil.div(100, columns, 2);
		// 细粒度组件列表<细粒度组件bind, 细粒度组件>
		Map<String, FinegrainedComponent> fcList = getFcList();

		out.write("<tr>");
		int collSpanSum = 0;
		int count = 0;
		for (String bopBind : fcList.keySet()) {
			count++;
			StringBuilder sbr = new StringBuilder();
			// 当前占用列数
			int thisCollSpan = cellMap.containsKey(bopBind) ? cellMap.get(
					bopBind).getCollSpan() : 1;
			// 当前占用的宽度
			double width = tdWidht * thisCollSpan;

			// 剩余列数
			int columnsLeft = columns - collSpanSum;
			// 判断当不够画<td>，用</tr><tr>结束新起一行
			if (count != 1 && columnsLeft < thisCollSpan) {
				for (int i = 0; i < columnsLeft; i++)
					out.write(HTMLWebHelper.addSpaceTd());
				out.write("</tr><tr>");
				collSpanSum = 0;
			}

			// 开始画<td>和组件内容
			HTMLWebHelper.appendStartTag(sbr, "td");
			if (thisCollSpan > 1)
				HTMLWebHelper.appendAttr(sbr, "colspan",
						String.valueOf(thisCollSpan));
			HTMLWebHelper.appendAttr(sbr, "width", String.valueOf(width) + "%");
			HTMLWebHelper.appendEndTag(sbr);
			out.write(sbr.toString());
			FinegrainedComponent fc = fcList.get(bopBind);
			fc.setWidth(maxLabelWidth);
			fc.paint();
			out.write("</td>");

			// 最后一行补充空白<td>
			if (count == fcList.size())
				for (int i = 0; i < (columnsLeft - thisCollSpan); i++)
					out.write(HTMLWebHelper.addSpaceTd());

			// 用于行内<td>数统计，换行后清零
			collSpanSum += thisCollSpan;
		}
		// 结束</tr>标签
		out.write("</tr>");
	}
}