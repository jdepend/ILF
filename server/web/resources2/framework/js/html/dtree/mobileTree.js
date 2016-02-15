function mobileTree(objName){
	dTree.apply(this, [objName]);	
	this.root = new Node(0);
}
mobileTree.prototype=new dTree();

//Creates the tree structure
mobileTree.prototype.addNode = function(pNode) {
	var str = '<div id=s' + this.obj + pNode.id + '>';
	str += '<ul class="rounded">';
	var n=0;
	if (this.config.inOrder) n = pNode._ai;
	for (n; n<this.aNodes.length; n++) {
		if (this.aNodes[n].pid == pNode.id) {
			var cn = this.aNodes[n];
			cn._p = pNode;
			cn._ai = n;
			this.setCS(cn);
			if (!cn.target && this.config.target) cn.target = this.config.target;
			if (cn._hc && !cn._io && this.config.useCookies) cn._io = this.isOpen(cn.id);
			if (!this.config.folderLinks && cn._hc) cn.url = null;
			if (this.config.useSelection && cn.id == this.selectedNode && !this.selectedFound) {
					cn._is = true;
					this.selectedNode = n;
					this.selectedFound = true;
			}
			str += this.node(cn, n);
			if (cn._ls) break;
		}
	}
	str += '</ul></div>';
	return str;
};
//Creates the node icon, url and text
mobileTree.prototype.node = function(node, nodeId) {
	var str = '<li '+ (node._hc? 'class="arrow"' : '')+'>' 
	if (node._hc) {
		str += '<a href="javascript: ' + this.obj + '.o(' + node.id + ');" class="node">';
	}
	else if(node.url != undefined && node.url != null){
		str += '<a href="' + appConfig.ctx + actionURL.getRedirect() + node.url + '" class="node">';
	}
	
	str += node.name;
	if (node._hc || node.url) {
		 str += '</a>';
	}
	else if(node.url != undefined && node.url != null){
		str += '</a>';
	}
	
	str += '</li>';
	this.aIndent.pop();
	return str;
}

//Outputs the tree to the page
mobileTree.prototype.toString = function() {
	var str = '<div id="mobileTree" class="mobileTree">\n';
	if (document.getElementById) {
		if (this.config.useCookies) this.selectedNode = this.getSelected();
		str += this.addNode(this.root);
	} 
	else str += 'Browser not supported.';
	str += '</div>';
	if (!this.selectedFound) this.selectedNode = null;
	this.completed = true;
	return str;
};

//Toggle Open or close
mobileTree.prototype.o = function(id) {
	var cn;
	for ( var int = 0; int < this.aNodes.length; int++) {
		if (this.aNodes[int].id == id) {
			cn = this.aNodes[int];
		}
	}
	if (this.config.closeSameLevel) this.closeLevel(cn);
	if (this.config.useCookies) this.updateCookie();
	if (cn._hc) {
		var tree = document.getElementById("mobileTree");
		tree.innerHTML = this.addNode(cn)
	}
	
};
