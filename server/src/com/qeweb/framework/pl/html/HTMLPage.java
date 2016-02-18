package com.qeweb.framework.pl.html;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.pal.Page;

import java.util.List;

/**
 * HTMLPage负责画出整个页面
 *
 */
public class HTMLPage extends Page {

	@Override
	protected void paintBodyStart() {
		getPageContextInfo().write("<body>");
	}

	/**
	 * 画出页面标题
	 */
	@Override
    public void paintTitle() {
		getPageContextInfo().write("<title>");
		getPageContextInfo().write(getTitle());
		getPageContextInfo().write("</title>");
	}

	@Override
	protected void paintTipMessage(String title, String content) {

	}

	@Override
	protected void paintHeadButton() {
		getPageContextInfo().write("<div align='right'>");
		super.paintHeadButton();
		getPageContextInfo().write("</div>");
	}

	@Override
	protected void paintFootButton() {
		getPageContextInfo().write("<div align='right'>");
		super.paintFootButton();
		getPageContextInfo().write("</div>");
	}

    @Override
    protected void paintBodyEnd() {

    }

    @Override
	protected void paintContainerRelation() {
		// TODO Auto-generated method stub

	}
}
