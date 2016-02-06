package com.qeweb.framework.impconfig.promodule.bo.handle;

import java.io.File;

import com.qeweb.framework.common.utils.file.FileUtil;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.impconfig.promodule.bo.ProjectModuleBO;

/**
 * 实施配置工具的文件夹处理类
 */
public abstract class PackageHandle {
	
	/**
	 * 创建相应文件夹
	 * @param bo
	 * @throws BOException
	 */
	public void mkPackage(ProjectModuleBO bo) throws BOException {
		String path = getPath(bo);
		File file = new File(path);
		if(file.exists())
			throw new BOException("文件包已存在！");
		else
			FileUtil.mkdirs(path);
	}
	
	/**
	 * 修改文件夹：先判断旧文件下有没有文件，如果没有，删除旧的路径，创建新的路径，如果有，则提示错误
	 * @param bo
	 * @param oldPath
	 * @throws BOException
	 */
	public void modifyPackage(ProjectModuleBO bo, String oldPath) throws BOException {
//		String rootPath = ProjectPathUtil.getProjectPath()+ ImpConfigConstant.DEFAULT_HBMPATH;
//		String path = rootPath + bo.getHbmPackage();
//		oldPath = rootPath + oldPath;
//		File file = new File(oldPath);
//		if(StringUtils.isEmpty(file.list())){
//			try {
//				FileUtil.del(oldPath);
//			} catch (IOException e) {
//				throw new BOException(e.getMessage());
//			}
//			FileUtil.mkdirs(path);
//		}else{
//			throw new BOException("原文件夹中还有内容，不能修改");
//		}
	}
	
	abstract protected String getPath(ProjectModuleBO bo);
}
