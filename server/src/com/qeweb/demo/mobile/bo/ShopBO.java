package com.qeweb.demo.mobile.bo;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.demo.mobile.bop.ShopTypeBOP;
import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.frameworkbop.AreaBOP;
import com.qeweb.framework.frameworkbop.CityBOP;
import com.qeweb.framework.frameworkbop.EmptyBop;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.frameworkbop.ProvinceBOP;

/**
 * 门店BO
 */
public class ShopBO extends BusinessObject {

	private static final long serialVersionUID = -1086457049154686659L;

	private String shopCode;		//门店编码	
	private String shopName;		//门店名称
	private String address;			//门店地址
	private Integer province;
	private Integer city;
	private Integer area;
	private String shopType;		//门店类型
	private String shopMaster;		//店长名称
	private String remark;			//备注
	
	public ShopBO() {
		super();
		addBOP("shopCode", new NotEmptyBop(3, 20));
		addBOP("shopName", new NotEmptyBop(3, 40));
		addBOP("province", new ProvinceBOP());
		addBOP("city", new CityBOP());
		addBOP("area", new AreaBOP());
		addBOP("address", new NotEmptyBop(3, 200));
		addBOP("shopMaster", new EmptyBop(20));
		addBOP("remark", new EmptyBop(200));
		addBOP("shopType", new ShopTypeBOP());
	}
	
	@Override
	public Map<String, String> queryOrderBy() {
		Map<String, String> orderMap = new LinkedHashMap<String, String>();
		orderMap.put("shopCode", IBaseDao.ORDER_BY_DESC);
		return orderMap;
	}

	public BusinessObject showDesc(BusinessObject bo) {
		BOHelper.initPreferencePage(bo);
		return bo;
	}
	
	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getShopType() {
		return shopType;
	}

	public void setShopType(String shopType) {
		this.shopType = shopType;
	}

	public String getShopMaster() {
		return shopMaster;
	}

	public void setShopMaster(String shopMaster) {
		this.shopMaster = shopMaster;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}
}
