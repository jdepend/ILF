package com.qeweb.framework.impconfig.promodule.bo.handle;

import java.io.File;
import java.io.IOException;

import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.file.FileUtil;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.impconfig.ImpConfigConstant;
import com.qeweb.framework.impconfig.common.util.pathutil.ProjectPathUtil;
import com.qeweb.framework.impconfig.promodule.bo.ProjectModuleBO;

public class PageFlowHandle extends PackageHandle{

	@Override
	public void modifyPackage(ProjectModuleBO bo, String oldPath)
			throws BOException {
		String rootPath = ProjectPathUtil.getProjectPath()+ ImpConfigConstant.DEFAULT_PAGEFLOWPATH;
		String path = rootPath + bo.getPageflowPackage();
		oldPath = rootPath + oldPath;
		File file = new File(oldPath);
		if(StringUtils.isEmpty(file.list())){
			try {
				FileUtil.del(oldPath);
			} catch (IOException e) {
				throw new BOException(e.getMessage());
			}
			FileUtil.mkdirs(path);
		}else{
			throw new BOException("原文件夹中还有内容，不能修改");
		}
	}

	@Override
	protected String getPath(ProjectModuleBO bo) {
		return ProjectPathUtil.getProjectPath()+ ImpConfigConstant.DEFAULT_PAGEFLOWPATH + bo.getPageflowPackage();
	}

	
}
