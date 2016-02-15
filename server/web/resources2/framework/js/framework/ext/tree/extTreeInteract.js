
/**
 * 菜单导航
 * @param  node 树节点
 * @param  event
 * @param  targetName
 */
function navigationMenu(node, event, target) {
	if(isUseBorderLayout()) {
		addTab(node);
	}
	else {
		var iframeObj = $(parent.document.getElementById(target));
		var path = node.attributes.path;
		if(path && path != 'null'){
			iframeObj.attr("src", appConfig.ctx + actionURL.getRedirect(path));
		}	
	}
}

/**
 * 用treeBO替换数据岛中的treeBO
 * @param treeBOStr
 */
function updateTreeBO(treeBOStr){
	var treeDom = XMLDomFactory.getInstance(treeBOStr).find(DISLAND.TREE)
	DISLAND.updateBO(treeDom);
}

/**
 * 在点击topTree节点时, 保存节点信息
 * @param a 节点所在的超链接标签
 * @param nodeId
 */
function saveTopTreeMsg(a, nodeId) {
	$a = $(a);
	$.ajax({
		type : "POST",
		url : appConfig.ctx + actionURL.saveTopMenuNode(),
		async : false,
		timeout : appConfig.ajaxTimeout,
		data : {
			"nodeId" : nodeId,
			"nodeName" : $a.text()
		},
		success : function() {
			$(".selected").attr("class", "default");
			$a.attr('href', appConfig.ctx + "/system/menu_frame_top.action");
			$a.attr("class", "selected");
		}
	});
}
