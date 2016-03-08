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
import com.qeweb.framework.pal.finegrained.text.Editor;
import com.qeweb.framework.pal.finegrained.text.Hidden;
import com.qeweb.framework.pal.finegrained.text.Label;
import com.qeweb.framework.pal.finegrained.text.Spinner;
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
import com.qeweb.framework.pl.html.finegrained.text.HTMLSpinner;
import com.qeweb.framework.pl.html.finegrained.text.HTMLPassword;
import com.qeweb.framework.pl.html.finegrained.text.HTMLTextArea;
import com.qeweb.framework.pl.html.finegrained.text.HTMLTextField;
import com.qeweb.framework.pl.html.finegrained.text.HtmlEditor;

/**
 * html factory
 *
 */
public class HtmlFactory extends VCFactory {
	
	@Override
	protected Page createPage() {
		return new HTMLPage();
	}
	
	@Override
	protected TextField createTextField() {
		return new HTMLTextField();
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
		return new HTMLRadio();
	}
	
	@Override
	protected DateField createDateField() {
		return new HTMLDataField();
	}
	
	protected TextArea createTextArea() {
		return new HTMLTextArea();
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
		return new HTMLForm();
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
		return new HTMLLabel();
	}

	@Override
	protected CommandButton createCommandButton() {
		return new HTMLCommandButton();
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
        return null;
    }

    @Override
    protected Title createTitle() {
        return null;
    }

    @Override
    protected Icon createIcon() {
        return null;
    }

    @Override
    protected List createList() {
        return null;
    }

    @Override
    protected ListElement createListElement() {
        return null;
    }
}
