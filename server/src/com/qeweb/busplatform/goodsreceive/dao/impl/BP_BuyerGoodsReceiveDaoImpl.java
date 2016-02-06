package com.qeweb.busplatform.goodsreceive.dao.impl;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO;
import com.qeweb.busplatform.goodsreceive.dao.ia.IBP_BuyerGoodsReceiveDao;
import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.base.impl.BaseDaoHibImpl;
import com.qeweb.framework.common.utils.ContainerUtil;

/**
 *
 */
public class BP_BuyerGoodsReceiveDaoImpl extends BaseDaoHibImpl implements
		IBP_BuyerGoodsReceiveDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2931125155573147725L;

	/**
	 * 获取系统是否已存在此收货单, 判断是否存在的条件:
	 * 存在某收货单, 该收货单与receiveBO的收货单号, 供应商id, 采购组织id相同.
	 * @param receiveBO
	 * @return
	 */
	@Override
	public boolean isExists(BP_BuyerGoodsReceiveBO receiveBO) {
		List<?> receiveBOs = this.findBySql("from BP_BuyerGoodsReceiveBO where receiveNo = ? and buyerId = ? and vendorId = ? ",
				new Object[]{receiveBO.getReceiveNo(), receiveBO.getBuyerId(), receiveBO.getVendorId()});

		return ContainerUtil.isNotNull(receiveBOs);
	}

	/**
	 * 根据收货单ID统计收货数量
	 * @param receiveId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public double getReceiveQty(long receiveId) {
		StringBuilder hql = new StringBuilder("select sum(ri.receiveQty) from BP_BuyerGoodsReceiveItemBO ri where ");
		hql.append("ri.").append(IBaseDao.FIELD_DELETEFLAG).append("=").append(IBaseDao.UNDELETE_SIGNE);
		hql.append(" and ri.buyerGoodsReceiveBO.id = ?");

		List<Object> params = new LinkedList<Object>();
		params.add(receiveId);

		List<Double> result = this.findBySql(hql.toString(), params.toArray());
		if(ContainerUtil.isNotNull(result))
			return result.get(0) == null ? 0d : result.get(0);
		else
			return 0d;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Timestamp getLastDownloadTime() {
		StringBuilder hql = new StringBuilder("select max(ri.modifyTime) from BP_BuyerGoodsReceiveBO ri where ");
		hql.append("ri.").append(IBaseDao.FIELD_DELETEFLAG).append("=").append(IBaseDao.UNDELETE_SIGNE);

		List<Timestamp> result = this.findBySql(hql.toString());
		if(ContainerUtil.isNotNull(result))
			return result.get(0);
		else
			return null;
	}

}
