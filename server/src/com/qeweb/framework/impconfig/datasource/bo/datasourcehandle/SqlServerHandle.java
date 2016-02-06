package com.qeweb.framework.impconfig.datasource.bo.datasourcehandle;

import com.qeweb.framework.impconfig.datasource.bo.DataSourceBO;
import com.qeweb.framework.impconfig.datasource.bop.DBTypeBOP;

public class SqlServerHandle extends DataSourceHandle {

	@Override
	protected String getDBType() {
		return DBTypeBOP.DB_TYPE_SQLSERVER;
	}

	@Override
	protected String getDBHostAddress(String url) {
		return getURLArray(url)[3];
	}

	@Override
	protected String getDBPort(String url) {
		return getURLArray(url)[4];
	}

	@Override
	protected String getDBName(String url) {
		return getURLArray(url)[5];
	}
	
	public String getDriverClassName() {
		return "om.microsoft.sqlserver.jdbc.SQLServerDriver";
	}

	@Override
	public String createDBUrl(DataSourceBO dataSourceBO) {
		StringBuffer url = new StringBuffer();
		url.append("jdbc:mysql://");
		url.append(dataSourceBO.getHostAddress() + ":" + dataSourceBO.getPort());
		url.append("/" + dataSourceBO.getName());
		url.append("?useUnicode=true&characterEncoding=UTF-8");
		return url.toString();
	}
	
	/**
	 * sqlserver url格式为：jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=ssh
	 * @param url
	 * @return
	 */
	private String[] getURLArray(String url) {
		String urlStr = url.replace("DatabaseName=", "");
		urlStr = urlStr.replace("//", "");
		urlStr = urlStr.replace(";", "");
		String[] urlArray = urlStr.split(":");
		
		return urlArray;
	};
}
