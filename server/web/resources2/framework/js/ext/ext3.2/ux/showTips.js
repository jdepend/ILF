var showTips = {
	/**
	 * 显示消息框的坐标,默认为左上角
	 * @param message （必选参数）消息内容
	 * @param x （可选参数）可配置为数字、left和right或百分比
	 * @param y （可选参数）可配置为数字、top或bottom或百分比
	 * @param delay （可选参数）延迟多长时间消失，-1为永不消失，单位为秒
	 */
	showTips : function(message, x, y, delay){
		if(!message) return;
        // 只允许百分数或数值参数
        x=/\d{1,2}%|100%|left|right/.test(x)?x:(parseInt(x)||0)+"px";
        y=/\d{1,2}%|100%|top|bottom/.test(y)?y:(parseInt(y)||0)+"px";
        delay=parseInt(delay) || -1;
        var fdDiv=Ext.get('show_feedBack');
        if(!fdDiv){
            fdDiv=Ext.DomHelper.append(document.body,{
                tag:'div',
                id:'show_feedBack',
                style:'position:absolute;z-index:10000;top:0;left:0;filter:alpha(opacity=100);'
            }, true);
            Ext.DomHelper.append('show_feedBack',{
                tag:'div',
                id:'show_feedBack_message',
                style:'white-space:nowrap'
            }, true);
        }

        var fdDivContent=fdDiv.first('div'); // 内容域
        fdDiv.stopFx();
        fdDiv.setLeftTop(0,0);
        fdDivContent.dom.innerHTML='';
        fdDivContent.insertHtml('afterBegin', message);
        fdDiv.setDisplayed(true);

        var x1=x, y1=y;
        function calPos(){ // 如果是left/top/right/bottom参数则计算位置
            var doc=Ext.getBody().getViewSize();
            var scroll=Ext.getBody().getScroll();
            if (/left|right/.test(x1)) {
                x=(x1=="left")?0:(doc.width-fdDiv.getWidth());
                x=x+scroll.left;
            }
            if (/top|bottom/.test(y1)) {
                y=(y1 == "top")?0:(doc.height-fdDiv.getHeight());
                y=y+scroll.top;
            }
            fdDiv.setLeftTop(x, y);
        };
        try{
            clearInterval(tui.showTip.repos)
        } catch (e) {}
        if(/left|right/.test(x1)||/top|bottom/.test(y1)) {
            tui.showTip.repos=setInterval(calPos, 1); // 每隔0.1秒定位一次层
        }else{ // 数值或百分比
            fdDiv.setLeftTop(x, y)
        }
        fdDiv.setOpacity(1);

        // 渐隐效果
        if (delay!=-1) {
            fdDiv.pause(delay).fadeOut({
                endOpacity:0,
                useDisplay:true,
                duration:3
            });
        }
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