package com.qeweb.framework.pal.handler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.constant.ConstantParam;
import com.qeweb.framework.common.constant.ConstantSplit;
import com.qeweb.framework.common.constant.ConstantTag;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.coarsegrained.Table;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.handler.bean.ContainerBean;
import com.qeweb.framework.pal.handler.bean.TableBean;

/**
 * TableInitHandler 的初始化处理者
 */
public class TableInitHandler extends ContainerInitHandler {

	@Override
	public void initEnd(Container container, ContainerBean containerBean) {
		Table table = (Table)container;
		TableBean tableBean = (TableBean)containerBean;
		
		table.setParent(tableBean.getParent());
		initDisplayFields(table, StringUtils.removeAllSpace(tableBean.getDisplay()), StringUtils.removeAllSpace(tableBean.getEditCell()));
		
		String sm = Envir.getRequest().getParameter(ConstantParam.SOURCEBTN_SM);
		if(StringUtils.isNotEmpty(sm)) {
			table.setSelectionModel(sm);
			table.setFill(true);
		}
		else if(StringUtils.isNotEmpty(tableBean.getSm())){
			table.setSelectionModel(tableBean.getSm());
		}
		
		table.setLayoutStr(tableBean.getLayout());
		table.setAddLayoutStr(tableBean.getAddLayout());
		table.setEditLayoutStr(tableBean.getEditLayout());
		table.setViewLayoutStr(tableBean.getViewLayout());
		table.setWinSizeStr(tableBean.getWin());
		table.setRememberChecked(StringUtils.convertToBool(tableBean.getRememberChecked()));
		table.setSortStr(tableBean.getSort());
		table.setMaxHeight(tableBean.getMaxHeight());
		if(StringUtils.isNotEmpty(tableBean.getHeight()))
			table.setHeight(StringUtils.convertToFloat(tableBean.getHeight()));
		if(StringUtils.isNotEmpty(tableBean.getAutoScroll()))
			table.setForceFit(!StringUtils.convertToBool(tableBean.getAutoScroll()));
		if(StringUtils.isNotEmpty(tableBean.getPageSize())){
			table.setPageSize(StringUtils.convertToInteger(tableBean.getPageSize()));
			table.getBc().setPageSize(StringUtils.convertToInteger(tableBean.getPageSize()));
		}
		
		if(StringUtils.isNotEmpty(tableBean.getHasBbar()))
			table.setHasBbar(StringUtils.convertToBool(tableBean.getHasBbar()));
		if(StringUtils.isNotEmpty(tableBean.getHideHeaders())) 
			table.setHideHeaders(StringUtils.convertToBool(tableBean.getHideHeaders()));
	}
	
	/**
	 * 根据display设置table的显示字段、弹出框的显示字段及双击可编辑字段
	 * @param table
	 * @param display
	 *  @param editCell
	 */
	static private void initDisplayFields(Table table, String display, String editCell) {
		//arr中的每个元素是  field1=table:edit,insert,update,view 的格式
		String[] arr = StringUtils.split(display, ConstantSplit.SEMICOLON_SPLIT);
		
		Map<String, FinegrainedComponent> fcList = table.getFcList();
		table.setDisplayFields(getDspisplayResult(arr, fcList, ConstantTag.TAG_TABLE_DISPLAY_TABLE));
		table.setInsertFields(getDspisplayResult(arr, fcList, ConstantTag.TAG_TABLE_DISPLAY_INSERT));
		table.setUpdateFields(getDspisplayResult(arr, fcList, ConstantTag.TAG_TABLE_DISPLAY_UPDATE));
		table.setViewFields(getDspisplayResult(arr, fcList, ConstantTag.TAG_TABLE_DISPLAY_VIEW));
		
		Map<String, FinegrainedComponent> editFields = getDspisplayResult(arr, fcList, ConstantTag.TAG_TABLE_DISPLAY_EDIT);
		addEditFields(editFields, editCell, fcList);
		table.setEditFields(editFields);
	}
	
	/**
	 * 获取待展示的字段
	 * @param arr
	 * @param fcList
	 * @param sign 展示类型  table/insert/update/view
	 * @return
	 */
	static private Map<String, FinegrainedComponent> getDspisplayResult(String[] arr,
			Map<String, FinegrainedComponent> fcList, String sign) {
		//默认(display为空)展示table中的所有细粒度组件 
		if(StringUtils.isEmpty(arr)) {
			if(StringUtils.isEqual(ConstantTag.TAG_TABLE_DISPLAY_EDIT, sign))				
				return new HashMap<String, FinegrainedComponent>();
			else
				return fcList;
		}
		
		Map<String, FinegrainedComponent> result = new LinkedHashMap<String, FinegrainedComponent>();
		for(String str : arr) { 
			//fieldExpression[0] : field1, fieldExpression[1] : table:edit,insert,update,view
			String[] fieldExpression = StringUtils.split(str, ConstantSplit.EQUAL_SPLIT);
			if(fieldExpression[1].indexOf(sign) != -1 && fcList.keySet().contains(fieldExpression[0])) 
				result.put(fieldExpression[0], fcList.get(fieldExpression[0]));
		}
		return result;
	}
	
	/**
	 * 将editCell(可编辑单元格表达式)中的信息并入editFields
	 * @param editFields
	 * @param editCell
	 * @param fcList
	 */
	static private void addEditFields(Map<String, FinegrainedComponent> editFields, String editCell, 
			Map<String, FinegrainedComponent> fcList) {
		if(StringUtils.isEmpty(editCell))
			return;
		
		String[] arr = StringUtils.split(StringUtils.removeAllSpace(editCell), ConstantSplit.COMMA_SPLIT);
		for (String cell : arr) {
			editFields.put(cell, fcList.get(cell));
		}
	}
}
