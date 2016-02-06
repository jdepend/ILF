package com.qeweb.framework.bc.prop;

import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.utils.MatcherUtil;
import com.qeweb.framework.common.utils.MathUtil;


/**
 * 连续型范围
 */
public class SequenceRange extends Range {
	/**
	 * 
	 */	
	private static final long serialVersionUID = -2319980038691283242L;
	private double min = 0d;
	private double max = 0d;
	private double step = 0d;
	private int scale = Integer.valueOf(AppConfig.getNumberScale());

	/**
	 * 是否在指定范围内<br>
	 * true：在指定范围内
	 * @return 
	 */
	@Override
	public boolean isIN(Value value) {
		double val = 0.0d;
		try{
			val = Double.parseDouble(value.getValue());
		}catch (Exception e) {
			return false;
		}
		
		return isINSequence(val);
	}
	
	/**
	 * 判断value是否在连续型范围内
	 * @param value
	 * @return
	 * @TODO 双精度会出现问题
	 */
	private boolean isINSequence(double value) {
		if(MathUtil.compareTo(value, min) == 1 || MathUtil.compareTo(value, max) == -1)
			return false;
		
		if(scale > 0){
			return MatcherUtil.isRightScale(String.valueOf(value), scale);			 
		}
			
		return MathUtil.sub(value, getMin()) % step == 0; 
	}
	
	
	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getStep() {
		return step;
	}

	public void setStep(double step) {
		this.step = step;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}
}
