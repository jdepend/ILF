package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.dao.impl;

import java.util.LinkedList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.qeweb.framework.base.impl.XmlDaoImpl;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.GuidUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.frameworkbop.StatusBOP;
import com.qeweb.framework.impconfig.common.VCType;
import com.qeweb.framework.impconfig.common.util.pathutil.VarPagePathUtil;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bo.SheetBO;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bo.VarConfBO;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bo.VarPageBO;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bo.VarPageItemBO;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bo.VarVCBO;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.dao.ia.IVarPageDao;

/**
 * 变量-组件关联管理impl
 */
public class VarPageDaoImpl extends XmlDaoImpl implements IVarPageDao {

	private static final long serialVersionUID = 4217500055376178826L;

	@Override
	public List<VarPageBO> findVarPages(BOTemplate bot) {
		List<VarPageBO> list = new LinkedList<VarPageBO>();
		
		List<Element> vpElements = getElmentsByXpath(getXPath_All(), getRootElement());
		for(Element element : vpElements){
			VarPageBO bo = convertElToVarPageBO(element, false);
			
			if(bot == null || ContainerUtil.isNull(bot.getBotMap())) {
				list.add(bo);
				continue;
			}
			
			// 判断查询结果是否符合查询条件
			boolean inQuery = true;
			if(StringUtils.isNotEmpty((String) bot.getBotMap().get("id"))) {
				if(bo.getId() == StringUtils.convertLong(bot.getBotMap().get("id").toString())) {
					list.add(bo);
					break;
				}
			}
			if(inQuery && StringUtils.isNotEmpty((String) bot.getBotMap().get("relateName"))) {
				if(!StringUtils.hasSubString(bo.getRelateName(), bot.getBotMap().get("relateName").toString()))
					inQuery = false;
			}
			if(inQuery && StringUtils.isNotEmpty((String) bot.getBotMap().get("containerId"))) {
				if(!StringUtils.hasSubString(bo.getContainerId(), bot.getBotMap().get("containerId").toString()))
					inQuery = false;
			}
			if(inQuery && StringUtils.isNotEmpty((String) bot.getBotMap().get("bind"))) {
				if(!StringUtils.hasSubString(bo.getBind(), bot.getBotMap().get("bind").toString()))
					inQuery = false;
			}
			if(inQuery && StringUtils.isNotEmpty((String) bot.getBotMap().get("componentType"))) {
				if(StringUtils.isNotEqual(bo.getContainerType().toString(), bot.getBotMap().get("componentType").toString()))
					inQuery = false;
			}
            if(inQuery && StringUtils.isNotEmpty((String) bot.getBotMap().get("configStatus"))){
                if(StringUtils.isNotEqual(bo.getConfigStatus(), bot.getBotMap().get("configStatus").toString()))
                    inQuery = false;
            }
            if(inQuery && StringUtils.isNotEmpty((String) bot.getBotMap().get("vars"))) {
            	if(!StringUtils.hasSubString(bo.getVars(), (String) bot.getBotMap().get("vars")))
            		inQuery = false;
            }
				
			if(inQuery) {
				list.add(bo);
			}
		}
		
		return list;
	}
	
	@Override
	public List<VarConfBO> findVarConfs(long varPageItemId) throws Exception {
		Element rootElement = getRootElement();
        Element varPageItemEL = getElementByXpath(rootElement, getXPath_ItemId(varPageItemId));
        
        List<VarConfBO> result = new LinkedList<VarConfBO>();
        //group存储变量值信息, 其格式: group="var1=vs1:001,var2=vs2:002"
        String group = StringUtils.removeAllSpace(varPageItemEL.getAttributeValue(ATTR_GROUP));
        String[] varArr = StringUtils.split(group, ",");
        for(String varInfo : varArr) {
        	VarConfBO varConfBO = new VarConfBO();
        	varConfBO.setId(GuidUtil.getGUID());
        	String[] varTemp = StringUtils.split(varInfo, "=");
        	varConfBO.setVarName(varTemp[0]);
        	
        	String[] valueTemp = StringUtils.split(varTemp[1], ":");
        	varConfBO.setValueSetCode(valueTemp[0]);
        	if(valueTemp.length == 2)
        		varConfBO.setValueStr(valueTemp[1]);
        	else
        		varConfBO.setValueStr("");
        	
        	result.add(varConfBO);
        }
        
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VarVCBO> findVarVCs(long varPageItemId) throws Exception {
		Element rootElement = getRootElement();
        Element varPageItemEL = getElementByXpath(rootElement, getXPath_ItemId(varPageItemId));
        
        List<VarVCBO> result = new LinkedList<VarVCBO>();
        //<vc id="10" bind="pageEntryBO" vctype="21" readonly="true" disable="true" hidden="true"/>
        List<Element> vcEls = varPageItemEL.getChildren();
        for (Element el : vcEls) {
        	VarVCBO varVCBO = new VarVCBO();
        	varVCBO.setId(GuidUtil.getGUID());
        	varVCBO.setBind(el.getAttributeValue(ATTR_BIND));
        	varVCBO.setVcType(StringUtils.convertToInt(el.getAttributeValue(ATTR_VC_TYPE)));
        	varVCBO.setReadonly(el.getAttributeValue(ATTR_READONLY));
        	varVCBO.setDisable(el.getAttributeValue(ATTR_DISABLE));
        	varVCBO.setHidden(el.getAttributeValue(ATTR_HIDDEN));
        	varVCBO.setValueStr(el.getAttributeValue(ATTR_VALUE));
        	result.add(varVCBO);
		}
        
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<SheetBO> findVarTab(long varPageItemId) throws Exception {
		Element rootElement = getRootElement();
        Element varPageItemEL = getElementByXpath(rootElement, getXPath_ItemId(varPageItemId));
        
        List<SheetBO> result = new LinkedList<SheetBO>();
        /*
         * 	<vc id="1376026748888" vctype="23">
				<sheet text='t1' path='/WEB-INF/pages/demo/layoutExample.jsp'/>
				<sheet text='t2' path='/WEB-INF/pages/demo/layoutExample2.jsp'/>
			</vc>
        */
        List<Element> vcEls = varPageItemEL.getChildren();
        if(ContainerUtil.isNull(vcEls))
        	return result;
        
        List<Element> sheetEls = vcEls.get(0).getChildren();
        for (Element sheetEl : sheetEls) {
        	SheetBO sheetBO = new SheetBO();
        	sheetBO.setId(GuidUtil.getGUID());
        	sheetBO.setText(sheetEl.getAttributeValue(ATTR_TEXT));
        	sheetBO.setPath(sheetEl.getAttributeValue(ATTR_PATH));
        	result.add(sheetBO);
		}
        
        return result;
	}
	
	@Override
	public VarPageBO getVarPage(long id) throws Exception {
		Element element = getElementByXpath(getRootElement(), getXPath_Id(id));
		return convertElToVarPageBO(element, true);
	}

	@Override
	public VarPageBO getVarPage(String relateName) {
		BOTemplate bot = new BOTemplate();
		bot.push("relateName", relateName);
		List<VarPageBO> result = findVarPages(bot);
		
		return ContainerUtil.isNull(result) ? null : result.get(0);
	}
	
	@Override
	public VarPageBO getVarPageByItem(long varPageItemId) throws Exception {
		Element rootElement = getRootElement();
        Element varPageItemEL = getElementByXpath(rootElement, getXPath_ItemId(varPageItemId));
        Element varPageEl = varPageItemEL.getParentElement();
        
        return convertElToVarPageBO(varPageEl, false);
	}
	
	@Override
	public VarPageItemBO getVarPageItem(long id) throws Exception {
		Element rootElement = getRootElement();
		Element varPageItemEL = getElementByXpath(rootElement, getXPath_ItemId(id));
		
		return convertElToVarPageItemBO(varPageItemEL, false);
	}

	@Override
	public void insertVarPage(VarPageBO varPageBO) throws Exception {
		Element rootElement = getRootElement();
		Element element = new Element(NODE_RELATE);
		element.setAttribute(ATTR_ID, GuidUtil.getGUID() + "");
		setVarPageElement(varPageBO, element);
		
		rootElement.addContent(0, element);
		saveXML(rootElement);
	}

	@Override
	public void insertVarMapping(VarPageBO varPageBO, VarPageItemBO varPageItemBO, 
			List<VarConfBO> varConfBOList, List<VarVCBO> varVCBOList) throws Exception {
		//1.获取relate节点
		Element rootElement = getRootElement();
        Element varPageEl = getElementByXpath(rootElement, getXPath_Id(varPageBO.getId()));
        
		//2.创建vars(变量)节点: <vars id="1375683189713" group="var1=vs1:001,var2=vs2:002" relateName="r1-1">
        Element varPageItemEl = createVarPageItemEl(varPageItemBO, varConfBOList);

		//3.创建vc(变量影响的组件)节点
        List<Element> varVCEls = createVarVCEls(varVCBOList);
        
        varPageItemEl.addContent(varVCEls);
        varPageEl.addContent(varPageItemEl);
        saveXML(varPageEl);
	}
	
	@Override
	public void insertVarTabMapping(VarPageBO varPageBO, VarPageItemBO varPageItemBO,
			List<VarConfBO> varConfBOList, List<SheetBO> sheetBOList) throws Exception {
		//1.获取relate节点
		Element rootElement = getRootElement();
        Element varPageEl = getElementByXpath(rootElement, getXPath_Id(varPageBO.getId()));
        
		//2.创建vars(变量)节点: <vars id="1375683189713" group="var1=vs1:001,var2=vs2:002" relateName="r1-1">
        Element varPageItemEl = createVarPageItemEl(varPageItemBO, varConfBOList);

        //3.创建vc(变量影响的sheet)节点
        Element varSheetEl = createVarSheetEls(sheetBOList);

        varPageItemEl.addContent(varSheetEl);
        varPageEl.addContent(varPageItemEl);
        saveXML(varPageEl);
	}
	
	@Override
	public void updateVarMapping(VarPageBO varPageBO, VarPageItemBO varPageItemBO, 
			List<VarConfBO> varConfBOList, List<VarVCBO> varVCBOList) throws Exception {
		deleteVarEls(varPageBO, varPageItemBO);
        
        //3.创建vars(变量)节点, 创建vc(变量影响的组件)节点
        insertVarMapping(varPageBO, varPageItemBO, varConfBOList, varVCBOList);
	}

	@Override
	public void updateVarTabMapping(VarPageBO varPageBO, VarPageItemBO varPageItemBO, 
			List<VarConfBO> varConfBOList, List<SheetBO> sheetBOList) throws Exception {
		deleteVarEls(varPageBO, varPageItemBO);
        
        //3.创建var(变量)节点, 创建vc(变量影响的tab组件)节点
        insertVarTabMapping(varPageBO, varPageItemBO, varConfBOList, sheetBOList);
	}
	
    @Override
	public void updateVarPage(VarPageBO varPageBO) throws Exception {
	    Element rootElement = getRootElement();
	    Element varPageEL = getElementByXpath(rootElement, getXPath_Id(varPageBO.getId()));
        setVarPageElement(varPageBO, varPageEL);
//        //如主节点的变量已修改，则vars节点中的变量也需要修改
//        String[] vars = StringUtils.split(varPageBO.getVars(), ",");
//        for(Element item : (List<Element>) varPageEL.getChildren(NODE_VARS)){
//            updateVars(item, vars);
//        }
        
        saveXML(varPageEL);
	}
	
	@Override
	public void deleteVarPage(List<BusinessComponent> bcList) throws Exception {
		if (ContainerUtil.isNull(bcList))
			return;

		Element rootElement = getRootElement();
		for (BusinessComponent bc : bcList) {
			Element element = getElementByXpath(rootElement, getXPath_Id(bc.getId()));
			rootElement.removeContent(element);
		}

		saveXML(rootElement);
	}
	
	@Override
	public void deleteVarPageItem(List<BusinessComponent> bcList) throws Exception {
		if(ContainerUtil.isNull(bcList))
			return;
		
		Element rootElement = getRootElement();
        Element varPageItemEL = getElementByXpath(rootElement, getXPath_ItemId(bcList.get(0).getId()));
        Element varPageEl = varPageItemEL.getParentElement();
		for(BusinessComponent bc : bcList) {
			VarPageItemBO item = (VarPageItemBO) bc;
			Element element = getElementByXpath(varPageEl, getXPath_ItemId(item.getId()));
			varPageEl.removeContent(element);
		}
		
		saveXML(varPageEl);
	}
	
	/**
	 * 删除varPageItemBO下的全部变量节点(删除vars(变量)节点)
	 * @param varPageBO
	 * @param varPageItemBO
	 * @throws Exception
	 */
	private void deleteVarEls(VarPageBO varPageBO, VarPageItemBO varPageItemBO)
			throws Exception {
		//1.获取relate节点
		Element rootElement = getRootElement();

		Element varPageEl = getElementByXpath(rootElement, getXPath_Id(varPageBO.getId()));

        //2.删除vars(变量)节点
        Element oldVarPageItemEl = getElementByXpath(rootElement, getXPath_ItemId(varPageItemBO.getId()));
        varPageEl.removeContent(oldVarPageItemEl);
        saveXML(varPageEl);
	}
	 
	/**
	 * 创建vars(变量)节点: <vars id="1375683189713" group="var1=vs1:001,var2=vs2:002" relateName="r1-1">
	 * @param varPageItemBO
	 * @param varConfBOList
	 * @return
	 */
	private Element createVarPageItemEl(VarPageItemBO varPageItemBO, List<VarConfBO> varConfBOList) {
		Element element = new Element(NODE_VARS);
		element.setAttribute(ATTR_ID, GuidUtil.getGUID() + "");
		element.setAttribute(ATTR_RELATENAME, varPageItemBO.getRelateName());
		
		StringBuilder group = new StringBuilder();
		for (VarConfBO varConfBO : varConfBOList) {
			group.append(varConfBO.getVarName()).append("=")
				.append(varConfBO.getValueSetCode()).append(":").append(StringUtils.removeAllSpace(varConfBO.getValueStr()))
				.append(",");
		}
		element.setAttribute(ATTR_GROUP, StringUtils.removeEnd(group.toString()));
		
		return element;
	}
	
	/**
	 * 创建vc(变量影响的组件)节点: <vc id="30" bind="btn1" vctype="0" readonly="true" disable="false" hidden="true" value="asdf"/>
	 * @param varVCBOList
	 * @return
	 */
	private List<Element> createVarVCEls(List<VarVCBO> varVCBOList) {
		List<Element> result = new LinkedList<Element>();
		for (VarVCBO varVCBO : varVCBOList) {
			Element element = new Element(NODE_VC);
			element.setAttribute(ATTR_ID, GuidUtil.getGUID() + "");
			element.setAttribute(ATTR_BIND, varVCBO.getBind());
			element.setAttribute(ATTR_VC_TYPE, varVCBO.getVcType() + "");
			if(StringUtils.isNotEmpty(varVCBO.getReadonly()))
				element.setAttribute(ATTR_READONLY, varVCBO.getReadonly());
			if(StringUtils.isNotEmpty(varVCBO.getHidden()))
				element.setAttribute(ATTR_HIDDEN, varVCBO.getHidden());
			if(StringUtils.isNotEmpty(varVCBO.getDisable()))
				element.setAttribute(ATTR_DISABLE, varVCBO.getDisable());
			if(StringUtils.isNotEmpty(varVCBO.getValueStr()))
				element.setAttribute(ATTR_VALUE, varVCBO.getValueStr());
			result.add(element);
		}
		
		return result;
	}
	
	/**
	 * 创建包含sheet的var节点: 
		<vc id="1376026748888" vctype="23">
			<sheet text='作为' path='/WEB-INF/pages/demo/layoutExample.jsp'/>
		</vc>
	 * @param sheetBOList
	 * @return
	 */
	private Element createVarSheetEls(List<SheetBO> sheetBOList) { 
		Element varEl = new Element(NODE_VC);
		varEl.setAttribute(ATTR_VC_TYPE, VCType.VC_TYPE_TAB + "");
		
		List<Element> sheetList = new LinkedList<Element>();
		for (SheetBO sheetBO : sheetBOList) {
			Element sheetEl = new Element(NODE_SHEET);
			sheetEl.setAttribute(ATTR_TEXT, sheetBO.getText());
			sheetEl.setAttribute(ATTR_PATH, sheetBO.getPath());
			sheetList.add(sheetEl);
		}
		varEl.addContent(sheetList);
		
		return varEl;
	}
	
	/**
	 * @param varPageBO
	 * @param element
	 */
	private void setVarPageElement(VarPageBO varPageBO, Element element) {
		element.setAttribute(ATTR_PAGE, varPageBO.getPageURL());
		element.setAttribute(ATTR_VC_ID, varPageBO.getContainerId());
		if(StringUtils.isNotEmpty(varPageBO.getBind()))
			element.setAttribute(ATTR_BIND, varPageBO.getBind());
		element.setAttribute(ATTR_VC_TYPE, varPageBO.getContainerType().toString());
		element.setAttribute(ATTR_VARS, varPageBO.getVars());
		element.setAttribute(ATTR_RELATENAME, varPageBO.getRelateName());
	}
	
	/**
	 * @param element
	 * @param isDeep 	深度创建标识, 如果isDeep=true, 将生成VarPageBO.varPagetItems, 和varPagetItem.vcList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private VarPageBO convertElToVarPageBO(Element element, boolean isDeep) {
		VarPageBO bo = new VarPageBO();
		bo.setId(StringUtils.convertLong(element.getAttributeValue(ATTR_ID)));
		bo.setPageURL(element.getAttributeValue(ATTR_PAGE));
		bo.setContainerId(element.getAttributeValue(ATTR_VC_ID));
		bo.setBind(element.getAttributeValue(ATTR_BIND));
		bo.setContainerType(StringUtils.convertToInteger(element.getAttributeValue(ATTR_VC_TYPE)));
		bo.setVars(element.getAttributeValue(ATTR_VARS));
		bo.setRelateName(element.getAttributeValue(ATTR_RELATENAME));
        if(hasChildren(element))
            bo.setConfigStatus(StatusBOP.YES);
        else
            bo.setConfigStatus(StatusBOP.NO);
        
       if(isDeep)
    	   addVarPageItems(element.getChildren(NODE_VARS), bo);
        
		return bo;
	}
	
	/**
	 * 将vars节点添加到VarPageBO
	 * @param varPageItemEls
	 * @param bo
	 */
	private void addVarPageItems(List<Element> varPageItemEls, VarPageBO bo) {
		if (ContainerUtil.isNotNull(varPageItemEls)) {
			List<VarPageItemBO> varPagetItems = new LinkedList<VarPageItemBO>();
			for (Element varPageItemEl : varPageItemEls) {
				VarPageItemBO varPageItemBO = convertElToVarPageItemBO(varPageItemEl, true);
				varPagetItems.add(varPageItemBO);
			}
			bo.setVarPageItems(varPagetItems);
		}
	}

	/**
	 * 将var节点转换为VarPageItemBO
	 * @param varPageItemEl
	 * @param isDeep  深度转换标识, 如果isDeep==true, 则将var下的vc节点添加到VarPageItemBO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private VarPageItemBO convertElToVarPageItemBO(Element varPageItemEl, boolean isDeep) {
		VarPageItemBO varPageItemBO = new VarPageItemBO();
		varPageItemBO.setId(StringUtils.convertLong(varPageItemEl.getAttributeValue(ATTR_ID)));
		varPageItemBO.setRelateName(varPageItemEl.getAttributeValue(ATTR_RELATENAME));
		varPageItemBO.setGroup(varPageItemEl.getAttributeValue(ATTR_GROUP));
		if(isDeep)
			addVarVC(varPageItemEl.getChildren(), varPageItemBO);
		
		return varPageItemBO;
	}
	
	/**
	 * 将vc节点添加到VarPageItemBO
	 * @param vcEls
	 * @param bo
	 */
	private void addVarVC(List<Element> vcEls, VarPageItemBO bo) {
		if(ContainerUtil.isNotNull(vcEls)) {
			List<VarVCBO> varVCList = new LinkedList<VarVCBO>();
			for(Element vcEL : vcEls) {
				VarVCBO varVCBO = new VarVCBO();
				varVCBO.setId(StringUtils.convertLong(vcEL.getAttributeValue(ATTR_ID)));
				if(vcEL.getAttribute(ATTR_BIND) != null)
					varVCBO.setBind(vcEL.getAttributeValue(ATTR_BIND));
				varVCBO.setVcType(StringUtils.convertToInteger(vcEL.getAttributeValue(ATTR_VC_TYPE)));
				varVCBO.setReadonly(vcEL.getAttributeValue(ATTR_READONLY));
				varVCBO.setDisable(vcEL.getAttributeValue(ATTR_DISABLE));
				varVCBO.setHidden(vcEL.getAttributeValue(ATTR_HIDDEN));
				varVCBO.setValueStr(vcEL.getAttributeValue(ATTR_VALUE));
				varVCList.add(varVCBO);
				bo.setVarVCList(varVCList);
			}
		}
	}

    /**
     * @param vpEl
     * @return
     */
	@SuppressWarnings({ "unchecked" })
	private boolean hasChildren(Element vpEl) {
		if(vpEl.getChildren(NODE_VARS).isEmpty())
			return false;

		for(Element itemEl : (List<Element>) vpEl.getChildren()) {
			if(!itemEl.getChildren(NODE_VC).isEmpty())
				return true;

		}

		return false;
	}
    
	/**
	 * 返回qeweb-var_page.xml的root节点
	 * @return
	 */
	private Element getRootElement(){
		return getRootElement(VarPagePathUtil.getClientVarPagePath(), VarPagePathUtil.getServerVarPagePath());
	}
	
	/**
	 * 查询条件(查询所有pageflow节点)
	 * @return
	 */
	private String getXPath_All() {
		return "/" + ROOT + "/" + NODE_RELATE;
	}
	
	/**
	 * 查询条件(根据ID查询relate节点)
	 * @return
	 */
	private String getXPath_Id(long id){
		return "/" + ROOT + "/" + NODE_RELATE + "[@" + ATTR_ID + "='" + id + "']";
	}
	
	/**
	 * 查询条件(根据ID查询vars节点)
	 * @return
	 */
	private String getXPath_ItemId(long id){
		return "/" + ROOT + "/" + NODE_RELATE + "/" + NODE_VARS + "[@" + ATTR_ID + "='" + id + "']";
	}
	
	/**
	 * 持久化xml文件
	 * @param rootElement
	 */
	private void saveXML(Element rootElement) {
		Document doc = rootElement.getDocument();
		//修改工程中的文件
		outPutDocToFile(doc, VarPagePathUtil.getClientVarPagePath());
		//修改服务器中的文件
		outPutDocToFile(doc, VarPagePathUtil.getServerVarPagePath());
	}
}
