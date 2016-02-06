package com.qeweb.demo.mobile.action;

import com.qeweb.demo.mobile.common.LoginShopMsg;
import com.qeweb.framework.base.ac.BaseAction;

/**
 * 进店校验AC，进店前先判断是否已经扫描过条码， 
 * 如果通过校验则直接进入业务菜单，否则需要进行条码扫描
 */
public class DemoComeInShopAC extends BaseAction {

	private static final long serialVersionUID = 657983042232615207L;

	@Override
	public String execute() {
		//如果巡检人员已经进店， 跳转到业务菜单页面
		if(LoginShopMsg.isComeIn())
			return SUCCESS;
			
		//跳转到进店条码扫描页面
		return "barcodeScan";
	}
}
