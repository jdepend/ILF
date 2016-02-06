package com.qeweb.framework.bc;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.common.utils.StringUtils;

/**
 * 当开启table的记忆翻页选择功能时，将会还原出CheckedBO.<br>
 *
 */
public class CheckedBO extends BusinessObject {

	private static final long serialVersionUID = -750989879214398893L;
	
	private BusinessObject bindBO; 	// 保存绑定的bo,用于分区同页面多个表格对应的bo
	private String checkedIds;		//所有选中的id,以逗号 , 分割


	/**
	 * 获取记忆的id.可以用于条件IN查询
	 */
	public List<Long> getPks() {
		String[] checkedIds = StringUtils.split(this.checkedIds, ",");
		if(StringUtils.isEmpty(checkedIds))
			return null;
		
		List<Long> pks = new LinkedList<Long>();
		for(String id : checkedIds) {
			pks.add(StringUtils.convertToLong(id));
		}
		
		return pks;
	}
	
	public long[] getPksArr() {
		String[] checkedIds = StringUtils.split(this.checkedIds, ",");
		return StringUtils.convertToLong(checkedIds);
	}

	public BusinessObject getBindBO() {
		return bindBO;
	}

	public void setBindBO(BusinessObject bindBO) {
		this.bindBO = bindBO;
	}

	public String getCheckedIds() {
		return checkedIds;
	}

	public void setCheckedIds(String checkedIds) {
		this.checkedIds = checkedIds;
	}
	
}
