package com.qeweb.framework.common;

import java.util.List;

import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.utils.ContainerUtil;

/**
 * BaseDao分页工具类
 * 
 * @author Pengt
 * 
 */
public class Page {

	private int pageSize = AppConfig.getPageSize();	//每页显示的记录数

	@SuppressWarnings("rawtypes")
	private List items;

	private int totalCount;

	private int[] indexes = new int[0];

	private int startIndex = 0;		//记录行标识
	
	private List<? extends BusinessObject> boList;
	
	public Page(){
		
	}
	
	/**
	 * 可设定长度，开始标识，每页显示记录
	 * 
	 * @param items
	 * @param totalCount
	 * @param pageSize	每页显示的记录数
	 * @param startIndex
	 */
	@SuppressWarnings("rawtypes")
	public Page(List items, int totalCount, int pageSize, int startIndex) {
		setPageSize(pageSize);
		setTotalCount(totalCount);
		if(ContainerUtil.isNull(items)) {
			setItems(items);
		}
		else if(items.size() < totalCount) {
			setItems(items);
		}
		else {
			int endIndex = startIndex + pageSize;
			if(endIndex > totalCount)
				endIndex = totalCount;
			setItems(items.subList(startIndex, endIndex));
		}
		
		setStartIndex(startIndex);
	}
	
	/**
	 * 存储 简单的展示数据
	 * @param simpleData
	 */
	public void setBOList(List<? extends BusinessObject> boList){
		this.boList = boList;
	}
	
	public List<? extends BusinessObject> getBOList(){
		return this.boList;
	}

	@SuppressWarnings("rawtypes")
	public List getItems() {
		return items;
	}

	@SuppressWarnings("rawtypes")
	public void setItems(List items) {
		this.items = items;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		if (totalCount > 0) {
			this.totalCount = totalCount;
			int count = totalCount / pageSize;
			if (totalCount % pageSize > 0)
				count++;
			indexes = new int[count];
			for (int i = 0; i < count; i++) {
				indexes[i] = pageSize * i;
			}
		} else {
			this.totalCount = 0;
		}
	}

	public int[] getIndexes() {
		return indexes;
	}

	public void setIndexes(int[] indexes) {
		this.indexes = indexes;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		if (totalCount <= 0)
			this.startIndex = 0;
		else if (startIndex >= totalCount)
			this.startIndex = indexes[indexes.length - 1];
		else if (startIndex < 0)
			this.startIndex = 0;
		else 
			this.startIndex = indexes[startIndex / pageSize];
	}

	public int getNextIndex() {
		int nextIndex = getStartIndex() + pageSize;
		if (nextIndex >= totalCount)
			return getStartIndex();
		else
			return nextIndex;
	}

	public int getPreviousIndex() {
		int previousIndex = getStartIndex() - pageSize;
		if (previousIndex < 0)
			return 0;
		else
			return previousIndex;
	}

	public final void free() {
		if(boList != null)  {
			for(BusinessObject bo : boList) {
				bo.free();
			}
			boList.clear();
		}
		if(items != null) {
			for(Object obj : items){
				if(obj instanceof BusinessComponent)
					((BusinessComponent)obj).free();
			}
			items.clear();
		}
	}
}
