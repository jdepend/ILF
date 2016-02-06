package com.qeweb.framework.common.dataisland;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.math.NumberUtils;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.CheckedBO;
import com.qeweb.framework.bc.DelRecordBO;
import com.qeweb.framework.bc.prop.BCRange;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.bc.prop.LogicRange;
import com.qeweb.framework.bc.prop.MutiValue;
import com.qeweb.framework.bc.prop.Range;
import com.qeweb.framework.bc.prop.SequenceRange;
import com.qeweb.framework.bc.prop.Status;
import com.qeweb.framework.bc.prop.Value;
import com.qeweb.framework.bc.sysbo.TreeBO;
import com.qeweb.framework.bc.sysbop.FileBOP;
import com.qeweb.framework.bc.sysbop.FileBopUtil;
import com.qeweb.framework.bc.sysbop.ImageBOP;
import com.qeweb.framework.bc.sysbop.MultiFileBOP;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.bc.sysbop.mobilebop.Camera;
import com.qeweb.framework.bc.sysbop.mobilebop.LocationBOP;
import com.qeweb.framework.bc.sysbop.mobilebop.PictureBOP;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.constant.ConstantAppProp;
import com.qeweb.framework.common.constant.ConstantBOMethod;
import com.qeweb.framework.common.constant.ConstantDataIsland;
import com.qeweb.framework.common.constant.ConstantSplit;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.pageflow.ContextUtil;
import com.qeweb.framework.common.utils.BoOperateUtil;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.VCUtil;
import com.qeweb.framework.common.utils.XMLUtil;
import com.qeweb.framework.manager.BOManager;
import com.qeweb.framework.manager.DisplayType;
import com.qeweb.framework.pal.Schema;
import com.qeweb.framework.pal.SchemaPool;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.coarsegrained.Form;
import com.qeweb.framework.pal.coarsegrained.Sheet;
import com.qeweb.framework.pal.coarsegrained.Tab;
import com.qeweb.framework.pal.coarsegrained.Table;
import com.qeweb.framework.pal.coarsegrained.Tree;
import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pal.decorativeview.ContainerRelationGroup;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.finegrained.SourceBtn;
import com.qeweb.framework.pal.finegrained.enumcomp.MutiValueHelper;
import com.qeweb.framework.pal.finegrained.other.Anchor;
import com.qeweb.framework.pal.finegrained.other.Blank;
import com.qeweb.framework.pal.finegrained.other.FileField;
import com.qeweb.framework.pal.finegrained.other.Image;

/**
 * XML格式的数据岛
 * @see com.qeweb.framework.test.common.dataisland.TestDataIsland.java
 */
@SuppressWarnings("unchecked")
public class XMLDataIsland extends DataIsland {
	
	@Override
	public String createConRelationIsland(ContainerRelationGroup containerRelationGroup) {
		Map<String, List<String>> relationGroupMap = containerRelationGroup.getRelationGroupMap();
		if(ContainerUtil.isNull(relationGroupMap))
			return "";

		StringBuilder sbr = new StringBuilder();
		XMLDataIslandHelper.appendStartSingleTag(sbr, ConstantDataIsland.BORELATION);

		//构造每个粗粒度组件的关联关系表
		Set<String> keys = relationGroupMap.keySet();
		for(String sourceContainerName : keys) {
			createContainerRelationXML(sourceContainerName, relationGroupMap.get(sourceContainerName), sbr);
		}

		XMLDataIslandHelper.appendEndTag(sbr, ConstantDataIsland.BORELATION);

		return sbr.toString();
	}
	
	/**
	 * 构造粗粒度组件的关联关系表
	 * @param targetContainerNames 源目标组件名称
	 * @param targetContainerNames 受影响的目标组件名称
	 * @param sbr
	 */
	private void createContainerRelationXML(String sourceContainerName, List<String> targetContainerNames, StringBuilder sbr) {
		if(ContainerUtil.isNull(targetContainerNames))
			return;

		XMLDataIslandHelper.appendStartTag(sbr, ConstantDataIsland.GROUP);
		XMLDataIslandHelper.appendAttr(sbr, ConstantDataIsland.GROUP_SOURCE, sourceContainerName);
		XMLDataIslandHelper.appendEndTag(sbr);
		for(String containerName : targetContainerNames) {
			String[] conArr = StringUtils.split(containerName, ",");
			for(String target : conArr) {
				XMLDataIslandHelper.appendContent(sbr, ConstantDataIsland.GROUP_TARGET, target);
			}
		}
		XMLDataIslandHelper.appendEndTag(sbr, ConstantDataIsland.GROUP);
	}
	
	@Override
	public String createDataIsland(com.qeweb.framework.pal.Page page) {
		//设置令牌,以防止重复提交
		ContextUtil.setTokenTicket();
		
		StringBuilder dataIsland = new StringBuilder();
		XMLDataIslandHelper.appendHead(dataIsland);

		if(page.getBc() != null) {
			XMLDataIslandHelper.appendStartTag(dataIsland, PAGE);
			XMLDataIslandHelper.appendAttr(dataIsland, BIND, page.getBcId());
			XMLDataIslandHelper.appendAttr(dataIsland, BO_OPERATIONSTATUS, STATEMACHINE_INIT);
			XMLDataIslandHelper.appendAttr(dataIsland, PAGE_ONLOAD, page.getOnLoad());
			XMLDataIslandHelper.appendAttr(dataIsland, TIPS_TYPE, DisplayType.getTipsType());
			if(StringUtils.isEqual(ConstantAppProp.TIPS_TYPE_SIMPLE, DisplayType.getTipsType())
					&& NumberUtils.isNumber(DisplayType.getShowTipDelay())){
				XMLDataIslandHelper.appendAttr(dataIsland, TIPS_DELAY, DisplayType.getShowTipDelay());
			}
			else if(StringUtils.isEqual(ConstantAppProp.TIPS_TYPE_POPUP, DisplayType.getTipsType())){
				XMLDataIslandHelper.appendAttr(dataIsland, TIPS_DISPLAY, DisplayType.getShowTipDisplay());
			}
			XMLDataIslandHelper.appendAttr(dataIsland, CONFIRM_DISPLAY, DisplayType.getConfirmDisplay());
			
			XMLDataIslandHelper.appendEndTag(dataIsland);
			createOperateDataIsland(page.getCommandList(), page.getBc(), dataIsland);
			XMLDataIslandHelper.appendEndTag(dataIsland, PAGE);
		}
		else {
			XMLDataIslandHelper.appendStartTag(dataIsland, PAGE);
			XMLDataIslandHelper.appendAttr(dataIsland, PAGE_ONLOAD, page.getOnLoad());
			XMLDataIslandHelper.appendAttr(dataIsland, TIPS_TYPE, DisplayType.getTipsType());
			if(StringUtils.isEqual(ConstantAppProp.TIPS_TYPE_SIMPLE, DisplayType.getTipsType())
					&& NumberUtils.isNumber(DisplayType.getShowTipDelay())){
				XMLDataIslandHelper.appendAttr(dataIsland, TIPS_DELAY, DisplayType.getShowTipDelay());
			}
			else if(StringUtils.isEqual(ConstantAppProp.TIPS_TYPE_POPUP, DisplayType.getTipsType())){
				XMLDataIslandHelper.appendAttr(dataIsland, TIPS_DISPLAY, DisplayType.getShowTipDisplay());
			}
			XMLDataIslandHelper.appendAttr(dataIsland, CONFIRM_DISPLAY, DisplayType.getConfirmDisplay());
			
			XMLDataIslandHelper.appendSingleEndTag(dataIsland);
		}

		dataIsland.append(getDataIslandContent(page.getContainerList(), Envir.getRequestURI()));
		XMLDataIslandHelper.appendTail(dataIsland);

		return dataIsland.toString();
	}

	private String getDataIslandContent(List<Container> containerList, String sourcePage) {
		StringBuilder dataIsland = new StringBuilder();

		for (Container container : containerList){
			if(container instanceof Form){
				dataIsland.append(createFormDataIsland(container));
			}
			else if(container instanceof Table){
				dataIsland.append(createTableDataIsland((Table)container, sourcePage));
			}
			else if(container instanceof Tree){
				dataIsland.append(createTreeDataIsland((Tree)container));
			}
			else if(container instanceof Tab){
				dataIsland.append(createTabDataIsland((Tab)container, sourcePage));
			}
		}

		return dataIsland.toString();
	}

	@Override
	public BOProperty revertBOPById(String finegrainedId, String dataIsland) {
		BOProperty bop = null;
		String bopBind = VCUtil.getBopBind(finegrainedId);
		Document doc = XMLUtil.stringToXml(dataIsland);

		Element rootElement = doc.getRootElement();
		for(Element bopEl : (List<Element>)rootElement.getChildren(BOP)){
			if(StringUtils.isEqual(bopBind, bopEl.getAttributeValue(BIND))) {
				if(StringUtils.isNotEmpty(bopEl.getAttributeValue(BOP_CLASS))) {
					try {
						bop = (BOProperty)Class.forName(bopEl.getAttributeValue(BOP_CLASS)).newInstance();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					setBOPByXml(null, bop, bopEl);
				}
				else {
					BusinessObject bo = (BusinessObject) SpringConstant.getCTX().getBean(bopEl.getParentElement().getAttributeValue(BIND));
					bop = bo.getBOP(bopEl.getAttributeValue(BIND));
					setBOPByXml(null, bop, bopEl);
					break;
				}
				
				break;
			}
		}

		return bop;
	}

	@Override
	public String createTableDataIsland(String containerName, Page page, String dataIsland, String sourcePage) {
		if(StringUtils.isEmpty(dataIsland) || page == null)
			return null;

		return createTableDisland(containerName, page, null, dataIsland, sourcePage);
	}

	public String createTableDataIsland(String containerName, BusinessObject bo, String dataIsland, String sourcePage) {
		if(StringUtils.isEmpty(dataIsland) || bo == null)
			return null;
		
		return createTableDisland(containerName, null, bo, dataIsland, sourcePage);
	}
	
	/**
	 * 创建表格的数据岛, 参数page和bo是互斥的, 应该仅存在一个
	 * @param containerName
	 * @param page
	 * @param bo
	 * @param dataIsland
	 * @param sourcePage
	 * @return
	 */
	private String createTableDisland(String containerName, Page page, BusinessObject bo, String dataIsland, String sourcePage) {
		if(page == null && bo == null)
			return null;
		
		StringBuilder sbr = new StringBuilder();
		if(page != null)
			createTableHead(sbr, containerName, page.getPageSize(), null);
		else
			createTableHead(sbr, containerName, 0, bo);

		//创建table数据岛
		String boId = VCUtil.getDataIslandBOId(containerName);
		Element checkedEl = null;
		Element tableColumnsEl = null;
		List<Element> tableOpEl = null;
		List<Element> tableEls = new ArrayList<Element>();

		Element rootElement = XMLUtil.stringToXml(dataIsland).getRootElement();
		if(StringUtils.isEqual(rootElement.getName(), BOLIST))
			tableEls.add(rootElement);
		else
			tableEls = rootElement.getChildren(BOLIST);

		for(Element tableEl : tableEls){
			if(StringUtils.isEqual(boId, tableEl.getAttributeValue(BO_ID))) {
				checkedEl = tableEl.getChild(BOLIST_CHECKEDIDS);
				tableColumnsEl = tableEl.getChild(COLUMNINFO);
				sbr.append(XMLUtil.ElToStr(tableColumnsEl));
				tableOpEl = tableEl.getChildren(OPERATE);
				break;
			}
		}

		//添加table的operate节点
		for(Element op : tableOpEl) {
			sbr.append(XMLUtil.ElToStr(op));
		}

		sbr.append(XMLUtil.ElToStr(checkedEl));
		if(page != null)
			appendPageInfo(page, sbr, tableColumnsEl, tableOpEl, containerName, sourcePage);
		
		createTableTail(sbr);
		
		return sbr.toString();
	}

	/**
	 * 创建table的数据岛
	 * @param table
	 * @param sourcePage
	 * @return
	 */
	@Override
	public String createTableDataIsland(Table table, String sourcePage) {
		StringBuilder sbr = new StringBuilder();
		createTableHead(sbr, table.getName(), table.getPageSize(), null);
		createCheckedRow(table, sbr);
		createColumnData(table, sbr);
		createOperateDataIsland(table, sbr);
		createTableTail(sbr);

		if(table.getPage() == null)
			return sbr.toString();
		else
			return createTableDataIsland(table.getName(), table.getPage(), sbr.toString(), sourcePage);
	}

	private void createTableHead(StringBuilder sbr, String name, int pageSize, BusinessObject bo) {
		String boId = VCUtil.getDataIslandBOId(name);
		String boName = VCUtil.getBoBind(name);
		XMLDataIslandHelper.appendStartTag(sbr, BOLIST);
		XMLDataIslandHelper.appendAttr(sbr, BO_ID, boId);
		XMLDataIslandHelper.appendAttr(sbr, BIND, boName);
		//添加表格的状态
		if(bo != null) {
			XMLDataIslandHelper.appendAttr(sbr, STATUS_HIDDEN, "true", bo.getStatus().isHidden());
			XMLDataIslandHelper.appendAttr(sbr, STATUS_HIDDEN, "true", bo.getStatus().isReadonly());
			XMLDataIslandHelper.appendAttr(sbr, STATUS_HIDDEN, "true", bo.getStatus().isDisable());
		}
		//仅当自定义翻页信息时需要设置该属性
		if(pageSize != AppConfig.getPageSize())
			XMLDataIslandHelper.appendAttr(sbr, BOLIST_PAGESIZE, pageSize + "");

		XMLDataIslandHelper.appendEndTag(sbr);
	}

	private void createCheckedRow(Table table, StringBuilder sbr) {
		if(table.isRememberChecked()) {
			XMLDataIslandHelper.appendStartSingleTag(sbr, BOLIST_CHECKEDIDS);
			XMLDataIslandHelper.appendEndTag(sbr, BOLIST_CHECKEDIDS);
		}
	}

	private void createTableTail(StringBuilder sbr) {
		XMLDataIslandHelper.appendEndTag(sbr, BOLIST);
	}

	@Override
	public String updateFRelationDataIsland(String dataIsland, Map<String, BusinessComponent> bopList) {
		Document doc = XMLUtil.stringToXml(dataIsland);
		if(doc == null)
			return null;

		String boBind = doc.getRootElement().getAttributeValue(BIND);
		boolean isTable = StringUtils.isNotEmpty(doc.getRootElement().getAttributeValue(BO_INDEX));
		BusinessObject bo = (BusinessObject)SpringConstant.getCTX().getBean(boBind);
		List<Element> deleteEL = new LinkedList<Element>();
		List<Element> ELResult = doc.getRootElement().getChildren(BOP);
		for (Element bopEl : ELResult) {
			boolean deleteThisEL = true;

			for (Entry<String, BusinessComponent> bopEntry : bopList.entrySet()) {
				BOProperty bop = bo.getBOP(bopEl.getAttributeValue(BIND));
				if(bop == null)
					continue;

				bop = BoOperateUtil.getRealBop(bop);
				//对比对象是否相同
				BOProperty targetBOP = (BOProperty)bopEntry.getValue();
				if(StringUtils.isEqual(bopEl.getAttributeValue(BOP_CLASS), bopEntry.getKey()) || bop.getClass().getName().equals(bopEntry.getKey())){
					if(isTable){
						setTableValue(bopEl, targetBOP);
						setTableBOPStatus(bopEl, targetBOP);
						setTableBOPRange(bopEl, targetBOP);
					}
					else {
						setValue(bopEl, targetBOP);
						setStatus(bopEl, targetBOP, true);
						setRange(bopEl, targetBOP);
					}
					deleteThisEL = false;
					break;
				}
			}

			if(deleteThisEL)
				deleteEL.add(bopEl);
		}

		ELResult.removeAll(deleteEL);
		return XMLUtil.xmlToString(doc);
	}

	@Override
	public String updateCRelationDataIsland(String dataIsland, Map<String, Object> boList, String relations, String sourcePage) {
		Document doc = XMLUtil.stringToXml(dataIsland);
		if(doc == null || ContainerUtil.isNull(boList))
			return "";

		Element rootElement = doc.getRootElement();
		for (Entry<String, Object> entry : boList.entrySet()) {
			// query返回page
			if(entry.getValue() instanceof Page){
				updateTableElement(dataIsland, boList, relations, rootElement, entry.getKey(), sourcePage);
			}
			// query返回bo
			else if (entry.getValue() instanceof BusinessObject) {
				updateTableStatus(dataIsland, (BusinessObject)entry.getValue(), rootElement, entry.getKey());
			}
		}

		return XMLUtil.xmlToString(doc);
	}

	@Override
	public List<BusinessObject> revertBOList(String dataisland_boId, String dataIsland) {
		Document doc = XMLUtil.stringToXml(dataIsland);
		if(doc == null)
			return null;

		List<BusinessObject> boList = new LinkedList<BusinessObject>();
		Element root = doc.getRootElement();
		
		//当某个按钮的保存模式是all时, 数据岛将具备BO_SYS_TEMP状态机, 此时将还原整个页面的数据
		if(StringUtils.isEqual(ConstantBOMethod.BO_SYS_TEMP, root.getAttributeValue(BO_OPERATIONSTATUS))) {
			List<Element> childEls = root.getChildren();
			for(Element el : childEls) {
				revertBOList(el.getAttributeValue(BO_ID), boList, root, true);
			}	
		}
		//点击全局按钮还原数据岛
		if(StringUtils.isEmptyStr(dataisland_boId)) {
			List<Element> childEls = root.getChildren();
			for(Element el : childEls) {
				revertBOList(el.getAttributeValue(BO_ID), boList, root, false);
			}
		}
		//点击其它按钮还原数据岛
		else {
			revertBOList(dataisland_boId, boList, root, false);
		}

		return boList;
	}

	/**
	 *
	 * @param dataisland_boId
	 * @param boList
	 * @param root
	 * @param unCheck 是否不校验状态机, true:无需校验
	 */
	private void revertBOList(String dataisland_boId,
			List<BusinessObject> boList, Element root, boolean unCheck) {
		//从Form中还原BOList,此时BOList中仅有一个BO
		Element boElement = getContainerElement(dataisland_boId, root, BO);
		if(boElement != null && (!XMLDataIslandHelper.isInitStatus(boElement) || unCheck)) {
			if(StringUtils.isNotEmptyStr(boElement.getAttributeValue(BIND)))
				boList.add(createBOByElement(boElement.getAttributeValue(BIND), boElement));
		}

		//从Table中还原BOList
		Element boListElement = getContainerElement(dataisland_boId, root, BOLIST);
		if(boListElement != null) {
			int pageSize = 0;
			if(StringUtils.isNotEmpty(boListElement.getAttributeValue(BOLIST_PAGESIZE)))
				pageSize = StringUtils.convertToInt(boListElement.getAttributeValue(BOLIST_PAGESIZE));
						
			String bind = boListElement.getAttributeValue(BIND);
			
			for (Element boEl : (List<Element>) boListElement.getChildren(BO)) {
				if(!XMLDataIslandHelper.isInitStatus(boEl) || unCheck){
					BusinessObject bo = createBOByElement(bind, boEl);
					bo.setPageSize(pageSize);
					boList.add(bo);
				}
			}

			//翻页时记录的id
			Element checkedDom = boListElement.getChild(BOLIST_CHECKEDIDS);
			String checkedIds = "";
			if(checkedDom != null)
				checkedIds = checkedDom.getAttributeValue(BO_ID);
			if(StringUtils.isNotEmpty(checkedIds)) {
				CheckedBO checkedBO = new CheckedBO();
				BusinessObject bo = BOManager.createBO(bind);
				checkedBO.setBindBO(bo);
				checkedBO.setCheckedIds(checkedIds);
				boList.add(checkedBO);
			}

			//从数据岛还原删除的id,动态新增/删除时使用
			String delIds = boListElement.getChildText(DEL_BO_IDS);
			if(StringUtils.isNotEmpty(delIds)) {
				delIds = delIds + ",";
				//如果已删除的数据又重新被添加, 清除删除
				for(BusinessObject bo : boList) {
					if(bo.getId() != 0)
						delIds = delIds.replaceAll(bo.getId() + ",", "");
				}
				
				DelRecordBO delRecordBO = new DelRecordBO();
				BusinessObject bo = BOManager.createBO(bind);
				delRecordBO.setBindBO(bo);
				delRecordBO.setDelIds(StringUtils.removeEnd(delIds));
				boList.add(delRecordBO);
			}
		}

		//从Tree中还原BOList
		Element treeListElement = getContainerElement(dataisland_boId, root, TREE);
		if(treeListElement != null) {
			for (Element boEl : (List<Element>) treeListElement.getChildren(BO)) {
				if(!XMLDataIslandHelper.isInitStatus(boEl))
					boList.add(createTreeBOByEl(treeListElement.getAttributeValue(BIND), boEl));
			}
		}
	}

	@Override
	public BOTemplate createBOTemplate(String containerId, String dataIsland){
		if(StringUtils.isEmptyStr(dataIsland))
			return new BOTemplate();

		Element rootEl = XMLUtil.stringToXml(dataIsland).getRootElement();
		Element formEl = getContainerElement(containerId, rootEl, BO);
		if(formEl != null)
			return createFormBOT(formEl);

		Element tableEl = getContainerElement(containerId, rootEl, BOLIST);
		if(tableEl != null)
			return createTableBOT(tableEl);

		Element treeEl = getContainerElement(containerId, rootEl, TREE);
		if(treeEl != null)
			return createTreeBOT(tableEl);

		return new BOTemplate();
	}

	/**
	 * 更新table的数据岛
	 * @param dataIsland
	 * @param boList
	 * @param relations
	 * @param rootElement
	 * @param bcName
	 * @param sourcePage
	 */
	private void updateTableElement(String dataIsland,
			Map<String, Object> boList, String relations, Element rootElement,
			String bcName, String sourcePage) {
		Page page = (Page)boList.get(bcName);
		int tableNum = rootElement.getChildren(BOLIST).size();
		for (int i = 0; i < tableNum; i++) {
			Element tableEl = ((List<Element>)rootElement.getChildren(BOLIST)).get(i);
			String bcNameStr = tableEl.getAttributeValue(BIND);
			//数据岛中是否存在该table
			if(StringUtils.isNotEqual(bcName, bcNameStr))
				continue;

			String tableId = tableEl.getAttributeValue(BO_ID);
			//绘制新的数据岛XML
			Element newTableEl = XMLUtil.stringToXml(
				createTableDataIsland((tableId + HORIZONTAL_SPLIT + bcNameStr), page, dataIsland, sourcePage)).getRootElement();
			rootElement.removeContent(tableEl);
			rootElement.addContent(i, newTableEl.detach());
		}
	}
	
	private void updateTableStatus(String dataIsland, BusinessObject bo, Element rootElement, String bcName) {
		int tableNum = rootElement.getChildren(BOLIST).size();
		for (int i = 0; i < tableNum; i++) {
			Element tableEl = ((List<Element>)rootElement.getChildren(BOLIST)).get(i);
			String bcNameStr = tableEl.getAttributeValue(BIND);
			//数据岛中是否存在该table
			if(StringUtils.isNotEqual(bcName, bcNameStr))
				continue;
			
			if(bo.getStatus().isHidden())
				tableEl.setAttribute(STATUS_HIDDEN, "true");
			else
			    tableEl.removeAttribute(STATUS_HIDDEN);
		}
	}

	/**
	 * 添加table的分页信息
	 * @param page
	 * @param tableIsland
	 * @param tableColumnsEl
	 * @param tableOpEl
	 * @param sourcePage
	 * @param name
	 */
	private void appendPageInfo(Page page, StringBuilder tableIsland, Element tableColumnsEl, List<Element> tableOpEl, String name, String sourcePage) {
		if(page == null || ContainerUtil.isNull(page.getBOList()))
			return;

		appendPagebar(page, tableIsland);

		String boId = VCUtil.getDataIslandBOId(name);
		String boName = VCUtil.getBoBind(name);
		if(StringUtils.isEmpty(sourcePage))
			sourcePage = Envir.getRequestURI();
		
		//是否有单元格设置了高亮显示
		boolean highLight = false;
		StringBuilder bos = new StringBuilder();
		int index = 0;
		for (BusinessObject bo : page.getBOList()) {
			XMLDataIslandHelper.appendStartTag(bos, BO);
			XMLDataIslandHelper.appendAttr(bos, BO_INDEX, (++index) + "");
			XMLDataIslandHelper.appendAttr(bos, BO_OPERATIONSTATUS, STATEMACHINE_INIT);

			if(ContainerUtil.isNotNull(tableOpEl)) {
				StringBuilder sbr = new StringBuilder();
				//是否设置了其中一个行级按钮的状态
				boolean rowBtuStatus = false;
				for(Element operate : tableOpEl) {
					if(rowOperateInit(bo, operate, index))
						rowBtuStatus = true;
					
					if(operate.getAttribute(OPERATE_EXPEND) != null)
						sbr.append(XMLUtil.ElToStr(operate));
				}
				XMLDataIslandHelper.appendAttr(bos, BO_ROWOPT_STATUS, rowBtuStatus);
				XMLDataIslandHelper.appendEndTag(bos);
				bos.append(sbr.toString());
			}
			else {
				XMLDataIslandHelper.appendEndTag(bos);
			}

			if(tableColumnsEl != null) {
				for (Element columnEl : (List<Element>) tableColumnsEl.getChildren(COLUMN)) {
					BOProperty bop = BoOperateUtil.getBOP(bo, columnEl.getAttributeValue(BIND));
					if(bop == null)
						continue;
					XMLDataIslandHelper.appendStartTag(bos, BOP);
					String bind = columnEl.getAttributeValue(BIND);
					XMLDataIslandHelper.appendAttr(bos, BIND, bind);
					if((bop.getValue().getClass().getName() != Value.class.getName()))
						XMLDataIslandHelper.appendAttr(bos, BOP_DISPLAY, XMLUtil.encode(bop.getValue().getDisplayValue()));
					
					if(StringUtils.isNotEmpty(bop.getStyle())){
						XMLDataIslandHelper.appendAttr(bos,	BOP_HIGHLIGHT, bop.getStyle());
						highLight = true;
					}
					else if(bop.isHighlight()){
						Schema schema = SchemaPool.getSchema(boId, boName, bind, sourcePage);
						if(schema != null) {
							XMLDataIslandHelper.appendAttr(bos,	BOP_HIGHLIGHT, schema.getStyle());
							highLight = true;
						}
					}
					
					//FileBOP
					BOProperty realBOP = BoOperateUtil.getRealBop(bop);
					if(realBOP instanceof FileBOP) {
						XMLDataIslandHelper.appendAttr(bos,	BOP_DATA_URL, bop.toLink());
					}
					//多附件 
					else if(realBOP instanceof MultiFileBOP){
						String url = "";
						for(FileBOP itemBop : ((MultiFileBOP) bop).getFiles()){
							url = url + itemBop.toLink() + ConstantSplit.TREBLE_BACKSLASH_SPLIT;
						}
						XMLDataIslandHelper.appendAttr(bos, BOP_DATA_URL, url);
					}

					if(bop.getStatus().isDisable())
						XMLDataIslandHelper.appendAttr(bos,	STATUS_DISABLE, "true");
					else if(bop.getStatus().isReadonly())
						XMLDataIslandHelper.appendAttr(bos,	STATUS_READONLY, "true");
					
					//选项
					String items = getTableBOPItems(bop);
					if(StringUtils.isNotEmpty(items))
						XMLDataIslandHelper.appendAttr(bos,	BOP_RANGE_ENUM, items);
					
					XMLDataIslandHelper.appendEndTag(bos);
					XMLDataIslandHelper.appendContent(bos, bop.getValue().getValue());
					XMLDataIslandHelper.appendEndTag(bos, BOP);

					if(BoOperateUtil.getRealBop(bop) instanceof OperateBOP) {
						setRowOperate(
								getOpnerateId(boId, boName, bind), 
								((OperateBOP) BoOperateUtil.getRealBop(bop)).getOperate(), 
								index, 
								bind, 
								bos);
					}
				}
			}

			XMLDataIslandHelper.appendEndTag(bos, BO);
		}
		
		if(highLight) {
			tableIsland.append("<").append(BOLIST_HEIGHT).append("/>");
		}
		
		tableIsland.append(bos.toString());
	}

	private String getOpnerateId(String boId, String boName, String bind) {
		return boId + ConstantSplit.GA_PARAM_SPLIT + boName + ConstantSplit.GA_PARAM_SPLIT + bind + ConstantSplit.GA_PARAM_SPLIT + "operate";
	}

	/**
	 * 表格中的可执行超链接对应的operate
	 * @param opertateId
	 * @param operate
	 * @param index
	 * @param bind
	 * @param tableDataIsland
	 */
	private void setRowOperate(String opertateId, String operate, int index, String bind, StringBuilder tableDataIsland) {
		XMLDataIslandHelper.appendStartTag(tableDataIsland, OPERATE);
		XMLDataIslandHelper.appendAttr(tableDataIsland, OPERATE_ID, opertateId);
		XMLDataIslandHelper.appendAttr(tableDataIsland, OPERATE_IDX, index + "");
		XMLDataIslandHelper.appendAttr(tableDataIsland, OPERATE_EXPEND, "true");
		XMLDataIslandHelper.appendAttr(tableDataIsland, OPERATE_HASMSG, "true");
		XMLDataIslandHelper.appendAttr(tableDataIsland, OPERATE_METHOD, operate);
		XMLDataIslandHelper.appendAttr(tableDataIsland, BIND, bind);
		XMLDataIslandHelper.appendSingleEndTag(tableDataIsland);
	}

	/**
	 * 设置行级按钮的属性
	 * @param bo
	 * @param operate
	 * @param index
	 * @result 是否为行级按钮设置了状态
	 */
	private boolean rowOperateInit(BusinessObject bo, Element operate, int index) {
		//非行级按钮
		if(operate.getAttribute(OPERATE_EXPEND) == null)
			return false;

		operate.setAttribute(OPERATE_IDX, index + "");
		if(operate.getAttribute(STATUS_HIDDEN) != null)
			operate.removeAttribute(STATUS_HIDDEN);
		if(operate.getAttribute(STATUS_DISABLE) != null)
			operate.removeAttribute(STATUS_DISABLE);

		//设置行级按钮的状态
		Map<String, OperateBOP> operateMap = bo.getOperateMap();
		if(ContainerUtil.isNull(operateMap))
			return false;

		String btnTagName = VCUtil.getButtonTagName(operate.getAttributeValue(OPERATE_ID));
		if(operateMap.containsKey(btnTagName)) {
			OperateBOP bop = operateMap.get(btnTagName);
			if(bop.getStatus().isHidden()) {
				operate.setAttribute(STATUS_HIDDEN, "true");
				return true;
			}
			else if(bop.getStatus().isDisable()) {
				operate.setAttribute(STATUS_DISABLE, "true");
				return true;
			}
			else if(bop.getStatus().isReadonly()) {
				operate.setAttribute(STATUS_READONLY, "true");
				return true;
			}
			else {
				return false;
			}
		}
		
		return false;
	}

	/**
	 * append table Pagebar
	 * @param page
	 * @param tableDataIsland
	 */
	private void appendPagebar(Page page, StringBuilder tableDataIsland) {
		XMLDataIslandHelper.appendStartTag(tableDataIsland, PAGINATION);
		XMLDataIslandHelper.appendAttr(tableDataIsland, PAGINATION_START, page.getStartIndex() + "");
		XMLDataIslandHelper.appendAttr(tableDataIsland, PAGINATION_TOTAL, page.getTotalCount() + "");
		XMLDataIslandHelper.appendAttr(tableDataIsland, PAGINATION_LIMIT, page.getPageSize() + "");
		XMLDataIslandHelper.appendSingleEndTag(tableDataIsland);
	}

	/**
	 *
	 * @param formBO
	 * @param bcName
	 * @param rootElement
	 *
	 */
	private void updateFormElement(BusinessObject formBO, String bcName, Element rootElement) {
		for (Element boEl : (List<Element>) rootElement.getChildren(BO)) {
			if (StringUtils.isEqual(boEl.getAttributeValue(BIND), bcName))
				processXml(formBO, boEl);
		}
	}

	/**
	 * 根据formElement创建BOT
	 * @param boElement
	 * @return
	 */
	private BOTemplate createFormBOT(Element formElement) {
		BOTemplate bot = new BOTemplate();
		for (Element bopElement : (List<Element>) formElement.getChildren(BOP)) {
			List<Element> valueEls = bopElement.getChildren(BOP_VALUE);
			if(valueEls.size() == 1)
				bot.push(bopElement.getAttributeValue(BIND), bopElement.getChildText(BOP_VALUE));
			//设置范围型BOT
			else if(valueEls.size() == 2) {
				for(Element valueEl : valueEls) {
					if(StringUtils.isEqual(EXPEND_MIN, valueEl.getAttributeValue(BOP_VALUE_EXPEND)))
						bot.push(bopElement.getAttributeValue(BIND) + EXPEND_SPLIT + EXPEND_MIN, valueEl.getText());
					else
						bot.push(bopElement.getAttributeValue(BIND) + EXPEND_SPLIT + EXPEND_MAX, valueEl.getText());
				}
			}
		}
		bot.setBoName(formElement.getAttributeValue(BIND));

		return bot;
	}

	/**
	 * 根据tableElement创建BOT
	 * @param boEl
	 * @return
	 */
	private BOTemplate createTableBOT(Element tableElement){
		BOTemplate bot = new BOTemplate();
		bot.setBoName(tableElement.getAttributeValue(BIND));

		Map<String, Object> botMap = new HashMap<String, Object>();
		
		//粗粒度组件关联时使用
		Element botEl = tableElement.getChild(BOLIST_BOT);
		if(botEl != null) {
			for(Element boEl : (List<Element>) botEl.getChildren(BO)){
				for (Element bopEl : (List<Element>) boEl.getChildren(BOP)) {
					if(StringUtils.isEmpty(bopEl.getText()))
						continue;
					botMap.put(bopEl.getAttributeValue(BIND), XMLUtil.decode(bopEl.getText()));
				}
			}
		}
		//目标页面默认执行query方法时使用
		else {
			for(Element boEl : (List<Element>) tableElement.getChildren(BO)){
				if(XMLDataIslandHelper.isInitStatus(boEl))
					continue;

				for (Element bopEl : (List<Element>) boEl.getChildren(BOP)) {
					if(StringUtils.isEmpty(bopEl.getText()))
						continue;
					botMap.put(bopEl.getAttributeValue(BIND), XMLUtil.decode(bopEl.getText()));
				}
			}
		}

		bot.setBotMap(botMap);
		return bot;
	}

	/**
	 * 根据treeElement创建BOT
	 * @param boEl
	 * @return
	 */
	private BOTemplate createTreeBOT(Element treeElement){
		BOTemplate bot = new BOTemplate();
		for(Element boEl : (List<Element>) treeElement.getChildren(BO)){
			if(XMLDataIslandHelper.isInitStatus(boEl))
				continue;

			for (Element bopEl : (List<Element>) boEl.getChildren(BOP)) {
				if(StringUtils.isNotEmpty(bopEl.getText()))
					bot.push(bopEl.getAttributeValue(BIND), XMLUtil.decode(bopEl.getText()));
			}
			return bot;
		}

		return bot;
	}

	/**
	 * 获取粗粒度组件的BOElement
	 * @param containerId
	 * @param rootElement
	 * @param elementName
	 * @return
	 */
	private Element getContainerElement(String containerId, Element rootElement, String elementName) {
		if(StringUtils.isNotEmpty(containerId)){
			for (Element boElement : (List<Element>) rootElement.getChildren(elementName)) {
				if (containerId.equals(boElement.getAttributeValue(BO_ID)))
					return boElement;
			}
		}

		if(elementName.equals(rootElement.getName()))
			return rootElement;

		return null;
	}

	private BusinessObject createTreeBOByEl(String boId, Element boEl) {
		BusinessObject bo = BOManager.createBO(boId);
		String operationStatus = boEl.getAttributeValue(BO_OPERATIONSTATUS);
		if(StringUtils.isEmpty(operationStatus))
			operationStatus = STATEMACHINE_INIT;

		String id = boEl.getAttributeValue(TREE_BO_ID);
		bo.setId(StringUtils.convertLong(id));
		bo.getBOP("id").setValue(id);
		bo.pushDisplayBopName("id");
		return bo;
	}

	/**
	 * 根据boElement创建BO
	 * @param boId  bo的唯一标识
	 * @param boElement  XMLEmement
	 * @return
	 */
	private BusinessObject createBOByElement(String boId, Element boElement) {
		BusinessObject bo = BOManager.getBOInstance(boId);
		String operationStatus = boElement.getAttributeValue(BO_OPERATIONSTATUS);
		if(StringUtils.isEmpty(operationStatus))
			operationStatus = STATEMACHINE_INIT;

		bo.setOperationStatus(operationStatus);
		bo.setCurrentField(boElement.getAttributeValue(BIND));
		createBOPByElementForBO(bo, boElement);

		return bo;
	}

	/**
	 * 根据boElement为BO创建BOP
	 * @param bo
	 * @param boElement
	 */
	private void createBOPByElementForBO(BusinessObject bo, Element boElement){
		List<Element> elList = boElement.getChildren(BOP);
		for (Element bcEl : elList) {
			BOProperty bop = BoOperateUtil.getBOP(bo, bcEl.getAttributeValue(BIND));
			bo.pushDisplayBopName(bcEl.getAttributeValue(BIND));
			// 根据Element值设置BOP的值、状态、范围
			setBOPByXml(bo, bop, bcEl);
		}
	}

	/**
	 * 添加Form组件的数据岛
	 * @param container
	 */
	@Override
	public String createFormDataIsland(Container container){
		return createFormDataIsland(container, false);
	}

	/**
	 * 添加table弹出框的数据岛
	 */
	@Override
	public String createHiddenFormDataIsland(Table table, String hiddenFormId) {
		return createFormDataIsland(table, true, hiddenFormId);
	}

	public String createFormDataIsland(Container container, boolean isHidden){
		return this.createFormDataIsland(container, isHidden, null);
	}

	public String createFormDataIsland(Container container, boolean isHidden, String hiddenContainerId) {
		StringBuilder sbr = new StringBuilder();

		XMLDataIslandHelper.appendStartTag(sbr, BO);
		Map<String, FinegrainedComponent> fcList = container.getFcList();
		if(StringUtils.isEmpty(hiddenContainerId))
			XMLDataIslandHelper.appendAttr(sbr, BO_ID, container.getId());
		else{
			XMLDataIslandHelper.appendAttr(sbr, BO_ID, hiddenContainerId);
			fcList = getHiddenFcList((Table) container, hiddenContainerId);
		}
		XMLDataIslandHelper.appendAttr(sbr, BIND, container.getBcId());
		XMLDataIslandHelper.appendAttr(sbr, BO_OPERATIONSTATUS, STATEMACHINE_INIT);
		XMLDataIslandHelper.appendEndTag(sbr);

		
		for(FinegrainedComponent fc : fcList.values()) {
			BOProperty bop =  fc.getBc();
			BOProperty realBOP = BoOperateUtil.getRealBop(bop);
			//占位标签并不绑定bop
			if(fc instanceof Blank || bop == null)
				continue;
			XMLDataIslandHelper.appendStartTag(sbr, BOP);
			XMLDataIslandHelper.appendAttr(sbr, BIND, fc.getBcId());
			XMLDataIslandHelper.appendAttr(sbr, GROUPNAME, fc.getGroupName());
			XMLDataIslandHelper.appendAttr(sbr, BOP_ISTIGGER, "true", realBOP.isTiggerCRelation());
			if(fc.isEnableBopRelation() && realBOP.hasBOPRelation()) {
				XMLDataIslandHelper.appendAttr(sbr, BOP_ISRELATE, "true");
				XMLDataIslandHelper.appendAttr(sbr, BOP_CLASS, realBOP.getClass().getName());
			}
			XMLDataIslandHelper.appendAttr(sbr, BOP_ISCONRELATE, "true", realBOP.hasBORelation());
			XMLDataIslandHelper.appendAttr(sbr, BOP_JS, realBOP.getDesirousMethod());
			//终端bop标识
			if(realBOP instanceof LocationBOP){
				XMLDataIslandHelper.appendAttr(sbr, BOP_TERMINAL_LOCATION, "true");
			}
			else if(realBOP instanceof PictureBOP){
				XMLDataIslandHelper.appendAttr(sbr, BOP_TERMINAL_PIC, "true");
			}
			
			if(fc instanceof FileField) {
				XMLDataIslandHelper.appendAttr(sbr, BOP_ISFILE, "true");
				String operate = ((FileField) fc).getOperate();
				if(StringUtils.isNotEmpty(operate))
					XMLDataIslandHelper.appendAttr(sbr, OPERATE, operate);
			}
			XMLDataIslandHelper.appendEndTag(sbr);
			appendValue(sbr, fc);
			appendPdropertiesForSpecialFC(sbr, fc);
			appendStatus(sbr, fc);
			appendRange(sbr, fc);
			XMLDataIslandHelper.appendEndTag(sbr, BOP);
			//超链接按钮添加operate节点
			if(container instanceof Form && realBOP instanceof OperateBOP) {
				XMLDataIslandHelper.appendStartTag(sbr, OPERATE);
				XMLDataIslandHelper.appendAttr(sbr, OPERATE_ID, VCUtil.createOperateName(container.getId(), container.getBcId(), fc.getBcId()));
				XMLDataIslandHelper.appendAttr(sbr, OPERATE_HASMSG, "true");
				OperateBOP optBop = (OperateBOP) realBOP;
				XMLDataIslandHelper.appendAttr(sbr, OPERATE_METHOD, optBop.getOperate());
				if(!optBop.isSubmit())
					XMLDataIslandHelper.appendAttr(sbr, OPERATE_NOTSUBMIT, "true");
				XMLDataIslandHelper.appendSingleEndTag(sbr);
			}
		}

		if(isHidden) {
			XMLDataIslandHelper.appendStartTag(sbr, OPERATE);
			XMLDataIslandHelper.appendAttr(sbr, OPERATE_ID, VCUtil.createFinegrainedID(container.getName(), ConstantBOMethod.BO_INIT));
			XMLDataIslandHelper.appendAttr(sbr, OPERATE_METHOD, ConstantBOMethod.BO_INIT);
			XMLDataIslandHelper.appendAttr(sbr, OPERATE_TEXT, AppLocalization.getLocalization("form.save"));
			XMLDataIslandHelper.appendAttr(sbr, OPERATE_HASMSG, "true");
			XMLDataIslandHelper.appendSingleEndTag(sbr);
		}
		else {
			createOperateDataIsland(container, sbr);
		}

		XMLDataIslandHelper.appendEndTag(sbr, BO);
		return sbr.toString();
	}

	/**
	 * 获取系统弹出框自身需要显示的bop集合
	 * @param table
	 * @param hiddenContainerId
	 * @return
	 */
	private Map<String, FinegrainedComponent> getHiddenFcList(
			Table table, String hiddenContainerId) {
		String[] arr  = StringUtils.split(hiddenContainerId, HORIZONTAL_SPLIT);
		String btnName = StringUtils.split(arr[2], COMPONENTSPLIT)[0];
		if(StringUtils.isEqual(ConstantBOMethod.BO_INSERT, btnName))
			return table.getInsertFields();
		if(StringUtils.isEqual(ConstantBOMethod.BO_UPDATE, btnName))
			return table.getUpdateFields();
		if(StringUtils.isEqual(ConstantBOMethod.BO_VIEW, btnName))
			return table.getViewFields();
		return table.getFcList();
	}

	/**
	 * 添加table的列信息，包括隐藏列和显示列
	 * @param table
	 * @param dataIsland
	 * @return
	 */
	private Element createColumnData(Table table, StringBuilder dataIsland){
		Map<String, FinegrainedComponent> fcList = table.getFcList();
		StringBuilder sbr = new StringBuilder();
		XMLDataIslandHelper.appendStartTag(sbr, COLUMNINFO);
		XMLDataIslandHelper.appendAttr(sbr, SELECTIONMODEL, table.getSelectionModel());
		XMLDataIslandHelper.appendAttr(sbr, COLUMNINFO_TABLEFIELDS, table.getDisplayFields().keySet());
		XMLDataIslandHelper.appendEndTag(sbr);
		for (Entry<String, FinegrainedComponent> entry : fcList.entrySet()) {
			XMLDataIslandHelper.appendStartTag(sbr, COLUMN);
			XMLDataIslandHelper.appendAttr(sbr, BIND, entry.getKey());
			BOProperty bop = entry.getValue().getBc();
			BOProperty realBOP = BoOperateUtil.getRealBop(bop);
			if(bop != null){
				XMLDataIslandHelper.appendAttr(sbr, BOP_ISRELATE, "true", bop.hasBOPRelation());
				XMLDataIslandHelper.appendAttr(sbr, BOP_ISCONRELATE, "true", bop.hasBORelation());
				Value value = bop.getValue();
				if(value != null && StringUtils.isNotEmpty(value.getValue())){
					XMLDataIslandHelper.appendAttr(sbr, COLUMN_DEFAULT_VALUE, value.getValue());
				}
			}
			//超链接
			if(entry.getValue() instanceof Anchor){
				XMLDataIslandHelper.appendAttr(sbr, COLUMN_TARGET, ((Anchor) entry.getValue()).getTarget());
			}
			//文件
			else if(entry.getValue() instanceof FileField){
				XMLDataIslandHelper.appendAttr(sbr, COLUMN_TYPE, COLUMN_TYPE_FILE);
				String operate = ((FileField) entry.getValue()).getOperate();
				if(StringUtils.isNotEmpty(operate))
					XMLDataIslandHelper.appendAttr(sbr, OPERATE, operate);
			}
			//图片
			else if(entry.getValue() instanceof Image){
				XMLDataIslandHelper.appendAttr(sbr, COLUMN_TYPE, COLUMN_TYPE_IMG);
				if(realBOP instanceof ImageBOP) {
					ImageBOP imgBOP = (ImageBOP) realBOP;
					if(imgBOP.isAdaptive()) {
						XMLDataIslandHelper.appendAttr(sbr, COLUMN_TYPE_IMG_ISADAPTIVE, "true");
					}
					else {
						XMLDataIslandHelper.appendAttr(sbr, COLUMN_TYPE_IMG_HEIGHT, imgBOP.getHeight());
						XMLDataIslandHelper.appendAttr(sbr, COLUMN_TYPE_IMG_WIDTH, imgBOP.getWidth());
					}
				}
			}
			//地址信息
			else if(realBOP instanceof LocationBOP){
				XMLDataIslandHelper.appendAttr(sbr, BOP_TERMINAL_LOCATION, "true");
			}
			XMLDataIslandHelper.appendEndTag(sbr);
			appendStatus(sbr, entry.getValue());
			appendRange(sbr, entry.getValue());
			XMLDataIslandHelper.appendEndTag(sbr, COLUMN);
		}
		XMLDataIslandHelper.appendEndTag(sbr, COLUMNINFO);
		dataIsland.append(sbr);

		return XMLUtil.stringToXml(sbr.toString()).getRootElement();
	}

	/**
	 * 创建operate的数据岛
	 * @param container
	 * @param sbr
	 */
	private void createOperateDataIsland(Container container, StringBuilder sbr) {
		Map<String, CommandButton> btnMap = container.getButtonList();
		createOperateDataIsland(btnMap.values(), container.getBc(), sbr);
	}

	/**
	 *
	 * @param btns
	 * @param bo
	 * @param sbr
	 */
	private void createOperateDataIsland(Collection<CommandButton> btns, BusinessObject bo, StringBuilder sbr) {
		if(ContainerUtil.isNull(btns))
			return;

		for(CommandButton btn : btns) {
			XMLDataIslandHelper.appendStartTag(sbr, OPERATE);
			XMLDataIslandHelper.appendAttr(sbr, OPERATE_ID, btn.getName());

			String operate = btn.getOperate();
			if(StringUtils.isNotEmpty(operate)){
				XMLDataIslandHelper.appendAttr(sbr, OPERATE_JS_BEFORE, ConstantBOMethod.getJSMethodsBefore(bo, operate));
				XMLDataIslandHelper.appendAttr(sbr, OPERATE_JS_AFTER, ConstantBOMethod.getJSMethodsAfter(bo, operate));
				XMLDataIslandHelper.appendAttr(sbr, OPERATE_METHOD, removeSysOperate(bo, operate));
			}
			XMLDataIslandHelper.appendAttr(sbr, OPERATE_TEXT, btn.getText());
			XMLDataIslandHelper.appendAttr(sbr, GROUPNAME, btn.getGroupName());
			BOProperty btnBOP = btn.getBc();
			if(btnBOP != null && btnBOP instanceof OperateBOP) {
				OperateBOP optBOP = (OperateBOP)btnBOP;
				//点击按钮时不进行校验
				if(!optBOP.isSubmit())
					XMLDataIslandHelper.appendAttr(sbr, OPERATE_NOTSUBMIT, "true");
				//点击按钮后不刷新
				if(!optBOP.isFresh())
					XMLDataIslandHelper.appendAttr(sbr, OPERATE_NOTFRESH, "true");
				
				if(optBOP.getStatus().isDisable())
					XMLDataIslandHelper.appendAttr(sbr, STATUS_DISABLE, "true");
				if(optBOP.getStatus().isHidden())
					XMLDataIslandHelper.appendAttr(sbr, STATUS_HIDDEN, "true");
			}

			XMLDataIslandHelper.appendAttr(sbr, OPERATE_SAVEMOD, btn.getBc().getSaveMod());
			Map<String, String> operateMod = btn.getBc().getOperateMod();
			if(ContainerUtil.isNotNull(operateMod)) {
				String operateModStr = "";
				for (Entry<String, String> entry : operateMod.entrySet()) {
					operateModStr += entry.getKey() + "=" + entry.getValue() + ",";
				}
				XMLDataIslandHelper.appendAttr(sbr, OPERATE_OPTMOD, StringUtils.removeEnd(operateModStr));
			}
			XMLDataIslandHelper.appendAttr(sbr, OPERATE_EXPEND, "true", btn.isHasExpand());
			if(btn.getBc().isHasConfirm())
				XMLDataIslandHelper.appendAttr(sbr, OPERATE_HASCONFIRM, "true");
			XMLDataIslandHelper.appendAttr(sbr, OPERATE_CONFIRMMSG, btn.getBc().getConfirmMsg());
			XMLDataIslandHelper.appendAttr(sbr, OPERATE_HASMSG, "true");
			
			XMLDataIslandHelper.appendEndTag(sbr);
			
			//按钮的<qeweb:source>标签
			SourceBtn soureBtn = btn.getSourceBtn();
			if(soureBtn != null) {
				XMLDataIslandHelper.appendStartTag(sbr, SOURCE);
				XMLDataIslandHelper.appendAttr(sbr, SOURCE_BINDBO, soureBtn.getBcId());
				XMLDataIslandHelper.appendAttr(sbr, SOURCE_BINDBOP, soureBtn.getBindBop());
				XMLDataIslandHelper.appendAttr(sbr, SOURCE_OPERATE, soureBtn.getOperate());
				XMLDataIslandHelper.appendAttr(sbr, SOURCE_SM, soureBtn.getSm());
				XMLDataIslandHelper.appendAttr(sbr, SOURCE_TEXT, soureBtn.getText());
				XMLDataIslandHelper.appendSingleEndTag(sbr);
			}
			XMLDataIslandHelper.appendEndTag(sbr, OPERATE);
		}
	}

	/**
	 * 去掉operate中的平台方法
	 * @param operate
	 * @return
	 */
	private String removeSysOperate(BusinessObject bo, String operate) {
		if(StringUtils.isEmpty(operate))
			return "";
		
		String result = "";
		String[] methodArr = StringUtils.split(operate, ConstantSplit.COMMA_SPLIT);
		if(StringUtils.isEmpty(methodArr))
			return result;
		
		for(String methodName : methodArr) {
			if(StringUtils.isNotEmpty(ConstantBOMethod.getJSMethodsBefore(bo, methodName)))
				continue;
			else if(StringUtils.isNotEmpty(ConstantBOMethod.getJSMethodsAfter(bo, methodName)))
				continue;
			else 
				result += methodName + ",";
		}
		
		return StringUtils.removeEnd(result);
	}
	
	
	@Override
	public String createTabDataIsland(Tab tab, String sourcePage) {
		StringBuilder sbr = new StringBuilder();
		sbr.append(createTabSingleDataIsland(tab));
		sbr.append(getDataIslandContent(tab.getContainerList(), sourcePage));
		return sbr.toString();
	}

	//创建tab数据岛---不包含粗粒度时用
	private String createTabSingleDataIsland(Tab tab) {
		StringBuilder sbr = new StringBuilder();

		XMLDataIslandHelper.appendStartTag(sbr, BO);
		XMLDataIslandHelper.appendAttr(sbr, BO_ID, tab.getId());
		XMLDataIslandHelper.appendAttr(sbr, BIND, tab.getBcId());
		XMLDataIslandHelper.appendAttr(sbr, BO_OPERATIONSTATUS, STATEMACHINE_INIT);
		XMLDataIslandHelper.appendEndTag(sbr);

		Map<String, FinegrainedComponent> fcList = tab.getFcList();
		for(FinegrainedComponent fc : fcList.values()) {
			BOProperty bop =  fc.getBc();
			//占位标签并不绑定bop
			if(fc instanceof Blank || bop == null)
				continue;
			XMLDataIslandHelper.appendStartTag(sbr, BOP);
			XMLDataIslandHelper.appendAttr(sbr, BIND, fc.getBcId());
			XMLDataIslandHelper.appendAttr(sbr, GROUPNAME, fc.getGroupName());
			XMLDataIslandHelper.appendAttr(sbr, BOP_ISRELATE, "true", bop.hasBOPRelation());
			XMLDataIslandHelper.appendAttr(sbr, BOP_ISCONRELATE, "true", bop.hasBORelation());
			XMLDataIslandHelper.appendAttr(sbr, BOP_JS, bop.getDesirousMethod());
			if(fc instanceof FileField) {
				XMLDataIslandHelper.appendAttr(sbr, BOP_ISFILE, "true");
				String operate = ((FileField) fc).getOperate();
				if(StringUtils.isNotEmpty(operate))
					XMLDataIslandHelper.appendAttr(sbr, OPERATE, operate);
			}
			XMLDataIslandHelper.appendEndTag(sbr);
			appendValue(sbr, fc);
			appendPdropertiesForSpecialFC(sbr, fc);
			appendStatus(sbr, fc);
			appendRange(sbr, fc);
			XMLDataIslandHelper.appendEndTag(sbr, BOP);
		}

		for (Sheet sheet : tab.getSheetList()) {
			createOperateDataIsland(sheet.getButtonList(), tab.getBc(), sbr);
		}

		XMLDataIslandHelper.appendEndTag(sbr, BO);
		return sbr.toString();
	}

	/**
	 * 为特殊的细粒度组件添加属性<br>
	 * 这些组件包括上传、下载、手机拍照
	 * @param dataIsland
	 * @param FinegrainedComponent
	 */
	private void appendPdropertiesForSpecialFC(StringBuilder dataIsland, FinegrainedComponent fc) {
		if(fc == null)
			return;

		BOProperty bop = fc.getBc();
		//文件上传、手机端拍照，控件数据岛结构
		FileBOP fileBop = FileBopUtil.getFileBop(bop);
		MultiFileBOP multiFileBOP = FileBopUtil.getMultiFileBOP(bop);
		if(fileBop != null || multiFileBOP != null || bop instanceof Camera){
			XMLDataIslandHelper.appendStartTag(dataIsland, BOP_DATA);
			String url = "";
			if(fileBop != null && StringUtils.isNotEmptyStr(fileBop.getPath()))
				url = fileBop.getPath();
			else if(multiFileBOP != null){
				for(Iterator<FileBOP> it = multiFileBOP.getFiles().iterator(); it.hasNext();){
					FileBOP itemBop = it.next();
					url = url + itemBop.toLink() + ConstantSplit.TREBLE_BACKSLASH_SPLIT;
				}
				XMLDataIslandHelper.appendAttr(dataIsland, BOP_DATA_UPLOAD_NUM, multiFileBOP.getUploadNum());
				XMLDataIslandHelper.appendAttr(dataIsland, BOP_DATA_UPLOAD_SIZE, multiFileBOP.getUploadSize());
			}
			XMLDataIslandHelper.appendAttr(dataIsland, BOP_DATA_URL, url);
			XMLDataIslandHelper.appendSingleEndTag(dataIsland);
		}
	}

	/**
	 * 添加细粒度组件的值
	 * @param dataIsland
	 * @param FinegrainedComponent
	 */
	private void appendValue(StringBuilder dataIsland, FinegrainedComponent fc) {
		Value value = fc.getBc().getValue();
		String display = ((value.getClass().getName() != Value.class.getName()) ?
				"display='"+ XMLUtil.encode(value.getDisplayValue()) + "'" : "");
		if (fc.isHasExpend()) {
			dataIsland.append("<value expend='min' " + display + ">");
			XMLDataIslandHelper.appendContent(dataIsland, value.getValue());
			dataIsland.append("</value>");
			dataIsland.append("<value expend='max' " + display + ">");
			XMLDataIslandHelper.appendContent(dataIsland, value.getValue());
			dataIsland.append("</value>");
		}
		else if (value instanceof MutiValue) {
			dataIsland.append("<value " + display + ">");
			Set<String> keys = MutiValueHelper.getChecked(fc.getBc()).keySet();
			String muti = "";
			for(String key : keys) {
				muti += key + ",";
			}
			muti = StringUtils.removeEnd(muti);
			XMLDataIslandHelper.appendContent(dataIsland, muti);
			dataIsland.append(muti);
			dataIsland.append("</value>");
		}
		else {
			dataIsland.append("<value " + display + ">");
			XMLDataIslandHelper.appendContent(dataIsland, value.getValue());
			dataIsland.append("</value>");
		}
	}

	/**
	 * 添加细粒度组件的状态
	 * @param dataIsland
	 * @param FinegrainedComponent
	 */
	private void appendStatus(StringBuilder dataIsland, FinegrainedComponent fc) {
		BOProperty bop = fc.getBc();
		if(bop != null){
			Status status = bop.getStatus();
			XMLDataIslandHelper.appendStartTag(dataIsland, STATUS);
			if(status.isHidden())
				XMLDataIslandHelper.appendAttr(dataIsland, STATUS_HIDDEN, "true");
			if(status.isReadonly())
				XMLDataIslandHelper.appendAttr(dataIsland, STATUS_READONLY, "true");
			if(status.isDisable())
				XMLDataIslandHelper.appendAttr(dataIsland, STATUS_DISABLE, "true");
			XMLDataIslandHelper.appendSingleEndTag(dataIsland);
		}
	}

	/**
	 * 添加细粒度组件的范围
	 * <br>
	 * @param dataIsland
	 * @param FinegrainedComponent
	 */
	private void appendRange(StringBuilder dataIsland, FinegrainedComponent fc) {
		BOProperty bop = fc.getBc();
		if(bop == null)
			return;

		BCRange bopRange = fc.isQueryRange() ? bop.getQueryRange() : bop.getRange();
		if(bopRange == null)
			return;

		XMLDataIslandHelper.appendStartTag(dataIsland, BOP_RANGE);
		if(bopRange.isRequired())
			XMLDataIslandHelper.appendAttr(dataIsland, BOP_RANGE_REQUIRED, "true");
		XMLDataIslandHelper.appendAttr(dataIsland, BOP_RANGE_MINLENGTH, bopRange.getMinLength() + "", bopRange.getMinLength() != -1);
		XMLDataIslandHelper.appendAttr(dataIsland, BOP_RANGE_MAXLENGTH, bopRange.getMaxLength() + "", bopRange.getMaxLength() != -1);
		XMLDataIslandHelper.appendEndTag(dataIsland);

		if(ContainerUtil.isNotNull(bopRange.getRangeList())){
			for (Range range : bopRange.getRangeList()) {
				if (range instanceof EnumRange)
					appendEnumRange(dataIsland, bopRange, range);
				else if (range instanceof SequenceRange)
					appendSequenceRange(dataIsland, bopRange, range);
				else if (range instanceof LogicRange)
					appendLogicRange(dataIsland, bopRange, range);
			}
		}

		XMLDataIslandHelper.appendEndTag(dataIsland, BOP_RANGE);
	}


	/**
	 * 添加枚举型范围
	 * @param dataIsland
	 * @param bopRange
	 * @param range
	 */
	private void appendEnumRange(StringBuilder dataIsland, BCRange bopRange, Range range) {
		dataIsland.append("<enum rule='").append(bopRange.rangeLogic(range)).append("'>");

		Map<String, String> result = ((EnumRange) range).getResult();
		for (String key : result.keySet()) {
			dataIsland.append("<item value='").append(key)
				.append("' label='").append(StringUtils.filter(result.get(key))).append("'/>");
		}

		dataIsland.append("</enum>");
	}

	/**
	 * 添加连续型范围
	 * @param dataIsland
	 * @param bopRange
	 * @param range
	 */
	private void appendSequenceRange(StringBuilder dataIsland, BCRange bopRange, Range range) {
		int scale = ((SequenceRange) range).getScale();
		dataIsland.append("<sequence rule='").append(bopRange.rangeLogic(range)).append("'>")
			.append("<min>").append(StringUtils.toString(((SequenceRange) range).getMin(), scale)).append("</min>")
			.append("<max>").append(StringUtils.toString(((SequenceRange) range).getMax(), scale)).append("</max>")
			.append("<step>").append(StringUtils.toString(((SequenceRange) range).getStep(), scale)).append("</step>")
			.append("<scale>").append(scale).append("</scale>");
		dataIsland.append("</sequence>");
	}

	/**
	 * 添加逻辑型范围
	 * @param dataIsland
	 * @param bopRange
	 * @param range
	 */
	private void appendLogicRange(StringBuilder dataIsland, BCRange bopRange, Range range) {
		dataIsland.append("<logic rule='")
			.append(bopRange.rangeLogic(range))
			.append("' rangeClass='")
			.append(range.getClass().getName())
			.append("' />");
	}

	@Override
	public String createTreeDataIsland(Tree tree) {
		if(tree == null)
			return null;

		StringBuilder sbr = new StringBuilder();
		XMLDataIslandHelper.appendStartTag(sbr, TREE);
		XMLDataIslandHelper.appendAttr(sbr, BO_ID, tree.getId());
		XMLDataIslandHelper.appendAttr(sbr, BIND, tree.getBcId());
		XMLDataIslandHelper.appendEndTag(sbr);

		if(tree.getBc() != null && ContainerUtil.isNotNull(tree.getBc().getTree())) {
			appendTreeBO(sbr, tree.getBc().getTree());
		}

		XMLDataIslandHelper.appendEndTag(sbr, TREE);

		return sbr.toString();
	}

	private void appendTreeBO(StringBuilder treeIsland, List<TreeBO> treeBOList) {
		for (TreeBO treeBO : treeBOList) {
			XMLDataIslandHelper.appendStartTag(treeIsland, TREE_BO);
			XMLDataIslandHelper.appendAttr(treeIsland, TREE_BO_ID, treeBO.getId());
			XMLDataIslandHelper.appendSingleEndTag(treeIsland);
		}
	}

	private void processXml(BusinessObject bo, Element boEl){
		for (Element bopEl : (List<Element>) boEl.getChildren(BOP)) {
			BOProperty bop = BoOperateUtil.getBOP(bo, bopEl.getAttributeValue(BIND));
			bopEl.getChild(BOP_VALUE).setText(XMLUtil.encode(bop.getValue().getValue()));
			if(bopEl.getChild(BOP_DATA) != null && bop instanceof FileBOP) {
				bopEl.getChild(BOP_DATA).setAttribute(BOP_DATA_URL, ((FileBOP)bop).getPath());
			}
		}
		
		for(Element operateEl : (List<Element>) boEl.getChildren(OPERATE)){
			String bopName = StringUtils.split(operateEl.getAttributeValue(OPERATE_ID), "-")[2];
			OperateBOP bop = bo.getOperateBOP(bopName);
			if(bop != null){
				if(bop.getStatus().isDisable())
					operateEl.setAttribute(STATUS_DISABLE, "true");
				if(bop.getStatus().isHidden())
					operateEl.setAttribute(STATUS_HIDDEN, "true");
			}
		}
	}
		
	@Override
	public String updateFCRelationDataIsland(String dataIsland,
			Map<String, Object> resultMap) {
		Document doc = XMLUtil.stringToXml(dataIsland);
		if(doc == null)
			return null;
		
		for(Entry<String, Object> entry : resultMap.entrySet()){
			if(entry.getValue() instanceof BusinessObject){
				updateDataIslandByBO(doc, (BusinessObject) entry.getValue());
			}
			else if(entry.getValue() instanceof ArrayList){
				//TODO 暂无业务需求
			}
			else if(entry.getValue() instanceof Page){
				//TODO 暂无业务需求
			}
		}
		return XMLUtil.xmlToString(doc);
	}

	private void updateDataIslandByBO(Document doc, BusinessObject bo) {
		List<Element> ELResult = doc.getRootElement().getChildren(BOP);
		for (Element bopEl : ELResult) {

			BOProperty bop = bo.getBOP(bopEl.getAttributeValue(BIND));
			if(bop == null)
				continue;

			bop = BoOperateUtil.getRealBop(bop);

			setValue(bopEl, bop);
			setStatus(bopEl, bop, true);
			setRange(bopEl, bop);
		}
	}

	@Override
	public BusinessObject revertRelationBO(String dataIsland) {
		Document doc = XMLUtil.stringToXml(dataIsland);
		Element rootElement = doc.getRootElement();
		BusinessObject bo = (BusinessObject) SpringConstant.getCTX().getBean(rootElement.getAttributeValue(BIND));
		
		for(Element bopEl : (List<Element>)rootElement.getChildren(BOP)){
			BOProperty bop = bo.getBOP(bopEl.getAttributeValue(BIND));
			setBOPByXml(bo, bop, bopEl);
		}
		
		for(Element optEl : (List<Element>)rootElement.getChildren(OPERATE)){
			setOperateBOPByXml(bo, optEl);
		}

		return bo;
	}

	/**
	 * 根据operate xml还原operatebop
	 * @param bo
	 * @param optEl
	 */
	private void setOperateBOPByXml(BusinessObject bo, Element optEl) {
		OperateBOP optBop = new OperateBOP();
		String optId = optEl.getAttributeValue(OPERATE_ID);
		String optName = VCUtil.getBopBind(optId);
		
		optBop.setOperate(optEl.getAttributeValue(OPERATE_METHOD));
		optBop.setConfirmMsg(optEl.getAttributeValue(OPERATE_CONFIRMMSG));
		optBop.setHasConfirm(StringUtils.convertToBool(optEl.getAttributeValue(OPERATE_HASCONFIRM)));
		optBop.setSaveMod(optEl.getAttributeValue(OPERATE_SAVEMOD));
		optBop.setSubmit(!StringUtils.convertToBool(optEl.getAttributeValue(OPERATE_NOTSUBMIT)));
		optBop.setFresh(!StringUtils.convertToBool(optEl.getAttributeValue(OPERATE_NOTFRESH)));
		
		optBop.getStatus().setHidden(StringUtils.convertToBool(optEl.getAttributeValue(STATUS_HIDDEN)));
		optBop.getStatus().setReadonly(StringUtils.convertToBool(optEl.getAttributeValue(STATUS_READONLY)));
		optBop.getStatus().setDisable(StringUtils.convertToBool(optEl.getAttributeValue(STATUS_DISABLE)));
		
		bo.addOperateBOP(optName, optBop);
	}
	
	/**
	 * 根据Element值设置BOP的值、状态、范围
	 */
	private void setBOPByXml(BusinessObject bo, BOProperty bop, Element bopEl) {
		String bopName = bopEl.getAttributeValue(BIND);
		String bopValue = "";
		Element data = bopEl.getChild(BOP_DATA);
		
		if(bopEl.getChild(BOP_VALUE) != null)
			bopValue = XMLUtil.decode(bopEl.getChildText(BOP_VALUE));
		else
			bopValue = XMLUtil.decode(bopEl.getText());

		if(bo != null){
			try {
				//将bopName中所有"#"字符转换为"."
				bopName = bopName.replaceAll("#", "\\.");
				BoOperateUtil.setBOFieldValue(bo, bopName, bopValue);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//上传文件控件bop设置
		if(data != null || bopEl.getAttribute(BOP_DATA_URL) != null){
			FileBOP sbop = FileBopUtil.getFileBop(bop);
			MultiFileBOP mbop = FileBopUtil.getMultiFileBOP(bop);
			//上传文件路径
			String url = data == null ? 
						bopEl.getAttributeValue(BOP_DATA_URL) : 
						data.getAttributeValue(BOP_DATA_URL);
			//单文件上传
			if(sbop != null){
				if(StringUtils.isNotEmptyStr(url)){
					sbop.setDisplayName(bopValue);
					sbop.setPath(url);
				}
				sbop.setValue(bopValue);
				bop = sbop;
			}
			//多文件上传
			else if(mbop != null){
				if(StringUtils.isNotEmptyStr(url)){
					String[] urls = url.split(ConstantSplit.TREBLE_BACKSLASH_SPLIT);
					String[] names = bopValue.split(ConstantSplit.TREBLE_BACKSLASH_SPLIT);
					
					//当超链接控件绑定多附件bop时作此操作
					if(urls.length != names.length) {
						names = bopValue.split(" ");
					}
					
					for(int i = 0; i < urls.length; i++){
						if(StringUtils.isEmptyStr(urls[i]))
							continue;
						FileBOP fileBop = mbop.getItemBopObject();
						fileBop.setDisplayName(names[i]);
						fileBop.setPath(urls[i]);
						mbop.getFiles().add(fileBop);
					}
				}
				mbop.setValue(bopValue);
				bop = mbop;
			}
			try {
				if(sbop != null)
					BoOperateUtil.setBOFieldValue(bo, bopName, bop);
				else if(mbop != null)
					BoOperateUtil.setBOFieldValue(bo, bopName, mbop);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if(bop != null){
			bop.setValue(bopValue);
			setStatus(bopEl, bop, false);
		}
	}


	private void setValue(Element bopEl, BOProperty bop) {
		Element bopValue = bopEl.getChild(BOP_VALUE);
		if(bopValue != null)
			bopValue.setText(XMLUtil.encode(bop.getValue().getValue()));
		else
			bopEl.setText(XMLUtil.encode(bop.getValue().getValue()));
	}

	private void setStatus(Element bopEl, BOProperty bop, boolean flag){
		if(flag){
			Element status = bopEl.getChild(STATUS);
			if(status == null) {
				if(bop.getStatus().isDisable())
					bopEl.setAttribute(STATUS_DISABLE, "true");
				else 
					bopEl.removeAttribute(STATUS_DISABLE);
			}
			else {
				if(bop.getStatus().isHidden())
					status.setAttribute(STATUS_HIDDEN, "true");
				else 
					status.removeAttribute(STATUS_HIDDEN);
				
				if(bop.getStatus().isReadonly())
					status.setAttribute(STATUS_READONLY, "true");
				else 
					status.removeAttribute(STATUS_READONLY);
				
				if(bop.getStatus().isDisable())
					status.setAttribute(STATUS_DISABLE, "true");
				else
					status.removeAttribute(STATUS_DISABLE);
			}
		}
		else {
			Element statusEl = bopEl.getChild(STATUS);
			if(statusEl != null){
				bop.getStatus().setHidden(StringUtils.convertToBool(statusEl.getAttributeValue(STATUS_HIDDEN)));
				bop.getStatus().setDisable(StringUtils.convertToBool(statusEl.getAttributeValue(STATUS_DISABLE)));
				bop.getStatus().setReadonly(StringUtils.convertToBool(statusEl.getAttributeValue(STATUS_READONLY)));
			}
		}
	}
	
	private void setRange(Element bopEl,BOProperty bop){
		Element bopRange = bopEl.getChild(BOP_RANGE);
		if(bopRange == null)
			return;
		
		if(bop.getRange().isRequired())
			bopRange.setAttribute(BOP_RANGE_REQUIRED, "true");
		else
			bopRange.removeAttribute(BOP_RANGE_REQUIRED);

		List<Element> enumList = bopRange.getChildren(BOP_RANGE_ENUM);
		for(Range range:bop.getRange().getRangeList()){
			if(!(range instanceof EnumRange))
				continue;

			Map<String, String> enumResult  = ((EnumRange)range).getResult();
			if(enumList.isEmpty()) {
				Element enumm = new Element(BOP_RANGE_ENUM);
				addEnumContent(enumResult, enumm);
				enumList.add(enumm);
			}
			else {
				for (Element enumm:enumList) {
					enumm.removeChildren(BOP_RANGE_ITEM);
					addEnumContent(enumResult, enumm);
				}
			}
		}
	}

	private void addEnumContent(Map<String, String> enumResult, Element enumm) {
		Element newBopEl;
		for(String enumKey:enumResult.keySet()){
			newBopEl = new Element(BOP_RANGE_ITEM);
			newBopEl.setAttribute(BOP_VALUE,enumKey);
			newBopEl.setAttribute(BOP_LABEL,enumResult.get(enumKey));
			enumm.addContent(newBopEl);
		}
	}

	@Override
	public void revertBop(String dataisland_boId, String dataIsland, BusinessObject bo) {
		if(StringUtils.isEmpty(dataisland_boId))
			return;
		Document doc = XMLUtil.stringToXml(dataIsland);
		if(doc == null)
			return;

		Element root = doc.getRootElement();
		Element tableEl = getContainerElement(dataisland_boId, root, BOLIST);
		if(tableEl != null) {
			Element tableColumnsEl = tableEl.getChild(COLUMNINFO);
			if(tableColumnsEl == null)
				return;
			
			List<Element> tableColEls = tableColumnsEl.getChildren(COLUMN);
			for (Element columnEl : tableColEls) {
				String bopBind = columnEl.getAttributeValue(BIND);
				bo.pushDisplayBopName(bopBind);
			}
			if(StringUtils.isNotEmpty(tableEl.getAttributeValue(BOLIST_PAGESIZE)))
				bo.setPageSize(StringUtils.convertToInt(tableEl.getAttributeValue(BOLIST_PAGESIZE)));
			return;
		}
		Element formEl = getContainerElement(dataisland_boId, root, BO);
		if(formEl != null){
			for (Element bopEl : (List<Element>) formEl.getChildren(BOP)) {
				String bopBind = bopEl.getAttributeValue(BIND);
				bo.pushDisplayBopName(bopBind);
			}
			return;
		}
	}

	/**
	 * 设定表格中bop的值
	 * @param bopEl
	 * @param targetBOP
	 */
	private void setTableValue(Element bopEl, BOProperty targetBOP) {
		if(targetBOP.getValue() == null)
			return;
		bopEl.setText(targetBOP.getValue().getValue());		
	}

	/**
	 * 设定表格中bop的状态，在表格中仅disable状态有效
	 * @param bopEl
	 * @param targetBOP
	 */
	private void setTableBOPStatus(Element bopEl, BOProperty targetBOP) {
		if(targetBOP.getStatus() == null)
			return;
		Attribute disable = new Attribute(STATUS_DISABLE, String.valueOf(targetBOP.getStatus().isDisable()));
		bopEl.setAttribute(disable);
	}

	/**
	 * 设定表格中bop的范围
	 * @param bopEl
	 * @param bop
	 */
	private void setTableBOPRange(Element bopEl, BOProperty bop){
		String items = getTableBOPItems(bop);
		
		Attribute range = bopEl.getAttribute(BOP_RANGE_ENUM);
		if(StringUtils.isEmpty(items)){
			bopEl.removeAttribute(BOP_RANGE_ENUM);
			return;
		}
		if(range == null){
			range = new Attribute(BOP_RANGE_ENUM, items);
			bopEl.setAttribute(range);
		}
		else {
			range.setValue(items);
		}
	}

	/**
	 * 组装选项
	 * @param bop
	 * @return
	 */
	private String getTableBOPItems(BOProperty bop) {
		StringBuilder rangeStr = new StringBuilder();
		for(Range range : bop.getRange().getRangeList()){
			if(!(range instanceof EnumRange))
				continue;
			Map<String, String> enumResult  = ((EnumRange)range).getResult();
			boolean flag = false;
			if(ContainerUtil.isNull(enumResult))
				continue;
			for(Entry<String, String> entry : enumResult.entrySet()){
				if(flag)
					rangeStr.append(",");
				rangeStr.append("{&quot;k&quot;:&quot;");
				rangeStr.append(entry.getKey());
				rangeStr.append("&quot;,&quot;v&quot;:&quot;");
				rangeStr.append(entry.getValue());
				rangeStr.append("&quot;}");
				flag = true;
			}
		}
		return StringUtils.isEmpty(rangeStr.toString())? null : "[" + rangeStr.toString() + "]";
	}

	@Override
	public String updateContainer(String dataIsland, String boId, 
			String boBind, Object result) {
		Document doc = XMLUtil.stringToXml(dataIsland);
		if(doc == null || result == null)
			return "";

		Element rootElement = doc.getRootElement();
		
		Element formEl = getContainerElement(boId, rootElement, BO);
		if(formEl != null){
			BusinessObject formBo;
			if (result instanceof BusinessObject) 
				formBo = (BusinessObject) result;
			else if(result instanceof Page)
				formBo = ((Page) result).getBOList().get(0);
			else
				formBo = BOManager.getBOInstance(boBind);
			
			updateFormElement(formBo, boBind, rootElement);
		}
		Element tableEl = getContainerElement(boId, rootElement, BOLIST);
		if(tableEl != null){
			//TODO 
		}
		

		return XMLUtil.xmlToString(doc);
	}
	
	@Override
	public String updatePageOperate(String dataIsland, String boBind, BusinessObject bo) {
		Document doc = XMLUtil.stringToXml(dataIsland);
		if(doc == null || bo == null)
			return "";

		Element rootElement = doc.getRootElement();
		List<Element> optList = rootElement.getChildren(OPERATE);
		if(ContainerUtil.isNotNull(optList)) {
			for(Element operateEl : (List<Element>) rootElement.getChildren(OPERATE)){
				String bopName = StringUtils.split(operateEl.getAttributeValue(OPERATE_ID), "-")[1];
				OperateBOP bop = bo.getOperateBOP(bopName);
				if(bop != null){
					if(bop.getStatus().isDisable())
						operateEl.setAttribute(STATUS_DISABLE, "true");
					if(bop.getStatus().isHidden())
						operateEl.setAttribute(STATUS_HIDDEN, "true");
					if(bop.getStatus().isReadonly())
						operateEl.setAttribute(STATUS_READONLY, "true");
				}
			}
		}
		
		return XMLUtil.xmlToString(doc);
	}
}
