package com.qeweb.framework.app.action;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.qeweb.framework.base.ac.BaseAction;
import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.syssetting.savecase.bo.QueryCaseBO;
import com.qeweb.framework.common.syssetting.savecase.service.ISaveCaseService;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;

/**
 * 
 * 保存查询条件
 */
public class SaveCaseAC extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5000716844491834927L;

	private String dataIsland; 		//数据岛
	private String sourcePage;		//页面路径
	private String caseName;		//查询条件名称
	private String containerId; 	//"保存条件"按钮所在的组件ID
	private String ids;				//数据id, 以逗号分隔
	private boolean saveForNewCase;	//是否保存为新条件的标识
	private String oldId;
	private ISaveCaseService saveCaseService;
	
	/**
	 * 保存查询条件
	 */
	public void saveCase() {
		caseName = StringUtils.removeAllSpace(caseName);
		
		boolean success = true;
		String errMsg = "";
		try {
			if(StringUtils.isEmpty(caseName)) {
				throw new BOException("com.qeweb.framework.app.action.saveCase_err1");
			}
			getSaveCaseService().saveCase(caseName, UserContext.getUserId(), sourcePage, dataIsland, oldId);
		} catch(BOException e) {
			errMsg = e.getErrMessage();
			e.printStackTrace();
			success = false;
		} catch(Exception e) {
			e.printStackTrace();
			success = false;
		}
		
		try {
			String result = "{success:" + success + ",msg:'" + errMsg + "'}";
			Envir.getResponse().setContentType(Constant.CONTENTTYPE);
			Envir.getResponse().getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查找查询条件
	 * @return
	 */
	public void findQueryCase() {
		List<QueryCaseBO> result = getSaveCaseService().findQueryCase(UserContext.getUserId(), sourcePage);
		JSONArray members = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		
		if(ContainerUtil.isNotNull(result)) {
			for (QueryCaseBO bo : result) {
				JSONObject member = new JSONObject();
				member.put("id", bo.getId());
				member.put("caseName", bo.getCaseName());
				member.put("disland", bo.getDisland());
				members.add(member);
			}
		}
		jsonObject.put("data", members);
		
		Envir.getResponse().setContentType(Constant.CONTENTTYPE);
		try {
			Envir.getResponse().getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除查询条件
	 */
	public void delCase() {
		getSaveCaseService().deleteCase(ids);
	}
	
	public String getDataIsland() {
		return dataIsland;
	}
	
	public void setDataIsland(String dataIsland) {
		this.dataIsland = dataIsland;
	}
	
	public String getSourcePage() {
		return sourcePage;
	}
	
	public void setSourcePage(String sourcePage) {
		this.sourcePage = sourcePage;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getContainerId() {
		return containerId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public ISaveCaseService getSaveCaseService() {
		return saveCaseService;
	}

	public void setSaveCaseService(ISaveCaseService saveCaseService) {
		this.saveCaseService = saveCaseService;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public boolean isSaveForNewCase() {
		return saveForNewCase;
	}

	public void setSaveForNewCase(boolean saveForNewCase) {
		this.saveForNewCase = saveForNewCase;
	}

	public String getOldId() {
		return oldId;
	}

	public void setOldId(String oldId) {
		this.oldId = oldId;
	}
}
