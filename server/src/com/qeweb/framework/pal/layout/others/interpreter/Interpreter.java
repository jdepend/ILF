package com.qeweb.framework.pal.layout.others.interpreter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.MatcherUtil;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 
 * 布局管理器表达式解释器
 */
public class Interpreter {
	
	/*
	 * 布局管理器表达式
	 * 布局管理器表达式示例：
	 * 	1.  3;C2(bop1, bop2),C3(bop3, bop4);
	 * 	默认3列,bop1,bop2合并2列，bop3,bop4合并3列;
	 *  2.  4;R2(C2(bop1, bop2)),C3(bop3, bop4) 
	 *  默认4列,bop1,bop2合并2列2行，bop3,bop4合并3列;
	 *  3.  粗粒度组件布局管理器表达式类似： C2(boid1, boid2).
	 */
	private String layoutStr;
	private int columns;	//布局的列数
	/*
	 * key : vcSign, value : formCell
	 * vcSign:如果是细粒度组件，vcSign表示细粒度组件标签的bind属性;
     * 如果是粗粒度组件，vcSign表示粗粒度组件标签的id属性。
	 */
	private Map<String, Cell> cellMap = new HashMap<String, Cell>();
	
	private final String EXPRESSION_SPLIT = ";";
	private final char FC_SPLIT = ',';
	private final char LEFT_BRACKET = '(';
	private final char RIGHT_BRACKET = ')';
	
	/**
	 * @param layoutStr  	布局管理器表达式
	 * @param defColumns 	默认列数
	 */
	public Interpreter(String layoutStr, int defColumns) {
		this.layoutStr = StringUtils.removeAllSpace(layoutStr);
		this.columns = defColumns;
	}

	/**
	 * 解析布局管理器表达式.
	 */
	public void interpret() {
		if(StringUtils.isEmpty(getLayoutStr())) 
			return;
		
		String[] layoutSplit = StringUtils.split(getLayoutStr(), EXPRESSION_SPLIT);
		if(StringUtils.isEmpty(layoutSplit))
			return;
		
		interpertColumns(layoutSplit[0]);
		for(String str : layoutSplit) {
			if(StringUtils.isEmpty(str) || MatcherUtil.isNumber(str))
				continue;
			
			interpertFormCell(str);
		}
	}
	
	/**
	 * 解析默认列数
	 * @param str	布局管理器划分的总列数
	 */
	private void interpertColumns(String str) {
		Integer columns = StringUtils.convertToInteger(str);
		if(columns != null && columns > 0)
			setColumns(columns);
	}
	
	/**
	 * 构造每个bop的布局样式
	 * R2(C2(bop1, bop2)) 或  R2(bop3, C2(bop1, bop2))
	 * <li>1. 遍历表达式, 解析子元素;
	 * <li>2. 遇到左括号,将R2, C2 表达式压入表达式栈顶;
	 * <li>3. 遇到逗号, 如果栈不为空, 将栈中的所有表达式代表的含义赋予bop, 将bop压入cellMap; 如果栈为空, 直接略过;
	 * <li>4. 遇到右括号, 将栈中的所有表达式代表的含义赋予bop, 将bop压入cellMap, 从表达式栈顶弹出一项.
	 * @param str
	 */
	private void interpertFormCell(String str) {
		//表达式栈, 存储 R1, C1, R2, C2等合并单元格的表达式
		Stack<String> stack = new Stack<String>();
		
		//表达式的元素
		String atom = "";
		//遍历表达式, 解析子元素
		for(int i = 0, length = str.length(); i < length; i++) {
			char ch = str.charAt(i);
			//遇到左括号,将R2, C2 表达式压入表达式栈顶
			if(LEFT_BRACKET == ch) {
				stack.push(atom);
				atom = "";
			}
			//遇到逗号, 如果栈不为空, 将栈中的所有表达式代表的含义赋予bop, 将bop压入cellMap; 
			//如果栈为空, 直接略过;
			else if(FC_SPLIT == ch) {
				if(ContainerUtil.isNotNull(stack))
					formatCell(atom, stack);
				atom = "";
			}
			//遇到右括号, 将栈中的所有表达式代表的含义赋予bop, 将bop压入cellMap, 从表达式栈顶弹出一项
			else if(RIGHT_BRACKET == ch) {
				if(StringUtils.isNotEmpty(atom))
					formatCell(atom, stack);
				stack.pop();
				atom = "";
			}
			else {
				atom += ch;
			}
		}
	}
	
	/**
	 * 装饰formCell, 将bop压入cellMap
	 * @param vcSign
	 * @param stack 表达式栈
	 */
	private void formatCell(String vcSign, Stack<String> stack) {
		Cell cell = getCell(vcSign);
		
		Iterator<String> itr = stack.iterator();
		while(itr.hasNext()) {
			String expression = itr.next();
			if(isCELLSPAN(expression)) 
				cell.setCollSpan(getCallSpan(expression));
			else if(isROWSPAN(expression)) 
				cell.setRowSpan(getRowSpan(expression));
		}		
		
		cellMap.put(vcSign, cell);
	}
	
	private int getCallSpan(String expression) {
		int collSpan = Integer.parseInt(expression.toUpperCase().replace("C", ""));
		return collSpan > getColumns() ? getColumns() : collSpan;
	}
	
	private int getRowSpan(String expression) {
		return Integer.parseInt(expression.toUpperCase().replace("R", ""));
	}

	private Cell getCell(String atom) {
		Cell cell = cellMap.get(atom);
		return cell == null ? new Cell() : cell;
	}
	
	private boolean isCELLSPAN(String str) {
		return MatcherUtil.isAllMatch(str, "^[C|c]-?\\d+$");
	}
	
	private boolean isROWSPAN(String str) {
		return MatcherUtil.isAllMatch(str, "^[R|r]-?\\d+$");
	}
	
	public int getColumns() {
		return this.columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public String getLayoutStr() {
		return layoutStr;
	}

	public void setLayoutStr(String layoutStr) {
		this.layoutStr = layoutStr;
	}

	public Map<String, Cell> getCellMap() {
		return cellMap;
	}

	public void setCellMap(Map<String, Cell> cellMap) {
		this.cellMap = cellMap;
	}
}
