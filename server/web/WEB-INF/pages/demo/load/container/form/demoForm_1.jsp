<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 表单示例 -->
<qeweb:page>
	<qeweb:form bind="demoFormBO" id="demoFormBO_1" layout="3;C2(textArea,label,label2),C3(editor,b1)" text="text型控件" icon="file">
		<qeweb:textField bind="textField" text="textField"/>
		<qeweb:password bind="password" text="password"/>
		<qeweb:textArea bind="textArea" text="textArea"/>
		<qeweb:blank bind="b1" text="----------------------------------------------------------"/>
		<qeweb:label bind="label" text="label"/>
		<qeweb:label bind="label2" text="label2"/>
		<qeweb:editor bind="editor" height="150" text="editor"/>
		<qeweb:label bind="editor2" text="editor2" translate="true"/>
		<qeweb:spinner bind="spinner" text="spinner"/>
		<qeweb:commandButton name="save" operate="save" text="save"/>
	</qeweb:form>
	
	<qeweb:form bind="demoFormBO" id="demoFormBO_2" layout="3;C2(optionTransferSelect)" text="枚举型控件" icon="file">
		<qeweb:select bind="select" text="select"/>
		<qeweb:checkBox bind="checkBox" text="checkBox"/>
		<qeweb:radio bind="radio" text="radio"/>
		<qeweb:optionTranserSelect bind="optionTransferSelect" text="optionTransferSelect"/>
	</qeweb:form>
	
	<qeweb:form bind="demoFormBO" id="demoFormBO_3" layout="2" text="附件控件">
		<qeweb:fileField bind="singleFile" operate="upload" showRange="true" text="单文件"/>
		<qeweb:fileField bind="multiFile" operate="upload" text="多文件"/>
		<qeweb:anchor bind="downLoad" text="downLoad"/>
	</qeweb:form>
	
	<qeweb:form bind="demoFormBO" id="demoFormBO_4" layout="3;C2(date_1,date_2,date_3,date_4),C3(image)" text="其它控件">
		<qeweb:dateField bind="date_1" text="日期"/>
		<qeweb:expend>
			<qeweb:dateField bind="date_2" text="日期段1"/>
		</qeweb:expend>
		<qeweb:expend>
			<qeweb:dateField bind="date_3" text="日期段2"/>
		</qeweb:expend>
		<qeweb:expend>
			<qeweb:dateField bind="date_4" text="日期段2"/>
		</qeweb:expend>
		<qeweb:img bind="image" text=""/>
	</qeweb:form>
</qeweb:page>

