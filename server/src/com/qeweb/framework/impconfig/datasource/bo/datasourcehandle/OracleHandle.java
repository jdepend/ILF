package com.qeweb.framework.impconfig.datasource.bo.datasourcehandle;

import com.qeweb.framework.impconfig.datasource.bo.DataSourceBO;
import com.qeweb.framework.impconfig.datasource.bop.DBTypeBOP;

public class OracleHandle extends DataSourceHandle {

	@Override
	public String createDBUrl(DataSourceBO dataSourceBO) {
		StringBuffer url = new StringBuffer();
		url.append("jdbc:oracle:thin:@");
		url.append(dataSourceBO.getHostAddress() + ":" + dataSourceBO.getPort());
		url.append(":" + dataSourceBO.getName());
		return url.toString();
	}
	
	public String getDriverClassName() {
		return "oracle.jdbc.driver.OracleDriver";
	}
	
	@Override
	protected String getDBType() {
		return DBTypeBOP.DB_TYPE_ORACLE;
	}
	
	@Override
	protected String getDBHostAddress(String url){
		return getURLArray(url)[3];
	}

	@Override
	protected String getDBPort(String url){
		return getURLArray(url)[4];
	}
	
	@Override
	protected String getDBName(String url){
		return getURLArray(url)[5];
	};
	
	/**
	 * oracle url格式为： jdbc:oracle:thin:@192.168.0.239:1521:qeway
	 * @param url
	 * @return
	 */
	private String[] getURLArray(String url) {
		return  url.replace("@", "").split("\\:");
	};
}
