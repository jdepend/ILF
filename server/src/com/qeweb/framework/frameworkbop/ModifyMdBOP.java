package com.qeweb.framework.frameworkbop;

import com.qeweb.framework.bc.sysbop.OperateBOP;

/**
 * Modify模式BOP.
 * 仅将表格中修改的数据发送到后台, 仅当表格设置了可修改单元格时modify模式才有实际作用
 */
public class ModifyMdBOP extends OperateBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = -181253334575173727L;

	public ModifyMdBOP(String operate) {
		super(operate);
	}
	
	public ModifyMdBOP() {
		super();
	}
	
	@Override
	public String getSaveMod() {
		return SAVEMOD_MODIFY;
	}
}
