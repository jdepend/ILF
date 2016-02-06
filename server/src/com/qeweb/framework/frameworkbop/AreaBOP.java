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

public class AreaBOP extends BOProperty {
	private static final long serialVersionUID = 124143542L;
	private CityBOP city;
	private String areaName;
	
	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new LinkedList<BusinessComponent>();
		result.add(new StreetBOP());
		
		return result;
	}
	
	/**
	 * 处理省-市-区间的关联, 根据省份和城市查询装载地区
	 * @param sourceBCList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BusinessComponent query(BOProperty sourceBop) {
		if(StringUtils.isEmpty(sourceBop.getValue().getValue()))
			return this;
		Map<String, String> areaMap = new LinkedHashMap<String, String>();
		EnumRange.addPlease(areaMap);
		Value value = sourceBop.getValue();
		if(BoOperateUtil.getRealBop(sourceBop) instanceof CityBOP && value.getValue() != null){
			DetachedCriteria dc = DetachedCriteria.forClass(AreaBOP.class);			
			dc.add(Restrictions.eq("city.id", Long.parseLong(sourceBop.getValue().getValue())));
			List<AreaBOP> list = getDao().findByCriteria(dc);
			for (AreaBOP area : list) {
				areaMap.put(area.getId() + "", area.getAreaName());
			}
		}
		EnumRange areaRange = new EnumRange();
		areaRange.setResult(areaMap);
		BCRange bcRange = new BCRange();
		bcRange.addRange(areaRange);
		setRange(bcRange);
		return this;
	}

	public CityBOP getCity() {
		return city;
	}

	public void setCity(CityBOP city) {
		this.city = city;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

}
