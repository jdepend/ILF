package com.qeweb.framework.app.action;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qeweb.framework.bc.prop.BCRange;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.bc.prop.Range;
import com.qeweb.framework.bc.prop.Status;
import com.qeweb.framework.bc.prop.Value;
import com.qeweb.framework.common.utils.ContainerUtil;


/**
 * bop的json对象
 *
 */
public class BopJSON {
	private String bopId;
	private Range range;
	private Value value;
	private Status status;
	
	private List<String> textList = new LinkedList<String>();
	private List<String> valueList = new LinkedList<String>();
	
	public String getBopId() {
		return bopId;
	}
	public void setBopId(String bopId) {
		this.bopId = bopId;
	}
	public Range getRange() {
		return range;
	}
	public void setRange(BCRange range) {
		this.range = range;
		
		Map<String, String> rangeMap = new LinkedHashMap<String, String>();
		Set<Range> rangeList = range.getRangeList();
		//累加枚举型的结果集
		for (Range ran : rangeList) {
			if(ran instanceof EnumRange) {
				EnumRange enumRange = (EnumRange)ran;
				rangeMap.putAll(enumRange.getResult());
			}
		}
		if(ContainerUtil.isNull(rangeMap))
			return;
		
		Set<String> value = rangeMap.keySet();
		for(String v : value) {
			valueList.add(v);
			textList.add(rangeMap.get(v));
		}
		setValueList(valueList);
	}
	public Value getValue() {
		return value;
	}
	public void setValue(Value value) {
		this.value = value;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public List<String> getTextList() {
		return textList;
	}
	public void setTextList(List<String> textList) {
		this.textList = textList;
	}
	public List<String> getValueList() {
		return valueList;
	}
	public void setValueList(List<String> valueList) {
		this.valueList = valueList;
	}
	
	
}
