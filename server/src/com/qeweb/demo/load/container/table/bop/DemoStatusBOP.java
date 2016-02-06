package com.qeweb.demo.load.container.table.bop;

import com.qeweb.framework.bc.sysbop.StatusBOP;

/**
 * 状态图标BOP, 通过数据库表[qeweb_dict]设置
 */
public class DemoStatusBOP extends StatusBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8963437144593362475L;

	@Override
	public String getKey() {
		//confirmstatus对应qeweb_dict.dict_key的值
		return "confirmstatus";
	}
}
