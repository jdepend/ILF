package com.qeweb.framework.impconfig.mdt.use.bean;

import java.io.Serializable;

/**
 * MDT纵向扩展bean
 */
public class MDTVertical implements Serializable {

	private static final long serialVersionUID = -8347671158950301992L;

	private long id;
	private long mdtFieldId;				//弹性域ID
	private String relTableName;			//关联表表名
	private String relTableFileName;		//关联表字段名
	private String mdtCaseField;			//弹性域关联字段
	private String relCaseField;			//关联表关联字段
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getMdtFieldId() {
		return mdtFieldId;
	}
	public void setMdtFieldId(long mdtFieldId) {
		this.mdtFieldId = mdtFieldId;
	}
	public String getRelTableName() {
		return relTableName;
	}
	public void setRelTableName(String relTableName) {
		this.relTableName = relTableName;
	}
	public String getRelTableFileName() {
		return relTableFileName;
	}
	public void setRelTableFileName(String relTableFileName) {
		this.relTableFileName = relTableFileName;
	}
	public String getMdtCaseField() {
		return mdtCaseField;
	}
	public void setMdtCaseField(String mdtCaseField) {
		this.mdtCaseField = mdtCaseField;
	}
	public String getRelCaseField() {
		return relCaseField;
	}
	public void setRelCaseField(String relCaseField) {
		this.relCaseField = relCaseField;
	}

}
