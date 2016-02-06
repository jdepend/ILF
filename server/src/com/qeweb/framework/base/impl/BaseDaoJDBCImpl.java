package com.qeweb.framework.base.impl;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.Page;

/**
 * 业务库通用DAO
 *
 */
public class BaseDaoJDBCImpl extends JdbcDaoSupport implements IBaseDao {

	private static final long serialVersionUID = -8543625059916993533L;

	/**
	 * 根据ID获取对象. 实际调用Hibernate的session.load()方法返回实体或其proxy对象. 如果对象不存在，抛出异常.
	 */
	public Object getById(final Class<?> clazz, final Serializable id) {
		return null;
	}

	/**
	 * 根据查询条件查询BO类型的对象,返回一个BO
	 * @param criteria 查询条件
	 * @return Object
	 */
	@Override
	public Object get(final Object criteria) {
		return null;
	}

	/**
	 * 查询所有数据
	 */
	@SuppressWarnings("rawtypes")
	public List findAll(final Class<?> clazz) {
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		return findByCriteria(dc);
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
		return null;
	}
	
	/**
	 * 根据sql查询,仅有JDBC实现
	 * @param sql
	 * @param mapper
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findBySql(final String sql, RowMapper mapper) {
		return getJdbcTemplate().query(sql, mapper);
	}
	
	
	@SuppressWarnings("rawtypes")
	public List findBySql(final String sql, Object[] args, RowMapper rowMapper) {
		 return getJdbcTemplate().query(sql, args, rowMapper);
	}
	
	/**
	 * 根据sql查询,仅有hbm实现
	 * @param sql
	 * @param values
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findBySql(final String sql, final Object[] values) {
		return null;
	}
	
	/**
	 * 利用Criteria进行分类查询(有开始与结果标识)
	 *
	 * @param criteria
	 * @param pageSize
	 * @param startIndex
	 * @return
	 */
	public Page findPageByCriteria(final Object criteria,
			final int pageSize, final int startIndex) {
		return null;
	}

	/**
	 * 跟据Criteria查询 返回所有记录，若数据量较大不建议使用
	 * @param criteria
	 */
	@Override
	public Page findPageByCriteria(final Object criteria) {
		return null;
	}

	/**
	 * 跟据Criteria查询
	 *
	 * @param criteria
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findByCriteria(final Object criteria) {
		return null;
	}

	/**
	 * 根据sql查询唯一数据
	 * @param sql
	 * @return
	 */
	public Object createQueryUniqueResult(String sql){
		return null;
	}

	/**
	 * 根据sql查询数据
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List createQuery(String sql){
		return null;
	}

	/**
	 * 跟据Criteria查询数量
	 *
	 * @param criteria
	 * @return
	 */
	public int getCountByCriteria(final Object criteria) {
		return 0;
	}


	/**
	 * 根据sql查询
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findBySql(String sql) {
		return getJdbcTemplate().queryForList(sql);
	}

	@Override
	public void save(BusinessComponent bc) {
		try {
			throw new Exception("liwai");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save(String sql) {
		executeSql(sql);
	}

	@Override
	public void save(String sql, Object[] parameters) {
		executeSql(sql, parameters);
	}

	@Override
	public void batchSave(Collection<BusinessComponent> bcList) {

	}

	@Override
	public void batchSave(String sql, List<Object[]> parameters) {
		batchExecuteSql(sql, parameters);
	}

	public void saveOrUpdate(final BusinessComponent bc){
	}

	public void update(BusinessComponent bc) {

	}

	@Override
	public void update(String sql) {
		executeSql(sql);
	}

	@Override
	public void update(String sql, Object[] parameters) {
		executeSql(sql, parameters);
	}

	@Override
	public void batchUpdate(Collection<BusinessComponent> bcList) {
	}

	@Override
	public void batchUpdate(final String sql, final List<Object[]> parameters) {
		batchExecuteSql(sql, parameters);
	}

	@Override
	public void delete(BusinessComponent bc) {

	}

	@Override
	public void delete(String sql) {
		executeSql(sql);
	}

	@Override
	public void delete(String sql, Object[] parameters) {
		executeSql(sql, parameters);
	}

	@Override
	public void batchDelete(Collection<BusinessComponent> bcList) {

	}

	@Override
	public void batchDelete(String sql, List<Object[]> parameters) {
		batchExecuteSql(sql, parameters);
	}

	@Override
	public void executeSql(String sql) {
		getJdbcTemplate().execute(sql);
	}

	/**
	 * 根据sql插入，更新，删除，不能查询
	 * @param sql
	 */
	@Override
	public void executeSql(final String sql, final Object[] parameters){
		getJdbcTemplate().update(sql, parameters);
	}

	@Override
	public void batchExecuteSql(final String sql, final List<Object[]> parameters) {
		getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Object[] param = parameters.get(i);
				for (int j = 0; j < param.length; j++) {
					ps.setString(j + 1, param[j].toString());
				}
			}

			@Override
			public int getBatchSize() {
				return parameters.size();
			}
		});
	}

	@Override
	public Integer findTotleByCriteria(Object criteria) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List findListByCriteria(Object criteria, int pageSize, int startIndex) {
		return null;
	}
}