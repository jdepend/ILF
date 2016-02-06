package com.qeweb.busplatform.goodsreceive.filter;

import java.util.List;
import java.util.Map;

import com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO;

/**
 * 收货单下载过滤器接口
 */
public interface IReceiveDownLoadFilter {

	final static public String UPDATE = "update";
	final static public String INSERT = "insert";

	/**
	 * 从boList中过滤收货单信息,将不符合条件的收货单剔除.
	 * @param boList
	 * @return 符合条件的收货单
	 */
	Map<String, List<BP_BuyerGoodsReceiveBO>> filterReceiveBOs(List<BP_BuyerGoodsReceiveBO> boList);
}
