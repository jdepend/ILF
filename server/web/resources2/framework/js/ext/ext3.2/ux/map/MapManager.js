
/**
 * 显示地图窗口
 * @param {} lat
 * @param {} lng
 * @param {} address
 */
function getMapWin(lat, lng, address) {
	var map = new Ext.ux.MapPanel({
		zoomLevel : 15,
		gmapType : 'map',
		setCenter : {
			lat : lat,
			lng : lng,
			marker : {
				title : address
			}
		}
	});

	var mapwin = new Ext.Window({
				layout : 'fit',
				title : address,
				width : 400,
				height : 400,
				x : 40,
				y : 60,
				constrain : true,
				items : map
			});
	mapwin.show();

	mapwin.addListener("resize", function(){
		var width = mapwin.getWidth();
		var height = mapwin.getHeight();
		map.setSize(width, height);
	});
}

/**
 * MapManager
 * @type
 */
var MapManager = {
	getMapHandler : function(obj){
		if(appConfig.isBaiduMap)
			return new BMapHandler(obj);
		else
			return new GMapHandler(obj);
	}
};