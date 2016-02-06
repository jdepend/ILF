package com.qeweb.framework.manager;

import com.qeweb.framework.pal.coarsegrained.Form;
import com.qeweb.framework.pl.android.coarsegrained.PadForm;

/**
 * PadFactory
 *
 */
public class PadClassFactory extends AndroidClassFactory {

	@Override
	protected Class<? extends Form> getFormClass() {
		return PadForm.class;
	}

}
