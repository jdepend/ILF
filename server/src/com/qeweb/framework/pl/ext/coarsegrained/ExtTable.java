package com.qeweb.framework.pl.ext.coarsegrained;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.sysbop.DateBOP;
import com.qeweb.framework.bc.sysbop.ImageBOP;
import com.qeweb.framework.bc.sysbop.StatusBOP;
import com.qeweb.framework.bc.sysbop.mobilebop.LocationBOP;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.constant.ConstantAppProp;
import com.qeweb.framework.common.constant.ConstantBOMethod;
import com.qeweb.framework.common.constant.ConstantJSON;
import com.qeweb.framework.common.constant.ConstantParam;
import com.qeweb.framework.common.constant.ConstantSplit;
import com.qeweb.framework.common.constant.ConstantURL;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.syssetting.tablesetting.bo.TableSettingBO;
import com.qeweb.framework.common.utils.BoOperateUtil;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.VCUtil;
import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.manager.DisplayType;
import com.qeweb.framework.pal.PageContextInfo;
import com.qeweb.framework.pal.coarsegrained.Tab;
import com.qeweb.framework.pal.coarsegrained.Table;
import com.qeweb.framework.pal.coarsegrained.tableWindow.WindowSize;
import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.finegrained.enumcomp.CheckBox;
import com.qeweb.framework.pal.finegrained.enumcomp.Select;
import com.qeweb.framework.pal.finegrained.other.Anchor;
import com.qeweb.framework.pal.finegrained.other.DateField;
import com.qeweb.framework.pal.finegrained.other.FileField;
import com.qeweb.framework.pal.finegrained.other.Image;
import com.qeweb.framework.pal.finegrained.text.Hidden;
import com.qeweb.framework.pal.finegrained.text.TextArea;
import com.qeweb.framework.pal.layout.others.Layout;
import com.qeweb.framework.pal.layout.others.LayoutFactory;
import com.qeweb.framework.pal.layout.others.interpreter.Cell;
import com.qeweb.framework.pal.layout.table.TableLayout;
import com.qeweb.framework.pal.layout.table.interpreter.Header;
import com.qeweb.framework.pl.PLWebHelper;
import com.qeweb.framework.pl.ext.ExtWebHelper;

/**
 * Ext Table
 */
public class ExtTable extends Table {
	@Override
	public void paint() {
		TableLayout layout = new TableLayout(getLayoutStr());
		layout.interpret();
		
		if(hasGroupHeader()) {
			paintHeaderGroup(layout);
		}
		
		paintStore();
		if(isHasBbar())
			paintBbar();
		paintGrid(layout);
		paintReceordWin();
	}

	/**
	 * 是否有表头布局
	 * @return
	 */
	private boolean hasGroupHeader() {
		return isHeader() && isEmptyMod() && StringUtils.isNotEmpty(getLayoutStr());
	}
	
	/**
	 * 表头分组
	 * @param layout
	 * 
	 * var tableId_header = [];
	 * var tableId_header_header = [];
	 * var tableId_header_header_header = [];
	 * 
	 * tableId_header = [{header: 'top', colspan: 10, align: 'center'}];
	 * tableId_header_header =
       [
          {header: 'Asia', colspan: 4, align: 'center'},
          {header: 'Europe', colspan: 6, align: 'center'}
       ];
       tableId_header_header_header =
       [
          {header: 'Beijing', colspan: 2, align: 'center'},
          {header: 'Tokyo', colspan: 2, align: 'center'},
          {header: 'Berlin', colspan: 2, align: 'center'},
          {header: 'London', colspan: 2, align: 'center'},
          {header: 'Paris', colspan: 2, align: 'center'}
       ];
	   var tableId_group = new Ext.ux.grid.ColumnHeaderGroup({
          rows: [tableId_header, tableId_header_header, tableId_header_header_header]
       });
	 */
	private void paintHeaderGroup(TableLayout layout) {
		Set<Header> headerSet = layout.getHeaderSet();
		if(ContainerUtil.isNull(headerSet))
			return;
				
		List<String> headerGroupNames = getHeaderGroupArrName(headerSet);
		paintHeaderGroupArr(headerGroupNames);
		paintHeaderGroup(headerSet, 1);
		paintHeaderGroupClumn(headerGroupNames);
	}
	
	/**
	 * 获取表头分组的变量名称
	 * @param headerSet
	 * @return
	 */
	private List<String> getHeaderGroupArrName(Set<Header> headerSet) {
		//分组的高度
		int height = 1;
		for (Header header : headerSet) {
			int temp = header.getHeight();
			if(height < temp)
				height = temp;
		}
		
		List<String> headerGroupNames = new LinkedList<String>();
		for(int i = 1; i <= height; i++) {
			headerGroupNames.add(getHeaderName(i));
		}
		
		return headerGroupNames;
	}
	
	/**
	 * paint表头分组的变量名称
	 * var tableId_header = [];
	 * var tableId_header_header = [];
	 * var tableId_header_header_header = [];
	 * @param headerGroupNames
	 */
	private void paintHeaderGroupArr(List<String> headerGroupNames) {
		StringBuilder sbr = new StringBuilder();
		for (String headerGroupName : headerGroupNames) {
			sbr.append("var ").append(headerGroupName).append(" = [];");
		}
		getPageContextInfo().write(sbr.toString());
	}
	
	/**
	 * paint 表头分组
	 * @param headerSet
	 * @param height		分组的高度
	 */
	private void paintHeaderGroup(Set<Header> headerSet, int height) {
		if(ContainerUtil.isNull(headerSet))
			return;
		
		StringBuilder sbr = new StringBuilder();
		String headerName = getHeaderName(height);
		
		/*
			tableId_header.push({header: 'Asia', colspan: 4, align: 'center'});
			tableId_header.push({header: 'Europe', colspan: 6, align: 'center'});
		 */
		for (Header header : headerSet) {
			sbr.append(headerName).append(".push({header:'").append(header.getTitle()).append("',");
			sbr.append("colspan:").append(header.getColCount()).append(",align:'center'});");
			paintHeaderGroup(header.getHeaderSet(), header.getHeight());
		}
		
		getPageContextInfo().write(sbr.toString());
	}
	
	/**
	 * paint表格分组插件
	 *  var tableId_group = new Ext.ux.grid.ColumnHeaderGroup({
          rows: [tableId_header, tableId_header_header, tableId_header_header_header]
       });
	 * @param headerGroupNames
	 */
	private void paintHeaderGroupClumn(List<String> headerGroupNames) {
		StringBuilder sbr = new StringBuilder();
		sbr.append("var ").append(getGroupName()).append(" = new Ext.ux.grid.ColumnHeaderGroup({ rows: [");
		
		StringBuilder rows = new StringBuilder();
		for (String headerGroupName : headerGroupNames) {
			rows.append(headerGroupName).append(",");
		}
		
		sbr.append(StringUtils.removeEnd(rows.toString())).append("]});");
		getPageContextInfo().write(sbr.toString());
	}
	
	/**
	 *  var tableId_store = new Ext.data.JsonStore({
				        root: 'data',
				        totalProperty: 'total',
				        //True表示在proxy配合下，要求服务器提供一个更新版本的数据对象以便排序，反之就是在Record缓存中排序（默认是false）。
				        remoteSort: true,
				        //autoLoad : {params : {start:0, limit:10}},
				        url: '/qeweb/system/generalSearchAC.action?operation&tableName=orderList-Demo_purchaseOrderBO',
				        //自定义属性，表示是否已经注册了行级按钮的handler
				        hasRBarHandler : false,
				        fields: [
				        	'index',
				            'title',
				            {name: 'replycount', mapping: 'replycount', type: 'int'},
				            {name: 'lastpost', type: 'date', dateFormat: 'timestamp'}
				        ],
				        listeners : {
							'load' : function() {
								//更新数据岛
								TableHelper.updataTableIsland(tableId);
								TableHelper.setTableStatus(tableId);
								TableHelper.gridCheckboxFix(tableId);
								TableHelper.setBtnState(tableId);
								TableHelper.checkedOnThisPage(tableId);
								TableHelper.setHeight(tableId);
								unlockScreen();
							},
							'beforeload' : function() {
								function() {TableHelper.beforeTurnPage(tableId);}
							},
							'exception' : function() {
								unlockScreen();
								showMSG.showErr('加载出错!');
							}
						}
				    });
	 */
	private void paintStore() {
		StringBuilder sbr = new StringBuilder();
		ExtWebHelper.appendCreateObjectBegin(sbr, getStoreId(), "Ext.data.JsonStore");
		ExtWebHelper.appendObjectBegin(sbr);
		ExtWebHelper.appendAttr(sbr, "root", ConstantJSON.PAGEBAR_ROOT);
		ExtWebHelper.appendAttr(sbr, "totalProperty", ConstantJSON.PAGEBAR_TOTAL);
		ExtWebHelper.appendAttr(sbr, "remoteSort", true);
		String url = ConstantURL.CONTAINER_QUERY + "&" + ConstantParam.GA_TABLENAME + "=" + getName();
		ExtWebHelper.appendAttr(sbr, "url", getPageContextInfo().getSecurityURL(url));
		ExtWebHelper.appendAttr(sbr, "hasRBarHandler", false);
		sbr.append(ExtWebHelper.getFiledsStr("fields", getFcList().keySet()));
		ExtWebHelper.appendObjectSplit(sbr);
		String listeners =  "{'load':function() {" +
			"TableHelper.updataTableIsland(\"" + getId() + "\");" +
			"TableHelper.setTableStatus(\"" + getId() + "\");" +
			"TableHelper.gridCheckboxFix(\"" + getId() + "\");" +
			"TableHelper.setBtnState(\"" + getId() + "\");" +
			"TableHelper.setHeight(\"" + getId() + "\");";
		if(isRememberChecked())
			listeners += "TableHelper.checkedOnThisPage(\"" + getId() + "\");";
		listeners += "unlockScreen()},";
		listeners += "'beforeload': function() {TableHelper.beforeTurnPage('" + getId() + "');},";
		listeners += "'exception' : function() {unlockScreen();}";
		listeners += "}";
		ExtWebHelper.appendObjectTail(sbr, "listeners", listeners);
		ExtWebHelper.appendObjectAfter(sbr);
		ExtWebHelper.appendCreateObjectEnd(sbr);
		ExtWebHelper.appendSingleEnd(sbr);

		getPageContextInfo().write(sbr.toString());
	}
	
	/**
	 * 添加翻页控件
	 * var tableId_bbar =  new Ext.PagingToolbar({
			pageSize: 10,
			store: store,
			displayInfo: true,
			listeners : {
				'beforechange' : function() {
					lockSrceen();
					TableHelper.rememberCheckedRow('tableId');
					if(handler.isEdit('tableId'))
						return false;
				},
				'change' : function() {
					TableHelper.changeNoWidth(this, 1, 'tableId');
				}
			}
		})
	 */
	private void paintBbar() {
		StringBuilder sbr = new StringBuilder();
		sbr.append("var ").append(getBbarId()).append(" = new Ext.PagingToolbar({");
		ExtWebHelper.appendAttr(sbr, "pageSize", getPageSize());
		if(DisplayType.isProgressBarStyle())
			ExtWebHelper.appendObjectAttr(sbr, "plugins ", "new Ext.ux.ProgressBarPager()");
		else if(DisplayType.isSlidingStyle())
			ExtWebHelper.appendObjectAttr(sbr, "plugins ", "new Ext.ux.SlidingPager()");
		ExtWebHelper.appendObjectAttr(sbr, "store", getStoreId());
		ExtWebHelper.appendAttr(sbr, "displayInfo", true);
		sbr.append("listeners : {'beforechange' : function() {lockSrceen();");
		if(isRememberChecked())
			sbr.append("TableHelper.rememberCheckedRow('" + getId() + "');");
		sbr.append("var handler = new TableHandler();");
		sbr.append("if(handler.isEdit('");
		sbr.append(getId());
		sbr.append("'))return false;");
		sbr.append("},");
		sbr.append("'change' : function(){");
		if(isCheckBoxMod() || isRadioMod())
			sbr.append("TableHelper.changeNoWidth(this, 1, '" + getId() + "');");
		else
			sbr.append("TableHelper.changeNoWidth(this, 0, '" + getId() + "');");
		sbr.append("}}");
		sbr.append("});");
		
		getPageContextInfo().write(sbr.toString());
	}

	/**
	 * var tableId_grid = new Ext.grid.EditorGridPanel({
								id : 'tableId',
								name : 'orderList-Demo_purchaseOrderBO',
								title : '采购订单',
								deferRowRender : false,
								enableColumnMove : false,
								iconCls : 'grid-icon',
								header : true,
								width : document.body.clientWidth,
								style : 'margin-left:1px;margin-top:10px;',
								collapsible : true,
								hideHeaders : true,
								frame: true,
								store : store,
								viewConfig:{forceFit : true},
								stripeRows : true,
								trackMouseOver: true,
								columnLines:true,
								//是否有可编辑列
								hasEditorsCol:ContainerUtil.isNotNull(getEditFields()),
								//是否有展示状态图标的列
								hasStatusCol: hasStatusCol(),
								//表头分组布局
								plugins: tableId_group,
								listeners:{
									'headerclick' : function(){
										if(getStoreId().remoteSort)
											TableHelper.beforeTurnPage(getId());
									},
									'beforeedit' : TableHelper.beforeEdit,
									'columnmove' : function() {
										TableHelper.setTableStatus(tableId),
										TableHelper.setBtnState(tableId);
									},
									'afteredit' : TableHelper.afterEdit,
									'columnresize' : function(width, index) {TableSetting.saveWidthChange(width, index, tableId);}
								},
								sm : new Ext.grid.CheckboxSelectionModel(),
								cm : new Ext.grid.ColumnModel({
									columns: [
										new Ext.grid.CheckboxSelectionModel(),
										//new Ext.grid.RowNumberer({width : 28})
										new Ext.grid.RowNumberer(),
										{
											header : "<div style='color:red' align=center>操作</div>",
											width : 201,
											dataIndex : 'rowIndex',
											renderer : tableId_rowBtnRender
										}
									  	{header : "订单号", dataIndex : "title", sortable : true, editor : {fieldLabel : '供应商编码',allowBlank : true,blankText : '必填项',viewlabel : '供应商编码',id : 'orderList-PurchaseOrderBO-vendorCode',name : 'orderList-PurchaseOrderBO-vendorCode',xtype : 'textfield',vtype : 'bopRange',disabled : false,readOnly : false,hidden : false}, id:"title"},
									  	{header : "物料编码", dataIndex : "replycount", sortable : true, id:"replycount"},
									  	{header : "发货日期", dataIndex : "lastpost", sortable : true, id:"lastpost"}
									 ],
									 listeners:{
											'hiddenchange' : TableSetting.saveHiddenChange,
											'columnmoved' : TableSetting.saveColMoved
									 }
								}),
								tbar: [
									 	new Ext.Button({
											id : 'showAll-DemoBO_ShowAllFC-insert',
											name : 'showAll-DemoBO_ShowAllFC-insert',
											iconCls : 'modifyIcon',
											text : '新增'
										}),
										'-',
										new Ext.Button({
											id : 'showAll-DemoBO_ShowAllFC-insert',
											name : 'showAll-DemoBO_ShowAllFC-insert',
											iconCls : 'modifyIcon',
										text : '删除'})
									] ,
								 bbar: tableId_bbar
							});
		itemArray.push(tableId_grid);
	 */
	private void paintGrid(TableLayout layout) {
		StringBuilder sbr = new StringBuilder();
		ExtWebHelper.appendCreateObjectBegin(sbr, getGridId(), "Ext.grid.EditorGridPanel");
		ExtWebHelper.appendObjectBegin(sbr);
		ExtWebHelper.appendAttr(sbr, "id", getId());
		ExtWebHelper.appendAttr(sbr, "name", getName());
		ExtWebHelper.appendAttr(sbr, "title", getText());
		ExtWebHelper.appendAttr(sbr, "frame", true);
		ExtWebHelper.appendAttr(sbr, "hidden", getStatus().isHidden());
		ExtWebHelper.appendAttr(sbr, "hideMode", "offsets");		 
		ExtWebHelper.appendAttr(sbr, "iconCls", getIcon());
		ExtWebHelper.appendAttr(sbr, "header", isHeader());
		ExtWebHelper.appendAttr(sbr, "enableColumnMove", AppConfig.getTableColumnMove());
		ExtWebHelper.appendAttr(sbr, "collapsible", true);
		ExtWebHelper.appendAttr(sbr, "hideHeaders", isHideHeaders());
		ExtWebHelper.appendAttr(sbr, "deferRowRender", false);
		setTableHeight(sbr);
		ExtWebHelper.appendObjectAttr(sbr, "viewConfig", "{forceFit : " + isForceFit() + "}");
		ExtWebHelper.appendObjectAttr(sbr, "store", getStoreId());
		ExtWebHelper.appendAttr(sbr, "stripeRows ", true);
		ExtWebHelper.appendAttr(sbr, "trackMouseOver ", true);
		ExtWebHelper.appendAttr(sbr, "columnLines", true);
		ExtWebHelper.appendAttr(sbr, "clicksToEdit ", "1");
		//是否有可编辑列
		ExtWebHelper.appendAttr(sbr, "hasEditorsCol", ContainerUtil.isNotNull(getEditFields()));
		//是否有展示状态图标的列
		ExtWebHelper.appendAttr(sbr, "hasStatusCol", hasStatusCol());
		
		String listeners = "{" +
		"'beforeedit' : TableHelper.beforeEdit, " +
		"'headerclick' : function(){if("+getStoreId()+".remoteSort)TableHelper.beforeTurnPage('" + getId() + "');}, " +
		"'afteredit' : TableHelper.afterEdit,";
		if(AppConfig.getTableSetting()) {
			listeners += "'columnresize' : function(width, index){TableSetting.saveWidthChange(width, index, '" + getId() + "')},";
		}
		listeners += "'columnmove' : function() {" +
			"TableHelper.setTableStatus(\"" + getId() + "\")," +
			"TableHelper.setBtnState(\"" + getId() + "\");" +
		"}}";
		ExtWebHelper.appendObjectAttr(sbr, "listeners", listeners);

		ExtWebHelper.appendObjectAttr(sbr, "sm", getExtSelectionModel());
		if(hasGroupHeader() && ContainerUtil.isNotNull(layout.getHeaderSet())) {
			ExtWebHelper.appendObjectAttr(sbr, "plugins", getGroupName());
		}
		getPageContextInfo().write(sbr.toString());

		//cm
		paintCM();
		if(ContainerUtil.isNotNull(getNoExpendBtnList()) || isFill() || isHasBbar())
			getPageContextInfo().write(",");

		ExtContainerHelper.paintTbars(getNoExpendBtnList(), isFill(), getGridId(), layout.getBtnColumns(), getPageContextInfo());

		sbr = new StringBuilder();
		if((ContainerUtil.isNotNull(getNoExpendBtnList()) || isFill()) && isHasBbar()){
			ExtWebHelper.appendObjectSplit(sbr);
		}
		if(isHasBbar()) {
			ExtWebHelper.appendObjectTail(sbr, "bbar", getBbarId());
		}
		ExtWebHelper.appendObjectAfter(sbr);
		ExtWebHelper.appendCreateObjectEnd(sbr);
		ExtWebHelper.appendSingleEnd(sbr);
		//如果在tab中，就不把自己放入itemArray
		if(!(this.getParent() instanceof Tab))
			sbr.append("itemArray.push(").append(getGridId()).append(");");
		getPageContextInfo().write(sbr.toString());
	}

	private void paintCM() {
		StringBuilder sbr = new StringBuilder();
		sbr.append("cm:");
		sbr.append("new Ext.grid.ColumnModel({");
		if(AppConfig.getTableSetting()) {
			ExtWebHelper.appendAttr(sbr, "gridId", getId());
			sbr.append("listeners:{");
			sbr.append("'hiddenchange' : TableSetting.saveHiddenChange,");
			sbr.append("'columnmoved' : TableSetting.saveColMoved");
			sbr.append("},");
		}
		sbr.append("columns: [");
		String selectionModel = getExtSelectionModel();
		if(StringUtils.isNotEmpty(selectionModel))
			sbr.append(selectionModel).append(",");
		
		sbr.append("new Ext.grid.RowNumberer({width:projectStyle.getNoWidth('" + getBbarId()
				+ "')}),");
		//行级按钮在左侧显示
		if(StringUtils.isEqual(ConstantAppProp.RBTNALIGH_LEFT, AppConfig.getRbtnAligh()))
			sbr.append(getRowBtns());

		boolean flag = false;
		TableSettingBO tableSetting = getTableSetting();
		//列宽信息, key:bop, value:width
		Map<String, String> columnWidth = null;
		if(tableSetting != null) 
			columnWidth = tableSetting.getColumnWidthMap();
		
		Map<String, FinegrainedComponent> displayFields = getDisplayFields();
		for (FinegrainedComponent fc : displayFields.values()) {
			BOProperty bop = fc.getBc();
			if(fc instanceof Hidden || bop == null || fc.getStatus().isHidden())
				continue;
			if(flag)
				sbr.append(",");
			
			//根据tableSetting重新设置列宽
			if(ContainerUtil.isNotNull(columnWidth) && columnWidth.containsKey(fc.getBcId())) 
				fc.setWidth(StringUtils.convertToFloat(columnWidth.get(fc.getBcId())));
			
			String bcId = ExtWebHelper.changeTOExtItemName(fc.getBcId());
			ExtWebHelper.appendObjectBegin(sbr);
			ExtWebHelper.appendAttr(sbr, "header", fc.getText());
			ExtWebHelper.appendAttr(sbr, "dataIndex", bcId);
			if(tableSetting != null)
				ExtWebHelper.appendAttr(sbr, "hidden ", StringUtils.isInArray(fc.getBcId(), tableSetting.getHiddenBopArr()));

			BOProperty realBOP = BoOperateUtil.getRealBop(bop);
			boolean isEditor = getEditFields().containsKey(fc.getBcId());
			
			//地址信息
			if(realBOP instanceof LocationBOP) {
				ExtWebHelper.appendObjectAttr(sbr, "renderer", ExtTableRender.getLocationRender(getId()));
			}
			//超链接控件
			else if(fc instanceof Anchor || fc instanceof FileField) {
				ExtWebHelper.appendObjectAttr(sbr, "renderer", ExtTableRender.getAnchorRender(getId(), fc.getBcId(), isEditor));
			}
			//图标
			if(realBOP instanceof StatusBOP) {
				ExtWebHelper.appendObjectAttr(sbr, "renderer", ExtTableRender.getStatusIconRender((StatusBOP)realBOP, isEditor));
			}
			//日期类型
			else if(realBOP instanceof DateBOP) {
				ExtWebHelper.appendObjectAttr(sbr, "renderer", ExtTableRender.getDateRender((DateBOP)realBOP, isEditor));
			}
			//日期控件, 优先级在日期类型之后, 当日期控件绑定非DateBop时使用 
			else if(fc instanceof DateField) {
				ExtWebHelper.appendObjectAttr(sbr, "renderer", ExtTableRender.getDateRender(isEditor));
			}
			//图片类型
			else if(fc instanceof Image) {
				ExtWebHelper.appendObjectAttr(sbr, "renderer", ExtTableRender.getImageRender((ImageBOP)realBOP, isEditor));
			}
			//checkbox
			else if(fc instanceof CheckBox) {
				ExtWebHelper.appendObjectAttr(sbr, "renderer", ExtTableRender.getCheckboxRender(bop, isEditor));
			}
			//枚举值
			else if(bop.getRange().hasEnumRange()) {
				ExtWebHelper.appendObjectAttr(sbr, "renderer", ExtTableRender.getEnumRender(bop, isEditor));
			}
			//可编辑单元格中的普通值
			else if(isEditor) {
				ExtWebHelper.appendObjectAttr(sbr, "renderer", "CellRender.cellStyleRender");
			}

			if(isEditor){
				sbr.append("editor:{");
				getPageContextInfo().write(sbr.toString());
				if(fc instanceof FileField && StringUtils.isEmpty(((FileField)fc).getOperate()))
					((FileField)fc).setOperate("upload");
				fc.paint();
				sbr = new StringBuilder();
				sbr.append("},");
			}

			ExtWebHelper.appendAttr(sbr, "id", bcId);
			if(StringUtils.isNotEmpty(fc.getAlign()))
				ExtWebHelper.appendAttr(sbr, "align", fc.getAlign());
			if(fc.getWidth() > 0)
				ExtWebHelper.appendAttr(sbr, "width", fc.getWidth());

			ExtWebHelper.appendTail(sbr, "sortable", getSortMap().get(bcId));
			ExtWebHelper.appendObjectAfter(sbr);
			flag = true;
		}
		//行级按钮在右侧显示
		if(StringUtils.isEqual(ConstantAppProp.RBTNALIGH_RIGHT, AppConfig.getRbtnAligh()))
			sbr.append(getRowBtns());
		
		sbr.append("]})");

		getPageContextInfo().write(sbr.toString());
	}

	/**
	 * 获取table展现高度
	 * @return
	 */
	private void setTableHeight(StringBuilder sbr){
		//若标签设定了height, 设置高度
		if(getHeight() > 0) {
			ExtWebHelper.appendAttr(sbr, "height", getHeight());
			//静态高度标识, 以便height不受"自动计算高度"影响
			ExtWebHelper.appendAttr(sbr, "staticHeight", getHeight());
		}
		//没有翻页控件时
		else if(!isHasBbar()) {
			ExtWebHelper.appendAttr(sbr, "autoWidth", true);
		}
		
		//最大高度是整个操作区域的高度
		if(isBodyHeight()) {
			ExtWebHelper.appendObjectAttr(sbr, "maxHeight", "Ext.getBody().getHeight() - 12");
		}
		//最大高度
		else if(StringUtils.convertToDouble(getMaxHeight()) != null) {
			ExtWebHelper.appendObjectAttr(sbr, "maxHeight", getMaxHeight());
		}
	}

	/**
	 * 添加行级按钮
	 * {
			header : "<div style='color:red' align=center>操作</div>",
			width : 201,
			dataIndex : 'index',
			renderer : tableId_rowBtnRender,
			hideable : false,
			fixed : true,
			menuDisabled : true
		}
	 * @param columns
	 */
	private String getRowBtns() {
		List<CommandButton> btns = getExpendBtnList();
		if(ContainerUtil.isNull(btns))
			return "";

		StringBuilder sbr = new StringBuilder();
		if(StringUtils.isEqual(ConstantAppProp.RBTNALIGH_RIGHT, AppConfig.getRbtnAligh()))
			sbr.append(",");
		
		ExtWebHelper.appendObjectBegin(sbr);
		ExtWebHelper.appendObjectAttr(sbr, "header", "'<span style=\"color:red;\" align=center>" + AppLocalization.getLocalization("table.operate") + "</span>'");

		if(getOptWidth() > 0)
			ExtWebHelper.appendAttr(sbr, "width", getOptWidth());
		else
			ExtWebHelper.appendAttr(sbr, "width", PLWebHelper.getOperateWidth(getExpendBtnList()));

		//禁止用户隐藏该列
		ExtWebHelper.appendAttr(sbr, "hideable", false);
		//禁止列菜单
		ExtWebHelper.appendAttr(sbr, "menuDisabled", true);
		ExtWebHelper.appendAttr(sbr, "dataIndex", ConstantJSON.PAGEBAR_ROWINDEX);
		ExtWebHelper.appendObjectTail(sbr, "renderer", ExtTableRender.getRwoBtnRender(getExpendBtnList()));
		ExtWebHelper.appendObjectAfter(sbr);
		
		if(StringUtils.isEqual(ConstantAppProp.RBTNALIGH_LEFT, AppConfig.getRbtnAligh()))
			sbr.append(",");
			
		return sbr.toString();
	}

	/**
	 * getExtSelectionModel
	 * @return
	 */
	private String getExtSelectionModel() {
		if(isCheckBoxMod())
			return "new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn})";
		else if(isRadioMod())
			return "new Ext.grid.CheckboxSelectionModel({header:'',singleSelect:true})";
		else
			return "";
	}

	/**
	 * 增/改/查看弹出框的window
	 *
	 * recordWinArr[btnId] = new Ext.Window({
						width:400,
						height:300,
						modal:true,
						title:'新增',
						layout:'fit',
						closeAction:'hide',
						maximizable: true,
						collapsible: true,
						items:formPanelArr[btnId],
						buttons:[
							{
								xtype:'checkbox',
								id:btnid+'-closeOnFinish',
								inputValue:1,
								checked:true
							},
							{
								xtype:'label',
								html:'添加完后关闭'
							},
							{
								text:'确定',
								handler:function() {
									if(BtnTablePopuEvent.validate(btnId ){
										BtnTablePopuEvent.handle(btnId);
										if(Ext.getCmp('" + btnId + "-closeOnFinish" + "').getValue())
											recordWinArr[btnId].hide();
									}
								}
							},
							{
								text:'重置',
								handler:function() {
									BtnTablePopuEvent.clear(operateId);
								}
							},
							{
								text:'关闭',
								handler:function() {
									recordWinArr[btnId].hide();
								}
							}
						]
					});
	 */
	private void paintReceordWin() {
		paintFormDisland();
		paitnFormPanel();
	}

	/**
	 * 画出增/改/查看弹出框的数据岛
	 */
	private void paintFormDisland() {
		if(hasBtn(ConstantBOMethod.BO_INSERT) && ContainerUtil.isNotNull(getInsertFields())) {
			String insertBtnId = VCUtil.createFinegrainedID(getName(), ConstantBOMethod.BO_INSERT);
			String insertFormDisland = AppManager.createDataIsland().createHiddenFormDataIsland(this, getWinFormId(insertBtnId));
			getPageContextInfo().write("formDislandlArr['" + insertBtnId + "']=XMLDomFactory.getInstance(\"" + insertFormDisland + "\").find(DISLAND.BO);");
		}
		if(hasBtn(ConstantBOMethod.BO_UPDATE)  && ContainerUtil.isNotNull(getUpdateFields())) {
			String updateBtnId = VCUtil.createFinegrainedID(getName(), ConstantBOMethod.BO_UPDATE);
			String updateFormDisland = AppManager.createDataIsland().createHiddenFormDataIsland(this, getWinFormId(updateBtnId));
			getPageContextInfo().write("formDislandlArr['" + updateBtnId + "']=XMLDomFactory.getInstance(\"" + updateFormDisland + "\").find(DISLAND.BO);");
		}
		if(hasBtn(ConstantBOMethod.BO_VIEW) && ContainerUtil.isNotNull(getViewFields())) {
			String viewBtnId = VCUtil.createFinegrainedID(getName(), ConstantBOMethod.BO_VIEW);
			String viewFormDisland = AppManager.createDataIsland().createHiddenFormDataIsland(this, getWinFormId(viewBtnId));
			getPageContextInfo().write("formDislandlArr['" + viewBtnId + "']=XMLDomFactory.getInstance(\"" + viewFormDisland + "\").find(DISLAND.BO);");
		}
	}
	/**
	 * 增/改/查看弹出框的formPanel
	 */
	private void paitnFormPanel() {
		WindowSize size = new WindowSize(this.getWinSizeStr());

		if(hasBtn(ConstantBOMethod.BO_INSERT)) {
			String insertBtnId = VCUtil.createFinegrainedID(getName(), ConstantBOMethod.BO_INSERT);
			Layout addLayout = LayoutFactory.createLayout(getAddLayoutStr(), LayoutFactory.LAYOUTTYPE_WINDOW);
			paintDeptTree(getInsertFields(), ConstantBOMethod.BO_INSERT);
			paintFormPanel(getInsertFields(), insertBtnId, ConstantBOMethod.BO_INSERT, addLayout);
			paintReceordWin(getInsertFields(), insertBtnId, ConstantBOMethod.BO_INSERT, addLayout.getColumns(), size);
		}
		
		if(hasBtn(ConstantBOMethod.BO_UPDATE)) {
			String updateBtnId = VCUtil.createFinegrainedID(getName(), ConstantBOMethod.BO_UPDATE);
			Layout editLayout = LayoutFactory.createLayout(getEditLayoutStr(), LayoutFactory.LAYOUTTYPE_WINDOW);
			paintDeptTree(getUpdateFields(), ConstantBOMethod.BO_UPDATE);
			paintFormPanel(getUpdateFields(), updateBtnId, ConstantBOMethod.BO_UPDATE, editLayout);
			paintReceordWin(getUpdateFields(), updateBtnId, ConstantBOMethod.BO_UPDATE, editLayout.getColumns(), size);
		}

		if(hasBtn(ConstantBOMethod.BO_VIEW)) {
			String viewBtnId = VCUtil.createFinegrainedID(getName(), ConstantBOMethod.BO_VIEW);
			Layout viewLayout = LayoutFactory.createLayout(getViewLayoutStr(), LayoutFactory.LAYOUTTYPE_WINDOW);
			paintFormPanel(getViewFields(), viewBtnId, ConstantBOMethod.BO_VIEW, viewLayout);
			paintReceordWin(getViewFields(), viewBtnId, ConstantBOMethod.BO_VIEW, viewLayout.getColumns(), size);
		}
	}
	
	/**
	 * 如果select控件是下拉树, 在此绘制
	 * @param fcFields
	 * @param operate
	 */
	private void paintDeptTree(Map<String, FinegrainedComponent> fcFields, String operate) {
		if(ContainerUtil.isNull(fcFields))
			return;
		
		for (Entry<String, FinegrainedComponent> entry : fcFields.entrySet()) {
			if(entry.getValue() instanceof Select) {
				Select select = (Select) entry.getValue();
				resetFcId(operate, select);

				if(select.isDeptTree())
					select.paintDeptTree();
			}
		}
	}
	
	/**
	 * 新增弹出框
	 * formPanelArr[btnId] = new Ext.FormPanel({
						bodyStyle:{padding:'30,55'},
						defaults:{width:120},
						labelWidth:80,
						items:[...]
					});
	 */
	private void paintFormPanel(Map<String, FinegrainedComponent> fcFields, String btnId, String operate, Layout layout) {
		if(ContainerUtil.isNull(fcFields))
			return;

		PageContextInfo out = getPageContextInfo();
		StringBuilder sbr = new StringBuilder();
		sbr.append("formPanelArr['").append(btnId).append("'] = ");
		ExtWebHelper.appendCreateObjectBegin(sbr, null, "Ext.FormPanel");
		ExtWebHelper.appendObjectBegin(sbr);
		ExtWebHelper.appendAttr(sbr, "id", getWinFormId(btnId));
		ExtWebHelper.appendAttr(sbr, "name", getName());
		ExtWebHelper.appendObjectAttr(sbr, "bodyStyle", "{padding:'20'}");
		ExtWebHelper.appendAttr(sbr, "border", false);
		ExtWebHelper.appendAttr(sbr, "labelAlign", "right");
		ExtWebHelper.appendAttr(sbr, "labelWidth", 120);

		Map<String, Cell> cellMap = layout.getCellMap();
		//表单列数
		int columns = layout.getColumns();
		ExtWebHelper.appendAttr(sbr, "layout", "tableform");
		ExtWebHelper.appendObjectAttr(sbr, "layoutConfig", "{columns:" + columns + "}");

		ExtWebHelper.appendObjectTail(sbr, "items", "[");

		//累计合并的单元格数
		int collSpanSum = 0;
		for(Entry<String, FinegrainedComponent> entry : fcFields.entrySet()){
			FinegrainedComponent fc = entry.getValue();
			String bopBind = entry.getKey();
			resetFcId(operate, fc);

			//查看弹出框去掉所有校验
			if(ConstantBOMethod.isView(operate)) {
				fc.setRequired(false);
				fc.setValidateable(false);
			}
			//bopBind的前几个组件（这几个组件在同一行）所占的单元格数
			int lastCollSpan = collSpanSum;
			//bopBind合并的单元格数
			int thisCollSpan = 0;

			if(cellMap.containsKey(entry.getKey())) {
				collSpanSum += cellMap.get(bopBind).getCollSpan();
				thisCollSpan = cellMap.get(bopBind).getCollSpan();
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
				ExtWebHelper.appendObjectBegin(sbr);
				ExtWebHelper.appendAttr(sbr, "xtype", "label");
				ExtWebHelper.appendTail(sbr, "colspan", Math.abs(columns - lastCollSpan));
				ExtWebHelper.appendObjectAfter(sbr);
				ExtWebHelper.appendObjectSplit(sbr);
				lastCollSpan = 0;
				collSpanSum = 0;
			}

			ExtWebHelper.appendObjectBegin(sbr);
			if(cellMap.containsKey(bopBind)) {
				ExtWebHelper.appendAttr(sbr, "colspan", cellMap.get(bopBind).getCollSpan());
				ExtWebHelper.appendAttr(sbr, "rowspan", cellMap.get(bopBind).getRowSpan());
			}
			else {
				ExtWebHelper.appendAttr(sbr, "colspan", 1);
				ExtWebHelper.appendAttr(sbr, "rowspan", 1);
			}

			ExtWebHelper.appendAttr(sbr, "anchor", "98%");
			out.write(sbr.toString());
			fc.paint();
			out.write("}");
			sbr = new StringBuilder();
			ExtWebHelper.appendObjectSplit(sbr);
		}
		sbr = new StringBuilder();
		ExtWebHelper.appendArrayAfter(sbr);
		ExtWebHelper.appendObjectAfter(sbr);
		ExtWebHelper.appendCreateObjectEnd(sbr);
		out.write(sbr.toString());
		out.write(";");
	}

	/**
	 * 新设置细粒度组件ID, 以便在ext中形成唯一的组件ID, 否则ext将不能正确解析
	 * @param operate
	 * @param fc
	 */
	private void resetFcId(String operate, FinegrainedComponent fc) {
		String[] arr = fc.getId().split(ConstantSplit.GA_PARAM_SPLIT);
		String fpFcId = arr[0] + ConstantSplit.GA_PARAM_SPLIT + arr[1]
				+ ConstantSplit.GA_PARAM_SPLIT + arr[2]
				+ ConstantSplit.GA_PARAM_SPLIT + operate;
		fc.setId(fpFcId);
		if(fc.getBc() == null)
			return;
		if(ConstantBOMethod.isView(operate))
			fc.getStatus().setReadonly(true);
		else if(fc.getSourceBtn() != null){
			fc.getSourceBtn().setSysOperate(operate);
		}
	}

	private void paintReceordWin(Map<String, FinegrainedComponent> fcFields,
			String btnId, String operate, int columns, WindowSize size) {
		if(ContainerUtil.isNull(fcFields))
			return;

		String start = "recordWinArr['" + btnId + "'] = new Ext.Window({";
		StringBuilder recordWinBody = new StringBuilder();
		ExtWebHelper.appendAttr(recordWinBody, "width",  getReceordWinWidth(operate, columns, size));
		ExtWebHelper.appendAttr(recordWinBody, "height", getReceordWinHeight(operate, size));
		ExtWebHelper.appendAttr(recordWinBody, "modal", true);
		ExtWebHelper.appendAttr(recordWinBody, "layout", "fit");
		ExtWebHelper.appendAttr(recordWinBody, "title", AppLocalization.getLocalization(operate));
		ExtWebHelper.appendAttr(recordWinBody, "closeAction", "hide");
		ExtWebHelper.appendAttr(recordWinBody, "maximizable", true);
		ExtWebHelper.appendAttr(recordWinBody, "collapsible", true);
		ExtWebHelper.appendAttr(recordWinBody, "constrain", true);
		if(!ConstantBOMethod.isView(operate)) {
			//设置细粒度组件关联及将sourceBtn关联的细粒度组件设置为只读状态
			ExtWebHelper.appendObjectTail(recordWinBody, "listeners", "{'afterrender': function(){");
			recordWinBody.append("BtnTablePopuEvent.sysWinInit(formDislandlArr['");
			recordWinBody.append( btnId);
			recordWinBody.append("'], '");
			recordWinBody.append(operate);
			recordWinBody.append("');}},");
		}
		
		//弹出框左上角的icon图标
		if(ConstantBOMethod.isInsert(operate)) 
			ExtWebHelper.appendAttr(recordWinBody, "iconCls", "add");
		else if(ConstantBOMethod.isUpdate(operate))
			ExtWebHelper.appendAttr(recordWinBody, "iconCls", "edit");
		else 
			ExtWebHelper.appendAttr(recordWinBody, "iconCls", "view");
		
		//ExtWebHelper.appendAttr(recordWinBody, "iconCls", "view");
		ExtWebHelper.appendObjectAttr(recordWinBody, "items", "formPanelArr['" + btnId + "']");

		String recordWinBtn = "buttons:[";
		if(!ConstantBOMethod.isView(operate)) {
			//是否添加完后关闭
			recordWinBtn += "{xtype:'checkbox',id:'" + btnId + "-closeOnFinish" + "',height:27,inputValue:1,checked:true},";
			recordWinBtn += "{xtype:'label',html:'" + getCloseOnFinishLabel(operate) + "'},";
			//确认按钮
			recordWinBtn += "{text:'" + AppLocalization.getLocalization("form.save") + "',iconCls:'save',height:" + ExtWebHelper.btnHeight +
				",handler:function(){" +
				"if(BtnTablePopuEvent.validate(\"" + btnId + "\")){" +
					"BtnTablePopuEvent.handle(\"" + btnId + "\");" +
					"if(Ext.getCmp('" + btnId + "-closeOnFinish" + "').getValue()){ " +
						"recordWinArr['" + btnId + "'].hide();BtnTablePopuEvent.clear(\"" + btnId + "\");" +
					"}" +
				"}}},";
		}
		//在新增框添加重置按钮
		if(ConstantBOMethod.isInsert(operate)) {
			recordWinBtn += "{text:'" + AppLocalization.getLocalization("reset") + "',iconCls:'reset',height:" + ExtWebHelper.btnHeight +
			",handler:function(){" +
			"BtnTablePopuEvent.clear(\"" + btnId + "\");" +
			"}},";
		}
		//在修改框添加重置按钮
		if(ConstantBOMethod.isUpdate(operate)) {
			recordWinBtn += "{text:'" + AppLocalization.getLocalization("reset") + "',iconCls:'reset',height:" + ExtWebHelper.btnHeight + "," +
			"handler:function(){" +
			"BtnTablePopuEvent.reset(\"" + btnId + "\");" +
			"}},";
		}
		
		//添加关闭按钮
		if(AppConfig.hasCloseBtn()) {
			recordWinBtn += "{text:'" + AppLocalization.getLocalization("cancel") + "',iconCls:'noIcon',height:" + ExtWebHelper.btnHeight;
	        recordWinBtn += ", handler:function(){";
	        recordWinBtn += "BtnTablePopuEvent.clear(\"" + btnId + "\");recordWinArr['" + btnId + "'].hide();}}";
		}
		if(recordWinBtn.charAt(recordWinBtn.length() - 1) == ',')
			recordWinBtn = StringUtils.removeEnd(recordWinBtn);
		String end = "]});";

		getPageContextInfo().write(start + recordWinBody.toString() + recordWinBtn + end);
	}

	private String getReceordWinWidth(String operate, int columns, WindowSize size) {
		String width = null;
		if(StringUtils.isEqual(ConstantBOMethod.BO_INSERT, operate)){
			width = StringUtils.getNotNullStr(this.getInsertWindow().getWinWidth());
			if(StringUtils.isNotEmpty(size.getAddWinWidth()))
				width = size.getAddWinWidth();
		}
		else if(StringUtils.isEqual(ConstantBOMethod.BO_UPDATE, operate)){
			width = StringUtils.getNotNullStr(this.getUpdateWindow().getWinWidth());
			if(StringUtils.isNotEmpty(size.getUpdateWinWidth()))
				width = size.getUpdateWinWidth();
		}
		else if(StringUtils.isEqual(ConstantBOMethod.BO_VIEW, operate)){
			width = StringUtils.getNotNullStr(this.getViewWindow().getWinWidth());
			if(StringUtils.isNotEmpty(size.getViewWinWidth()))
				width = size.getViewWinWidth();
		}
		
		//弹出框的默认宽度
		String DEFAULT_WIN_WIDTH = "500";	
		width = (StringUtils.isEmpty(width) ? DEFAULT_WIN_WIDTH : width);

		if(columns > 1){
			BigDecimal num1 = new BigDecimal(width);
			BigDecimal num2 = new BigDecimal(DEFAULT_WIN_WIDTH);
			BigDecimal num3 = new BigDecimal(StringUtils.convertToString(columns));

			if(num1.multiply(num3).compareTo(num2.multiply(num3)) > 0){
				width = num2.multiply(num3).toString();
			}
			else{
				width = num1.multiply(num3).toString();
			}
		}
		return width;
	}

	private String getReceordWinHeight(String operate, WindowSize size) {
		String height = null;
		if(StringUtils.isEqual(ConstantBOMethod.BO_INSERT, operate)) {
			height = StringUtils.getNotNullStr(this.getInsertWindow().getWinHeight());
			if(StringUtils.isNotEmpty(size.getAddWinHeight()))
				height = size.getAddWinHeight();
			if(StringUtils.isEmpty(height))
				height = getAutoWinHeight(getInsertFields());
		}
		else if(StringUtils.isEqual(ConstantBOMethod.BO_UPDATE, operate)) {
			height = StringUtils.getNotNullStr(this.getUpdateWindow().getWinHeight());
			if(StringUtils.isNotEmpty(size.getUpdateWinHeight()))
				height = size.getUpdateWinHeight();
			if(StringUtils.isEmpty(height))
				height = getAutoWinHeight(getUpdateFields());
		}
		else if(StringUtils.isEqual(ConstantBOMethod.BO_VIEW, operate)) {
			height = StringUtils.getNotNullStr(this.getViewWindow().getWinHeight());
			if(StringUtils.isNotEmpty(size.getViewWinHeight()))
				height = size.getViewWinHeight();
			if(StringUtils.isEmpty(height))
				height = getAutoWinHeight(getViewFields());
		}

		return height;
	}

	private String getAutoWinHeight(Map<String, FinegrainedComponent> fcMap) {
		int height = 0;
		int fcHeight = 25;
		int bottomHeight = 120;
		for(FinegrainedComponent fc : fcMap.values()) {
			if(fc instanceof Hidden)
				continue;
			else if(fc instanceof TextArea)
				height += fcHeight * 3;
			else
				height += fcHeight;
		}
		
		return height + bottomHeight + "";
	}

	private String getCloseOnFinishLabel(String operate) {
		return ConstantBOMethod.isInsert(operate) ?
			AppLocalization.getLocalization("table.operate.closeOnInsert") :
			AppLocalization.getLocalization("table.operate.closeOnUpdate");
	}

	/**
	 * 是否有展示状态图标的列
	 * @return
	 */
	private boolean hasStatusCol() {
		Map<String, FinegrainedComponent> displayFields = getDisplayFields();
		for (FinegrainedComponent fc : displayFields.values()) {
			BOProperty bop = fc.getBc();
			if(fc instanceof Hidden || bop == null || fc.getStatus().isHidden())
				continue;
			
			if(BoOperateUtil.getRealBop(bop) instanceof StatusBOP)
				return true;
		}
		
		return false;
	}

	/**
	 * getStoreId
	 * @return
	 */
	private String getStoreId() {
		return getId() + "_store";
	}

	/**
	 * getGridId
	 * @return
	 */
	private String getGridId() {
		return getId() + "_grid";
	}

	/**
	 * getWinFormId
	 * @param btnId
	 * @return
	 */
	private String getWinFormId(String btnId) {
		return btnId + "_form";
	}
	
	/**
	 * getBbarId
	 * @return
	 */
	private String getBbarId() {
		return getId() + "_bbar";
	}
	
	/**
	 * 表头分组插件的名称
	 * @return
	 */
	private String getGroupName() {
		return getId() + "_group";
	}
	
	/**
	 * 表头分组的变量名称
	 * @param height	分组的高度
	 * @return
	 */
	private String getHeaderName(int height) {
		String headerName = getId();
		for(int i = 1; i <= height; i++) {
			headerName += "_header";
		}
		
		return headerName;
	}
}
