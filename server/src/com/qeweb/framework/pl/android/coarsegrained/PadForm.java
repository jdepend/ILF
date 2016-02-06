package com.qeweb.framework.pl.android.coarsegrained;

import com.qeweb.framework.common.appconfig.AppConfig;

public class PadForm extends AndroidForm {

	@Override
	public String getLayoutStr() {
		return AppConfig.getPropValue(AppConfig.LAYOUT_COLUMN_NUMBER_PAD);
	}
}
