package com.qeweb.framework.common.map.baidumap;

import net.sf.json.JSONObject;

import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.map.*;

/**
 * 利用基站信息或经纬度获取地址信息
 */
public class BaiduLocation implements Location {

	/**
	 * 根据经纬度或基站信息获得真实地址信息
	 * @param value	基站或经纬度信息
	 * @return  格式： 经度,维度,国家地区城市街道
	 */
	public String getAddressInfo(String value) {
		if(isGPS(value))
			return doGetGeoByBaidu(value);
		else if(isLBS(value))
			return getLocationByCellid(value);
		else
			return "";
	}

	/**
	 * 地址解析：根据地址获取坐标
	 * http://api.map.baidu.com/geocoder?address=地址&output=输出格式类型&key=用户密钥&city=城市名
	 *
	 * 通过地址获取  纬度,经度
	 * @param latLon
	 * @return 纬度,经度
	 */
	public String doGetGeoCoderByBaidu(String latLon) {
		latLon = StringUtils.removeAllSpace(latLon);

		String json = HttpRequestProxy
		.doPost("http://api.map.baidu.com/geocoder?address="
				+ latLon.split(",")[0]
				+ "&output=json&key=d36249006a64b7f388bb6eb0104cebbc"
				+ "&city=" + latLon.split(",")[1],
				"", Constant.ENCODING_UTF8);

		/*
		 * return json 格式：
		 *
		 * { "status":"OK",
			 "result":{
			 "location":{
					 "lng":113.088418,
					 "lat":28.245909
			 		},
			 "precise":1,
			 "confidence":80,
			 "level":"\u9053\u8def"
			 	}
			}
		*/

		String lng = "",lat="";
		if (json != null && json.indexOf("location") > 0) {
			JSONObject jsonObj = JSONObject.fromObject(json);
			lng = (String) jsonObj.getJSONObject("result").getJSONObject("location").getString("lng");
			lat = (String) jsonObj.getJSONObject("result").getJSONObject("location").getString("lat");
		}

		return lat + "," + lng;
	}


	/**

	 * 	逆地址解析：根据坐标获取地址
	 * 	http://api.map.baidu.com/geocoder?location=纬度,经度&output=输出格式类型&key=用户密钥
	 *
	 * 通过经纬度从baidu获取地址信息<br>
	 * @param latLon 经纬度， 格式：Latitude:经度,Longitude:纬度
	 * @return string 地址信息，  格式： 经度,纬度
	 */
	private static String doGetGeoByBaidu(String latLon) {
		latLon = StringUtils.removeAllSpace(latLon);
		latLon = latLon.replace("Latitude:", "");
		latLon = latLon.replace("Longitude:", "");

		String json = HttpRequestProxy
				.doPost("http://api.map.baidu.com/geocoder?location="
						+ latLon.split(",")[0]
						+ ","
						+ latLon.split(",")[1]
						+ "&output=json&key=d36249006a64b7f388bb6eb0104cebbc",
						"", Constant.ENCODING_UTF8);
	/*
	 * return json 格式：
	 *
		 {	"status":"OK",
			"result":{
				"location":{ "lng":113.088718, "lat":28.245377 },
				"formatted_address":"湖南省长沙市长沙县天华中路27号",
				"business":"",
				"addressComponent":{
								"city":"长沙市",
								"district":"长沙县",
								"province":"湖南省",
								"street":"天华中路",
								"street_number":"27号"
								},
				"cityCode":158
				}
			}
		*/

		String addr = "";
		if (json != null && json.indexOf("address") > 0) {
			JSONObject jsonObj = JSONObject.fromObject(json);
			addr = (String) jsonObj.getJSONObject("result").getString("formatted_address");
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

	public static void main(String[] args) {
//		System.out.println(getLocationByCellid("23373,29658,460,0"));
//		System.out.println(getLocationByCellid("42,415,310,410"));
		System.out.println(doGetGeoByBaidu("28.251587,113.095895"));
//		System.out.println(doGetCoordByBaidu("湖南省长沙市长沙县开元东路53号,长沙市"));
		System.out.println(doGetGeoByBaidu("Latitude: 28.2453773,Longitude: 113.0887178"));
		System.out.println(doGetGeoByBaidu("Latitude: 28.251587,Longitude: 113.095895"));
	}

}
