package com.qeweb.framework.pl.ext.coarsegrained;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.bc.prop.BCRange;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.bc.prop.Range;
import com.qeweb.framework.common.constant.ConstantAppProp;
import com.qeweb.framework.common.constant.ConstantDataIsland;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.PropertyUtils;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.manager.DisplayType;
import com.qeweb.framework.pal.PageContextInfo;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.coarsegrained.Table;
import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.finegrained.enumcomp.Select;
import com.qeweb.framework.pl.ext.ExtWebHelper;

public class ExtContainerHelper {

	/**
	 * getContainerPanelName，供ext形式的组粒度组件使用
	 * @param container
	 * @return
	 */
	final static public String getContainerPanelName(Container container) {
		return container.getId() + ConstantDataIsland.COMPONENTSPLIT + container.getBcId();
	}

	/**
	 * @param cols Collection&ltCommandButton>
	 * @param out
	 *
	 * 画出按钮<br>
	 * buttons : [new Ext.Button({
					id : 'abc',
					text:'toggle button '
				}),
				new Ext.Button({
					id : 'aaa',
					text:'toggle button '
				})]
	 */
	final static public void paintButtons(Collection<CommandButton> cols, PageContextInfo out){
		paintButtons(cols, false, null, out);
	}
	
	/**
	 * 画出树结构的选择按钮.
	 * 通过sourceBtn弹出选择树, 此时树中画出选择按钮, 以便将选中的值回填到父页面.<br>
	 * @param cols
	 * @param isFill
	 * @param treeId
	 * @param out
	 */
	final static public void paintButtons(Collection<CommandButton> cols, boolean isFill, String treeId, PageContextInfo out){
		if(ContainerUtil.isNull(cols) && !isFill)
			return;

		out.write("buttons:[");
		if(ContainerUtil.isNotNull(cols)){
			Object[] btns = cols.toArray();
			for(int i = 0; i < btns.length - 1; i++) {
				((CommandButton) btns[i]).paint();
				out.write(",");
			}
			((CommandButton) btns[btns.length - 1]).paint();
		}
		paintSMBtn(isFill, treeId, ContainerUtil.isNull(cols), out);
		out.write("]");
	}

	/**
	 * 画出 table级按钮.
	 * @param cols
	 * @param isFill
	 * @param gridId
	 * @param btnColumns
	 * @param out
	 *
	 * tbar: [{
			xtype:'buttongroup',
			columns:6,//每行显示几列（犹如table画法）
			frame: false,
		 	items : [
				new Ext.Button({
					id : 'showAll-DemoBO_ShowAllFC-camera',
					name : 'showAll-DemoBO_ShowAllFC-camera',
					iconCls : 'modifyIcon',
					text : '拍照'
				}),
				'-',
				new Ext.Button({
					id : 'showAll-DemoBO_ShowAllFC-camera2',
					name : 'showAll-DemoBO_ShowAllFC-camera2',
					iconCls : 'modifyIcon',
				text : '拍照2'
				})
			]
		}]
	 */
	final static public void paintTbars(Collection<CommandButton> cols, boolean isFill, String gridId, 
			int btnColumns, PageContextInfo out) {
		if(ContainerUtil.isNull(cols) && !isFill)
			return;
		
		out.write("tbar:[");
		if(StringUtils.isEqual(ConstantAppProp.TBTNALIGH_RIGHT, DisplayType.getTbtnAligh()))
			out.write("'->',");
		out.write("{xtype:'buttongroup',columns:" + btnColumns + ",frame: false,items : [");
		if(ContainerUtil.isNotNull(cols)){
			Object[] btns = cols.toArray();
			for(int i = 0; i < btns.length - 1; i++) {
				((CommandButton) btns[i]).paint();
				out.write(",");
			}
			((CommandButton) btns[btns.length - 1]).paint();
		}
		paintSMBtn(isFill, gridId, ContainerUtil.isNull(cols), out);
		out.write("]}]");
	}

	/**
	 * 画出selectionModel 按钮, 即弹出页面的选择按钮.
	 * 通过sourceBtn弹出选择页面, 此时应在相应组件中画出"选择"按钮, 以便将选中的信息回填到父页面.
	 */
	private static void paintSMBtn(boolean isFill, String gridId, boolean hasBtns, PageContextInfo out) {
		if(isFill) {
			String result = " new Ext.Button({ text : '<font color=red><b>"
					+ AppLocalization.getLocalization("select1")
					+ "</b></font>', iconCls:'check'," + "'handler' : function(){doSelect(" + gridId + ");} " + "})";

			out.write(hasBtns ? result : ("," + result));
		}
	}
	
	/**
	 * 创建table的单元格校验类型
	 * 
	 * @param fc
	 * @return
	 */
	final static public String createGridColEditor(Table table, FinegrainedComponent fc) {
		BCRange bcRange = fc.getBc().getRange();
		int maxLength = bcRange.getMaxLength();
		int minLength = bcRange.getMinLength();
		
		try {
			Class<?> clasz = PropertyUtils.getPropertyType(table.getBc(), fc.getBcId());
			if(fc instanceof Select) 
				return getSelectCell(fc);
			if(clasz == Integer.class ||  clasz == int.class || clasz == Long.class ||  clasz == long.class)
				return "new Ext.form.NumberField(" + getEditorItem(minLength, maxLength) + ")";
			else if(clasz == Date.class || clasz == Timestamp.class)
				return "new Ext.form.DateField({format:'Y-m-d'})";
			else 
				return "new Ext.form.TextField(" + getEditorItem(minLength, maxLength) + ")";
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}	
		
		return "new Ext.form.TextField(" + getEditorItem(minLength, maxLength) + ")";
	}

	private static String getSelectCell(FinegrainedComponent fc) {
		StringBuilder sbr = new StringBuilder();
		ExtWebHelper.appendContent(sbr, "new Ext.form.ComboBox({");
		ExtWebHelper.appendAttr(sbr, "typeAhead", true);
		ExtWebHelper.appendAttr(sbr, "triggerAction", "all");
		ExtWebHelper.appendAttr(sbr, "forceSelection", true);
		ExtWebHelper.appendAttr(sbr, "selectOnFocus", true);
		ExtWebHelper.appendAttr(sbr, "lazyRender", true);
		ExtWebHelper.appendAttr(sbr, "mode", "local");
		ExtWebHelper.appendAttr(sbr, "displayField", fc.getBcId() + "-text");
		ExtWebHelper.appendAttr(sbr, "valueField", fc.getBcId() + "-value");
		sbr.append("store:new Ext.data.ArrayStore({");
		List<String> items = new ArrayList<String>();
		items.add("'" + fc.getBcId() + "-value'");
		items.add("'" + fc.getBcId() + "-text'");
		ExtWebHelper.appendArray(sbr, "fields", items);
		ExtWebHelper.appendObjectSplit(sbr);
		sbr.append("data : [");
		for (Range range : fc.getBc().getRange().getRangeList()) {
			if(range instanceof EnumRange){
				Map<String, String> enumResult = ((EnumRange) range).getResult();
				for (String enumKey : enumResult.keySet()) {
					ExtWebHelper.appendArrayBegin(sbr);
					sbr.append("'" + enumKey + "'");
					ExtWebHelper.appendObjectSplit(sbr);
					sbr.append("'" + enumResult.get(enumKey) + "'");
					ExtWebHelper.appendArrayAfter(sbr);
					ExtWebHelper.appendObjectSplit(sbr);
				}
				sbr.delete(sbr.length() - 1, sbr.length());
				ExtWebHelper.appendObjectSplit(sbr);
			}
		}
		sbr.delete(sbr.length() - 1, sbr.length());
		ExtWebHelper.appendArrayAfter(sbr);
		ExtWebHelper.appendObjectAfter(sbr);
		ExtWebHelper.appendCreateObjectEnd(sbr);
		ExtWebHelper.appendObjectAfter(sbr);
		ExtWebHelper.appendCreateObjectEnd(sbr);
		return sbr.toString();
	}
	
	static private String getEditorItem(int minLength, int maxLength) {
		return "{minLength:" + minLength + ",maxLength:" + maxLength + "}";
	}
}
