package com.qeweb.busplatform.goodsreceive.dao.impl;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.busplatform.goodsreceive.dao.ia.IBP_BuyerGoodsReceiveItemDao;
import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.base.impl.BaseDaoHibImpl;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;

/**
 *
 */
public class BP_BuyerGoodsReceiveItemDaoImpl extends BaseDaoHibImpl implements
		IBP_BuyerGoodsReceiveItemDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3998213711893142612L;

	/**
	 * 根据采购订单号获取收获单IDs
	 * @param purchaseNo
	 * @return
	 */
	@SuppressWarnings("all")
	@Override
	public List<Long> getReceiveIds(String purchaseNo){
		if(StringUtils.isEmpty(purchaseNo))
			return null;

		String hql = "select item.buyerGoodsReceiveBO.id from BP_BuyerGoodsReceiveItemBO item where item.purchaseOrderItemBO.purchaseOrderBO.purchaseNo like ?";
		List<Object> params = new LinkedList<Object>();
		params.add(purchaseNo + "%");
		return this.findBySql(hql.toString(), params.toArray());
	}

	/**
	 * 根据订单明细获取明细收货数量
	 * @param orderItemId 订单明细id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public double getOrderItemReceiveQty(long orderItemId) {
		StringBuilder hql = new StringBuilder("select sum(ri.receiveQty) from BP_BuyerGoodsReceiveItemBO ri where ");
		hql.append("ri.").append(IBaseDao.FIELD_DELETEFLAG).append("=").append(IBaseDao.UNDELETE_SIGNE);
		hql.append(" and ri.purchaseOrderItemBO.id = ?");

		List<Object> params = new LinkedList<Object>();
		params.add(orderItemId);

		List<Double> result = this.findBySql(hql.toString(), params.toArray());
		if(ContainerUtil.isNotNull(result))
			return result.get(0) == null ? 0d : result.get(0);
		else
			return 0d;
	}

	/**
	 * 根据订单明细获取明细验退数量
	 * @param orderItemId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public double getOrderItemGoodsRejectQty(long orderItemId) {
		StringBuilder hql = new StringBuilder("select sum(ri.goodsRejectQty) from BP_BuyerGoodsReceiveItemBO ri where ");
		hql.append("ri.").append(IBaseDao.FIELD_DELETEFLAG).append("=").append(IBaseDao.UNDELETE_SIGNE);
		hql.append(" and ri.purchaseOrderItemBO.id = ?");

		List<Object> params = new LinkedList<Object>();
		params.add(orderItemId);

		List<Double> result = this.findBySql(hql.toString(), params.toArray());
		if(ContainerUtil.isNotNull(result))
			return result.get(0) == null ? 0d : result.get(0);
		else
			return 0d;
	}
}
