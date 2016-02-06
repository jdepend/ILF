package com.qeweb.framework.impconfig.ddt.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.base.impl.BaseDaoInfo;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.utils.BoOperateUtil;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.GuidUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.file.FileUtil;
import com.qeweb.framework.impconfig.common.VCType;
import com.qeweb.framework.impconfig.common.util.AnalyzeJspUtil;
import com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysAnalyzeContainerBO;
import com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysAnalyzeFcBO;
import com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysContainerBO;
import com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysFcBO;
import com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysModulesBO;
import com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysPagesBO;
import com.qeweb.framework.manager.BOManager;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.coarsegrained.Form;
import com.qeweb.framework.pal.coarsegrained.Tab;
import com.qeweb.framework.pal.coarsegrained.Table;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;

/**
 * 解析jspHandler
 */
public class AnalyzeJspHandler {


	private static Map<String, List<Element>> rootMap = new LinkedHashMap<String, List<Element>>();
	
	/**
	 * 解析JSP中的粗粒度组件
	 * 
	 * @param module
	 * @param page
	 * @return
	 */
	final public static List<DdtSysAnalyzeContainerBO> analyzeContainer(DdtSysModulesBO module, DdtSysPagesBO page) {
		//读取jsp文件内容, 将格式化的标签结构解析为展示组件
		String jspContent = FileUtil.readFile(page.getPageRealPath());
		List<Container> containerList = AnalyzeJspUtil.getContainers(jspContent, null, false);
		
		if(ContainerUtil.isNull(containerList))
			return null;
		
		List<DdtSysAnalyzeContainerBO> result = new LinkedList<DdtSysAnalyzeContainerBO>();
		for(Container container : containerList) {
			DdtSysAnalyzeContainerBO bo = new DdtSysAnalyzeContainerBO();
			bo.setId(GuidUtil.getGUID());
			bo.setModule(module);
			bo.setPage(page);
			bo.setContainerId(container.getId());
			bo.setBoName(container.getBcId());
			if (container instanceof Form) {
				bo.setContainerType(VCType.VC_TYPE_FORM);
				bo.setContainerTypeShow("Form");
			}
			else if (container instanceof Table) {
				bo.setContainerType(VCType.VC_TYPE_TABLE);
				bo.setContainerTypeShow("Table");
			}
			else if (container instanceof Tab) {
				bo.setContainerType(VCType.VC_TYPE_TAB);
				bo.setContainerTypeShow("Tab");
			}
			
			result.add(bo);
		}
		
		return result;
	}

	/**
	 * 解析BOP
	 * @param module
	 * @param page
	 * @return
	 */
	final public static List<DdtSysAnalyzeFcBO> analyzeFc(DdtSysModulesBO module, DdtSysPagesBO page, Integer containerType, String boName) {
		//读取jsp文件内容, 将格式化的标签结构解析为展示组件
		String jspContent = FileUtil.readFile(page.getPageRealPath());
		List<Container> containerList = AnalyzeJspUtil.getContainers(jspContent, null, false);
		
		if(ContainerUtil.isNull(containerList))
			return null;
		
		List<DdtSysAnalyzeFcBO> result = new LinkedList<DdtSysAnalyzeFcBO>();
		for(Container container : containerList) {
			if(VCType.VC_TYPE_FORM == containerType && !(container instanceof Form))
				continue;
			else if(VCType.VC_TYPE_TABLE == containerType && !(container instanceof Table))
				continue;
			else if(VCType.VC_TYPE_TAB == containerType)
				continue;
			
			//粗粒度组件是否在DDT中配置
			DdtSysContainerBO containerBO = getContainer(container.getId(), container.getBcId(), page.getId());
			if(containerBO == null)
				continue;
			
			BusinessObject bo = container.getBc();
			String[] arr = StringUtils.split(boName, ".");
			//BO中所有的BOP
			Set<String> fields = null;
			if(arr.length > 1){
				fields = getBOFields(bo.getClass(), arr);	
			}
			else {
				BOHelper.initPreferencePage(bo);
				fields = getBOFields(bo);
			}
			
			//JPS页面中的组件(通过标签画出的组件)
			Set<String> fieldsInPage = new LinkedHashSet<String>();
			for(Entry<String, FinegrainedComponent> entry : container.getFcList().entrySet()) {
				fieldsInPage.add(entry.getValue().getBcId());
			}
			//只保留页面中未展示的FC(通过标签画出的组件被认为是参与计算的组件, 不可通过DDT配置)
			fields.removeAll(fieldsInPage);
			
			int count = 1;
			for(String fieldName : fields){
				DdtSysAnalyzeFcBO ddtBO = new DdtSysAnalyzeFcBO();
				ddtBO.setId(count);
				ddtBO.setModule(module);
				ddtBO.setPage(page);
				ddtBO.setContainer(containerBO);
				
				DdtSysFcBO fc = new DdtSysFcBO();
				fc.setBopname(fieldName);				
				ddtBO.setFc(fc);
				result.add(ddtBO);
				count++;
			}
		}
		
		return result;
	}
	
	
	//TODO 以下代码待重构
	/**
	 * 解析指定页面中指定粗粒度类型的对应BO名称
	 * @param page
	 * @param containerType
	 * @return
	 */
	final public static EnumRange analyzeContainerBoName(DdtSysPagesBO page, Integer containerType){
		List<Element> containerElements = getContainerElements(page);
		EnumRange range = new EnumRange();
		Map<String, String> result = new LinkedHashMap<String, String>();
		range.setResult(result);
		for (Element containerEl : (List<Element>) containerElements) {
			String containerTypeShow = getContainerTypeShow(containerType);
			if(!StringUtils.isEqualIgnoreCase(containerEl.getName(), containerTypeShow))
				continue;
			
			// 过滤系统中不存在的粗粒度组件
			DdtSysContainerBO containerBO = getContainer(containerEl.getAttributeValue("id"), containerEl.getAttributeValue("bind"), page.getId());
			if(containerBO == null)
				continue;
			
			BusinessObject container = BOManager.createBO(containerBO.getBoName());
			if(container == null)
				continue;
			result.put(containerBO.getBoName(), containerBO.getBoName());
			
			result.putAll(addOtherBOs(container.getClass(), containerBO.getBoName()));
		}
		return range;
	}
	
	/**
	 * 解析bo中所有类型为BO的属性的名称
	 * @param clz
	 * @param prefix
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static Map<String, String> addOtherBOs(
			Class clz, String prefix) {
		Map<String, String> result = new LinkedHashMap<String, String>();
		if(clz.getSuperclass() != null){
			result.putAll(addOtherBOs(clz.getSuperclass(), prefix));
		}
		for(Field field : clz.getDeclaredFields()){			
			if(!BoOperateUtil.isBO(field.getType()))
				continue;
			String subBOName = prefix + "." + field.getName();
			result.put(subBOName, subBOName);
			result.putAll(addOtherBOs(field.getType(), subBOName));
		}
		
		return result;
	}


	/**
	 * 获取指定页面中的所有粗粒度组件元素
	 * @param page
	 * @return
	 */
	private static List<Element> getContainerElements(DdtSysPagesBO page){
		List<Element> containerElements = null;
		if(rootMap.isEmpty() || !rootMap.containsKey(page.getPageRealPath())){
			Document doc = getPageDoc(page.getPageRealPath());
			if(doc == null)
				return containerElements;
			
			Element root = doc.getRootElement();
			containerElements = AnalyzeJspUtil.getXmlDao().getElmentsByXpath(getXPath_Container(), root);
			rootMap.put(page.getPageRealPath(), containerElements);
		}
		return rootMap.get(page.getPageRealPath());
	}

	/**
	 * 获取粗粒度组件类型
	 * @param module
	 * @param page
	 * @param fcBOs
	 * @param containerElements
	 */
	private static String getContainerTypeShow(Integer containerType) {
		if(VCType.VC_TYPE_FORM == containerType.intValue()) {
			return "Form";
		}
		if(VCType.VC_TYPE_TABLE == containerType.intValue()) {
			return "Table";
		}
		if(VCType.VC_TYPE_TAB == containerType.intValue()) {
			return "Tab";
		}
		return null;
	}

	/**
	 * 获取bo对象中的非bo类型的属性名称
	 * @param bo
	 * @return
	 */
	private static Set<String> getBOFields(BusinessObject bo) {
		Set<String> fields = new LinkedHashSet<String>();
		for(Entry<String, BusinessComponent> entry : bo.getBcList().entrySet()){
			if(entry.getValue() instanceof BusinessObject)
				continue;
			fields.add(entry.getKey());
		}		
		return fields;
	}
		
	/**
	 * 获取指定bo对象中的非bo类型的属性名称
	 * @param clz
	 * @param arr
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static Set<String> getBOFields(Class clz, String[] arr) {
		Set<String> fields = new LinkedHashSet<String>();
		if(clz.getSuperclass() != null 
				&& StringUtils.isNotEqual("BusinessObject", clz.getSimpleName())){
			fields.addAll(getBOFields(clz.getSuperclass(), arr));
		}
		Class currentBO = clz;
		String prefix = arr[0];
		for(int i = 1; i < arr.length; i++){
			try {
				prefix += "." + arr[i];
				currentBO = currentBO.getDeclaredField(arr[i]).getType();
			} 
			catch (SecurityException e) {} 
			catch (NoSuchFieldException e) {}
		}
		if(currentBO == null)
			return fields;
		
		BusinessObject bo;
		try {
			bo = (BusinessObject) currentBO.newInstance();
			BOHelper.initPreferencePage(bo);
			for(Entry<String, BusinessComponent> entry : bo.getBcList().entrySet()){
				if(entry.getValue() instanceof BusinessObject)
					continue;
				fields.add(prefix + "." + entry.getKey());
			}	
		} 
		catch (InstantiationException e) {} 
		catch (IllegalAccessException e) {}
		
		return fields;
	}

	/**
	 * 查询DdtSysContainerBO
	 * @param containerId
	 * @param boName
	 * @param pageId
	 * @return
	 */
	private static DdtSysContainerBO getContainer(String containerId, String boName, long pageId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DdtSysContainerBO.class);
		criteria.add(Restrictions.eq("containerId", containerId));
		criteria.add(Restrictions.eq("boName", boName));
		criteria.add(Restrictions.eq("page.id", pageId));
		criteria.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));

		return (DdtSysContainerBO) BaseDaoInfo.getDao().get(criteria);
	}

	/**
	 * 从jsp截取qeweb:page , 转换为DOC
	 * @param path
	 * @return
	 */
	private static Document getPageDoc(String path) {
		if (!FileUtil.isFile(path))
			return null;
		File file = new File(path);

		Document doc = null;
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String s = null;
			Pattern ps = Pattern.compile(AnalyzeJspUtil.START);
			Pattern pe = Pattern.compile(AnalyzeJspUtil.END);
			Matcher ms = null;
			Matcher me = null;
			StringBuilder sbr = new StringBuilder();
			boolean start = false;
			boolean end = false;
			while ((s = br.readLine()) != null) {
				ms = ps.matcher(s);
				me = pe.matcher(s);
				if (!start)
					start = ms.find();
				// 开始处理
				if (start && !end) {
					sbr.append(s);
					end = me.find();
				}
				// 结束处理
				if (end) {
					// 归零
					start = false;
					end = false;
				}
			}
			// 绑定前缀
			sbr.insert(sbr.indexOf(">"),
					" xmlns:qeweb=\"http://www.qeweb.com/list\"");

			SAXBuilder builder = new SAXBuilder();
			try {
				doc = builder.build(new StringReader(sbr.toString()));
			} catch (JDOMException e) {
				e.printStackTrace();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	final static private String getXPath_Container() {
		StringBuilder sbr = new StringBuilder();
		sbr.append("(/").append(AnalyzeJspUtil.TAG_PAGE).append("/").append(AnalyzeJspUtil.TAG_FORM);
		sbr.append("|");
		sbr.append("/").append(AnalyzeJspUtil.TAG_PAGE).append("/").append(AnalyzeJspUtil.TAG_GROUP).append("/").append(AnalyzeJspUtil.TAG_FORM);
		sbr.append("|");
		sbr.append("/").append(AnalyzeJspUtil.TAG_PAGE).append("/").append(AnalyzeJspUtil.TAG_TABLE);
		sbr.append("|");
		sbr.append("/").append(AnalyzeJspUtil.TAG_PAGE).append("/").append(AnalyzeJspUtil.TAG_GROUP).append("/").append(AnalyzeJspUtil.TAG_TABLE);
		sbr.append("|");
		sbr.append("/").append(AnalyzeJspUtil.TAG_PAGE).append("/").append(AnalyzeJspUtil.TAG_TAB);
		sbr.append(")");
		
		return sbr.toString();
	}
	
	@SuppressWarnings({"unchecked" })
	public static void main(String[] args) {
		String path = "F:/qeweb/030-Construct/qewebNEW/WebContent/WEB-INF/pages/busplatform/approve/admittance/approveNodeSettingList.jsp";
		Document doc = getPageDoc(path);
		Element root = doc.getRootElement();
		try {
			List<Element> selectNodes = XPath.selectNodes(root, getXPath_Container());
			System.out.println(selectNodes.size());
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		
	}

}
