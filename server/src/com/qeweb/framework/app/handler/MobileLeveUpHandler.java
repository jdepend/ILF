package com.qeweb.framework.app.handler;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.base.impl.BaseDaoInfo;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.frameworkbo.MobileLevelUpBO;

/**
 * 手机端升级
 * @author lu.yue
 *
 */
public class MobileLeveUpHandler {

	/**
	 * 取得MobileLevelUpBO
	 * @param versioncode	手机端程序的版本号
	 * @return
	 */
	public static MobileLevelUpBO getMobileLevelUpBO(String versioncode) {
		DetachedCriteria dc = DetachedCriteria.forClass(MobileLevelUpBO.class);
		if(StringUtils.isNotEmpty(versioncode)) {
			dc.add(Restrictions.eq("versionCode", versioncode));
		}
		return (MobileLevelUpBO) BaseDaoInfo.getDao().get(dc);
	}
	
	/**
	 * 创建返回结果
	 * @param mobileUpdateBO
	 * @return
	 */
	public static String createUpdateXml(MobileLevelUpBO mobileUpdateBO) {
		StringBuffer updateXml= new StringBuffer();
		if (mobileUpdateBO != null) {
			updateXml.append("<update needupdate=\"true\"  versionCode=\"")
			.append(mobileUpdateBO.getVersionCode()).append("\" url=\"")
			.append(Envir.getBaseURL() + mobileUpdateBO.getDownLoadUrl())
			.append("\"></update>");
		}
		else {//不升级
			updateXml.append("<update needupdate=\"false\"></update>");
		}
		return updateXml.toString();
	}
}
