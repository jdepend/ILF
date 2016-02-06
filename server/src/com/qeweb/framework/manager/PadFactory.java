package com.qeweb.framework.manager;

import com.qeweb.framework.pal.coarsegrained.Form;
import com.qeweb.framework.pl.android.coarsegrained.PadForm;

/**
 * PadFactory
 *
 */
public class PadFactory extends AndroidFactory {

	@Override
	protected Form createForm() {
		return new PadForm();
	}

}
