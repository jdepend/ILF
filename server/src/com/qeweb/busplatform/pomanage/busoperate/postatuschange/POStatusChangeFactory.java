package com.qeweb.busplatform.pomanage.busoperate.postatuschange;

import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.poconfirm.ItemConfirm;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.poconfirm.POConfirm;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.poconfirm.WholeConfirm;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.popublish.ItemPublish;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.popublish.POPublish;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.popublish.WholePublish;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.pororeject.ItemReject;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.pororeject.POReject;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.pororeject.WholeReject;

/**
 * 订单状态变更操作factory
 * @see POConfirm
 */
public class POStatusChangeFactory {

	/**
	 * 根据配置获取订单确认操作
	 * @return  整单确认或按订单行确认
	 */
	public POConfirm getOPT_POConfirm() {
		if(BusSettingConstants.isWholeSign())
			return new WholeConfirm();
		else
			return new ItemConfirm();
	}

	/**
	 * 根据配置获取订单驳回操作
	 * @return  整单驳回或按订单行驳回
	 */
	public POReject getOPT_POReject() {
		if(BusSettingConstants.isWholeSign())
			return new WholeReject();
		else
			return new ItemReject();
	}

	/**
	 * 根据配置获取订单发布操作
	 * @return  整单发布或按订行单发布
	 */
	public POPublish getOPT_POPublish() {
		if(BusSettingConstants.isWholeSign())
			return new WholePublish();
		else
			return new ItemPublish();
	}

	/**
	 * 根据配置获取订单关闭操作
	 * @return
	 */
//	public POClose getOPT_POClose() {
//		if(BusSettingConstants.isWholeSign())
//			return new WholeClose();
//		else
//			return new ItemClose();
//	}
}
