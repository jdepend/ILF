package com.qeweb.framework.app.action.mobile;

import com.qeweb.framework.app.handler.MobileLeveUpHandler;
import com.qeweb.framework.base.ac.BaseAction;
import com.qeweb.framework.common.Constant;
import com.qeweb.framework.frameworkbo.MobileLevelUpBO;

/**
 * 手机端升级
 *
 */
public class MobileLevelUpAC extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4254955737763728365L;
	
	private String versionCode;

	@Override
	public String execute() throws Exception {
		MobileLevelUpBO mobileUpdateBO = MobileLeveUpHandler.getMobileLevelUpBO(getVersionCode());
		String updateResult = "";
		
		//当前为最新版本
		if (mobileUpdateBO != null) {
			updateResult = MobileLeveUpHandler.createUpdateXml(null); 
		}
		//不是最新版本，从数据库取得最新版本信息
		else {
			mobileUpdateBO = MobileLeveUpHandler.getMobileLevelUpBO("");
			updateResult = MobileLeveUpHandler.createUpdateXml(mobileUpdateBO); 
		}
		
		getResponse().setContentType(Constant.CONTENTTYPE_XML);
		getResponse().setHeader("Cache-Control","no-cache");  
		getResponse().getWriter().write(updateResult);
		return null;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionCode() {
		return versionCode;
	}
}
