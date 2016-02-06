package com.qeweb.framework.impconfig.common.util.pathutil;

import java.util.Properties;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.utils.PropertiesUtil;
import com.qeweb.framework.common.utils.file.FileUtil;
import com.qeweb.framework.impconfig.ImpConfigConstant;

/**
 * 项目基本信息util, 负责读取projectPath.properties中的内容
 */
public class ProjectPathUtil {
	
	/**
	 * 获得工程地址
	 * @return
	 */
	final public static String getProjectPath(){
		String projectRealPath = Envir.getContext().getRealPath("/") + ImpConfigConstant.CONFIG_SERVER_PROJECTPATH;
		if(FileUtil.isFile(projectRealPath)) {
			Properties serverProp = PropertiesUtil.getPropertiesFile(projectRealPath);
			return serverProp.getProperty(ImpConfigConstant.PROP_KEY_PROJECTPATH);
		}
		return null;
	}
	
}
