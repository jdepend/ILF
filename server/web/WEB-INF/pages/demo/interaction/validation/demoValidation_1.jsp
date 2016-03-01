<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 细粒度组件校验示例 -->
<qeweb:page>
	<qeweb:form bind="demoValidationBO1" id="demo" text="前台校验" layout="2;C2(clientLabel,clientLabel2,clientLabel3,clientLabel4,clientLabel5)">
		<qeweb:label bind="clientLabel" text="前台校验1"/>
		<qeweb:textField bind="clientBOP" text="前台校验1"/>
		<qeweb:label bind="clientLabel2" text="前台校验2"/>
		<qeweb:textField bind="clientBOP2" text="前台校验2"/>
		<qeweb:label bind="clientLabel3" text="前台校验3"/>
		<qeweb:checkBox bind="clientBOP3" text="前台校验3"/>
		<qeweb:label bind="clientLabel4" text="前台校验4"/>
		<qeweb:radio bind="clientBOP4" text="前台校验4"/>
		<qeweb:label bind="clientLabel5" text="前台校验5"/>
		<qeweb:spinner bind="clientBOP5" text="前台校验5"/>
		<qeweb:commandButton name="save" icon="save" text="form.save"/>
	</qeweb:form>
	
	<qeweb:form bind="demoValidationBO1" id="demo2" text="后台校验-点击按钮时校验">
		<qeweb:label bind="serverLabel" text="后台校验1"/>
		<qeweb:textField bind="serverBOP" text="后台校验1"/>
		<qeweb:blank bind="blank1"/>
		<qeweb:label bind="serverLabel2" text="后台校验2"/>
		<qeweb:textField bind="serverBOP2" text="后台校验2"/>
		<qeweb:commandButton name="save" icon="save" text="form.save"/>
	</qeweb:form>
</qeweb:page>

