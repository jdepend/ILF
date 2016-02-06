package com.qeweb.framework.impconfig.mdt.phymag.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.base.impl.BaseDaoHibImpl;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.impconfig.mdt.phymag.bo.PhyTableBO;
import com.qeweb.framework.impconfig.mdt.phymag.dao.ia.IPhyTableDao;

/**
 * 物理表相关dao impl
 */
public class PhyTableDaoImpl extends BaseDaoHibImpl implements IPhyTableDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4754315959703022072L;

	@SuppressWarnings("unchecked")
	@Override
	public List<PhyTableBO> findAll() {
		return findAll(PhyTableBO.class);
	}
	
	@Override
	public PhyTableBO findById(long id) {
		return (PhyTableBO) getById(PhyTableBO.class, id);
	}

	@Override
	public PhyTableBO findUndelByName(String tableName, String tableAlias) {
		DetachedCriteria dc = DetachedCriteria.forClass(PhyTableBO.class);
		dc.add(Restrictions.eq(FIELD_DELETEFLAG, UNDELETE_SIGNE));
		dc.add(Restrictions.or(Restrictions.eq("name", tableName), Restrictions.eq("alias", tableAlias)));
		
		return (PhyTableBO) get(dc);
	}

	@Override
	public void delete(List<BusinessComponent> phyTableList) {
		for(BusinessComponent tableBO : phyTableList) {
			update("update qeweb_mdt_phy_column set delete_flag = ? where table_id = ?", 
					new Object[]{DELETE_SIGNE, tableBO.getId()});
			tableBO.setDeleteFlag(DELETE_SIGNE);
			update(tableBO);
		}
		
		//删除表与删除数据分别操作，删除表操作无法回滚
		dropTable(phyTableList);
	}
	
	@Override
	public void saveOrUpdate(BusinessComponent bc) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.clear();
		session.saveOrUpdate(bc);
	}
	
	/**
	 * 删除表
	 * @param phyTableList
	 */
	private void dropTable(List<BusinessComponent> phyTableList) {
		for(BusinessComponent tableBO : phyTableList) {
			executeSql("DROP TABLE IF EXISTS " + ((PhyTableBO) tableBO).getName() + "");
		}
	}
}