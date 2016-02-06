package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.common.VCType;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.dao.ia.IVarPageUseDao;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.dao.impl.VarPageUseDaoImpl;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.simplevc.container.SimpleContainer;
import com.qeweb.framework.impconfig.mdt.metadata.valueset.bo.MdtValueSetBO;
import com.qeweb.framework.impconfig.mdt.metadata.var.bo.VarBO;
import com.qeweb.framework.impconfig.mdt.metadata.var.dao.ia.IVarDao;
import com.qeweb.framework.impconfig.mdt.metadata.var.dao.impl.VarDaoImpl;

/**
 * 变量关联页面.
 * <p>
 * 变量关联页面是MDT 4+3模型中的一种: 变量影响页面组件(JSP页面中标签代表的组件)的值/状态/范围.
 * </p>
 */
public class Var_Page implements Serializable {
	private static final long serialVersionUID = -7309857693136076153L;
	
	private String pageURL;					//页面URL
	private String containerId;				//粗粒度组件ID
	private String bind;					//粗粒度组件bind
	private int containerType;				//粗粒度组件类型
	private Map<String, VarBO> varMap;		//能够影响当前组件的变量, key:varId, value:varBO
	
	/*
	 * varContainerRelation存储变量和粗粒度组件间的关联.
	 * varContainerRelation<变量组合标识, SimpleContainer>中, 变量组合标识是由任意个变量的name及值集和值组合而成，name与值集和值间用“=”分隔，多个变量间用“,”分隔,
	 * 如, 当var1=1,var2=2,var3='',var4=4 时将影响 SimpleContainer, 1在值集vs1中,2在值集vs2中,4在值集vs4中, 值集用编码标识
	 * 则变量组合标识为: var1=vs1:1,var2=vs2:2,var3=,var4=vs4:4
	 * 
	 * 组件也可以直接受变量对应的值集影响, 即值集中的每一个值都可以作用于组件, 此时变量组合标识中不包含值,
	 * 如, 当var1等于值集vs1的每个值时都将影响SimpleContainer,
	 * 则变量组合表示为: var1=vs1
	 * 
	 * SimpleContainer表示在当前变量值的作用下影响的粗粒度组件.
	 * 变量的值应当在值集中.
	 */
	private Map<String, SimpleContainer> varContainerRelation;
	
	private static IVarPageUseDao varPageUseDao;
	private static IVarDao varDao;

	public Var_Page(String pageURL, String containerId, String bind, int containerType) {
		if(VCType.VC_TYPE_TAB == containerType)
			return;
		
		this.varContainerRelation = new HashMap<String, SimpleContainer>();
		this.pageURL = pageURL;
		this.containerId = containerId;
		this.bind = bind;
		this.containerType = containerType;
		this.varMap = getVarPageUseDao().getRelateVarList(pageURL, containerId, bind, containerType);
		
		addContainer();
	}
	
	/**
	 * 获取变量在当前值作用下影响的粗粒度组件
	 * @param varCtxMap, 变量当前值组合,  key:varName, value:varValue
	 * @return
	 * @throws Exception 
	 */
	public SimpleContainer getRelateContainer(Map<String, String> varCtxMap) throws Exception {
		//如果当前组件不受任何变量影响, 返回null
		if(ContainerUtil.isNull(getVarMap()) || ContainerUtil.isNull(getVarContainerRelation()) || ContainerUtil.isNull(varCtxMap))
			return null;
		
		//如果上下文中没有当前变量信息, 返回null
		for(Entry<String, VarBO> entry : getVarMap().entrySet()) {
			if(!varCtxMap.containsKey(entry.getValue().getName()))
				return null;
		}
		
		for(Entry<String, SimpleContainer> entry : getVarContainerRelation().entrySet()) {
			String varGroup = entry.getKey();
			//变量名称和值集的分组
			String[] arr = StringUtils.split(varGroup, ","); 
			boolean result = true;
			for(int i = 0; i < arr.length; i++) {
				String[] vGroup = StringUtils.split(arr[i], "=");
				if(StringUtils.isEmpty(vGroup) || vGroup.length != 2)
					return null;

				//变量name
				String varName = vGroup[0];
				//varId对应的当前值
				String currentValue = varCtxMap.get(varName);
				//varId对应的 值集和值
				String valueSign = vGroup[1];
				
				//判断变量varId的当前值currentValue是否在valueSign内
				if(!isInVarGroup(varName, currentValue, valueSign)) {
					result = false;
					break;
				}
			}
			
			if(result)
				return getVarContainerRelation().get(varGroup);
		}
		
		
		return null;
	}


	/**
	 * 判断变量varName的当前值currentValue是否在valueSign内
	 * @param varName
	 * @param varCurrentValue
	 * @param valueSign 值和值集的组合, 二者以:分隔.
	 * 			<li>vs1:1, 表示值集是vs1,值是1;
	 *  		<li>vs2, 表示值集是vs2
	 * @return
	 */
	private boolean isInVarGroup(String varName, String varCurrentValue, String valueSign) {
		VarBO varBO = getVarMap().get(varName);
		String[] arr = StringUtils.split(valueSign, ":");
		//变量的设定值
		String value = arr.length > 1 ? arr[1] : null;
		
		//如果当前值和变量的设定值都为空, 返回true
		if(StringUtils.isEmpty(value) && StringUtils.isEmpty(varCurrentValue))
			return true;
		//如果当前值为空, 变量的设定值不为空, 返回false
		else if(StringUtils.isEmpty(varCurrentValue) && StringUtils.isNotEmpty(value))
			return false;
		//如果当前值和变量的设定值都不为空, 返回StringUtils.isEqual(value, varCurrentValue)
		else if(StringUtils.isNotEmpty(varCurrentValue) && StringUtils.isNotEmpty(value))
			return StringUtils.isEqual(value, varCurrentValue);
		
		//如果如果当前值不为空, 设定值为空, 判断变量值是否在值集中
		Set<MdtValueSetBO> valueSets = varBO.getValueSetBOSet();
		if(ContainerUtil.isNull(valueSets))
			return false;
		
		for(MdtValueSetBO vs : valueSets) {
			if(vs.isExistValue(varCurrentValue))
				return true;
		}
		
		return false;
	}

	/**
	 * 将变量影响的container添加到varContainerRelation
	 */
	private void addContainer() {
		setVarContainerRelation(getVarPageUseDao().getVarContainerRelation(pageURL, containerId, bind, containerType));
	}
	
	public Map<String, SimpleContainer> getVarContainerRelation() {
		return varContainerRelation;
	}

	public void setVarContainerRelation(Map<String, SimpleContainer> varContainerRelation) {
		this.varContainerRelation = varContainerRelation;
	}

	public String getPageURL() {
		return pageURL;
	}

	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}

	public String getContainerId() {
		return containerId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public String getBind() {
		return bind;
	}

	public void setBind(String bind) {
		this.bind = bind;
	}

	public int getContainerType() {
		return containerType;
	}

	public void setContainerType(int containerType) {
		this.containerType = containerType;
	}

	public Map<String, VarBO> getVarMap() {
		return varMap;
	}

	public void setVarMap(Map<String, VarBO> varMap) {
		this.varMap = varMap;
	}

	public static IVarDao getVarDao() {
		if(varDao == null)
			varDao = new VarDaoImpl();
		return varDao;
	}

	public static IVarPageUseDao getVarPageUseDao() {
		if(varPageUseDao == null)
			varPageUseDao = new VarPageUseDaoImpl();
		return varPageUseDao;
	}
}
