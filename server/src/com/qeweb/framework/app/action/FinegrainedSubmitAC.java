package com.qeweb.framework.app.action;

import java.util.LinkedList;
import java.util.List;
import com.qeweb.framework.app.handler.BOPRelation;
import com.qeweb.framework.base.ac.BaseAction;
import com.qeweb.framework.common.Constant;

/**
 * 处理bop的提交(一个bop改变时将刷新的其它bop)
 *
 */
public class FinegrainedSubmitAC extends BaseAction {
	private static final long serialVersionUID = 1001L;
	
	private String vcId;
	private String value;
	private List<BopJSON> jsonList = new LinkedList<BopJSON>();
	private String dataIsland;
	
	/**
	 * 处理(细细关联)bop的提交,返回json
	 */
	public String executeBop() {
		try {
			getResponse().setContentType(Constant.CONTENTTYPE);
			getResponse().getWriter().write(BOPRelation.bopRelationHandle(vcId, dataIsland));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	

	/**
	 * 处理（细粗关联）bop的提交,返回json
	 */
	public String executeBo() {
		try {
			getResponse().setContentType(Constant.CONTENTTYPE);
			getResponse().getWriter().write(BOPRelation.boRelationHandle(vcId, dataIsland));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String getVcId() {
		return vcId;
	}

	public void setVcId(String vcId) {
		this.vcId = vcId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<BopJSON> getJsonList() {
		return jsonList;
	}

	public void setJsonList(List<BopJSON> jsonList) {
		this.jsonList = jsonList;
	}

	public String getDataIsland() {
		return dataIsland;
	}

	public void setDataIsland(String dataIsland) {
		this.dataIsland = dataIsland;
	}
}
