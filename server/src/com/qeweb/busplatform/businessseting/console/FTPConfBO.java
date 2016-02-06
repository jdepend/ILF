package com.qeweb.busplatform.businessseting.console;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.utils.ftp.FTPConfig;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.frameworkbop.SequenceBop;

/**
 * FTP配置BO
 */
public class FTPConfBO extends BusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6823721364035818116L;

	private String userName;
	private String password;
	private String hostName;
	private int port;
	private String uploadPath;
	private String downloadPath;
	
	public FTPConfBO() {
		addBOP("userName", new NotEmptyBop(1, 50));
		addBOP("password", new NotEmptyBop(1, 100));
		addBOP("hostName", new NotEmptyBop(1, 50));
		addBOP("port", new SequenceBop(new NotEmptyBop(), 1, 99999, 1));
		addBOP("uploadPath", new NotEmptyBop());
		addBOP("downloadPath", new NotEmptyBop());
		setUserName(FTPConfig.getUserName());
		setPassword(FTPConfig.getPassword());
		setHostName(FTPConfig.getHostName());
		setPort(FTPConfig.getPort());
		setUploadPath(FTPConfig.getUploadPath());
		setDownloadPath(FTPConfig.getDownloadPath());
		BOHelper.initPreferencePage(this);
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	public String getDownloadPath() {
		return downloadPath;
	}
	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}
}
