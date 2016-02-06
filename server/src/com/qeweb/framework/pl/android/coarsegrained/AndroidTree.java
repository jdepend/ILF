package com.qeweb.framework.pl.android.coarsegrained;

import com.qeweb.framework.pal.coarsegrained.Tree;
import com.qeweb.framework.pl.android.coarsegrained.treepaint.AndroidTreePaint;

public class AndroidTree extends Tree {

	@Override
	public void paint() {
		new AndroidTreePaint(this).paint();
	}
}
