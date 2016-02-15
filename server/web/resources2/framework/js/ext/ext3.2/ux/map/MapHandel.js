/**
 * 地图显示处理类
 */
function MapHandler(obj) {
	this.obj = obj;
	this.map;					//当前地图
	this.currentPositionPoint;	//当前Point
	this.currentPositionMarker;	//图像标注

	/**
	 * 初始化地图
	 */
	this.initialize = function(lng, lat, zoomLevel) {};

	/**
	 * 获取地图
	 */
	this.getMap = function() {
		return this.map;
	};

	/**
	 * 地图显示当前Point图像标注
	 */
	this.showCurrentPositionMarker = function(title) {};

	/**
	 * 添加地图事件
	 */
	this.addMapControl = function() {};


}

