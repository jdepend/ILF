package com.qeweb.framework.common.syssetting.savecase.service;

import java.util.List;

import com.qeweb.framework.common.syssetting.savecase.bo.QueryCaseBO;

/**
 * 
 * 保存查询条件
 */
public interface ISaveCaseService {
	
	/**
	 * 查找查询条件
	 * @param userId		当前登录用户ID
	 * @param pagePath		页面路径
	 * @return
	 */
	List<QueryCaseBO> findQueryCase(long userId, String pagePath); 
	
	/**
	 * 保存查询条件
	 * @param caseName		查询条件名称
	 * @param userId		当前登录用户ID
	 * @param pagePath		页面路径
	 * @param disland		数据岛
	 * @param oldId			
	 * @throws Exception
	 */
	void saveCase(String caseName, long userId, String pagePath, String disland, String oldId) throws Exception;
	
	/**
	 * 删除查询条件
	 * @param ids			数据id, 以逗号分隔
	 */
	void deleteCase(String ids);
}
