package com.qeweb.busplatform.businessseting.console.dao.impl;

import java.io.File;
import java.util.List;
import java.util.Properties;
import java.util.Map.Entry;

import org.jdom.Document;
import org.jdom.Element;

import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.busplatform.businessseting.BusSettingPoolHelper;
import com.qeweb.busplatform.businessseting.console.BusConsoleBO;
import com.qeweb.busplatform.businessseting.console.FTPConfBO;
import com.qeweb.busplatform.businessseting.console.consoleitem.ConsoleItemBOP;
import com.qeweb.busplatform.businessseting.console.dao.ia.IBusConsoleDao;
import com.qeweb.framework.base.impl.XmlDaoImpl;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.utils.PropertiesUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.XMLUtil;
import com.qeweb.framework.common.utils.ftp.FTPConfig;

public class BusConsoleDaoImpl extends XmlDaoImpl implements IBusConsoleDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -813049620846586204L;

	@SuppressWarnings("unchecked")
	@Override
	public void saveBussConf(BusConsoleBO bo) {
		File file = BusSettingPoolHelper.getXMLFile();
		Document doc = XMLUtil.getXmlFile(file);
		Element root = doc.getRootElement();
		List<Element> elList = root.getChildren();
		for (Element el : elList) {
			for (Entry<String, BusinessComponent> entry : bo.getBcList().entrySet()) {
				if (!(entry.getValue() instanceof ConsoleItemBOP))
					continue;

				ConsoleItemBOP bop = (ConsoleItemBOP) entry.getValue();
				if (isEqual(el, bop)) {
					el.setAttribute("paramValue", bop.getValue().getValue());
					break;
				}
			}
		}

		outPutDocToFile(doc, BusSettingConstants.getServerPath());
		outPutDocToFile(doc, BusSettingConstants.getClientPath());
	}
	
	@Override
	public void saveFtpConf(FTPConfBO bo) {
		Properties serverProp = PropertiesUtil.getPropertiesFile(FTPConfig.getServerPath());
		serverProp.setProperty(FTPConfig.HOSTNAME, bo.getHostName());
		serverProp.setProperty(FTPConfig.PORT, bo.getPort() + "");
		serverProp.setProperty(FTPConfig.USERNAME, bo.getUserName());
		serverProp.setProperty(FTPConfig.PASSWORD, bo.getPassword());
		serverProp.setProperty(FTPConfig.UPLOADPATH, bo.getUploadPath());
		serverProp.setProperty(FTPConfig.DOWNLOADPATH, bo.getDownloadPath());
		
		PropertiesUtil.save(FTPConfig.getServerPath(), serverProp);
		PropertiesUtil.save(FTPConfig.getClientPath(), serverProp);
		FTPConfig.reload();
	}
	
	private boolean isEqual(Element el, ConsoleItemBOP bop) {
		String boId = el.getAttributeValue("boId");
		String moduleName = el.getAttributeValue("moduleName");
		String paramName = el.getAttributeValue("paramName");

		return StringUtils.isEqual(boId, bop.getBoId()) && StringUtils.isEqual(moduleName, bop.getModuleName())
			&& StringUtils.isEqual(paramName, bop.getParamName());
	}
}
