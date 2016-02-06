package com.qeweb.framework.app.tag.finegrained;

import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.finegrained.enumcomp.CheckBox;

public class CheckBoxTag extends FineGrainedTag {
	
	private static final long serialVersionUID = 312345890234L;
	
	/*
	 * 使用自动布局显示 checkbox/radio 组的时候使用的列的数目，这个配置选项可以有多种不同的类型的值。 
		'auto' : 
		渲染的时候，组件会一列挨着一列，每一列的宽度按照整行的宽度均分。默认的是auto 
		int : 
		如果你指定了一个像 3 这样的数字，那么将会创建指定的数目的列，包含的组建将会根据vertical的值 自动的分发。
	 */
	private String columns;
	
	@Override
	protected FinegrainedComponent getFCInstance() {
		CheckBox checkbox = (CheckBox) AppManager.createVC(CheckBox.class);
		if(StringUtils.isNotEmpty(columns))
			checkbox.setColumns(columns);
		return checkbox;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}
}
