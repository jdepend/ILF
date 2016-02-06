package com.qeweb.framework.pl.android.coarsegrained;

import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.pl.html.coarsegrained.HTMLForm;

public class AndroidForm extends HTMLForm {

	@Override
	public String getLayoutStr() {
		return AppConfig.getPropValue(AppConfig.LAYOUT_COLUMN_NUMBER_MOBILE);
	}
}
