package com.qeweb.framework.frameworkbo;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;

/**
 * 手机端发布，升级bo
 * @author lu.yue
 *
 */
public class MobileLevelUpBO extends BusinessObject {

	private static final long serialVersionUID = -3140257169285835657L;
	private String versionCode;//版本号
	private String downLoadUrl;//下载路径
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object query(BOTemplate bot, int start) {
		MobileLevelUpBO bo = new MobileLevelUpBO();
		DetachedCriteria dc = DetachedCriteria.forClass(MobileLevelUpBO.class);
		dc.add(Restrictions.eq("versionCode", bot.getValue("versionCode")));
		dc.add(Restrictions.eq("deleteFlag", IBaseDao.UNDELETE_SIGNE));
		List list = getDao().findByCriteria(dc);
		if (list != null && list.size() == 1) {
			bo = (MobileLevelUpBO) list.get(0);
		}	
		return bo;
	}
	
	/**
	 * 获取升级信息, 以协议的方式传递给终端
	 * 
	 * @return  
	 * 传递给终端的升级协议, 如果有新版本, 协议为:
	 * <update needupdate="true"  versionCode="版本号" url="新的版本的下载地址"></update>
	 * 
	 * 如果没有新版本, 协议为:
	 * <update needupdate="false"></update>
	 */
	public String getUpdateInfo() {
		BOTemplate bot = new BOTemplate();
		bot.push("versionCode", this.getVersionCode());
		MobileLevelUpBO bo = (MobileLevelUpBO)this.query(bot, 0);
		// 当前为最新版本
		if (bo != null) {
			return createUpdateXml(null);
		}
		// 不是最新版本，从数据库取得最新版本信息
		else {
			bot = new BOTemplate();
			bot.push("versionCode", "");
			bo = (MobileLevelUpBO)this.query(bot, 0);
			return createUpdateXml(bo);
		}
		
	}
	
	/**
	 * 创建升级协议 xml
	 * @param mobileUpdateBO
	 * @return
	 */
	private String createUpdateXml(MobileLevelUpBO mobileUpdateBO) {
		StringBuilder updateXml= new StringBuilder();
		
		//有新版本, 需要升级
		if (mobileUpdateBO != null) {
			updateXml.append("<update needupdate=\"true\"  versionCode=\"")
				.append(mobileUpdateBO.getVersionCode()).append("\" url=\"")
				.append(mobileUpdateBO.getDownLoadUrl()).append("\"></update>");
		}
		//没有新版本,不需要升级
		else {
			updateXml.append("<update needupdate=\"false\"></update>");
		}
		
		return updateXml.toString();
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getDownLoadUrl() {
		return downLoadUrl;
	}

	public void setDownLoadUrl(String downLoadUrl) {
		this.downLoadUrl = downLoadUrl;
	}

}
