package com.qeweb.busplatform.goodsdelivery.dao.impl;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import com.qeweb.busplatform.common.bop.DeliveryStatus;
import com.qeweb.busplatform.common.bop.ReceiveStatus;
import com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryItemBO;
import com.qeweb.busplatform.goodsdelivery.dao.ia.IBP_VendorGoodsDeliveryItemDao;
import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.base.impl.BaseDaoHibImpl;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 发货dao
 */
public class BP_VendorGoodsDeliveryItemDaoImpl extends BaseDaoHibImpl implements
	IBP_VendorGoodsDeliveryItemDao {

	/**
	 *
	 */
	private static final long serialVersionUID = -4204072212992794754L;

	/**
	 * 根据订单号查询发货单ID
	 * @param materialCode
	 * @param materialName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getDeliveryIds(String purchaseNo) {
		if(StringUtils.isEmptyStr(purchaseNo))
			return null;

		StringBuilder hql = new StringBuilder("select di.deliveryId from BP_VendorGoodsDeliveryItemBO di where ");
		List<Object> params = new LinkedList<Object>();
		hql.append(" di.purchaseOrderItemBO.purchaseOrderBO.purchaseNo like ?");
		params.add("%" + purchaseNo + "%");

		return this.findBySql(hql.toString(), params.toArray());
	}


	/**
	 * 根据订单明细id获取订单行发货单占用数量：已创建未发货
	 * @param orderItemId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public double getOrderItemOccupyQty(long orderItemId) {
		StringBuilder hql = new StringBuilder("select sum(di.deliveryQty) from BP_VendorGoodsDeliveryItemBO di where ");
		hql.append(" di.vendorGoodsDelivery.deliveryStatus = ").append(DeliveryStatus.NO);
		hql.append(" and di.").append(IBaseDao.FIELD_DELETEFLAG).append("=").append(IBaseDao.UNDELETE_SIGNE);
		hql.append(" and di.purchaseOrderItemBO.id = ?");

		List<Object> params = new LinkedList<Object>();
		params.add(orderItemId);


		List<Double> result = this.findBySql(hql.toString(), params.toArray());
		if(ContainerUtil.isNotNull(result))
			return result.get(0) == null ? 0d : result.get(0);
		else
			return 0d;
	}

	/**
	 * 根据订单明细id获取订单行发货数量
	 * @param orderItemId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public double getOrderItemDeliveryQty(long orderItemId) {
		StringBuilder hql = new StringBuilder("select sum(di.deliveryQty) from BP_VendorGoodsDeliveryItemBO di where ");
		hql.append(" di.vendorGoodsDelivery.deliveryStatus = ").append(DeliveryStatus.YES);
		hql.append(" and di.").append(IBaseDao.FIELD_DELETEFLAG).append("=").append(IBaseDao.UNDELETE_SIGNE);
		hql.append(" and di.purchaseOrderItemBO.id = ?");

		List<Object> params = new LinkedList<Object>();
		params.add(orderItemId);


		List<Double> result = this.findBySql(hql.toString(), params.toArray());
		if(ContainerUtil.isNotNull(result))
			return result.get(0) == null ? 0d : result.get(0);
		else
			return 0d;
	}

	/**
	 * 根据供货计划id获取订单行发货数量
	 * @param orderItemId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public double getPOPlanDeliveryQty(long orderPlanId) {
		StringBuilder hql = new StringBuilder("select sum(di.deliveryQty) from BP_VendorGoodsDeliveryItemBO di where ");
		hql.append(" di.vendorGoodsDelivery.deliveryStatus = ").append(DeliveryStatus.YES);
		hql.append(" and di.").append(IBaseDao.FIELD_DELETEFLAG).append("=").append(IBaseDao.UNDELETE_SIGNE);
		hql.append(" and di.purchaseGoodsPlan.id = ?");

		List<Object> params = new LinkedList<Object>();
		params.add(orderPlanId);


		List<Double> result = this.findBySql(hql.toString(), params.toArray());
		if(ContainerUtil.isNotNull(result))
			return result.get(0) == null ? 0d : result.get(0);
		else
			return 0d;
	}

	@SuppressWarnings("unchecked")
	@Override
	public double getOrderItemHaveRecDlvQty(long orderItemId) {
		StringBuilder hql = new StringBuilder("select sum(di.deliveryQty) from BP_VendorGoodsDeliveryItemBO di where ");
		hql.append(" di.vendorGoodsDelivery.deliveryStatus = ").append(DeliveryStatus.YES);
		hql.append(" and di.vendorGoodsDelivery.receiveStatus = ").append(ReceiveStatus.YES);
		hql.append(" and di.").append(IBaseDao.FIELD_DELETEFLAG).append("=").append(IBaseDao.UNDELETE_SIGNE);
		hql.append(" and di.purchaseOrderItemBO.id = ?");

		List<Object> params = new LinkedList<Object>();
		params.add(orderItemId);


		List<Double> result = this.findBySql(hql.toString(), params.toArray());
		if(ContainerUtil.isNotNull(result))
			return result.get(0) == null ? 0d : result.get(0);
		else
			return 0d;
	}

	@SuppressWarnings("unchecked")
	@Override
	public double getOrderItemTotalDlvQty(long orderItemId) {
		StringBuilder hql = new StringBuilder("select sum(di.deliveryQty) from BP_VendorGoodsDeliveryItemBO di where ");
		hql.append(" di.").append(IBaseDao.FIELD_DELETEFLAG).append("=").append(IBaseDao.UNDELETE_SIGNE);
		hql.append(" and di.purchaseOrderItemBO.id = ?");

		List<Object> params = new LinkedList<Object>();
		params.add(orderItemId);


		List<Double> result = this.findBySql(hql.toString(), params.toArray());
		if(ContainerUtil.isNotNull(result))
			return result.get(0) == null ? 0d : result.get(0);
		else
			return 0d;
	}

	/**
	 * 据订单明细id获取订单行发货在途数量
	 * 发货单无对应收货单则为在途
	 * @param orderItemId
	 * @return
	 */
	@Override
	public double getOrderItemOnWayQty(long orderItemId) {
		StringBuilder sb = new StringBuilder("");
		sb.append("SELECT SUM(I.DELIVERY_QTY) FROM QEWEB_BP_VEN_GO_DLV_ITEM I WHERE ID IN (SELECT DISTINCT DI.ID FROM QEWEB_BP_VEN_GOODS_DLV DL INNER JOIN");
		sb.append(" QEWEB_BP_VEN_GO_DLV_ITEM DI ON DL.ID = DI.DELIVERY_ID LEFT JOIN QEWEB_BP_BUY_GO_REC_ITEM RI ON DI.ID = RI.DELIVERY_ITEM_ID WHERE ");
		sb.append(" DL.DELETE_FLAG=0 AND DI.DELETE_FLAG=0 AND DL.DELIVERY_STATUS=1 AND RI.DELETE_FLAG IS NULL AND DI.PURCHASE_ITEM_ID="
				+ orderItemId + ")");

		BigDecimal result = (BigDecimal)this.createQueryUniqueResult(sb.toString());
		return result == null ? 0d : result.doubleValue();
	}

	/**
	 * 获取订单行的发货单未收货明细
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<BP_VendorGoodsDeliveryItemBO> getUnRecItemByPOItemId(long orderItemId) {
		StringBuilder hql = new StringBuilder("from BP_VendorGoodsDeliveryItemBO di where ");
		hql.append(" di.").append(IBaseDao.FIELD_DELETEFLAG).append("=").append(IBaseDao.UNDELETE_SIGNE);
		hql.append(" and di.vendorGoodsDelivery.receiveStatus = ?");
		hql.append(" and di.purchaseOrderItemBO.id = ?");

		List<Object> params = new LinkedList<Object>();
		params.add(ReceiveStatus.NO);
		params.add(orderItemId);

		List<BP_VendorGoodsDeliveryItemBO> result = this.findBySql(hql.toString(), params.toArray());
		return result;
	}

}
