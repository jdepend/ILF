package com.qeweb.busplatform.goodsreceive.dao.ia;

import java.util.List;

import com.qeweb.framework.base.ia.IBaseDao;

/**
 * 收货dao接口
 */
public interface IBP_BuyerGoodsReceiveItemDao extends IBaseDao {

	List<Long> getReceiveIds(String purchaseNo);

	double getOrderItemReceiveQty(long orderItemId);

	double getOrderItemGoodsRejectQty(long orderItemId);

}
