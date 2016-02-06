package com.qeweb.framework.common.syssetting.savecase.dao;

import java.util.List;

import com.qeweb.framework.common.syssetting.savecase.bo.QueryCaseBO;

/**
 * 保存查询条件 dao 接口
 */
public interface ISaveCaseDao {
	/**
	 * 当前用户在pagePath页面是否设置过名称是caseName的查询条件
	 * @param caseName		查询条件名称
	 * @param userId		当前登录用户ID
	 * @param pagePath		页面路径
	 * @return
	 */
	boolean isExists(String caseName, long userId, String pagePath);
	
	/**
	 * 查找查询条件
	 * @param userId		当前登录用户ID
	 * @param pagePath		页面路径
	 * @return
	 */
	List<QueryCaseBO> findQueryCase(long userId, String pagePath); 
	
	/**
	 * 根据id获取QueryCaseBO
	 * @param id
	 * @return
	 */
	QueryCaseBO getQueryCaseBO(long id);
	
	/**
	 * 新增查询条件
	 * @param bo
	 */
	void insertCase(QueryCaseBO bo) throws Exception;
	
	/**
	 * 修改查询条件
	 * @param bo
	 */
	void updateCase(QueryCaseBO bo) throws Exception;
	
	/**
	 * 删除查询条件
	 * @param ids			数据id, 以逗号分隔
	 */
	void deleteCase(String ids);
}
