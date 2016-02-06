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
 * 城市
 *
 */
public class CityBOP extends BOProperty {
	private static final long serialVersionUID = 124143541L;
	private ProvinceBOP province;
	private String cityName;
	
	/**
	 * 当城市改变时将影响区的变动
	 * @return List
	 */
	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new LinkedList<BusinessComponent>();
		result.add(new AreaBOP());
		result.add(new StreetBOP());
		
		return result;
	}
	
	/**
	 * 处理省市间的关联, 根据省份查询装载市
	 * @param sourceBCList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BusinessComponent query(BOProperty sourceBop) {
		if(StringUtils.isEmpty(sourceBop.getValue().getValue()))
			return this;
		Map<String, String> cityMap = new LinkedHashMap<String, String>();
		EnumRange.addPlease(cityMap);
		Value value = sourceBop.getValue();
		if(BoOperateUtil.getRealBop(sourceBop) instanceof ProvinceBOP && value.getValue() != null) {
			DetachedCriteria dc = DetachedCriteria.forClass(CityBOP.class);
			dc.add(Restrictions.eq("province.id", Long.parseLong(sourceBop.getValue().getValue())));
			List<CityBOP> list = getDao().findByCriteria(dc);
			
			for (CityBOP city : list) {
				cityMap.put(city.getId() + "", city.getCityName());
			}
		}
		
		EnumRange cityRange = new EnumRange();
		cityRange.setResult(cityMap);
		BCRange bcRange = new BCRange();
		bcRange.addRange(cityRange);
		setRange(bcRange);

		return this;
	}

	public ProvinceBOP getProvince() {
		return province;
	}
	public void setProvince(ProvinceBOP province) {
		this.province = province;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
}
