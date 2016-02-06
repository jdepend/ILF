package com.qeweb.framework.impconfig.exeprocessmag.pageflow.dao.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;

import com.qeweb.framework.base.impl.XmlDaoImpl;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.GuidUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.common.util.pathutil.PageflowEntryPathUtil;
import com.qeweb.framework.impconfig.exeprocessmag.pageflow.bo.PFEntryBO;
import com.qeweb.framework.impconfig.exeprocessmag.pageflow.bo.PFEntryItemBO;
import com.qeweb.framework.impconfig.exeprocessmag.pageflow.dao.ia.IPFEntryDao;
import com.qeweb.framework.impconfig.mdt.MDTVarPFEntryPool;
import com.qeweb.framework.impconfig.mdt.metadata.var.bo.VarBO;
import com.qeweb.framework.impconfig.mdt.metadata.var.dao.ia.IVarDao;

/**
 * 变量执行过程管理-页面流入口.操作qeweb-pageflow-entry.xml中的内容
 */
public class PFEntryDaoImpl extends XmlDaoImpl implements IPFEntryDao {

	private static final long serialVersionUID = 4850316238168468157L;
	
	private IVarDao varDao;

	@Override
	public Map<String, PFEntryBO> getPFEntrys() {
		List<PFEntryBO> pfEntryBOList = getPFEntrys(null);
		if(ContainerUtil.isNull(pfEntryBOList))
			return null;
		
		Map<String, PFEntryBO> result = new HashMap<String, PFEntryBO>();
		for (PFEntryBO pfEntryBO : pfEntryBOList) {
			String key = MDTVarPFEntryPool.getKey(pfEntryBO.getSourcePage(), pfEntryBO.getBoId(), pfEntryBO.getBtnName());
			result.put(key, pfEntryBO);
		}
		
		return result;
	}
	
	@Override
	public List<PFEntryBO> getPFEntrys(BOTemplate bot) {
		Element rootElement = getRootElement();
		if(rootElement == null)
			return null;
		
		List<Element> entryElements = getElmentsByXpath(getXPath_AllEntry(), rootElement);
		if(ContainerUtil.isNull(entryElements))
			return null;
		
		List<PFEntryBO> result = new LinkedList<PFEntryBO>();
		
		//根据其它条件查询
		for (Element element : entryElements) {
			PFEntryBO pfEntryBO = convertElementToBO(element);
			if (bot == null || ContainerUtil.isNull(bot.getBotMap())) {
				result.add(pfEntryBO);
				continue;
			}
				
			//判断查询结果是否符合查询条件
			if (StringUtils.isNotEmpty((String) bot.getBotMap().get("id"))) {
				//根据ID查询时仅能返回一个结果
				if (pfEntryBO.getId() == StringUtils.convertLong(bot.getBotMap().get("id").toString())) {
					result.add(pfEntryBO);
					return result;
				}
				else {
					continue;
				}
			}
			
			boolean inQuery = true;
			if (StringUtils.isNotEmpty((String) bot.getBotMap().get("sourcePage"))) {
				if (!StringUtils.hasSubString(pfEntryBO.getSourcePage(), bot.getBotMap().get("sourcePage").toString())) {
					inQuery = false;
				}
			}
			if (inQuery && StringUtils.isNotEmpty((String) bot.getBotMap().get("boId"))) {
				if (!StringUtils.hasSubString(pfEntryBO.getBoId(), bot.getBotMap().get("boId").toString())) {
					inQuery = false;
				}
			}
			if (inQuery && StringUtils.isNotEmpty((String) bot.getBotMap().get("btnName"))) {
				if (!StringUtils.hasSubString(pfEntryBO.getBtnName(), bot.getBotMap().get("btnName").toString())) {
					inQuery = false;
				}
			}
			if (inQuery && StringUtils.isNotEmpty((String) bot.getBotMap().get("targetPage"))) {
				if (!StringUtils.hasSubString(pfEntryBO.getTargetPage(), bot.getBotMap().get("targetPage").toString())) {
					inQuery = false;
				}
			}
		
			if (inQuery)
				result.add(pfEntryBO);
		}
		
		return result;
	}
	
	@Override
	public void insert(PFEntryBO pfEntryBO) {
		Element rootElement = getRootElement();
		if(rootElement == null)
			return;
		
		save(pfEntryBO, rootElement, new Element(NODE_ENTRY));
	}

	private void save(PFEntryBO pfEntryBO, Element rootElement, Element entryElement) {
		
		if(pfEntryBO.getId() == 0L)
			entryElement.setAttribute(ATTR_ID, GuidUtil.getGUID() + "");
		else
			entryElement.setAttribute(ATTR_ID, pfEntryBO.getId() + "");
		entryElement.setAttribute(ATTR_SOURCEPAGE, pfEntryBO.getSourcePage());
		entryElement.setAttribute(ATTR_BOID, pfEntryBO.getBoId());
		entryElement.setAttribute(ATTR_BTNNAME, pfEntryBO.getBtnName());
		
		List<Element> pfEntryItemEls = new LinkedList<Element>();
		long id = GuidUtil.getGUID();
		for(PFEntryItemBO pfEntryItem : pfEntryBO.getEntryItems()) {
			Element entryItemElement = new Element(NODE_STRATETY);
			entryItemElement.setAttribute(ATTR_ID, id++ + "");
			entryItemElement.setAttribute(ATTR_TYPE, pfEntryItem.getVarLoadStrategy() + "");
			entryItemElement.setAttribute(ATTR_VAR, pfEntryItem.getVarBO().getName());
			entryItemElement.setAttribute(ATTR_DEFVALUE, pfEntryItem.getDefValue());
			
			pfEntryItemEls.add(entryItemElement);
		}
		entryElement.addContent(pfEntryItemEls);
		rootElement.addContent(entryElement);
		
		saveXML(rootElement);
	}
	
	@Override
	public void update(PFEntryBO pfEntryBO) {
		Element rootElement = getRootElement();
		if(rootElement == null)
			return;
		
		List<Element> entryEls = getElmentsByXpath(getXPath_EntryId(pfEntryBO.getId()), rootElement);
		if(ContainerUtil.isNull(entryEls))
			return;
		
		Element entryEl = entryEls.get(0);
		rootElement.removeContent(entryEl);

		save(pfEntryBO, rootElement, new Element(NODE_ENTRY));
	}
	
	@Override
	public void delete(List<BusinessComponent> bcList) {
		Element rootElement = getRootElement();
		if(rootElement == null)
			return;
		
		for(BusinessComponent bc : bcList){
			Element element = getElmentsByXpath(getXPath_EntryId(bc.getId()), rootElement).get(0);
			rootElement.removeContent(element);
		}
		
		saveXML(rootElement);
	}
	
	@SuppressWarnings("unchecked")
	private PFEntryBO convertElementToBO(Element element) {
		PFEntryBO pfEntryBO = new PFEntryBO();
		pfEntryBO.setId(StringUtils.convertLong(element.getAttributeValue(ATTR_ID)));
		pfEntryBO.setSourcePage(element.getAttributeValue(ATTR_SOURCEPAGE));
		pfEntryBO.setBoId(element.getAttributeValue(ATTR_BOID));
		pfEntryBO.setBtnName(element.getAttributeValue(ATTR_BTNNAME));
		pfEntryBO.setTargetPage(element.getAttributeValue(ATTR_TARGETPAGE));
		
		List<Element> pfEntryItemEls = element.getChildren();
		if(ContainerUtil.isNull(pfEntryItemEls))
			return pfEntryBO;
		
		String varNames = "";
		try {
			List<PFEntryItemBO> entryItems = new LinkedList<PFEntryItemBO>();
			for (Element el : pfEntryItemEls) {
				PFEntryItemBO pfEntryItemBO = new PFEntryItemBO();
				pfEntryItemBO.setId(StringUtils.convertLong(el.getAttributeValue(ATTR_ID)));
				pfEntryItemBO.setVarLoadStrategy(StringUtils.convertToInt(el.getAttributeValue(ATTR_TYPE)));
				pfEntryItemBO.setDefValue(el.getAttributeValue(ATTR_DEFVALUE));

				List<VarBO> varList = getVarDao().getVarByName(el.getAttributeValue(ATTR_VAR));
				if(ContainerUtil.isNotNull(varList)) {
					pfEntryItemBO.setVarBO(varList.get(0));
					varNames += (varList.get(0).getName() + ",");
				}
				entryItems.add(pfEntryItemBO);
			}
			
			pfEntryBO.setVarNames(StringUtils.removeEnd(varNames));
			pfEntryBO.setEntryItems(entryItems);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return pfEntryBO;
	}
	
	/**
	 * 查询条件(查询所有entry节点)
	 * @return
	 */
	private String getXPath_AllEntry() {
		return "/" + ROOT + "/" + NODE_ENTRY;
	}

	/**
	 * 查询条件(根据ID查询entry节点)
	 * @return
	 */
	private String getXPath_EntryId(long id){
		return "/" + ROOT + "/" + NODE_ENTRY + "[@" + ATTR_ID + "='" + id + "']";
	}
	
	/**
	 * 返回qeweb-pageflow-entry.xml的root节点
	 * @return
	 */
	private Element getRootElement(){
		return getRootElement(PageflowEntryPathUtil.getClientPath(), PageflowEntryPathUtil.getServerPath());
	}
	
	/**
	 * 持久化xml文件
	 * @param rootElement
	 */
	private void saveXML(Element rootElement) {
		Document doc = rootElement.getDocument();
		//修改服务器端文件
		outPutDocToFile(doc, PageflowEntryPathUtil.getClientPath());
		//修改工程中的文件
		outPutDocToFile(doc, PageflowEntryPathUtil.getServerPath());
	}
	
	public IVarDao getVarDao() {
		if(varDao == null)
			varDao = (IVarDao)SpringConstant.getCTX().getBean("varDao"); 
		return varDao;
	}
}
