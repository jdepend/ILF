<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<%@page import="com.qeweb.framework.common.UserContext"%>
<link href="<%=ctx%>/css/topMenu.css" rel="stylesheet" type="text/css" />

<html>
<body>
	<img id='logo' width='100%' height='75%'>
	<div class="x-toolbar x-small-editor x-toolbar-layout-ct">
		<table width="100%">
			<tr>
				<td id="leftArea" align="left"></td>
				<td align="left">
					<qeweb:page displayType="html">
						<qeweb:menu id="topMenu" bind="sysMenuBO" target="iframeLeft" menuType="top" />
					</qeweb:page>
				</td>
				<td id="rightArea" align="right"></td>
			</tr>
		</table>
	</div>
</body>
</html>

<script type="text/javascript">
	$(document).ready(function() {
		setLogo();
		setLeftArea();
		setRightArea();
	});
	
	/**
	 * 设置logo
	 */
	function setLogo() {
		var logoPath = "/framework/images/preference/theme/logo/<%=DisplayType.getThemeType() %>.jpg";
		if(<%=DisplayType.isCustomLogo()%>)
			logoPath = "<%=DisplayType.getLogoPath()%>";
			
		$('#logo').attr('src', appConfig.ctx + logoPath);
	}
	
	/**
	 * 设置LeftArea区域, 添加左缩进按扭,上缩进按扭
	 */
	function setLeftArea() {
		//左缩进按扭
		var maxmin1 = $("<img id='maxmin1' style='cursor:hand' src='<%=ctx%>/framework/images/project/arrow_left_blue.png'/>");
		//上缩进按扭
		var maxmin2 = $("<img id='maxmin2' style='cursor:hand' src='<%=ctx%>/framework/images/project/arrow_up_blue.png'/>");
		
		var logo = $('#logo');
		var logoHeight = logo.attr('height'); 
		
		//左缩进
       	maxmin1.bind('click', function() {
       		if (top.iframeMiddle.cols !== "18%,*") {
				top.iframeMiddle.cols = "18%,*";
				maxmin1.attr('src', '<%=ctx%>/framework/images/project/arrow_left_blue.png');
			} 
			else {
				top.iframeMiddle.cols = "0,*";
				maxmin1.attr('src', '<%=ctx%>/framework/images/project/arrow_right_blue.png');
			}
		});
		
		//上缩进
       	maxmin2.bind('click', function() {
        	if(logo.attr('height') > 0) {
        		logo.attr('height', 0);
        		top.frameset.rows = "28px,*,3%"
        		maxmin2.attr('src', '<%=ctx%>/framework/images/project/arrow_down_blue.png');
        	}
        	else {
        		logo.attr('height', logoHeight);
        		top.frameset.rows = "112px,*,3%"
        		maxmin2.attr('src', '<%=ctx%>/framework/images/project/arrow_up_blue.png');
        	}
		});
		
		//系统时钟
       	var dateImg = $("<img src='<%=ctx%>/framework/images/project/clock.png'>");
		var date = $('<span></span>');
		date.text(new Date().toLocaleString());
		
       	var leftArea = $('#leftArea');
		leftArea.append(maxmin1);
		leftArea.append('&nbsp;')
		leftArea.append(maxmin2);
       	leftArea.append('&nbsp;&nbsp;')
       	leftArea.append(dateImg);
       	leftArea.append(date);
		
		setTimeout(function() {
			date.text(new Date().toLocaleString());
		}, 1000);
	}
	
	/**
	 * 设置RightArea区域
	 */
	function setRightArea() {
		var rightArea = $('#rightArea');
		//当前用户
		var user = '<img src="<%=ctx%>/framework/images/project/user.png" title="'+ I18N.SYSTEM_CURRENT_USER + '"/><%=UserContext.getUserBO().getUserName()%>';
		//当前部门
		var org = '<img src="<%=ctx%>/framework/images/project/lightbulb.png" title="'+ I18N.SYSTEM_CURRENT_USER + '"/><%=UserContext.getOrgBO().getOrgName()%>';
		//"退出"按钮
		var logoutBtn = $("<img src='<%=ctx%>/framework/images/project/disconnect.png' style='cursor:hand' title=" + I18N.SYSTEM_EXIT + ">");
		rightArea.append("&nbsp;&nbsp;");
		logoutBtn.bind('click', function() {
			window.parent.location.href = "<%=ctx %>/system/login!logout.action";
		});
		
		rightArea.append(user)
		rightArea.append("&nbsp;&nbsp;");
		rightArea.append(org)
		rightArea.append("&nbsp;&nbsp;");
		rightArea.append(logoutBtn);
		rightArea.append("&nbsp;&nbsp;&nbsp;&nbsp;");
	}
</script>