package com.qeweb.framework.common.dataisland;

import java.util.List;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.constant.ConstantDataIsland;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.coarsegrained.Tab;
import com.qeweb.framework.pal.coarsegrained.Table;
import com.qeweb.framework.pal.coarsegrained.Tree;
import com.qeweb.framework.pal.decorativeview.ContainerRelationGroup;

/**
 * 数据岛 抽象类
 */
public abstract class DataIsland implements ConstantDataIsland {
	
	/**
	 * 粗粒度组件关联关系数据岛
	 * @param containerRelationGroup	粗粒度组件关联关系
	 * @return
	 */
	public abstract String createConRelationIsland(ContainerRelationGroup containerRelationGroup);
	
	/**
	 * 创建页面上所有粗粒度组件的数据岛
	 * @param page 
	 * @return 页面数据岛
	 */
	public abstract String createDataIsland(com.qeweb.framework.pal.Page page);
	
	/**
	 * 创建form的数据岛
	 * @param container
	 * @return
	 */
	public abstract String createFormDataIsland(Container container);
	
	/**
	 * 创建table弹出框(新增/修改/查看弹出框)的数据岛
	 * @param table
	 * @return
	 */
	public abstract String createHiddenFormDataIsland(Table table, String hiddenFormId);
	
	/**
	 * 创建table的数据岛
	 * 先从dataIsland中还原column，再把page转换重数据岛
	 * @param name 控件的Name
	 * @param page 
	 * @param dataIsland 页面的数据岛
	 * @param sourcePage 页面路径
	 */
	public abstract String createTableDataIsland(String containerName, Page page, String dataIsland, String sourcePage);
	
	/**
	 * 创建table的数据岛
	 * 先从dataIsland中还原column，再把bo的状态赋予bolist
	 * @param name 控件的Name
	 * @param bo 
	 * @param dataIsland 页面的数据岛
	 * @param sourcePage 页面路径
	 */
	public abstract String createTableDataIsland(String containerName, BusinessObject bo, String dataIsland, String sourcePage);
	
	/**
	 * 创建table的数据岛
	 * @param table
	 * @param sourcePage 页面路径
	 * @return
	 */
	public abstract String createTableDataIsland(Table table, String sourcePage);
	
	/**
	 * 创建tree的数据岛
	 * @param tree 		
	 * @return 页面数据岛
	 */
	public abstract String createTreeDataIsland(Tree tree);
	
	/**
	 * 创建tab的数据岛
	 * @param tab
	 * @param sourcePage
	 * @return
	 */
	public abstract String createTabDataIsland(Tab tab, String sourcePage);
	
	/**
	 * 根据id从数据岛中创建相应Bop
	 * @param finegrainedId 细粒度组件Id
	 * @param dataIsland 数据岛
	 * @return
	 */
	public abstract BOProperty revertBOPById(String finegrainedId, String dataIsland);
	
	/**
	 * 根据dataisland_boId从数据岛中还原相应Bo.
	 * <br>
	 * 从Form中还原的BOList仅有一个BO；
	 * 从Table中还原的BOList中有多个BO
	 * 
	 * @param dataisland_boId 数据到中bo节点的唯一标识  
	 * @param dataIsland
	 * @return
	 */
	public abstract List<BusinessObject> revertBOList(String dataisland_boId, String dataIsland);
	
	/**
	 * 根据数据岛创建BOTemplate
	 * @param containId, 粗粒度组件Id  
	 * @param dataIsland
	 * @return
	 */
	public abstract BOTemplate createBOTemplate(String containerId, String dataIsland);
	
	/**
	 * 更新由细粒度组件发生改变而影响的其它细粒度组件
	 * @param dataIsland
	 * @param bopList 
	 * 		受到影响并已经更新的细粒度组件
	 * @return 
	 * 		数据岛中仅包含发生改变的细粒度组件，即仅包含bopList或bopList的子集
	 */
	public abstract String updateFRelationDataIsland(String dataIsland, Map<String, BusinessComponent> bopList);

	/**
	 * 更新由细粒度组件发生改变而影响的粗粒度组件
	 * @param dataIsland
	 * @param resultMap 
	 * 		受到影响并已经更新的粗粒度组件
	 * @return 
	 * 		数据岛中仅包含发生改变的粗粒度组件，即仅包含bo或boList或page
	 */
	public abstract String updateFCRelationDataIsland(String dataIsland, Map<String, Object> resultMap); 
	
	/**
	 * 更新由粗粒度组件发生改变而影响的其它粗粒度组件
	 * @param dataIsland 数据岛
	 * @param boList 粗粒度组件数据
	 * @param relations 粗粒度组件的关联关系
	 * @param sourcePage 页面路径
	 * @return
	 */
	public abstract String updateCRelationDataIsland(String dataIsland, Map<String, Object> boList, String relations, String sourcePage);

	/**
	 * 数据岛中还原相应bo
	 * @param dataIsland 数据岛
	 * @return
	 */
	public abstract BusinessObject revertRelationBO(String dataIsland);

	/**
	 * 从数据岛还原对象需要显示的bop<br>
	 * 查询时的bo可能不包含这些bop，应此需要重新加载
	 * @param dataisland_boId 表格id
	 * @param dataIsland
	 * @param bo
	 */
	public abstract void revertBop(String dataisland_boId, String dataIsland,
			BusinessObject bo);

	/**
	 * 更dataIsland中boId指代的数据
	 * @param dataIsland
	 * @param boId
	 * @param boBind
	 * @param data
	 * @return
	 */
	public abstract String updateContainer(String dataIsland, String boId, String boBind, Object data);
	
	/**
	 * 更dataIsland中的全局按钮
	 * @param dataIsland
	 * @param boBind
	 * @param bo
	 * @return
	 */
	public abstract String updatePageOperate(String dataIsland, String boBind, BusinessObject bo);
}
