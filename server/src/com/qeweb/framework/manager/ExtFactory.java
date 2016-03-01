package com.qeweb.framework.manager;

import com.qeweb.framework.pal.MainMenu;
import com.qeweb.framework.pal.Page;
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
import com.qeweb.framework.pl.ext.finegrained.text.ExtEditor;
import com.qeweb.framework.pl.ext.finegrained.text.ExtHidden;
import com.qeweb.framework.pl.ext.finegrained.text.ExtLabel;
import com.qeweb.framework.pl.ext.finegrained.text.ExtSpinner;
import com.qeweb.framework.pl.ext.finegrained.text.ExtPassword;
import com.qeweb.framework.pl.ext.finegrained.text.ExtTextArea;
import com.qeweb.framework.pl.ext.finegrained.text.ExtTextField;
import com.qeweb.framework.pl.ext.mainmenu.ExtMainMenu;

/**
 * ExtFactory
 *
 */
public class ExtFactory extends VCFactory {
	
	@Override
	protected Page createPage() {
		return new ExtPage();
	}
	
	@Override
	protected TextField createTextField() {
		return new ExtTextField();
	}
	
	@Override
	protected Select createSelect() {
		return new ExtSelect();
	}
	
	@Override
	protected CheckBox createCheckBox() {
		return new ExtCheckBox();
	}
	
	@Override
	protected Radio createRadio() {
		return new ExtRadio();
	}
	
	@Override
	protected DateField createDateField() {
		return new ExtDateField();
	}
	
	@Override
	protected TextArea createTextArea() {
		return new ExtTextArea();
	}
	
	@Override
	protected OptionTranserSelect createOptionTranserSelect() {
		return new ExtOptionTranserSelect();
	}
	
	@Override
	protected Form createForm() {
		return new ExtForm();
	}
	
	@Override
	protected Table createTable() {
		return new ExtTable();
	}

	@Override
	protected Password createPassword() {
		return new ExtPassword();
	}

	@Override
	protected Label createLabel() {
		return new ExtLabel();
	}
	
	@Override
	protected CommandButton createCommandButton(){
		return new ExtCommandButton();
	}

	@Override
	protected Anchor createAnchor() {
		return new ExtAnchor();
	}

	@Override
	protected Tree createTree() {
		return new ExtTree();
	}

	@Override
	protected Menu createMenu() {
		return new ExtMenu();
	}
	
	@Override
	protected MainMenu createMainLayout() {
		return new ExtMainMenu();
	}

	@Override
	protected CheckTree createCheckTree() {
		return new ExtCheckTree();
	}
	
	@Override
	protected Hidden createHidden(){
		return new ExtHidden();
	}
	
	@Override
	protected Image createImage() {
		return new ExtImage();
	}
	
	@Override
	protected Tab createTab() {
		return new ExtTab();
	}
	
	@Override
	protected FileField createFileField() {
		return new ExtFileField();
	}

	@Override
	protected Blank createBlank() {
		return new ExtBlank();
	}
	
	@Override
	protected SourceBtn createSourceBtn() {
		return new ExtSourceBtn();
	}

	@Override
	protected Spinner createNumberField() {
		return new ExtSpinner();
	}

	@Override
	protected Editor createEditor() {
		return new ExtEditor();
	}

    @Override
    protected Navbar createNavbar() {
        return null;
    }

    @Override
    protected Title createTitle() {
        return null;
    }
}
