package com.qeweb.framework.impconfig.mdt.pageflow.bop;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.file.FileUtil;
import com.qeweb.framework.impconfig.common.util.pathutil.PageFlowFilePathUtil;
import com.qeweb.framework.impconfig.mdt.pageflow.bo.PageflowFileBO;
import com.qeweb.framework.impconfig.mdt.pageflow.dao.ia.IPageflowManageDao;
import com.qeweb.framework.impconfig.promodule.bo.ProjectModuleBO;
import com.qeweb.framework.impconfig.promodule.bop.ProModuleBOP;
import com.qeweb.framework.impconfig.promodule.dao.ia.IProModuleDao;

/**
 * 页面流文件BOP
 * 
 */
public class PageflowFileBOP extends BOProperty {

	private static final long serialVersionUID = -3062086816327760375L;

	@Override
	public void init() {
		EnumRange range = new EnumRange();
		Map<String, String> fileMap = new LinkedHashMap<String, String>();
		EnumRange.addPlease(fileMap);
		getRange().addRange(range);
		getRange().setRequired(true);
	}
	
	/**
	 * 处理模块和页面流文件的关联
	 */
	@Override
	public BusinessComponent query(BOProperty sourceBop) throws Exception {
		if(!(sourceBop instanceof ProModuleBOP))
			return this;
		
		ProModuleBOP proModuleBOP = (ProModuleBOP) sourceBop;
		Long moduleId = StringUtils.convertToLong(proModuleBOP.getValue().getValue());
		EnumRange filesRange = new EnumRange();
		filesRange.setResult(getPFFiles(moduleId));
		getRange().clear();
		addRange(filesRange);
		
		return this;
	}
	
	/**
	 * 获取 moduleId指定模块下的页面流文件， 如果moduleId为空，获取页面流根目录下的页面流文件
	 * @param moduleId
	 * @return  key: 页面流文件路径， value：页面流文件名
	 * @throws Exception 
	 */
	private Map<String, String> getPFFiles(Long moduleId) throws Exception {
		Map<String, String> fileMap = new LinkedHashMap<String, String>();
		EnumRange.addPlease(fileMap);
		//当模块信息为空时，加载所有页面流根目录下的文件
		if(moduleId == null) {
			List<PageflowFileBO> pfFileBoList = getPageflowManageDao().findPageFlowFiles(null, null);
			if(ContainerUtil.isNull(pfFileBoList))
				return fileMap;
			
			for(PageflowFileBO pfFileBO : pfFileBoList) {
				fileMap.put(pfFileBO.getFilePath(), pfFileBO.getFileName());
			}
			
			return fileMap;
		}
		
		ProjectModuleBO module = getProModuleDao().getProjectModule(moduleId);
		String moduleRootPath = PageFlowFilePathUtil.getClientPageflowFilePath() + module.getPageflowPackage();
		List<File> files = FileUtil.getAllFiles(new File(moduleRootPath), FileUtil.nameFileter(".xml"));
		if (ContainerUtil.isNotNull(files)) {
			for (File file : files) {
				fileMap.put(file.getAbsolutePath(), file.getName());
			}
		}
		
		return fileMap;
	}
	
	private IProModuleDao getProModuleDao() {
		return (IProModuleDao)SpringConstant.getCTX().getBean("proModuleDao");
	}
	
	private IPageflowManageDao getPageflowManageDao() {
		return (IPageflowManageDao)SpringConstant.getCTX().getBean("pageflowManageDao");
	}
}
