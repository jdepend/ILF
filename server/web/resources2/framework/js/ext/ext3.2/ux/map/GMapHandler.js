/**
 *
 * @param {} obj
 */
function GMapHandler(obj) {
	GMapHandler.superclass.constructor.call(this, obj);
	/**
	 * 初始化地图
	 */
	this.initialize = function(lng, lat, zoomLevel) {
		var map = new GMap2(this.obj.body.dom);
		var point = new GLatLng(lat,lng);
		map.setCenter(point, zoomLevel);
		this.currentPositionPoint = point;
		this.map = map;
	};

	/**
	 * 地图显示当前Point图像标注
	 */
	this.showCurrentPositionMarker = function(title) {
		if (this.currentPositionMarker == null) {
			var map = this.getMap();
 			var mark = new GMarker(this.currentPositionPoint, this.obj.setCenter.marker);
			map.addOverlay(mark);
		}
	};

	/**
	 * 添加地图事件
	 */
	this.addMapControl = function() {
		var map = this.getMap();
		var mcf = window["GSmallMapControl"];
        if (typeof mcf === 'function') {
            map.addControl(new mcf());
        }
	};
}

extend(GMapHandler, MapHandler);
