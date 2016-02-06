package com.qeweb.framework.app.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.opensymphony.xwork2.ActionSupport;
import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 
 * 附件下载
 */
public class DownloadAC extends ActionSupport {
	private static final long serialVersionUID = -186222091001940314L;
	private String fileName;				//保存到本地的默认文件名
	private String path;					//文件路径
	private InputStream inputStream;
	private String errorMessage;

	public String execute() {
		//下载文件
		try {
			path = URLDecoder.decode(path, Constant.ENCODING_UTF8);
			String realpath = Envir.getContext().getRealPath("/" + path);
			File file = new File(realpath);
			inputStream = new FileInputStream(file);
			if(StringUtils.isEmpty(fileName)){
				int index = path.lastIndexOf("/");
				fileName = path.substring(index + 1, path.length());
			}
			fileName = URLEncoder.encode(fileName, Constant.ENCODING_UTF8);
		} catch (FileNotFoundException e) {
			errorMessage = AppLocalization.getLocalization("file.download.error.filenotfound");
			return INPUT;
		} catch (UnsupportedEncodingException e) {
			errorMessage = AppLocalization.getLocalization("file.download.error.other");
			return INPUT;
		}
		return SUCCESS;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
