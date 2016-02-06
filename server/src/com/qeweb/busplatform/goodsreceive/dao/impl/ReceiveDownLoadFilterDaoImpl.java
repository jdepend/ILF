package com.qeweb.busplatform.goodsreceive.dao.impl;

import java.util.List;

import com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO;
import com.qeweb.busplatform.goodsreceive.dao.ia.IReceiveDownLoadFilterDao;
import com.qeweb.framework.base.impl.BaseDaoHibImpl;
import com.qeweb.framework.common.utils.ContainerUtil;

/**
 * 过滤收货单DAO impl
 */
public class ReceiveDownLoadFilterDaoImpl extends BaseDaoHibImpl implements IReceiveDownLoadFilterDao {

	private static final long serialVersionUID = 8622512189295500244L;

	@Override
	public BP_BuyerGoodsReceiveBO getTheSameRecBO(BP_BuyerGoodsReceiveBO receiveBO) {

		List<?> oldReceiveList = this.findBySql("from BP_BuyerGoodsReceiveBO where receiveNo = ? and buyerId = ? and vendorId = ? ",
				new Object[]{receiveBO.getReceiveNo(), receiveBO.getBuyerId(), receiveBO.getVendorId()});

		return ContainerUtil.isNotNull(oldReceiveList) ? (BP_BuyerGoodsReceiveBO)oldReceiveList.get(0) : null;
	}

}
