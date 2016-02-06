package com.qeweb.framework.impconfig.datasource.bo.datasourcehandle;

import com.qeweb.framework.impconfig.ImpConfigConstant;
import com.qeweb.framework.impconfig.datasource.bo.DataSourceBO;
import com.qeweb.framework.impconfig.datasource.bop.DBTypeBOP;

public class MySqlHandle extends DataSourceHandle {

	public String createDBUrl(DataSourceBO dataSourceBO) {
		StringBuffer url = new StringBuffer();
		url.append("jdbc:mysql://");
		url.append(dataSourceBO.getHostAddress() + ":" + dataSourceBO.getPort());
		url.append("/" + dataSourceBO.getName());
		url.append("?useUnicode=true&characterEncoding=UTF-8");
		
		return url.toString();
	}
	
	public String getDriverClassName() {
		return "com.mysql.jdbc.Driver";
	}
	
	@Override
	protected String getDBType() {
		return DBTypeBOP.DB_TYPE_MYSQL;
	}
	
	@Override
	protected String getDBHostAddress(String url){
		String[] urlArray = getURLArray(url);
		return urlArray[2];
	}

	@Override
	protected String getDBPort(String url){
		String[] urlArray = getURLArray(url);
		return urlArray.length == 4 ? ImpConfigConstant.DEFAULT_PORT_MYSQL : urlArray[3];
	}
	
	@Override
	protected String getDBName(String url){
		String[] urlArray = getURLArray(url);
		return urlArray.length == 4 ? urlArray[3] : urlArray[4];
	};
	
	/**
	 * mysql url格式为： jdbc:mysql://localhost/qeweb?useUnicode=true&characterEncoding=UTF-8
	 * @param url
	 * @return
	 */
	private String[] getURLArray(String url) {
		String urlStr = (url.split("\\?"))[0];
		urlStr = urlStr.replace("//", "");
		urlStr = urlStr.replace("/", ":");
		String[] urlArray = urlStr.split("\\:");
		
		return urlArray;
	};
}
