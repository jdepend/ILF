package com.qeweb.framework.pal.coarsegrained.tableWindow;

import org.apache.commons.lang3.math.NumberUtils;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 弹出窗体尺寸解析器.
 * <p>
 * sizeStr 格式是：add:width=600,height=300;update:width=700,height=400;view:width=800,height=500
	含义：新增弹出框宽度600，高度300；修改弹出框宽度700，高度400，查看弹出框宽度800，高度500
	注：弹出框宽度默认为500，弹出框高度无默认值
	</p>s
 */
public class Interpreter {
	private String sizeStr;
	
	final private String WIN_TYPE_SEPARATOR = ";";
	final private String WIN_SEPARATOR = ":";
	final private String WIN_SIZE_SEPARATOR = ",";
	final private String WIN_VALUE_SEPARATOR = "=";
	
	private final String WIN_TYPE_ADD = "add";
	private final String WIN_TYPE_UPDATE = "update";
	private final String WIN_TYPE_VIEW = "view";
	private final String WIN_SIZE_WIDTH = "width";
	private final String WIN_SIZE_HEIGHT = "height";
	
	private String addWinWidth;
	private String addWinHeight;
	private String updateWinWidth;
	private String updateWinHeight;
	private String viewWinWidth;
	private String viewWinHeight;
	
	public Interpreter(String sizeStr) {
		this.sizeStr =  StringUtils.removeAllSpace(sizeStr);
		interpret();
	}
	
	/**
	 * window显示尺寸解析
	 */
	public void interpret() {
		if(StringUtils.isEmpty(this.sizeStr))
			return;
		
		String[] winSplit = StringUtils.split(this.sizeStr, WIN_TYPE_SEPARATOR);
		for(String str : winSplit) {
			if(isAdd(str)) {
				setAddWinSize(str);
			}
			else if(isUpdate(str)) {
				setUpdateWinSize(str);
			}
			else if(isView(str)) {
				setViewWinSize(str);
			}
		}
	}

	private void setViewWinSize(String str) {
		this.setWinSize(str, this.WIN_TYPE_VIEW);
	}

	private void setUpdateWinSize(String str) {
		this.setWinSize(str, this.WIN_TYPE_UPDATE);
	}

	private void setAddWinSize(String str) {
		this.setWinSize(str, this.WIN_TYPE_ADD);
	}
	
	private void setWinSize(String str, String winType){
		String[] winSplit = StringUtils.split(str, this.WIN_SEPARATOR);
		if(winSplit.length != 2)
			return;
		String[] sizeSplit = StringUtils.split(winSplit[1], this.WIN_SIZE_SEPARATOR);
		for(String sizeStr : sizeSplit){
			if(StringUtils.isEmpty(sizeStr))
				continue;
			else if(isHeight(sizeStr))
				setHeightValue(sizeStr, winType);
			else if(isWidth(sizeStr))
				setWidthValue(sizeStr, winType);
		}
	}

	private void setWidthValue(String sizeStr, String winType) {
		if(StringUtils.isEqual(this.WIN_TYPE_VIEW, winType))
			this.viewWinWidth = getValue(sizeStr);
		else if(StringUtils.isEqual(this.WIN_TYPE_UPDATE, winType))
			this.updateWinWidth = getValue(sizeStr);
		else if(StringUtils.isEqual(this.WIN_TYPE_ADD, winType))
			this.addWinWidth = getValue(sizeStr);
	}

	private void setHeightValue(String sizeStr, String winType) {
		if(StringUtils.isEqual(this.WIN_TYPE_VIEW, winType))
			this.viewWinHeight = getValue(sizeStr);
		else if(StringUtils.isEqual(this.WIN_TYPE_UPDATE, winType))
			this.updateWinHeight = getValue(sizeStr);
		else if(StringUtils.isEqual(this.WIN_TYPE_ADD, winType))
			this.addWinHeight = getValue(sizeStr);
	}

	private String getValue(String str) {
		if(StringUtils.isEmpty(str))
			return null;
		String[] sizeSplit = StringUtils.split(str, this.WIN_VALUE_SEPARATOR);
		if(sizeSplit.length != 2)
			return null;
		if(NumberUtils.isNumber(sizeSplit[1]))
			return sizeSplit[1];		
		return null;
	}

	private boolean isView(String str) {
		return isRightType(str, this.WIN_TYPE_VIEW, this.WIN_SEPARATOR);
	}

	private boolean isUpdate(String str) {
		return isRightType(str, this.WIN_TYPE_UPDATE, this.WIN_SEPARATOR);
	}

	private boolean isAdd(String str) {
		return isRightType(str, this.WIN_TYPE_ADD, this.WIN_SEPARATOR);
	}
	
	private boolean isWidth(String str) {
		return isRightType(str, this.WIN_SIZE_WIDTH, this.WIN_VALUE_SEPARATOR);
	}
	
	private boolean isHeight(String str) {
		return isRightType(str, this.WIN_SIZE_HEIGHT, this.WIN_VALUE_SEPARATOR);
	}
		
	private boolean isRightType(String str, String type, String separator) {
		String[] winSplit = StringUtils.split(str, separator);
		if(StringUtils.isEmpty(winSplit) || winSplit.length != 2)
			return false;
		
		return StringUtils.isEqualIgnoreCase(type, winSplit[0]);
	}

	public String getAddWinWidth() {
		return addWinWidth;
	}

	public void setAddWinWidth(String addWinWidth) {
		this.addWinWidth = addWinWidth;
	}

	public String getAddWinHeight() {
		return addWinHeight;
	}

	public void setAddWinHeight(String addWinHeight) {
		this.addWinHeight = addWinHeight;
	}

	public String getUpdateWinWidth() {
		return updateWinWidth;
	}

	public void setUpdateWinWidth(String updateWinWidth) {
		this.updateWinWidth = updateWinWidth;
	}

	public String getUpdateWinHeight() {
		return updateWinHeight;
	}

	public void setUpdateWinHeight(String updateWinHeight) {
		this.updateWinHeight = updateWinHeight;
	}

	public String getViewWinWidth() {
		return viewWinWidth;
	}

	public void setViewWinWidth(String viewWinWidth) {
		this.viewWinWidth = viewWinWidth;
	}

	public String getViewWinHeight() {
		return viewWinHeight;
	}

	public void setViewWinHeight(String viewWinHeight) {
		this.viewWinHeight = viewWinHeight;
	}
}
