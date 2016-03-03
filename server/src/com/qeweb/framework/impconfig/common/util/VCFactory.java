package com.qeweb.framework.impconfig.common.util;

import com.qeweb.framework.pal.finegrained.other.Icon;
import org.jdom.Element;

import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.finegrained.enumcomp.CheckBox;
import com.qeweb.framework.pal.finegrained.enumcomp.Radio;
import com.qeweb.framework.pal.finegrained.enumcomp.Select;
import com.qeweb.framework.pal.finegrained.other.DateField;
import com.qeweb.framework.pal.finegrained.text.Hidden;
import com.qeweb.framework.pal.finegrained.text.Label;
import com.qeweb.framework.pal.finegrained.text.Password;
import com.qeweb.framework.pal.finegrained.text.TextArea;
import com.qeweb.framework.pal.finegrained.text.TextField;

class VCFactory {

	/**
	 *
	 * @param el
	 * @return
	 */
	static FinegrainedComponent createFC(Element el) {
		FinegrainedComponent fc = null;
		String fcType = el.getName();

		if(StringUtils.isEqualIgnoreCase(AnalyzeJspUtil.DOC_TEXTFIELD, fcType)) {
			fc = (TextField) AppManager.createVC(TextField.class);
		}
		else if(StringUtils.isEqualIgnoreCase(AnalyzeJspUtil.DOC_LABEL, fcType)) {
			fc = (Label) AppManager.createVC(Label.class);
		}
		else if(StringUtils.isEqualIgnoreCase(AnalyzeJspUtil.DOC_TEXTAREA, fcType)) {
			fc = (TextArea) AppManager.createVC(TextArea.class);
		}
		else if(StringUtils.isEqualIgnoreCase(AnalyzeJspUtil.DOC_DATEFIELD, fcType)) {
			fc = (DateField) AppManager.createVC(DateField.class);
		}
		else if(StringUtils.isEqualIgnoreCase(AnalyzeJspUtil.DOC_HIDDEN, fcType)) {
			fc = (Hidden) AppManager.createVC(Hidden.class);
		}
		else if(StringUtils.isEqualIgnoreCase(AnalyzeJspUtil.DOC_PASSWORD, fcType)) {
			fc = (Password) AppManager.createVC(Password.class);
		}
		else if(StringUtils.isEqualIgnoreCase(AnalyzeJspUtil.DOC_SELECT, fcType)) {
			fc = (Select) AppManager.createVC(Select.class);
		}
		else if(StringUtils.isEqualIgnoreCase(AnalyzeJspUtil.DOC_RADIO, fcType)) {
			fc = (Radio) AppManager.createVC(Radio.class);
		}
		else if(StringUtils.isEqualIgnoreCase(AnalyzeJspUtil.DOC_CHECKBOX, fcType)) {
			fc = (CheckBox) AppManager.createVC(CheckBox.class);
		}
        else if(StringUtils.isEqualIgnoreCase(AnalyzeJspUtil.DOC_ICON, fcType)) {
            fc = (Icon) AppManager.createVC(Icon.class);
        }

		return fc;
	}

	/**
	 * @return
	 */
	static CommandButton createBtn() {
		return (CommandButton) AppManager.createVC(CommandButton.class);
	}
}
