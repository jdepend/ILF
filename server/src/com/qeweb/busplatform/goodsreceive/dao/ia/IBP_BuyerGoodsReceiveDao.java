package com.qeweb.busplatform.goodsreceive.dao.ia;

import java.sql.Timestamp;

import com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO;
import com.qeweb.framework.base.ia.IBaseDao;

/**
 * 收货dao接口
 */
public interface IBP_BuyerGoodsReceiveDao extends IBaseDao {

	/**
	 * 获取系统是否已存在此收货单, 判断是否存在的条件:
	 * 存在某收货单, 该收货单与receiveBO的收货单号, 供应商id, 采购组织id相同.
	 * @param receiveBO
	 * @return
	 */
	boolean isExists(BP_BuyerGoodsReceiveBO receiveBO);

	/**
	 * 根据收货单ID统计收货数量
	 * @param receiveId
	 * @return
	 */
	double getReceiveQty(long receiveId);

	/**
	 * 获取收货单的最后下载时间
	 * @return
	 */
	Timestamp getLastDownloadTime();

}
