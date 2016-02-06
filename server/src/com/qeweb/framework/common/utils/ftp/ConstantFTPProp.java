package com.qeweb.framework.common.utils.ftp;

/**
 * ftp.properties 相关信息
 */
public interface ConstantFTPProp {
	final public static String PROPFILE_FTP = "/prop/ftp.properties";
	
	final public static String HOSTNAME = "hostname";  			//FTP服务器hostname
	final public static String PORT = "port";					//FTP服务器端口
	final public static String USERNAME = "username";			//FTP登录账号
	final public static String PASSWORD = "password";			//FTP登录密码
	final public static String UPLOADPATH = "uploadPath"; 		//FTP服务器保存目录
	final public static String DOWNLOADPATH = "downloadPath"; 	//下载后保存到本地的路径   
}
