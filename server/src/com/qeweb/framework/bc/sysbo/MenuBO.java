package com.qeweb.framework.bc.sysbo;

import com.qeweb.framework.common.utils.StringUtils;

/**
 * MenuBO 是一类特殊的TreeBO, 它用于生成菜单导航树,
 * 每个节点都存在一个指向展示视图的path属性.
 *
 */
public abstract class MenuBO extends TreeBO {
	private static final long serialVersionUID = -6303174679622556268L;

	private String path; 	//菜单路径
	
	public MenuBO() {}
	
	public MenuBO(long id, long parentId, String value, int sortIndex, String path) {
		super(id, parentId, value, sortIndex);
		if(StringUtils.isNotEmpty(path))
			this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
