package com.qeweb.demo.interaction.relation.bop;


import com.qeweb.demo.interaction.relation.bop.DemoCheckLogoBOP.LOGO;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.ImgValue;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 展现图片
 *
 */
public class DemoLogoBOP extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4857066914397936132L;

	@Override
	public void init() {
		ImgValue value = new ImgValue();
		value.setWidth("100");
		value.setHeight("100");
		value.setValue("http://www.baidu.com/img/baidu_sylogo1.gif");
		this.setValue(value);
	}
	
	@Override
	public BusinessComponent query(BOProperty sourceBop) {
		if(!(sourceBop instanceof DemoCheckLogoBOP)) 
			return null;
		
		ImgValue value = new ImgValue();
		DemoCheckLogoBOP checkLogo = (DemoCheckLogoBOP)sourceBop;
		if(StringUtils.isEqual(LOGO.BAIDU + "", checkLogo.getValue().getValue())) 
			value.setValue("http://www.baidu.com/img/baidu_sylogo1.gif");
		else if(StringUtils.isEqual(LOGO.GOOGLE + "", checkLogo.getValue().getValue())) 
			value.setValue("http://www.google.cn/landing/cnexp/google-search.png");

		value.setWidth("100");
		value.setHeight("100");
		this.setValue(value);
		
		return this;
	}
}
