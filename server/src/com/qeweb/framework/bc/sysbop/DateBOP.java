package com.qeweb.framework.bc.sysbop;

import com.qeweb.framework.bc.BOProperty;

/**
 * 时间bop，格式化时间
 */
public class DateBOP extends BOProperty {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1099243959380145899L;
	public static String YYYY = "Y";
	public static String YYYY_MM = "Y-m";
	public static String YYYY_MM_DD = "Y-m-d";
	public static String YYYY_MM_DD_HH = "Y-m-d H";
	public static String YYYY_MM_DD_HH_MM = "Y-m-d H:i";
	public static String YYYY_MM_DD_HH_MM_SS = "Y-m-d H:i:s";
	
	private String format = YYYY_MM_DD;

	//禁止选择的星期, 格式:[N1,N2...]  Nx是自然数, 且 0 <= N <= 6
	//例: [0,6], 禁止选择所有星期日和星期六
	private String disabledDays;
	
	public DateBOP(){
	}

	public DateBOP(String format){
		this.format = format;
	}
	
	@Override
	public void free() {
		super.free();
		disabledDays = null;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * 禁止选择的星期
	 * @return 	格式:[N1,N2...], Nx是自然数, 且 0 <= N <= 6
	 */
	public String getDisabledDays() {
		return disabledDays;
	}

	/**
	 * 设置禁止选择的星期
	 * @param disabledDays
	 * 	格式:[N1,N2...], Nx是自然数, 且 0 <= N <= 6
	 *  <li>例: [0,6], 禁止选择所有星期日和星期六
	 */
	public void setDisabledDays(String disabledDays) {
		this.disabledDays = disabledDays;
	}
}
