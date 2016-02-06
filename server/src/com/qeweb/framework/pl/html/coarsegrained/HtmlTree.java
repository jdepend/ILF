package com.qeweb.framework.pl.html.coarsegrained;

import com.qeweb.framework.pal.coarsegrained.Tree;
import com.qeweb.framework.pl.html.coarsegrained.treepaint.TreePaint;

/**
 * html树
 */
public class HtmlTree extends Tree {
	@Override
	public void paint() {
		new TreePaint(this).paint();
	}
}
