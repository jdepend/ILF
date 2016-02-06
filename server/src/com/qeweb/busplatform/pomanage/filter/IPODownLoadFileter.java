package com.qeweb.busplatform.pomanage.filter;

import java.util.List;
import java.util.Map;

import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO;

/**
 * 采购订单下载过滤器
 */
public interface IPODownLoadFileter {

	final static public String UPDATE = "update";
	final static public String INSERT = "insert";

	/**
	 * 过滤下载的采购订单
	 * @param boList
	 * @return key : update/insert  过滤后的订单信息, 包括新增订单及待更新的订单
	 */
	Map<String, List<BP_PurchaseOrderBO>> filterPOs(List<BP_PurchaseOrderBO> boList);
}
