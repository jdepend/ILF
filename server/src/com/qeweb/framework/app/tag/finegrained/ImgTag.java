package com.qeweb.framework.app.tag.finegrained;

import java.io.Serializable;

import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.finegrained.other.Image;


/**
 * 细粒度组件--ImgTag标签
 *
 */
public class ImgTag extends FineGrainedTag implements Serializable {
	
	private static final long serialVersionUID = -2732047485056427503L;

	@Override
	protected FinegrainedComponent getFCInstance() {
		return (Image) AppManager.createVC(Image.class);
	}
}
