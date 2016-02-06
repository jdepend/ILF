package com.qeweb.busplatform.pomanage.dao.ia;

import java.util.List;

import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderStatisticsBO;
import com.qeweb.framework.base.ia.IBaseDao;

/**
 * 订单执行情况查询DAO
 */
public interface IBP_PurchaseOrderExecutionDao extends IBaseDao {

	/**
	 * 获取需统计的订单明细
	 * @return [订单行ID, 订单数量, 确认状态, 关闭状态]
	 */
	List<Object[]> getPendingStatisticsOrderItems();

	/**
	 * 获取订单行统计结果
	 * @param itemId
	 * @return
	 */
	BP_PurchaseOrderStatisticsBO getOrderStatisticsByItmeId(long itemId);

	/**
	 * dao session.flush
	 */
	void flush();
}
