package com.qeweb.framework.bc.sysbop;

import java.io.File;

import com.qeweb.framework.bc.prop.ImgValue;
import com.qeweb.framework.bc.prop.Value;

/**
 * 图片BOP
 */
public class ImageBOP extends FileBOP {

	private static final long serialVersionUID = 3036916368752439955L;
	
	private ImgValue value;

	public ImageBOP() {
		super();
	}
	
	public ImageBOP(String displayName, String path, File file){
		super(displayName, path, file);
	}
	
	/**
	 * 设置宽度, 可支持百分比型式.
	 * 高度和宽度仅需设置1个即可, 图片会自动计算另一个的压缩比例.
	 * 
	 * @param width
	 */
	public void setWidth(String width) {
		getValue().setWidth(width);
	}
	
	public String getWidth() {
		return getValue().getWidth();
	}
	
	/**
	 * 设置高度, 可支持百分比型式.
	 * 高度和宽度仅需设置1个即可, 图片会自动计算另一个的压缩比例.
	 * 
	 * @param height
	 */
	public void setHeight(String height) {
		getValue().setHeight(height);
	}
	
	public String getHeight() {
		return getValue().getHeight();
	}
	
	/**
	 * 图片是否自适应, 默认是false, 
	 * 如果isAdaptive==true, 将根据图片的实际大小展示图片;否则根据设定的height和weight展示
	 * @param isAdaptive
	 */
	public void setAdaptive(boolean isAdaptive) {
		getValue().setAdaptive(isAdaptive);
	}
	
	public boolean isAdaptive() {
		return getValue().isAdaptive();
	}
	
	@Override
	public ImgValue getValue() {
		if(value == null)
			value = new ImgValue();
		return value;
	}

	public void setValue(ImgValue value) {
		this.value = value;
	}
	
	@Override
	public void setValue(Value value) {
		if(value != null)
			getValue().setValue(value.getValue());
	}
	
	@Override
	public void setValue(String value) {
		ImgValue thisValue = getValue();
		thisValue.setValue(value);
	}
	
	@Override
	public String toImage() {
		return toText();
	}
}
