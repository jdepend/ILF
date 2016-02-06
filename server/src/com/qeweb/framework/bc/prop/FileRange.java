package com.qeweb.framework.bc.prop;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import com.qeweb.framework.bc.sysbop.FileBopUtil;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.constant.ConstantSplit;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;

public class FileRange extends Range {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5400901902304885514L;
	private Long maxSize;				//文件大小,单位:字节
	private Set<String> allowedType;	//允许上传类型(优先于notAllowedType), set中的每个元素是一个文件扩展名, 型如xls,xlsx,doc,docx,jpg,jpeg,gif,rar,zip,txt
	private Set<String> notAllowedType;	//不允许上传类型, set中的每个元素是一个文件扩展名, 型如xls,xlsx,doc,docx,jpg,jpeg,gif,rar,zip,txt
	private String errorType;			//错误类型
	private File file;					//当前上传文件
	private String displayName;			//当前上传文件展示名称
	
	
	public FileRange(){
		maxSize = AppConfig.getAllowMaxSize();
		allowedType = this.getDefaultTypeSet(AppConfig.getAllowedType());
		notAllowedType = this.getDefaultTypeSet(AppConfig.getNotAllowedType());
	}
	
	/**
	 * @param maxSize				文件大小限制, 文件大小,单位:字节
	 * @param allowedTypeStr		允许上传类型(优先于notAllowedType), 格式型如 xls,xlsx,doc,docx,jpg,jpeg,gif,rar,zip,txt
	 * @param notAllowedTypeStr		不允许上传类型, 格式型如xls,xlsx,doc,docx,jpg,jpeg,gif,rar,zip,txt
	 */
	public FileRange(Long maxSize, String allowedTypeStr, String notAllowedTypeStr){
		maxSize = (maxSize == null ? AppConfig.getAllowMaxSize() : maxSize);
		if(StringUtils.isEmpty(allowedTypeStr)) 
			setAllowedType(getDefaultTypeSet(AppConfig.getAllowedType()));
		else 
			setAllowedType(ContainerUtil.convertToSet(allowedTypeStr, ","));
		
		if(StringUtils.isEmpty(notAllowedTypeStr))
			setNotAllowedType(getDefaultTypeSet(AppConfig.getNotAllowedType()));
		else 
			setNotAllowedType(ContainerUtil.convertToSet(notAllowedTypeStr, ","));
	}
	
	/**
	 * 获取默认(允许/不允许)上传类型集合
	 * @param typeStr
	 * @return
	 */
	private Set<String> getDefaultTypeSet(String typeStr) {
		Set<String> typeSet = ContainerUtil.convertToSet(typeStr, ",");
		return typeSet == null ? new HashSet<String>() : typeSet;
	}
	
	@Override
	public boolean isIN(Value value) {
		StringBuffer errorMsg = new StringBuffer();
		//空文件
		if(getFile()  == null || getFile().length() <= 0){
			errorMsg.append(AppLocalization.getLocalization("file.upload.mulipart.isEmpty"));
			this.setValidateMessage(errorMsg.toString());
			return false;
		}
		//过大
		if(getMaxSize() < getFile().length()){
			errorMsg.append(AppLocalization.getLocalization("file.upload.mulipart.maxSize"));
			errorMsg.append((getMaxSize() / 1024 / 1024) + "M");
			this.setValidateMessage(errorMsg.toString());
			return false;
		}
		String extension = FileBopUtil.getExtention(getDisplayName()).replace(ConstantSplit.BIND_SPLIT, "").toLowerCase();
		//文件扩展名不在允许名单中
		if((!getAllowedType().isEmpty() && !getAllowedType().contains(extension))
				|| (!getNotAllowedType().isEmpty() && getNotAllowedType().contains(extension))){
			String allowedTypeStr = FileBopUtil.getTypeStr(getAllowedType());
			if(StringUtils.isNotEmpty(allowedTypeStr)){
				errorMsg.append("<br>");
				errorMsg.append(AppLocalization.getLocalization("file.upload.allowedType"));
				errorMsg.append(allowedTypeStr);
			}
			//不允许类型
			String notAllowedTypeStr = FileBopUtil.getTypeStr(getNotAllowedType());
			if(StringUtils.isNotEmpty(notAllowedTypeStr)){
				errorMsg.append("<br>");
				errorMsg.append(AppLocalization.getLocalization("file.upload.notAllowedType"));
				errorMsg.append(notAllowedTypeStr);
			}
			this.setValidateMessage(errorMsg.toString());
			return false;
		}
		
		return true;
	}

	public Long getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Long maxSize) {
		this.maxSize = maxSize;
	}

	/**
	 * 获取允许上传文件类型, 
	 * set中的每个元素是一个文件扩展名, 型如xls,xlsx,doc,docx,jpg,jpeg,gif,rar,zip,txt
	 * 允许上传文件类型优选于不允许上传类型.
	 * @return
	 */
	public Set<String> getAllowedType() {
		return allowedType;
	}


	/**
	 *  设置允许上传文件类型
	 *  allowedType中的每个元素是一个文件扩展名, 型如xls,xlsx,doc,docx,jpg,jpeg,gif,rar,zip,txt.
	 *  允许上传文件类型优选于不允许上传类型.
	 * @param allowedType
	 */
	public void setAllowedType(Set<String> allowedType) {
		this.allowedType = allowedType;
	}

	/**
	 * 获取不允许上传文件类型, 
	 * set中的每个元素是一个文件扩展名, 型如xls,xlsx,doc,docx,jpg,jpeg,gif,rar,zip,txt
	 * 允许上传文件类型优选于不允许上传类型.
	 * @return
	 */
	public Set<String> getNotAllowedType() {
		return notAllowedType;
	}

	/**
	 *  设置不允许上传文件类型
	 *  notAllowedType中的每个元素是一个文件扩展名, 型如xls,xlsx,doc,docx,jpg,jpeg,gif,rar,zip,txt.
	 *  允许上传文件类型优选于不允许上传类型.
	 * @param notAllowedType
	 */
	public void setNotAllowedType(Set<String> notAllowedType) {
		this.notAllowedType = notAllowedType;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
