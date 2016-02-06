package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.dao.ia;

import java.util.List;
import java.util.Map;

import com.qeweb.framework.base.ia.IXmlDao;
import com.qeweb.framework.exception.QewebException;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bo.SheetBO;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.simplevc.container.SimpleContainer;
import com.qeweb.framework.impconfig.mdt.metadata.var.bo.VarBO;
import com.qeweb.framework.pal.coarsegrained.Sheet;
import com.qeweb.framework.pal.coarsegrained.Tab;

/**
 * 使用变量-组件关联, 通过变量-组件关联获取变量对应的组件
 */
public interface IVarPageUseDao extends IXmlDao {

	/**
	 * 获取能够影响当前组件的变量
	 * @param pageURL
	 * @param containerId
	 * @param bind
	 * @param containerType
	 * @return Map key:varName, value:varBO
	 */
	Map<String, VarBO> getRelateVarList(String pageURL, String containerId, String bind, int containerType);
	
	/**
	 * 查询动态tab在当前值下的对应的sheet信息
	 * @param pageURL
	 * @param tabId
	 * @param bind
	 * @param currentValueMap	影响动态tab的变量及变量的当前值, key:varName, value:varValue 
	 * @return
	 */
	List<SheetBO> getSheetList(String pageURL, String tabId, String bind, Map<String, String> currentValueMap);
	
	/**
	 * 获取动态tab
	 * @param sheetList 动态sheet信息
	 * @param tab
	 * @return
	 * @throws QewebException
	 */
	List<Sheet> getDynamicTab(List<SheetBO> sheetList, Tab tab) throws QewebException;

	/**
	 * 变量能够影响的当前粗粒度组件.
	 * 返回map中KEY的格式：var1:value,var2:value,var3:value
	 * <p>
	 * getVar2PageBean负责解析qeweb-var_page.xml,将一个relate节点解析为若干个Var2PageBean.
	 * @see qeweb-var_page.xml
	 * 
	 * @param pageURL
	 * @param containerId
	 * @param bind
	 * @param containerType
	 * @return
	 */
	Map<String, SimpleContainer> getVarContainerRelation(String pageURL, String containerId, String bind, int containerType);
}
