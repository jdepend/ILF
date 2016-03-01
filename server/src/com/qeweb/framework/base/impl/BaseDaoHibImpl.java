package com.qeweb.framework.base.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.qeweb.framework.base.QewebDetachedCriteria;
import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.Page;

/**
 * 业务库通用DAO
 *
 */
public class BaseDaoHibImpl extends HibernateDaoSupport implements IBaseDao {
	
	private static final long serialVersionUID = 2215080037091373036L;

	/**
	 * 根据ID获取对象. 实际调用Hibernate的session.load()方法返回实体或其proxy对象. 如果对象不存在，抛出异常.
	 */
	public Object getById(final Class<?> clazz, final Serializable id) {
		return getHibernateTemplate().get(clazz, id);
	}

	/**
	 * 根据查询条件查询BO类型的对象,返回一个BO
	 * @param criteria 查询条件
	 * @return Object
	 */
	@Override
	public Object get(final Object criteria) {
		return getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				DetachedCriteria datacriteria = (DetachedCriteria)criteria;
				Criteria criteria = datacriteria.getExecutableCriteria(session);
				return criteria.uniqueResult();
			}
		});
	}

	/**
	 * 查询所有数据
	 */
	@SuppressWarnings("rawtypes")
	public List findAll(final Class<?> clazz) {
		return getHibernateTemplate().loadAll(clazz);
	}

	/**
	 * 跟据hsql查询(有参)
	 *
	 * @param sql
	 * @param criteria
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findBySql(final String sql, final Object criteria) {
		return getHibernateTemplate().find(sql, criteria);
	}

	/**
	 * 根据sql查询,仅有JDBC实现
	 * @param sql
	 * @param mapper
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findBySql(String sql, RowMapper mapper) {
		return null;
	}
	
	/**
	 * 跟据hsql查询(有参)
	 * @param sql
	 * @param values
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findBySql(final String sql, final Object[] values) {
		return getHibernateTemplate().find(sql, values);
	}
	
	/**
	 * 利用Criteria进行分类查询(有开始与结果标识)
	 *
	 * @param criteria
	 * @param pageSize
	 * @param startIndex
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page findPageByCriteria(final Object criteria,
			final int pageSize, final int startIndex) {
		int totalCount = this.findTotleByCriteria(criteria);
		List items = this.findListByCriteria(criteria, pageSize, startIndex);
		return new Page(items, totalCount, pageSize > Integer.MIN_VALUE ? pageSize : totalCount , startIndex);
	}

	/**
	 * 跟据Criteria查询 返回所有记录，若数据量较大不建议使用
	 * @param criteria
	 * @return
	 */
	@Override
	public Page findPageByCriteria(final Object criteria) {
		return findPageByCriteria(criteria, Integer.MIN_VALUE, Integer.valueOf(0));
	}

	/**
	 * 跟据Criteria查询
	 *
	 * @param criteria
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findByCriteria(final Object criteria) {
		return (List)getHibernateTemplate().findByCriteria((DetachedCriteria)criteria);
	}

	/**
	 * 根据sql查询唯一数据
	 * @param sql
	 * @return
	 */
	public Object createQueryUniqueResult(final String sql){        
        return (Object) getHibernateTemplate().execute(new HibernateCallback() {  
            public Object doInHibernate(Session session) throws HibernateException {
                SQLQuery q = session.createSQLQuery(sql);  
                  
                return q.uniqueResult();  
            }  
        }); 
	}

	/**
	 * 根据sql查询数据
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List createQuery(final String sql){
        return (List) getHibernateTemplate().execute(new HibernateCallback() {  
            public Object doInHibernate(Session session) throws HibernateException {
                SQLQuery q = session.createSQLQuery(sql);  
                  
                return q.list();  
            }  
        });  
		
	}

	/**
	 * 跟据Criteria查询数量
	 *
	 * @param criteria
	 * @return
	 */
	public int getCountByCriteria(final Object criteria) {
		Integer count = (Integer) getHibernateTemplate().executeWithNativeSession(
			new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException {
					DetachedCriteria datacriteria = (DetachedCriteria)criteria;
					Criteria criteria = datacriteria.getExecutableCriteria(session);
					return criteria.setProjection(Projections.rowCount()).uniqueResult();
				}
			});
		return count.intValue();
	}

	/**
	 * 根据sql查询
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findBySql(String sql) {
		return (List)getHibernateTemplate().find(sql);
	}

	@Override
	public void save(BusinessComponent bc) {
		getHibernateTemplate().save(bc);
	}

	@Override
	public void save(final String sql) {
		executeSql(sql);
	}

	@Override
	public void save(final String sql, final Object[] parameters) {
		executeSql(sql, parameters);
	}

	@Override
	public void batchSave(final Collection<BusinessComponent> bcList) {
		for (BusinessComponent bc : bcList) {
			save(bc);
		}
	}

	@Override
	public void batchSave(final String sql, final List<Object[]> parameters) {
		for (Object[] param : parameters) {
			executeSql(sql, param);
		}
	}

	@Override
	public void saveOrUpdate(BusinessComponent bc) {
		getHibernateTemplate().saveOrUpdate(bc);
	}

	public void update(final BusinessComponent bc) {
		getHibernateTemplate().update(bc);
	}

	@Override
	public void update(final String sql) {
		executeSql(sql);
	}

	@Override
	public void update(final String sql, final Object[] parameters) {
		executeSql(sql, parameters);
	}

	@Override
	public void batchUpdate(final Collection<BusinessComponent> bcList) {
		for (BusinessComponent bc : bcList) {
			update(bc);
		}
	}

	@Override
	public void batchUpdate(final String sql, final  List<Object[]> parameters) {
		for (Object[] param : parameters) {
			update(sql, param);
		}
	}

	@Override
	public void delete(final BusinessComponent bc) {
		getHibernateTemplate().delete(bc);
	}

	@Override
	public void delete(final String sql) {
		executeSql(sql);
	}

	@Override
	public void delete(final String sql, final Object[] parameters) {
		executeSql(sql, parameters);
	}

	@Override
	public void batchDelete(final Collection<BusinessComponent> bcList) {
		getHibernateTemplate().deleteAll(bcList);
	}

	@Override
	public void batchDelete(final String sql, final List<Object[]> parameters) {
		for (Object[] param : parameters) {
			delete(sql, param);
		}
	}

	@Override
	public void executeSql(final String sql) {
		executeSql(sql, null);
	}

	/**
	 * 根据sql插入，更新，删除，不能查询
	 * @param sql
	 * @param parameters
	 */
	@Override
	public void executeSql(final String sql, final Object[] parameters){
		getHibernateTemplate().execute(
				new HibernateCallback(){
					public Object doInHibernate(Session session) throws HibernateException {
						Query query= session.createSQLQuery(sql);
						if(parameters != null){
							for (int i = 0 ; i < parameters.length ; i++) {
								query.setParameter( i, parameters[i]);
							}
						}
						query.executeUpdate();
						return null;
				}
		});
	}

	@Override
	public void batchExecuteSql(String sql, final List<Object[]> parameters) {

	}

	@Override
	public Integer findTotleByCriteria(final Object criteria) {
		return (Integer) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				QewebDetachedCriteria datacriteria = (QewebDetachedCriteria) criteria;
				Criteria ci = datacriteria.getExecutableCriteria(session);
				Object totalObj = ci.setProjection(Projections.rowCount()).uniqueResult();
				int totalCount = 0;
				if (totalObj != null)
					totalCount = (Integer) totalObj;
				
				return totalCount;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List findListByCriteria(final Object criteria, final int pageSize, final int startIndex) {
		return (List) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				QewebDetachedCriteria datacriteria = (QewebDetachedCriteria) criteria;
				Criteria ci = datacriteria.getExecutableCriteria(session);
		
				datacriteria.fillOrdersSetting(ci);		//把datacriteria中保存的排序信息设置进去 
		
				ci.setProjection(null);
				ci.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		
				List items = null;
				if (pageSize > Integer.MIN_VALUE)
					items = ci.setFirstResult(startIndex).setMaxResults(pageSize).list();
				else
					items = ci.list();
				return items;
			}
		});
	}
}