function BMapHandler(obj) {
	BMapHandler.superclass.constructor.call(this, obj);
	/**
	 * 初始化地图
	 */
	this.initialize = function(lng, lat, zoomLevel) {
		var map = new BMap.Map(this.obj.body.dom);
		var point = new BMap.Point(lng, lat);
		map.centerAndZoom(point, zoomLevel);
		this.currentPositionPoint = point;
		this.map = map;
	};

	/**
	 * 地图显示当前Point图像标注
	 */
	this.showCurrentPositionMarker = function(title) {
		if (this.currentPositionMarker == null) {
			var map = this.getMap();
			var marker = new BMap.Marker(this.currentPositionPoint); // 创建标注
			map.addOverlay(marker); // 将标注添加到地图中
			this.currentPositionMarker = marker;
			marker.setTitle(title);
		}
	};

	/**
	 * 添加地图事件
	 */
	this.addMapControl = function() {
		var map = this.getMap();
		map.enableDragging();			//启用地图拖拽事件，默认启用(可不写)
		map.enableScrollWheelZoom();	//启用地图滚轮放大缩小
		map.enableDoubleClickZoom();	//启用鼠标双击放大，默认启用(可不写)
		map.enableKeyboard();			//启用键盘上下左右键移动地图
	};
}

extend(BMapHandler, MapHandler);
