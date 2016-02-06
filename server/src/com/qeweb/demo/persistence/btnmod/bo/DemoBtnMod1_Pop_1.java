package com.qeweb.demo.persistence.btnmod.bo;

import java.util.List;
import java.util.Map;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.MsgService;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * demo: 按钮模式示例1 表格级按钮的模式的弹出框BO, 用于批量修改订购数量
 * 路径: 持久化-按钮的模式
 */
public class DemoBtnMod1_Pop_1 extends BusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7195128392202736656L;

	private int selectOrders;	//选中的订单数
	private int orders;			//订购数量
	private String poIds;		//选中的订单的ID,用,分隔

	/**
	 * 接收选中的订单, 根据订单数量设置selectOrders
	 * @param boList
	 * @return
	 */
	public BusinessObject acceptSelectOrders(List<BusinessObject> boList) {
		if(ContainerUtil.isNull(boList))
			return this;
		
		selectOrders =  boList.size();
		poIds = "";
		for (BusinessObject bo : boList) {
			poIds += bo.getId() + ","; 
		}
		poIds = StringUtils.removeEnd(poIds);
		BOHelper.initPreferencePage(this);
		
		return this;
	}
	
	/**
	 * 设置订购数量
	 * @param bo
	 */
	@SuppressWarnings("unchecked")
	public void saveOrders(DemoBtnMod1_Pop_1 bo) {
		System.out.println("----------------------------------------------------------saveOrders------------------------------------------------------");
		
		Map<String, DemoBtnModeBO_1> map = (Map<String, DemoBtnModeBO_1>)MsgService.getMsg("DemoBtnModeBO_1");
		if(ContainerUtil.isNull(map))
			return;
		
		String[] poIdArr = StringUtils.split(bo.getPoIds(), ",");
		for(String poId : poIdArr) {
			DemoBtnModeBO_1 po = map.get(poId);
			if(po != null) {
				po.setOrders(bo.getOrders());
			}
		}
		MsgService.setMsg("DemoBtnModeBO_1", map);
		System.out.println("----------------------------------------------------------end-------------------------------------------------------------");
	}
	
	public int getSelectOrders() {
		return selectOrders;
	}

	public void setSelectOrders(int selectOrders) {
		this.selectOrders = selectOrders;
	}

	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}

	public String getPoIds() {
		return poIds;
	}

	public void setPoIds(String poIds) {
		this.poIds = poIds;
	}
}
