package com.qeweb.framework.frameworkbo;

import com.qeweb.framework.bc.BusinessObject;

/**
 * 断点续传bo
 * 
 */
public class RetryBrokenUploadBO extends BusinessObject {

	private static final long serialVersionUID = 1L;
	private String fileId;		//文件ID,做为文件的唯一标识,记录了文件名信息,文件以时间戳命名
	private String fileUrl;		//文件URL
	private long filePosition;	//文件bytes流的传输位置, 如:文件共1000bytes, 第一次传输到400bytes处
	private long fileSize;		//文件大小,单位bytes

	protected long getLoginUserId() {
		return 0L;
	}
	
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public long getFilePosition() {
		return filePosition;
	}

	public void setFilePosition(long filePosition) {
		this.filePosition = filePosition;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
}
