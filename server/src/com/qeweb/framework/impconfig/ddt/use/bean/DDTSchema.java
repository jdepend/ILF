package com.qeweb.framework.impconfig.ddt.use.bean;

import java.io.Serializable;
import java.util.List;

import com.qeweb.framework.impconfig.mdt.use.bean.MDTVertical;

/**
 * DDT 方案类，用于存储DDT组件的数据结构
 */
public class DDTSchema implements Serializable {

	private static final long serialVersionUID = 101405965958660503L;
	
	/*
	 * DDT中的细粒度组件类型。 
	 * 1textFeild, 2:textArea, 3:label, 4:Password, 5:hidden
	 * 6:select, 7:radio, 8,checkbox, 9,OptionTranserSelect
	 * 10:anchor, 11:editor
	 */
	final public static int TEXTFIELD = 1;
	final public static int TEXTAREA = 2;
	final public static int LABEL = 3;
	final public static int PASSWORD = 4;
	final public static int HIDDEN = 5;
	final public static int SELECT = 6;
	final public static int RADIO = 7;
	final public static int CHECKBOX = 8;
	final public static int OPTIONTRANSERSELECT = 9;
	final public static int ANCHOR = 10;
	final public static int EDITOR = 11;
	
	private String id;
	private String schemaCode;			//schema编码
	private String sourcePage;			//页面URL
	private String containerId;			//粗粒度组件ID
	private String boName;				//粗粒度组件对应的BO
	private String bopName;				//细粒度组件对应的BOP
	private int type;					//细粒度组件类型
	private String dataType;			//数据类型(String, int, date, decimal)
	private String valueSetId;			//值集ID,用逗号分隔
	private Boolean isRequired = false;	//是否必填
	private Integer maxLength;			//最大长度 
	private Double maxValue;			//最大值					
	private Double minValue;			//最小值
	private Double stepValue;			//步长
	private String tableName;			//弹性域表名
	private String fieldId;				//弹性域字段ID
	private String fieldName;			//弹性域字段名
	private String alias;				//字段别名(BOP的国际化标识)
	private String contextName;			//上下文信息(细粒度组件在该配置中的localName)
	private String prevBopName;			//前一个组件对应的BOPName, 用于排序

	//MDT纵向扩展
	private List<MDTVertical> mdtVerticalSet;
	
	public String getContextName() {
		return contextName;
	}
	public void setContextName(String contextName) {
		this.contextName = contextName;
	}
	public String getBopName() {
		return bopName;
	}
	public void setBopName(String bopName) {
		this.bopName = bopName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public Boolean getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}
	public Integer getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}
	public Double getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}
	public Double getMinValue() {
		return minValue;
	}
	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}
	public Double getStepValue() {
		return stepValue;
	}
	public void setStepValue(Double stepValue) {
		this.stepValue = stepValue;
	}
	public String getValueSetId() {
		return valueSetId;
	}
	public void setValueSetId(String valueSetId) {
		this.valueSetId = valueSetId;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getPrevBopName() {
		return prevBopName;
	}
	public void setPrevBopName(String prevBopName) {
		this.prevBopName = prevBopName;
	}
	public String getBoName() {
		return boName;
	}
	public void setBoName(String boName) {
		this.boName = boName;
	}
	public String getContainerId() {
		return containerId;
	}
	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}
	public String getSourcePage() {
		return sourcePage;
	}
	public void setSourcePage(String sourcePage) {
		this.sourcePage = sourcePage;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldId() {
		return fieldId;
	}
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSchemaCode() {
		return schemaCode;
	}
	public void setSchemaCode(String schemaCode) {
		this.schemaCode = schemaCode;
	}
	public List<MDTVertical> getMdtVerticalSet() {
		return mdtVerticalSet;
	}
	public void setMdtVerticalSet(List<MDTVertical> mdtVerticalSet) {
		this.mdtVerticalSet = mdtVerticalSet;
	}
	
}
