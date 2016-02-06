package com.qeweb.busplatform.pomanage.dao.ia;

import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO;
import com.qeweb.framework.base.ia.IBaseDao;

/**
 * 过滤接口DAO
 */
public interface IPODownLoadFilterDao extends IBaseDao {

	/**
	 * 获取相同的采购订单
	 * <p>订单相同条件：采购订单号,采购商ID,供应商ID 全部相同</p>
	 * @param orderBO
	 * @return
	 */
	BP_PurchaseOrderBO getTheSameOrder(BP_PurchaseOrderBO orderBO);

}
