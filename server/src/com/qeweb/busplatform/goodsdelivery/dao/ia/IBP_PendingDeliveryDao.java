package com.qeweb.busplatform.goodsdelivery.dao.ia;

import java.util.List;

import com.qeweb.busplatform.goodsdelivery.bo.BP_PendingDeliveryBO;
import com.qeweb.busplatform.goodsdelivery.bo.BP_PendingDeliveryMaterialBO;
import com.qeweb.framework.base.ia.IBaseDao;

/**
 * 待发货dao接口
 */
public interface IBP_PendingDeliveryDao extends IBaseDao {

	double getOrderItemUnsendQty(long orderItemId);

	List<BP_PendingDeliveryBO> getSameTypePending(BP_PendingDeliveryMaterialBO pendingMateirlBO);

}
