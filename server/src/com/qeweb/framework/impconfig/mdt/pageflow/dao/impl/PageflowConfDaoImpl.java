package com.qeweb.framework.impconfig.mdt.pageflow.dao.impl;

import java.util.LinkedList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.qeweb.framework.base.impl.XmlDaoImpl;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.impconfig.mdt.pageflow.bo.PageflowConfigBO;
import com.qeweb.framework.impconfig.mdt.pageflow.bo.PageflowFileBO;
import com.qeweb.framework.impconfig.mdt.pageflow.dao.ia.IPageflowConfDao;

/**
 * 页面流配置相关查询/持久化 dao Impl
 */
public class PageflowConfDaoImpl extends XmlDaoImpl implements IPageflowConfDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1637209224959023000L;

	@Override
	public List<PageflowConfigBO> findPFItems(BOTemplate bot, List<PageflowFileBO> pageflowFiles) {
		List<PageflowConfigBO> pfConfList = new LinkedList<PageflowConfigBO>();
		
		if(ContainerUtil.isNull(pageflowFiles))
			return pfConfList;
		
		long id = 1;
		//解析页面流文件
		for(PageflowFileBO pfFile : pageflowFiles) {
			Element rootElement = getRootElement(pfFile.getFilePath(), null);
			if(rootElement == null)
				continue;
			
			List<Element> pfElements = getElmentsByXpath(getXPath_All(), rootElement);
			for(Element element : pfElements){
				PageflowConfigBO pfConf = convertElementToPfConf(element, pfFile.getModuleId(), id++);
				if(bot == null || ContainerUtil.isNull(bot.getBotMap())) {
					pfConf.setFileName(pfFile.getFileName());
					pfConf.setFilePath(pfFile.getFilePath());
					pfConfList.add(pfConf);
					continue;
				}
				
				// 判断查询结果是否符合查询条件
				boolean inQuery = true;
				if (StringUtils.isNotEmpty((String) bot.getBotMap().get("boId"))) {
					if (!StringUtils.hasSubString(pfConf.getBoId(), bot.getBotMap().get("boId").toString()))
						inQuery = false;
				}
				if (inQuery && StringUtils.isNotEmpty((String) bot.getBotMap().get("fileName"))) {
					if (!StringUtils.hasSubString(bot.getBotMap().get("fileName").toString(), pfFile.getFileName()))
						inQuery = false;
				}
				if (inQuery && StringUtils.isNotEmpty((String) bot.getBotMap().get("btnName"))) {
					if (!StringUtils.hasSubString(pfConf.getBtnName(), bot.getBotMap().get("btnName").toString()))
						inQuery = false;
				}
				if (inQuery && StringUtils.isNotEmpty((String) bot.getBotMap().get("sourcePage"))) {
					if (!StringUtils.hasSubString(pfConf.getSourcePage(), bot.getBotMap().get("sourcePage").toString()))
						inQuery = false;
				}
				if (inQuery && StringUtils.isNotEmpty((String) bot.getBotMap().get("targetPage"))) {
					if (!StringUtils.hasSubString(pfConf.getTargetPage(), bot.getBotMap().get("targetPage").toString()))
						inQuery = false;
				}
				if (inQuery && StringUtils.isNotEmpty((String) bot.getBotMap().get("msgFlag"))) {
					if (StringUtils.isNotEqual(pfConf.getMsgFlag(), bot.getBotMap().get("msgFlag").toString()))
						inQuery = false;
				}
				if (inQuery && StringUtils.isNotEmpty((String) bot.getBotMap().get("isPopup"))) {
					if (StringUtils.isNotEqual(pfConf.getIsPopup(), bot.getBotMap().get("isPopup").toString()))
						inQuery = false;
				}
				if (inQuery && StringUtils.isNotEmpty((String) bot.getBotMap().get("bindBop"))) {
					if (!StringUtils.hasSubString(pfConf.getBtnName(), bot.getBotMap().get("bindBop").toString()))
						inQuery = false;
				}
				if (inQuery && StringUtils.isNotEmpty((String) bot.getBotMap().get("targetVC"))) {
					if (!StringUtils.hasSubString(pfConf.getBtnName(), bot.getBotMap().get("targetVC").toString()))
						inQuery = false;
				}
				
				if (inQuery) {
					pfConf.setFileName(pfFile.getFileName());
					pfConf.setFilePath(pfFile.getFilePath());
					pfConfList.add(pfConf);
				}
			}
		}
		
		return pfConfList;
	}
	
	@Override
	public boolean isExists(PageflowConfigBO pageFlowConfBO) throws Exception {
		if(StringUtils.isEmpty(pageFlowConfBO.getFilePath())) 
			throw new BOException("页面流文件路径不能为空！");
		
		Element rootElement = getRootElement(pageFlowConfBO.getFilePath(), null);
		if(rootElement == null)
			throw new BOException("获取页面流根节点失败！对应文件：" + pageFlowConfBO.getFilePath());
		
		String xpath = getXPath_singleNode(
				pageFlowConfBO.getBoId(), pageFlowConfBO.getBtnName(), 
				pageFlowConfBO.getBindBop(), pageFlowConfBO.getSourcePage());
		return getElementByXpath(rootElement, xpath) != null;
	}
	
	@Override
	public void insert(PageflowConfigBO pageFlowConfBO) throws Exception {
		if(StringUtils.isEmpty(pageFlowConfBO.getFilePath())) 
			throw new BOException("页面流文件路径不能为空！");
		
		Element rootElement = getRootElement(pageFlowConfBO.getFilePath(), null);
		if(rootElement == null)
			throw new BOException("获取页面流根节点失败！对应文件：" + pageFlowConfBO.getFilePath());
		
		Element element = new Element(NODE_PAGEFLOW);
		initElement(pageFlowConfBO, element);
		
		rootElement.addContent(0, element);
		saveXML(rootElement, pageFlowConfBO.getFilePath());
	}
	
	@Override
	public void update(PageflowConfigBO pageFlowConfBO) throws Exception {
		Element rootElement = getRootElement(pageFlowConfBO.getFilePath(), null);
		if(rootElement == null)
			return;
		
		String xpath = getXPath_singleNode(
				pageFlowConfBO.getBoId_old(), pageFlowConfBO.getBtnName_old(), 
				pageFlowConfBO.getBindBop_old(), pageFlowConfBO.getSourcePage_old());
		List<Element> elements = getElmentsByXpath(xpath, rootElement);
		if(ContainerUtil.isNull(elements))
			throw new BOException("原页面流记录不存在！");
		
		initElement(pageFlowConfBO, elements.get(0));
		saveXML(rootElement, pageFlowConfBO.getFilePath());
	}
	
	@Override
	public void delete(List<PageflowConfigBO> pageFlowConfList) throws Exception {
		if(ContainerUtil.isNull(pageFlowConfList))
			return;
		
		for(PageflowConfigBO pageFlowConfBO : pageFlowConfList) {
			Element rootElement = getRootElement(pageFlowConfBO.getFilePath(), null);
			if(rootElement == null)
				throw new BOException("获取页面流根节点失败！对应文件：" + pageFlowConfBO.getFilePath());
			
			String xpath = getXPath_singleNode(
					pageFlowConfBO.getBoId(), pageFlowConfBO.getBtnName(), 
					pageFlowConfBO.getBindBop(), pageFlowConfBO.getSourcePage());
			List<Element> elements = getElmentsByXpath(xpath, rootElement);
			if(ContainerUtil.isNull(elements)) {
				continue;
			}
			else {
				rootElement.removeContent(elements.get(0));
				saveXML(rootElement, pageFlowConfBO.getFilePath());
			}
		}
	}
	
	/**
	 * 转换element为PageflowConfigBO对象
	 * @param element
	 * @param moduleId
	 * @param id
	 * @return
	 */
	private PageflowConfigBO convertElementToPfConf(Element element, String moduleId, long id){
		PageflowConfigBO pfConf = new PageflowConfigBO();

		pfConf.setId(id);
		pfConf.setModuleId(moduleId);
		pfConf.setBoId(element.getAttributeValue(ATTR_BOID));
		pfConf.setBtnName(element.getAttributeValue(ATTR_BTNNAME));
		pfConf.setSourcePage(element.getAttributeValue(ATTR_SOURCEPAGE));
		pfConf.setTargetPage(element.getAttributeValue(ATTR_TARGETPAGE));
		pfConf.setMsgFlag(element.getAttributeValue(ATTR_MSGFLAG));
		pfConf.setIsPopup(element.getAttributeValue(ATTR_ISPOPUP));
		pfConf.setDialogHeight(element.getAttributeValue(ATTR_DIALOGHEIGHT));
		pfConf.setDialogWidth(element.getAttributeValue(ATTR_DIALOGWIDTH));
		pfConf.setDialogTitle(element.getAttributeValue(ATTR_DIALOGTITLE));
		pfConf.setClosePage(element.getAttributeValue(ATTR_CLOSEPAGE));
		pfConf.setRemark(element.getAttributeValue(ATTR_REMARK));
		pfConf.setBindBop(element.getAttributeValue(ATTR_BINDBOP));
		pfConf.setTargetVC(element.getAttributeValue(ATTR_TARGETVC));
		
		return pfConf;
	}
	
	/**
	 * 通过pageFlowConfBO初始化element
	 * @param pageFlowConfBO
	 * @param element
	 */
	private void initElement(PageflowConfigBO pageFlowConfBO, Element element) {
		element.setAttribute(ATTR_BOID, pageFlowConfBO.getBoId());
		
		if(StringUtils.isNotEmpty(pageFlowConfBO.getSourcePage()))
			element.setAttribute(ATTR_SOURCEPAGE, pageFlowConfBO.getSourcePage());
		else 
			element.setAttribute(ATTR_SOURCEPAGE, "");
		
		if(StringUtils.isNotEmpty(pageFlowConfBO.getTargetPage()))
			element.setAttribute(ATTR_TARGETPAGE, pageFlowConfBO.getTargetPage());
		else 
			element.setAttribute(ATTR_TARGETPAGE, "");
		
		if(StringUtils.isNotEmpty(pageFlowConfBO.getBindBop()))
			element.setAttribute(ATTR_BINDBOP, pageFlowConfBO.getBindBop());
		else
			element.removeAttribute(ATTR_BINDBOP);
		
		if(StringUtils.isNotEmpty(pageFlowConfBO.getBtnName()))
			element.setAttribute(ATTR_BTNNAME, pageFlowConfBO.getBtnName());
		else
			element.removeAttribute(ATTR_BTNNAME);
		
		if(StringUtils.isNotEmpty(pageFlowConfBO.getTargetVC()))
			element.setAttribute(ATTR_TARGETVC, pageFlowConfBO.getTargetVC());
		else
			element.removeAttribute(ATTR_TARGETVC);
		
		if(StringUtils.isNotEmpty(pageFlowConfBO.getMsgFlag()))
			element.setAttribute(ATTR_MSGFLAG, pageFlowConfBO.getMsgFlag());
		else
			element.removeAttribute(ATTR_MSGFLAG);
		
		if(StringUtils.isNotEmpty(pageFlowConfBO.getIsPopup()))
			element.setAttribute(ATTR_ISPOPUP, pageFlowConfBO.getIsPopup());
		else
			element.removeAttribute(ATTR_ISPOPUP);
		
		if(StringUtils.isNotEmpty(pageFlowConfBO.getDialogWidth()))
			element.setAttribute(ATTR_DIALOGWIDTH, pageFlowConfBO.getDialogWidth());
		else
			element.removeAttribute(ATTR_DIALOGWIDTH);
		
		if(StringUtils.isNotEmpty(pageFlowConfBO.getDialogHeight()))
			element.setAttribute(ATTR_DIALOGHEIGHT, pageFlowConfBO.getDialogHeight());
		else
			element.removeAttribute(ATTR_DIALOGHEIGHT);
		
		if(StringUtils.isNotEmpty(pageFlowConfBO.getDialogTitle()))
			element.setAttribute(ATTR_DIALOGTITLE, pageFlowConfBO.getDialogTitle());
		else
			element.removeAttribute(ATTR_DIALOGTITLE);
		
		if(StringUtils.isNotEmpty(pageFlowConfBO.getClosePage()))
			element.setAttribute(ATTR_CLOSEPAGE, pageFlowConfBO.getClosePage());
		else
			element.removeAttribute(ATTR_CLOSEPAGE);
		
		if(StringUtils.isNotEmpty(pageFlowConfBO.getRemark()))
			element.setAttribute(ATTR_REMARK, pageFlowConfBO.getRemark());
		else
			element.removeAttribute(ATTR_REMARK);
	}
	
	/**
	 * 持久化xml文件
	 * @param rootElement
	 * @param fileName
	 */
	private void saveXML(Element rootElement, String fileName) {
		Document doc = rootElement.getDocument();
		//修改工程中的文件
		outPutDocToFile(doc, fileName);
	}
	
	/**
	 * 查询条件(查询所有pageflow节点)
	 * @return
	 */
	private String getXPath_All() {
		return "/" + ROOT + "/" + NODE_PAGEFLOW;
	}
	
	/**
	 * 查询条件,根据boId,btnName,sourcePage查询pageFlow节点.boId,btnName,sourcePage可确定唯一的页面流记录.
	 * @param boId
	 * @param btnName
	 * @param sourcePage
	 * @return
	 */
	private String getXPath_singleNode(String boId, String btnName, String bindBop, String sourcePage) {
		StringBuilder sbr = new StringBuilder();
		sbr.append("/").append(ROOT)
			.append("/").append(NODE_PAGEFLOW)
			.append("[@").append(ATTR_BOID).append("='").append(boId).append("']")
			.append("[@").append(ATTR_SOURCEPAGE).append("='").append(sourcePage == null ? "" : sourcePage).append("']");
		if(StringUtils.isNotEmpty(btnName))
			sbr.append("[@").append(ATTR_BTNNAME).append("='").append(btnName).append("']");
		else if(StringUtils.isNotEmpty(bindBop))
			sbr.append("[@").append(ATTR_BINDBOP).append("='").append(bindBop).append("']");
		
		return  sbr.toString();
	}
}
