package com.qeweb.framework.pal.coarsegrained;

import java.util.LinkedList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.mdt.MDTContext;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.Var_DynamicTab;
import com.qeweb.framework.pal.ViewComponent;
import com.qeweb.framework.pal.control.CommandButton;

/**
 * * Tab标签，做为粗粒度组件的容器，可以不绑定任何BO。
 * <p>
 * Tab的bind属性是非必填属性，仅当Tab中包含控制组件时可能需要添加bind属性.
 * <br>
 * Tab自身也可以包含控制组件，这通常在所有sheet需要批量提交时使用：
 * <LI>如果tab绑定了bo，则控制组件将执行该bo的对应方法;
 * <LI>如果tab没绑定bo，则控制组件将依次执行所有sheet页中粗粒度组件的对应方法，如果找不到对应方法则不做任何操作.
 * </p>
 * Tab中包含多个Sheet页，每个sheet中包含一个或多个粗粒度组件;或多个细粒度组件.

 * <br>
 * 例：
 * <qeweb:tab id='myTab' bind='bo'>
 * 	 <qeweb:sheet text='sheet1'>
 * 		<qeweb:form id='f1' bind='bo1'>...</qeweb:form>
 *   </qeweb:sheet>
 *
 *   <qeweb:sheet text='sheet2'>
 *   	<qeweb:textField bind='bop1'/>
 *   	<qeweb:textField bind='bop2'/>
 *   </qeweb:sheet>
 *
 *   <qeweb:sheet text='sheet3'>
 *   	<qeweb:textField bind='bop3'/>
 *   	<qeweb:textField bind='bop4'/>
 *   </qeweb:sheet>
 *
 *   <qeweb:commandButton name='btn1' operate='insert'/>
 * </qeweb:tab>
 */
public abstract class Tab extends Container {

	
	private List<Sheet> sheetList = new LinkedList<Sheet>();
	
	@Override
	public void init() {
		List<Container> containerList = getContainerList();
		if(ContainerUtil.isNotNull(containerList)) {
			for (Container container : containerList) {
				container.init();
			}
		}
	}
	
	@Override
	public void loadData(Object data) {
		if(data == null) {
			;
		}
		else if(data instanceof Page) {
			Page page = (Page) data;
			if(page.getTotalCount() > 0)
				initBOP((BusinessObject) page.getBOList().get(0));
		}
		else {
			initBOP((BusinessObject) data);
		}
	}
	
	@Override
	public void loadMDT() {
		Var_DynamicTab dynamicTab = new Var_DynamicTab(Envir.getRequestURI(), this);
		
		//添加动态sheet
		List<Sheet> dynamicSheetList = dynamicTab.getRelateContainer(MDTContext.getVarMap());
		if(ContainerUtil.isNotNull(dynamicSheetList))
			this.sheetList.addAll(dynamicSheetList);
	}

	/**
	 * 添加sheet页
	 */
	public void addSheet(Sheet sheet) {
		sheetList.add(sheet);
	}

	/**
	 * 添加sheet页
	 * @param id	sheetId
	 * @param text	sheetText
	 * @param vcList
	 */
	public void addSheet(String id, String text, List<ViewComponent> vcList) {
		addSheet(new Sheet(id, text, vcList, this));
	}

	public List<Sheet> getSheetList() {
		return sheetList;
	}

	public void setSheetList(List<Sheet> sheetList) {
		this.sheetList = sheetList;
	}

	public List<ViewComponent> getVCList() {
		List<ViewComponent> vcList = new LinkedList<ViewComponent>();
		for(Sheet sheet : getSheetList()) {
			vcList.addAll(sheet.getVcList());
		}

		return vcList;
	}

	public List<Container> getContainerList() {
		List<Container> containerList = new LinkedList<Container>();
		for(ViewComponent vc : getVCList()) {
			if(vc instanceof Container)
				containerList.add((Container) vc);
		}

		return containerList;
	}

	/**
	 * 获取tab直属的逻辑控制组件(即直接包含在<qeweb:tab>下的按钮).
	 * <br>
	 * 例:<br>
	 *  <qeweb:tab id='myTab' bind='bo'>
	 *   <qeweb:sheet text='sheet3'>
	 *   	 <qeweb:commandButton name='btn1' operate='insert'/>
	 *   </qeweb:sheet>
	 *
	 *   <qeweb:commandButton name='btn2' operate='insert'/>
	 * 	</qeweb:tab>
	 * getButtonList仅获得btn2
	 *
	 * @return Map<按钮operate, commandButton>
	 */
	@Override
	public Map<String, CommandButton> getButtonList() {
		Map<String, CommandButton> btnList = super.getButtonList();
		Map<String, CommandButton> result = new LinkedHashMap<String, CommandButton>();

		for(Entry<String, CommandButton> entry : btnList.entrySet()) {
			for(ViewComponent vc : getVCList()) {
				if(vc instanceof CommandButton && !StringUtils.isEqual(entry.getValue().getId(), vc.getId()))
					result.put(entry.getKey(), entry.getValue());
			}
		}

		return result;
	}
	
	@Override
	public void free() {
		if(getSheetList() != null)
			getSheetList().clear();

		super.free();
	}
}
