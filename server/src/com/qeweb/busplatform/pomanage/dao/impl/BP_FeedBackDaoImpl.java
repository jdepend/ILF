package com.qeweb.busplatform.pomanage.dao.impl;

import java.util.List;

import com.qeweb.busplatform.pomanage.dao.ia.IBP_FeedBackDao;
import com.qeweb.framework.base.impl.BaseDaoHibImpl;
import com.qeweb.framework.common.utils.ContainerUtil;

/**
 *  反馈dao Impl
 */
public class BP_FeedBackDaoImpl extends BaseDaoHibImpl implements IBP_FeedBackDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9208947246550304137L;

	/**
	 * 获取单据反馈数
	 * @param billId 单据ID
	 * @param billType 单据类别
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Integer getfeedbackCount(long billId, Integer billType) {
		String hql = "select count(*) from BP_FeedBackBO fb where fb.billId = ? and fb.billType = ?";

		List count = findBySql(hql, new Object[]{billId, billType});
		return ContainerUtil.isNull(count) ? 0 : Integer.valueOf(count.get(0) + "");
	}

}
