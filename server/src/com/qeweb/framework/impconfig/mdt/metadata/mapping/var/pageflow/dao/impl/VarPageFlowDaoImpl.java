package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.pageflow.dao.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;

import com.qeweb.framework.base.impl.XmlDaoImpl;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.GuidUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.common.util.pathutil.VarPFPathUtil;
import com.qeweb.framework.impconfig.mdt.MDTVarPFPool;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.common.bop.ConfigStatusBOP;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.pageflow.bo.VarPageFlowBO;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.pageflow.bo.VarPageFlowItemBO;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.pageflow.dao.ia.IVarPageFlowDao;

/**
 * 变量-页面流映射dao, 负责读写qeweb-var_pageflow.xml
 */
public class VarPageFlowDaoImpl extends XmlDaoImpl implements IVarPageFlowDao {

	private static final long serialVersionUID = 5807481249366195974L;
	
	@Override
	public List<VarPageFlowBO> getVarPFs(BOTemplate bot) {
		List<Element> relateElements = getRelateElements(getXPath_All());
		if(ContainerUtil.isNull(relateElements))
			return null;
		
		List<VarPageFlowBO> varPageFlowList = new LinkedList<VarPageFlowBO>();
		for (Element element : relateElements) {
			VarPageFlowBO varPF = convertElementToVar(element);
			
			if (bot == null || ContainerUtil.isNull(bot.getBotMap())) {
				varPageFlowList.add(varPF);
				continue;
			}
			
			// 判断查询结果是否符合查询条件
			boolean inQuery = true;
			if (StringUtils.isNotEmpty((String) bot.getBotMap().get("relateName"))) {
				if (!StringUtils.hasSubString(varPF.getRelateName(), bot.getBotMap().get("relateName").toString())) {
					inQuery = false;
				}
			}
			if (inQuery && StringUtils.isNotEmpty((String) bot.getBotMap().get("sourcePage"))) {
				if (!StringUtils.hasSubString(varPF.getSourcePage(), bot.getBotMap().get("sourcePage").toString())) {
					inQuery = false;
				}
			}
			if (inQuery && StringUtils.isNotEmpty((String) bot.getBotMap().get("boId"))) {
				if (!StringUtils.hasSubString(varPF.getBoId(), bot.getBotMap().get("boId").toString())) {
					inQuery = false;
				}
			}
			if (inQuery && StringUtils.isNotEmpty((String) bot.getBotMap().get("btnName"))) {
				if (!StringUtils.hasSubString(varPF.getBtnName(), bot.getBotMap().get("btnName").toString())) {
					inQuery = false;
				}
			}
			if (inQuery && StringUtils.isNotEmpty((String) bot.getBotMap().get("configStatus"))) {
				if(StringUtils.isEqual(bot.getBotMap().get("configStatus").toString(), ConfigStatusBOP.YES))
					inQuery = ContainerUtil.isNotNull(varPF.getPfRelations());
				else 
					inQuery = ContainerUtil.isNull(varPF.getPfRelations());
			}
			
			if (inQuery)
				varPageFlowList.add(varPF);
		}
		
		return varPageFlowList;
	}
	
	@Override
	public Map<String, VarPageFlowBO> getVarPFs() {
		List<Element> relateElements = getRelateElements(getXPath_All());
		if(ContainerUtil.isNull(relateElements))
			return null;
		
		Map<String, VarPageFlowBO> result = new HashMap<String, VarPageFlowBO>();
		for (Element element : relateElements) {
			VarPageFlowBO varPF = convertElementToVar(element);
			result.put(MDTVarPFPool.getKey(varPF.getSourcePage(), varPF.getBoId(), varPF.getBtnName()), varPF);
		}
		
		return result;
	}
	
	@Override
	public VarPageFlowBO getVarPF(long id) {
		List<Element> relateElements = getRelateElements(getXPath_RelateId(id));
		if(ContainerUtil.isNull(relateElements))
			return null;
		
		return convertElementToVar(relateElements.get(0));
	}
	
	/**
	 * 新增变量和页面流的映射关系(relate节点).
	 * @param varPageFlowBO
	 */
	@Override
	public void insert(VarPageFlowBO varPageFlowBO) {
		Element rootElement = getRootElement();
		if(rootElement == null)
			return;
		
		Element relateElement = new Element(NODE_RELATE);
		relateElement.setAttribute(ATTR_ID, GuidUtil.getGUID() + "");
		setRelateEl(varPageFlowBO, relateElement);
		
		rootElement.addContent(0, relateElement);
		saveXML(rootElement);
	}
	
	/**
	 * 新增目标页面信息(变量值如何影响targetPage)
	 * @param varPageFlowBO
	 * @param targetPageInfo
	 */
	@Override
	public void insertTargetPage(VarPageFlowBO varPageFlowBO, VarPageFlowItemBO targetPageInfo) {
		Element rootElement = getRootElement();
		if(rootElement == null)
			return;
		
		List<Element> relateElements = getElmentsByXpath(getXPath_RelateId(varPageFlowBO.getId()), rootElement);
		if(ContainerUtil.isNull(relateElements))
			return;
		
		Element varElement = new Element(NODE_VAR);
		varElement.setAttribute(ATTR_ID, GuidUtil.getGUID() + "");
		varElement.setAttribute(ATTR_RELATENAME, targetPageInfo.getRelateName());
		varElement.setAttribute(ATTR_GROUP, targetPageInfo.getGroup());
		Element targetElement = new Element(NODE_TARGETPAGE);
		targetElement.setAttribute(ATTR_PATH, targetPageInfo.getTargetPage());
		varElement.addContent(0, targetElement);
		relateElements.get(0).addContent(0, varElement);
		
		saveXML(rootElement);
	}
	
	/**
	 * 修改变量和页面流的映射关系(relate节点).
	 */
	@Override
	public void update(VarPageFlowBO varPageFlowBO) {
		Element rootElement = getRootElement();
		if(rootElement == null)
			return;
		
		List<Element> elements = getElmentsByXpath(getXPath_RelateId(varPageFlowBO.getId()), rootElement);
		if(ContainerUtil.isNull(elements))
			return;
		
		Element relateElement = elements.get(0);
		setRelateEl(varPageFlowBO, relateElement);
		saveXML(rootElement);
	}
	
	@Override
	public void delete(List<BusinessComponent> bcList) throws Exception {
		Element rootElement = getRootElement();
		for(BusinessComponent bc : bcList) {
			Element element = getElementByXpath(rootElement, getXPath_RelateId(bc.getId()));
			rootElement.removeContent(element);
		}
		
		saveXML(rootElement);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void delete(VarPageFlowBO varPageFlowBO, List<BusinessComponent> bcList) throws Exception {
		Element rootElement = getRootElement();
		Element relateElement = getElementByXpath(rootElement, getXPath_RelateId(varPageFlowBO.getId()));
		List<Element> varEls = relateElement.getChildren();
		
		Iterator<Element> itr = varEls.iterator();
		while(itr.hasNext()) {
			Element varEl = itr.next();
			for(BusinessComponent bc : bcList) {
				if(StringUtils.isEqual(bc.getId() + "", varEl.getAttributeValue(ATTR_ID))) {
					relateElement.removeContent(varEl);
					itr = varEls.iterator();
				}
			}
		}
		
		saveXML(rootElement);
	}
	
	@Override
	public boolean isRelateNameExists(String relateName) {
		return ContainerUtil.isNotNull(getRelateElements(getXPath_RelateName(relateName)));
	}
	
	/**
	 * 转换element为VarPageFlowBO
	 * @param element
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private VarPageFlowBO convertElementToVar(Element element){
		VarPageFlowBO result = new VarPageFlowBO();
		result.setId(Long.parseLong(element.getAttributeValue(ATTR_ID)));
		result.setSourcePage(element.getAttributeValue(ATTR_SOURCEPAGE));
		result.setBoId(element.getAttributeValue(ATTR_BOID));
		result.setBtnName(element.getAttributeValue(ATTR_BTNNAME));
		result.setRelateName(element.getAttributeValue(ATTR_RELATENAME));
		result.setVars(element.getAttributeValue(ATTR_VARS));
		
		List<Element> varNodeList = element.getChildren();
		if(ContainerUtil.isNull(varNodeList))
			return result;
		
		Set<VarPageFlowItemBO> pfRelations = new HashSet<VarPageFlowItemBO>();
		for(Element varNode : varNodeList) {
			VarPageFlowItemBO pfItem = new VarPageFlowItemBO();
			pfItem.setId(Long.parseLong(varNode.getAttributeValue(ATTR_ID)));
			pfItem.setRelateName(varNode.getAttributeValue(ATTR_RELATENAME));
			pfItem.setGroup(varNode.getAttributeValue(ATTR_GROUP));
			pfItem.setTargetPage(varNode.getChild(NODE_TARGETPAGE).getAttributeValue(ATTR_PATH));
			pfRelations.add(pfItem);
		}
		
		result.setPfRelations(pfRelations);
		
		return result;
	}
	
	/**
	 * 
	 * @param xpath
	 * @return
	 */
	private List<Element> getRelateElements(String xpath) {
		Element rootElement = getRootElement();
		if(rootElement == null)
			return null;
		
		return getElmentsByXpath(xpath, rootElement);
	}
	
	private void setRelateEl(VarPageFlowBO varPageFlowBO, Element relateElement) {
		relateElement.setAttribute(ATTR_RELATENAME, varPageFlowBO.getRelateName());
		relateElement.setAttribute(ATTR_VARS, varPageFlowBO.getVars());
		relateElement.setAttribute(ATTR_SOURCEPAGE, varPageFlowBO.getSourcePage());
		relateElement.setAttribute(ATTR_BOID, varPageFlowBO.getBoId());
		relateElement.setAttribute(ATTR_BTNNAME, varPageFlowBO.getBtnName());
	}
	
	/**
	 * 持久化xml文件
	 * @param rootElement
	 */
	private void saveXML(Element rootElement) {
		Document doc = rootElement.getDocument();
		//修改服务器端文件
		outPutDocToFile(doc, VarPFPathUtil.getClientVarPath());
		//修改工程中的文件
		outPutDocToFile(doc, VarPFPathUtil.getServerVarPath());
	}

	
	/**
	 * 返回qeweb-var_pageflow.xml的root节点
	 * @return
	 */
	private Element getRootElement(){
		return getRootElement(VarPFPathUtil.getClientVarPath(), VarPFPathUtil.getServerVarPath());
	}
	
	/**
	 * 查询条件(根据relateName查询所有relate节点)
	 * @param id
	 * @return
	 */
	private String getXPath_RelateName(String relateName){
		return "/" + ROOT + "/" + NODE_RELATE + "[@" + ATTR_RELATENAME + "='" + relateName + "']";
	}
	
	
	/**
	 * 查询条件(根据ID查询所有relate节点)
	 * @param id
	 * @return
	 */
	private String getXPath_RelateId(long id){
		return "/" + ROOT + "/" + NODE_RELATE + "[@" + ATTR_ID + "='" + id + "']";
	}
	
	/**
	 * 查询条件(查询所有relate节点)
	 * @return
	 */
	private String getXPath_All() {
		return "/" + ROOT + "/" + NODE_RELATE;
	}
}
