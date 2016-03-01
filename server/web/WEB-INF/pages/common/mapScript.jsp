<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.qeweb.framework.common.appconfig.AppConfig"%>
<%
	if(AppConfig.isBaiduMap()) {
%>
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.4"></script>
<%
	} else {
%>
<!-- 服务器使用ip地址：221.224.132.210 -->
<!-- 		<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false&amp;key=AIzaSyDfbYAepDWOV2HgKdlQMzn8pYjEI2-r8U4" type="text/javascript"></script> -->
<!-- 本地使用ip地址：127.0.0.1 -->
		<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false&amp;key=AIzaSyA0_Kv_OaoHcPFxZgJWTI2kZQyVSDOF2fk" type="text/javascript"></script>
<%
	}
%>
<script type="text/javascript" src="<%=ctx %>/framework/js/ext/ext3.2/ux/map/MapHandel.js"></script>
<script type="text/javascript" src="<%=ctx %>/framework/js/ext/ext3.2/ux/map/BMapHandler.js"></script>
<script type="text/javascript" src="<%=ctx %>/framework/js/ext/ext3.2/ux/map/GMapHandler.js"></script>
<script type="text/javascript" src="<%=ctx %>/framework/js/ext/ext3.2/ux/map/MapManager.js"></script>
<script type="text/javascript" src="<%=ctx %>/framework/js/ext/ext3.2/ux/map/MapPanel.js"></script>

