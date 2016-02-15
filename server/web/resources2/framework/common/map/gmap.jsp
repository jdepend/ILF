<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title><%=java.net.URLDecoder.decode(request.getParameter("title"),"UTF-8") %></title>
		<link href="http://code.google.com/apis/maps/documentation/javascript/examples/default.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
		<script type="text/javascript">
		<!--
		function initialize() {
			var latitude = <%=request.getParameter("latitude") %>;
			var longitude = <%=request.getParameter("longitude") %>;
		
			// 经纬度
			var latlng = new google.maps.LatLng(latitude, longitude);
			// 设定参数，缩放，中心位置。
			var myOptions = {
				zoom: 15,
		        center: latlng,
		        navigationControlOptions: {style: google.maps.NavigationControlStyle.SMALL},
		        mapTypeId: google.maps.MapTypeId.ROADMAP
			}
			var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
			// 设置标记点
			var marker = new google.maps.Marker({
				position : latlng,
				map : map,
				title : '<%=java.net.URLDecoder.decode(request.getParameter("title"),"UTF-8") %>'
			});
		}
		//-->
		</script>
	</head>
	<body onload="initialize();">
		<div id="map_canvas"></div>
	</body>
</html>