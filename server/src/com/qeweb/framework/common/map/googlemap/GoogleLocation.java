package com.qeweb.framework.common.map.googlemap;

import net.sf.json.JSONObject;

import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.map.HttpRequestProxy;
import com.qeweb.framework.common.map.Location;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 利用基站信息或经纬度获取地址信息
 */
public class GoogleLocation implements Location {

	/**
	 * 根据经纬度或基站信息获得真实地址信息
	 * @param value	基站或经纬度信息
	 * @return  格式： 经度,维度,国家地区城市街道
	 */
	public String getAddressInfo(String value) {
		if(isGPS(value))
			return doGetGeoByGoogle(value);
		/**
		 * 此分支已停用
		 * google http://www.google.com/loc/json 接口已停用
		 */
		else if(isLBS(value))
			return getLocationByCellid(value);
		else
			return "";
	}

	/**
	 * 通过经纬度从google获取地址信息<br>
	 * @param latLon 经纬度， 格式：Latitude:经度,Longitude:维度
	 * @return string 地址信息，  格式： 经度,维度,国家地区城市街道
	 */
	public String doGetGeoByGoogle(String latLon) {
		latLon = StringUtils.removeAllSpace(latLon);
		latLon = latLon.replace("Latitude:", "");
		latLon = latLon.replace("Longitude:", "");

		String json = HttpRequestProxy
				.doPost("http://ditu.google.cn/maps/geo?ll="
						+ latLon.split(",")[0]
						+ ","
						+ latLon.split(",")[1]
						+ "&output=json&sensor=false&key=ABQIAAAAYunk7iChu3oT9uQDoTHzkBTisvXQSLDjqT59YAJlq7Vy6wcKxhRaEdjn0RLqq609cz7fA0J04y8uZQ",
						"", Constant.ENCODING_UTF8);

		String addr = "";
		if (json != null && json.indexOf("address") > 0) {
			JSONObject jsonObj = JSONObject.fromObject(json);
			addr = (String) jsonObj.getJSONArray("Placemark").getJSONObject(0).get("address");
		}

		return latLon + "," + addr;
	}

	/**
	 * 通过Cellid 从google 获取经纬度和地址信息<br>
	 * {"location":{"latitude":28.2453773,"longitude":113.0887178,
	 * "address":{"country":"中国","country_code":"CN","region":"湖南省","city":"长沙市","street":"开元中路"},
	 * "accuracy":933.0}}
	 *
	 * @param cellId
	 * @return string 地址信息， 格式： 经度,纬度,国家地区城市街道
	 */
	private static String getLocationByCellid(String cellId) {
		if(!isLBS(cellId)){
			return null;
		}

		String cell_id = cellId.split(",")[0];
		String location_area_code = cellId.split(",")[1];
		String mobile_country_code = cellId.split(",")[2];
		String mobile_network_code = cellId.split(",")[3];
		// 建立json串
		StringBuffer sbf = new StringBuffer();
		sbf.append("{\"version\": \"1.1.0\",");
		sbf.append("\"host\": \"maps.google.com\",");
		sbf.append("\"access_token\": \"2:k7j3G6LaL6u_lafw:4iXOeOpTh1glSXe\",");
//		sbf.append("\"home_mobile_country_code\": " + mobile_country_code + ",");
//		sbf.append("\"home_mobile_network_code\":" + mobile_network_code + ",");
		sbf.append("\"radio_type\": \"gsm\",");
		sbf.append("\"carrier\": \"Vodafone\",");
		sbf.append("\"request_address\": true,");
		sbf.append("\"address_language\": \"zh_CN\",");
		sbf.append("\"cell_towers\": [");
		sbf.append("{");
		sbf.append("\"cell_id\":\"" + cell_id + "\",");
		sbf.append("\"location_area_code\": " + location_area_code + ",");
		sbf.append("\"mobile_country_code\": " + mobile_country_code + ",");
		sbf.append("\"mobile_network_code\":" + mobile_network_code + ",");
		sbf.append("\"age\": 0,");
		sbf.append("\"signal_strength\": -60,");
		sbf.append("\"timing_advance\": 10");
		sbf.append("} ] }");

//		sb.append("{ \"access_token\" : \"2:k7j3G6LaL6u_lafw:4iXOeOpTh1glSXe\", \"host\" :");
//		sb.append("\"code.google.com\", \"radio_type\" : \"unknown\",");
//		sb.append(" \"home_mobile_country_code\": " + mcc + ",");
//		sb.append(" \"home_mobile_network_code\":" + mnc + ",");
//		sb.append("\"address_language\": \"zh_CN\", \"request_address\" : true, \"version\" : \"1.1.0\",  \"cell_towers\": [ ");
//		sb.append("{");
//		sb.append("\"cell_id\": \"" + cid + "\",");
//		sb.append("\"location_area_code\": " + lac + ",");
//		sb.append("\"mobile_country_code\": " + mcc + ",");
//		sb.append("\"mobile_network_code\":" + mnc + ",");
//		sb.append("\"age\": 0,");
//		sb.append("\"signal_strength\": -60,");
//		sb.append("\"timing_advance\": 1");
//		sb.append("} ] }");

		// 从Google获取latlon
		String JsonAdd = "http://www.google.com/loc/json";
		String req = HttpRequestProxy.doPost(JsonAdd, sbf.toString(), Constant.ENCODING_UTF8);
		if(StringUtils.isNotEmpty(req)) {
			JSONObject jsonObj = JSONObject.fromObject(req);
			String location = jsonObj.get("location").toString();
			String latitude = JSONObject.fromObject(location).get("latitude").toString();
			String longitude = JSONObject.fromObject(location).get("longitude").toString();
			String address = JSONObject.fromObject(location).get("address").toString();
			String country = JSONObject.fromObject(address).get("country").toString();
			String region = JSONObject.fromObject(address).get("region").toString();
			String city = JSONObject.fromObject(address).get("city").toString();
			String street = JSONObject.fromObject(address).get("street").toString();

			return latitude + "," + longitude + "," + country + region + city + street;
		}
		else {
			return null;
		}
	}

	/**
	 * 判断location是否是GPS格式
	 * @param location	地址信息串,可能是基站信息或经纬度信息
	 * <br>
	 * 经纬度 格式：经度,维度 ，如：28.2453773,113.0887178
	 * 基站信息格式：如：23373,29658,460,0
	 * @return	是GPS格式 返回true
	 */
	private static boolean isGPS(String location) {
		return (StringUtils.isNotEmpty(location) && location.split(",").length == 2);
	}

	/**
	 * 判断location是否是LBS
	 * @param location	地址信息串,可能是基站信息或经纬度信息
	 * <br>
	 * 经纬度 格式：经度,维度 ，如：28.2453773,113.0887178
	 * 基站信息格式：如：23373,29658,460,0
	 * @return 是LBS格式 返回true
	 */
	private static boolean isLBS(String location){
		return StringUtils.isNotEmpty(location) && location.split(",").length == 4;
	}

}
