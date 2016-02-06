package com.qeweb.framework.pl.ext;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.pageflow.ContextUtil;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.MathUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.manager.DisplayType;
import com.qeweb.framework.pal.Page;
import com.qeweb.framework.pal.PageContextInfo;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.coarsegrained.Tab;
import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pal.layout.others.Layout;
import com.qeweb.framework.pal.layout.others.LayoutFactory;
import com.qeweb.framework.pal.layout.others.interpreter.Cell;
import com.qeweb.framework.pl.JSTemplate;
import com.qeweb.framework.pl.ext.coarsegrained.ExtContainerHelper;

public class ExtPage extends Page {
	
	@Override
	protected void paintTitle() {
		PageContextInfo out = getPageContextInfo();
		out.write("<title>");
		out.write(getTitle());
		out.write("</title>");
	}

	@Override
	protected void paintBodyStart() {
		PageContextInfo out = getPageContextInfo();
		out.write("<body>");
//		out.write("<div id='extMain'></div>");
		out.write(JSTemplate.getJsHead());
		//粗粒度组件
		out.write("var itemArray = [];");
		//formPanelArr和recordWinArr是table组件的弹出框(新增/修改/查看弹出框),formPanel包含在recordWin中;
		//formDislandlArr是其对应的数据岛;
		//其中recordWinArr和formPanelArr的下标是按钮的id, 增/改/查看可公用同一个数据岛, 故formDislandlArr的下标是tableId
		out.write("var recordWinArr = [];");
		out.write("var formPanelArr = [];");
		out.write("var formDislandlArr = [];");
		out.write("var observableArr = [];");
		out.write("Ext.onReady(function() {");
		out.write("Ext.QuickTips.init();");
		out.write("Ext.form.Field.prototype.msgTarget = '" + DisplayType.getMsgTarget() + "';");
		out.write("Ext.apply(Ext.QuickTips.getQuickTip(), {maxWidth: 500,minWidth: 100,showDelay: 120,trackMouse: true});");
	}
	
	/**
	 * 页面加载时的提示信息, 通常可以用来实现消息提醒功能.
	 * @param title			提示消息标题
	 * @param content		提示消息内容
	 *  new Ext.ux.TipWindow({
			title : '<span class=commoncss>提示</span>',
			html : '您有[0]条未读信息',
			iconCls : 'commentsIcon'
		}).show(Ext.getBody());
	 */
	@Override
	protected void paintTipMessage(String title, String content) {
		if(StringUtils.isEmpty(content))
			return;
		
		if(StringUtils.isEmpty(title))
			title = AppLocalization.getLocalization("tipMessage");
		
		StringBuilder sbr = new StringBuilder();
		sbr.append("new Ext.ux.TipWindow({");
		ExtWebHelper.appendAttr(sbr, "title", "<span class=commoncss>" + title + "</span>");
		ExtWebHelper.appendAttr(sbr, "html", content);
		ExtWebHelper.appendTail(sbr, "iconCls", "commentsIcon");
		sbr.append("}).show(Ext.getBody());");
		getPageContextInfo().write(sbr.toString());
	}

	@Override
	protected void patinBodyEnd() {
		PageContextInfo out = getPageContextInfo();
        out.write("if(itemArray.length >= 1){");
        paintContainerLayout();
        out.write("}");
        out.write("addDomListener();"); //事件注册
        out.write("showReturnMsg();"); 	//弹出操作成功提示信息
		String errorMsg = ContextUtil.getTipMsg();
		if(StringUtils.isNotEmpty(errorMsg)){
			out.write("ResultMessage.showErrMsg('");
			out.write(errorMsg);
			out.write("');");
		}	
		out.write("});");
		out.write(JSTemplate.getJsEnd());
	}
	

	@Override
	protected void paintHeadButton() {
		paintButton(getHeadBtnList(), "pageHeadBtnPanel");
	}
	
	@Override
	protected void paintFootButton() {
		paintButton(getFootBtnList(), "pageFootBtnPanel");
	}

	/**
	 * 画出粗粒度组件关联
	 * <p>
	 * 	relations="form:table,table2;table:table2"
	 * 
	 * 	observableArr['form'] = new ContainerObservable();
		observableArr['form'].containerName = 'form-demoConRelationBO3';
		observableArr['form'].addObserver(new ContainerObserver(), 'table');
		observableArr['form'].addObserver(new ContainerObserver(), 'table2');
					
		observableArr['table'] = new ContainerObservable();
		observableArr['table'].containerName = 'table-demoConRelationBO3';
		observableArr['table'].addObserver(new ContainerObserver(), 'table2');
	 */
	@Override
	protected void paintContainerRelation() {
		Map<String, List<String>> relationGroupMap = getContainerRelationGroup().getRelationGroupMap();
		if(ContainerUtil.isNull(relationGroupMap))
			return;
		
		Map<String, Container> containerMap = getContainerMap();
		StringBuilder sbr = new StringBuilder();
		for(Entry<String, List<String>> entry : relationGroupMap.entrySet()){
			if(!containerMap.containsKey(entry.getKey()))
				continue;
			
			sbr.append("observableArr['").append(entry.getKey()).append("']=new ContainerObservable();");
			sbr.append("observableArr['").append(entry.getKey()).append("'].containerName='")
										.append(containerMap.get(entry.getKey()).getName()).append("';");
			this.addObservers(sbr, containerMap, entry.getKey(), entry.getValue());
		}
		getPageContextInfo().write(sbr.toString());
	}
	
	/**
	 * new Ext.Viewport({
			layout : 'fit',
			columns : 3,	
			items : [ {
				border : false,
				autoScroll : true,
				overflow : 'auto',
				bodyStyle : 'overflow-x:hidden;overflow-y:auto;',
				layout : 'column',
				items : [
					{
						colspan : 1,
						columnWidth : 0.33,
						border : false,
						items : [{
					       layout:'fit',
					       items:[itemArray[0]]
					    }]
					},
					{
						colspan : 2,
						columnWidth : 0.66,
						border : false,
						items : [{
					       layout:'fit',
					       items:[itemArray[1]]
					    }]
					},
					{
						colspan : 1,
						columnWidth : 0.33,
						border : false,
						items : [{
					       layout:'fit',
					       items:[itemArray[2]]
					    }]
					},
					{
						colspan : 2,
						columnWidth : 0.66,
						border : false,
						items : [{
					       layout:'fit',
					       items:[itemArray[3]]
					    }]
					}  
				]}],
											
				listeners : {'resize':function(){resizeContainer(this);}}
			});
	 */
	private void paintContainerLayout() {
		PageContextInfo out = getPageContextInfo();

		Layout layout = LayoutFactory.createLayout(getLayoutStr(), LayoutFactory.LAYOUTTYPE_CONTAINER);
		Map<String, Cell> cellMap = layout.getCellMap();
		int columns = layout.getColumns();
		
		StringBuilder sbr = new StringBuilder();
		sbr.append("new Ext.Viewport({");
		ExtWebHelper.appendAttr(sbr, "layout", "fit");
		ExtWebHelper.appendAttr(sbr, "columns", columns);
		sbr.append("items : [{");
		ExtWebHelper.appendAttr(sbr, "border", false);
		ExtWebHelper.appendAttr(sbr, "autoScroll", true);
		ExtWebHelper.appendAttr(sbr, "overflow", "auto");
		ExtWebHelper.appendAttr(sbr, "bodyStyle", "overflow-x:hidden;overflow-y:auto;");
		ExtWebHelper.appendAttr(sbr, "layout", "column");
		sbr.append("items : [");
		
		//是否有页面顶端的全局按钮栏
		int headPageBtnPanel = ContainerUtil.isNull(getHeadBtnList()) ? 0 : 1;
		if(headPageBtnPanel == 1){
			appendItem(sbr, columns, null, 0, columns);
			sbr.append(",");
		}

		int collSpanSum = 0;
		for(int i = 0, len = getContainerList().size(); i < len; i++){
			if(i != 0)
				sbr.append(",");
			//获取粗粒度组件ID
			String containerId = getContainerList().get(i).getId();

			//前几个组件（这几个组件在同一行）所占的单元格数
			int lastCollSpan = collSpanSum;
			//合并的单元格数
			int thisCollSpan = 0;

			if(cellMap.containsKey(containerId)) {
				collSpanSum += cellMap.get(containerId).getCollSpan();
				thisCollSpan = cellMap.get(containerId).getCollSpan();
			}
			else {
				collSpanSum += 1;
				thisCollSpan = 1;
			}

			if(lastCollSpan + thisCollSpan == columns) {
				collSpanSum = 0;
				lastCollSpan = 0;
			}
			//填充空白单元格
			if(lastCollSpan + thisCollSpan > columns) {
				appendItem(sbr, Math.abs(columns - lastCollSpan), null, null, columns);
				ExtWebHelper.appendObjectSplit(sbr);
				lastCollSpan = 0;
				collSpanSum = 0;
			}
			Integer colspan = 1,rowspan = 1;

			if(cellMap.containsKey(containerId)) {
				colspan = cellMap.get(containerId).getCollSpan();
				rowspan = cellMap.get(containerId).getRowSpan();
			}
			appendItem(sbr, colspan, rowspan, i + headPageBtnPanel, columns);
		}
		
		//是否有页面底端的全局按钮栏
		if(ContainerUtil.isNotNull(getFootBtnList())){
			sbr.append(",");
			appendItem(sbr, columns, null, headPageBtnPanel + getContainerList().size(), columns);
		}
		
		sbr.append("]}],");
		sbr.append("listeners : {'resize':function(){resizeContainer(this,").append(columns).append(");}}");
		sbr.append("});");


		out.write(sbr.toString());
	}

//下列注释的代码可以在jsp中嵌入非开发平台的html, 但是在页面流弹出框中, 组件无法自适应, 需要查找该原因
//	/**
//	 * new Ext.Panel({
//	 * 		renderTo : 'extMain',
//			columns : 3,	
//			items : [ {
//				border : false,
//				autoScroll : true,
//				overflow : 'auto',
//				bodyStyle : 'overflow-x:hidden;overflow-y:auto;',
//				layout : 'column',
//				items : [
//					{
//						colspan : 1,
//						columnWidth : 0.33,
//						border : false,
//						items : [{
//					       layout:'fit',
//					       items:[itemArray[0]]
//					    }]
//					},
//					{
//						colspan : 2,
//						columnWidth : 0.66,
//						border : false,
//						items : [{
//					       layout:'fit',
//					       items:[itemArray[1]]
//					    }]
//					},
//					{
//						colspan : 1,
//						columnWidth : 0.33,
//						border : false,
//						items : [{
//					       layout:'fit',
//					       items:[itemArray[2]]
//					    }]
//					},
//					{
//						colspan : 2,
//						columnWidth : 0.66,
//						border : false,
//						items : [{
//					       layout:'fit',
//					       items:[itemArray[3]]
//					    }]
//					}  
//				]}]
//			});
//	 */
//	private void paintContainerLayout() {
//		PageContextInfo out = getPageContextInfo();
//
//		Layout layout = LayoutFactory.createLayout(getLayoutStr(), LayoutFactory.LAYOUTTYPE_CONTAINER);
//		Map<String, Cell> cellMap = layout.getCellMap();
//		int columns = layout.getColumns();
//		
//		StringBuilder sbr = new StringBuilder();
//		sbr.append("new Ext.Panel({");
//		ExtWebHelper.appendAttr(sbr, "renderTo", "extMain");
//		ExtWebHelper.appendAttr(sbr, "columns", columns);
//		sbr.append("items : [{");
//		ExtWebHelper.appendAttr(sbr, "border", false);
//		ExtWebHelper.appendAttr(sbr, "autoScroll", true);
//		ExtWebHelper.appendAttr(sbr, "overflow", "auto");
//		ExtWebHelper.appendAttr(sbr, "bodyStyle", "overflow-x:hidden;overflow-y:auto;");
//		ExtWebHelper.appendAttr(sbr, "layout", "column");
//		sbr.append("items : [");
//		
//		//是否有页面顶端的全局按钮栏
//		int headPageBtnPanel = ContainerUtil.isNull(getHeadBtnList()) ? 0 : 1;
//		if(headPageBtnPanel == 1){
//			appendItem(sbr, columns, null, 0, columns);
//			sbr.append(",");
//		}
//
//		int collSpanSum = 0;
//		for(int i = 0, len = getContainerList().size(); i < len; i++){
//			if(i != 0)
//				sbr.append(",");
//			//获取粗粒度组件ID
//			String containerId = getContainerList().get(i).getId();
//
//			//前几个组件（这几个组件在同一行）所占的单元格数
//			int lastCollSpan = collSpanSum;
//			//合并的单元格数
//			int thisCollSpan = 0;
//
//			if(cellMap.containsKey(containerId)) {
//				collSpanSum += cellMap.get(containerId).getCollSpan();
//				thisCollSpan = cellMap.get(containerId).getCollSpan();
//			}
//			else {
//				collSpanSum += 1;
//				thisCollSpan = 1;
//			}
//
//			if(lastCollSpan + thisCollSpan == columns) {
//				collSpanSum = 0;
//				lastCollSpan = 0;
//			}
//			//填充空白单元格
//			if(lastCollSpan + thisCollSpan > columns) {
//				appendItem(sbr, Math.abs(columns - lastCollSpan), null, null, columns);
//				ExtWebHelper.appendObjectSplit(sbr);
//				lastCollSpan = 0;
//				collSpanSum = 0;
//			}
//			Integer colspan = 1,rowspan = 1;
//
//			if(cellMap.containsKey(containerId)) {
//				colspan = cellMap.get(containerId).getCollSpan();
//				rowspan = cellMap.get(containerId).getRowSpan();
//			}
//			appendItem(sbr, colspan, rowspan, i + headPageBtnPanel, columns);
//		}
//		
//		//是否有页面底端的全局按钮栏
//		if(ContainerUtil.isNotNull(getFootBtnList())){
//			sbr.append(",");
//			appendItem(sbr, columns, null, headPageBtnPanel + getContainerList().size(), columns);
//		}
//		
//		sbr.append("]}]");
//		sbr.append("});");
//
//
//		out.write(sbr.toString());
//	}
	
	/**
	 * 添加布局的组件(粗粒度,按钮)
	 * {
	 * 		colspan : 1,
			columnWidth : 0.33,
			border : false,
			bodyStyle : padding:0 5px 5px 0,
			items : [items:[itemArray[0]]]
		}
	 * @param sbr
	 * @param colspan 占列数
	 * @param rowspan 占行数
	 * @param arrayIndex 数组位置
	 * @param columns 整体列数
	 * @return
	 */
	private String appendItem(StringBuilder sbr, Integer colspan, Integer rowspan, Integer arrayIndex, int columns){
		ExtWebHelper.appendObjectBegin(sbr);
		ExtWebHelper.appendAttr(sbr, "colspan", colspan);
		ExtWebHelper.appendAttr(sbr, "columnWidth", MathUtil.divDown(1, columns, 2) * colspan);
		ExtWebHelper.appendAttr(sbr, "border", false);
		ExtWebHelper.appendAttr(sbr, "bodyStyle", "padding:0 5px 5px 0");
		ExtWebHelper.appendObjectTail(sbr, "items", "[itemArray[" + arrayIndex + "]]");
		ExtWebHelper.appendObjectAfter(sbr);

		return sbr.toString();
	}
	
	/**
	 * @param btnList
	 * @param btnPanelName
	 * var pageBtnPanel = new Ext.Panel({
			border : false,
			buttonAlign : 'right',
			buttons : [new Ext.Button({
							id : 'abc',
						    text:'toggle button '
						}),new Ext.Button({
							id : 'aaa',
						    text:'toggle button '
						})]
		});

		itemArray.push(pageBtnPanel);
	 */
	private void paintButton(List<CommandButton> btnList, String btnPanelName) {
		if(ContainerUtil.isNull(btnList))
			return;

		StringBuilder sbr = new StringBuilder();
		ExtWebHelper.appendCreateObjectBegin(sbr, btnPanelName, "Ext.Panel");
		ExtWebHelper.appendObjectBegin(sbr);
		ExtWebHelper.appendAttr(sbr, "style", "padding-right: 20px;");
		ExtWebHelper.appendAttr(sbr, "border", false);
		ExtWebHelper.appendAttr(sbr, "buttonAlign", "right");
		getPageContextInfo().write(sbr.toString());

		ExtContainerHelper.paintButtons(btnList, getPageContextInfo());

		sbr = new StringBuilder();
		ExtWebHelper.appendObjectAfter(sbr);
		ExtWebHelper.appendCreateObjectEnd(sbr);
		ExtWebHelper.appendSingleEnd(sbr);
		sbr.append("itemArray.push(").append(btnPanelName).append(");");

		getPageContextInfo().write(sbr.toString());
	}

	private void addObservers(StringBuilder sbr,
			Map<String, Container> containerMap, String sourceContainerId, List<String> relationIds) {
		Container sourceContainer = containerMap.get(sourceContainerId);
		Set<String> boClassNameSet = getBOClassNameSet(sourceContainer.getBc().getRelations());
		for(String conId : relationIds) {
			String[] arr = StringUtils.split(conId, ",");
			for(String containerId : arr) {
				//页面中不存在该粗粒度组件
				if(!containerMap.containsKey(containerId))
					continue;
				
				Container relationContainer = containerMap.get(containerId);
				//该粗粒度组件对应的bo与源bo并无关联关系
				if(isNotRelation(sourceContainer, relationContainer, boClassNameSet))
					continue;
				sbr.append("observableArr['").append(sourceContainerId).append("'].addObserver(new ContainerObserver(),'").append(containerId).append("');");
			}
		}
	}
	
	private boolean isNotRelation(Container sourceContainer,
			Container relationContainer, Set<String> boClassNameSet) {
		String sourceClassName = BOHelper.getRealClass(sourceContainer.getBc()).getName();
		String relationClassName = BOHelper.getRealClass(relationContainer.getBc()).getName();
		return StringUtils.isNotEqual(sourceClassName, relationClassName)
				&& !boClassNameSet.contains(relationClassName);
	}

	private Set<String> getBOClassNameSet(List<BusinessComponent> relations) {
		Set<String> boClassNameSet = new LinkedHashSet<String>();
		if(ContainerUtil.isNull(relations))
			return boClassNameSet;			
		for(BusinessComponent bc : relations){
			boClassNameSet.add(BOHelper.getRealClass(bc).getName());
		}
		return boClassNameSet;
	}

	/**
	 * 获取page中除粗粒度组件, 如果某个粗粒度组件在tab中, 则将tab中的粗粒度组件计入结果集.
	 * 结果集供添加粗粒度组件关联使用.
	 * @return 
	 */
	private Map<String, Container> getContainerMap(){
		Map<String, Container> containerMap = new LinkedHashMap<String, Container>();
		pushContainer(getContainerList(), containerMap);
		
		for(Container container : getContainerList()){
			if(container instanceof Tab) {
				pushContainer(((Tab) container).getContainerList(), containerMap);
			}
		}
		return containerMap;
	}
	
	/**
	 * 将containerList放入containerMap中
	 * @param containerList 除Tab外的粗粒度组件
	 * @param containerMap
	 */
	private void pushContainer(List<Container> containerList, Map<String, Container> containerMap) {
		for(Container container : containerList){
			containerMap.put(container.getId(), container);
		}
	}
}
