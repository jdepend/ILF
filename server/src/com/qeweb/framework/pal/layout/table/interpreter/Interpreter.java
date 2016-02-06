package com.qeweb.framework.pal.layout.table.interpreter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Stack;

import com.qeweb.framework.common.utils.StringUtils;

/**
 * 
 * 表格布局管理器表达式解释器
 */
public class Interpreter {
	
	/*
	 * 布局管理器表达式示例：
	 * 	1.  3;[title1](bop1, bop2),[title2](bop3, bop4);
	 * 	每行展示3个表格级按钮;bop1,bop2合并,合并后的title为title1; bop3,bop4合并,合并后的title为title2;
	 *  2.  4;[title2](bop4, [title1](bop1, bop2, bop3))
	 *  每行展示4个表格级按钮;bop1,bop2,bop3合并,合并后的title为title1; title1与bop4合并,合并后的title为title2
	 */
	private String layoutStr;
	//每行显示的按钮数
	private int btnColumns;
	//表头的结构, 用于合并table的表头
	private Set<Header> headerSet;
	
	private final String EXPRESSION_SPLIT = ";";
	private final char FC_SPLIT = ',';
	private final char LEFT_BRACKET = '(';
	private final char RIGHT_BRACKET = ')';
	private final char LEFG_SQUARE = '[';
	private final char RIGHT_SQUARE = ']';
	
	/**
	 * @param layoutStr  	布局管理器表达式
	 */
	public Interpreter(String layoutStr) {
		this.layoutStr = StringUtils.removeAllSpace(layoutStr);
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
		
		if(layoutSplit.length == 2) {
			interpertBtnColumns(layoutSplit[0]);
			interpertHeader(layoutSplit[1]);
		}
		else if(StringUtils.convertToInteger(layoutSplit[0]) == null) {
			interpertHeader(layoutSplit[0]);
		}
		else {
			interpertBtnColumns(layoutSplit[0]);
		}
	}
	
	/**
	 * 解析每行显示的按钮数
	 * @param str	布局管理器划分的总列数
	 */
	private void interpertBtnColumns(String str) {
		Integer btnColumns = Integer.valueOf(str);
		if(btnColumns != null && btnColumns > 0)
			setBtnColumns(btnColumns);
	}
	
	/**
	 *  解析表头布局.
	 * <p>例1: [title2](bop4, [title1](bop1, bop2, bop3)) 或  [title2]([title1](bop1, bop2, bop3), bop4)
	 * <p>例2: [title2](bop4, [title1](bop1, bop2, bop3)),[title3](bop5,bop6)
	 * <ul>
	 * 将title2, bop4等称为有义序列串; 将左/右中括号, 左右小括号, 逗号称为符号; 有意序序列串中不能包含符号.
	 * 其解析规则:
	 * <li>1. 遍历表达式, 解析子元素, 遇到非符号字符, 将非符号字符拼接到有义序列串;
	 * <li>2. 遇到[, 创建一个合并单元Header, 将Header入栈;
	 * <li>3. 遇到], 将title2设置到栈顶元素, 并清空有义序列串;
	 * <li>4. 遇到(, 直接跳过;
	 * <li>5. 遇到), 出栈; 
	 * 			<ol>a.如果栈为空,
	 * 				<ol>a1.如果有义序列串不为空, 将有义序列串添加到出栈元素的bopSet中, 再将出栈的Header添加到解释器的headerSet中, 并清空有义序列串;</
	 * 				<ol>a2.如果有义序列串为空, 将出栈的Header添加到解释器的headerSet中;</ol>
	 * 			</ol>
	 * 			<ol>b.如果此时有义序列串为空, 将出栈的Header添加到到栈顶元素的headerSet中;</ol> 
	 * 			<ol>c.如果此时有义序列串不为空, 将有义序列串添加到出栈元素的bopSet中,再将出栈元素添加栈顶元素的headerSet中, 并清空有义序列串;</ol>
	 * <li>6. 遇到逗号, 
	 * 			<ol>a.如果此时有义序列串为空, 直接跳过;</ol>
	 * 			<ol>b.如果此时有义序列串不为空, 将有义序列串添加到栈顶元素的bopSet中, 并清空有义序列串.</ol>
	 * </ul>
	 * @param str 不包含按钮列数的表头布局字符串
	 */
	private void interpertHeader(String str) {
		Stack<Header> stack = new Stack<Header>(); 
		//有义序列串
		String atom = "";
		//遍历表达式, 解析子元素
		for(int i = 0, length = str.length(); i < length; i++) {
			char ch = str.charAt(i);
			//遇到[
			if(LEFG_SQUARE == ch) {
				Header header = new Header();
				stack.push(header);
			}
			//遇到]
			else if(RIGHT_SQUARE == ch) {
				addTitleToTop(stack, atom);
				atom = "";
			}
			//遇到(
			else if(LEFT_BRACKET == ch) {
				;
			}
			//遇到)
			else if(RIGHT_BRACKET == ch) {
				Header header = stack.pop();
				if(stack.isEmpty()) {
					if(StringUtils.isNotEmpty(atom)) {
						header.addBopSet(atom);
						atom = "";
					}
					addHeader(header);
				}
				else if(StringUtils.isEmpty(atom)) {
					addHeaderToTop(stack, header);
				}
				else {
					header.addBopSet(atom);
					addHeaderToTop(stack, header);
					atom = "";
				}
			}
			//遇到逗号
			else if(FC_SPLIT == ch) {
				if(StringUtils.isNotEmpty(atom)) {
					addBopToTop(stack, atom);
					atom = "";
				}
			}
			//遇到非符号字符, 将非符号字符拼接到有义序列串;
			else {
				atom += ch;
			}
		}
	}

	/**
	 * 将header添加到栈顶的headerSet
	 * @param stack
	 * @param header
	 */
	private void addHeaderToTop(Stack<Header> stack, Header header) {
		Header top = stack.pop();
		top.addHeaderSet(header);
		stack.push(top);
	}

	/**
	 * 将title添加到栈顶的bopSet
	 * @param stack
	 * @param title
	 */
	private void addTitleToTop(Stack<Header> stack, String title) {
		Header header = stack.pop();
		header.setTitle(title);
		stack.push(header);
	}

	/**
	 * 将bop添加到栈顶的bopSet
	 * @param stack
	 * @param bop
	 */
	private void addBopToTop(Stack<Header> stack, String bop) {
		Header top = stack.pop();
		top.addBopSet(bop);
		stack.push(top);
	}

	public void addHeader(Header header) {
		if(headerSet == null)
			headerSet = new LinkedHashSet<Header>();
		
		headerSet.add(header);
	}
	
	public int getBtnColumns() {
		return btnColumns;
	}

	public void setBtnColumns(int btnColumns) {
		this.btnColumns = btnColumns;
	}

	public String getLayoutStr() {
		return layoutStr;
	}

	public void setLayoutStr(String layoutStr) {
		this.layoutStr = layoutStr;
	}

	public Set<Header> getHeaderSet() {
		return headerSet;
	}

	public void setHeaderSet(Set<Header> headerSet) {
		this.headerSet = headerSet;
	}
}
