package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.dao.ia;

import java.util.List;

import com.qeweb.framework.base.ia.IXmlDao;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bo.AnalyzeComponentBO;

/**
 * 配置变量与页面映射时用于解析jsp的dao
 *
 */
public interface IAnalyzeDao extends IXmlDao {
	
	/**
	 * 解析jsp中的所有粗粒度组件
	 * @param url
	 * @return
	 */
	List<AnalyzeComponentBO> analyzeContainer(String url);
	
	/**
	 * 解析jsp中指定粗粒度组件包含的所有组件
	 * @param url
	 * @param containerId
	 * @return
	 */
	List<AnalyzeComponentBO> analyzeComponent(String url, String containerId);
}
