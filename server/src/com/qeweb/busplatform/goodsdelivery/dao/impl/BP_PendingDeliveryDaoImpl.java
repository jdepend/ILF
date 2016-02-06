package com.qeweb.busplatform.goodsdelivery.dao.impl;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.busplatform.goodsdelivery.bo.BP_PendingDeliveryBO;
import com.qeweb.busplatform.goodsdelivery.bo.BP_PendingDeliveryMaterialBO;
import com.qeweb.busplatform.goodsdelivery.dao.ia.IBP_PendingDeliveryDao;
import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.base.impl.BaseDaoHibImpl;
import com.qeweb.framework.common.utils.ContainerUtil;

/**
 * 待发货dao
 */
public class BP_PendingDeliveryDaoImpl extends BaseDaoHibImpl implements
	IBP_PendingDeliveryDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8018945055056257618L;

	/**
	 * 根据订单明细id 获取明细未发货数量
	 * @param orderItemId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public double getOrderItemUnsendQty(long orderItemId) {
		StringBuilder hql = new StringBuilder("select sum(pending.shuldDlvQty) from BP_PendingDeliveryBO pending where ");
		hql.append("pending.").append(IBaseDao.FIELD_DELETEFLAG).append("=").append(IBaseDao.UNDELETE_SIGNE);
		hql.append(" and pending.itemId = ?");

		List<Object> params = new LinkedList<Object>();
		params.add(orderItemId);

		List<Double> result = this.findBySql(hql.toString(), params.toArray());
		if(ContainerUtil.isNotNull(result))
			return result.get(0) == null ? 0d : result.get(0);
		else
			return 0d;
	}

	/**
	 * 获取同一物料类型下的所有待发货明细
	 * @param pendingMateirlBO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BP_PendingDeliveryBO> getSameTypePending(BP_PendingDeliveryMaterialBO pendingMateirlBO) {

		String hql = "select pending from BP_PendingDeliveryBO pending,BP_PurchaseOrderItemBO item where "
			+ " item.id=pending.itemId and item.material.id = ? and item.purchaseOrderBO.buyer.id = ? "
			+ " and item.purchaseOrderBO.vendor.id = ? order by pending.orderTime asc";

		return this.findBySql(hql,new Object[]{pendingMateirlBO.getMaterialId(), pendingMateirlBO.getBuyerId(),
				pendingMateirlBO.getVendorId()});

	}
}
