package com.qeweb.busplatform.pomanage.dao.ia;

import java.util.List;

import com.qeweb.framework.base.ia.IBaseDao;

/**
 * 订单明细dao接口
 */
public interface IBP_PurchaseOrderItemDao extends IBaseDao {

	/**
	 * 根据物料编码和物料名称查询采购订单ID
	 * @param materialCode
	 * @param materialName
	 * @return
	 */
	public List<Long> getPOIds(String materialCode, String materialName);

	/**
	 * 根据订单ID查询订单对应的明细ID
	 * @param orderId
	 * @return
	 */
	public List<Long> getPOItemIds(long orderId);
}
