package com.qeweb.framework.common.utils.file;

import java.io.File;
import java.io.FilenameFilter;


/**
 * 文件名称过滤器
 */
public class FileNameFilterImpl implements FilenameFilter {
	
	String extension;
	public FileNameFilterImpl(String extension){
		this.extension = "."+extension;
	}
	
	/* (non-Javadoc)
	 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
	 */
	public boolean accept(File dir, String name) {
		return name.endsWith(extension);
	}

}
