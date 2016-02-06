package com.qeweb.framework.pal.layout.others;

import java.util.Map;

import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.layout.others.interpreter.Cell;
import com.qeweb.framework.pal.layout.others.interpreter.Interpreter;

/**
 * 布局管理器.
 * Layout是所有布局管理器的基类.
 * 可以通过为Layout设置不同的interpreter, 以达到解析不同布局字符串的目的.
 * 表单的布局管理器可直接使用Layout而不必实现具体的子类.
 */
public class Layout {
	
	/**
	 * 解析器
	 */
	private Interpreter interpreter;

	/**
	 * 执行解析操作
	 */
	void interpret() {
		getInterpreter().interpret();
	}
	
	/**
	 * 获取默认列数
	 * @return
	 */
	protected int getDefColCount() {
		return StringUtils.convertToInt(AppConfig.getPropValue((AppConfig.LAYOUT_COLUMN_NUMBER)));
	}
	
	/**
	 * 获取合并结果
	 * @return Map
	 * 	 key : vcSign, value : formCell
	 * 		vcSign:如果是细粒度组件，vcSign表示细粒度组件标签的bind属性;
     * 		如果是粗粒度组件，vcSign表示粗粒度组件标签的id属性。
	 */
	public Map<String, Cell> getCellMap() {
		return getInterpreter().getCellMap();
	}

	/**
	 * 获取布局字符串中设置的列数
	 * @return
	 */
	public int getColumns() {
		return getInterpreter().getColumns();
	}

	public Interpreter getInterpreter() {
		return interpreter;
	}

	public void setInterpreter(Interpreter interpreter) {
		this.interpreter = interpreter;
	}
	
}
