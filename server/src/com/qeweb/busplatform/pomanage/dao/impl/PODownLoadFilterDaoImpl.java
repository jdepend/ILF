package com.qeweb.busplatform.pomanage.dao.impl;

import java.util.List;

import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO;
import com.qeweb.busplatform.pomanage.dao.ia.IPODownLoadFilterDao;
import com.qeweb.framework.base.impl.BaseDaoHibImpl;
import com.qeweb.framework.common.utils.ContainerUtil;

/**
 * 过滤采购订单DAO impl
 */
public class PODownLoadFilterDaoImpl extends BaseDaoHibImpl implements IPODownLoadFilterDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9038464862448064249L;

	/**
	 * 获取相同的采购订单
	 * <p>订单相同条件：采购订单号,采购商ID,供应商ID 全部相同</p>
	 * @param orderBO
	 * @return
	 */
	@Override
	public BP_PurchaseOrderBO getTheSameOrder(BP_PurchaseOrderBO orderBO) {
		List<?> oldOrderList = this.findBySql("from BP_PurchaseOrderBO where purchaseNo = ? and buyer.id = ? and vendor.id = ? ",
				new Object[]{orderBO.getPurchaseNo(), orderBO.getBuyer().getId(), orderBO.getVendor().getId()});

		return ContainerUtil.isNotNull(oldOrderList) ? (BP_PurchaseOrderBO)oldOrderList.get(0) : null;
	}

}
