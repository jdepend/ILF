package com.qeweb.framework.pl.html.coarsegrained;

import com.qeweb.framework.pal.coarsegrained.CheckTree;
import com.qeweb.framework.pl.html.coarsegrained.treepaint.CheckTreePaint;

public class HTMLCheckTree extends CheckTree {

	@Override
	public void paint() {
		new CheckTreePaint(this).paint();
	}

}
