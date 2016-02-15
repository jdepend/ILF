var CheckboxHelper = {
	resize : function(checkbox) {
		// 解决非ie浏览器，checkbox垂直展示时，resize计算的高度不够，展示不全
		if(!$.browser.msie) {
			var offset = 18;
			if(!checkbox.panel)
				return;
			var height = checkbox.panel.getHeight() + offset;
			checkbox.panel.setHeight(height);
		}
	}
};