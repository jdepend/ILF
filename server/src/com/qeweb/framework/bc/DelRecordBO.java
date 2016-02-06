package com.qeweb.framework.bc;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.common.utils.StringUtils;

/**
 * 动态新增/删除行时，保存删除记录的id
 * 
 */
public class DelRecordBO extends BusinessObject {

	private static final long serialVersionUID = -7509808688223046622L;
	private BusinessObject bindBO; 	// 保存绑定的bo,用于分区同页面多个表格动态新增/删除行时删除的id对应bo
	private String delIds; 			// 保存删除的id，以逗号 , 分割

	/**
	 * 获取删除的id.可以用于条件IN查询
	 */
	public List<Long> getDelPks() {
		String[] delIds = StringUtils.split(getDelIds(), ",");
		if(StringUtils.isEmpty(delIds))
			return null;
		
		List<Long> pks = new LinkedList<Long>();
		for(String id : delIds) {
			pks.add(StringUtils.convertToLong(id));
		}
		
		return pks;
	}
	
	public long[] getDelPksArr() {
		String[] delIds = StringUtils.split(getDelIds(), ",");
		return StringUtils.convertToLong(delIds);
	}

	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}

	public String getDelIds() {
		String[] arr = StringUtils.split(this.delIds, ",");
		return StringUtils.removeFromArr(arr, "", ",");
	}
	
	public void setBindBO(BusinessObject bindBO) {
		this.bindBO = bindBO;
	}

	public BusinessObject getBindBO() {
		return bindBO;
	}
}
