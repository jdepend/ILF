package com.qeweb.framework.manager;

import com.qeweb.framework.pal.Page;
import com.qeweb.framework.pal.coarsegrained.CheckTree;
import com.qeweb.framework.pal.coarsegrained.Form;
import com.qeweb.framework.pal.coarsegrained.Menu;
import com.qeweb.framework.pal.coarsegrained.Tab;
import com.qeweb.framework.pal.coarsegrained.Table;
import com.qeweb.framework.pal.coarsegrained.Tree;
import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pal.finegrained.SourceBtn;
import com.qeweb.framework.pal.finegrained.enumcomp.CheckBox;
import com.qeweb.framework.pal.finegrained.enumcomp.OptionTranserSelect;
import com.qeweb.framework.pal.finegrained.enumcomp.Radio;
import com.qeweb.framework.pal.finegrained.enumcomp.Select;
import com.qeweb.framework.pal.finegrained.other.Anchor;
import com.qeweb.framework.pal.finegrained.other.Blank;
import com.qeweb.framework.pal.finegrained.other.DateField;
import com.qeweb.framework.pal.finegrained.other.FileField;
import com.qeweb.framework.pal.finegrained.other.Image;
import com.qeweb.framework.pal.finegrained.text.Hidden;
import com.qeweb.framework.pal.finegrained.text.Label;
import com.qeweb.framework.pal.finegrained.text.Password;
import com.qeweb.framework.pal.finegrained.text.TextArea;
import com.qeweb.framework.pal.finegrained.text.TextField;
import com.qeweb.framework.pl.html.HTMLPage;
import com.qeweb.framework.pl.html.coarsegrained.HTMLCheckTree;
import com.qeweb.framework.pl.html.coarsegrained.HTMLForm;
import com.qeweb.framework.pl.html.coarsegrained.HTMLTab;
import com.qeweb.framework.pl.html.coarsegrained.HTMLTable;
import com.qeweb.framework.pl.html.coarsegrained.HtmlMenu;
import com.qeweb.framework.pl.html.coarsegrained.HtmlTree;
import com.qeweb.framework.pl.html.control.HTMLCommandButton;
import com.qeweb.framework.pl.html.finegrained.HTMLSourceBtn;
import com.qeweb.framework.pl.html.finegrained.enumcomp.HTMLCheckBox;
import com.qeweb.framework.pl.html.finegrained.enumcomp.HTMLOptionTranserSelect;
import com.qeweb.framework.pl.html.finegrained.enumcomp.HTMLRadio;
import com.qeweb.framework.pl.html.finegrained.enumcomp.HTMLSelect;
import com.qeweb.framework.pl.html.finegrained.other.HTMLAnchor;
import com.qeweb.framework.pl.html.finegrained.other.HTMLDataField;
import com.qeweb.framework.pl.html.finegrained.other.HTMLFileField;
import com.qeweb.framework.pl.html.finegrained.other.HTMLImage;
import com.qeweb.framework.pl.html.finegrained.text.HTMLHidden;
import com.qeweb.framework.pl.html.finegrained.text.HTMLLabel;
import com.qeweb.framework.pl.html.finegrained.text.HTMLPassword;
import com.qeweb.framework.pl.html.finegrained.text.HTMLTextArea;
import com.qeweb.framework.pl.html.finegrained.text.HTMLTextField;


public class HtmlClassFactory extends VCClassFactory {

	@Override
	protected Class<? extends Page> getPageClass() {
		return HTMLPage.class;
	}

	@Override
	protected Class<? extends TextField> getTextFieldClass() {
		return HTMLTextField.class;
	}

	@Override
	protected Class<? extends Select> getSelectClass() {
		return HTMLSelect.class;
	}

	@Override
	protected Class<? extends CheckBox> getCheckBoxClass() {
		return HTMLCheckBox.class;
	}

	@Override
	protected Class<? extends Radio> getRadioClass() {
		return HTMLRadio.class;
	}

	@Override
	protected Class<? extends DateField> getDateFieldClass() {
		return HTMLDataField.class;
	}

	@Override
	protected Class<? extends TextArea> getTextAreaClass() {
		return HTMLTextArea.class;
	}

	@Override
	protected Class<? extends OptionTranserSelect> getOptionTranserSelectClass() {
		return HTMLOptionTranserSelect.class;
	}

	@Override
	protected Class<? extends Form> getFormClass() {
		return HTMLForm.class;
	}

	@Override
	protected Class<? extends Table> getTableClass() {
		return HTMLTable.class;
	}

	@Override
	protected Class<? extends Password> getPasswordClass() {
		return HTMLPassword.class;
	}

	@Override
	protected Class<? extends Label> getLabelClass() {
		return HTMLLabel.class;
	}

	@Override
	protected Class<? extends CommandButton> getCommandButtonClass() {
		return HTMLCommandButton.class;
	}

	@Override
	protected Class<? extends Anchor> getAnchorClass() {
		return HTMLAnchor.class;
	}

	@Override
	protected Class<? extends Tree> getTreeClass() {
		return HtmlTree.class;
	}

	@Override
	protected Class<? extends Menu> getMenuClass() {
		return HtmlMenu.class;
	}

	@Override
	protected Class<? extends CheckTree> getCheckTreeClass() {
		return HTMLCheckTree.class;
	}

	@Override
	protected Class<? extends Hidden> getHiddenClass() {
		return HTMLHidden.class;
	}

	@Override
	protected Class<? extends Image> getImageClass() {
		return HTMLImage.class;
	}

	@Override
	protected Class<? extends Tab> getTabClass() {
		return HTMLTab.class;
	}

	@Override
	protected Class<? extends FileField> getFileFieldClass() {
		return HTMLFileField.class;
	}

	@Override
	protected Class<? extends Blank> getBlankClass() {
		return null;
	}

	@Override
	protected Class<? extends SourceBtn> getSourceBtnClass() {
		return HTMLSourceBtn.class;
	}

}
