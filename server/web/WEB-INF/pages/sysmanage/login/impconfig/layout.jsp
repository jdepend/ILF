<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.qeweb.framework.common.Envir"%>
<%String ctx = Envir.getContextPath();%>
<html>
	<frameset rows="112px,*,3%" id="frameset">
		<frame id="iframeTop" name="iframeTop" src="<%=ctx %>/system/imptop.action" frameborder="NO" marginheight="0" marginwidth="0"/>
		<frameset cols="18%,*" id="iframeMiddle">
			<frame id="iframeLeft" name="iframeLeft" src="<%=ctx %>/system/impmenu.action"  frameborder="NO" marginheight="0" marginwidth="0"/>
			<frame id="iframeMain" name="iframeMain" src="" frameborder="NO" marginheight="0" marginwidth="0"/>
		</frameset>
		<frame id="iframeBottom" name="iframeBottom" src="<%=ctx %>/system/bottom_frame.action" frameborder="NO" marginheight="0" marginwidth="0"/>
	</frameset>
	
	<noframes><body></body></noframes>
</html>

