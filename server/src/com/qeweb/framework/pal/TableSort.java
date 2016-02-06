package com.qeweb.framework.pal;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 解析表格排序串
 */
public class TableSort implements Serializable {
	private static final long serialVersionUID = 6656331772969449952L;
	
	private String SORT = "sort:";
	private String NOSORT = "nosort:";
	
	private String sortStr;
	private Set<String> sortSet = new LinkedHashSet<String>();
	private Set<String> noSortSet = new LinkedHashSet<String>();
	
	public TableSort(String sortStr){
		this.sortStr = StringUtils.removeAllSpace(sortStr);
		interpreter();
	}
	
	public boolean isSort(String fcName){		
		if(this.sortSet.contains(fcName))
			return true;
		
		if(this.noSortSet.contains(fcName))
			return false;
		
		return AppConfig.getHasSort();			
	}
	
	
	private void interpreter(){
		if(StringUtils.isEmpty(sortStr))
			return;
		String arr[] = StringUtils.split(sortStr, ";");
		for(String sortTypeStr : arr){
			if(StringUtils.isEqualIgnoreCase(SORT, sortTypeStr.substring(0, "sort:".length())))
				putFields(sortTypeStr, this.sortSet);
			else if(StringUtils.isEqualIgnoreCase(NOSORT, sortTypeStr.substring(0, "nosort:".length())))
				putFields(sortTypeStr, this.noSortSet);
		}
	}

	private void putFields(String sortTypeStr, Set<String> sortFieldSet) {
		String arr[] = StringUtils.split(sortStr, ":");
		if(StringUtils.isEmpty(arr[1]))
			return;
		String fields[] = StringUtils.split(arr[1], ",");
		for(String field : fields){
			if(StringUtils.isNotEmpty(field))
				sortFieldSet.add(field);
		}
	}
}
