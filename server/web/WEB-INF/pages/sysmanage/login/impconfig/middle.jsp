<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<html>
<head>
<script>
	function shleft() {
		if (parent.document.getElementById("iframeMiddle").cols == "0,6,*") {
			parent.document.getElementById("iframeMiddle").cols = "18%,6,*"
			document.getElementById("img").src = ctx + "/js/ext/ext3.2/resources/images/default/layout/mini-left.gif"
		} else {
			parent.document.getElementById("iframeMiddle").cols = "0,6,*"
			document.getElementById("img").src = ctx + "/js/ext/ext3.2/resources/images/default/layout/mini-right.gif"
		}
	}
</script>
<style type="text/css">
body {
	background: url(../js/ext/ext3.2/resources/images/default/panel/light-hd.gif) #D9E7F8 repeat-y;
}
</style>
</head>
<body leftMargin=0 topMargin=0>
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
<TBODY>
<TR>
    <TD width="10px" height="50%">&nbsp;</TD>
</TR>
<TR>
    <TD style="BORDER-RIGHT: 0px; BORDER-TOP: 0px; BORDER-LEFT: 0px; BORDER-BOTTOM: 0px; " width="10px">
    <A href="javascript:shleft();" title=点击缩放窗口 style="CURSOR: hand" >
    <IMG id="img" src="<%=ctx %>/js/ext/ext3.2/resources/images/default/layout/mini-left.gif" border=0 width="6px"></A>
    </TD>
</TR>
<TR>
    <TD width="10px" height="50%">&nbsp;</TD>
</TR>
    </TBODY>
</TABLE>
</body>
</html>

