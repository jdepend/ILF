package com.qeweb.framework.frameworkbop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BopDecorator;
import com.qeweb.framework.bc.prop.BCRange.LogicEnum;
import com.qeweb.framework.bc.prop.SequenceRange;

/**
 * 为bop增加连续型校验
 *
 */
public class SequenceBop extends BopDecorator {
	private static final long serialVersionUID = 1328424220986934081L;
	public static final double MAX_VALUE = 9999999999999D;

	/**
	 * 为bop增加连续型校验,默认使用 AND 连接 
	 * @param bop
	 * @param min 最小值, 数值要求要求:小数点前最多13位，小数点后最多8位
	 * @param max 最大值, 数值要求要求:小数点前最多13位，小数点后最多8位
	 * @param step 步进值, 数值要求要求:小数点前最多13位，小数点后最多8位
	 */
	public SequenceBop(BOProperty bop, double min, double max, double step) {
		super(bop);
		SequenceRange range = new SequenceRange(); 
		range.setMin(min);
		range.setMax(max);
		range.setStep(step);

		bop.getRange().addRange(range, LogicEnum.AND);
		this.setRange(bop.getRange());
	}
	
	/**
	 * 为bop增加连续型校验,使用 logicEnum 连接 
	 * @param bop
	 * @param min 小数点前最多13位，小数点后最多8位
	 * @param max 小数点前最多13位，小数点后最多8位
	 * @param step 小数点前最多13位，小数点后最多8位
	 * @param logicEnum
	 */
	public SequenceBop(BOProperty bop, double min, double max, double step, LogicEnum logicEnum) {
		super(bop);
		SequenceRange range = new SequenceRange(); 
		range.setMin(min);
		range.setMax(max);
		range.setStep(step);
		
		bop.getRange().addRange(range, logicEnum);
		this.setRange(bop.getRange());
	}

	/**
	 * 为bop增加连续型校验,默认使用 AND 连接 
	 * @param bop
	 * @param min 小数点前最多13位，小数点后最多8位
	 * @param max 小数点前最多13位，小数点后最多8位
	 * @param step 小数点前最多13位，小数点后最多8位
	 * @param scale 最大值8，最小值0
	 */
	public SequenceBop(BOProperty bop, double min, double max, double step, int scale) {
		super(bop);
		SequenceRange range = new SequenceRange(); 
		range.setMin(min);
		range.setMax(max);
		range.setStep(step);
		range.setScale(scale);

		bop.getRange().addRange(range, LogicEnum.AND);
		this.setRange(bop.getRange());
	}
	
	/**
	 * 为bop增加连续型校验,使用 logicEnum 连接 
	 * @param bop
	 * @param min 小数点前最多13位，小数点后最多8位
	 * @param max 小数点前最多13位，小数点后最多8位
	 * @param step 小数点前最多13位，小数点后最多8位
	 * @param scale 最大值8，最小值0
	 * @param logicEnum
	 */
	public SequenceBop(BOProperty bop, double min, double max, double step, int scale, LogicEnum logicEnum) {
		super(bop);
		SequenceRange range = new SequenceRange(); 
		range.setMin(min);
		range.setMax(max);
		range.setStep(step);
		range.setScale(scale);
		
		bop.getRange().addRange(range, logicEnum);
		this.setRange(bop.getRange());
	}

	@Override
	public void addRange(LogicEnum logicRule) {
		
	}
}
