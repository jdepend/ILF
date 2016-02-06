package com.qeweb.framework.impconfig.promodule.bo;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.impconfig.promodule.bop.HbmPackageOpBOP;
import com.qeweb.framework.impconfig.promodule.bop.I18nPackageOpBOP;
import com.qeweb.framework.impconfig.promodule.bop.JspPackageOpBOP;
import com.qeweb.framework.impconfig.promodule.bop.PageFlowPackageOpBOP;
import com.qeweb.framework.impconfig.promodule.bop.SpringPackageOpBOP;
import com.qeweb.framework.impconfig.promodule.bop.SrcPackageOpBOP;
import com.qeweb.framework.impconfig.promodule.bop.VarPackageOpBOP;

/**
 * 项目代码信息
 */
public class ProjectCodeBO extends ProjectModuleBO {

	private static final long serialVersionUID = 8141435253679308950L;

	@SuppressWarnings("unchecked")
	@Override
	protected void initPreferencePage(Page page){
		List<BusinessObject> boList = new LinkedList<BusinessObject>();
		for (ProjectModuleBO module : (List<ProjectModuleBO>)page.getItems()){
			module.addBOP("srcPackage", new SrcPackageOpBOP());
			module.addBOP("jspPackage", new JspPackageOpBOP());
			module.addBOP("springPackage", new SpringPackageOpBOP());
			module.addBOP("hbmPackage", new HbmPackageOpBOP());
			module.addBOP("pageflowPackage", new PageFlowPackageOpBOP());
			module.addBOP("varPackage", new VarPackageOpBOP());
			module.addBOP("i18nPackage", new I18nPackageOpBOP());
			BOHelper.initPreferencePage(module);
			
			boList.add(module);
		}
		page.setBOList(boList);
	}
}
