package com.qeweb.framework.impconfig.datasource.bo.datasourcehandle;

import java.util.Properties;

import com.qeweb.framework.impconfig.ImpConfigConstant;
import com.qeweb.framework.impconfig.datasource.bo.DataSourceBO;

/**
 * 数据源处理器，负责为jdbc.properties解析及生成正确的数据源
 */
public abstract class DataSourceHandle {

	/**
	 * 解析数据源，将jdbc.properties中的内容转换为DataSourceBO
	 * @param jdbcProp
	 * @return
	 */
	public DataSourceBO resolve(Properties jdbcProp) {
		DataSourceBO dsBO = new DataSourceBO();
		String url = jdbcProp.getProperty(ImpConfigConstant.JDBC_KEY_URL);
		dsBO.setType(getDBType());
		dsBO.setHostAddress(getDBHostAddress(url));
		dsBO.setPort(getDBPort(url));
		dsBO.setName(getDBName(url));
		dsBO.setUsername(jdbcProp.getProperty(ImpConfigConstant.JDBC_KEY_USERNAME));
		dsBO.setPassword(jdbcProp.getProperty(ImpConfigConstant.JDBC_KEY_PASSWORD));
		dsBO.setMaxActive(jdbcProp.getProperty(ImpConfigConstant.DBSOURCE_KEY_MAX_ACTIVE));
		dsBO.setMaxIdle(jdbcProp.getProperty(ImpConfigConstant.DBSOURCE_KEY_MAX_IDLE));
		dsBO.setMaxWait(jdbcProp.getProperty(ImpConfigConstant.DBSOURCE_KEY_MAX_WAIT));
		dsBO.setDefaultAutoCommit(jdbcProp.getProperty(ImpConfigConstant.DBSOURCE_KEY_AUTO_COMMIT));
		
		return dsBO;
	}
	
	/**
	 * 生成DBUrl
	 * @param dataSourceBO
	 * @return
	 */
	abstract public String createDBUrl(DataSourceBO dataSourceBO);
	
	/**
	 * 获取驱动类名称
	 * @return
	 */
	abstract public String getDriverClassName();
	
	/**
	 * 获取数据库类型
	 * @return
	 */
	abstract protected String getDBType();
	
	/**
	 * 根据jdbcURL 解析DBHostAddress
	 * @param url
	 * @return
	 */
	abstract protected String getDBHostAddress(String url);

	/**
	 * 根据jdbcURL 解析DBPort
	 * @param url
	 * @return
	 */
	abstract protected String getDBPort(String url);
	
	/**
	 * 根据jdbcURL 解析DBName
	 * @param url
	 * @return
	 */
	abstract protected String getDBName(String url);
}
