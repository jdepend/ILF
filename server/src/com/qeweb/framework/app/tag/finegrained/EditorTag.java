package com.qeweb.framework.app.tag.finegrained;

import java.io.Serializable;

import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.finegrained.text.Editor;


/**
 * 细粒度组件--富文本编辑器标签
 *
 */
public class EditorTag extends FineGrainedTag implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9066812028059265403L;
	
	private String height;

	@Override
	protected FinegrainedComponent getFCInstance() {
		return (Editor) AppManager.createVC(Editor.class);
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
