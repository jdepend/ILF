package com.qeweb.sysmanage.login.licence;

import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.FileBOP;
import com.qeweb.framework.bc.sysbop.NOSubmitBOP;
import com.qeweb.framework.frameworkbop.NotEmptyBopDec;

/**
 * licence处理BO
 */
public class LicenceBO extends BusinessObject{

	private static final long serialVersionUID = -5263468815320466259L;

	private FileBOP licenceBOP;

	public LicenceBO(){
		addBOP("licenceBOP", new NotEmptyBopDec(new LicenceBOP()));
		addOperateBOP("goback", new NOSubmitBOP());
	}

	public FileBOP getLicenceBOP() {
		return licenceBOP;
	}

	public void setLicenceBOP(FileBOP licenceBOP) {
		this.licenceBOP = licenceBOP;
	}
}
