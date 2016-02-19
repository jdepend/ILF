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

        HTMLWebHelper.appendStartTag(sbr, "form");
        HTMLWebHelper.appendAttr(sbr, "name", getName());
        HTMLWebHelper.appendAttr(sbr, "class", "form-horizontal");
        HTMLWebHelper.appendAttr(sbr, "action", out.getContextPath()
                + ConstantURL.CONTAINER_SUBMIT);
        if (getBc().getStatus().isHidden()) {
            HTMLWebHelper.appendAttr(sbr, "style", "display:none");
        }else{
            HTMLWebHelper.appendAttr(sbr, "style", "padding: 10px 10px");
        }
        HTMLWebHelper.appendAttr(sbr, "method", "post");
        HTMLWebHelper.appendEndTag(sbr);

		out.write(sbr.toString());

		paintFCList();
		paintBtnList();

		sbr = new StringBuilder();
        sbr.append("</form>");
		out.write(sbr.toString());
	}

	private void paintBtnList() {
		if (ContainerUtil.isNotNull(getButtonList())) {
			PageContextInfo out = getPageContextInfo();

            out.write("<div class=\"form-group\">\n");
            out.write("<div class=\"col-xs-12 text-center\">\n");

			Map<String, CommandButton> buttonList = getButtonList();
			for (String bind : buttonList.keySet()) {
				CommandButton commandButton = buttonList.get(bind);
				commandButton.paint();
			}

            out.write("</div>\n");
            out.write("</div>\n");
		}
	}

	private void paintFCList() {
		if (ContainerUtil.isNull(getFcList()))
			return;

		PageContextInfo out = getPageContextInfo();
		// 细粒度组件列表<细粒度组件bind, 细粒度组件>
		Map<String, FinegrainedComponent> fcList = getFcList();

		for (String bopBind : fcList.keySet()) {
            FinegrainedComponent fc = fcList.get(bopBind);

            String id = this.getBcId() + "." + fc.getId();
            String name = fc.getName();
            String text = fc.getText();

            out.write("<div class=\"form-group\" id=\"" + id + "_group\">\n");
            out.write("<label for=\"" + name + "\" class=\"col-xs-4 control-label\" style=\"text-align: right;\">" + text + "：</label>\n");
            out.write("<div class=\"col-xs-8\">\n");
            fc.paint();
            out.write("</div>\n");
            out.write("</div>\n");
	}
	}
}