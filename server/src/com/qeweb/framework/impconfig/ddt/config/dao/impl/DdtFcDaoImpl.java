/**
 * 
 */
package com.qeweb.framework.impconfig.ddt.config.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.base.impl.BaseDaoHibImpl;
import com.qeweb.framework.bc.BOTHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.impconfig.ddt.config.bo.DdtFcBO;
import com.qeweb.framework.impconfig.ddt.config.dao.ia.IDdtFcDao;

/**
 * DDT细粒度配置DAO实现
 */
public class DdtFcDaoImpl extends BaseDaoHibImpl implements IDdtFcDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -253474926141676139L;

	@Override
	public DdtFcBO getDdtFcBO(long containerConfigId, long sysFcId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DdtFcBO.class);
		criteria.add(Restrictions.eq("ddtContainerBO.id", containerConfigId));
		criteria.add(Restrictions.eq("ddtSysFcBO.id", sysFcId));
		return (DdtFcBO) get(criteria);
	}

	@Override
	public Page query(BOTemplate bot, int pageSize, int start) {
		String boName = null;
		String containerId = null;
		String containerType = null;
		String pageName = null;
		String pageUrl = null;
		if(bot.getBotMap().containsKey("ddtContainerBO.ddtSysContainerBO.boName")) {
			boName = (String) bot.getBotMap().get("ddtContainerBO.ddtSysContainerBO.boName");
			bot.getBotMap().remove("ddtContainerBO.ddtSysContainerBO.boName");
		}
		if(bot.getBotMap().containsKey("ddtContainerBO.ddtSysContainerBO.containerId")) {
			containerId = (String) bot.getBotMap().get("ddtContainerBO.ddtSysContainerBO.containerId");
			bot.getBotMap().remove("ddtContainerBO.ddtSysContainerBO.containerId");
		}
		if(bot.getBotMap().containsKey("ddtContainerBO.ddtSysContainerBO.containerType")) {
			containerType = (String) bot.getBotMap().get("ddtContainerBO.ddtSysContainerBO.containerType");
			bot.getBotMap().remove("ddtContainerBO.ddtSysContainerBO.containerType");
		}
		if(bot.getBotMap().containsKey("ddtContainerBO.ddtSysContainerBO.page.name")) {
			pageName = (String) bot.getBotMap().get("ddtContainerBO.ddtSysContainerBO.page.name");
			bot.getBotMap().remove("ddtContainerBO.ddtSysContainerBO.page.name");
		}
		if(bot.getBotMap().containsKey("ddtContainerBO.ddtSysContainerBO.page.url")) {
			pageUrl = (String) bot.getBotMap().get("ddtContainerBO.ddtSysContainerBO.page.url");
			bot.getBotMap().remove("ddtContainerBO.ddtSysContainerBO.page.url");
		}
		DetachedCriteria dc = BOTHelper.getDetachedCriteria(bot, DdtFcBO.class);
		if(boName != null || containerId != null || containerType != null || pageName != null || pageUrl != null) {
			dc.createAlias("ddtContainerBO", "ddtContainer");
			dc.createAlias("ddtContainer.ddtSysContainerBO", "ddtSysContainer");
		}
		if (boName != null) 
			dc.add(Restrictions.like("ddtSysContainer.boName", boName, MatchMode.ANYWHERE));
		if (containerId != null) 
			dc.add(Restrictions.like("ddtSysContainer.containerId", containerId, MatchMode.ANYWHERE));
		if (containerType != null) 
			dc.add(Restrictions.eq("ddtSysContainer.containerType", Integer.valueOf(containerType)));
		if(pageName != null || pageUrl != null) 
			dc.createAlias("ddtSysContainer.page", "page");
		if(pageName != null)
			dc.add(Restrictions.like("page.name", pageName, MatchMode.ANYWHERE));
		if(pageUrl != null) 
			dc.add(Restrictions.like("page.url", pageUrl, MatchMode.ANYWHERE));
		
		return findPageByCriteria(dc, pageSize, start);
	}

}
