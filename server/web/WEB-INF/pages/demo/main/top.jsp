<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<%@page import="com.qeweb.framework.common.UserContext"%>

<script type="text/javascript">
	Ext.onReady(function(){
		var bd = Ext.getBody();
		var date = new Ext.Button({
			icon : '<%=ctx%>/framework/images/project/clock.png',
			text : ''
		});
		var logoPath = "/framework/images/preference/theme/logo/blue.jpg";
		
		//左缩进按扭
		var maxmin1 = new Ext.Button({
    		icon: '<%=ctx%>/framework/images/project/arrow_left_blue.png'
    	});
		//上缩进按扭
		var maxmin2 = new Ext.Button({
    		icon: '<%=ctx%>/framework/images/project/arrow_up_blue.png'
    	});
		
		var panel = new Ext.Panel({
			html : "<img src='" + appConfig.ctx + logoPath + "' width='100%' height='100%'>",
			width : bd.getWidth(),
			height : bd.getHeight(),
			bbar : [maxmin1, maxmin2,
		            date, '->', '【开发平台示例】&nbsp;&nbsp;','&nbsp;&nbsp;',
		            {
						xtype : 'button',
						pressed : true,
						tooltip : I18N.SYSTEM_EXIT,
						icon : '<%=ctx%>/framework/images/project/disconnect.png',
						text : '&nbsp;' + I18N.SYSTEM_EXIT + '&nbsp;',
						handler : function() {
							window.parent.location.href = "<%=ctx %>";
						}
					},
					'&nbsp;'
		   	]
		});
		panel.render(bd);
		
		//上缩进
        maxmin2.on('click', function() {
			if (panel.getHeight() < 100) {
				panel.setHeight(112);
        		top.frameset.rows = "112px,*,3%";
        		maxmin2.setIcon('<%=ctx%>/framework/images/project/arrow_up_blue.png');
			} 
			else {
				panel.setHeight(30);
        		top.frameset.rows = "32,*,3%";
       		 	maxmin2.setIcon('<%=ctx%>/framework/images/project/arrow_down_blue.png');
			}
		});
		
		//左缩进
        maxmin1.on('click', function() {
			if (top.iframeMiddle.cols !== "18%,*") {
				top.iframeMiddle.cols = "18%,*";
        		maxmin1.setIcon('<%=ctx%>/framework/images/project/arrow_left_blue.png');
			} 
			else {
				top.iframeMiddle.cols = "0,*";
        		maxmin1.setIcon('<%=ctx%>/framework/images/project/arrow_right_blue.png');
			}
		});

		Ext.TaskMgr.start({
			run : function() {
				date.setText("<span style='font-size: 12px;'>" + new Date().toLocaleString() + "</span>")
			},
			interval : 1000
		});
	});
</script>

