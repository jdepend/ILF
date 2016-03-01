<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<html>
	<head>
		<link href="<%=ctx %>/framework/js/desktop/css/desktop.css" type="text/css"  rel="stylesheet" />
		<script type="text/javascript" src="<%=ctx %>/framework/js/desktop/js/StartMenu.js"></script>
		<script type="text/javascript" src="<%=ctx %>/framework/js/desktop/js/TaskBar.js"></script>
		<script type="text/javascript" src="<%=ctx %>/framework/js/desktop/js/Desktop.js"></script>
		<script type="text/javascript" src="<%=ctx %>/framework/js/desktop/js/App.js"></script>
		<script type="text/javascript" src="<%=ctx %>/framework/js/desktop/js/Module.js"></script>
		<script type="text/javascript" src="<%=ctx %>/framework/js/framework/desktop/sample.js"></script>
	</head>
	<body scroll="no">
		<div id="x-desktop">
		    <a href="http://www.qeweb.com" target="_blank" style="margin:5px; float:right;"><img src="<%=ctx %>/framework/images/logo.png" /></a>
		
		    <dl id="x-shortcuts">
		        <dt id="grid-win-shortcut">
		            <a href="#"><img src="images/s.gif" />
		            <div>test1</div></a>
		        </dt>
		        <dt id="acc-win-shortcut">
		            <a href="#"><img src="images/s.gif" />
		            <div>test2</div></a>
		        </dt>
		    </dl>
		</div>
		
		<div id="ux-taskbar">
			<div id="ux-taskbar-start"></div>
			<div id="ux-taskbuttons-panel"></div>
			<div class="x-clear"></div>
		</div>
	</body>
</html>
