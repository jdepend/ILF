package com.qeweb.framework.pl.ext.coarsegrained.menu;

import net.sf.json.JSONArray;

import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.coarsegrained.Menu;
import com.qeweb.framework.pl.ext.ExtWebHelper;
import com.qeweb.framework.pl.ext.coarsegrained.ExtMenu;

public class ExtSimpleMenu extends ExtMenu {
	public void paint() {
		StringBuilder sbr = new StringBuilder();
		/*
		 * demoMenuBO_demoMenuBO = function() {
						demoMenuBO_demoMenuBO.superclass.constructor.call(this, {
					        id:'api-tree',
					        split:true,
					        header: false,
					        autoScroll : true,
					        xtype: menu,
					        collapsible: true,
					        rootVisible:false,
					        lines:false,
					        animCollapse:false,
					        animate: false,
					        border : false,
					        target : 'iframeMain',
					        collapseMode:'mini',
					        loader: new Ext.tree.TreeLoader({
								preloadChildren: true,
								clearOnLoad: false
							}),
							root : new Ext.tree.AsyncTreeNode(
									{
										expanded : true,
										children : [ {
											"id" : 100,
											"text" : "示例",
											"sort" : 0,
											"children" : [
													{
														"id" : 1001,
														"text" : "装载",
														"sort" : 1,
														"children" : [
																{
																	"id" : 100101,
																	"text" : "表单",
																	"sort" : 1,
																	"path" : "/WEB-INF/pages/demo/load/container/form/demoForm.jsp?pathType=menu",
																	"leaf" : true
																}]
										} ]
									}),
					        collapseFirst:false
					    });
					    this.getSelectionModel().on('beforeselect', function(sm, node){
					        return node.isLeaf();
					    });
					};
		 */
		sbr.append(getTreePanelName()).append(" = function() {");
		sbr.append(getTreePanelName()).append(".superclass.constructor.call(this, {");
		ExtWebHelper.appendAttr(sbr, "id", getId());
		ExtWebHelper.appendAttr(sbr, "split", true);
		ExtWebHelper.appendAttr(sbr, "xtype", "menu");
		ExtWebHelper.appendAttr(sbr, "header", isHeader());
		ExtWebHelper.appendAttr(sbr, "autoScroll", true);
		ExtWebHelper.appendAttr(sbr, "title", getText());
		ExtWebHelper.appendAttr(sbr, "iconCls", getIcon());
		ExtWebHelper.appendObjectAttr(sbr, "height", "Ext.getBody().getHeight() - 8");
		ExtWebHelper.appendAttr(sbr, "collapsible", true);
		ExtWebHelper.appendAttr(sbr, "rootVisible", false);
		ExtWebHelper.appendAttr(sbr, "lines", false);
		ExtWebHelper.appendAttr(sbr, "animCollapse", false);
		ExtWebHelper.appendAttr(sbr, "border", false);
		ExtWebHelper.appendAttr(sbr, "animate", false);
		ExtWebHelper.appendAttr(sbr, "target", ((Menu) this).getTarget());
		ExtWebHelper.appendAttr(sbr, "collapseMode", "mini");
		ExtWebHelper.appendObjectAttr(sbr, "loader", "new Ext.tree.TreeLoader({preloadChildren: true,clearOnLoad: false})");
		ExtWebHelper.appendObjectAttr(sbr, "listeners", "{'click' : function(node, event) {navigationMenu(node, event, '" + getTarget() + "');}}");
		ExtWebHelper.appendObjectAttr(sbr, "root", "new Ext.tree.AsyncTreeNode({expanded:true,children:" + JSONArray.fromObject(createTree(this)).toString() + "})");
		ExtWebHelper.appendTail(sbr, "collapseFirst", false);
		ExtWebHelper.appendObjectAfter(sbr);
		ExtWebHelper.appendCreateObjectEnd(sbr);
		sbr.append(";");
		sbr.append("this.getSelectionModel().on('beforeselect', function(sm, node){return node.isLeaf();});");
		sbr.append("};");
		
		/*
		 * Ext.extend(demoMenuBO_demoMenuBO, Ext.tree.TreePanel, {
					    initComponent: function(){
					        this.hiddenPkgs = [];
					        Ext.apply(this, {
					        	listeners : {
									'load' : function() {
										this.expandAll();
									}
								},
					            tbar:[ ' ',
								new Ext.form.TextField({
									width: 150,
									emptyText:'查找节点',
					                enableKeyEvents: true,
									listeners:{
										render: function(f){
					                    	this.filter = new Ext.tree.TreeFilter(this, {
					                    		clearBlank: true,
					                    		autoClear: true
					                    	});
										},
					                    keydown: {
					                        fn: this.filterTree,
					                        buffer: 350,
					                        scope: this
					                    },
					                    scope: this
									}
								}), ' ', ' ',
								{
					                iconCls: 'icon-expand-all',
									tooltip: '全部展开',
					                handler: function(){ this.root.expand(true); },
					                scope: this
					            }, '-', {
					                iconCls: 'icon-collapse-all',
					                tooltip: '全部收缩',
					                handler: function(){ this.root.collapse(true); },
					                scope: this
					            }]
					        })
					        demoMenuBO_demoMenuBO.superclass.initComponent.call(this);
					    },
					    filterTree: function(t, e){
							var text = t.getValue();
							Ext.each(this.hiddenPkgs, function(n){
								n.ui.show();
							});
							if(!text){
								this.filter.clear();
								return;
							}
							this.expandAll();
							
							var re = new RegExp(Ext.escapeRe(text), 'i');
							this.filter.filterBy(function(n){
								return !n.isLeaf() || re.test(n.text);
							});
							
							// hide empty packages that weren't filtered
							this.hiddenPkgs = [];
					        var me = this;
							this.root.cascade(function(n){
								if(!n.isLeaf() && n.ui.ctNode.offsetHeight < 3){
									n.ui.hide();
									me.hiddenPkgs.push(n);
								}
							});
						}
					   
					});
		 */
		sbr.append("Ext.extend(").append(getTreePanelName()).append(", Ext.tree.TreePanel, {");
		sbr.append("initComponent: function(){");
		sbr.append("this.hiddenPkgs = [];");
		sbr.append("Ext.apply(this, {");
		if(isExpandAll()) 
			ExtWebHelper.appendObjectAttr(sbr, "listeners", "{'load':function(node){node.expand(true);}}");
		sbr.append("tbar:[ ' ',");
		sbr.append("new Ext.form.TextField({");
		sbr.append("width: 150,");
		sbr.append("emptyText:'").append(AppLocalization.getLocalization("tree.emptyText")).append("',");
		sbr.append("enableKeyEvents: true,");
		sbr.append("listeners:{");
		sbr.append("render: function(f){");
		sbr.append("this.filter = new Ext.tree.TreeFilter(this, {");
		sbr.append("clearBlank: true,autoClear: true});},");
		sbr.append("keydown: {");
		sbr.append("fn: this.filterTree,buffer: 350,scope: this},");
		sbr.append("scope: this");
		sbr.append("}");
		sbr.append("}), ' ', ' ',");
		sbr.append("{");
		sbr.append("iconCls: 'icon-expand-all',");
		sbr.append("tooltip: '").append(AppLocalization.getLocalization("tree.expandAll")).append("',");
		sbr.append("handler: function(){ this.root.expand(true); },");
		sbr.append("scope: this");
		sbr.append("}, '-', {");
		sbr.append("iconCls: 'icon-collapse-all',");
		sbr.append("tooltip: '").append(AppLocalization.getLocalization("tree.collapseAll")).append("',");
		sbr.append("handler: function(){ this.root.collapse(true); },");
		sbr.append("scope: this");
		sbr.append(" }]");
		sbr.append("}),");
		sbr.append(getTreePanelName()).append(".superclass.initComponent.call(this);");
		sbr.append("},");
		sbr.append("filterTree: function(t, e){");
		sbr.append("var text = t.getValue();");
		sbr.append("Ext.each(this.hiddenPkgs, function(n){n.ui.show();});");
		sbr.append("if(!text){this.filter.clear();return;}");
		sbr.append("this.expandAll();");
		sbr.append("var re = new RegExp(Ext.escapeRe(text), 'i');");
		sbr.append("this.filter.filterBy(function(n){return !n.isLeaf() || re.test(n.text);});");
		sbr.append("this.hiddenPkgs = [];");
		sbr.append("var me = this;");
		sbr.append("this.root.cascade(function(n){");
		sbr.append("if(!n.isLeaf() && n.ui.ctNode.offsetHeight < 3){");
		sbr.append("n.ui.hide();me.hiddenPkgs.push(n);}");
		sbr.append("});");
		sbr.append("}");
		sbr.append("});");
		
		/*
		 *  var demoMenuBO_demoMenuBO = new demoMenuBO_demoMenuBO();
			itemArray.push(demoMenuBO_demoMenuBO);
		 */
		sbr.append(" var ").append(getTreePanelName()).append(" = new ").append(getTreePanelName()).append("();");
		sbr.append("itemArray.push(").append(getTreePanelName()).append(");");
		
		getPageContextInfo().write(sbr.toString());
	}
	
	@Override
	public String getText() {
		//此处仅判断text != null, 如果text == "", 表示强制不使用fieldLabel
		if(text != null)
			text = AppLocalization.getLocalization(text);
		else if(getBc() != null)
			text = getLocalName(); 
		else 
			text = "";
		
		return StringUtils.getUnescapedText(text);
	}
}
