/**
 * 保存查询条件
 * 
 * @param saveCaseBtn
 */

var oldId = "";
var oldCaseName = "";
var saveCaseWin = null;
function sysSaveCase(saveBtnEvent) {
	if(saveCaseWin == null) {
		saveCaseWin = new Ext.Window({
			width : projectStyle.getSaveCaseWinWidth(),
			height : projectStyle.getSaveCaseWinHeight(),
			modal : true,
			title : I18N.SYS_SAVECASE_TITLE,
			layout : 'fit',
			closeAction : 'hide',
			maximizable : true,
			collapsible : true,
			items : new Ext.FormPanel({
				bodyStyle:{padding:'30'},
				labelWidth : 100,
				border : false,
				labelAlign : 'right',
				items : [{
					id : 'saveCaseWin-input',
					xtype : 'textfield',
					anchor : '98%',
					fieldLabel : '<font color=red>*</font> ' + I18N.SYS_SAVECASE_IPT,
					allowBlank : false,
					maxLength : 50
				}]
			}),

			buttons : [ 
				{
					xtype : 'checkbox',
					id : 'saveForNewCase',
					height : 27
				},
				{
					xtype : 'label',
					id : 'saveForNewCase_label',
					html : I18N.SYS_SAVECASE_LABEL
				},
			    {
					text : I18N.DIALOG_YES,
					iconCls : 'yesIcon',
					handler : function() {
						var caseName = Ext.getCmp('saveCaseWin-input');
						if(!caseName.validateValue(caseName.getValue())) 
							return;
						
						var bo = DISLAND.getBODom(saveBtnEvent.containerDom.getId()).slice(0);
						var data = paramValue(xmlToString(bo));
						var isNewCase = Ext.getCmp('saveForNewCase').getValue();
						//如果保存为新条件,清空oldId
						if(isNewCase) {
							oldId = "";
							oldCaseName = "";
						}
						else if(!!oldCaseName) {
							oldCaseName = caseName.getValue();
						}
						
						Ext.Ajax.request({
							url : appConfig.ctx + actionURL.saveCase(),
							method : "POST",
							params : saveCase.sourcePage + DISLAND.getSourcepage() +
								param(saveCase.caseName) + encodeURIComponent(caseName.getValue()) +
								param(saveCase.containerId) + saveBtnEvent.containerDom.getId() +
								param(saveCase.saveForNewCase) + isNewCase + 
								param(saveCase.oldId) + oldId +
								param(saveCase.dataIsland) + data,
							timeout : appConfig.ajaxTimeout,
	
							success : function(response, options) {
								//如果窜session，跳转到登录后的第一个页面
								validateSession(response.responseText);
								//后续操作
								var resultMsg = new ResultMsg(response.responseText);
								//操作成功
								if(resultMsg.success) {
									ResultMessage.showMsg(resultMsg.success, I18N.MSG_OPERATION_SUCCESS, true, false);
								}
								//操作失败, 打印错误提示
								else {
									if(!resultMsg.msg || resultMsg.msg == 'null')
										resultMsg.msg = I18N.MSG_OPERATION_FAILURE;
									
									ResultMessage.showMsg(resultMsg.success, resultMsg.msg, true, false);
								}
							},
							failure : function(response) {
								showMSG.showErr(I18N.MSG_AJAX_FAILURE);
							}
						});

						saveCaseWin.hide();
					}
				}
			]
		});
		
		//是否默认带有关闭按钮
		if(appConfig.hasCloseBtn) {
			saveCaseWin.addButton({
				text : I18N.DIALOG_CLOSE,
				iconCls : 'noIcon',
				handler : function() {
					saveCaseWin.hide();
				}
			} );
		}
	}
	
	Ext.getCmp('saveCaseWin-input').reset();
	//如果查询条件是从列表中选择的, 可勾选"保存为新条件", 否则隐藏"保存为新条件"
	if(!!oldCaseName) {
		Ext.getCmp('saveCaseWin-input').setValue(oldCaseName);
		Ext.getCmp('saveForNewCase_label').show();
		Ext.getCmp('saveForNewCase').show();
	}
	else {
		Ext.getCmp('saveForNewCase_label').hide();
		Ext.getCmp('saveForNewCase').hide();
	}
	
	saveCaseWin.show(Ext.getBody());

	// 职责链不向下流转
	return false;
}

/**
 * 使用查询条件
 * 
 * @param openCaseBtn
 */
var openCaseWin = null;
var openCaseStore = null;
function sysOpenCase(openCaseBtn) {
	if(openCaseWin == null) {
		openCaseStore = new Ext.data.JsonStore({
			url : appConfig.ctx + actionURL.findQueryCase() + param(saveCase.sourcePage) + DISLAND.getSourcepage(),
			root : 'data',
			autoLoad : true,
			fields : [ 
			          {name : 'id'},
			          {name : 'caseName'},
			          {name : 'disland'}
			]
		});
		openCaseWin = new Ext.Window({
			width : projectStyle.getUseCaseWinWidth(),
			height : projectStyle.getUseCaseWinHeight(),
			modal : true,
			title : I18N.SYS_OPENCASE_TITLE,
			layout : 'fit',
			closeAction : 'hide',
			maximizable : true,
			collapsible : true,
			items : [ new Ext.grid.GridPanel({
				id : 'openCaseGrid',
				ds : openCaseStore,
				loadMask : true,
				stripeRows : true,

				sm : new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn}),
				cm : new Ext.grid.ColumnModel({
					columns : [
						new Ext.grid.CheckboxSelectionModel({handleMouseDown : Ext.emptyFn}), 
						new Ext.grid.RowNumberer(),
						{
							dataIndex : 'id',
							hidden : true,
							hideable : false
						},
						{
							header : I18N.SYS_SAVECASE_IPT,
							sortable : true,
							dataIndex : 'caseName',
							width : 200,
							fixed : true,
							menuDisabled : true,
							renderer : function(value, cell){
								return decodeToValue(value);
							}
						},
						{
							dataIndex : 'disland',
							hidden : true,
							hideable : false
						}
					]
				}),
				tbar : [
					new Ext.Button({
						iconCls : 'remove',
						text : I18N.BTN_DEL,
						handler : function() {
							var container = Ext.getCmp('openCaseGrid');
							var records = container.getSelectionModel().getSelections();
							if(records == 0) {
								alertMsg.choseRecord(I18N.SYS_CAVECASE_IPT);
								return;
							}
							
							var ids = [];
							for(var i = 0; i < records.length; i++) {
								ids.push(records[i].data['id']);
							}
							//清空oldId
							if(ids.exist(oldId)) {
								oldId = "";
								oldCaseName = "";
							}
							
							Ext.Ajax.request({
								url : appConfig.ctx + actionURL.delCase(),
								method : "POST",
								params : "ids=" + ids.join(','),
								timeout : appConfig.ajaxTimeout,
	
								success : function(response, options) {
									//如果窜session，跳转到登录后的第一个页面
									validateSession(response.responseText);
									
									openCaseStore.reload();
								},
								failure : function(response) {
									showMSG.showErr(I18N.MSG_AJAX_FAILURE);
								}
							});
							
							openCaseStore.load();
						}
					})
				],
				
				listeners : {
					'rowdblclick' : function(grid, rowIndex) {
						var recordData = grid.store.data.items[rowIndex].data;
						var boDom = XMLDomFactory.getInstance(recordData['disland']);
						oldId = recordData['id'];
						oldCaseName = recordData['caseName'];
						var formHandler = new FormHandler();
						formHandler.freshMyself(boDom);
						
						//自动点击查询按钮
						if(appConfig.isAutoSearch) {
							var queryDom = boDom.find(DISLAND.OPERATE + "[" + DISLAND.OPERATE_METHOD + "='" + BoFinalMethod.QUERY + "']");
							Ext.getCmp(queryDom.attr(DISLAND.OPERATE_ID)).fireEvent("click");
						}
						
						openCaseWin.hide();
					}
				} 
			}) ]
		});
		
		//是否默认带有关闭按钮
		if(appConfig.hasCloseBtn) {
			openCaseWin.addButton({
				text : I18N.DIALOG_CLOSE,
				iconCls : 'noIcon',
				handler : function() {
					openCaseWin.hide();
				}
			} );
		}
	}
	
	openCaseStore.load();
	openCaseWin.show(Ext.getBody());

	// 职责链不向下流转
	return false;
};
