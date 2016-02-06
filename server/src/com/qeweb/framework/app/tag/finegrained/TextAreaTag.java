package com.qeweb.framework.app.tag.finegrained;

import java.io.Serializable;

import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.finegrained.text.TextArea;


/**
 * 细粒度组件--TextArea标签
 *
 */
public class TextAreaTag extends FineGrainedTag implements Serializable {
	
	private static final long serialVersionUID = 1975008044930028985L;

	private String height;		//多行文本域高度
	
	@Override
	protected FinegrainedComponent getFCInstance() {
		return (TextArea) AppManager.createVC(TextArea.class);
	}
	
	@Override
	protected void initFC(FinegrainedComponent fc, Container container) {
		Float height = StringUtils.convertToFloat(getHeight());
		if(height != null)
			fc.setHeight(height);
		
		super.initFC(fc, container);
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
}
