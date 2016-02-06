package com.qeweb.framework.impconfig.mdt.pageflow.dao.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jdom.Document;
import org.jdom.Element;

import com.qeweb.framework.base.impl.XmlDaoImpl;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.GuidUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.StatusBOP;
import com.qeweb.framework.impconfig.common.util.pathutil.PFMappingPathUtil;
import com.qeweb.framework.impconfig.mdt.pageflow.bo.PageflowMappingBO;
import com.qeweb.framework.impconfig.mdt.pageflow.bo.mapping.MappingConfBO;
import com.qeweb.framework.impconfig.mdt.pageflow.dao.ia.IPageflowMappingDao;

/**
 * 页面流-节点映射文件管理 dao impl
 * 
 */
public class PageflowMappingDaoImpl extends XmlDaoImpl implements IPageflowMappingDao {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9130168058418028715L;

	/* 映射文件的格式: 
	 * <qeweb-pageflow-var>
		<pageflow id='1' name='订单查看页面' boId='bp_PurchaseOrderBO' sourcePage='/WEB-INF/pages/busplatform/purchase/buyer/purchaseOrderList.jsp'
				targetPage='/WEB-INF/pages/busplatform/purchase/buyer/purchaseOrderView.jsp' btnName='view' 
				var='1,2,3'>
			<target id='2' page='........targetpage1.jsp'>
				<group>
					<var id='1'>201</var>
					<var id='2'>202</var>
					<user field='usercode'>admin</user>
				</group>
				<group>
					<var id='1'>101</var>
					<var id='2'>102</var>
					<user field='usercode'>admin</user>
				</group>
			</target>
			<target id='3' page='........targetpage2.jsp'>
				<group>
					<var id='3'>201</var>
					<var id='2'>203</var>
					<user field='usercode'>vendor</user>
				</group>
			</target>
		</pageflow>
	</qeweb-pageflow-var>
	 */
	public List<PageflowMappingBO> findPFVList(BOTemplate bot) throws Exception {
		List<PageflowMappingBO> pfVarList = new LinkedList<PageflowMappingBO>();
		
		Element rootElement = getRootElement();
		List<Element> pfElements = getElmentsByXpath(getXPath_All(), rootElement);
		for(Element element : pfElements){
			PageflowMappingBO pfVarBO = convertElementToPfVarBO(element);
			
			if(bot == null || ContainerUtil.isNull(bot.getBotMap())) {
				pfVarList.add(pfVarBO);
				continue;
			}
			
			// 判断查询结果是否符合查询条件
			boolean inQuery = true;
			if (StringUtils.isNotEmpty((String) bot.getBotMap().get("id"))) {
				if (pfVarBO.getId() == StringUtils.convertLong(bot.getBotMap().get("id").toString())) {
					pfVarList.add(pfVarBO);
					break;
				}
			}
			if (inQuery && StringUtils.isNotEmpty((String) bot.getBotMap().get("nodeName"))) {
				if (!StringUtils.hasSubString(pfVarBO.getNodeName(), bot.getBotMap().get("nodeName").toString()))
					inQuery = false;
			}
			if (inQuery && StringUtils.isNotEmpty((String) bot.getBotMap().get("moduleId"))) {
				if (StringUtils.isNotEqual(pfVarBO.getModuleId(), bot.getBotMap().get("moduleId").toString()))
					inQuery = false;
			}
			if (inQuery && StringUtils.isEqual((String) bot.getBotMap().get("isConfig"), StatusBOP.YES)) {
				if (pfVarBO.getIsConfig() != StringUtils.convertToInt(StatusBOP.YES))
					inQuery = false;
			}
			if (inQuery && StringUtils.isEqual((String) bot.getBotMap().get("isConfig"), StatusBOP.NO)) {
				if (pfVarBO.getIsConfig() != StringUtils.convertToInt(StatusBOP.NO))
					inQuery = false;
			}
				
			if (inQuery) {
				pfVarList.add(pfVarBO);
			}
		}
		
		return pfVarList;
	}
	
	@Override
	public PageflowMappingBO getPFV(long id) throws Exception {
		Element element = getElementByXpath(getRootElement(), getXPath_Id(id));
		return convertElementToPfVarBO(element);
	}
	
	@Override
	public void insertPFV(PageflowMappingBO pfMappingBO) throws Exception {
		Element rootElement = getRootElement();
		Element pfElement = new Element(NODE_PAGEFLOW);
		pfElement.setAttribute(ATTR_ID, GuidUtil.getGUID() + "");
		setPFVElement(pfMappingBO, pfElement);
		
		rootElement.addContent(0, pfElement);
		saveXML(rootElement);
	}

	@SuppressWarnings("unchecked")
    @Override
	public void updatePFV(PageflowMappingBO pfMappingBO) throws Exception {
		Element rootElement = getRootElement();
		List<Element> elements = getElmentsByXpath(getXPath_Id(pfMappingBO.getId()), rootElement);
		if(ContainerUtil.isNull(elements))
			return;
		
		Element pfElement = elements.get(0);
		setPFVElement(pfMappingBO, pfElement);
		
		//如主节点的变量已修改，则vars节点中的变量也需要修改
        String[] vars = StringUtils.split(pfMappingBO.getVars(), ",");
        for(Element item : (List<Element>) pfElement.getChildren(NODE_VARS)){
            updateVars(item, vars);
        }
		
		saveXML(rootElement);
	}

    @Override
	public void deletePFVs(List<BusinessComponent> pfMappingBOList) throws Exception {
		if(ContainerUtil.isNull(pfMappingBOList))
			return;
		
		Element rootElement = getRootElement();
		for(BusinessComponent bc : pfMappingBOList) {
			Element element = getElementByXpath(rootElement, getXPath_Id(bc.getId()));
			rootElement.removeContent(element);
		}
		
		saveXML(rootElement);
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<MappingConfBO> findPFVItemList(BOTemplate bot) throws Exception {
        List<MappingConfBO> list = new LinkedList<MappingConfBO>();
        String id = StringUtils.toString(bot.getBotMap().get("id"));
        if(StringUtils.isEmpty(id))
            return list;
        try{
            Element relateEL = getElementByXpath(getRootElement(), getXPath_Id(StringUtils.convertLong(id)));
            // 读取能改变粗粒度本身的变量组合及其对应的改变形式
            if(relateEL == null)
                return list;
            convertElementsToList(relateEL.getChildren(NODE_VARS), list);
        }
        catch (Exception e) {
            e.printStackTrace();
            list.clear();
            return list;
        }
        return list;
    }

    @Override
    public MappingConfBO getPFVItem(long id, long pfvId) throws Exception {
        Element varPageEL = getElementByXpath(getRootElement(), getXPath_Id(pfvId));
        Element itemEL = getElementByXpath(varPageEL, getXPath_ConfId(id));
        
        return convertElementToItemBO(itemEL);
    }

    @Override
    public void insertPFVItem(MappingConfBO confBO) throws Exception {
        Element varPageEL = getElementByXpath(getRootElement(), getXPath_Id(confBO.getPfvId()));
        Element itemEL = new Element(NODE_VARS);
        setPFVItemElement(confBO, itemEL);
        varPageEL.addContent(0, itemEL);
        saveXML(itemEL);
    }

    @Override
    public void updatePFVItem(MappingConfBO confBO) throws Exception {
        Element varPageEL = getElementByXpath(getRootElement(), getXPath_Id(confBO.getPfvId()));
        Element itemEL = getElementByXpath(varPageEL, getXPath_ConfId(confBO.getId()));
        setPFVItemElement(confBO, itemEL);
        saveXML(itemEL);
    }

	@Override
	public void deletePFVItems(long pfvId, List<BusinessComponent> bcList) throws Exception {
		if (ContainerUtil.isNull(bcList))
			return;

		Element varPageEL = getElementByXpath(getRootElement(),
				getXPath_Id(pfvId));
		for (BusinessComponent bc : bcList) {
			Element element = getElementByXpath(varPageEL, getXPath_ConfId(bc.getId()));
			varPageEL.removeContent(element);
		}

		saveXML(varPageEL);
	}
    
    private void updateVars(Element itemEL, String[] vars) {
        String group = itemEL.getAttributeValue(ATTR_GROUP);
        String[] arr = StringUtils.split(group, ",");
        Map<String, String> oldVarMap = new TreeMap<String, String>();
        Map<String, String> newVarMap = new TreeMap<String, String>();
        for(String str : arr){
            String[] arr2 = StringUtils.split(str, "=");
            String value = arr2.length > 1 ? arr2[1] : "";
            oldVarMap.put(arr2[0], value);
        }
        for(String key : vars){
            String value = "";
            if(oldVarMap.containsKey(key))
                value = oldVarMap.get(key);
            newVarMap.put(key, value);
        }
        itemEL.setAttribute(ATTR_GROUP, StringUtils.toString(newVarMap));
    }
	
	/**
	 * @param pfMappingBO
	 * @param pfElement
	 */
	private void setPFVElement(PageflowMappingBO pfMappingBO, Element pfElement) {
		pfElement.setAttribute(ATTR_NAME, pfMappingBO.getNodeName());
		pfElement.setAttribute(ATTR_MODULEID, pfMappingBO.getModuleId());
		pfElement.setAttribute(ATTR_BTNNAME, pfMappingBO.getBtnName());
		pfElement.setAttribute(ATTR_BOID, pfMappingBO.getBoId());
		pfElement.setAttribute(ATTR_SOURCEPAGE, pfMappingBO.getSourcePage());
		pfElement.setAttribute(ATTR_VARS, pfMappingBO.getVars());
		pfElement.setAttribute(ATTR_TARGETPAGE, pfMappingBO.getTargetPage());
		if(StringUtils.isNotEmpty(pfMappingBO.getRemark()))
			pfElement.setAttribute(ATTR_REMARK, pfMappingBO.getRemark());
		else
			pfElement.removeAttribute(ATTR_REMARK);
	}
	
    /**
     * @param confBO
     * @param element
     */
    private void setPFVItemElement(MappingConfBO confBO, Element element) {
        element.setAttribute(ATTR_ID, GuidUtil.getGUID() + "");
        element.setAttribute(ATTR_GROUP, confBO.getVars());
        element.setAttribute(ATTR_TARGETPAGE, confBO.getTargetPage());
    }
	
	/**
	 * @return
	 * @throws BOException
	 */
	private Element getRootElement() throws BOException {
		Element rootElement = getRootElement(PFMappingPathUtil.getClientPageflowFilePath(), null);
		if(rootElement == null) 
			 throw new BOException("获取页面流根节点失败！对应文件：" + PFMappingPathUtil.getClientPageflowFilePath());
		
		return rootElement;
	}
	
	/**
	 * 转换element为PageflowVarBO对象
	 * @param element
	 * @param id
	 * @return
	 */
	private PageflowMappingBO convertElementToPfVarBO(Element element){
		PageflowMappingBO pfVarBO = new PageflowMappingBO();

		pfVarBO.setId(StringUtils.convertLong(element.getAttributeValue(ATTR_ID)));
		pfVarBO.setBoId(element.getAttributeValue(ATTR_BOID));
		pfVarBO.setBtnName(element.getAttributeValue(ATTR_BTNNAME));
		pfVarBO.setSourcePage(element.getAttributeValue(ATTR_SOURCEPAGE));
		pfVarBO.setTargetPage(element.getAttributeValue(ATTR_TARGETPAGE));
		pfVarBO.setModuleId(element.getAttributeValue(ATTR_MODULEID));
		pfVarBO.setNodeName(element.getAttributeValue(ATTR_NAME));
		pfVarBO.setRemark(element.getAttributeValue(ATTR_REMARK));
		if(isMapping(element)) 
			pfVarBO.setIsConfig(StringUtils.convertToInt(StatusBOP.YES));
		else 
			pfVarBO.setIsConfig(StringUtils.convertToInt(StatusBOP.NO));
		pfVarBO.setVars(element.getAttributeValue(ATTR_VARS));
		return pfVarBO;
	}
	
	/**
     * @param childrenELs
     * @param items
     */
    private void convertElementsToList(List<Element> childrenELs, List<MappingConfBO> items){
        if(ContainerUtil.isNull(childrenELs))
            return;
        for(Element childrenEl : childrenELs){
            MappingConfBO itemBO = convertElementToItemBO(childrenEl);
            items.add(itemBO);
        }
    }
    
    /**
     * @param element
     * @return
     */
    private MappingConfBO convertElementToItemBO(Element element) {
        MappingConfBO itemBO = new MappingConfBO();
        itemBO.setId(StringUtils.convertLong(element.getAttributeValue(ATTR_ID)));
        itemBO.setVars(element.getAttributeValue(ATTR_GROUP));
        itemBO.setTargetPage(element.getAttributeValue(ATTR_TARGETPAGE));
        return itemBO;
    }

	/**
	 * pageflow节点是否引配置了映射
	 * (target节点下是否有变量节点)
	 * @param element
	 * @return
	 */
	private boolean isMapping(Element element) {
		return !element.getChildren(NODE_VARS).isEmpty();
	}
	
	/**
	 * 持久化xml文件
	 * @param rootElement
	 */
	private void saveXML(Element rootElement) {
		Document doc = rootElement.getDocument();
		//修改工程中的文件
		outPutDocToFile(doc, PFMappingPathUtil.getClientPageflowFilePath());
	}
	
	/**
	 * 查询条件(根据ID查询pageflow节点)
	 * @return
	 */
	private String getXPath_Id(long id){
		return "/" + ROOT + "/" + NODE_PAGEFLOW + "[@" + ATTR_ID + "='" + id + "']";
	}
	
	/**
	 * 查询条件(根据ID查询target节点)
	 * @return
	 */
	private String getXPath_ConfId(long id){
		return "/" + ROOT + "/" + NODE_PAGEFLOW + "/" + NODE_VARS + "[@" + ATTR_ID + "='" + id + "']";
	}
	
	/**
	 * 查询条件(查询所有pageflow节点)
	 * @return
	 */
	private String getXPath_All() {
		return "/" + ROOT + "/" + NODE_PAGEFLOW;
	}
}
