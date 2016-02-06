package com.qeweb.busplatform.goodsreceive.bo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.qeweb.busplatform.common.bop.DeliveryStatus;
import com.qeweb.busplatform.common.bop.ReceiveStatus;
import com.qeweb.busplatform.common.bop.VerifyStatus;
import com.qeweb.busplatform.common.operate.BusOptManager;
import com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO;
import com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryItemBO;
import com.qeweb.busplatform.goodsdelivery.busoperate.recive.PendingReceive;
import com.qeweb.busplatform.pomanage.POStatusFactory;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.common.utils.PropertyUtils;

/**
 * 收货看板
 */
public class BP_PendingReceiveBO extends BP_VendorGoodsDeliveryBO {

	private static final long serialVersionUID = -5656421397618775609L;
	
	public BP_PendingReceiveBO() {
		super();
		addBOP("deliveryStatus", new POStatusFactory().getSendStatus());
	}

	//收货看板操作
	private PendingReceive OPT_PendingReceive;

	@SuppressWarnings("rawtypes")
	protected Class getSearchClass() {
		return BP_VendorGoodsDeliveryBO.class;
	}

	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		//订单号条件
		List<Long> dlvIds = new ArrayList<Long>();
		List<Long> ids = new BP_VendorGoodsDeliveryItemBO().getDeliveryIds(bot.getValue("purchaseNo") + "");
		if(ContainerUtil.isNotNull(ids))
			dlvIds.addAll(ids);

		//收货状态为"未收货"
		bot.push("id", dlvIds);
		bot.getBotMap().remove("purchaseNo");
		bot.getBotMap().remove("receiveStatus");

		return super.query(getOPT_PendingReceive().getBOT(bot), start);
	}

	@Override
	protected void initPreferencePage(Page page) {
		List<BusinessObject> boList = new LinkedList<BusinessObject>();
		for (int i = 0; i < page.getItems().size(); i++) {
			BP_VendorGoodsDeliveryBO bo = (BP_VendorGoodsDeliveryBO) page.getItems().get(i);
			// 收货看板BOdeliveryStatus覆盖了父类的bop，返回的记录需要转换为收货看板BO
			BP_PendingReceiveBO _bo = new BP_PendingReceiveBO();
			PropertyUtils.copyPropertyWithOutNull(_bo, bo);
			BOHelper.initPreferencePage_lazy(_bo, this);
			//如果收货状态不是"未收货" 或发货状态是"未发货", 收货按钮隐藏
			if(bo.getReceiveStatus() != ReceiveStatus.NO || bo.getDeliveryStatus() == DeliveryStatus.NO) {
				OperateBOP receiveBOP = new OperateBOP();
				receiveBOP.getStatus().setHidden(true);
				_bo.addOperateBOP("goReceive", receiveBOP);
			}
			boList.add(_bo);
		}

		page.setBOList(boList);
	}

	/**
	 * 设置按钮状态，包括发货/取消发货/审核通过/审核驳回
	 * @param bo
	 * @return
	 */
	public BP_VendorGoodsDeliveryBO setBtnStatus(BP_VendorGoodsDeliveryBO bo) {
		//审核通过
		addOperateBOP("verifyPass", BusOptManager.getDeliveryVerifyBtn().getPassBtn(bo));
		//审核驳回
		addOperateBOP("verifyReject", BusOptManager.getDeliveryVerifyBtn().getRejectBtn(bo));

		return this;
	}

	/**
	 * 审核通过
	 * @param deliveryBills
	 * @throws Exception
	 */
	public void verifyPass(List<BusinessObject> boList) throws Exception {
		BusinessObject bo = boList.get(0);
		verify(bo.getId(), VerifyStatus.YES);
	}

	/**
	 * 审核驳回
	 * @param deliveryBills
	 * @throws Exception
	 */
	public void verifyReject(List<BusinessObject> boList) throws Exception {
		BusinessObject bo = boList.get(0);
		verify(bo.getId(), VerifyStatus.REJECT);
	}

	/**
	 * 审核发货单
	 * @param id	发货单id
	 * @param verifyStatus	审核状态
	 * @throws Exception
	 */
	private void verify(long id, int verifyStatus) throws Exception {
		BP_VendorGoodsDeliveryBO result = (BP_VendorGoodsDeliveryBO) this.getRecord(id);
		result.setVerifyStatus(verifyStatus);
		result.setVerifyTime(DateUtils.getCurrentTimestamp());
		result.update();
	}

	public PendingReceive getOPT_PendingReceive() {
		if(OPT_PendingReceive == null)
			OPT_PendingReceive = BusOptManager.getOptPendingReceive();
		return OPT_PendingReceive;
	}

	public void setOPT_PendingReceive(PendingReceive oPT_PendingReceive) {
		OPT_PendingReceive = oPT_PendingReceive;
	}
}
