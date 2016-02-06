package com.qeweb.framework.impconfig.mdt.phymag.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.qeweb.framework.base.impl.BaseDaoHibImpl;
import com.qeweb.framework.bc.BOTHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.impconfig.mdt.phymag.bo.PhyColumnBO;
import com.qeweb.framework.impconfig.mdt.phymag.dao.ia.IPhyColumnDao;

public class PhyColumnDaoImpl extends BaseDaoHibImpl implements IPhyColumnDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7146656564954229394L;

	@SuppressWarnings("unchecked")
	@Override
	public List<PhyColumnBO> getPhyColumnsByPhyTableName(String tableName) {
		BOTemplate bot = new BOTemplate();
		bot.push("phyTable.name", tableName);
		DetachedCriteria dc = BOTHelper.getDetachedCriteria(bot, PhyColumnBO.class);
		return findByCriteria(dc);
	}

}
