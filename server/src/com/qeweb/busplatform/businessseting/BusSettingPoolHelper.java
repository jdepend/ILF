package com.qeweb.busplatform.businessseting;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.constant.ConstantSplit;
import com.qeweb.framework.common.utils.XMLUtil;

/**
 *
 * 读取业务配置xml, 从conf/businesssetting/qeweb-businesssetting.xml 中读取配置项, 
 * 供BusSettingPool使用
 */
public class BusSettingPoolHelper {
	//对应xml的属性
	private static final String SETTING = "setting";
	private static final String SETTING_BO_ID = "boId";
	private static final String SETTING_MODULENAME = "moduleName";
	private static final String SETTING_PARAMNAME = "paramName";
	private static final String SETTING_PARAMVALUE = "paramValue";
	private static final String SETTING_DESC = "desc";
	private static final String SETTING_REMARK = "remark";
	
	/**
	 * 获取业务流程配置信息
	 * @return map,  
	 * 	<li>key : boId + "-" + moduleName + "-" + paramName
	 *  <li>value : paramValue
	 */
	final static Map<String, BusSetting> getBusSetting() {
		Map<String, BusSetting> busSetting = new LinkedHashMap<String, BusSetting>();
		for(BusSetting setting : loadBusinessSetting()){
			busSetting.put(getBusSettingKey(setting), setting);
		}
		
		return busSetting;
	}

	/**
	 * 从businessSettingPath包中读取业务设置配置信息
	 * @return
	 */
	final private static Set<BusSetting> loadBusinessSetting(){
		Set<BusSetting> set = new LinkedHashSet<BusSetting>();
		File configFile = getXMLFile();
		if(configFile == null)
			return set;
		set.addAll(reloadXMLFile(configFile));

		return set;
	}
	
	
	/**
	 * boId + "-" + moduleName + "-" + paramName
	 * @param setting
	 * @return
	 */
	private static String getBusSettingKey(BusSetting setting) {
		return setting.getBoId() + ConstantSplit.GA_PARAM_SPLIT + setting.getModuleName() + ConstantSplit.GA_PARAM_SPLIT + setting.getParamName();
	}
	
	/**
	 * 从businessSettingPath包中读取页面所有的文件
	 * @return
	 */
	final public static File getXMLFile() {
		if(Envir.getContext() == null)
			return null;
		
		return new File(BusSettingConstants.getServerPath());
	}

	/**
	 *
	 * @param file
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static Set<BusSetting> reloadXMLFile(File file) {
		Set<BusSetting> set = new LinkedHashSet<BusSetting>();
		Document doc = XMLUtil.getXmlFile(file);
		if(doc == null)
			return set;

		Element rootElement = doc.getRootElement();
		for(Element busSettingEl : (List<Element>)rootElement.getChildren(SETTING)){
			BusSetting setting = loadBusSetting(busSettingEl);
			if(setting != null)
				set.add(setting);
		}

		return set;
	}

	/**
	 *
	 * @param busSettingEl
	 * @return
	 */
	private static BusSetting loadBusSetting(Element busSettingEl) {
		BusSetting setting = new BusSetting();
		setting.setBoId(busSettingEl.getAttributeValue(SETTING_BO_ID));
		setting.setModuleName(busSettingEl.getAttributeValue(SETTING_MODULENAME));
		setting.setParamName(busSettingEl.getAttributeValue(SETTING_PARAMNAME));
		setting.setParamValue(busSettingEl.getAttributeValue(SETTING_PARAMVALUE));
		setting.setDescription(busSettingEl.getAttributeValue(SETTING_DESC));
		setting.setRemark(busSettingEl.getAttributeValue(SETTING_REMARK));
		return setting;
	}

	
//	
//	/**
//	 * 获取FTP配置文件服务器路径
//	 * @return
//	 */
//	public static String getFTPServerPath() {
//		return Envir.getContext().getRealPath("/") + MIDDLEPATH + AppConfig.getPropValue(AppConfig.BUSINESSSETTING_PATH) + File.separator + QEWEB_BUSINESSSETTING_XML;
//	}
//
//	/**
//	 * 获取FTP配置文件客户端路径
//	 * @return
//	 */
//	public static String getFTPClientPath() {
//		return ProjectPathUtil.getProjectPath() + File.separator + CONF + AppConfig.getPropValue(AppConfig.BUSINESSSETTING_PATH) + File.separator + QEWEB_BUSINESSSETTING_XML;
//	}
}
