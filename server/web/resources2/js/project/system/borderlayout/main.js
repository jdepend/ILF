/**
 * 首页north部分的js, 包括系统登出/修改密码/系统锁定/首选项等功能
 */
Ext.onReady(function() {
	/**
	 * 系统登出 
	 */
	var logoutButton = new Ext.Button({
		iconCls : 'logout',
		iconAlign : 'center',
		scale : 'medium',
		width : 30,
		tooltip : '&nbsp;' + I18N.SYSTEM_EXIT + '&nbsp;',
		pressed : true,
		arrowAlign : 'right',
		renderTo : 'logoutDiv',
		handler : function() {
			logout();
		}
	});
	
	var mainMenu = new Ext.menu.Menu({
		id : 'mainMenu',
		items : [{
					text : I18N.SYS_MODIFY_PWD,
					iconCls : 'keyIcon',
					handler : function() {
						modifyPwd();
					}
				}, {
					text : I18N.SYS_LOCK,
					iconCls : 'hmenu-lock',
					handler : function() {
						lockWindow.show();
					}
				}]
	});
	
	/**
	 * 首选项
	 */
	var configButton = new Ext.Button({
		text : I18N.SYS_CONFIG,
		iconCls : 'config2',
		iconAlign : 'left',
		scale : 'medium',
		width : 50,
		pressed : true,
		renderTo : 'configDiv',
		menu : mainMenu
	});
	
	/**
	 * 系统锁定
	 */
	var lockForm = new Ext.form.FormPanel({
		labelWidth : 60,
		defaultType : 'textfield',
		labelAlign : 'right',
		bodyStyle : 'padding:10 5 5 5',
		layout : 'form',
		items : [{
					fieldLabel : I18N.SYS_PWD,
					name : 'password',
					inputType : 'password',
					id : 'password_lock',
					allowBlank : false,
					maxLength : 50,
					listeners : {
						specialkey : function(field, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
								unlockSystem();
							}
						}
					},
					anchor : '100%'
				}, {
					xtype : 'panel',
					border : false,
					html : '<div style="font-size:12px;margin-left:10px">' + I18N.SYS_LOCK_TIP + '</div>'
				}]
	});
	
	var lockWindow = new Ext.Window({
		title : I18N.SYS_LOCK,
		iconCls : 'hmenu-lock',
		layout : 'fit',
		width : 320,
		height : 130,
		closeAction : 'hide',
		collapsible : false,
		closable : false,
		maximizable : false,
		border : false,
		modal : true,
		constrain : true,
		animateTarget : Ext.getBody(),
		items : [lockForm],
		listeners : {
			'show' : function(obj) {
				lockForm.form.reset();
				lockForm.findById('password_lock').focus(true, 50);
			}
		},
		buttons : [{
					text : I18N.SYS_UNLOCK,
					iconCls : 'keyIcon',
					handler : function() {
						unlockSystem();
					}
				}, {
					text : I18N.SYS_RELOGIN,
					iconCls : 'redo2',
					handler : function() {
						logout();
					}
				}]
	});
	
	/**
	 * 解锁
	 */
	function unlockSystem() {
		if (!lockForm.form.isValid())
			return;
		
		var sessionTicket = document.getElementById('sessionTicket').value;
		Ext.Ajax.request({
			params : 'password_lock=' + Ext.getCmp('password_lock').getValue() + '&sessionTicket=' + sessionTicket,
			timeout : appConfig.ajaxTimeout,
			url : appConfig.ctx + '/system/unlockSystem.action',
			success : function(response) {
				if (response.responseText == "-1") {
					Ext.Msg.alert(I18N.CONFIRM_TIPS, "会话超时或该用户在其它窗口退出了系统，请重新登录！", function() {
						logout();
					});
				} 
				else if (response.responseText == "1") {
					lockWindow.hide();
				} 
				else if (response.responseText == "0"){
					Ext.Msg.alert(I18N.CONFIRM_TIPS, I18N.SYS_PWD_ERROR_MSG, function() {
						lockForm.form.reset();
						lockForm.findById('password_lock').focus();
					});
				}
				else {
					 //处理窜session问题
					window.location.href = appConfig.ctx + actionURL.getRelogin();
				}
			}
		});
	}
}); 

/**
 * 显示系统时钟
 */
function showTime() {
	document.getElementById('rTime').innerHTML = new Date().toLocaleString();
}

/**
 * 修改密码
 */
function modifyPwd() {
	var sessionTicket = document.getElementById('sessionTicket').value;
	var win = new Ext.Window({
		title : I18N.SYS_MODIFY_PWD,
		maximizable : true,
		collapsible : true,
		width : 400,
		height : 255,
		modal : true,
		buttons : [{
			text : I18N.DIALOG_CLOSE,
			iconCls : 'noIcon',
			height : 25,
			handler : function() {
				win.hide();
			}
		}],
		html : "<iframe src='" + appConfig.ctx 
			+ "/system/modifyPwd.action?load=getUserInfo&sessionTicket=" + sessionTicket  +  "' onload=Ext.getCmp('centerPanel').getEl().unmask() " 
			+ "style='scrollbar:true;' height='100%' width='100%' frameborder='0'></iframe>",
		listeners : {
			'render' : function() {
				Ext.getCmp('centerPanel').getEl().mask('<span style=font-size:12>' + I18N.COMMON_LOADING + '</span>', 'x-mask-loading');
			},
			'close' : function() {
				win.hide();
			}
		} 
	});
	
	win.show(Ext.getBody());
}

function logout() {
	window.location.href = appConfig.ctx + "/system/login!logout.action";
}
