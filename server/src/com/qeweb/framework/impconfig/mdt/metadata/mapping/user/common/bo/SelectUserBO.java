package com.qeweb.framework.impconfig.mdt.metadata.mapping.user.common.bo;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.utils.BoOperateUtil;
import com.qeweb.framework.common.utils.ContainerUtil;

/**
 * 选择用户信息, SelectUserBO 将列出UserBOExpend中的所有属性信息
 */
public class SelectUserBO extends BusinessObject {

	private static final long serialVersionUID = -33779052454780814L;
	
	private String subBOName;			//子BO名称, 多级BO以逗号分隔
	private String fieldName;			//subBOName的属性名
	private String fullFieldName;		//属性全名 : subBOName.filedName
	
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		List<String> allBaseFieldNames = BoOperateUtil.getAllBaseFieldName(UserBOExpend.getInstance().getClass());
		if(ContainerUtil.isNull(allBaseFieldNames))
			return new Page();
		
		List<SelectUserBO> result = new LinkedList<SelectUserBO>();
		int id = 1;
		for(String baseFieldName : allBaseFieldNames) {
			SelectUserBO bo = new SelectUserBO();
			bo.setId(id++);
			int indexPoint = baseFieldName.lastIndexOf(".");
			if(indexPoint > 0) {
				bo.setSubBOName(baseFieldName.substring(0, indexPoint));
				bo.setFieldName(baseFieldName.substring(indexPoint + 1, baseFieldName.length()));
			}
			else {
				bo.setFieldName(baseFieldName);
			}
			
			bo.setFullFieldName(baseFieldName);
			result.add(bo);
		}
		
		int total = allBaseFieldNames.size();
		Page page = new Page(result, total, total, start);
		initPreferencePage(page);
		
		return page;
	}
	
	public String getSubBOName() {
		return subBOName;
	}

	public void setSubBOName(String subBOName) {
		this.subBOName = subBOName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFullFieldName() {
		return fullFieldName;
	}

	public void setFullFieldName(String fullFieldName) {
		this.fullFieldName = fullFieldName;
	}
}
