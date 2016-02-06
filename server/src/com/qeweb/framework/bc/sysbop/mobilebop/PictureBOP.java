package com.qeweb.framework.bc.sysbop.mobilebop;

import com.qeweb.framework.bc.BOProperty;

/**
 * PictureBOP是一个平台BO,用于存储终端拍照后的照片名称
 */
public class PictureBOP extends BOProperty {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1802802304119871159L;
	
	private String fileName;		//照片文件名称

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
