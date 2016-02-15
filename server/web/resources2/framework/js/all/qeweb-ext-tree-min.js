
function navigationMenu(node,event,target){if(isUseBorderLayout()){addTab(node);}
else{var iframeObj=$(parent.document.getElementById(target));var path=node.attributes.path;if(path&&path!='null'){iframeObj.attr("src",appConfig.ctx+actionURL.getRedirect(path));}}}
function updateTreeBO(treeBOStr){var treeDom=XMLDomFactory.getInstance(treeBOStr).find(DISLAND.TREE)
DISLAND.updateBO(treeDom);}