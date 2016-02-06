package com.qeweb.framework.pl.ext.finegrained.enumcomp;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.qeweb.framework.bc.prop.TreeRange;
import com.qeweb.framework.bc.prop.Value;
import com.qeweb.framework.common.constant.ConstantDataIsland;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.Schema;
import com.qeweb.framework.pal.finegrained.enumcomp.Select;
import com.qeweb.framework.pl.ext.ExtWebHelper;
import com.qeweb.framework.pl.ext.NodeComparator;

public class ExtSelect extends Select {

	@Override
	public void paint() {
		StringBuilder sbr = new StringBuilder();
		
		ExtWebHelper.appendSingleValueHead(sbr, "fieldLabel", this);
		ExtWebHelper.appendAttr(sbr, "viewlabel", getText());
		ExtWebHelper.appendAttr(sbr, "id", getId());
		ExtWebHelper.appendAttr(sbr, "name", getName());
		ExtWebHelper.appendAttr(sbr, "xtype", "combo");
		Value value = getValue();
		ExtWebHelper.appendAttr(sbr, "value", value.getValue());
		if(getHistoryBC() != null)
			ExtWebHelper.appendAttr(sbr, "historyText", getHistoryBC().toText()); 
		ExtWebHelper.appendAttr(sbr, "valueField", "valueField");
		ExtWebHelper.appendAttr(sbr, "displayField", "displayField");
		ExtWebHelper.appendAttr(sbr, "mode", "local");
		ExtWebHelper.appendAttr(sbr, "editable ", false);
		String emptyText = getBc().getValue().getEmptyValue();
		if(StringUtils.isEmpty(emptyText))
			emptyText = AppLocalization.getLocalization("fc.select.emptyText");
		ExtWebHelper.appendAttr(sbr, "emptyText", emptyText);
		Schema schema = getSchema();
		if(schema != null) {
			ExtWebHelper.appendAttr(sbr, "labelStyle", schema.getStyle());
		}
		else if(getStyle() != null) {
			ExtWebHelper.appendAttr(sbr, "labelStyle", getStyle());
		}
		ExtWebHelper.appendAttr(sbr, "triggerAction", "all");
		
		//普通下拉框
		if(!isDeptTree()) {
			Map<String, String> dataMap = getBc().toMap();
			StringBuffer store = new StringBuffer("new Ext.data.SimpleStore({");
			store.append("fields: ['valueField','displayField'],");
			store.append("data: ");
			store.append(ExtWebHelper.getSimpleDataStr(dataMap));
			store.append("})");
			ExtWebHelper.appendObjectAttr(sbr, "store", store.toString());
		}
		//下拉树
		else {
			ExtWebHelper.appendObjectAttr(sbr, "onSelect", "Ext.emptyFn");
			ExtWebHelper.appendObjectAttr(sbr, "store", "new Ext.data.SimpleStore({fields : ['valueField','displayField'],data : [[]]})");
			// 下拉框的显示模板,addDeptTreeDiv作为显示下拉树的容器
			ExtWebHelper.appendAttr(sbr, "tpl", "<tpl for=\".\"><div style=\"height:390px\"><div id=\"" + getDeptTreeDivName() + "\"></div></div></tpl>");
			/*
			listeners : {
				'expand' : function() {
					this.store = new Ext.data.SimpleStore({
						fields : ['valueField','displayField'],
						data : [ [] ]});
					// 将UI树挂到treeDiv容器
					deptTree.render('deptTreeDiv');
					// addDeptTree.root.expand(); //只是第一次下拉会加载数据
					deptTree.root
							.reload(); // 每次下拉都会加载数据
					deptTree.expandAll();
				}
			}
			*/
			sbr.append("listeners : {'expand' : function() {").append("this.store = new Ext.data.SimpleStore({fields : ['valueField','displayField'],data : [[]]});")
				.append(getDeptTreeName()).append(".render('").append(getDeptTreeDivName()).append("');");
			sbr.append(getDeptTreeName()).append(".root.reload();");
			sbr.append(getDeptTreeName()).append(".expandAll();");
			sbr.append("}}");
		}
		
		ExtWebHelper.appendStatus(sbr, getStatus());

		getPageContextInfo().write(ExtWebHelper.removeEnd(sbr));
	}

	/**
	 * 画出下拉树,
	 * paintDeptTree 在 select 所在的粗粒度组件中调用
	 * var addDeptTree = new Ext.tree.TreePanel({
						root : addRoot,
						autoScroll : true,
						animate : false,
						useArrows : false,
						border : false,
						listeners : {
									'click' : function(node) {
										var combox = Ext.getCmp('fcId');
										var newData = [];
										newData.push([node.id, node.text]);
										combox.getStore().loadData(newData);
										combox.setValue(node.id);
										combox.collapse();
									}
								}
					});
	 */
	@Override
	public void paintDeptTree() {
		TreeRange treeRange = this.getBc().getRange().getTreeRange();
    	
		StringBuilder sbr = new StringBuilder();
		ExtWebHelper.appendCreateObjectBegin(sbr, getDeptTreeName(), "Ext.tree.TreePanel");
		ExtWebHelper.appendObjectBegin(sbr);
		ExtWebHelper.appendObjectAttr(sbr, "root",
				"new Ext.tree.AsyncTreeNode({text:" + "'" + treeRange.getRootText() + "',"
				+ "id : '" +  treeRange.getRootValue() + "',"
				+ "expanded:true,children:"
						+ JSONArray.fromObject(createDeptTree(treeRange.getRootValue(), treeRange.create())).toString() + "})");
		ExtWebHelper.appendObjectAttr(sbr, "loader", "new Ext.tree.TreeLoader()");
		ExtWebHelper.appendAttr(sbr, "autoScroll", true);
		ExtWebHelper.appendAttr(sbr, "animate", false);
		ExtWebHelper.appendAttr(sbr, "useArrows", false);
		ExtWebHelper.appendAttr(sbr, "border", false);
		sbr.append("listeners : {'click' : function(node) {var combox = Ext.getCmp('").append(getId()).append("');"
				+ "var newData = [];newData.push([node.id, node.text]);combox.getStore().loadData(newData);"
				+ "combox.setValue(node.id);combox.collapse();}}");
		ExtWebHelper.appendObjectAfter(sbr);
		ExtWebHelper.appendCreateObjectEnd(sbr);
		sbr.append(";");
		getPageContextInfo().write(sbr.toString());
	}
	
	
	/**
     * 创建节点
     * 将TreeRange中的节点存储为List结构，
     * @param value	父节点value
     * @param nodes
     * @return
     */
    private List<Map<String, Object>> createDeptTree(String value, List<TreeRange.Node> nodes) {
    	List<Map<String, Object>> result = new LinkedList<Map<String, Object>>();
    	if(ContainerUtil.isNull(nodes))
    		return result;
    	
		for (TreeRange.Node node : nodes) {
			if (StringUtils.isNotEqual(node.getParentValue(), value))
				continue;

			Map<String, Object> nodeMap = new HashMap<String, Object>();
			nodeMap.put("id", node.getValue());
			nodeMap.put("text", node.getText());
			nodeMap.put("sort", node.getSortIdx());

			List<Map<String, Object>> children = createDeptTree(node.getValue(), nodes);

			if (ContainerUtil.isNull(children))
				nodeMap.put("leaf", true);
			else
				nodeMap.put("children", children);
			result.add(nodeMap);
		}

        Collections.sort(result, new NodeComparator());

        return result;
    }
	
	private String getDeptTreeDivName() {
		return getDeptTreeName() + ConstantDataIsland.COMPONENTSPLIT + "div";
	}
}
