package com.qeweb.framework.bc.prop;

/**
 * 图片类型的Value 
 *
 */
public class ImgValue extends Value {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3997561148280338695L;
	//图片是否自适应, 默认是false, 
	//如果isAdaptive==true, 将根据图片的实际大小展示图片;否则根据设定的height和weight展示
	private boolean isAdaptive = false;
	private String height;
	private String width;
	
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public boolean isAdaptive() {
		return isAdaptive;
	}
	public void setAdaptive(boolean isAdaptive) {
		this.isAdaptive = isAdaptive;
	}
}
