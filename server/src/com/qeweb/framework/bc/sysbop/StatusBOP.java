package com.qeweb.framework.bc.sysbop;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.bc.prop.StatusValue;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;

/**
 *	列表显示的状态可以用图片来代替方案
 *	数据库SYS_DICT用于记录用户定义的状态和每个状态具体的值，展示文字，展示图片路径
 *	可以实现列表中状态列以不同图片区分状态
 *	使用：用户创建一个继承StatusBOP的状态bop，重写getKey方法即可。
 */
public class StatusBOP extends BOProperty {

	private static final long serialVersionUID = 5924032201798934950L;
	private StatusValue value = new StatusValue(); // 值
	private String dictKey; 		// 状态-字典key
	private String statusValue; 	// 状态的值
	private String statusValueText; // 状态展示文字
	private String imgPath; 		// 状态展示图片的路径
	//key : 典表中的SYS_DICT.dictKey的值;  value : 枚举值
	//例: confirmstatus={0='未确认', 1='已确认'}
	private static Map<String, LinkedHashMap<String, String>> dictMap = new HashMap<String, LinkedHashMap<String,String>>();
	//key : 典表中的SYS_DICT.dictKey的值;  value : 枚举值-图片路径
	//例: confirmstatus={0=/framework/images/common/no.png, 1=/framework/images/common/ok.png}
	private static Map<String, LinkedHashMap<String, String>> display = new HashMap<String, LinkedHashMap<String,String>>();

	@Override
	public void init() {
		if (StringUtils.isEmpty(getKey()))
			return;
		
		initDict();
		initVSR();
	}

	/**
	 * 初始化字典池
	 */
	@SuppressWarnings("unchecked")
	private void initDict() {
		if(getDictMap().get(getKey()) != null)
			return;
		
		LinkedHashMap<String,String> localDictMap = new LinkedHashMap<String,String>();
		LinkedHashMap<String,String> localDisplay = new LinkedHashMap<String,String>();
		// 查询状态的值、展示文字、展示图片
		DetachedCriteria dc = DetachedCriteria.forClass(StatusBOP.class);
		dc.add(Restrictions.eq("dictKey", getKey()));
		List<StatusBOP> list = getDao().findByCriteria(dc);
		for (StatusBOP statusBOP : list) {
			localDictMap.put(statusBOP.getStatusValue(), statusBOP.getStatusValueText());
			localDisplay.put(statusBOP.getStatusValue(), statusBOP.getImgPath());
		}
		
		//将查询结果添加至map池中
		dictMap.put(getKey(), localDictMap);
		display.put(getKey(), localDisplay);
	}

	/**
	 * 初始化值、状态、范围
	 */
	public void initVSR() {
		LinkedHashMap<String,String> localDictMap = getDictMap().get(getKey());
		LinkedHashMap<String,String> localDict = new LinkedHashMap<String, String>();
		
		EnumRange.addPlease(localDict);
		localDict.putAll(localDictMap);

		EnumRange dictRange = new EnumRange();
		dictRange.setResult(localDict);
		getRange().addRange(dictRange);
	}
	
	/**
	 * 字典表中的SYS_DICT.dictKey的值, 可根据SYS_DICT.dictKey获取具体的状态信息,
	 * 包括枚举值 和 图片路径
	 * @return
	 */
	public String getKey() {
		return "";
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}


	public void setDictKey(String dictKey) {
		this.dictKey = dictKey;
	}

	public void setValue(StatusValue value) {
		this.value = value;
	}

	public StatusValue getValue() {
		value.setDisplay(display.get(getKey()));
		return value;
	}

	public String getStatusValueText() {
		return AppLocalization.getLocalization(statusValueText);
	}

	public void setStatusValueText(String statusValueText) {
		this.statusValueText = statusValueText;
	}

	public String getDictKey() {
		return dictKey;
	}

	public static void setDictMap(Map<String, LinkedHashMap<String, String>> dictMap) {
		StatusBOP.dictMap = dictMap;
	}

	public static Map<String, LinkedHashMap<String, String>> getDictMap() {
		return dictMap;
	}

	public static Map<String, LinkedHashMap<String, String>> getDisplay() {
		return display;
	}

	public static void setDisplay(Map<String, LinkedHashMap<String, String>> display) {
		StatusBOP.display = display;
	}

}
