package com.qeweb.framework.pl.ext.mainmenu;

import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.pal.MainMenu;
import com.qeweb.framework.pal.PageContextInfo;
import com.qeweb.framework.pl.JSTemplate;
import com.qeweb.framework.pl.ext.ExtWebHelper;
import com.qeweb.framework.pl.ext.coarsegrained.ExtMenu;
import com.qeweb.framework.pl.ext.coarsegrained.menu.ExtLevelMenu;

public class ExtMainMenu extends MainMenu {
	private ExtMenu menu = new ExtMenu();
	
	@Override
	public void paint() {
		PageContextInfo out = getPageContextInfo();
		out.write("<title>");
		out.write(getTitle());
		out.write("</title>");
		
		out.write(JSTemplate.getJsHead());
		StringBuilder maiinTabs = new StringBuilder();
		maiinTabs.append("var mainTabs = new Ext.TabPanel({");
		maiinTabs.append("enableTabScroll:false,");
		maiinTabs.append("border:false,");
		maiinTabs.append("frame:true,");
		maiinTabs.append("plugins: new Ext.ux.TabCloseMenu()");
		maiinTabs.append("});");
		out.write(maiinTabs.toString());
		
		out.write("Ext.onReady(function(){");
		out.write("var itemArray = [];");
		paintMenu();
		
		StringBuilder sbr = new StringBuilder();
		sbr.append("var viewport = new Ext.Viewport({");
		sbr.append("layout:'border',");
		sbr.append("items:[");
		if (getNorthHeight() > 0) {
			sbr.append("new Ext.Panel({");
			sbr.append("region:'north',");
			sbr.append("contentEl:'north', ");
			ExtWebHelper.appendAttr(sbr, "height", getNorthHeight());
			sbr.append("collapsible:true,");
			sbr.append("border:false,");
			sbr.append("layout: 'fit',");
			sbr.append("title:''}),");
		}
		if (getSouthHeight() > 0) {
			sbr.append("new Ext.BoxComponent({");
			sbr.append("region:'south',");
			sbr.append("contentEl: 'south',");
			ExtWebHelper.appendAttr(sbr, "height", getSouthHeight());
			sbr.append("layout: 'fit',");
			sbr.append("collapsible: true}),");
		}
		
		//风琴样式
		if(menu instanceof ExtLevelMenu) {
			sbr.append(menu.getTreePanelName()).append(",");
		}
		//树形样式
		else {
			sbr.append("{region:'west',");
			ExtWebHelper.appendAttr(sbr, "iconCls", getIcon());
			sbr.append("title:'").append(getText()).append("',");
			sbr.append("split:true,");
			ExtWebHelper.appendAttr(sbr, "width",getWestWidth());
			sbr.append("collapsible: true,");
			sbr.append("autoScroll : true,");
			sbr.append("layout:'fit',");
			sbr.append("items: [").append(menu.getTreePanelName()).append("]");
			sbr.append("},");
		}
		
		sbr.append("{");
		sbr.append("region:'center', ");
		sbr.append("id: 'centerPanel', ");
		sbr.append("iconCls:'',");
		sbr.append("title:'").append(AppLocalization.getLocalization("breadcrumb")).append("',");
		sbr.append("autoScroll:false,");
		sbr.append("layout: 'fit',");
		sbr.append("items:[mainTabs]");
		sbr.append("}]");
		sbr.append("});");
		sbr.append("});");
		out.write(sbr.toString());
		
		paintBreadcrumb();
		paintAddTab();
		
		out.write(JSTemplate.getJsEnd());
	}
	
	/**
	 * 打印左侧菜单新
	 */
	private void paintMenu() {
		menu.setIcon(getIcon());
		menu.setId(getId());
		menu.setBcId(getBcId());
		menu.setMenuType(getMenuType());
		menu.setExpandAll(isExpandAll());
		menu.setBc(getBc());
		menu.setText(getText());
		if(menu instanceof ExtLevelMenu)
			menu.setHeader(true);
		menu.setPageContextInfo(getPageContextInfo());
		menu.paint();
	}
	
	/**
	 * 打印 '拼接 面包屑 函数'
	 * 
	 * function getBreadcrumb(node, pathCh) {
		if (!pathCh)
			pathCh = node.text;
		else
			pathCh = node.text + '->' + pathCh;
		
		if(node.parentNode.isRoot) {
			if(node.ownerTree && && node.ownerTree.ownerCt.firstLevel)
				return node.ownerTree.ownerCt.title + '->' + pathCh;
			else
				return pathCh;
		}
		else {
			return getBreadcrumb(node.parentNode, pathCh);
		}
	}
	 */
	private void paintBreadcrumb() {
		StringBuilder sbr = new StringBuilder();
		sbr.append("function getBreadcrumb(node, pathCh) {");
		sbr.append("if (!pathCh) pathCh = node.text;");
		sbr.append("else pathCh = node.text + '->' + pathCh;");
		sbr.append("if(node.parentNode.isRoot) {");
		sbr.append("if(node.ownerTree && node.ownerTree.ownerCt.firstLevel)");
		sbr.append("return node.ownerTree.ownerCt.title + '->' + pathCh;");
		sbr.append("else return pathCh;}");
		sbr.append("else {return getBreadcrumb(node.parentNode, pathCh);}}");
		
		getPageContextInfo().write(sbr.toString());
	}

	/**
	 * 
	 * 响应树节点单击事件
	function addTab(node, url) {
		if(!node.attributes.leaf)
			return;
		
		
		var name = node.text, 
			menuid = node.id, 
			pathCh = getBreadcrumb(node, ''),
			url = node.attributes.path,
			icon = 'tab_blank.png';

		if (Ext.isEmpty(icon)) {
			icon = 's.gif';
		}
		var id = "tab_id_" + menuid;
		if (url == '#' || url == '') {
			Ext.Msg.alert('提示', '此菜单还没有指定请求地址,无法为您打开页面.');
			return;
		} 
		else {
			var n = mainTabs.getComponent(id);
			if (!n) {
				Ext.getCmp('centerPanel').getEl().mask('<span style=font-size:12>' + I18N.COMMON_LOADING + '</span>', 'x-mask-loading');
				n = mainTabs.add({
							id : id,
							//title : "<img align='top' class='IEPNG' src='./resource/image/ext/" + icon + "'/>" + name,
							title : name,
							closable : true,
							border : false,
							layout : 'fit',
							listeners : {
								'activate' : function() {
									Ext.getCmp('centerPanel').setTitle(pathCh)
								}
							},
							html : "<iframe src='"
									+ appConfig.ctx
									+ actionURL.getRedirect(url)
									+ "' onload=Ext.getCmp('centerPanel').getEl().unmask() style='scrollbar:true;' height='100%' width='100%' frameborder='0'></iframe>"
						});
			}
			mainTabs.setActiveTab(n);
		}
	}
	 */
	private void paintAddTab() {
		StringBuilder sbr = new StringBuilder();
		sbr.append("function addTab(node, url) {");
		sbr.append("if(!node.attributes.leaf) return;");
		sbr.append("var name = node.text,menuid = node.id,pathCh = getBreadcrumb(node, ''),url = node.attributes.path,icon = 'tab_blank.png';");
		sbr.append("if (Ext.isEmpty(icon)) {icon = 's.gif';}");
		sbr.append("var id = 'tab_id_' + menuid;");
		sbr.append("if (url == '#' || url == '') {");
		sbr.append("Ext.Msg.alert('提示', '此菜单还没有指定请求地址,无法为您打开页面.');");
		sbr.append("return;}");
		sbr.append("else {");
		sbr.append("var n = mainTabs.getComponent(id);");
		sbr.append("if (!n) {");
		sbr.append("Ext.getCmp('centerPanel').getEl().mask('<span style=font-size:12>' + I18N.COMMON_LOADING + '</span>', 'x-mask-loading');");
		sbr.append("n = mainTabs.add({");
		sbr.append("id : id,");
		sbr.append("title : name,");
		sbr.append("closable : true,");
		sbr.append("border : false,");
		sbr.append("layout : 'fit',");
		sbr.append("listeners : {'activate' : function() {Ext.getCmp('centerPanel').setTitle(pathCh)}},");
		sbr.append("html : \"<iframe src='\"");
		sbr.append("+ appConfig.ctx + actionURL.getRedirect(url)");
		sbr.append("+ \"' onload=Ext.getCmp('centerPanel').getEl().unmask() ");
		sbr.append("style='scrollbar:true;' height='100%' width='100%' frameborder='0'></iframe>\"");
		sbr.append("});}");
		sbr.append("mainTabs.setActiveTab(n);}}");
		
		getPageContextInfo().write(sbr.toString());
	}
}
