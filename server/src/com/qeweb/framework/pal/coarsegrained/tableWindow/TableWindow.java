package com.qeweb.framework.pal.coarsegrained.tableWindow;

import com.qeweb.framework.pal.ViewComponent;


/**
 * 平台特定功能窗体（新增、修改、查看）
 *
 */
public class TableWindow extends ViewComponent  {
	//窗体宽度
	private String winWidth = null;
	//窗体高度
	private String winHeight = null;

	@Override
	public void paint() {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the winWidth
	 */
	public String getWinWidth() {
		return winWidth;
	}

	/**
	 * @param winWidth the winWidth to set
	 */
	public void setWinWidth(String winWidth) {
		this.winWidth = winWidth;
	}

	/**
	 * @return the windHeight
	 */
	public String getWinHeight() {
		return winHeight;
	}

	/**
	 * @param windHeight the windHeight to set
	 */
	public void setWinHeight(String winHeight) {
		this.winHeight = winHeight;
	}

	@Override
	public void free() {
		winWidth = null;
		winHeight = null;

		super.free();
	}
}
