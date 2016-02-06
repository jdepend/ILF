package com.qeweb.framework.pal.coarsegrained;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.syssetting.tablesetting.bo.TableSettingBO;
import com.qeweb.framework.common.syssetting.tablesetting.service.ITableSettingService;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.common.VCType;
import com.qeweb.framework.impconfig.mdt.MDTContext;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.Var_Page;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.simplevc.container.SimpleContainer;
import com.qeweb.framework.pal.TableSort;
import com.qeweb.framework.pal.ViewComponent;
import com.qeweb.framework.pal.coarsegrained.helper.DDTHelper;
import com.qeweb.framework.pal.coarsegrained.helper.MDTHelper;
import com.qeweb.framework.pal.coarsegrained.tableWindow.TableWindow;
import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.finegrained.text.Hidden;
import com.qeweb.framework.pl.ext.ExtWebHelper;

public abstract class Table extends Container {
	public static final String SM_CHECKBOX = "checkBox";
	public static final String SM_RADIO = "radio";
	public static final String SM_EMPTY = "empty";
	//table选择模式，可选值为checkbox, radio, empty
	private String selectionModel = SM_CHECKBOX;

	//table中展示的列, key : bind, value : 细粒度组件
	private Map<String, FinegrainedComponent> displayFields = new LinkedHashMap<String, FinegrainedComponent>();
	//弹出新增框中的展示列, key : bind, value : 细粒度组件
	private Map<String, FinegrainedComponent> insertFields = new LinkedHashMap<String, FinegrainedComponent>();
	//弹出修改框中的展示列, key : bind, value : 细粒度组件
	private Map<String, FinegrainedComponent> updateFields = new LinkedHashMap<String, FinegrainedComponent>();
	//弹出查看框中的展示列, key : bind, value : 细粒度组件
	private Map<String, FinegrainedComponent> viewFields = new LinkedHashMap<String, FinegrainedComponent>();
	//可编辑的列, key : bind, value : 细粒度组件
	private Map<String, FinegrainedComponent> editFields = new LinkedHashMap<String, FinegrainedComponent>();

	//组件的排序标识, key : fcId, value: 是否排序
	private Map<String, Boolean> sortMap = new HashMap<String, Boolean>();
	
	private Page page;						//table的结果集
	private boolean hasBbar = true;			//是否有翻页控件
	private boolean hideHeaders = false;	//是否隐藏表格的头部
	private float optWidth;					//操作按钮列宽
	/*
	 * 是否开启记忆选择行功能. 如果开启, 将记住每页选择的id, 在翻页后自动勾选.
	 * 该选项仅在selectionModel是checkbox时有效.
	 */
	private boolean rememberChecked = false;
	private int pageSize = AppConfig.getPageSize();	//每页显示记录数
	//新增窗口
	private TableWindow insertWindow;
	//修改窗口
	private TableWindow updateWindow;
	//查看窗口
	private TableWindow viewWindow;
	
	private String addLayoutStr;	//弹出新增框的布局管理器表达式
	private String editLayoutStr;	//弹出修改框的布局管理器表达式
	private String viewLayoutStr;	//弹出查看框的布局管理器表达式
	private String winSizeStr;		//弹出框尺寸表达式
	
	private Boolean forceFit;		//列头是否自适应列表宽度，默认值为“true”
	private String sortStr;			//列头排序表达式
	/*
	 * 	表格数据列显示的最大高度.
		如果设置了maxHeight, 则:
		1.表格依据列数自动计算的高度将 不大于maxHeight;
		2.如果同时设置了height, maxHeight >= height;
		3.如果不设置height, 在依据列数自动计算的高度小于maxHeight时, 表格高度自动计算;
		4.如果maxHeight的值为bodyHeight, 则maxHeight的实际值是整个主操作区域的高度.
	 */
	private String maxHeight;		
	
	/*
	 * 表格设置, 记录了表格列的隐藏/反隐藏, 列宽信息, 列的位置, 仅当开启"记忆表格设置"功能时生效
	 */
	private TableSettingBO tableSettingBO;
	
	/**
	 * 组件的排序标识, key : bcId, value: 是否排序
	 * @return 
	 */
	public Map<String, Boolean> getSortMap() {
		if(sortMap != null && sortMap.size() > 0) 
			return sortMap;
		
		for (FinegrainedComponent fc : getDisplayFields().values()) {
			if(fc instanceof Hidden || fc.getBc() == null || fc.getStatus().isHidden())
				continue;
			
			String bcId = ExtWebHelper.changeTOExtItemName(fc.getBcId());
			Boolean sortAble = fc.getBc().isSortAble();
			TableSort sort = new TableSort(getSortStr());
			if(sortAble == null) {
				sortAble = sort.isSort(fc.getBcId());
				sortMap.put(bcId, sortAble);
			}
			else {
				sortMap.put(bcId, false);
			}
		}
		
		return sortMap;
	}

	/**
	 * 加载DDT方案中的细粒度组件,这些细粒度组件绑定弹性域,不在页面的标签中.
	 * @return
	 */
	@Override
	public void loadDDT() {
		//来自标签的细粒度组件
		Map<String, FinegrainedComponent> staticList = getDisplayFields();
		//来自DDTschema 的细粒度组件
		Map<String, FinegrainedComponent> dynamicList = DDTHelper.getDDTFC(this);
		if(ContainerUtil.isNull(dynamicList) || ContainerUtil.isNull(staticList))
			return;

		Map<String, ViewComponent> vcList = getVcList();
		vcList.putAll(dynamicList);
		setVcList(vcList);
		
		Map<String, FinegrainedComponent> displayFields = new LinkedHashMap<String, FinegrainedComponent>();
		displayFields.putAll( DDTHelper.getSortFCList(staticList, dynamicList));
		setDisplayFields(displayFields);
	}

	@Override
	public void loadMDT() {
		if(ContainerUtil.isNull(getVcList()))
			return;
		
		Var_Page varPage = new Var_Page(Envir.getRequestURI(), getId(), getBcId(), VCType.VC_TYPE_TABLE);
		
		//受全局变量影响的组件
		SimpleContainer simpleContainer = null;
		try {
			simpleContainer = varPage.getRelateContainer(MDTContext.getVarMap());
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		if(simpleContainer == null)
			return;
		
		//变量当前值影响表格的状态
		if(simpleContainer.isDisable() != null)
			getBc().getStatus().setDisable(simpleContainer.isDisable());
		if(simpleContainer.isHidden() != null)
			getBc().getStatus().setHidden(simpleContainer.isHidden());
		if(simpleContainer.isReadonly() != null)
			getBc().getStatus().setReadonly(simpleContainer.isReadonly());
		
		//变量影响表格中的细粒度组件状态
		MDTHelper.var2FC(getDisplayFields(), simpleContainer);
		MDTHelper.var2FC(getInsertFields(), simpleContainer);
		MDTHelper.var2FC(getUpdateFields(), simpleContainer);
		MDTHelper.var2FC(getViewFields(), simpleContainer);
		MDTHelper.var2FC(getEditFields(), simpleContainer);
		//变量影响表格中的控制组件状态
		MDTHelper.var2Btn(getButtonList(), simpleContainer, getId(), getBcId());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void loadData(Object data) {
		Page page = null;
		if(data instanceof Page) {
			page = (Page) data;
		}
		else if(data instanceof List) {
			page = new Page();
			page.setBOList((List) data);
		}

		setPage(page);
	}
	
	/**
	 * 表格中是否有操作是operate的按钮
	 * @param operate 	按钮的operate属性
	 * @return
	 */
	public boolean hasBtn(String operate) {
		Map<String, CommandButton> btnList = getButtonList();
		for(Entry<String, CommandButton> entry : btnList.entrySet()) {
			if(StringUtils.isEqual(operate, entry.getValue().getOperate()))
				return true;
		}
		
		return false;
	}
	
	@Override
	public void free() {
		if(page != null)
			page.free();
		page = null;
		selectionModel = SM_CHECKBOX;
		hasBbar = true;
		hideHeaders = false;
		optWidth = 0f;
		rememberChecked = false;
		pageSize = AppConfig.getPageSize();
		insertWindow = null;
		updateWindow = null;
		viewWindow = null;
		winSizeStr = null;
		addLayoutStr = null;
		editLayoutStr = null;
		viewLayoutStr = null;
		forceFit = null;
		sortStr = null;
		maxHeight = null;
		if(tableSettingBO != null)
			tableSettingBO.free();
		tableSettingBO = null;
		
		if(displayFields != null)
			displayFields.clear();
		if(insertFields != null)
			insertFields.clear();
		if(updateFields != null)
			updateFields.clear();
		if(viewFields != null) 
			viewFields.clear();
		if(editFields != null)
			editFields.clear();
		if(sortMap != null)
			sortMap.clear();
		
		super.free();
	}
	
	/**
	 * 表格设置, 记录了表格列的隐藏/反隐藏, 列宽信息, 列的位置, 仅当开启"记忆表格设置"功能时生效
	 * @return
	 */
	public TableSettingBO getTableSetting() {
		//仅当开启了"保存表格设置"功能时,getTableSettingBO才可能返回非空值
		if(!AppConfig.getTableSetting())
			return null;
		
		if(tableSettingBO == null) {
			ITableSettingService service = (ITableSettingService) SpringConstant.getCTX().getBean("tableSettingService");
			tableSettingBO = service.findTableSetting(UserContext.getUserId(), Envir.getRequestURI(), getId());
		}
		return tableSettingBO;
	}

	public String getBcName() {
		return getBc().getLocalName() + AppLocalization.getLocalization("table.titleSuffix");
	}

	public String getSelectionModel() {
		return selectionModel;
	}

	public void setSelectionModel(String selectionModel) {
		this.selectionModel = selectionModel;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	/**
	 * table中展示的列, key : bind, value : 细粒度组件
	 * @return
	 */
	public Map<String, FinegrainedComponent> getDisplayFields() {
		if(getTableSetting() == null)
			return displayFields;
		
		String[] bopPositionArr = getTableSetting().getBopPositionArr();
		if(StringUtils.isEmpty(bopPositionArr))
			return displayFields;
		
		//为组件排列顺序, 根据bopPositionArr中的元素顺序排序
		Map<String, FinegrainedComponent> sortDisplayFields = new LinkedHashMap<String, FinegrainedComponent>();
		for(String bop : bopPositionArr) {
			if(displayFields.containsKey(bop))
				sortDisplayFields.put(bop, displayFields.get(bop));
		}
		
		//容错处理, 将不在bopPositionArr中的组件添加到结果中
		for(String bop : displayFields.keySet()) {
			if(!sortDisplayFields.containsKey(bop))
				sortDisplayFields.put(bop, displayFields.get(bop));
		}
		
		displayFields = sortDisplayFields;
		return displayFields;
	}

	/**
	 * 如果maxHeight的值是否是bodyHeight.
	 * 如果maxHeight的值为bodyHeight, 则maxHeight的实际值是整个主操作区域的高度.
	 * @return
	 */
	protected boolean isBodyHeight() {
		return StringUtils.isEqual("bodyHeight", getMaxHeight());
	}
	
	public void setDisplayFields(Map<String, FinegrainedComponent> displayFields) {
		this.displayFields = displayFields;
	}

	public boolean isHasBbar() {
		return hasBbar;
	}

	public void setHasBbar(boolean hasBbar) {
		this.hasBbar = hasBbar;
	}

	public Map<String, FinegrainedComponent> getInsertFields() {
		return insertFields;
	}

	public void setInsertFields(Map<String, FinegrainedComponent> insertFields) {
		this.insertFields = insertFields;
	}

	public Map<String, FinegrainedComponent> getViewFields() {
		return viewFields;
	}

	public void setViewFields(Map<String, FinegrainedComponent> viewFields) {
		this.viewFields = viewFields;
	}

	public Map<String, FinegrainedComponent> getUpdateFields() {
		return updateFields;
	}

	public void setUpdateFields(Map<String, FinegrainedComponent> updateFields) {
		this.updateFields = updateFields;
	}

	public Map<String, FinegrainedComponent> getEditFields() {
		return editFields;
	}

	public void setEditFields(Map<String, FinegrainedComponent> editFields) {
		this.editFields = editFields;
	}

	public float getOptWidth() {
		return optWidth;
	}

	public void setOptWidth(float optWidth) {
		this.optWidth = optWidth;
	}

	public TableWindow getInsertWindow() {
		if(insertWindow == null)
			insertWindow = new TableWindow();
		return insertWindow;
	}

	public void setInsertWindow(TableWindow insertWindow) {
		this.insertWindow = insertWindow;
	}

	public TableWindow getUpdateWindow() {
		if(updateWindow == null)
			updateWindow = new TableWindow();
		return updateWindow;
	}
	
	public void setUpdateWindow(TableWindow updateWindow) {
		this.updateWindow = updateWindow;
	}
	
	public TableWindow getViewWindow() {
		if(viewWindow == null)
			viewWindow = new TableWindow();
		return viewWindow;
	}
	
	public void setViewWindow(TableWindow viewWindow) {
		this.viewWindow = viewWindow;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public boolean isCheckBoxMod() {
		return StringUtils.isEqualIgnoreCase(SM_CHECKBOX, selectionModel);
	}

	public boolean isRadioMod() {
		return StringUtils.isEqualIgnoreCase(SM_RADIO, selectionModel);
	}

	public boolean isEmptyMod() {
		return StringUtils.isEqualIgnoreCase(SM_EMPTY, selectionModel);
	}
	
	public boolean isRememberChecked() {
		return StringUtils.isEqualIgnoreCase(SM_CHECKBOX, getSelectionModel()) ? rememberChecked : false;
	}

	public void setRememberChecked(boolean rememberChecked) {
		this.rememberChecked = rememberChecked;
	}

	public String getAddLayoutStr() {
		return addLayoutStr;
	}

	public void setAddLayoutStr(String addLayoutStr) {
		this.addLayoutStr = addLayoutStr;
	}

	public String getEditLayoutStr() {
		return editLayoutStr;
	}

	public void setEditLayoutStr(String editLayoutStr) {
		this.editLayoutStr = editLayoutStr;
	}

	public String getViewLayoutStr() {
		return viewLayoutStr;
	}

	public void setViewLayoutStr(String viewLayoutStr) {
		this.viewLayoutStr = viewLayoutStr;
	}

	public Boolean isForceFit() {
		//当设置为强制自适应时
		if(forceFit != null && forceFit)
			return true;
		//当未设置强制自适应，且控制组件或细粒度组件拥有宽度属性时
		for(CommandButton btn : getButtonList().values()){
			if(btn.getWidth() > 0F)
				return false;
		}
		for(FinegrainedComponent fc : getFcList().values()){
			if(fc.getWidth() > 0F)
				return false;
		}
		//未设置时
		if(forceFit == null)
			return true;
		return forceFit;
	}
	
	public void setForceFit(Boolean forceFit) {
		this.forceFit = forceFit;
	}

	public String getSortStr() {
		return sortStr;
	}

	public void setSortStr(String sortStr) {
		this.sortStr = sortStr;
	}

	public boolean isHideHeaders() {
		return hideHeaders;
	}

	public void setHideHeaders(boolean hideHeaders) {
		this.hideHeaders = hideHeaders;
	}

	public String getWinSizeStr() {
		return winSizeStr;
	}

	public void setWinSizeStr(String winSizeStr) {
		this.winSizeStr = winSizeStr;
	}

	public String getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(String maxHeight) {
		this.maxHeight = maxHeight;
	}
	
}
