package com.qeweb.framework.pl.ext;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.qeweb.framework.bc.prop.Status;
import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.constant.ConstantJSON;
import com.qeweb.framework.common.constant.ConstantSplit;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.MatcherUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pl.PLWebHelper;

/**
 * ExtWebHelper为Ext的PL层提供了统一和简便的字符串拼装方法
 *
 */
public class ExtWebHelper {

	//按钮的高度
	final static public int btnHeight = 25;
	
	/**
	 * 为单值型dom节点添加label, label中可能会包含必填项标记
	 *
	 * @param sbr
	 * @param attrName
	 * @param fc
	 */
	final static public void appendSingleValueHead(StringBuilder sbr,
			String attrName, FinegrainedComponent fc) {
		appendAttrName(sbr, attrName, fc);
		// 非空校验及其描述
		appendBlank(sbr, fc);
	}
	
	final static public void appendAttrName(StringBuilder sbr,
			String attrName, FinegrainedComponent fc) {
		String label = PLWebHelper.requiredTextFlag(fc) + fc.getText();
		String tipText = fc.getTipText();
		if(StringUtils.isNotEmpty(tipText))
			label = "<span title=" + StringUtils.html(tipText) + ">" + label + "</span>";
		
		appendAttr(sbr, attrName, label);
	}
	
	final static public void appendBlank(StringBuilder sbr,
			FinegrainedComponent fc) {
		// 非空校验及其描述
		appendAttr(sbr, "allowBlank", !fc.isRequired());
		appendAttr(sbr, "blankText", AppLocalization.getLocalization(Constant.LOCATION_FORM_REQUIRED));
	}
	
	/**
	 * 为dom节点的属性(attrName)添加值(attrValue), 该方法将改变sbr参数. <br>
	 * 如: 传递 xtype, textfield, 则结果是:"xtype:'textfield'" <br>
	 * appendTail应当在字符串的最后使用, 它将结束整个拼装
	 *
	 * @param sbr
	 * @param attrName
	 * @param attrValue
	 */
	final static public void appendTail(StringBuilder sbr, String attrName, String attrValue) {
		if(StringUtils.isNotEmpty(attrValue))
			sbr.append(attrName).append(":'").append(attrValue).append("'");
	}

	final static public void appendTail(StringBuilder sbr, String attrName, boolean attrValue) {
		appendObjectTail(sbr, attrName, attrValue + "");
	}

	public static void appendTail(StringBuilder sbr, String attrName, int attrValue) {
		appendObjectTail(sbr, attrName, attrValue + "");
	}

	final static public void appendObjectTail(StringBuilder sbr, String attrName, String attrValue) {
		if(StringUtils.isNotEmpty(attrValue))
			sbr.append(attrName).append(":").append(attrValue);
	}

	final static public void appendObjectTail(StringBuilder sbr, String attrName, int attrValue) {
		appendObjectTail(sbr, attrName, attrValue + "");
	}
	
	/**
	 * 为dom节点的属性(attrName)添加值(attrValue), 该方法将改变sbr参数. <br>
	 * 如: 传递 xtype, textfield, 则结果是:"xtype:'textfield',"
	 *
	 * @param sbr
	 * @param attrName
	 * @param attrValue
	 */
	final static public void appendAttr(StringBuilder sbr, String attrName, String attrValue) {
		if(StringUtils.isNotEmpty(attrValue))
			sbr.append(attrName).append(":'").append(attrValue).append("',");
	}

	final static public void appendAttr(StringBuilder sbr, String attrName, double attrValue) {
		sbr.append(attrName).append(":").append(attrValue).append(",");
	}
	
	final static public void appendAttr(StringBuilder sbr, String attrName,	boolean attrValue) {
		appendObjectAttr(sbr, attrName, attrValue + "");
	}
	
	final public static void appendAttr(StringBuilder sbr, String attrName, int attrValue) {
		appendObjectAttr(sbr, attrName, attrValue + "");
	}

	final public static void appendAttr(StringBuilder sbr, String attrName, float attrValue) {
		appendObjectAttr(sbr, attrName, attrValue + "");
	}
	
	final static public void appendObjectAttr(StringBuilder sbr, String attrName, String attrValue) {
		if(StringUtils.isNotEmpty(attrValue))
			sbr.append(attrName).append(":").append(attrValue).append(",");
	}

	/**
	 * 添加状态
	 *
	 * @param sbr
	 * @param status
	 */
	final static public void appendStatus(StringBuilder sbr, Status status) {
		if(status.isDisable())
			appendAttr(sbr, "disabled", true);
		if(status.isReadonly())
			appendAttr(sbr, "readOnly", true);
		if(status.isHidden())
			appendAttr(sbr, "hidden", true);
	}

	/**
	 * 添加状态
	 * @param sbr
	 * @param status
	 * @param hasSourceBtn 是否附带弹出选择按钮
	 */
	final static public void appendStatus(StringBuilder sbr, Status status, boolean hasSourceBtn) {
		if(hasSourceBtn) {
			appendAttr(sbr, "readOnly", true);
			if(status.isDisable())
				appendAttr(sbr, "disabled", true);
			if(status.isHidden())
				appendAttr(sbr, "hidden", true);
		}
		else {
			appendStatus(sbr, status);
		}
	}

	/**
	 * 将可能出现在字符最后的","替换为""
	 *
	 * @param sbr
	 * @return
	 */
	final static public String removeEnd(StringBuilder sbr) {
		return MatcherUtil.removeEnd(sbr, ",");
	}

	/**
	 * 生成数据组
	 *
	 * @param store
	 * @return
	 */
	final static public String getSimpleDataStr(Map<String, String> store) {
		StringBuilder sbr = new StringBuilder();
		appendArrayBegin(sbr);
		for (Entry<String, String> entry : store.entrySet()) {
			if(StringUtils.isEmpty(entry.getValue()))
				continue;
			
			appendArrayBegin(sbr);
			sbr.append("'" + entry.getKey() + "',");
			sbr.append("'" + StringUtils.getUnescapedText(entry.getValue()) + "'");
			appendArrayAfter(sbr);
			sbr.append(",");
		}

		return removeEnd(sbr) + "]";
	}
	
	/**
	 * @param attrName
	 * @param fields
	 * @return
	 * fields: ['index', 'title', 'name', 'id']
	 */
	final static public String getFiledsStr(String attrName, Collection<String> fields) {
		StringBuilder sbr = new StringBuilder();
		sbr.append(attrName).append(":");
		
		appendArrayBegin(sbr);
		sbr.append("'" + ConstantJSON.PAGEBAR_ROWINDEX + "',");
		for (String item : fields) {
			
			sbr.append("'").append(changeTOExtItemName(item)).append("',");
		}

		return removeEnd(sbr) + "]";
	}
	
	/**
	 * 当item是bo.bop时，需要将.替换成#,以便ext正确识别
	 * @param itemName
	 * @return
	 */
	final static public String changeTOExtItemName(String itemName) {
		return itemName.replaceAll("\\.", "#");
	}
	
	/**
	 * appendArray
	 * @param sbr
	 * @param attrName
	 * @param items
	 */
	final static public void appendArray(StringBuilder sbr, String attrName, List<String> items) {
		if(ContainerUtil.isNull(items))
			return;
		
		sbr.append(attrName).append(":[");
		sbr.append(StringUtils.revertColl2Str(items, ConstantSplit.COMMA_SPLIT));
		sbr.append("]");
	}

	/**
	 * 对象定义开始符号
	 * {
	 * @param sbr
	 */
	public static void appendObjectBegin(StringBuilder sbr) {
		sbr.append("{");
	}

	/**
	 * 对象定义结束符号
	 * }
	 * @param sbr
	 */
	public static void appendObjectAfter(StringBuilder sbr) {
		sbr.append("}");
	}

	/**
	 * 数组定义开始符号
	 * [
	 * @param sbr
	 */
	public static void appendArrayBegin(StringBuilder sbr) {
		sbr.append("[");
	}

	/**
	 * 数组定义结束符号
	 * ]
	 * @param sbr
	 */
	public static void appendArrayAfter(StringBuilder sbr) {
		sbr.append("]");
	}

	/**
	 * 数组定义结束符号
	 * ,
	 * @param sbr
	 */
	public static void appendObjectSplit(StringBuilder sbr) {
		sbr.append(",");
	}

	/**
	 * @param sbr
	 * @param content
	 */
	public static void appendContent(StringBuilder sbr, String content) {
		sbr.append(content);
	}

	/**
	 * 创建Ext对象
	 * @param sbr
	 * @param panelName 接受对象
	 * @param panelClass ext对象类名
	 */
	public static void appendCreateObjectBegin(StringBuilder sbr, String panelName, String panelClass){
		if(StringUtils.isNotEmpty(panelName)){
			sbr.append(" var ");
			sbr.append(panelName);
			sbr.append(" = ");
		}
		sbr.append(" new ");
		sbr.append(panelClass);
		sbr.append("(");
	}

	/**
	 * Ext对象接受符号（“)”）
	 * @param sbr
	 */
	public static void appendCreateObjectEnd(StringBuilder sbr){
		sbr.append(")");
	}

	/**
	 * Ext结束符号（“;”）
	 * @param sbr
	 */
	public static void appendSingleEnd(StringBuilder sbr){
		sbr.append(";");
	}

	/**
	 * 当细粒度组件有source子标签时使用, 计算按钮所占位置的长度 
	 * @param collSpan
	 * @return
	 */
	final public static int getFlex(int collSpan) {
		return 38 * collSpan;
	}
}
