package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.dao.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.jdom.Element;

import com.qeweb.framework.base.impl.XmlDaoImpl;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.file.FileUtil;
import com.qeweb.framework.exception.QewebException;
import com.qeweb.framework.impconfig.common.VCType;
import com.qeweb.framework.impconfig.common.util.AnalyzeJspUtil;
import com.qeweb.framework.impconfig.common.util.pathutil.VarPagePathUtil;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bo.SheetBO;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.dao.ia.IVarPageDao;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.dao.ia.IVarPageUseDao;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.simplevc.SimpleVC;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.simplevc.container.SimpleContainer;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.simplevc.control.SimpleBtn;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.simplevc.finegrained.SimpleFC;
import com.qeweb.framework.impconfig.mdt.metadata.var.bo.VarBO;
import com.qeweb.framework.impconfig.mdt.metadata.var.dao.ia.IVarDao;
import com.qeweb.framework.pal.ViewComponent;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.coarsegrained.Sheet;
import com.qeweb.framework.pal.coarsegrained.Tab;

/**
 * 使用变量-组件关联, 通过变量-组件关联获取变量对应的组件
 */
public class VarPageUseDaoImpl extends XmlDaoImpl implements IVarPageUseDao {

	private static final long serialVersionUID = 4217500055376156786L;

	
	@Override
	public Map<String, VarBO> getRelateVarList(String pageURL, String containerId, String bind, int containerType) {
		Element relateEL = getRelateEl(pageURL, containerId, bind, containerType);
		if(relateEL == null)
			return null;
		
		String[] varArr = StringUtils.split(relateEL.getAttributeValue(IVarPageDao.ATTR_VARS), ",");
		if(StringUtils.isEmpty(varArr))
			return null;
		
		Map<String, VarBO> result = new HashMap<String, VarBO>();
		for(int i = 0; i < varArr.length; i++) {
			VarBO varBO = getVar(varArr[i]);
			if(varBO == null)
				return null;
			result.put(varArr[i], varBO);
		}
			
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SheetBO> getSheetList(String pageURL, String tabId, String bind, Map<String, String> currentValueMap) {
		List<Element> varGroupEls = getVarGroupEls(pageURL, tabId, bind, VCType.VC_TYPE_TAB);
		if(ContainerUtil.isNull(varGroupEls))
			return null;
		
		List<SheetBO> result = new LinkedList<SheetBO>();
		//判断变量当前值是否能和xml中的配置匹配
		for(Element el : varGroupEls) {
			if(ContainerUtil.isNotNull(result))
				break;
			
			if(!isMatch(el, currentValueMap))
				continue;
			
			Element vcEl = (Element) el.getChildren().get(0);
			List<Element> sheetEls = vcEl.getChildren();
			int idx = 0;
			for(Element sheetEl : sheetEls) {
				SheetBO bo = new SheetBO();
				bo.setId(idx++);
				bo.setText(sheetEl.getAttributeValue(IVarPageDao.ATTR_TEXT));
				bo.setPath(sheetEl.getAttributeValue(IVarPageDao.ATTR_PATH));
				result.add(bo);
			}
		}
		
		return result;
	}
	
	@Override
	public List<Sheet> getDynamicTab(List<SheetBO> sheetList, Tab tab) throws QewebException {
		if(ContainerUtil.isNull(sheetList))
			return null;
		
		List<Sheet> result = new LinkedList<Sheet>();
		//在动态tab中,变量关联的值集中的每个值应当代表一个jsp页面, 解析时,将值的text作为sheet的名称, 将value作为jsp路径
		// 动态解析JSP, 将jsp中的粗粒度组件设置到sheet中
		for (SheetBO sheetBO : sheetList) {
			// 读取JSP内容
			String jspContent = FileUtil.readFile(Envir.getContext().getRealPath("/") + sheetBO.getPath());
			List<Container> containerList = AnalyzeJspUtil.getContainers(jspContent, tab.getPageContextInfo());
			List<ViewComponent> vcList = new LinkedList<ViewComponent>();
			for (Container container : containerList) {
				container.setParent(tab);
				container.init();
				vcList.add(container);
			}
			//sheet的id不能以数字开头, 故此处需要在sheetBO.getId()添加字符串"s"
			Sheet sheet = new Sheet("s" + sheetBO.getId(), sheetBO.getText(), vcList, tab);
			result.add(sheet);
		}
	
		return result;
	}
	
	@Override
	public Map<String, SimpleContainer> getVarContainerRelation(String pageURL, String containerId, String bind, int containerType) {
		if(VCType.VC_TYPE_TABLE == containerType || VCType.VC_TYPE_FORM == containerType)
			return getContainers(pageURL, containerId, bind, containerType);
		else 
			return null;
	}
	
	/**
	 * 根据element为simpleVC设置VSR
	 * @param simpleVC
	 * @param element
	 */
	private void setVCVSR(SimpleVC simpleVC, Element element) {
		if(element == null || simpleVC == null)
			return;
		
		simpleVC.setDisable(StringUtils.convertToBool(element.getAttributeValue(IVarPageDao.ATTR_DISABLE)));
		simpleVC.setReadonly(StringUtils.convertToBool(element.getAttributeValue(IVarPageDao.ATTR_READONLY)));
		simpleVC.setHidden(StringUtils.convertToBool(element.getAttributeValue(IVarPageDao.ATTR_HIDDEN)));
		simpleVC.setValueStr(element.getAttributeValue(IVarPageDao.ATTR_VALUE));
	}
	
	/**
	 * 参数pageURL, containerId, bind, containerType可以指定唯一组件, 
	 * getContainers将获取变量值如何影响该组件.
	 * @param pageURL
	 * @param containerId
	 * @param bind
	 * @param containerType
	 * @return map  key:能够影响组件的变量信息,  value:SimpleContainer
	 * @throws QewebException
	 */
	@SuppressWarnings("unchecked")
	private Map<String, SimpleContainer> getContainers(String pageURL, String containerId, String bind, int containerType) {
		//能够影响组件的变量组合, 即relate中的所有vars节点
		List<Element> varGroupEls = getVarGroupEls(pageURL, containerId, bind, containerType);
		if(ContainerUtil.isNull(varGroupEls))
			return null;
		
		Map<String, SimpleContainer> result = new LinkedHashMap<String, SimpleContainer>();
		//遍历变量组
		for(Element varGroupEl : varGroupEls) {
			String varGroups = getVarGroups(varGroupEl.getAttributeValue(IVarPageDao.ATTR_GROUP));
			List<Element> vcEls = varGroupEl.getChildren();
			if (ContainerUtil.isNull(vcEls))
				continue;
			
			SimpleContainer container = new SimpleContainer(containerId, bind);
			for(Element vcEl : vcEls) {
				//设置变量影响的粗粒度组件
				if(VCType.isContainer(StringUtils.convertToInt(vcEl.getAttributeValue(IVarPageDao.ATTR_VC_TYPE)))) {
					setVCVSR(container, vcEl);
					continue;
				}
				
				//设置变量影响的细粒度组件和控制组件
				SimpleVC vc = null;
				if(VCType.isFC(StringUtils.convertToInt(vcEl.getAttributeValue(IVarPageDao.ATTR_VC_TYPE))))
					vc = new SimpleFC();
				else if(VCType.isButton(StringUtils.convertToInt(vcEl.getAttributeValue(IVarPageDao.ATTR_VC_TYPE))))
					vc = new SimpleBtn();
				else
					continue;
				
				vc.setBind(vcEl.getAttributeValue(IVarPageDao.ATTR_BIND));
				setVCVSR(vc, vcEl);
				container.addSimpleVC(vc);
			}
			
			result.put(varGroups, container);
		}

		return result;
	}
	
	/**
	 * 将varGroups重组, 重组后的结果有以下特点:
	 * <li>包含多个变量名称,以逗号分隔;
	 * <li>变量名称按从小到大排序.
	 * @param varGroups	变量组合标识
	 * 
	 * <ul>
	 * 变量组合标识是由任意个变量的name及值集和值组合而成，name与值集和值间用“=”分隔，多个变量间用“,”分隔,
	 * 如, 当var1=1,var2=2,var3='',var4=4 时将影响 SimpleContainer, 1在值集vs1中,2在值集vs2中,4在值集vs4中
	 * 则变量组合标识为: var1=vs1:1,var2=vs2:2,var3=,var4=vs4:4
	 * </ul>
	 * <ul>
	 * 组件也可以直接受变量对应的值集影响, 即值集中的每一个值都可以作用于组件, 此时变量组合标识中不包含值,
	 * 如, 当var1等于值集vs1的每个值时都将影响SimpleContainer,
	 * 则变量组合表示为: var1=vs1
	 * </ul>
	 * 
	 * @return 重组后的varGroups
	 */
	private String getVarGroups(String varGroups) {
		Set<String> set = new TreeSet<String>();
		for(String str : StringUtils.split(varGroups, ",")){
			set.add(str);
		}
		
		return StringUtils.toString(set);
	}
	
	/**
	 * 判断变量当前值是否能和xml中的配置匹配
	 * @param el 变量节点
	 * @param currentValueMap  变量当前值map
	 * @return
	 */
	private boolean isMatch(Element el, Map<String, String> currentValueMap) {
		boolean result = false;
		
		//变量组合, 型如 group="var_que1=que1:3,var_que2=que2:4 或 group="var_que1=que1,var_que2=que2" 
		String varGroups = el.getAttributeValue(IVarPageDao.ATTR_GROUP);
		
		//判断变量当前值currentValueMap是否能和varGroups匹配
		//1.按变量拆分varGroups
		Map<String, String> varMap = new HashMap<String, String>();
		String[] varArr = StringUtils.split(varGroups, ",");
		for(int i = 0; i < varArr.length; i++) {
			String[] temp = varArr[i].split("=");
			varMap.put(temp[0], temp[1]);
		}
		
		//2.判断变量当前值是否能和varGroups匹配
		for(Entry<String ,String> entry : currentValueMap.entrySet()) {
			result = true;
			//变量当前值
			String currentVarValue = entry.getValue();
			//变量设定值
			String[] varValueGroup = varMap.get(entry.getKey()).split(":");
			String varValue = varValueGroup.length == 2 ? varValueGroup[1] : "";
			
			if(StringUtils.isNotEqual(currentVarValue, varValue)) {
				result = false;
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * 获取变量信息
	 * @param varName
	 * @return
	 */
	private VarBO getVar(String varName) {
		try {
			List<VarBO> result = getVarDao().getVarByName(varName);
			if(ContainerUtil.isNotNull(result))
				return result.get(0);
			else 
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
     * 
     * @param pageURL
     * @param containerId
     * @param bind
     * @param containerType
     * @return
     */
    private Element getRelateEl(String pageURL, String containerId, String bind, int containerType) {
	    Element rootElement = getRootElement();
		if(rootElement == null)
			return null;
		
		List<Element> relateElements = getElmentsByXpath(getXPath_Relate(pageURL, containerId, bind, containerType), rootElement);
		
		if(ContainerUtil.isNull(relateElements))
			return null;
		
		try {
			if(relateElements.size() > 1) 
				throw new QewebException("pageURL, containerId, bind, containerTpe 不能定位唯一记录！");
		}
		catch(QewebException e) {
			e.printStackTrace();
			return null;
		}
		
		return relateElements.get(0);
    }

		
	/**
	 * 
	 * @param pageURL
	 * @param containerId
	 * @param bind
	 * @param containerType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Element> getVarGroupEls(String pageURL, String containerId, String bind, int containerType) {
		Element relateEL = getRelateEl(pageURL, containerId, bind, containerType);
		if (relateEL == null)
			return null;

		return relateEL.getChildren(IVarPageDao.NODE_VARS);
	}
    
	/**
	 * 返回qeweb-var_page.xml的root节点
	 * @return
	 */
	private Element getRootElement(){
		return getRootElement(VarPagePathUtil.getClientVarPagePath(), VarPagePathUtil.getServerVarPagePath());
	}
	
	/**
	 * 查询条件(查询relate节点)
	 * @param pageURL
	 * @param containerId
	 * @param bind
	 * @param containerType
	 * @return
	 */
	private String getXPath_Relate(String pageURL, String containerId, String bind, int containerType) {
		String result = "/" + IVarPageDao.ROOT + "/" + IVarPageDao.NODE_RELATE + "[@" + IVarPageDao.ATTR_PAGE + "='" + pageURL + "']" +
			"[@" + IVarPageDao.ATTR_VC_ID + "='" + containerId + "']";
		if(StringUtils.isNotEmpty(bind))
			bind += "[@" + IVarPageDao.ATTR_BIND + "='" + bind + "']";
		
		result += "[@" + IVarPageDao.ATTR_VC_TYPE + "='" + containerType + "']";
		
		return result;
	}
	
	private IVarDao getVarDao() {
		return (IVarDao)SpringConstant.getCTX().getBean("varDao");
	}
}
