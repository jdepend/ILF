package com.qeweb.framework.impconfig.proresource.bo;

import java.util.Properties;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.utils.PropertiesUtil;
import com.qeweb.framework.common.utils.file.FileUtil;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.impconfig.ImpConfigConstant;
import com.qeweb.framework.impconfig.common.util.pathutil.ProjectPathUtil;

/**
 * 项目路径配置.
 * ProjectPathBO 将修改 ctPath.properties 中 projectPath的值.
 * 每个开发人员在开发阶段单独配置 projectPath, 借由该路径可遍历eclipse项目中的所有代码.
 */
public class ProjectPathBO extends BusinessObject {
	
	private static final long serialVersionUID = -1877641278799520982L;
	
	//项目路径
	private String projectPath;

	public ProjectPathBO() {
		addBOP("projectPath", new NotEmptyBop());
	}
	
	@Override
	public ProjectPathBO query(BOTemplate bot, int start) {
		setProjectPath(ProjectPathUtil.getProjectPath());
		BOHelper.initPreferencePage(this);
		
		return this;
	}
	
	/**
	 * 保存操作.
	 * <p>该操作将修改开发人员工程中和服务器中的projectPath.properties文件.
	 * 	由于该动作是在开发阶段执行, 故此处省略了包括文件权限在内的一些校验.
	 * @param bo
	 * @throws BOException
	 */
	public void save(ProjectPathBO bo) throws BOException {
		//验证项目路径是否存在
		if(!FileUtil.isDir(bo.getProjectPath()))
			throw new BOException("项目路径错误！");
		
		saveInClient(bo);
		saveInServer(bo);
	}

	/**
	 * 修改工程中的properties文件
	 * @param bo
	 * @throws BOException 
	 */
	private void saveInClient(ProjectPathBO bo) throws BOException {
		//验证开发人员工程中的projectPath.properties文件是否存在
		String clientPath = bo.getProjectPath() + ImpConfigConstant.CONFIG_CLIENT_PROJECTPATH;
		if(!FileUtil.isFile(clientPath))
			throw new BOException("工程中的" + clientPath + "文件不存在！");
		
		clientPath = clientPath.replaceAll("\\\\", "/");
		modifyFile(bo, clientPath);
	}
	
	/**
	 * 修改工程中的properties文件
	 * @param bo
	 * @throws BOException 
	 */
	private void saveInServer(ProjectPathBO bo) throws BOException {
		//验证开发人员服务器中的projectPath.properties文件是否存在
		String serverPath = getServerPath();
		if(!FileUtil.isFile(serverPath))
			throw new BOException("服务器中的" + serverPath + "文件不存在！");
		
		modifyFile(bo, serverPath);
	}

	/**
	 * 修改projectPath.properties文件
	 * @param bo
	 * @param path
	 */
	private void modifyFile(ProjectPathBO bo, String path) {
		Properties clientProp = PropertiesUtil.getPropertiesFile(path);
		clientProp.setProperty(ImpConfigConstant.PROP_KEY_PROJECTPATH, bo.getProjectPath());
		PropertiesUtil.save(path, clientProp);
	}
	
	private String getServerPath() {
		return Envir.getContext().getRealPath("/") + ImpConfigConstant.CONFIG_SERVER_PROJECTPATH;
	}
	
	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}
}
