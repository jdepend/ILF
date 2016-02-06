package com.qeweb.demo.persistence.btnmod.bo;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.qeweb.demo.common.bo.DemoPurchaseOrderBO;
import com.qeweb.demo.persistence.general.bop.DemoConfirmStatusBOP;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.MsgService;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.PropertyUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.AdaptiveMdBOP;
import com.qeweb.framework.frameworkbop.AllMdBOP;
import com.qeweb.framework.frameworkbop.EmptyMdBOP;
import com.qeweb.framework.frameworkbop.ModifyMdBOP;
import com.qeweb.framework.frameworkbop.SelectMdBOP;

/**
 * demo: 按钮模式示例1 表格级按钮的模式.
 * 路径: 持久化-按钮的模式
 */
public class DemoBtnModeBO_1 extends DemoPurchaseOrderBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8264695349737075263L;
	
	private int orders;			//订购数量

	public DemoBtnModeBO_1() {
		super();
		addOperateBOP("saveBtn_1", new SelectMdBOP());
		addOperateBOP("saveBtn_2", new ModifyMdBOP());
		addOperateBOP("saveBtn_3", new AllMdBOP());
		addOperateBOP("saveBtn_4", new EmptyMdBOP());
		addOperateBOP("saveBtn_5", new AdaptiveMdBOP());
		
		OperateBOP mixedBop = new SelectMdBOP();
		mixedBop.addOptMod("saveAll", OperateBOP.SAVEMOD_ALL);
		addOperateBOP("saveBtn_6", mixedBop);
		
		OperateBOP mixedBop2 = new SelectMdBOP();
		mixedBop2.addOptMod("saveAllRecs", OperateBOP.SAVEMOD_ALL);
		addOperateBOP("saveBtn_7", mixedBop2);
		
		addBOP("publishStatus", new DemoConfirmStatusBOP());
		addBOP("confirmStatus", new DemoConfirmStatusBOP());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initPreferencePage(Page page) {
		List<DemoBtnModeBO_1> boList = new LinkedList<DemoBtnModeBO_1>();
		Map<String, DemoBtnModeBO_1> map = (Map<String, DemoBtnModeBO_1>)MsgService.getMsg("DemoBtnModeBO_1");
		
		try {
			if(ContainerUtil.isNotNull(page.getItems())) {
				for (int i = 0; i < page.getItems().size(); i++) {
					DemoPurchaseOrderBO po = (DemoPurchaseOrderBO) page.getItems().get(i);
					DemoBtnModeBO_1 bo = new DemoBtnModeBO_1();
					PropertyUtils.copyProperties(bo, po);
					
					if(ContainerUtil.isNotNull(map) && map.containsKey(bo.getId() + "")) {
						bo = map.get(bo.getId() + "");
					}
					
					BOHelper.initPreferencePage_lazy(bo, this);
					boList.add(bo);
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		MsgService.removeMsg("DemoBtnModeBO_1");
		page.setBOList(boList);
	}
	

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getSearchClass() {
		return DemoPurchaseOrderBO.class;
	}
	
	/**
	 * 当saveMod=空时, 仅对table有效,将把页面中的所有数据转递至后台.
	 * @param boList
	 */
	public void saveDef(List<BusinessObject> boList) throws BOException {
		System.out.println("----------------------------------------------------------saveDef------------------------------------------------------");
		System.out.println("|\t\t\t\t\t\tboList.size = " + boList.size() + "\t\t\t\t\t\t\t\t\t|");
		System.out.println("|\tid\t\t\tpoNO\t\t\torgCode\t\t\tpublishStatus\t\tpurchaseTime\t\t|");
		
		for (BusinessObject bo : boList) {
			DemoPurchaseOrderBO demoBO = (DemoPurchaseOrderBO) bo;
			System.out.println("|\t" + bo.getId() + "\t\t\t"
					+ demoBO.getPurchaseNo() + "\t\t\t"
					+ demoBO.getVendor().getOrgCode() + "\t\t\t"
					+ demoBO.getPublishStatus() + "\t\t\t"
					+ demoBO.getPurchaseTime() + "\t|");
		}
		
		System.out.println("----------------------------------------------------------------end----------------------------------------------------");
	}
	
	/**
	 * 当saveMod=select时, 仅将选中的数据传输至后台
	 * @param boList
	 */
	public void saveSelect(List<BusinessObject> boList) throws BOException {
		System.out.println("----------------------------------------------------------saveSelect---------------------------------------------------");
		System.out.println("|\t\t\t\t\t\tboList.size = " + boList.size() + "\t\t\t\t\t\t\t\t\t|");
		System.out.println("|\tid\t\t\tpoNO\t\t\torgCode\t\t\tpublishStatus\t\tpurchaseTime\t\t|");
		
		for (BusinessObject bo : boList) {
			DemoPurchaseOrderBO demoBO = (DemoPurchaseOrderBO) bo;
			System.out.println("|\t" + bo.getId() + "\t\t\t"
					+ demoBO.getPurchaseNo() + "\t\t\t"
					+ demoBO.getVendor().getOrgCode() + "\t\t\t"
					+ demoBO.getPublishStatus() + "\t\t\t"
					+ demoBO.getPurchaseTime() + "\t|");
		}
		
		System.out.println("----------------------------------------------------------------end------------------------------------------------------");
	}
	
	/**
	 * 仅将表格中修改的数据发送到后台, 仅当表格设置了可修改单元格时modify模式才有实际作用
	 * @param boList 被修改的数据
	 */
	public void saveModify(List<BusinessObject> boList) throws BOException {
		System.out.println("----------------------------------------------------------saveModify-----------------------------------------------------");
		System.out.println("|\t\t\t\t\t\tboList.size = " + boList.size() + "\t\t\t\t\t\t\t\t\t|");
		System.out.println("|\tid\t\t\tpoNO\t\t\torgCode\t\t\tpublishStatus\t\tpurchaseTime\t\t|");
		
		for (BusinessObject bo : boList) {
			DemoPurchaseOrderBO demoBO = (DemoPurchaseOrderBO) bo;
			System.out.println("|\t" + bo.getId() + "\t\t\t"
					+ demoBO.getPurchaseNo() + "\t\t\t"
					+ demoBO.getVendor().getOrgCode() + "\t\t\t"
					+ demoBO.getPublishStatus() + "\t\t\t"
					+ demoBO.getPurchaseTime() + "\t|");
		}
		
		System.out.println("----------------------------------------------------------------end------------------------------------------------------");
	}
	
	/**
	 * 当saveMod=all或空时, 将把表格中的所有数据转递至后台.
	 * @param boList
	 */
	public void saveAll(List<BusinessObject> boList) throws BOException {
		System.out.println("----------------------------------------------------------saveAll--------------------------------------------------------");
		System.out.println("|\t\t\t\t\t\tboList.size = " + boList.size() + "\t\t\t\t\t\t\t\t\t|");
		System.out.println("|\tid\t\t\tpoNO\t\t\torgCode\t\t\tpublishStatus\t\tpurchaseTime\t\t|");
		
		for (BusinessObject bo : boList) {
			DemoPurchaseOrderBO demoBO = (DemoPurchaseOrderBO) bo;
			System.out.println("|\t" + bo.getId() + "\t\t\t"
					+ demoBO.getPurchaseNo() + "\t\t\t"
					+ demoBO.getVendor().getOrgCode() + "\t\t\t"
					+ demoBO.getPublishStatus() + "\t\t\t"
					+ demoBO.getPurchaseTime() + "\t|");
		}
		
		System.out.println("----------------------------------------------------------------end-----------------------------------------------------");
	}
	
	/**
	 * 当saveMod为empty时, 点击table级按钮不提示任何信息,直接将请求发送到后台，如果需要提示，则需要显示地设置isConfirm属性；
	 * @param boList
	 */
	public void saveEmpty(List<BusinessObject> boList) throws BOException {
		System.out.println("----------------------------------------------------------saveEmpty-----------------------------------------------------");
		System.out.println("|\t\t\t\t\t\tboList.size = " + boList.size() + "\t\t\t\t\t\t\t\t\t|");
		System.out.println("|\tid\t\t\tpoNO\t\t\torgCode\t\t\tpublishStatus\t\tpurchaseTime\t\t|");
		
		for (BusinessObject bo : boList) {
			DemoPurchaseOrderBO demoBO = (DemoPurchaseOrderBO) bo;
			System.out.println("|\t" + bo.getId() + "\t\t\t"
					+ demoBO.getPurchaseNo() + "\t\t\t"
					+ demoBO.getVendor().getOrgCode() + "\t\t\t"
					+ demoBO.getPublishStatus() + "\t\t\t"
					+ demoBO.getPurchaseTime() + "\t|");
		}
		
		System.out.println("----------------------------------------------------------------end-----------------------------------------------------");
	}
	
	/**
	 * 当saveMod=adaptive时, 无论是否未选中任何数据, 请求都将发送到后台, 未选中时不会传输表格中的任何数据, 选中时会选中数据传输至后台.
	 * @param boList
	 */
	public void saveAdaptive(List<BusinessObject> boList) throws BOException {
		System.out.println("----------------------------------------------------------saveAdaptive--------------------------------------------------");
		System.out.println("|\t\t\t\t\t\tboList.size = " + boList.size() + "\t\t\t\t\t\t\t\t\t|");
		System.out.println("|\tid\t\t\tpoNO\t\t\torgCode\t\t\tpublishStatus\t\tpurchaseTime\t\t|");
		
		for (BusinessObject bo : boList) {
			DemoPurchaseOrderBO demoBO = (DemoPurchaseOrderBO) bo;
			System.out.println("|\t" + bo.getId() + "\t\t\t"
					+ demoBO.getPurchaseNo() + "\t\t\t"
					+ demoBO.getVendor().getOrgCode() + "\t\t\t"
					+ demoBO.getPublishStatus() + "\t\t\t"
					+ demoBO.getPurchaseTime() + "\t|");
		}
		
		System.out.println("----------------------------------------------------------------end-----------------------------------------------------");
	}

	/**
	 * 保存所有记录
	 * @param boList
	 */
	public void saveAllRecs(List<DemoBtnModeBO_1> boList) {
		if(ContainerUtil.isNull(boList))
			return;
		
		Map<String, DemoBtnModeBO_1> map = new HashMap<String, DemoBtnModeBO_1>();
		for(DemoBtnModeBO_1 bo : boList) {
			map.put(bo.getId() + "", bo);
		}
		
		MsgService.setMsg("DemoBtnModeBO_1", map);
	}

	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}
}
