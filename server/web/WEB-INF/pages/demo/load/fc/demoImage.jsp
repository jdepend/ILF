<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 图片示例 -->
<qeweb:page onLoad="table.sysFresh">
	<qeweb:form id="form" bind="demoImageBO" layout="2;R3(image_1)" text="表单中的图片">
		<qeweb:textField bind="s1" text="s1"/>
		<qeweb:img bind="image_1" text=""/>
		<qeweb:textField bind="s2" text="s2"/>
		<qeweb:textField bind="s3" text="s3"/>
	</qeweb:form>
	
	<qeweb:table id="table" bind="demoImageBO" text="表格中的图片">
		<qeweb:hidden bind="id"/>
		<qeweb:img bind="image_2" text="图片2"/>
		<qeweb:img bind="image_3" text="图片3"/>
		<qeweb:img bind="image_4" text="图片4"/>
	</qeweb:table>
</qeweb:page>
