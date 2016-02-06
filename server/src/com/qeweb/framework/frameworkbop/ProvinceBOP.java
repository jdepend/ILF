package com.qeweb.framework.frameworkbop;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.BCRange;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.bc.prop.Value;
import com.qeweb.framework.common.utils.BoOperateUtil;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 省份
 *
 */
public class ProvinceBOP extends BOProperty {
	private static final long serialVersionUID = 124143544L;
	private CountryBOP country;
	private String provinceName;
	
	/**
	 * 当省份改变时将影响城市的变动
	 * @return List
	 */
	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new LinkedList<BusinessComponent>();
		result.add(new CityBOP());
		result.add(new AreaBOP());
		result.add(new StreetBOP());
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public BusinessComponent query(BOProperty sourceBop) {
		if(StringUtils.isEmpty(sourceBop.getValue().getValue()))
			return this;
		Map<String, String> provinceMap = new LinkedHashMap<String, String>();
		EnumRange.addPlease(provinceMap);
		Value value = sourceBop.getValue();
		if(BoOperateUtil.getRealBop(sourceBop) instanceof CountryBOP && value.getValue() != null) {
			DetachedCriteria dc = DetachedCriteria.forClass(ProvinceBOP.class);
			dc.add(Restrictions.eq("country.id", Long.parseLong(sourceBop.getValue().getValue())));
			List<ProvinceBOP> list = getDao().findByCriteria(dc);
			
			for (ProvinceBOP province : list) {
				provinceMap.put(province.getId() + "", province.getProvinceName());
			}
		}
		
		EnumRange cityRange = new EnumRange();
		cityRange.setResult(provinceMap);
		BCRange bcRange = new BCRange();
		bcRange.addRange(cityRange);
		setRange(bcRange);

		return this;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public CountryBOP getCountry() {
		return country;
	}

	public void setCountry(CountryBOP country) {
		this.country = country;
	}
	
}
