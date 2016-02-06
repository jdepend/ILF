package com.qeweb.busplatform.pomanage.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qeweb.busplatform.common.bop.DeliveryStatus;
import com.qeweb.busplatform.common.bop.ReceiveStatus;
import com.qeweb.busplatform.common.imp.excel.ImpExl;
import com.qeweb.busplatform.masterdata.bo.manage.BP_MaterialBO;
import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO;
import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO;
import com.qeweb.busplatform.pomanage.bop.ChangeStatus;
import com.qeweb.busplatform.pomanage.bop.CloseStatus;
import com.qeweb.busplatform.pomanage.bop.ConfirmStatus;
import com.qeweb.busplatform.pomanage.bop.PublishStatus;
import com.qeweb.busplatform.pomanage.filter.impl.PODownLoadFilterImpl;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.sysmanage.purview.bo.OrganizationBO;
import com.qeweb.sysmanage.purview.bo.UserBO;

/**
 * 导入采购订单BO
 */
public class BP_ImpPurchaseOrderBO extends ImpExl {

	private static final long serialVersionUID = 8037388637135766398L;

	@Override
	protected BP_PurchaseOrderBO getTargetBO() {
		BP_PurchaseOrderBO purchaseOrderBO = new BP_PurchaseOrderBO();
		purchaseOrderBO.setPoDownLoadFilter((PODownLoadFilterImpl) SpringConstant.getCTX().getBean("poDownLoadFilter"));

		return purchaseOrderBO;
	}

	@Override
	protected void saveData(List<BusinessObject> boList) throws Exception {
		List<BP_PurchaseOrderBO> purchaseOrderList = new ArrayList<BP_PurchaseOrderBO>();
		for(BusinessObject bo : boList) {
			if(bo instanceof BP_PurchaseOrderBO)
				purchaseOrderList.add((BP_PurchaseOrderBO) bo);
		}

		getTargetBO().saveOrUpdateOrders(purchaseOrderList);
	}

	@Override
	protected void impData(List<List<String>> sheetData, List<String> exlFormat, int sheetNO) throws BOException {
		//行号,列号
		int rowNO = 0, colNO = 0;
		List<BusinessObject> boList = new LinkedList<BusinessObject>();
		try {
			//key: 订单号+供应商 value: 每行订单数据
			Map<String, List<Map<String, String>>> orderListMap = new HashMap<String, List<Map<String,String>>>();
			while(rowNO < sheetData.size()) {
				List<String> rowData = sheetData.get(rowNO);

				Map<String, String> valueMap = new HashMap<String, String>();
				while(colNO < exlFormat.size()) {
					valueMap.put(exlFormat.get(colNO), rowData.get(colNO));
					colNO++;
				}
				//key
				String mapKey = valueMap.get("purchaseNo") + "" + valueMap.get("vendorCode");
				if(orderListMap.containsKey(mapKey)) {
					orderListMap.get(mapKey).add(valueMap);
				}
				else {
					List<Map<String, String>> rowsDataMap = new ArrayList<Map<String,String>>();
					rowsDataMap.add(valueMap);
					orderListMap.put(mapKey, rowsDataMap);
				}
				rowNO++;
				colNO = 0;
			}
			setValue(boList, orderListMap);

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

	private void setValue(List<BusinessObject> boList, Map<String, List<Map<String, String>>> orderListMap) throws Exception {
		if(ContainerUtil.isNull(orderListMap))
			return;

		BP_PurchaseOrderBO purchaseOrderBO = null;
		for(Map.Entry<String, List<Map<String, String>>> orderEntry : orderListMap.entrySet()) {
			purchaseOrderBO = getTargetBO();
			//采购订单明细
			Set<BP_PurchaseOrderItemBO> purchaseOrderItemBOs = new HashSet<BP_PurchaseOrderItemBO>();
			BP_PurchaseOrderItemBO orderItem = null;
			int count = 0;
			for(Map<String, String> itemMap : orderEntry.getValue()) {
				if(count == 0) {
					//采购订单号
					purchaseOrderBO.setPurchaseNo(itemMap.get("purchaseNo"));
					//订单时间
					purchaseOrderBO.setPurchaseTime(DateUtils.stringToTimestamp(itemMap.get("purchaseTime")));
					//采购员
					UserBO userBO = new UserBO();
					purchaseOrderBO.setBuyerUser(userBO.findUserBO(itemMap.get("buyerUserCode")));
					//供应商信息
					OrganizationBO org = new OrganizationBO();
					purchaseOrderBO.setVendor(org.findOrgBO(itemMap.get("vendorCode")));
					//采购商
					purchaseOrderBO.setBuyer(org.findOrgBO(itemMap.get("buyerCode")));
					//备注
					purchaseOrderBO.setRemark(itemMap.get("remark"));
					purchaseOrderBO.setPublishStatus(PublishStatus.NO);
					purchaseOrderBO.setConfirmStatus(ConfirmStatus.NO);
					purchaseOrderBO.setDeliveryStatus(DeliveryStatus.NO);
					purchaseOrderBO.setReceiveStatus(ReceiveStatus.NO);
					purchaseOrderBO.setChangeStatus(ChangeStatus.NO);
					purchaseOrderBO.setCloseStatus(CloseStatus.NO);
				}
				orderItem = new BP_PurchaseOrderItemBO();
				//行号
				orderItem.setItemNO(StringUtils.convertToInt(itemMap.get("purchaseOrderItemBO.itemNO")));
				//订购数量
				orderItem.setOrderQty(StringUtils.convertToDouble(itemMap.get("purchaseOrderItemBO.orderQty")));
				//物料
				BP_MaterialBO materialBO = new BP_MaterialBO();
				orderItem.setMaterial(materialBO.findMaterialBO(itemMap.get("purchaseOrderItemBO.materialCode")));
				//单位
				orderItem.setUnitName(itemMap.get("purchaseOrderItemBO.unitName"));
				//要货到货时间
				orderItem.setOrderTime(DateUtils.stringToTimestamp(itemMap.get("purchaseOrderItemBO.orderTime")));
				orderItem.setConfirmStatus(ConfirmStatus.NO);
				orderItem.setDeliveryStatus(DeliveryStatus.NO);
				orderItem.setCloseStatus(CloseStatus.NO);

				purchaseOrderItemBOs.add(orderItem);
				count ++;
			}
			purchaseOrderBO.setPurchaseOrderItemBOs(purchaseOrderItemBOs);
			boList.add(purchaseOrderBO);
		}

	}
}
