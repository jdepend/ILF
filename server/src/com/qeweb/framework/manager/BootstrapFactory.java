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
import com.qeweb.framework.pal.finegrained.other.*;
import com.qeweb.framework.pal.finegrained.text.*;
import com.qeweb.framework.pl.bootstrap.BootstrapPage;
import com.qeweb.framework.pl.bootstrap.coarsegrained.*;
import com.qeweb.framework.pl.bootstrap.control.BootstrapCommandButton;
import com.qeweb.framework.pl.bootstrap.finegrained.other.BootstrapIcon;
import com.qeweb.framework.pl.bootstrap.finegrained.text.BootstrapLabel;
import com.qeweb.framework.pl.bootstrap.finegrained.text.BootstrapRadio;
import com.qeweb.framework.pl.bootstrap.finegrained.text.BootstrapTextArea;
import com.qeweb.framework.pl.bootstrap.finegrained.text.BootstrapTextField;
import com.qeweb.framework.pl.html.HTMLPage;
import com.qeweb.framework.pl.html.coarsegrained.*;
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
import com.qeweb.framework.pl.html.finegrained.text.*;

/**
 * bootstrap factory
 *
 */
public class BootstrapFactory extends VCFactory {
	
	@Override
	protected Page createPage() {
		return new BootstrapPage();
	}
	
	@Override
	protected TextField createTextField() {
		return new BootstrapTextField();
	}
	
	@Override
	protected Select createSelect() {
		return new HTMLSelect();
	}
	
	@Override
	protected CheckBox createCheckBox() {
		return new HTMLCheckBox();
	}
	
	@Override
	protected Radio createRadio() {
		return new BootstrapRadio();
	}
	
	@Override
	protected DateField createDateField() {
		return new HTMLDataField();
	}
	
	protected TextArea createTextArea() {
		return new BootstrapTextArea();
	}

	@Override
	protected OptionTranserSelect createOptionTranserSelect() {
		return new HTMLOptionTranserSelect();
	}
	
	@Override
	protected FileField createFileField() {
		return new HTMLFileField();
	}
	
	@Override
	protected Form createForm() {
		return new BootstrapForm();
	}
	
	@Override
	protected Table createTable() {
		return new HTMLTable();
	}

	@Override
	protected Password createPassword() {
		return new HTMLPassword();
	}

	@Override
	protected Label createLabel() {
		return new BootstrapLabel();
	}

	@Override
	protected CommandButton createCommandButton() {
		return new BootstrapCommandButton();
	}

	@Override
	protected Anchor createAnchor() {
		return new HTMLAnchor();
	}

	@Override
	protected Tree createTree() {
		return new HtmlTree();
	}

	@Override
	protected Menu createMenu() {
		return new HtmlMenu();
	}

	@Override
	protected CheckTree createCheckTree() {
		return new HTMLCheckTree();
	}
	
	@Override
	protected Hidden createHidden(){
		return new HTMLHidden();
	}
	
	@Override
	protected Image createImage() {
		return new HTMLImage();
	}
	
	@Override
	protected Tab createTab() {
		return new HTMLTab();
	}

	@Override
	protected Blank createBlank() {
		return null;
	}
	
	@Override
	protected SourceBtn createSourceBtn() {
		return new HTMLSourceBtn();
	}
	
	@Override
	protected Spinner createNumberField() {
		return new HTMLSpinner();
	}

	@Override
	protected MainMenu createMainLayout() {
		return null;
	}

	@Override
	protected Editor createEditor() {
		return new HtmlEditor();
	}

    @Override
    protected Navbar createNavbar() {
        return new BootstrapNavbar();
    }

    @Override
    protected Title createTitle() {
        return new BootstrapTitle();
    }

    @Override
    protected Icon createIcon() {
        return new BootstrapIcon();
    }

    @Override
    protected List createList() {
        return new BootstrapList();
    }

    @Override
    protected ListElement createListElement() {
        return new BootstrapListElement();
    }
}
