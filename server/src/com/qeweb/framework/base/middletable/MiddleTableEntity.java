package com.qeweb.framework.base.middletable;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * MiddleTableEntity共MiddleTable和ExtendsMiddleTable使用
 * @author Administrator
 *
 */
public class MiddleTableEntity implements Serializable {

	private static final long serialVersionUID = 7395487025685066957L;
	
	private String fieldName;	//字段名
	private BusinessObject bo;	//主表BO
	private String[] ids;		//副表ID的数组形态
	private List<String> idList;//副表ID的List形态
	
	
	public MiddleTableEntity(String FileName) {
		this.fieldName = FileName;
	}
	
	public MiddleTableEntity(String FileName, BusinessObject Bo) {
		this.fieldName = FileName;
		this.bo = Bo;
	}

	public MiddleTableEntity(String FileName, String[] ids) {
		this.fieldName = FileName;
		this.setIds(ids);
	}

	public MiddleTableEntity(String FileName, List<String> idList) {
		this.fieldName = FileName;
		this.setIdList(idList);
	}
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public BusinessObject getBo() {
		return bo;
	}

	public void setBo(BusinessObject bo) {
		this.bo = bo;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
		if(StringUtils.isEmpty(ids))
			return;
		this.idList = new LinkedList<String>();
		for(String id : ids){
			this.idList.add(id);
		}
	}

	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
		if(ContainerUtil.isNull(this.idList))
			return;
		int size = this.idList.size();
		this.ids = new String[size];
		for(int i = 0; i < size; i++){
			this.ids[i] = this.idList.get(i);
		}
	}

}
