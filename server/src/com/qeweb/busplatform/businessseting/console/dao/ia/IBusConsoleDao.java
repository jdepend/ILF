package com.qeweb.busplatform.businessseting.console.dao.ia;

import com.qeweb.busplatform.businessseting.console.BusConsoleBO;
import com.qeweb.busplatform.businessseting.console.FTPConfBO;
import com.qeweb.framework.base.ia.IXmlDao;

/**
 * 业务配置文件操作dao
 */
public interface IBusConsoleDao extends IXmlDao {

	/**
	 * 保存业务配置文件
	 * @param bo
	 */
	public void saveBussConf(BusConsoleBO bo);
	
	/**
	 * 保存FTP配置
	 * @param bo
	 */
	public void saveFtpConf(FTPConfBO bo);
}
