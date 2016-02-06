package com.qeweb.framework.impconfig.mdt.use.bean;

import java.io.Serializable;

/**
 * MDT弹性域对应的枚举值
 */
public class MDT_Value implements Serializable {
	
	private static final long serialVersionUID = -682851553271352536L;

	private long id;
	private String value;
	private String text;
	private long valueSetId;		//值集ID
	private int deleteFlag;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public long getValueSetId() {
		return valueSetId;
	}
	public void setValueSetId(long valueSetId) {
		this.valueSetId = valueSetId;
	}
	public int getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
}
