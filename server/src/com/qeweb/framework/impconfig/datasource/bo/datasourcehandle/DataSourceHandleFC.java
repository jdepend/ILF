package com.qeweb.framework.impconfig.datasource.bo.datasourcehandle;

import java.util.Properties;

import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.ImpConfigConstant;
import com.qeweb.framework.impconfig.datasource.bop.DBTypeBOP;

/**
 * 数据源处理器工程，负责生成正确的处理器
 */
public class DataSourceHandleFC {

	final static public DataSourceHandle getDsHandleInstance(Properties jdbcProp) {
		String url = jdbcProp.getProperty(ImpConfigConstant.JDBC_KEY_URL);		
		String[] urlArray = StringUtils.split(url, ":");
		String dbType = urlArray[1];
		
		if(StringUtils.isEqual("mysql", dbType))
			return new MySqlHandle();
		else if(StringUtils.isEqual("microsoft", dbType))
			return new SqlServerHandle();
		else
			return new OracleHandle();
	}
	
	final static public DataSourceHandle getDsHandleInstance(String dbType) {
		if(StringUtils.isEqual(DBTypeBOP.DB_TYPE_MYSQL, dbType))
			return new MySqlHandle();
		else if(StringUtils.isEqual(DBTypeBOP.DB_TYPE_SQLSERVER, dbType))
			return new SqlServerHandle();
		else
			return new OracleHandle();
	}
}
