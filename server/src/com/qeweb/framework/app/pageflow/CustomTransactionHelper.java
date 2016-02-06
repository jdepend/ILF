package com.qeweb.framework.app.pageflow;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.XMLUtil;
import com.qeweb.framework.common.utils.file.FileUtil;

/**
 * 在个人事务中执行特定跳转后执行方法的工具类
 *
 */
public class CustomTransactionHelper {

	private static final String PATH = "/WEB-INF/classes/customtransaction";	//读取路径
	private static final String NODE_NAME = "customTransaction";				//节点名称
	private static final String BO_ID = "boId";
	private static final String METHOD = "method";
	private static final String ISREADONLY = "readOnly";
	
	/**
	 * 需要自定义事务的方法及其使用的事务类型（key：boId.方法名 value：是否只读事务）
	 */
	private Map<String, Boolean> customMethods;
	
	public static CustomTransactionHelper helper;
	
	private CustomTransactionHelper(){
		customMethods = this.reload();
	}
	
	public static CustomTransactionHelper getInstance(){
		if(helper == null || AppConfig.isDebug()){
			helper = new CustomTransactionHelper();
		}
		return helper;
	}
	
	public boolean isSubmit(String operate){
		if(StringUtils.isEmpty(operate))
			return false;
		if(this.customMethods.containsKey(operate))
			return !this.customMethods.get(operate);
		return false;
	}
	
	public boolean isReadonly(String operate){
		if(StringUtils.isEmpty(operate))
			return false;
		if(this.customMethods.containsKey(operate))
			return this.customMethods.get(operate);
		return false;
	}
	
	/**
	 * 从指定包中读取配置信息
	 * @return
	 */
	private Map<String, Boolean> reload(){
		Map<String, Boolean> map = new LinkedHashMap<String, Boolean>();
		File[] configFiles = getXMLFiles();
		if(configFiles == null)
			return map;
		
		for(File file : configFiles){
			map.putAll(reloadXMLFile(file));
		}
		
		return map;
	}

	/**
	 * 从指定包中读取页面所有的文件
	 * @return
	 */
	private static File[] getXMLFiles() {
		String realPath = Envir.getContext().getRealPath("/");		
		return FileUtil.getfiles(realPath + PATH);
	}
	
	/**
	 * @param file
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Boolean> reloadXMLFile(File file){
		Map<String, Boolean> map = new LinkedHashMap<String, Boolean>();
		Document doc = XMLUtil.getXmlFile(file);
		if(doc == null)
			return map;
		
		Element rootElement = doc.getRootElement();
		for(Element el : (List<Element>)rootElement.getChildren(NODE_NAME)){
			String boId =  el.getAttributeValue(BO_ID);
			String method = el.getAttributeValue(METHOD);
			if(StringUtils.isEmpty(boId) || StringUtils.isEmpty(method))
				continue;
			boolean isReadOnly = StringUtils.convertToBool(el.getAttributeValue(ISREADONLY));
			map.put(boId + "." + method, isReadOnly);
		}
		return map;
	}
}
