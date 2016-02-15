
var showMSG = {
	display_status_check : 'qeweb_display_status',
	
	/**
	 * 展示操作结果提示信息(alert提示信息)
	 * @param message 	提示信息
	 * @param icon	  	提示信息图标类型
	 * @param width   	提示信息框长度
	 * @param isNodAdd  是否强制展示提示信息, 当出现错误提示时, isNotAdd应当强制设置为true
	 */
	showMsg : function(message, icon, width, isNotAdd) {
		unlockScreen();
		var msgWidth = showMSG.getMsgWidth(message);
		
		if(Ext.Msg.YES != icon)
			msg_check = '';
		
		Ext.Msg.show({
			title : I18N.CONFIRM_TIPS,
			msg : showMSG.getMsg(message, isNotAdd),
			buttons : Ext.Msg.OK,
			icon : icon,
			fn : displayStatus.changeAlertStatus,
			width : width || msgWidth
		});
	},
	
	/**
	 * 提示信息框长度
	 */
	getMsgWidth : function(message, width){
		var msgWidth = (width ? width : 240);
		if(!!message && message.length > 9)
			msgWidth = 22 * message.length;
		return msgWidth > 500 ? 500 : msgWidth;
	},
	
	getMsg : function(message, isNotAdd){
		if(isNotAdd)
			return message;
		
		var msg_check = "";
		//记忆操作(弹出确认信息和提示信息)
		if(appConfig.optRemember == '1') {
			msg_check = "<br><br><span style='text-align:center;'><input id='" 
					+ showMSG.display_status_check
					+ "' type='checkbox'/>&nbsp;"
					+ I18N.CONFIRM_PROCESS_PROMPT
					+ "</span>";
		}
		
		return message + msg_check;
	},
	
	showOK : function(message, width) {
		this.showMsg(message, Ext.Msg.YES, width);
	},
	
	showWaring : function(message, width) {
		this.showMsg(message, Ext.Msg.WARNING, width, true);
	},
	
	showErr : function(message, width) {
		this.showMsg(message, Ext.Msg.ERROR, width, true);
	}
};

var alertMsg = {
	choseRecord : function(title) {
		if(!!title)
			Ext.Msg.alert(I18N.CONFIRM_TIPS, I18N.ALERT_CHOOSE_BEFORE + title + I18N.ALERT_CHOOSE_AFTER);
		else 
			Ext.Msg.alert(I18N.CONFIRM_TIPS, I18N.ALERT_CHOOSE_RECORD);
	},
	
	modify : function(title) {
		if(!!title)
			Ext.Msg.alert(I18N.CONFIRM_TIPS, title + I18N.ALERT_MODIFIED_AFTER);
		else 
			Ext.Msg.alert(I18N.CONFIRM_TIPS, I18N.ALERT_MODIFIED_RECORD);
	}
};

/**
 * 修改个人设置(操作结果提示信息和confirm信息是否不再显示)
 */
var displayStatus = {
	
	/**
	 * 设置confirm信息是否不再显示
	 */
	changeConfirmStatus : function(){
		var check = document.getElementById(showMSG.display_status_check)
		if(!check || !check.checked)
			return;

		Ext.Ajax.request({
			url : appConfig.ctx + actionURL.setConfirmStatus(),
			method : "POST",
			success : function(response){
				var pageDom = DISLAND.getDataIsland().children(DISLAND.PAGE);
				var json = StringToJSON(response.responseText) || new Array();
				pageDom.attr(DISLAND.CONFIRM_DISPLAY, json['confirmStatus']);
			}
		});
	},
	
	/**
	 * 设置confirm信息是否不再显示
	 */
	changeAlertStatus : function() {
		var check = document.getElementById(showMSG.display_status_check)
		if(!check || !check.checked)
			return;
		
		Ext.Ajax.request({
			url : appConfig.ctx + actionURL.setPopupStatus(),
			method : "POST",
			success : function(response){
				var pageDom = DISLAND.getDataIsland().children(DISLAND.PAGE);
				var json = StringToJSON(response.responseText) || new Array();
				pageDom.attr(DISLAND.TIPS_DISPLAY, json['popupStatus']);
			}
		});
	}
};

/**
 * 操作结果信息
 */
ResultMessage = { 
	/**
	 * 显示操作结果的提示信息
	 * @param success 操作结果, true 成功,  false 失败
	 * @param message 提示信息
	 * @param hasMsg  是否显示提示信息
	 * @param parentFlag 是否在父页面显示提示信息
	 */
	showMsg : function(success, message, hasMsg, parentFlag) {
		if(!hasMsg)
			return;
		
		//判断信息提示框类型
		var pageDom = DISLAND.getDataIsland().children(DISLAND.PAGE);
		var tipsType = pageDom.attr(DISLAND.TIPS_TYPE);
		//普通提示信息, 表示提示信息将以文字形式显示在页面上方
		if(DISLAND.TIPS_TYPE_SIMPLE == tipsType){
			//提示信息消失时间(秒)
			var delay = pageDom.attr(DISLAND.TIPS_DELAY) || DISLAND.TIPS_DELAY_DEF;
			if(parentFlag){
				if(success) 
					parent.showTips.showOK(message, delay);
				else 
					parent.showTips.showErr(message, delay);
			}
			else {
				if(success) 
					showTips.showOK(message, delay);
				else 
					showTips.showErr(message, delay);
			}
		}
		//弹出式提示信息, 表示提示信息将alert形式显示
		else if(DISLAND.TIPS_TYPE_POPUP == tipsType){
			//表示不显示"操作成功提示信息
			if(success && DISLAND.TIPS_DISPLAY_NO === pageDom.attr(DISLAND.TIPS_DISPLAY))
				return;
				
			if(parentFlag){
				if(success) 
					parent.showMSG.showOK(message);
				else 
					parent.showMSG.showErr(message);
			}
			else {
				if(success) 
					showMSG.showOK(message);
				else 
					showMSG.showErr(message);
			}
		}
	},
		
	/**
	 * 显示错误提示信息
	 * @param message 提示信息
	 */
	showErrMsg : function(message) {
		var pageDom = DISLAND.getDataIsland().children(DISLAND.PAGE);
		var tipsType = pageDom.attr(DISLAND.TIPS_TYPE);
		
		if(!message || message == 'null')
			message = I18N.MSG_OPERATION_FAILURE;
		
		//普通
		if(DISLAND.TIPS_TYPE_SIMPLE == tipsType){
			var delay = pageDom.attr(DISLAND.TIPS_DELAY) || DISLAND.TIPS_DELAY_DEF;		
			showTips.showErr(message, delay);
		}
		//弹出式
		else if(DISLAND.TIPS_TYPE_POPUP == tipsType){
			showMSG.showErr(message);
		}
	}
};