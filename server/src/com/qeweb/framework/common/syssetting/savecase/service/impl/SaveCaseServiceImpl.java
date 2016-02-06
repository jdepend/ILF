package com.qeweb.framework.common.syssetting.savecase.service.impl;

import java.util.List;

import com.qeweb.framework.common.syssetting.savecase.bo.QueryCaseBO;
import com.qeweb.framework.common.syssetting.savecase.dao.ISaveCaseDao;
import com.qeweb.framework.common.syssetting.savecase.service.ISaveCaseService;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;

/**
 * 
 * 保存查询条件, 关系型数据库实现
 *
 */
public class SaveCaseServiceImpl implements ISaveCaseService {

	private ISaveCaseDao saveCaseDao;
	
	@Override
	public List<QueryCaseBO> findQueryCase(long userId, String pagePath) {
		return getSaveCaseDao().findQueryCase(userId, pagePath);
	}

	@Override
	public void saveCase(String caseName, long userId, String pagePath,
			String disland, String oldId) throws Exception {
		QueryCaseBO bo = null;
		//添加新纪录
		if(StringUtils.isEmpty(oldId)) {
			validate(caseName, userId, pagePath);
			bo = new QueryCaseBO();
			setForQueryCaseBO(bo, caseName, userId, pagePath, disland);
			getSaveCaseDao().insertCase(bo);
		}
		//修改原记录
		else {
			bo = getSaveCaseDao().getQueryCaseBO(StringUtils.convertLong(oldId));
			//当修改后的查询条件名称和原名称不一致时校验
			if(StringUtils.isNotEqual(caseName, bo.getCaseName())) 
				validate(caseName, userId, pagePath);
			
			setForQueryCaseBO(bo, caseName, userId, pagePath, disland);
			getSaveCaseDao().updateCase(bo);
		}
		
	}

	@Override
	public void deleteCase(String ids) {
		getSaveCaseDao().deleteCase(ids);
	}
	
	/**
	 * 当前用户在pagePath页面是否设置过名称是caseName的查询条件
	 * @param caseName		查询条件名称
	 * @param userId		当前登录用户ID
	 * @param pagePath		页面路径
	 * @return
	 */
	private void validate(String caseName, long userId, String pagePath) throws BOException {
		if(getSaveCaseDao().isExists(caseName, userId, pagePath))
			throw new BOException("com.qeweb.framework.app.action.saveCase_err2");
	}
	
	private void setForQueryCaseBO(QueryCaseBO bo, String caseName, long userId, String pagePath, String disland) {
		bo.setUserId(userId);
		bo.setCaseName(caseName);
		bo.setPagePath(pagePath);
		bo.setDisland(disland);
	}

	public ISaveCaseDao getSaveCaseDao() {
		return saveCaseDao;
	}

	public void setSaveCaseDao(ISaveCaseDao saveCaseDao) {
		this.saveCaseDao = saveCaseDao;
	}
}
