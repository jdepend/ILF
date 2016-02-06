package com.qeweb.framework.bc.prop;

import java.util.List;

/**
 * 下拉树的range, 当下拉框拥有TreeRange时, 将展示成一个下拉树.
 * <p>
 * 	TreeRange 属于枚举型范围, 每个枚举值是一个名为Node的内部类, 
 *  包括value(节点的value), text(节点的展示值, 可以是国际化标识), parentValue(父节点的值), sortIdx(节点排序标识).
 * </p>
 * <p>
 *  	<ul>子类可应覆写:
 *   		<li>create方法创建下拉树;
 *   		<li>getRootValue确定根节点的值;
 *   		<li>getRootText确定根节点的国际化标识
 *    	</ul>
 * </p>
 */
public class TreeRange extends EnumRange {
	
	private static final long serialVersionUID = -8194374761821402779L;

	/**
	 * Node 用于存储下拉树的节点信息
	 */
	public class Node {
		private String value;		//节点的value
		private String text;		//节点的展示值, 可以是国际化标识
		private String parentValue;	//父节点的值
		private int sortIdx;		//节点排序标识
		
		/**
		 * Node 用于存储下拉树的节点信息
		 * @param value			节点的value
		 * @param text			节点的展示值, 可以是国际化标识
		 * @param parentValue 	父节点的值
		 * @param sortIdx		节点排序标识
		 */
		public Node(String value, String text, String parentValue, int sortIdx) {
			this.value = value;
			this.text = text;
			this.parentValue = parentValue;
			this.sortIdx = sortIdx;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getParentValue() {
			return parentValue;
		}

		public void setParentValue(String parentValue) {
			this.parentValue = parentValue;
		}

		public int getSortIdx() {
			return sortIdx;
		}

		public void setSortIdx(int sortIdx) {
			this.sortIdx = sortIdx;
		}
	}
	
	/**
	 * 创建下拉树, 子类应覆写该方法.
	 * <p>create方法仅需要把下拉树的所有节点形成List, 无需关注更多细节
	 * @return
	 */
	public List<Node> create() {
		return null; 
	}
	
	/**
	 * 根节点的值
	 * @return
	 */
	public String getRootValue() {
		return "-1";
	}
	
	/**
	 * 根节点的国际化标识
	 * @return
	 */
	public String getRootText() {
		return "root";
	}
}
