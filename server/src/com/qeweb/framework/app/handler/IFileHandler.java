package com.qeweb.framework.app.handler;

import java.io.IOException;

public interface IFileHandler {

	/**
	 * 上传文件处理方法
	 * 用户文件上传路径：系统根目录/UploadFiles/用户id/上传日期:yyyyMMdd/文件全称
	 */
	public abstract String upload() throws IOException;

}