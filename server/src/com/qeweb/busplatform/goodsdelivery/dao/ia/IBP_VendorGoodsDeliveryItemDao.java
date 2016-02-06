package com.qeweb.busplatform.goodsdelivery.dao.ia;

import java.util.List;

import com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryItemBO;
import com.qeweb.framework.base.ia.IBaseDao;

/**
 * 发货明细dao接口
 */
public interface IBP_VendorGoodsDeliveryItemDao extends IBaseDao {

	List<Long> getDeliveryIds(String purchaseNo);

	/**
	 * 获取订单行已创建发货单占用数量（发货单状态未发货）
	 * @param orderItemId
	 * @return
	 */
	double getOrderItemOccupyQty(long orderItemId);

	/**
	 * 获取订单行已发货数量（发货单已发货）
	 * @param orderItemId
	 * @return
	 */
	double getOrderItemDeliveryQty(long orderItemId);


	/**
	 * 获取订单行发货单已收货了的发货数量总量
	 * @param orderItemId
	 * @return
	 */
	double getOrderItemHaveRecDlvQty(long orderItemId);

	/**
	 * 获取订单行发货数量包括：<br>
	 * 1、发货单已创建未发货占用数量
	 * 2、发货单已发货
	 * @param orderItemId
	 * @return
	 */
	double getOrderItemTotalDlvQty(long orderItemId);

	/**
	 * 供货计划已发货的数量（发货单状态已发货）
	 * @param orderPlanId
	 * @return
	 */
	double getPOPlanDeliveryQty(long orderPlanId);

	/**
	 * 获取订单行发货在途数量（发货单状态已发货未收货）
	 * @param orderItemId
	 * @return
	 */
	double getOrderItemOnWayQty(long orderItemId);

	/**
	 * 根据订单行ID获取未收货的发货单明细
	 * @return
	 */
	List<BP_VendorGoodsDeliveryItemBO> getUnRecItemByPOItemId(long orderItemId);

}
