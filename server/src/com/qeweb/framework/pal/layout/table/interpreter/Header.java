package com.qeweb.framework.pal.layout.table.interpreter;

import java.util.LinkedHashSet;
import java.util.Set;

import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.ContainerUtil;

/**
 * 
 * 表头的结构, 用于合并table的表头, 表头的每个合并单元是一个Header
 * 
 */
public class Header {

	private String title;			//合并单元的标题
	private Set<String> bopSet;		//合并单元中包含的bop
	private Set<Header> headerSet;	//其它合并单元
	
	public void addBopSet(String bop) {
		if(bopSet == null)
			bopSet = new LinkedHashSet<String>();
		bopSet.add(bop);
	}
	
	public void addHeaderSet(Header header) {
		if(headerSet == null)
			headerSet = new LinkedHashSet<Header>();
		headerSet.add(header);
	}
	
	/**
	 * Header合并的总列数
	 * @return
	 */
	public int getColCount() {
		int count = 0;
		if(getBopSet() != null)
			count += getBopSet().size();
		
		if(ContainerUtil.isNotNull(getHeaderSet())) {
			for(Header header : getHeaderSet()) {
				count += header.getColCount();
			}
		}
		
		return count;
	}
	
	/**
	 * Header的高度
	 * @return
	 */
	public int getHeight() {
		int height = 1;
		if(ContainerUtil.isNull(getHeaderSet()))
			return height;
		
		for(Header header : getHeaderSet()) {
			int level = height + header.getHeight();
			if(level > height)
				height = level;
		}
		
		return height;
	}
	
	public String getTitle() {
		return AppLocalization.getLocalization(title);
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Set<String> getBopSet() {
		return bopSet;
	}
	public void setBopSet(Set<String> bopSet) {
		this.bopSet = bopSet;
	}
	public Set<Header> getHeaderSet() {
		return headerSet;
	}
	public void setHeaderSet(Set<Header> headerSet) {
		this.headerSet = headerSet;
	}
}
