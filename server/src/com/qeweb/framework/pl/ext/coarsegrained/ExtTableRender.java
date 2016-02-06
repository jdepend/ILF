package com.qeweb.framework.pl.ext.coarsegrained;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.sysbop.DateBOP;
import com.qeweb.framework.bc.sysbop.ImageBOP;
import com.qeweb.framework.bc.sysbop.StatusBOP;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.control.CommandButton;

/**
 * 表格的renderer
 */
public class ExtTableRender {
	
	/**
	 * 行级按钮
	 * @param btns
	 * @return
	 */
	final static public String getRwoBtnRender(List<CommandButton> btns) {
		if(ContainerUtil.isNull(btns))
			return "";

		StringBuilder sbr = new StringBuilder();
		sbr.append("function(value, cellmeta, record, rowIndex) {");
		sbr.append("return '<center>' + ");
		for(CommandButton btn : btns) {
			sbr.append(" CellRender.rowBtnRender('").append(btn.getId()).append("', '")
				.append(btn.getName()).append("', '").append(btn.getText()).append("', '").append(btn.getIcon()).append("', rowIndex + 1) + ");
		}
		sbr.append("'</center>';}");
		
		return sbr.toString();
	}
	
	/**
	 * 地址信息
	 * function(value) {
	 * 		return CellRender.locationRender(value, tableId);
	 * }
	 * @param tableId
	 * @return
	 */
	final static public String getLocationRender(String tableId) {
		StringBuilder sbr = new StringBuilder();
		sbr.append("function(value) {");
		sbr.append("return CellRender.locationRender(value, '").append(tableId).append("');");
		sbr.append("}");
		return sbr.toString();
	}

	/**
	 * 超链接控件
	 * function(value, cell, record, rowIndex)
	 * 		CellRender.cellStyleRender(value, cell);
	 		return CellRender.anchorRender(value, cell, record, rowIndex, tableId, bopBind); 
		}
	 * @param tableId
	 * @param bind
	 * @param isEditor
	 * @return
	 */
	final static public String getAnchorRender(String tableId, String bind, boolean isEditor) {
		StringBuilder sbr = new StringBuilder();
		sbr.append("function(value, cell, record, rowIndex) {");
		if(isEditor) 
			sbr.append("CellRender.cellStyleRender(value, cell);");
		sbr.append("return CellRender.anchorRender(value, cell, record, rowIndex, '").append(tableId).append("', '").append(bind).append("');");
		sbr.append("}");
		return sbr.toString();
	}
	
	/**
	 * 图标类型(statusBOP)
	 * fuction(value, cell) {
	 * 		CellRender.cellStyleRender(value, cell);
	 * 		return CellRender.iconRender(value, cell, imgStore, valueStore);
	 * }
	 * @param statusBop
	 * @param isEditor
	 * @return
	 */
	@SuppressWarnings("static-access")
	final static public String getStatusIconRender(StatusBOP statusBop, boolean isEditor) {
		if(StringUtils.isNotEmpty(statusBop.getKey())) {
			//图片路径集合
			Map<String, String> imgMap = statusBop.getDisplay().get(statusBop.getKey());
			//枚举值集合
			Map<String, String> valueMap = statusBop.getDictMap().get(statusBop.getKey());
			
			String imgStore = getStroeArr(imgMap, "imgStore");
			String valueStore = getStroeArr(valueMap, "valueStore");
			
			StringBuilder sbr = new StringBuilder();
			sbr.append("function(value, cell){").append(imgStore).append(valueStore); 
			if(isEditor) 
				sbr.append("CellRender.cellStyleRender(value, cell);");
			sbr.append("return CellRender.iconRender(value, cell, imgStore, valueStore);}"); 
			
			return sbr.toString();
		}
		else if(isEditor) {
			return "CellRender.cellStyleRender";
		}
		else {
			return null;
		}
	}
	
	/**
	 * 日期类型
	 * function(value, cell) {
	 * 		CellRender.cellStyleRender(value, cell);
	 * 		return CellRender.dateRender(value, format);
	 * }
	 * @param dateBop
	 * @param isEditor
	 * @return
	 */
	final static public String getDateRender(DateBOP dateBop, boolean isEditor) {
		StringBuilder sbr = new StringBuilder();
		sbr.append("function(value, cell){");
		if(isEditor) 
			sbr.append("CellRender.cellStyleRender(value, cell);");
		sbr.append("return CellRender.dateRender(value, '").append(dateBop.getFormat()).append("');");
		sbr.append("}");
		
		return sbr.toString();
	}
	
	/**
	 * 日期控件
	 * function(value) {
	 * 		CellRender.cellStyleRender(value, cell);
	 * 		return CellRender.dateFCRender(value);
		}
	 * @param isEditor
	 * @return
	 */
	final static public String getDateRender(boolean isEditor) {
		StringBuilder sbr = new StringBuilder();
		sbr.append("function(value, cell){");
		if(isEditor) 
			sbr.append("CellRender.cellStyleRender(value, cell);");
		sbr.append("return CellRender.dateFCRender(value);");
		sbr.append("}");
		
		return sbr.toString();
	}
	
	/**
	 * 图片类型
	 * function(value, cell) {
			CellRender.cellStyleRender(value, cell);
	 		return CellRender.imageRender(value, isAdaptive, height, width);
		}
	 * @param ImageBOP
	 * @param isEditor
	 * @return
	 */
	final static public String getImageRender(ImageBOP imageBOP, boolean isEditor) {
		StringBuilder sbr = new StringBuilder();
		sbr.append("function(value, cell){");
		if(isEditor) 
			sbr.append("CellRender.cellStyleRender(value, cell);");
		
		String height = imageBOP.getHeight() == null ? "" : imageBOP.getHeight();
		String width = imageBOP.getWidth() == null ? "" : imageBOP.getWidth();
		
		sbr.append("return CellRender.imageRender(value, ")
			.append(imageBOP.isAdaptive()).append(",'").append(height).append("','")
			.append(width).append("');");
		sbr.append("}");
		
		return 	sbr.toString();
	}
	
	/**
	 * checkbox
	 * function(value, cell){
	 * 		CellRender.cellStyleRender(value, cell);
	 		var bopStore = [];valueStore['0'] = '未确认';valueStore['1']='已确认'; 
			return CellRender.checkboxRender(value, bopStore);
		}
	 * @param bop
	 * @param isEditor
	 * @return
	 */
	final static public String getCheckboxRender(BOProperty bop, boolean isEditor) {
		StringBuilder sbr = new StringBuilder();
		sbr.append("function(value, cell){");
		if(isEditor) 
			sbr.append("CellRender.cellStyleRender(value, cell);");
		sbr.append(getStroeArr(bop, "bopStore"));
		sbr.append(" return CellRender.checkboxRender(value, bopStore);");
		sbr.append("}");
		
		return sbr.toString();
	}
	
	/**
	 * 枚举值
	 * function(value, cell){
	 * 		CellRender.cellStyleRender(value, cell);
	 		var bopStore = [];valueStore['0'] = '未确认';valueStore['1']='已确认'; 
			return CellRender.enumRender(value, bopStore);
		}
	 * @param bop
	 * @param isEditor
	 * @return
	 */
	final static public String getEnumRender(BOProperty bop, boolean isEditor) {
		StringBuilder sbr = new StringBuilder();
		sbr.append("function(value, cell){");
		if(isEditor) 
			sbr.append("CellRender.cellStyleRender(value, cell);");
		sbr.append(getStroeArr(bop, "bopStore"));
		sbr.append(" return CellRender.enumRender(value, bopStore);");
		sbr.append("}");
		
		return sbr.toString();
	}

	/**
	 * 将bop的枚举值转换为名为storeName的Array
	 * @param bop
	 * @param storeName
	 * @return
	 * <p>
	 * 例: 如果bop的枚举值为 0-未确认, 1-已确认, storeName 为 myStore.
	 * 则getStroeArr所做的操作: <br>
	 * var myStore=[]; myStore[0]='未确认'; myStore[1]='已确认';
	 */
	private static String getStroeArr(BOProperty bop, String storeName) {
		return getStroeArr(bop.toMap(), storeName);
	}
	
	/**
	 * 将枚举值转换为名为storeName的Array
	 * @param dataMap		枚举值
	 * @param storeName
	 * @return
	 * @see getStroeArr(BOProperty bop, String storeName)
	 */
	private static String getStroeArr(Map<String, String> dataMap, String storeName) {
		StringBuilder bopStore = new StringBuilder();
		bopStore.append("var ").append(storeName).append(" = [];");
		for(Entry<String, String> entry : dataMap.entrySet()) {
			if(StringUtils.isNotEmpty(entry.getKey())) {
				bopStore.append(storeName).append("['").append(entry.getKey()).append("']='")
					.append(StringUtils.getUnescapedText(entry.getValue())).append("';");
			}
		}
		
		return bopStore.toString();
	}
}
