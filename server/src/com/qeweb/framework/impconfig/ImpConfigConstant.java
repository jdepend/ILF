package com.qeweb.framework.impconfig;

/**
 * impconfig包下所有模块的静态变量
 */
public class ImpConfigConstant {
	final public static String SERVER_ROOT = "/WEB-INF/classes";
	
	//projectPath.properties 文件的路径
	final public static String CONFIG_CLIENT_PROJECTPATH = "/conf/impconfig/proresource/projectPath.properties";
	final public static String CONFIG_SERVER_PROJECTPATH = SERVER_ROOT + "/impconfig/proresource/projectPath.properties";
	

	//projectPath.properties文件中的工程路径key
	final public static String PROP_KEY_PROJECTPATH = "projectPath";
	
	//jdbc.properties 文件的路径
	final public static String CONFIG_CLIENT_JDBCPATH = "/conf/prop/jdbc.properties";
	final public static String CONFIG_SERVER_JDBCPATH = SERVER_ROOT + "/prop/jdbc.properties";
	
	//jdbc.properties 文件中的数据源配置key
	final public static String JDBC_KEY_DRIVERCLASSNAME = "jdbc.driverClassName";
	final public static String JDBC_KEY_URL = "jdbc.url";
	final public static String JDBC_KEY_USERNAME = "jdbc.username";
	final public static String JDBC_KEY_PASSWORD = "jdbc.password";
	final public static String DBSOURCE_KEY_MAX_ACTIVE = "datasource.maxActive";
	final public static String DBSOURCE_KEY_MAX_IDLE = "datasource.maxIdle";
	final public static String DBSOURCE_KEY_MAX_WAIT = "datasource.maxWait";
	final public static String DBSOURCE_KEY_AUTO_COMMIT = "datasource.defaultAutoCommit";

	//driverClassName设定
	final public static String DRIVER_CLASS_NAME_MYSQL= "com.mysql.jdbc.Driver";
	final public static String DRIVER_CLASS_NAME_ORACLE= "oracle.jdbc.driver.OracleDriver";
	final public static String DRIVER_CLASS_NAME_SQLSERVER= "com.microsoft.jdbc.sqlserver.SQLServerDriver";
	
	//数据库默认端口号
	final public static String DEFAULT_PORT_MYSQL = "3306";
	
	//qewebProject.properties 文件的路径
	final public static String CONFIG_CLIENT_PROJECTINFO_PATH = "/conf/impconfig/proresource/qewebProject.properties";
	final public static String CONFIG_SERVER_PROJECTINFO_PATH = SERVER_ROOT + "/impconfig/proresource/qewebProject.properties";
	
	//qewebProject.properties 文件中的key
	final public static String PROJECT_INFO_PROJECTNAME = "projectName"; 
	final public static String PROJECT_INFO_SRCPATH = "srcPath";
	final public static String PROJECT_INFO_JSPPATH = "jspPath"; 		
	final public static String PROJECT_INFO_HBMPATH = "hbmPath"; 	
	final public static String PROJECT_INFO_SPRINGPATH = "springPath"; 	
	final public static String PROJECT_INFO_PAGEFLOWPATH = "pageFlowPath"; 	
	final public static String PROJECT_INFO_I18NPATH = "i18nPath";
	final public static String PROJECT_INFO_VARPATH = "varPath"; 
	
	final public static String PROJECT_INFO_SVNURL = "svnUrl";
	final public static String PROJECT_INFO_SVNUSERNAME = "svnUsername";
	final public static String PROJECT_INFO_SVNPASSWORD = "svnPassword";	
	
	//qeweb-impuser.xml 文件路径
	final public static String CONFIG_CLIENT_IMPUSERPATH = "/conf/impconfig/proresource/qeweb-impuser.xml";
	final public static String CONFIG_SERVER_IMPUSERPATH = SERVER_ROOT + "/impconfig/proresource/qeweb-impuser.xml";
	
	//qeweb-pageflow_var.xml 文件路径
	final public static String CONFIG_CLIENT_PFVARPATH = "/conf/impconfig/pfmapping/qeweb-pageflow_var.xml";
	
	final public static String DEFAULT_MEMBER_PASSWORD = "123456";
	
	//qeweb-duty.xml 文件路径
	final public static String CONFIG_CLIENT_DUTYPATH = "/conf/impconfig/proresource/qeweb-duty.xml";
	final public static String CONFIG_SERVER_DUTYPATH = SERVER_ROOT + "/impconfig/proresource/qeweb-duty.xml";
	
	//qeweb-var.xml 文件路径
	final public static String CONFIG_CLIENT_VARPATH = "/conf/impconfig/var/qeweb-var.xml";
	final public static String CONFIG_SERVER_VARPATH = SERVER_ROOT + "/impconfig/var/qeweb-var.xml";
	
	//qeweb-var_pageflow.xml 文件路径
	final public static String CONFIG_CLIENT_VAR_PF_PATH = "/conf/impconfig/var/qeweb-var_pageflow.xml";
	final public static String CONFIG_SERVER_VAR_PF_PATH = SERVER_ROOT + "/impconfig/var/qeweb-var_pageflow.xml";
	
	//qeweb-var_page.xml 文件路径
	final public static String CONFIG_CLIENT_VAR_PAGE_PATH = "/conf/impconfig/var/qeweb-var_page.xml";
	final public static String CONFIG_SERVER_VAR_PAGE_PATH = SERVER_ROOT + "/impconfig/var/qeweb-var_page.xml";
	
	//qeweb-page-entry.xml 文件路径
	final public static String CONFIG_CLIENT_PAGE_ENTRY_PATH = "/conf/impconfig/var/qeweb-page-entry.xml";
	final public static String CONFIG_SERVER_PAGE_ENTRY_PATH = SERVER_ROOT + "/impconfig/var/qeweb-page-entry.xml";
	
	//qeweb-pageflow-entry.xml 文件路径
	final public static String CONFIG_CLIENT_PAGEFLOW_ENTRY_PATH = "/conf/impconfig/var/qeweb-pageflow-entry.xml";
	final public static String CONFIG_SERVER_PAGEFLOW_ENTRY_PATH = SERVER_ROOT + "/impconfig/var/qeweb-pageflow-entry.xml";
	
	//qeweb-user_page.xml 文件路径
	final public static String CONFIG_CLIENT_USER_PAGE_PATH = "/conf/impconfig/user/qeweb-user_page.xml";
	final public static String CONFIG_SERVER_USER_PAGE_PATH = SERVER_ROOT + "/impconfig/user/qeweb-user_page.xml";
	
	//默认各类文件根目录
	final public static String DEFAULT_SRCPATH = "/src/";
	final public static String DEFAULT_JSPPATH = "/WebContent/WEB-INF/";
	final public static String DEFAULT_HBMPATH = "/src/";
	final public static String DEFAULT_SPRINGPATH = "/conf/spring/";
	final public static String DEFAULT_PAGEFLOWPATH ="/conf/pageflow/";
	final public static String DEFAULT_VARPATH = "/conf/impconfig/var/";
	final public static String DEFAULT_I18NPATH = "/conf/i18n/";
	
	final public static String XML_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
}
