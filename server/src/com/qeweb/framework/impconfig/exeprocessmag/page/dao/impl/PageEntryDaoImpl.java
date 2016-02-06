package com.qeweb.framework.impconfig.exeprocessmag.page.dao.impl;

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
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.impconfig.common.util.pathutil.PageEntryPathUtil;
import com.qeweb.framework.impconfig.exeprocessmag.page.bo.PageEntryBO;
import com.qeweb.framework.impconfig.exeprocessmag.page.bo.PageEntryItemBO;
import com.qeweb.framework.impconfig.exeprocessmag.page.dao.ia.IPageEntryDao;
import com.qeweb.framework.impconfig.mdt.metadata.var.bo.VarBO;
import com.qeweb.framework.impconfig.mdt.metadata.var.dao.ia.IVarDao;

/**
 * 变量执行过程管理-页面入口. 操作qeweb-page-entry.xml中的内容
 * 
 */
public class PageEntryDaoImpl extends XmlDaoImpl implements IPageEntryDao {

	private static final long serialVersionUID = 8138421050027411932L;
	private IVarDao varDao;
	
	public Map<String, PageEntryBO> getPageEntrys() {
		List<PageEntryBO> pageEntryBOList = getPageEntrys(null);
		if(ContainerUtil.isNull(pageEntryBOList))
			return null;
		
		Map<String, PageEntryBO> result = new HashMap<String, PageEntryBO>();
		for (PageEntryBO pageEntryBO : pageEntryBOList) {
			result.put(pageEntryBO.getPageURL(), pageEntryBO);
		}
		
		return result;
	}
	
	@Override
	public List<PageEntryBO> getPageEntrys(BOTemplate bot) {
		Element rootElement = getRootElement();
		if(rootElement == null)
			return null;
		
		List<Element> entryElements = getElmentsByXpath(getXPath_AllEntry(), rootElement);
		if(ContainerUtil.isNull(entryElements))
			return null;
		
		List<PageEntryBO> result = new LinkedList<PageEntryBO>();
		for (Element element : entryElements) {
			PageEntryBO pageEntryBO = convertElementToBO(element);
			if (bot == null || ContainerUtil.isNull(bot.getBotMap())) {
				result.add(pageEntryBO);
				continue;
			}
			
			//判断查询结果是否符合查询条件
			if (StringUtils.isNotEmpty((String) bot.getBotMap().get("id"))) {
				//根据ID查询时仅能返回一个结果
				if (pageEntryBO.getId() == StringUtils.convertLong(bot.getBotMap().get("id").toString())) {
					result.add(pageEntryBO);
					return result;
				}
				else {
					continue;
				}
			}
			
			//判断查询结果是否符合查询条件
			boolean inQuery = true;
			if (StringUtils.isNotEmpty((String) bot.getBotMap().get("pageURL"))) {
				if (!StringUtils.hasSubString(pageEntryBO.getPageURL(), bot.getBotMap().get("pageURL").toString())) {
					inQuery = false;
				}
			}
			
			if (inQuery)
				result.add(pageEntryBO);
		}
		
		return result;
	}
	
	@Override
	public void insert(PageEntryBO pageEntryBO) {
		Element rootElement = getRootElement();
		if(rootElement == null)
			return;
		
		save(pageEntryBO, rootElement, new Element(NODE_ENTRY));
	}

	@Override
	public void update(PageEntryBO pageEntryBO) throws BOException {
		Element rootElement = getRootElement();
		if(rootElement == null)
			return;
		
		List<Element> entryEls = getElmentsByXpath(getXPath_EntryId(pageEntryBO.getId()), rootElement);
		if(ContainerUtil.isNull(entryEls))
			return;
		
		rootElement.removeContent(entryEls.get(0));
		save(pageEntryBO, rootElement, new Element(NODE_ENTRY));
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

	@Override
	public PageEntryBO findPageEntryBO(long id) throws BOException {
		return null;
	}
	
	/**
	 * 保存entry节点
	 * @param pageEntryBO
	 * @param rootElement
	 * @param entryElement
	 */
	private void save(PageEntryBO pageEntryBO, Element rootElement, Element entryElement) {
		if(pageEntryBO.getId() == 0L)
			entryElement.setAttribute(ATTR_ID, GuidUtil.getGUID() + "");
		else
			entryElement.setAttribute(ATTR_ID, pageEntryBO.getId() + "");
		entryElement.setAttribute(ATTR_PAGEURL, pageEntryBO.getPageURL());
		
		List<Element> pageEntryItemEls = new LinkedList<Element>();
		long id = GuidUtil.getGUID();
		for(PageEntryItemBO pagEntryItem : pageEntryBO.getEntryItems()) {
			Element entryItemElement = new Element(NODE_STRATETY);
			entryItemElement.setAttribute(ATTR_ID, id++ + "");
			entryItemElement.setAttribute(ATTR_TYPE, pagEntryItem.getVarLoadStrategy() + "");
			entryItemElement.setAttribute(ATTR_VAR, pagEntryItem.getVarBO().getName());
			if(StringUtils.isNotEmpty(pagEntryItem.getDefValue()))
				entryItemElement.setAttribute(ATTR_DEFVALUE, pagEntryItem.getDefValue());
			
			pageEntryItemEls.add(entryItemElement);
		}
		entryElement.addContent(pageEntryItemEls);
		rootElement.addContent(entryElement);
		
		saveXML(rootElement);
	}
	
	@SuppressWarnings("unchecked")
	private PageEntryBO convertElementToBO(Element element) {
		PageEntryBO pageEntryBO = new PageEntryBO();
		pageEntryBO.setId(StringUtils.convertLong(element.getAttributeValue(ATTR_ID)));
		pageEntryBO.setPageURL(element.getAttributeValue(ATTR_PAGEURL));
		
		List<Element> pageEntryItemEls = element.getChildren();
		if(ContainerUtil.isNull(pageEntryItemEls))
			return pageEntryBO;
		
		String varNames = "";
		try {
			List<PageEntryItemBO> entryItems = new LinkedList<PageEntryItemBO>();
			for (Element el : pageEntryItemEls) {
				PageEntryItemBO pageEntryItemBO = new PageEntryItemBO();
				pageEntryItemBO.setId(StringUtils.convertLong(el.getAttributeValue(ATTR_ID)));
				pageEntryItemBO.setVarLoadStrategy(StringUtils.convertToInt(el.getAttributeValue(ATTR_TYPE)));
				pageEntryItemBO.setDefValue(el.getAttributeValue(ATTR_DEFVALUE));

				List<VarBO> varList = getVarDao().getVarByName(el.getAttributeValue(ATTR_VAR));
				if(ContainerUtil.isNotNull(varList)) {
					pageEntryItemBO.setVarBO(varList.get(0));
					varNames += (varList.get(0).getName() + ",");
				}
				entryItems.add(pageEntryItemBO);
			}
			
			pageEntryBO.setVarNames(StringUtils.removeEnd(varNames));
			pageEntryBO.setEntryItems(entryItems);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return pageEntryBO;
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
	 * 返回qeweb-page-entry.xml的root节点
	 * @return
	 */
	private Element getRootElement(){
		return getRootElement(PageEntryPathUtil.getClientPath(), PageEntryPathUtil.getServerPath());
	}
	
	/**
	 * 持久化xml文件
	 * @param rootElement
	 */
	private void saveXML(Element rootElement) {
		Document doc = rootElement.getDocument();
		//修改服务器端文件
		outPutDocToFile(doc, PageEntryPathUtil.getClientPath());
		//修改工程中的文件
		outPutDocToFile(doc, PageEntryPathUtil.getServerPath());
	}
	
	public IVarDao getVarDao() {
		if(varDao == null)
			varDao = (IVarDao)SpringConstant.getCTX().getBean("varDao"); 
		return varDao;
	}
}
