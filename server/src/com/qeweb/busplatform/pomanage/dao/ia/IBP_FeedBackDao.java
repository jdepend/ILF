package com.qeweb.busplatform.pomanage.dao.ia;

import com.qeweb.framework.base.ia.IBaseDao;

/**
 * 反馈dao接口
 */
public interface IBP_FeedBackDao extends IBaseDao {

	/**
	 * 获取单据反馈数
	 * @param billId 单据ID
	 * @param billType 单据类别
	 * @return
	 */
	public Integer getfeedbackCount(long billId, Integer billType);
}
