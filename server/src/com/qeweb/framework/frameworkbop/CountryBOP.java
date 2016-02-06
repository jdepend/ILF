package com.qeweb.framework.frameworkbop;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.EnumRange;

/**
 * 国家
 * @author alex
 * 2013年12月31日上午11:18:51
 */
public class CountryBOP extends BOProperty {
	
	private static final long serialVersionUID = -3489612347557677315L;
	private String countryName;
	
	/**
	 * 当国家改变时将影响省份的变动
	 * @return List
	 */
	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new LinkedList<BusinessComponent>();
		result.add(new ProvinceBOP());
		result.add(new CityBOP());
		result.add(new AreaBOP());
		result.add(new StreetBOP());
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		Map<String, String> countryMap = new LinkedHashMap<String, String>();
		List<CountryBOP> list = (List<CountryBOP>)getDao().findAll(CountryBOP.class);
		EnumRange.addPlease(countryMap);
		for (CountryBOP country : list) {
			countryMap.put(country.getId() + "", country.getCountryName());
		}
		
		EnumRange range = new EnumRange();
		range.setResult(countryMap);
		addRange(range);
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

}
