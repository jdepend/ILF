/**
 * 显示地图Panel
 * @class Ext.ux.MapPanel
 * @extends Ext.Panel
 * @author
 */
Ext.ux.MapPanel = Ext.extend(Ext.Panel, {

	initComponent : function() {
		var defConfig = {
			zoomLevel: 5,
            border: false
        };
		Ext.applyIf(this,defConfig);
		Ext.ux.MapPanel.superclass.initComponent.call(this);
	},

	afterRender : function(){
		var wh = this.ownerCt.getSize();
        Ext.applyIf(this, wh);

		Ext.ux.MapPanel.superclass.afterRender.call(this);
		var mapHandler = MapManager.getMapHandler(this);
		mapHandler.initialize(this.setCenter.lng, this.setCenter.lat, this.zoomLevel);
		mapHandler.showCurrentPositionMarker(this.setCenter.marker.title);
		mapHandler.addMapControl();
	},

	setSize : function(width, height, animate) {
		 Ext.ux.MapPanel.superclass.setSize.call(this, width, height, animate);
	}
});

Ext.reg('mappanel', Ext.ux.MapPanel);