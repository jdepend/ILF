package com.qeweb.framework.impconfig.promodule.dao.impl;

import java.util.LinkedList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.base.ia.IXmlDao;
import com.qeweb.framework.base.impl.XmlDaoImpl;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.GuidUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.common.util.pathutil.ProjectDutyPathUtil;
import com.qeweb.framework.impconfig.promodule.bo.ProjectModuleBO;
import com.qeweb.framework.impconfig.promodule.dao.ia.IProModuleDao;

/**
 * 模块管理 dao impl
 */
public class ProModuleDaoImpl extends XmlDaoImpl implements IProModuleDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2371711148196157230L;

	@Override
	public List<ProjectModuleBO> findModules(BOTemplate bot) {
		Element rootElement = getRootElement();
		if(rootElement == null)
			return null;
		
		List<Element> moduleElements = getElmentsByXpath(getXPath_ModuleAll(), rootElement);
		if(ContainerUtil.isNull(moduleElements))
			return null;
		
		List<ProjectModuleBO> modules = new LinkedList<ProjectModuleBO> ();
		for(Element element : moduleElements){
			ProjectModuleBO module = convertElementToModule(element);
			
			if(bot == null || ContainerUtil.isNull(bot.getBotMap())) {
				modules.add(module);
				continue;
			}
			
			//判断查询结果是否符合查询条件
			boolean inQuery = true;
			//模块名称
			if(StringUtils.isNotEmpty((String)bot.getBotMap().get("moduleName"))) {
				if(!StringUtils.hasSubString(module.getModuleName(), bot.getBotMap().get("moduleName").toString())){
					inQuery = false;
				}
			}
			//开发人员
			if(inQuery && StringUtils.isNotEmpty((String)bot.getBotMap().get("developers"))) {
				if(!StringUtils.hasSubString(module.getDevelopers(), bot.getBotMap().get("developers").toString())){
					inQuery = false;
				}
			}
			//模块ID
			if(inQuery && StringUtils.isNotEmpty((String)bot.getBotMap().get("id"))) {
				if(!StringUtils.isEqual(module.getId() + "", bot.getBotMap().get("id").toString())){
					inQuery = false;
				}
			}
			if(inQuery)
				modules.add(module);
		}
		
		return modules;
	}
	
	@Override
	public void delete(List<BusinessComponent> modules) {
		Element rootElement = getRootElement();
		if(rootElement == null)
			return;
		
		for(BusinessComponent module : modules){
			Element element = getElmentsByXpath(getXPath_ModuleId(module.getId()), rootElement).get(0);
			element.setAttribute(IBaseDao.FIELD_DELETEFLAG, "" + IXmlDao.DELETE_SIGNE);
		}
		
		saveXML(rootElement);		
	}

	@Override
	public ProjectModuleBO getProjectModule(long id) throws Exception {
		return convertElementToModule(getElementByXpath(getRootElement(), getXPath_ModuleId(id)));
	}
	
	@Override
	public ProjectModuleBO getProjectModuleByMName(String moduleName) throws Exception {
		return convertElementToModule(getElementByXpath(getRootElement(), getXPath_ModuleName(moduleName)));
	}
	
	@Override
	public void insert(ProjectModuleBO module) {
		Element rootElement = getRootElement();
		if(rootElement == null)
			return;
		
		//创建module节点
		Element moduleElement = new Element(NODE_MODULE);
		moduleElement.setAttribute(ATTR_ID, GuidUtil.getGUID() + "");
		moduleElement.setAttribute(ATTR_NAME, module.getModuleName());
		moduleElement.setAttribute(ATTR_REMARK, module.getRemark());
		moduleElement.setAttribute(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE + "");
		
		//创建developers节点
		Element developerElement = new Element(NODE_DEV);
		developerElement.setAttribute(ATTR_ID, module.getDevelopers());
		moduleElement.addContent(developerElement);
		
		//创建package节点
		Element pakageElement = new Element(NODE_PACKAGE);
		Element srcElement = new Element(NODE_SRC);
		srcElement.setAttribute(ATTR_NAME, module.getSrcPackage());
		Element jspElement = new Element(NODE_JSP);
		jspElement.setAttribute(ATTR_NAME, module.getJspPackage());
		Element hbmElement = new Element(NODE_HBM);
		hbmElement.setAttribute(ATTR_NAME, module.getHbmPackage());
		Element springElement = new Element(NODE_SPRING);
		springElement.setAttribute(ATTR_NAME, module.getSpringPackage());
		Element pageflowElement = new Element(NODE_PAGEFLOW);
		pageflowElement.setAttribute(ATTR_NAME, module.getPageflowPackage());
		Element varElement = new Element(NODE_VAR);
		varElement.setAttribute(ATTR_NAME, module.getVarPackage());
		Element i18nElement = new Element(NODE_I18N);
		i18nElement.setAttribute(ATTR_NAME, module.getI18nPackage());
		pakageElement.addContent(srcElement);
		pakageElement.addContent(jspElement);
		pakageElement.addContent(hbmElement);
		pakageElement.addContent(springElement);
		pakageElement.addContent(pageflowElement);
		pakageElement.addContent(varElement);
		pakageElement.addContent(i18nElement);
		moduleElement.addContent(pakageElement);
		
		rootElement.addContent(0, moduleElement);
		saveXML(rootElement);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void update(ProjectModuleBO module) {
		Element rootElement = getRootElement();
		if(rootElement == null)
			return;
		
		List<Element> elements = getElmentsByXpath(getXPath_ModuleId(module.getId()), rootElement);
		if(ContainerUtil.isNull(elements))
			return;
		
		//修改moduleElement节点
		Element moduleElement = elements.get(0);
		moduleElement.setAttribute(ATTR_NAME, module.getModuleName());
		moduleElement.setAttribute(ATTR_REMARK, module.getRemark());
		
		//修改developers节点
		Element developElement = moduleElement.getChild(NODE_DEV);
		developElement.setAttribute(ATTR_ID, module.getDevelopers());
		
		//修改package节点
		Element packageElement = moduleElement.getChild(NODE_PACKAGE);
		List<Element> childElements = packageElement.getChildren();
		for(Element packElement: childElements) {
			if(StringUtils.isEqual(NODE_SRC, packElement.getName()))
				packElement.setAttribute(ATTR_NAME, module.getSrcPackage());
			else if(StringUtils.isEqual(NODE_JSP, packElement.getName()))
				packElement.setAttribute(ATTR_NAME, module.getJspPackage());
			else if(StringUtils.isEqual(NODE_HBM, packElement.getName()))
				packElement.setAttribute(ATTR_NAME, module.getHbmPackage());
			else if(StringUtils.isEqual(NODE_SPRING, packElement.getName()))
				packElement.setAttribute(ATTR_NAME, module.getSpringPackage());
			else if(StringUtils.isEqual(NODE_PAGEFLOW, packElement.getName()))
				packElement.setAttribute(ATTR_NAME, module.getPageflowPackage());
			else if(StringUtils.isEqual(NODE_VAR, packElement.getName()))
				packElement.setAttribute(ATTR_NAME, module.getVarPackage());
			else if(StringUtils.isEqual(NODE_I18N, packElement.getName()))
				packElement.setAttribute(ATTR_NAME, module.getI18nPackage());
		}
		
		saveXML(rootElement);
	}
	
	@SuppressWarnings("unchecked")
	private ProjectModuleBO convertElementToModule(Element element){
		ProjectModuleBO module = new ProjectModuleBO();
		module.setId(Long.parseLong(element.getAttributeValue(ATTR_ID)));
		module.setModuleName(element.getAttributeValue(ATTR_NAME));
		module.setRemark(element.getAttributeValue(ATTR_REMARK));
		
		Element developerElement = element.getChild(NODE_DEV);
		module.setDevelopers(developerElement.getAttributeValue(ATTR_ID));
		Element packageElement = element.getChild(NODE_PACKAGE);
		
		List<Element> packageChildren = packageElement.getChildren();
		for(Element packElement : packageChildren){
			String packName = packElement.getAttributeValue(ATTR_NAME);
			if(StringUtils.isEqual(packElement.getName(), NODE_SRC))
				module.setSrcPackage(packName);
			else if(StringUtils.isEqual(packElement.getName(), NODE_JSP))
				module.setJspPackage(packName);
			else if(StringUtils.isEqual(packElement.getName(), NODE_HBM))
				module.setHbmPackage(packName);
			else if(StringUtils.isEqual(packElement.getName(), NODE_SPRING))
				module.setSpringPackage(packName);
			else if(StringUtils.isEqual(packElement.getName(), NODE_PAGEFLOW))
				module.setPageflowPackage(packName);
			else if(StringUtils.isEqual(packElement.getName(), NODE_VAR))
				module.setVarPackage(packName);
			else if(StringUtils.isEqual(packElement.getName(), NODE_I18N))
				module.setI18nPackage(packName);
		}
		return module;
	}
	
	/**
	 * 持久化xml文件
	 * @param rootElement
	 */
	private void saveXML(Element rootElement) {
		Document doc = rootElement.getDocument();
		//修改服务器端文件
		outPutDocToFile(doc, ProjectDutyPathUtil.getServerDutyPath());
		//修改工程中的文件
		outPutDocToFile(doc, ProjectDutyPathUtil.getClientDutyPath());
	}
	
	/**
	 * 返回qeweb-impuser.xml的root节点
	 * @return
	 */
	private Element getRootElement(){
		return getRootElement(ProjectDutyPathUtil.getClientDutyPath(), 
				ProjectDutyPathUtil.getServerDutyPath());
	}
	
	/**
	 * 查询条件(查询所有module节点)
	 * @return
	 */
	private String getXPath_ModuleAll() {
		return "/" + ROOT + "/" + NODE_MODULE + "[@" + IBaseDao.FIELD_DELETEFLAG + "='" + IBaseDao.UNDELETE_SIGNE + "']";
	}
	
	/**
	 * 查询条件(根据id查询module节点)
	 * @param id
	 * @return
	 */
	private String getXPath_ModuleId(long id) {
		return "/" + ROOT + "/" + NODE_MODULE + "[@" + ATTR_ID + "='" + id + "']";
	}
	
	/**
	 * 查询条件(根据moduleName查询module节点)
	 * @param moduleName
	 * @return
	 */
	private String getXPath_ModuleName(String moduleName) {
		return "/" + ROOT + "/" + NODE_MODULE + "[@" + ATTR_NAME + "='" + moduleName + "']";
	}
}
