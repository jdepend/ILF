package com.qeweb.framework.pal.finegrained.enumcomp;

import java.util.Map;

import com.qeweb.framework.bc.BOProperty;

/**
 * 细粒度组件--checkbox
 *
 */
abstract public class CheckBox extends EnumFcComponent {
	/*
	 * 使用自动布局显示 checkbox/radio 组的时候使用的列的数目，这个配置选项可以有多种不同的类型的值。 
		'auto' : 
		渲染的时候，组件会一列挨着一列，每一列的宽度按照整行的宽度均分。默认的是auto 
		int : 
		如果你指定了一个像 3 这样的数字，那么将会创建指定的数目的列，包含的组建将会根据vertical的值 自动的分发。
	 */
	private String columns = "auto";

	/**
	 * 获取CheckBox的已选内容
	 * @param bop
	 * @return
	 */
	final protected Map<String, String> getChecked(BOProperty bop) {
		return MutiValueHelper.getChecked(bop);
	}

	@Override
	public void free() {
		columns = "auto";
		super.free();
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}
}
