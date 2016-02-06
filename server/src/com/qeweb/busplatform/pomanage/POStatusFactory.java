package com.qeweb.busplatform.pomanage;

import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.busplatform.common.bop.SendStatus;
import com.qeweb.busplatform.common.bop.SendStatusX;
import com.qeweb.busplatform.pomanage.bop.CloseStatus;
import com.qeweb.busplatform.pomanage.bop.CloseStatusX;
import com.qeweb.busplatform.pomanage.bop.ConfirmStatus;
import com.qeweb.busplatform.pomanage.bop.ConfirmStatusX;

/**
 * 单据状态factory
 */
public class POStatusFactory {

	/**
	 * 根据配置获取关闭状态(是否包含部分关闭状态)
	 * @return
	 */
	public CloseStatus getCloseStatus() {
		if(BusSettingConstants.isCloseForPart())
			return new CloseStatusX();
		else
			return new CloseStatus();
	}

	/**
	 * 根据配置获取关闭状态(是否包含部分关闭状态)
	 * @return
	 */
	public ConfirmStatus getConfirmStatus() {
		if(BusSettingConstants.isWholeSign())
			return new ConfirmStatus();
		else
			return new ConfirmStatusX();
	}

	/**
	 * 根据配置获取发货状态(是否包含未发货)
	 * 发货单不需要审核时，收货看板发货状态不包含”未发货“
	 * @return
	 */
	public SendStatus getSendStatus(){
		if(BusSettingConstants.isVerify())
			return new SendStatus();
		else
			return new SendStatusX();
	}
}
