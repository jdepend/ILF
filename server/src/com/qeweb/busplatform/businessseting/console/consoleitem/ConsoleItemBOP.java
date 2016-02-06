package com.qeweb.busplatform.businessseting.console.consoleitem;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.busplatform.businessseting.BusSetting;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.constant.ConstantSplit;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 业务流程配置BOP
 */
abstract public class ConsoleItemBOP extends BOProperty {

	private static final long serialVersionUID = -7682555272769988996L;

	private String boId;
	private String moduleName;
	private String paramName;
	private String desc;
	private String remark;

	public ConsoleItemBOP() {

	}

	public ConsoleItemBOP(Map<String, BusSetting> busMap) {
		load(busMap);
	}

	/**
	 * 从busSetting中读取配置信息, 以设置BOP的值/状态/范围
	 * @param busSetting
	 */
	private void load(Map<String, BusSetting> busMap) {
		BusSetting busSetting = busMap.get(getBusSettingKey());

		setSettingRange(busSetting);
		setLocalName(busSetting.getDescription());
		setValue(busSetting.getParamValue());

		setBoId(busSetting.getBoId());
		setModuleName(busSetting.getModuleName());
		setParamName(busSetting.getParamName());
		setDesc(busSetting.getDescription());
		setRemark(busSetting.getRemark());
	}

	/**
	 * 根据busSetting设置Range
	 * @param busSetting
	 */
	protected void setSettingRange(BusSetting busSetting) {
		//busSettion.remark的格式 : remark="1:手动发布,2:自动发布"
		String[] remark = StringUtils.split(StringUtils.removeAllSpace(busSetting.getRemark()), ConstantSplit.COMMA_SPLIT);

		Map<String, String> result = new LinkedHashMap<String, String>();
		for(String temp : remark) {
			String[] arr = StringUtils.split(temp, ":");
			result.put(arr[0], arr[1]);
		}

		EnumRange range = new EnumRange();
		range.setResult(result);

		addRange(range);
	}

	/**
	 * 获取bop的值
	 * @return
	 */
	public String getParamValue() {
		return getValue().getValue();
	}

	/**
	 *
	 * @return
	 */
	abstract protected String getBusSettingKey();

	/**
	 *
	 * @param boId
	 * @param moduleName
	 * @param paramName
	 * @return
	 */
	protected String getBusSettingKey(String boId, String moduleName, String paramName) {
		return boId + ConstantSplit.GA_PARAM_SPLIT + moduleName + ConstantSplit.GA_PARAM_SPLIT + paramName;
	}

	public String getBoId() {
		return boId;
	}

	public void setBoId(String boId) {
		this.boId = boId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


}
