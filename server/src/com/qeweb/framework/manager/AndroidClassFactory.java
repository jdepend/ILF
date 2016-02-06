package com.qeweb.framework.manager;

import com.qeweb.framework.pal.Page;
import com.qeweb.framework.pal.coarsegrained.CheckTree;
import com.qeweb.framework.pal.coarsegrained.Form;
import com.qeweb.framework.pal.coarsegrained.Menu;
import com.qeweb.framework.pal.coarsegrained.Tree;
import com.qeweb.framework.pl.android.AndroidPage;
import com.qeweb.framework.pl.android.coarsegrained.AndroidForm;
import com.qeweb.framework.pl.android.coarsegrained.AndroidMenu;
import com.qeweb.framework.pl.android.coarsegrained.AndroidTree;

/**
 * MobileFactory
 *
 */
public class AndroidClassFactory extends HtmlClassFactory {

	@Override
	protected Class<? extends Page> getPageClass() {
		return AndroidPage.class;
	}

	@Override
	protected Class<? extends Tree> getTreeClass() {
		return AndroidTree.class;
	}

	@Override
	protected Class<? extends Menu> getMenuClass() {
		return AndroidMenu.class;
	}

	@Override
	protected Class<? extends CheckTree> getCheckTreeClass() {
		return null;
	}

	@Override
	protected Class<? extends Form> getFormClass() {
		return AndroidForm.class;
	}

}
