package com.qeweb.sysmanage.preference.bop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.sysmanage.preference.bop.range.FileTypeRange;

public class FileTypeBop extends BOProperty {
	private static final long serialVersionUID = -4554852595346992675L;

	@Override
	public void init() {
		FileTypeRange range = new FileTypeRange();
		getRange().addRange(range);
	}
}
