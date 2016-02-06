package com.qeweb.framework.bc.prop;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 
 *
 */
public class BCRange extends Range {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6289520658335041142L;

	//range集合、逻辑操作
	private Map<Range, LogicEnum> rangeList = new LinkedHashMap<Range, LogicEnum>();
	
	//是否必填
	private boolean required = false;	
	//最大长度
	private int maxLength = -1;
	//最小长度
	private int minLength = -1;
	
	public enum LogicEnum {
		AND,OR,NOT
	}
	
	/**
	 * 添加一个范围，默认逻辑操作为与操作(AND)
	 * 通常只添加第一个范围或添加一个AND范围的时候使用此方法
	 * @param range
	 */
	public void addRange(Range range){
		if(!(range instanceof BCRange)){
			rangeList.put(range, LogicEnum.AND);
		}
	}
	
	/**
	 * 清空范围
	 */
	public void clear() {
		rangeList.clear();
	}
	
	/**
	 * 添加一个范围，包含一个逻辑操作
	 * @param range
	 * @param logicEnum
	 */
	public void addRange(Range range, LogicEnum logicEnum){
		if((range instanceof BCRange))
			return;
		
		for(Range r:getRangeList()){
			if(r.getClass().equals(range.getClass()))
				rangeList.remove(r);
		}
		rangeList.put(range, logicEnum);
	}

	/**
	 * 是否是必填
	 */
	public boolean isRequired() {
		return required;
	}
	
	/**
	 * 设置是必填
	 * @param required
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}
	
	@Override
	public boolean isIN(Value value) {
		boolean isIn = checkLength(value.getValue());
		if(!isIn) 
			return false;
		
		if(ContainerUtil.isNull(rangeList))
			return true;
		
		Iterator<Range> ranges = rangeList.keySet().iterator();
		
		//执行第一个范围校验
		if(ranges.hasNext()) {
			Range range = ranges.next();
			isIn = range.isIN(value);
			if(!isIn) {
				validateMessage = range.getValidateMessage();
			}
		}
		
		//执行其余的(带逻辑符号的)范围校验
		while (ranges.hasNext()) {
			Range range = ranges.next();
			LogicEnum logic = rangeList.get(range);
			
			boolean vrt = range.isIN(value);
			switch (logic) {
			case AND:
				isIn = isIn && vrt;
				break;
			case OR:
				isIn = isIn || vrt;
				break;
			case NOT:
				isIn = isIn && !vrt;
				break;
			}
			if(!isIn) {
				validateMessage = range.getValidateMessage();
			}
		}
		
		return isIn;
	}
	
	/**
	 * 长度校验
	 * @param value
	 * @return
	 */
	private boolean checkLength(String value) {
		boolean result = true;
		
		if(maxLength > 0)
			result = StringUtils.trim(value).length() <= maxLength;
		if(minLength > 0 )
			result = StringUtils.trim(value).length() >= minLength;
			
		return result;
	}
	
	/**
	 * 获取逻辑规则(与/或/非)
	 * @param range
	 * @return
	 */
	public LogicEnum rangeLogic(Range range) {
		return rangeList.get(range);
	}
	

	public Set<Range> getRangeList() {
		return rangeList.keySet();
	}

	public void setRangeList(Map<Range, LogicEnum> rangeList) {
		this.rangeList = rangeList;
	}
	
	/**
	 * 是否存在EnumRange
	 * @return
	 */
	public boolean hasEnumRange() {
		return hasRange(EnumRange.class);
	}
	
	/**
	 * 是否存在SequenceRange
	 * @return
	 */
	public boolean hasSequenceRange() {
		return hasRange(SequenceRange.class);
	}
	
	/**
	 * 是否存在LogicRange
	 * @return
	 */
	public boolean hasLogicRange() {
		return hasRange(LogicRange.class);
	}
	
	/**
	 * 是否存在TreeRange
	 * @return
	 */
	public boolean hasTreeRange() {
		return hasRange(TreeRange.class);
	}
	
	/**
	 * 获取treeRange
	 * @return
	 */
	public TreeRange getTreeRange() {
		Set<Range> rangeSet = getRangeList();
		if(ContainerUtil.isNull(rangeSet))
			return null;
		
		for (Range temp : rangeSet) {
			if(temp instanceof TreeRange)
				return (TreeRange) temp;
		}
		
		return null;
	}
	
	/**
	 * 获取所有EnumRange
	 * @return
	 */
	public List<EnumRange> getAllEnumRange() {
		Set<Range> rangeSet = getRangeList();
		if(ContainerUtil.isNull(rangeSet))
			return null;
		
		List<EnumRange> result = new LinkedList<EnumRange>();
		for (Range temp : rangeSet) {
			if(temp instanceof EnumRange && !(temp instanceof TreeRange))
				result.add((EnumRange)temp);
		}
		
		return result;
	}
	
	/**
	 * 获取第一个连续型范围
	 * @return
	 */
	public SequenceRange getFirstSequence() {
		Set<Range> rangeSet = getRangeList();
		if(ContainerUtil.isNull(rangeSet))
			return null;
		
		for (Range temp : rangeSet) {
			if(temp instanceof SequenceRange)
				return (SequenceRange)temp;
		}
		
		return null;
	}
	
	private boolean hasRange(Class<? extends Range> clasz) {
		Set<Range> rangeSet = getRangeList();
		if(ContainerUtil.isNull(rangeSet))
			return false;
		
		for (Range temp : rangeSet) {
			if(clasz.isAssignableFrom(temp.getClass()))
				return true;
		}
		
		return false;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}
	
}

