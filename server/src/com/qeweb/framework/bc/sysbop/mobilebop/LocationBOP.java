package com.qeweb.framework.bc.sysbop.mobilebop;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.common.map.LocationManager;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * LocationBOP是一个平台BO,用于单独存储基站/GPS信息和实际物理位置信息
 * <br>
 * 在数据库的设计上,它仅有三个字段:id, location[LBS或GPS信息], address[经度,维度,国家地区城市街道]
 * <br>
 */
public class LocationBOP extends BOProperty {
	/**
	 *
	 */
	private static final long serialVersionUID = -591542130137743272L;

	private String location;//LBS或GPS信息
	private String address;	//格式： 经度,维度,国家地区城市街道

	public LocationBOP insert() {
		if(StringUtils.isEmpty(getValue().getValue())) {
			LocationBOP bop = new LocationBOP();
			bop.setId(-1L);
			bop.setLocation("0,0,0,0");
			bop.setAddress(",,地址未识别");
			return bop;
		}

		LocationBOP bop = getLocationInfo();
		if(bop == null) {
			setLocation(getValue().getValue());
			setAddress(new LocationManager().getLocation().getAddressInfo(getLocation()));
			getDao().save(this);
			return this;
		}
		else {
			return bop;
		}
	}

	public LocationBOP getLocationInfo() {
		DetachedCriteria dc = DetachedCriteria.forClass(LocationBOP.class);
		dc.add(Restrictions.eq("location", getLocation()));
		dc.add(Restrictions.isNotNull("address"));

		return (LocationBOP) getDao().get(dc);
	}

	public String getLocation() {
		if(StringUtils.isEmpty(location))
			setLocation(getValue().getValue());
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
	    this.address = "";
		if(StringUtils.isNotEmpty(address)){
			this.address = address;
		}
		else{
    		LocationBOP result = getLocationInfo();
    		if(result != null)
    			this.address = result.getAddress();
		}
	}


}
