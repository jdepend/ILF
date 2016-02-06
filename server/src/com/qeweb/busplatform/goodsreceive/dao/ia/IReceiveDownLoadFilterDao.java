package com.qeweb.busplatform.goodsreceive.dao.ia;

import com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO;
import com.qeweb.framework.base.ia.IBaseDao;

/**
 * 收货过滤接口DAO
 */
public interface IReceiveDownLoadFilterDao extends IBaseDao {

	/**
	 * 获取相同的收货单
	 * <p>收货单相同条件：收货单号,采购商ID,供应商ID 全部相同</p>
	 * @param receiveBO
	 * @return
	 */
	BP_BuyerGoodsReceiveBO getTheSameRecBO(BP_BuyerGoodsReceiveBO receiveBO);

}
