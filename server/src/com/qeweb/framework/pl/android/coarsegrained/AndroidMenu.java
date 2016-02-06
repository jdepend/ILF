package com.qeweb.framework.pl.android.coarsegrained;

import com.qeweb.framework.pal.coarsegrained.Menu;
import com.qeweb.framework.pl.android.coarsegrained.treepaint.AndroidTreePaint;

public class AndroidMenu extends Menu {

	@Override
	public void paint() {
		new AndroidTreePaint(this).paint();
	}
}
