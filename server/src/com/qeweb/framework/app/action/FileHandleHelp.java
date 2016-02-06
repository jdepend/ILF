package com.qeweb.framework.app.action;

import java.io.File;
import java.util.Date;

import com.qeweb.framework.base.ac.BaseAction;
import com.qeweb.framework.bc.sysbop.FileBopUtil;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.constant.ConstantSplit;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.common.utils.StringUtils;

public class FileHandleHelp extends BaseAction {

	private static final long serialVersionUID = 123532324321L;

	/**
	 * 获取上传文件相当路径
	 * 
	 * @return
	 */
	public static String getFilePath(String dirName) {
		return getFilePath(dirName, true);
	}

	/**
	 * 获取上传文件相对路径
	 * 
	 * @return
	 */
	public static String getFilePath(String dirName, boolean hasDateDir) {
		// 相对应用根目录路径
		String filepath = AppConfig.getFileUploadPath()
				+ (StringUtils.isEmpty(dirName) ? "" : dirName + "/")				
				+ (hasDateDir ? DateUtils.dateToString(new Date(), "yyyyMMdd") : "");
		return filepath;
	}

	/**
	 * 获取上传文件相对路径
	 * 
	 * @return
	 */
	public static String getFilePath() {		
		return getFilePath(null);
	}

	/**
	 * 在服务器创建目录
	 * 
	 * @return
	 */
	public static File makdSaveDir(String filePath) {
		String realpath = getContext().getRealPath("/" + filePath);
		File savedir = new File(realpath);
		if (!savedir.exists())
			savedir.mkdirs();
		return savedir;
	}
	
	public static File makdSaveDir() {		
		return makdSaveDir(getFilePath());
	}

	/**
	 * 获取上传服务器文件的url路径
	 */
	public static String getFileURLPath(String savefileName, String filePath) {
		return "/" + filePath + "/" + savefileName;
	}
	
	public static String getFileURLPath(String savefileName) {
		return getFileURLPath(savefileName, getFilePath());
	}
	
	/**
	 * @param fileName
	 *            包含后缀的原始文件名
	 * @return 保存在服务器上的文件名称
	 */
	public static String getFileSaveName(String fileName) {
		if (StringUtils.isNotEmpty(fileName))
			return new Date().getTime() + FileBopUtil.getExtention(fileName);
		return null;
	}

	/**
	 * 已上传文件所占存储空间大小，单位：字节
	 * @param uploadSize
	 * @param fileSize
	 * @return
	 */
	public static Long getUploadSize(String uploadSize, Long fileSize) {
		Long size = 0L;
		String[] array = uploadSize.split(ConstantSplit.COMMA_SPLIT);
		for(String sizeStr : array){
			if(StringUtils.isEmpty(sizeStr))
				continue;
			size += Long.valueOf(sizeStr);
		}
		return size + fileSize;
	}
	
}
