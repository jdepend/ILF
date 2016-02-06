package com.qeweb.framework.pl.ext.coarsegrained;

import java.util.List;

import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.coarsegrained.Sheet;
import com.qeweb.framework.pal.coarsegrained.Tab;
import com.qeweb.framework.pl.ext.ExtWebHelper;

public class ExtTab extends Tab {
	
	/**
	 * 	
	  	var tabs2 = new Ext.TabPanel({
        id:'myTab',
        name:'myTab-Demo_purchaseOrderBO',
        enableTabScroll: true,
        activeTab: 0,
        width:document.body.clientWidth,
        defaults:{autoHeight: true},
        plain:true,
        items:[{
				title: 's1',
				xtype: 'form',
				items:[{colspan:1,rowspan:1,anchor:'98%',
					fieldLabel:'<font color=red>*</font>文本框',
					allowBlank:false,blankText:'必填项',
					viewlabel:'文本框',id:'showAll-DemoBO_ShowAllFC-textField',
					name:'showAll-DemoBO_ShowAllFC-textField',
					xtype:'textfield',vtype:'bopRange',disabled:false,readOnly:false,
					hidden:false
				},{
					colspan:1,rowspan:1,anchor:'98%',fieldLabel:'只读文本域',
					viewlabel:'只读文本域',id:'showAll-DemoBO_ShowAllFC-label',
					name:'showAll-DemoBO_ShowAllFC-label',xtype:'textfield',
					value:'只读区域',cls:'label',style:'padding-top: 5px;',readOnly:'true'
				},new Ext.Button({
					id:'myTab-Demo_purchaseOrderBO-insert',
					name:'myTab-Demo_purchaseOrderBO-insert',
					iconCls:'saveIcon',
					text:'新增'})
				]
			},{
				title:'s2',
				listeners:{ 
					render: sheetId,
					activate : function() {
						addFCListener('" + container.getId() + "');
						addBtnListener('" + container.getId() + "')
					}
				},
				items: [Ext.getCmp('showAll')]
		}]
    });
	 */
	@Override
	public void paint() {
		StringBuilder sbr = new StringBuilder();
		
		List<Sheet> sheetList = getSheetList();
		if(ContainerUtil.isNotNull(sheetList)) {
			for (int i = 0; i < sheetList.size(); i++) {
				paintContainer(sheetList.get(i), i);
			}
		}
		
		//tab的主体
		ExtWebHelper.appendCreateObjectBegin(sbr, getTabId(), "Ext.TabPanel");
		ExtWebHelper.appendObjectBegin(sbr);
		ExtWebHelper.appendAttr(sbr, "id", getId());
		ExtWebHelper.appendAttr(sbr, "name", getName());
		ExtWebHelper.appendObjectAttr(sbr, "width", "document.body.clientWidth");
		// 激活第一个tab页
		ExtWebHelper.appendAttr(sbr, "activeTab", 0);
		ExtWebHelper.appendAttr(sbr, "enableTabScroll", true);
		ExtWebHelper.appendObjectAttr(sbr, "defaults", "{autoHeight:true}");
		ExtWebHelper.appendContent(sbr, "items: [");
		int i = 0;
		for(Sheet sheet : getSheetList()) {		
			ExtWebHelper.appendObjectBegin(sbr);
			ExtWebHelper.appendAttr(sbr, "title", sheet.getText());
			if(sheet.getContainerList().isEmpty())
				ExtWebHelper.appendAttr(sbr, "xtype", "form");
			//切换tab页时，细粒度组件追加事件
			if(i == 0) {
				ExtWebHelper.appendObjectTail(sbr, "listeners", "{render: " + sheet.getId() + "}");
			}
			else {
				sbr.append("listeners : {render : ").append(sheet.getId()).append(",");
				sbr.append("activate : ").append(getActivate(sheet)).append("}");
			}

			ExtWebHelper.appendObjectAfter(sbr);
			getPageContextInfo().write(sbr.toString());
			if(i < getSheetList().size() - 1)
				getPageContextInfo().write(",");
				
			sbr = new StringBuilder();
			i++;
		}
		ExtWebHelper.appendArrayAfter(sbr);
		
		ExtWebHelper.appendObjectAfter(sbr);
		ExtWebHelper.appendCreateObjectEnd(sbr);
		ExtWebHelper.appendSingleEnd(sbr);
		sbr.append("itemArray.push(").append(getTabId()).append(");");
		
		getPageContextInfo().write(sbr.toString());
	}

	/**
	 * 画粗粒度
	 * @param sheet
	 * @param sheetIndex	sheet的序号
	 */
	private void paintContainer(Sheet sheet, int sheetIndex) {
		getPageContextInfo().write("function " + sheet.getId() + "(){");
		for(Container container : sheet.getContainerList()) {
			container.paint();
			getPageContextInfo().write("this.add(Ext.getCmp('" + container.getId() + "'));");
		}
		if(sheetIndex == 0)
			getPageContextInfo().write("onload('" + sheet.getId() + "');");
		
		getPageContextInfo().write("}");
	}
	
	/**
	 * 当面板视觉上取消活动后触发的行为.
	 */
	private String getActivate(Sheet sheet) {
		StringBuilder sbr = new StringBuilder();
		sbr.append("function(){");
		for(Container container : sheet.getContainerList()) {
			sbr.append("addFCListener('" + container.getId() + "');addBtnListener('" + container.getId() + "');");
		}
		sbr.append("onload('" + sheet.getId() + "');}");
		
		return sbr.toString();
	}

	/**
	 * 
	 */
	public String getTabId() {
		return getId() + "_tab";
	}
}
