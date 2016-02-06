package com.qeweb.busplatform.pomanage.filter.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO;
import com.qeweb.busplatform.pomanage.dao.ia.IPODownLoadFilterDao;
import com.qeweb.busplatform.pomanage.filter.IPODownLoadFileter;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 采购订单下载过滤器
 */
public class PODownLoadFilterImpl implements IPODownLoadFileter {

	private IPODownLoadFilterDao downLoadFilterDao;

	/**
	 * 过滤下载的采购订单.以下订单将被过滤:
	 * <li>明细为空的订单;
	 * <li>订单号为空的订单.
	 * @param boList
	 * @return
	 */
	@Override
	public Map<String, List<BP_PurchaseOrderBO>> filterPOs(List<BP_PurchaseOrderBO> boList) {
		if(ContainerUtil.isNull(boList))
			return null;

		Map<String, List<BP_PurchaseOrderBO>> result = new HashMap<String, List<BP_PurchaseOrderBO>>();
		List<BP_PurchaseOrderBO> insertResult = new LinkedList<BP_PurchaseOrderBO>();
		List<BP_PurchaseOrderBO> updateResult = new LinkedList<BP_PurchaseOrderBO>();

		//用于记录采购订单保存更新日志
		StringBuffer logBuf = new StringBuffer();
		for(BP_PurchaseOrderBO orderBO : boList) {
			if(ContainerUtil.isNull(orderBO.getPurchaseOrderItemBOs())) {
				logBuf.append("采购订单明细为空，忽略此条订单, 订单号：" + orderBO.getPurchaseNo()).append("\n");
				continue;
			}
			else if(StringUtils.isEmpty(orderBO.getPurchaseNo())) {
				logBuf.append("单明号为空，忽略此条订单,\n");
				continue;
			}

			BP_PurchaseOrderBO oldOrderBO = getDownLoadFilterDao().getTheSameOrder(orderBO);
			if(oldOrderBO == null) {
				insertResult.add(orderBO);
			}
			else {
				updateResult.add(orderBO);
			}
		}

		result.put(INSERT, insertResult);
		result.put(UPDATE, updateResult);

		return result;
	}

	public IPODownLoadFilterDao getDownLoadFilterDao() {
		return downLoadFilterDao;
	}

	public void setDownLoadFilterDao(IPODownLoadFilterDao downLoadFilterDao) {
		this.downLoadFilterDao = downLoadFilterDao;
	}

}
