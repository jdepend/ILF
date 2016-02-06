package com.qeweb.framework.impconfig.mdt.use;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.BCRange;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.bc.prop.SequenceRange;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.ddt.use.bean.DDTSchema;
import com.qeweb.framework.impconfig.mdt.use.bean.MDT_Value;
import com.qeweb.framework.impconfig.mdt.use.dao.ia.IMDT_ValueSetDao;
import com.qeweb.framework.impconfig.mdt.use.dao.impl.MDT_ValueSetDaoImpl;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;

/**
 * MDT值集处理者, 为DDT中的细粒度组件设置范围
 */
class MDTValueSetHandler {
	
	private static IMDT_ValueSetDao mdtValueSetDao = new MDT_ValueSetDaoImpl();
	/**
	 * 为fc设置范围.
	 * <p>1.通过字段类型(string, int, date...)设置范围;</p>
	 * <p>2.通过范围描述设置范围;</p>
	 * <p>3.通过弹性域值集(数据字典)设置范围;</p>
	 * <p>4.通过弹性域的多段值集设置范围.</p>
	 * @param fc
	 * @param DDTSchema
	 */
	final static void setRange(FinegrainedComponent fc, DDTSchema DDTSchema) {
		//如果FC来自DDT纵向扩展, 则不设置范围
		if(StringUtils.isEmpty(DDTSchema.getDataType()))
			return;
		
		BOProperty bop = fc.getBc();
		BCRange bcRange = bop.getRange();
		//1.通过字段类型(string, int, date...)设置范围
		setDataTypeRange(bcRange, DDTSchema);
		//2.通过范围描述设置范围
		setRangeDesc(bcRange, DDTSchema);
		//3.通过弹性域值集设置范围
		setVelueSetRange(bcRange, DDTSchema);
		//4.通过弹性域的多段值集设置范围
		setManyVelueSetRange(bcRange, DDTSchema);
	}
	
	/**
	 * 通过字段类型(string, int, date...)设置范围
	 * @param bcRange
	 * @param DDTSchema
	 */
	private static void setDataTypeRange(BCRange bcRange, DDTSchema DDTSchema) {
		if(StringUtils.isEqualIgnoreCase("int", DDTSchema.getDataType())) {
			SequenceRange range = new SequenceRange();
			range.setMax(Integer.MAX_VALUE);
			range.setMin(Integer.MIN_VALUE);
			range.setStep(1);
		}
		else if(StringUtils.isEqualIgnoreCase("date", DDTSchema.getDataType())) {
			;
		}
		
		bcRange.setRequired(DDTSchema.getIsRequired());
		bcRange.setMaxLength(DDTSchema.getMaxLength());
	}
	
	/**
	 * 通过范围描述设置范围
	 * @param bcRange
	 * @param DDTSchema
	 */
	private static void setRangeDesc(BCRange bcRange, DDTSchema DDTSchema) {
		boolean isAddRange = false;
		SequenceRange range = new SequenceRange();
		if(DDTSchema.getMaxValue() != null) {
			range.setMax(DDTSchema.getMaxValue());
			isAddRange = true;
		}
		if(DDTSchema.getMinValue() != null) {
			range.setMin(DDTSchema.getMinValue());
			isAddRange = true;
		}
		if(DDTSchema.getStepValue() != null) {
			range.setStep(DDTSchema.getStepValue());
			isAddRange = true;
		}
		
		if(isAddRange)
			bcRange.addRange(range);
	}
	
	/**
	 * 通过弹性域值集(数据字典)设置范围
	 * @param bcRange
	 * @param DDTSchema
	 */
	private static void setVelueSetRange(BCRange bcRange, DDTSchema DDTSchema) {
		String valueSetId = StringUtils.removeAllSpace(DDTSchema.getValueSetId());
		String[] valueSetIds = StringUtils.split(valueSetId, ",");
		if(StringUtils.isEmpty(valueSetId) || StringUtils.isNotEmpty(valueSetIds))
			return;
		
		setValueSetRange(bcRange, valueSetId);
	}
	
	/**
	 * 通过弹性域值集(数据字典)设置范围
	 * @param bcRange
	 * @param DDTSchema
	 */
	private static void setManyVelueSetRange(BCRange bcRange, DDTSchema DDTSchema) {
		String valueSetId = StringUtils.removeAllSpace(DDTSchema.getValueSetId());
		String[] valueSetIds = StringUtils.split(valueSetId, ",");
		if(StringUtils.isEmpty(valueSetIds))
			return;
		
		for(String id : valueSetIds) {
			setValueSetRange(bcRange, id);
		}
	}
	
	/**
	 * 将值集设置为枚举型范围
	 * @param bcRange
	 * @param valueSetId
	 */
	private static void setValueSetRange(BCRange bcRange, String valueSetId) {
		List<MDT_Value> mdtValueList = mdtValueSetDao.getVelueSetRange(StringUtils.convertLong(valueSetId));
		if(ContainerUtil.isNull(mdtValueList))
			return;
		
		EnumRange range = new EnumRange();
		Map<String, String> rangeMap = new LinkedHashMap<String, String>();
		EnumRange.addPlease(rangeMap);
		for(MDT_Value value : mdtValueList) {
			rangeMap.put(value.getValue(), value.getText());
		}
		range.setResult(rangeMap);
		
		bcRange.addRange(range);
	}

}
