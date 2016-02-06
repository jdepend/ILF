package com.qeweb.framework.common.syssetting.savecase.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.base.impl.BaseDaoHibImpl;
import com.qeweb.framework.common.syssetting.savecase.bo.QueryCaseBO;
import com.qeweb.framework.common.syssetting.savecase.dao.ISaveCaseDao;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 
 * 保存查询条件, 关系型数据库实现
 *
 */
public class SaveCaseDaoImpl_DB extends BaseDaoHibImpl implements ISaveCaseDao {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1127347356780443007L;

	@SuppressWarnings("unchecked")
	@Override
	public boolean isExists(String caseName, long userId, String pagePath) {
		DetachedCriteria criteria = DetachedCriteria.forClass(QueryCaseBO.class);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("caseName", caseName));
		criteria.add(Restrictions.eq("pagePath", pagePath));
		List<QueryCaseBO> result = findByCriteria(criteria);
		
		return ContainerUtil.isNotNull(result);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QueryCaseBO> findQueryCase(long userId, String pagePath) {
		DetachedCriteria criteria = DetachedCriteria.forClass(QueryCaseBO.class);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("pagePath", pagePath));
		List<QueryCaseBO> result = findByCriteria(criteria);
		try {
			for (QueryCaseBO queryCaseBO : result) {
				if (queryCaseBO.getDislandBlob() != null)
					queryCaseBO.setDisland(StringUtils.convertStreamToString(
							queryCaseBO.getDislandBlob().getBinaryStream()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public QueryCaseBO getQueryCaseBO(long id) {
		return (QueryCaseBO)getById(QueryCaseBO.class, id);
	}
	
	@Override
	public void insertCase(QueryCaseBO bo) throws Exception {
		setDislandBolob(bo);
		save(bo);
	}
	
	@Override
	public void updateCase(QueryCaseBO bo) throws Exception {
		setDislandBolob(bo);
		update(bo);
	}

	@Override
	public void deleteCase(String ids) {
		String[] idArr = StringUtils.split(ids, ",");
		if(StringUtils.isNotEmpty(idArr)) {
			List<Object[]> idList = new LinkedList<Object[]>();
			for (String id : idArr) {
				idList.add(new Object[]{id});
			}
			
			batchDelete("DELETE FROM QEWEB_SYS_QUERYCASE WHERE ID = ?", idList);
		}
	}
	
	/**
	 * 设置queryCaseBO的dislandBlob属性
	 * @param queryCaseBO
	 */
	private void setDislandBolob(QueryCaseBO queryCaseBO) {
//		try {
//			if(StringUtils.isNotEmpty(queryCaseBO.getDisland())) {
//				InputStream input = new ByteArrayInputStream(queryCaseBO.getDisland().getBytes());
//				queryCaseBO.setDislandBlob(Hibernate.createBlob(input));
//			}
//			else {
//				queryCaseBO.setDislandBlob(null);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
