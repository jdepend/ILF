package com.qeweb.busplatform.goodsreceive.filter.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO;
import com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveItemBO;
import com.qeweb.busplatform.goodsreceive.dao.ia.IReceiveDownLoadFilterDao;
import com.qeweb.busplatform.goodsreceive.filter.IReceiveDownLoadFilter;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;

/**
 *  收货单下载过滤器
 */
public class ReceiveDownLoadFilterImpl implements IReceiveDownLoadFilter {

	private IReceiveDownLoadFilterDao receiveDownLoadFilterDao;

	/**
	 * 从boList中过滤收货单信息,将不符合条件的收货单剔除.
	 * <p>
	 * 		将被过滤掉的收货单包括:
	 * 		<li>1.收货单号为空;
	 * 		<li>2.采购组织为空;
	 * 		<li>3.供应商为空;
	 * 		<li>4.收货单已存在;
	 * 		<li>5.明细为空;
	 * 		<li>6.订单明细不符合要求的(收货明细为空,收货明细无对应订单行,收货明细无对应送货明细).
	 * </p>
	 * @param boList
	 * @return 符合条件的收货单
	 */
	@Override
	public Map<String, List<BP_BuyerGoodsReceiveBO>> filterReceiveBOs(List<BP_BuyerGoodsReceiveBO> boList) {
		if(ContainerUtil.isNull(boList))
			return null;

		Map<String, List<BP_BuyerGoodsReceiveBO>> result = new HashMap<String, List<BP_BuyerGoodsReceiveBO>>();
		List<BP_BuyerGoodsReceiveBO> insertResult = new LinkedList<BP_BuyerGoodsReceiveBO>();
		List<BP_BuyerGoodsReceiveBO> updateResult = new LinkedList<BP_BuyerGoodsReceiveBO>();

		//TODO 1.记录日志的位置; 2.日志国际化
		StringBuffer logBuf = new StringBuffer("");
		logBuf.append("-->开始同步收货单操作，共有条 " + boList.size() + " 收货单信息\n");
		logBuf.append("-->开始对收货单信息有效性进行验证...\n");
		for(BP_BuyerGoodsReceiveBO receiveBO : boList) {
			if(StringUtils.isEmpty(receiveBO.getReceiveNo())) {
				logBuf.append("收货单号为空，忽略此条收货..\n");
				continue;
			}
			else if(receiveBO.getBuyerId() == 0L) {
				logBuf.append("收货单采购组织为空，忽略此条收货，收货单号：" + receiveBO.getReceiveNo()).append("\n");
				continue;
			}
			else if(receiveBO.getVendorId() == 0L) {
				logBuf.append("收货单供应商为空，忽略此条收货，收货单号：" + receiveBO.getReceiveNo()).append("\n");
				continue;
			}

			else if(ContainerUtil.isNull(receiveBO.getBuyerGoodsReceiveItemBOs())) {
				logBuf.append("收货单明细为空，忽略此条收货, 收货单号：" + receiveBO.getReceiveNo()).append("\n");
				continue;
			}
			//验证收货单明细
			else if(!validateReceiveBOs(receiveBO, logBuf))
				continue;

			BP_BuyerGoodsReceiveBO oldReceiveBO = getReceiveDownLoadFilterDao().getTheSameRecBO(receiveBO);
			if(oldReceiveBO == null) {
				insertResult.add(receiveBO);
			}
			else {
				updateResult.add(receiveBO);
			}

		}
		result.put(INSERT, insertResult);
		result.put(UPDATE, updateResult);
		logBuf.append("验证收货单信息结束验证结束，共" + (insertResult.size() + updateResult.size()) + "条通过验证.").append("\n");

		return result;
	}

	/**
	 * 同步收货单，对收货单明细进行验证
	 * @param receiveBO
	 * @param logBuf
	 * @return
	 */
	private boolean validateReceiveBOs(BP_BuyerGoodsReceiveBO receiveBO, StringBuffer logBuf) {
		boolean passItem = true;
		for(BP_BuyerGoodsReceiveItemBO recItem : receiveBO.getBuyerGoodsReceiveItemBOs()) {
			if(recItem.getReceiveQty() == null) {
				logBuf.append("收货明细为空，行号" + recItem.getItemNo()).append("\n");
				passItem = false;
			}
			if(recItem.getPurchaseOrderItemBO() == null) {
				logBuf.append("收货明细无对应订单行，行号" + recItem.getItemNo()).append("\n");
				passItem = false;
			}
			if(recItem.getVendorGoodsDeliveryItemBO() == null) {
				logBuf.append("收货明细无对应送货明细，行号" + recItem.getItemNo()).append("\n");
				passItem = false;
			}
		}

		return passItem;
	}

	public IReceiveDownLoadFilterDao getReceiveDownLoadFilterDao() {
		if(receiveDownLoadFilterDao == null)
			receiveDownLoadFilterDao = (IReceiveDownLoadFilterDao)SpringConstant.getCTX().getBean("IReceiveDownLoadFilterDao");

		return receiveDownLoadFilterDao;
	}

	public void setReceiveDownLoadFilterDao(
			IReceiveDownLoadFilterDao receiveDownLoadFilterDao) {
		this.receiveDownLoadFilterDao = receiveDownLoadFilterDao;
	}

}
