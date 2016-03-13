var showTips = {
	/**
	 * 显示消息框的坐标,默认为左上角
	 * @param {} message（必选参数）消息内容
	 * @param {} x （可选参数）可配置为数字百分比
	 * @param {} y （可选参数）可配置为数字百分比
	 * @param {} time （可选参数）延迟多长时间消失，-1为永不消失，单位为秒
	 */
	showTips : function showTips(message, x, y, time){
			if(!message) return;
			var tipsDiv = '<div class="tipsClass">' + message + '</div>';
			x = /\d{1,2}%|100%/.test(x) ? x : (x || 0) + "px";
			y = /\d{1,2}%|100%/.test(y) ? y : (y || 0) + "px";
			time = parseInt(time) || -1;
			$('body').append(tipsDiv);
			$('div.tipsClass').css({
				'top' : x,
				'left' : y,
				'position' : 'absolute',
				'padding' : '3px 5px',
				'background': '#FFFFA2',
				'border': '1px solid #CAA700',
				'font-size' : 12 + 'px',
				'margin' : '0 auto',
				'text-align': 'center',
				'width' : 'auto',
				'color' : '#fff',
				'opacity' : '0.8'
			}).show();
			if(-1 != time)
				setTimeout( function(){$( 'div.tipsClass' ).fadeOut();}, ( time * 1000 ) );
		},

	showOK : function(message, delay){
		this.showTips("<span style='color:green;font:14px;'>&nbsp;&nbsp;"+message+"&nbsp;</span>", 5, 5, delay);
	},

	showWaring : function(message, delay){
		this.showTips("<span style='color:orange;font:14px;'>&nbsp;&nbsp;"+message+"&nbsp;</span>", 5, 5, delay);
	},

	showErr : function(message, delay){
		this.showTips("<span style='color:red;font:14px;'>&nbsp;&nbsp;"+message+"&nbsp;</span>", 5, 5, delay);
	}

}