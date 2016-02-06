package com.qeweb.framework.manager;

import com.qeweb.framework.pal.Page;
import com.qeweb.framework.pal.ViewComponent;
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

/**
 * 获取展示组建Class
 */
public abstract class VCClassFactory {

	/**
	 * 获取具体的展示组件Class
	 * @param vcType
	 * @return
	 */
	public Class<?> getVCClass(Class<? extends ViewComponent> vcType) {
		Class<?> clasz;

		if(TextField.class.isAssignableFrom(vcType))
			clasz = getTextFieldClass();
		else if(Select.class.isAssignableFrom(vcType))
			clasz = getSelectClass();
		else if(Radio.class.isAssignableFrom(vcType))
			clasz = getRadioClass();
		else if(CheckBox.class.isAssignableFrom(vcType))
			clasz = getCheckBoxClass();
		else if(OptionTranserSelect.class.isAssignableFrom(vcType))
			clasz = getOptionTranserSelectClass();
		else if(DateField.class.isAssignableFrom(vcType))
			clasz = getDateFieldClass();
		else if(TextArea.class.isAssignableFrom(vcType))
			clasz = getTextAreaClass();
		else if(Password.class.isAssignableFrom(vcType))
			clasz = getPasswordClass();
		else if(Table.class.isAssignableFrom(vcType))
			clasz = getTableClass();
		else if(Form.class.isAssignableFrom(vcType))
			clasz = getFormClass();
		else if(Page.class.isAssignableFrom(vcType))
			clasz = getPageClass();
		else if(Label.class.isAssignableFrom(vcType))
			clasz = getLabelClass();
		else if(CommandButton.class.isAssignableFrom(vcType))
			clasz = getCommandButtonClass();
		else if(Anchor.class.isAssignableFrom(vcType))
			clasz = getAnchorClass();
		else if(Menu.class.isAssignableFrom(vcType))
			clasz = getMenuClass();
		else if(CheckTree.class.isAssignableFrom(vcType))
			clasz = getCheckTreeClass();
		else if(Tree.class.isAssignableFrom(vcType))
			clasz = getTreeClass();
		else if(Hidden.class.isAssignableFrom(vcType))
			clasz = getHiddenClass();
		else if(Image.class.isAssignableFrom(vcType))
			clasz = getImageClass();
		else if(Tab.class.isAssignableFrom(vcType))
			clasz = getTabClass();
		else if(FileField.class.isAssignableFrom(vcType))
			clasz = getFileFieldClass();
		else if(Blank.class.isAssignableFrom(vcType))
			clasz = getBlankClass();
		else if(SourceBtn.class.isAssignableFrom(vcType))
			clasz = getSourceBtnClass();
		else
			clasz = null;

		return clasz;
	}

	/**
	 * 以下方法获取ViewComponent的具体展现Class
	 *
	 */
	abstract protected Class<? extends Page> getPageClass();
	abstract protected Class<? extends TextField> getTextFieldClass();
	abstract protected Class<? extends Select> getSelectClass();
	abstract protected Class<? extends CheckBox> getCheckBoxClass();
	abstract protected Class<? extends Radio> getRadioClass();
	abstract protected Class<? extends DateField> getDateFieldClass();
	abstract protected Class<? extends TextArea> getTextAreaClass();
	abstract protected Class<? extends OptionTranserSelect> getOptionTranserSelectClass();
	abstract protected Class<? extends Form> getFormClass();
	abstract protected Class<? extends Table> getTableClass();
	abstract protected Class<? extends Password> getPasswordClass();
	abstract protected Class<? extends Label> getLabelClass();
	abstract protected Class<? extends CommandButton> getCommandButtonClass();
	abstract protected Class<? extends Anchor> getAnchorClass();
	abstract protected Class<? extends CheckTree> getCheckTreeClass();
	abstract protected Class<? extends Menu> getMenuClass();
	abstract protected Class<? extends Tree> getTreeClass();
	abstract protected Class<? extends Hidden> getHiddenClass();
	abstract protected Class<? extends Image> getImageClass();
	abstract protected Class<? extends Tab> getTabClass();
	abstract protected Class<? extends FileField> getFileFieldClass();
	abstract protected Class<? extends Blank> getBlankClass();
	abstract protected Class<? extends SourceBtn> getSourceBtnClass();
}
