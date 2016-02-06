package com.qeweb.busplatform.goodsdelivery.busoperate.recive;

import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.busplatform.common.bop.DeliveryStatus;
import com.qeweb.busplatform.common.bop.ReceiveStatus;
import com.qeweb.framework.bc.BOTemplate;

/**
 * 收货看板相关操作
 */
public class PendingReceive {

	/**
	 * 收货看板查询条件.
	 * 如果不需要审核, 则收货看板中仅能看到发货状态为已发货，未收货的记录.
	 * @param bo
	 * @param bot
	 * @param start
	 * @return
	 * @throws Exception
	 */
	public BOTemplate getBOT(BOTemplate bot) throws Exception {
		if(!(BusSettingConstants.isVerify()))
			bot.push("deliveryStatus", DeliveryStatus.YES);

		bot.push("receiveStatus", ReceiveStatus.NO);
		return bot;
	}
}
