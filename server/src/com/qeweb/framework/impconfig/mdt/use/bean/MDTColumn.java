package com.qeweb.framework.impconfig.mdt.use.bean;

import java.io.Serializable;

/**
 * 弹性域的分类列
 */
public class MDTColumn implements Serializable {

	private static final long serialVersionUID = 2573108884978021207L;
	private String name;	//弹性域列名
	private String alias;	//弹性域别名（弹性域的具体含义）
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
}
