package com.qeweb.framework.manager;

import com.qeweb.framework.impconfig.ddt.use.bean.DDTSchema;
import com.qeweb.framework.pal.MainMenu;
import com.qeweb.framework.pal.Page;
import com.qeweb.framework.pal.ViewComponent;
import com.qeweb.framework.pal.coarsegrained.*;
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
import com.qeweb.framework.pal.finegrained.text.Editor;
import com.qeweb.framework.pal.finegrained.text.Hidden;
import com.qeweb.framework.pal.finegrained.text.Label;
import com.qeweb.framework.pal.finegrained.text.Spinner;
import com.qeweb.framework.pal.finegrained.text.Password;
import com.qeweb.framework.pal.finegrained.text.TextArea;
import com.qeweb.framework.pal.finegrained.text.TextField;

/**
 * View Component Factory
 *
 */
public abstract class VCFactory {

	/**
	 * 创建一个展示层组件
	 * @param vcType vc的类型
	 * @return
	 */
	public ViewComponent createVC(Class<? extends ViewComponent> vcType) {
		ViewComponent vc;

		if(TextField.class.isAssignableFrom(vcType))
			vc = createTextField();
		else if(Select.class.isAssignableFrom(vcType))
			vc = createSelect();
		else if(Radio.class.isAssignableFrom(vcType))
			vc = createRadio();
		else if(CheckBox.class.isAssignableFrom(vcType))
			vc = createCheckBox();
		else if(OptionTranserSelect.class.isAssignableFrom(vcType))
			vc = createOptionTranserSelect();
		else if(DateField.class.isAssignableFrom(vcType))
			vc = createDateField();
		else if(TextArea.class.isAssignableFrom(vcType))
			vc = createTextArea();
		else if(Password.class.isAssignableFrom(vcType))
			vc = createPassword();
		else if(Table.class.isAssignableFrom(vcType))
			vc = createTable();
		else if(Form.class.isAssignableFrom(vcType))
			vc = createForm();
		else if(Page.class.isAssignableFrom(vcType))
			vc = createPage();
		else if(Label.class.isAssignableFrom(vcType))
			vc = createLabel();
		else if(CommandButton.class.isAssignableFrom(vcType))
			vc = createCommandButton();
		else if(Anchor.class.isAssignableFrom(vcType))
			vc = createAnchor();
		else if(Menu.class.isAssignableFrom(vcType))
			vc = createMenu();
		else if(CheckTree.class.isAssignableFrom(vcType))
			vc = createCheckTree();
		else if(Tree.class.isAssignableFrom(vcType))
			vc = createTree();
		else if(Hidden.class.isAssignableFrom(vcType))
			vc = createHidden();
		else if(Image.class.isAssignableFrom(vcType))
			vc = createImage();
		else if(Tab.class.isAssignableFrom(vcType))
			vc = createTab();
		else if(FileField.class.isAssignableFrom(vcType))
			vc = createFileField();
		else if(Blank.class.isAssignableFrom(vcType))
			vc = createBlank();
		else if(SourceBtn.class.isAssignableFrom(vcType))
			vc = createSourceBtn();
		else if(Spinner.class.isAssignableFrom(vcType))
			vc = createNumberField();
		else if(MainMenu.class.isAssignableFrom(vcType))
			vc = createMainLayout();
		else if(Editor.class.isAssignableFrom(vcType))
			vc = createEditor();
        else if(Navbar.class.isAssignableFrom(vcType))
            vc = createNavbar();
        else if(Title.class.isAssignableFrom(vcType))
            vc = createTitle();
		else
			vc = null;

		return vc;
	}

	/**
	 * 创建一个展示层组件
	 * @param vcType DDT方案中vc的类型
	 * @return
	 */
	public ViewComponent createVC(int vcType) {
		ViewComponent vc;

		if(vcType == DDTSchema.TEXTFIELD)
			vc = createTextField();
		else if(vcType == DDTSchema.SELECT)
			vc = createSelect();
		else if(vcType == DDTSchema.RADIO)
			vc = createRadio();
		else if(vcType == DDTSchema.CHECKBOX)
			vc = createCheckBox();
		else if(vcType == DDTSchema.OPTIONTRANSERSELECT)
			vc = createOptionTranserSelect();
		else if(vcType == DDTSchema.TEXTAREA)
			vc = createTextArea();
		else if(vcType == DDTSchema.PASSWORD)
			vc = createPassword();
		else if(vcType == DDTSchema.LABEL)
			vc = createLabel();
		else if(vcType == DDTSchema.ANCHOR)
			vc = createAnchor();
		else if(vcType == DDTSchema.HIDDEN)
			vc = createHidden();
		else if(vcType == DDTSchema.EDITOR)
			vc = createEditor();
		else
			vc = null;

		return vc;
	}

	/**
	 * 以下方法创建ViewComponent的具体展现
	 *
	 */
	abstract protected Page createPage();
	abstract protected TextField createTextField();
	abstract protected Select createSelect();
	abstract protected CheckBox createCheckBox();
	abstract protected Radio createRadio();
	abstract protected DateField createDateField();
	abstract protected TextArea createTextArea();
	abstract protected OptionTranserSelect createOptionTranserSelect();
	abstract protected Form createForm();
	abstract protected Table createTable();
	abstract protected Password createPassword();
	abstract protected Label createLabel();
	abstract protected CommandButton createCommandButton();
	abstract protected Anchor createAnchor();
	abstract protected Tree createTree();
	abstract protected Menu createMenu();
	abstract protected MainMenu createMainLayout();
	abstract protected CheckTree createCheckTree();
	abstract protected Hidden createHidden();
	abstract protected Image createImage();
	abstract protected Tab createTab();
	abstract protected FileField createFileField();
	abstract protected Blank createBlank();
	abstract protected SourceBtn createSourceBtn();
	abstract protected Spinner createNumberField();
	abstract protected Editor createEditor();
    abstract protected Navbar createNavbar();
    abstract protected Title createTitle();
}
