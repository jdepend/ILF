package com.qeweb.demo.interaction.tipmessage;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Page;

public class DemoTipMsgPopBO extends BusinessObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2029091486654204696L;
	
	private String bop1;
	private String bop2;
	private String bop3;
	
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		Page page = new Page();
		List<DemoTipMsgPopBO> boList = new LinkedList<DemoTipMsgPopBO>();
		DemoTipMsgPopBO bo1 = new DemoTipMsgPopBO();
		DemoTipMsgPopBO bo2 = new DemoTipMsgPopBO();
		DemoTipMsgPopBO bo3 = new DemoTipMsgPopBO();
		bo1.setBop1("bop1-1");bo1.setBop2("bop2-1");bo1.setBop3("bop3-1");bo1.setId(1);
		bo2.setBop1("bop1-2");bo2.setBop2("bop2-2");bo2.setBop3("bop3-2");bo2.setId(2);
		bo3.setBop1("bop1-3");bo3.setBop2("bop2-3");bo3.setBop3("bop3-3");bo3.setId(3);
		boList.add(bo1);
		boList.add(bo2);
		boList.add(bo3);
		page.setItems(boList);
		page.setTotalCount(3);
		initPreferencePage(page);
		
		return page;
	}
	
	public String getBop1() {
		return bop1;
	}
	public void setBop1(String bop1) {
		this.bop1 = bop1;
	}
	public String getBop2() {
		return bop2;
	}
	public void setBop2(String bop2) {
		this.bop2 = bop2;
	}
	public String getBop3() {
		return bop3;
	}
	public void setBop3(String bop3) {
		this.bop3 = bop3;
	}
}
