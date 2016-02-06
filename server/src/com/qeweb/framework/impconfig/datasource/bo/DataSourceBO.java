package com.qeweb.framework.impconfig.datasource.bo;

import java.util.List;
import java.util.Properties;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.utils.PropertiesUtil;
import com.qeweb.framework.common.utils.PropertyUtils;
import com.qeweb.framework.common.utils.file.FileUtil;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.frameworkbop.NotEmptyBopDec;
import com.qeweb.framework.frameworkbop.SequenceBop;
import com.qeweb.framework.impconfig.ImpConfigConstant;
import com.qeweb.framework.impconfig.datasource.bo.datasourcehandle.DataSourceHandle;
import com.qeweb.framework.impconfig.datasource.bo.datasourcehandle.DataSourceHandleFC;
import com.qeweb.framework.impconfig.datasource.bop.DBTypeBOP;
import com.qeweb.framework.impconfig.datasource.bop.TranAutoCommitTypeBOP;
import com.qeweb.framework.impconfig.proresource.bo.ProjectPathBO;

/**
 * 数据源管理, 在开发阶段将修改jdbc.properties文件
 *
 */
public class DataSourceBO extends BusinessObject {
	
	private static final long serialVersionUID = -6404625197821223654L;
	
	//数据库基本信息
	private String type;				//数据库类型
	private String hostAddress;			//数据库IP
	private String port;				//数据库端口
	private String name;				//数据库名称
	private String username;			//数据库用户名
	private String password;			//数据库密码
	//数据连接池信息
	private String maxActive; 			//最大连接数
	private String maxIdle;				//最大空闲连接数
	private String maxWait;				//最大等待时长
	private String defaultAutoCommit;	//默认是否自动提交
	
	private DataSourceHandle dataSourceHandle;	//数据源处理器
	
	public DataSourceBO() {
		addBOP("type", new NotEmptyBopDec(new DBTypeBOP()));
		addBOP("defaultAutoCommit", new NotEmptyBopDec(new TranAutoCommitTypeBOP()));
		addBOP("hostAddress", new NotEmptyBop());
		addBOP("port", new NotEmptyBop());
		addBOP("name", new NotEmptyBop());
		addBOP("username", new NotEmptyBop());
		addBOP("password", new NotEmptyBop());
		addBOP("maxActive", new SequenceBop(new NotEmptyBop(), 0d, SequenceBop.MAX_VALUE, 1));
		addBOP("maxIdle", new SequenceBop(new NotEmptyBop(), 0d, SequenceBop.MAX_VALUE, 1));
		addBOP("maxWait", new SequenceBop(new NotEmptyBop(), -1d, SequenceBop.MAX_VALUE, 1));
	}
	
	@Override
	public DataSourceBO query(BOTemplate bot, int start) {
		String jdbcPath = getServerPath();
		if(!FileUtil.isFile(jdbcPath))
			return this;
		
		Properties jdbcProp = PropertiesUtil.getPropertiesFile(jdbcPath);
		setDataSourceHandle(DataSourceHandleFC.getDsHandleInstance(jdbcProp));
		DataSourceBO result = getDataSourceHandle().resolve(jdbcProp);
		BOHelper.initPreferencePage(result);
		
		return result;
	}
	
	/**
	 * 保存数据配置
	 * @param boList
	 * @throws BOException
	 */
	public void saveAll(List<DataSourceBO> boList) throws BOException{
		DataSourceBO dataSourceBO = new DataSourceBO();
		//数据源信息
		PropertyUtils.copyPropertyWithOutNull(dataSourceBO, boList.get(0));
		//连接池信息
		PropertyUtils.copyPropertyWithOutNull(dataSourceBO, boList.get(1));
		
		//修改工程中的jdbc.properties
		modifyFile(dataSourceBO, getClientPath());
		//修改服务器中的jdbc.properties
		modifyFile(dataSourceBO, getServerPath());
	}
	
	/**
	 * 修改jdbc.properties文件
	 * @param bo
	 * @param path
	 */
	private void modifyFile(DataSourceBO bo, String path) {
		setDataSourceHandle(DataSourceHandleFC.getDsHandleInstance(bo.getType()));
		
		Properties clientProp = PropertiesUtil.getPropertiesFile(path);
		clientProp.setProperty(ImpConfigConstant.JDBC_KEY_DRIVERCLASSNAME, getDataSourceHandle().getDriverClassName());
		clientProp.setProperty(ImpConfigConstant.JDBC_KEY_URL, getDataSourceHandle().createDBUrl(bo));
		clientProp.setProperty(ImpConfigConstant.JDBC_KEY_USERNAME, bo.getUsername());
		clientProp.setProperty(ImpConfigConstant.JDBC_KEY_PASSWORD, bo.getPassword());
		clientProp.setProperty(ImpConfigConstant.DBSOURCE_KEY_MAX_ACTIVE,bo.getMaxActive());
		clientProp.setProperty(ImpConfigConstant.DBSOURCE_KEY_MAX_IDLE, bo.getMaxIdle());
		clientProp.setProperty(ImpConfigConstant.DBSOURCE_KEY_MAX_WAIT, bo.getMaxWait());
		clientProp.setProperty(ImpConfigConstant.DBSOURCE_KEY_AUTO_COMMIT, bo.getDefaultAutoCommit());
		PropertiesUtil.save(path, clientProp);
	}
	
	private String getServerPath() {
		return Envir.getContext().getRealPath("/") + ImpConfigConstant.CONFIG_SERVER_JDBCPATH;
	}
	
	private String getClientPath() {
		ProjectPathBO projectPathBO = new ProjectPathBO().query(null, 0);
		
		return projectPathBO.getProjectPath() + ImpConfigConstant.CONFIG_CLIENT_JDBCPATH;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHostAddress() {
		return hostAddress;
	}
	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(String maxActive) {
		this.maxActive = maxActive;
	}

	public String getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(String maxIdle) {
		this.maxIdle = maxIdle;
	}

	public String getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(String maxWait) {
		this.maxWait = maxWait;
	}

	public String getDefaultAutoCommit() {
		return defaultAutoCommit;
	}

	public void setDefaultAutoCommit(String defaultAutoCommit) {
		this.defaultAutoCommit = defaultAutoCommit;
	}
	
	/**
	 * 通过jdbcURL获取数据库主机地址
	 * @param url
	 * @return
	 */
	public  String getDBHostAddress(String url){
		return null;
	};
	
	/**
	 * 通过jdbcURL获取数据库端口号
	 * @param url
	 * @return
	 */	
	public String getDBPort(String url){
		return null;
	};
	
	/**
	 * 通过jdbcURL获取数据库名
	 * @param url
	 * @return
	 */	
	public String getDBName(String url){
		return null;
	};
	
	
	/**
	 * 根据主机地址、端口号、数据库名来拼出链接数据库URL地址
	 * @param hostAddress
	 * @param port
	 * @param name
	 * @return
	 */
	public String getDBUrl(String hostAddress, String port, String name){
		return null;
	}

	public DataSourceHandle getDataSourceHandle() {
		return dataSourceHandle;
	}

	public void setDataSourceHandle(DataSourceHandle dataSourceHandle) {
		this.dataSourceHandle = dataSourceHandle;
	};
	

}
