var showMSG = {
	display_status_check : 'qeweb_display_status',

	showMsg : function(message, width, icon) {

	},

	/**
	 * layer 参数说明 dialog : { msg : message, 信息框内容，重要参数。 type : 1
	 * 图标类型，提供了0-10的选择。 } time 自动关闭等待秒数，整数值。 title:[ '', false ]
	 * 控制标题栏，不想显示标题栏，配置[ '', false ]即可。 closeBtn 控制层右上角关闭按钮，不想显示按钮，配置[ '', false
	 * ]即可。 border:[4, 0.3, '#000', true]
	 * 4：边框大小，0.3：边框透明度，'#000'：边框颜色，true：是否显示边框（否：false）。 shade:[ 0.1, '#000',
	 * true ] 控制遮罩。0.1：遮罩透明度，'#000'：遮罩颜色，true：是否遮罩（否：false）。 offset:[ '0px',
	 * '50%' ] 控制层坐标。'220px'：纵坐标，'50%'：横坐标。
	 */
	showOK : function(message, delay) {
		$.layer({
			dialog : {
				msg : message,
				type : 1
			},
			time : delay,
			title : [ '', false ],
			closeBtn : [ '', false ],
			border : [ 4, 0.3, '#000', true ],
			shade : [ 0.1, '#000', false ],
			offset : [ '0px', '50%' ]
		});
	},

	/**
	 * layer 参数说明 dialog : { msg : message, 信息框内容，重要参数。 type : 1
	 * 图标类型，提供了0-10的选择。 btns : 按钮的个数。提供了0-2的选择 btn : [按钮一的文本值 , 按钮二的文本值] yes :
	 * 按钮一的回调函数 } remember:true 是否在confirm中显示checkbox title:[ '', false ]
	 * 控制标题栏，不想显示标题栏，配置[ '', false ]即可。 border:[4, 0.3, '#000', true]
	 * 4：边框大小，0.3：边框透明度，'#000'：边框颜色，true：是否显示边框（否：false）。
	 */
	confirm : function(message, conYes) {
		$.layer({
			remember : true,
			dialog : {
				msg : message,
				type : 4,
				btns : 2,
				yes : conYes
			},
			border : [ 5, 0.3, '#000', true ]
		});
	},

	showWaring : function(message) {
		this.showMsg(message)
	},

	/**
	 * layer 参数说明 dialog : { msg : message, 信息框内容，重要参数。 type : 1
	 * 图标类型，提供了0-10的选择。 } time 自动关闭等待秒数，整数值。 title:[ '', false ]
	 * 控制标题栏，不想显示标题栏，配置[ '', false ]即可。 closeBtn 控制层右上角关闭按钮，不想显示按钮，配置[ '', false
	 * ]即可。 border:[4, 0.3, '#000', true]
	 * 4：边框大小，0.3：边框透明度，'#000'：边框颜色，true：是否显示边框（否：false）。 shade:[ 0.1, '#000',
	 * true ] 控制遮罩。0.1：遮罩透明度，'#000'：遮罩颜色，true：是否遮罩（否：false）。 offset:[ '0px',
	 * '50%' ] 控制层坐标。'220px'：纵坐标，'50%'：横坐标。
	 */
	showErr : function(message, delay) {
		$.layer({
			dialog : {
				msg : message,
				type : 2
			},
			time : delay,
			title : [ '', false ],
			closeBtn : [ '', false ],
			border : [ 4, 0.3, '#000', true ],
			shade : [ 0.1, '#000', false ],
			offset : [ '0px', '50%' ]
		});
	},
	
	/**
	 * layer.alert(message , 1) 1:操作成功的提示
	 */
	alertOK : function(message) {
		layer.alert(message , 1);
	},
	
	/**
	 * layer.alert(message , 2) 2:操作失败的提示
	 */
	alertErr : function(message) {
		layer.alert(message , 2);
	}
}

var alertMsg = {
	choseRecord : function(title) {
		if (!!title)
			alert(I18N.ALERT_CHOOSE_BEFORE + title + I18N.ALERT_CHOOSE_AFTER);
		else
			alert(I18N.ALERT_CHOOSE_RECORD);
	},

	modify : function(title) {
		if (!!title)
			alert(title + I18N.ALERT_MODIFIED_AFTER);
		else
			alert(I18N.ALERT_MODIFIED_RECORD);
	}

}

/**
 * 修改个人设置(提示信息和confirm信息是否显示)
 */
var displayStatus = {
	submitBtn : function(isConfirm) {
		var check = document.getElementById(showMSG.display_status_check)
		if (!check)
			return;
		var url = appConfig.ctx + actionURL.setPopupStatus();
		if (isConfirm == true)
			url = appConfig.ctx + actionURL.setConfirmStatus();
		if (check.checked) {
			$.ajax({
				type : "POST",
				url : url,
				success : function(response) {
					var pageDom = DISLAND.getDataIsland()
							.children(DISLAND.PAGE);
					var json = StringToJSON(response.responseText)
							|| new Array();
					var popupStatus = json['popupStatus'];
					var confirmStatus = json['confirmStatus'];
					if (popupStatus) {
						pageDom.attr(DISLAND.TIPS_DISPLAY, popupStatus);
					} else if (confirmStatus) {
						pageDom.attr(DISLAND.CONFIRM_DISPLAY, confirmStatus);
					}
				}
			});
		}
	}
};

ResultMessage = {
	showMsg : function(success, message, hasMsg, parentFlag) {
		if (!hasMsg)
			return;

		// 判断信息提示框类型
		var pageDom = DISLAND.getDataIsland().children(DISLAND.PAGE);
		var tipsType = pageDom.attr(DISLAND.TIPS_TYPE);
		// 普通提示信息, 表示提示信息将以文字形式显示在页面上方
		if (DISLAND.TIPS_TYPE_SIMPLE == tipsType) {
			// 提示信息消失时间(秒)
			var delay = pageDom.attr(DISLAND.TIPS_DELAY)
					|| DISLAND.TIPS_DELAY_DEF;
			if (parentFlag) {
				if (success)
					parent.showMSG.showOK(message, delay);
				else
					parent.showMSG.showErr(message, delay);
			} else {
				if (success)
					showMSG.showOK(message, delay);
				else
					showMSG.showErr(message, delay);
			}
		}
		// 弹出式提示信息, 表示提示信息将alert形式显示
		else if (DISLAND.TIPS_TYPE_POPUP == tipsType) {
			// 表示不显示"操作成功提示信息
			if (success
					&& DISLAND.TIPS_DISPLAY_NO === pageDom
							.attr(DISLAND.TIPS_DISPLAY))
				return;

			if (parentFlag) {
				if (success)
					parent.showMSG.alertOK(message);
				else
					parent.showMSG.alertErr(message);
			} else {
				if (success)
					showMSG.alertOK(message);
				else
					showMSG.alertErr(message);
			}
		}
	}
};
