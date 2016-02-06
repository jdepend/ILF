package com.qeweb.framework.pal.coarsegrained.tableWindow;

/**
 * 弹出窗体尺寸数据存储类
 */
public class WindowSize {
	
	private String addWinWidth;
	private String addWinHeight;
	private String updateWinWidth;
	private String updateWinHeight;
	private String viewWinWidth;
	private String viewWinHeight;

	public WindowSize(String sizeStr) {
		Interpreter in = new Interpreter(sizeStr);
		addWinWidth = in.getAddWinWidth();
		addWinHeight = in.getAddWinHeight();
		updateWinWidth = in.getUpdateWinWidth();
		updateWinHeight = in.getUpdateWinHeight();
		viewWinWidth = in.getViewWinWidth();
		viewWinHeight = in.getViewWinHeight();
	}

	public String getAddWinWidth() {
		return addWinWidth;
	}

	public String getAddWinHeight() {
		return addWinHeight;
	}

	public String getUpdateWinWidth() {
		return updateWinWidth;
	}

	public String getUpdateWinHeight() {
		return updateWinHeight;
	}

	public String getViewWinWidth() {
		return viewWinWidth;
	}

	public String getViewWinHeight() {
		return viewWinHeight;
	}
}
