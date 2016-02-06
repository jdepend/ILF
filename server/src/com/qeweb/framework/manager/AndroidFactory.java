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
public class AndroidFactory extends HtmlFactory {

	@Override
	protected Page createPage() {
		return new AndroidPage();
	}

	@Override
	protected Tree createTree() {
		return new AndroidTree();
	}

	@Override
	protected Menu createMenu() {
		return new AndroidMenu();
	}

	@Override
	protected CheckTree createCheckTree() {
		return null;
	}

	@Override
	protected Form createForm() {
		return new AndroidForm();
	}

}
