package com.qeweb.framework.app.tag;

import com.qeweb.framework.pal.PageContextInfo;
import com.qeweb.framework.pal.ViewComponent;

/**
 * 
 * SheetTag/PageTag实现了该接口
 */
public interface ParentTag {
	PageContextInfo getPageContextInfo();
	
	void addVC(ViewComponent vc);
}
