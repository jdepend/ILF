/**
 * 当Viewport宽度发生变化时, 重新计算粗粒度组件的宽度, 以便组件大小, 使之自适应
 * 
 * 注：由于未使用Viewport, 该函数暂未使用
 * 
 * @param panel viewport中的顶级panel
 * @param 布局管理器划分的总列数
 */
function resizeContainer(panel, columns) {
	var containers = panel.items.items[0].items.items;
	var clientWidth = document.body.clientWidth;

	//纵向滚动条宽度
	var scrollBarWidth = 16;
	var sumColspan = 0;
	var padding = 10;
	
	for(var i = 0; i < containers.length; i++) {
		sumColspan += containers[i].colspan;
		var con = containers[i].items.items[0];
		
		//菜单类型
		if(con.xtype == 'menu') {
			con.setWidth(clientWidth * containers[i].columnWidth);
		}
		//一个组件占据一行
		else if(containers[i].colspan >= columns) {
			con.setWidth(clientWidth * containers[i].columnWidth - scrollBarWidth);
			sumColspan = 0;
		}
		//组件在最后一列
		else if(sumColspan == columns) {
			con.setWidth(clientWidth * containers[i].columnWidth - scrollBarWidth);
			sumColspan = 0;
		}
		else {
			con.setWidth(clientWidth * containers[i].columnWidth - padding);
		}
	}
}