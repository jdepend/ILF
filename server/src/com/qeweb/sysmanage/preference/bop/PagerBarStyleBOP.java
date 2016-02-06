package com.qeweb.sysmanage.preference.bop;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 分页风格
 */
public class PagerBarStyleBOP extends BOProperty {
	private static final long serialVersionUID = -5570745387375441401L;

	//普通风格
	public static final String STYLE_SIMPLE = "0";
	//进度条风格
	public static final String STYLE_PROGRESS = "1";
	//滑动风格
	public static final String STYLE_SLIDING = "2";
	
	@Override
	public void init() {
		EnumRange range = new EnumRange();
		Map<String,String> map = new LinkedHashMap<String,String>();
		map.put(STYLE_SIMPLE, getText(STYLE_SIMPLE));
		map.put(STYLE_PROGRESS, getText(STYLE_PROGRESS));
		map.put(STYLE_SLIDING, getText(STYLE_SLIDING));
		range.setResult(map);
		addRange(range);
	}
	
	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> relations = new ArrayList<BusinessComponent>();
		relations.add(new ProgressBarPagerImgBOP());
		return relations;
	}
	
	private String getText(String style){
		if(StringUtils.isEmpty(style))
			return "";
		
		if(style.equals(STYLE_SIMPLE))
			return "普通风格";
		else if(style.equals(STYLE_PROGRESS))
			return "进度条风格";
		else if(style.equals(STYLE_SLIDING))
			return "滑动风格";
		else
			return "";
	}
}
