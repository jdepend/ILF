package com.qeweb.busplatform.goodsreceive.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qeweb.busplatform.common.imp.excel.ImpExl;
import com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO;
import com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryItemBO;
import com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO;
import com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveItemBO;
import com.qeweb.busplatform.goodsreceive.filter.impl.ReceiveDownLoadFilterImpl;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.sysmanage.purview.bo.OrganizationBO;

/**
 * 导入收货单
 */
public class BP_ImpGoodsReceiveOrderBO extends ImpExl {

	private static final long serialVersionUID = -5253237633660252538L;

	@Override
	protected BP_BuyerGoodsReceiveBO getTargetBO() {
		BP_BuyerGoodsReceiveBO receiveBO = new BP_BuyerGoodsReceiveBO();
		receiveBO.setReceiveDownLoadFilter(new ReceiveDownLoadFilterImpl());

		return receiveBO;
	}

	@Override
	protected void saveData(List<BusinessObject> boList) throws Exception {
		List<BP_BuyerGoodsReceiveBO> goodsReceiveList = new LinkedList<BP_BuyerGoodsReceiveBO>();
		for(BusinessObject bo : boList) {
			if(bo instanceof BP_BuyerGoodsReceiveBO)
				goodsReceiveList.add((BP_BuyerGoodsReceiveBO) bo);
		}

		getTargetBO().saveReceiveBOs(goodsReceiveList);
	}

	@Override
	protected void impData(List<List<String>> sheetData, List<String> exlFormat, int sheetNO) throws BOException {
		//行号,列号
		int rowNO = 0, colNO = 0;
		List<BusinessObject> boList = new LinkedList<BusinessObject>();
		try {
			//根据收货单号+发货单号 分组
			Map<String, List<Map<String, String>>> receiveListMap = new HashMap<String, List<Map<String,String>>>();
			while(rowNO < sheetData.size()) {
				List<String> rowData = sheetData.get(rowNO);

				Map<String, String> valueMap = new HashMap<String, String>();
				while(colNO < exlFormat.size()) {
					valueMap.put(exlFormat.get(colNO), rowData.get(colNO));
					colNO++;
				}
				//key
				String mapKey = valueMap.get("receiveNo") + "" + valueMap.get("vendorGoodsDeliveryBO.deliveryNo");
				if(receiveListMap.containsKey(mapKey)) {
					receiveListMap.get(mapKey).add(valueMap);
				}
				else {
					List<Map<String, String>> rowsDataMap = new ArrayList<Map<String,String>>();
					rowsDataMap.add(valueMap);
					receiveListMap.put(mapKey, rowsDataMap);
				}
				rowNO++;
				colNO = 0;
			}
			setValue(boList, receiveListMap);

			saveData(boList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BOException("第" + rowNO + "行 第" + colNO + "列数据类型转换错误！");
		} finally {
			for(BusinessObject bo : boList) {
				bo.free();
			}
			boList.clear();
		}
	}

	private void setValue(List<BusinessObject> boList, Map<String, List<Map<String, String>>> receiveListMap) throws Exception {
		if(ContainerUtil.isNull(receiveListMap))
			return;

		BP_BuyerGoodsReceiveBO goodsReceiveBO = null;
		for(Map.Entry<String, List<Map<String, String>>> receiveEntry : receiveListMap.entrySet()) {
			goodsReceiveBO = getTargetBO();

			Set<BP_BuyerGoodsReceiveItemBO> goodsReceiveItemBOs = new HashSet<BP_BuyerGoodsReceiveItemBO>();
			BP_BuyerGoodsReceiveItemBO receiveItemBO = null;
			int count = 0;

			BP_VendorGoodsDeliveryBO deliveryBO = null;
			//发货单明细信息
			Set<BP_VendorGoodsDeliveryItemBO> deliveryItemSet = null;
			for(Map<String, String> itemMap : receiveEntry.getValue()) {
				if(count == 0) {
					//收货单号
					goodsReceiveBO.setReceiveNo(itemMap.get("receiveNo"));
					//供应商信息
					OrganizationBO org = new OrganizationBO();
					goodsReceiveBO.setVendorId(org.findOrgBO(itemMap.get("vendorCode")).getId());
					//采购商信息
					goodsReceiveBO.setBuyerId(org.findOrgBO(itemMap.get("buyerCode")).getId());
					//发货单信息
					deliveryBO = new BP_VendorGoodsDeliveryBO().getDelivery(itemMap.get("vendorGoodsDeliveryBO.deliveryNo"));
					//采购
					goodsReceiveBO.setVendorGoodsDeliveryBO(deliveryBO);
					goodsReceiveBO.setReceiveTime(DateUtils.getCurrentTimestamp());
					//发货单明细信息
					deliveryItemSet = deliveryBO.getVendorGoodsDeliveryItemBOs();
				}
				receiveItemBO = new BP_BuyerGoodsReceiveItemBO();
				receiveItemBO.setItemNo(StringUtils.convertToInteger(itemMap.get("vendorGoodsDeliveryItemBO.itemNo")));
				receiveItemBO.setReceiveQty(StringUtils.convertToDouble(itemMap.get("buyerGoodsReceiveItemBO.receiveQty")));
				receiveItemBO.setGoodsRejectQty(StringUtils.convertToDouble(itemMap.get("buyerGoodsReceiveItemBO.goodsRejectQty")));

				for(BP_VendorGoodsDeliveryItemBO deliveryItem : deliveryItemSet) {
					if(deliveryItem.getItemNo().equals(StringUtils.convertToInteger(itemMap.get("buyerGoodsReceiveItemBO.itemNo")))) {
						receiveItemBO.setPurchaseOrderItemBO(deliveryItem.getPurchaseOrderItemBO());
						receiveItemBO.setPurchaseGoodsPlanBO(deliveryItem.getPurchaseGoodsPlan());
						receiveItemBO.setVendorGoodsDeliveryItemBO(deliveryItem);
						receiveItemBO.setBuyerGoodsReceiveBO(goodsReceiveBO);
					}
				}
				goodsReceiveItemBOs.add(receiveItemBO);
			}
			goodsReceiveBO.setBuyerGoodsReceiveItemBOs(goodsReceiveItemBOs);
			boList.add(goodsReceiveBO);
		}

	}
}
