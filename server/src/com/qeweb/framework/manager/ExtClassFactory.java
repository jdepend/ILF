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
import com.qeweb.framework.pl.ext.ExtPage;
import com.qeweb.framework.pl.ext.coarsegrained.ExtCheckTree;
import com.qeweb.framework.pl.ext.coarsegrained.ExtForm;
import com.qeweb.framework.pl.ext.coarsegrained.ExtMenu;
import com.qeweb.framework.pl.ext.coarsegrained.ExtTab;
import com.qeweb.framework.pl.ext.coarsegrained.ExtTable;
import com.qeweb.framework.pl.ext.coarsegrained.ExtTree;
import com.qeweb.framework.pl.ext.control.ExtCommandButton;
import com.qeweb.framework.pl.ext.finegrained.ExtSourceBtn;
import com.qeweb.framework.pl.ext.finegrained.enumcomp.ExtCheckBox;
import com.qeweb.framework.pl.ext.finegrained.enumcomp.ExtOptionTranserSelect;
import com.qeweb.framework.pl.ext.finegrained.enumcomp.ExtRadio;
import com.qeweb.framework.pl.ext.finegrained.enumcomp.ExtSelect;
import com.qeweb.framework.pl.ext.finegrained.other.ExtAnchor;
import com.qeweb.framework.pl.ext.finegrained.other.ExtBlank;
import com.qeweb.framework.pl.ext.finegrained.other.ExtDateField;
import com.qeweb.framework.pl.ext.finegrained.other.ExtFileField;
import com.qeweb.framework.pl.ext.finegrained.other.ExtImage;
import com.qeweb.framework.pl.ext.finegrained.text.ExtHidden;
import com.qeweb.framework.pl.ext.finegrained.text.ExtLabel;
import com.qeweb.framework.pl.ext.finegrained.text.ExtPassword;
import com.qeweb.framework.pl.ext.finegrained.text.ExtTextArea;
import com.qeweb.framework.pl.ext.finegrained.text.ExtTextField;


public class ExtClassFactory extends VCClassFactory {

	@Override
	protected Class<? extends Page> getPageClass() {
		return ExtPage.class;
	}

	@Override
	protected Class<? extends TextField> getTextFieldClass() {
		return ExtTextField.class;
	}

	@Override
	protected Class<? extends Select> getSelectClass() {
		return ExtSelect.class;
	}

	@Override
	protected Class<? extends CheckBox> getCheckBoxClass() {
		return ExtCheckBox.class;
	}

	@Override
	protected Class<? extends Radio> getRadioClass() {
		return ExtRadio.class;
	}

	@Override
	protected Class<? extends DateField> getDateFieldClass() {
		return ExtDateField.class;
	}

	@Override
	protected Class<? extends TextArea> getTextAreaClass() {
		return ExtTextArea.class;
	}

	@Override
	protected Class<? extends OptionTranserSelect> getOptionTranserSelectClass() {
		return ExtOptionTranserSelect.class;
	}

	@Override
	protected Class<? extends Form> getFormClass() {
		return ExtForm.class;
	}

	@Override
	protected Class<? extends Table> getTableClass() {
		return ExtTable.class;
	}

	@Override
	protected Class<? extends Password> getPasswordClass() {
		return ExtPassword.class;
	}

	@Override
	protected Class<? extends Label> getLabelClass() {
		return ExtLabel.class;
	}

	@Override
	protected Class<? extends CommandButton> getCommandButtonClass() {
		return ExtCommandButton.class;
	}

	@Override
	protected Class<? extends Anchor> getAnchorClass() {
		return ExtAnchor.class;
	}

	@Override
	protected Class<? extends Tree> getTreeClass() {
		return ExtTree.class;
	}

	@Override
	protected Class<? extends Menu> getMenuClass() {
		return ExtMenu.class;
	}

	@Override
	protected Class<? extends CheckTree> getCheckTreeClass() {
		return ExtCheckTree.class;
	}

	@Override
	protected Class<? extends Hidden> getHiddenClass() {
		return ExtHidden.class;
	}

	@Override
	protected Class<? extends Image> getImageClass() {
		return ExtImage.class;
	}

	@Override
	protected Class<? extends Tab> getTabClass() {
		return ExtTab.class;
	}

	@Override
	protected Class<? extends FileField> getFileFieldClass() {
		return ExtFileField.class;
	}

	@Override
	protected Class<? extends Blank> getBlankClass() {
		return ExtBlank.class;
	}

	@Override
	protected Class<? extends SourceBtn> getSourceBtnClass() {
		return ExtSourceBtn.class;
	}

}
