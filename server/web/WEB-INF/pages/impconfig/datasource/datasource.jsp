<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page bind="dataSourceBO" >
	<qeweb:commandButton name="save" operate="saveAll" text="form.save"/>
	<qeweb:tab id="myTab">
		<qeweb:sheet id="s1" text="数据源">
			<qeweb:form id="dataSourceForm1" bind="dataSourceBO" layout="1" text="" param="required">
				<qeweb:textField bind="hostAddress" text="Host Address"/>
				<qeweb:textField bind="username" text="Username"/>
				<qeweb:textField bind="password" text="Password"/>
				<qeweb:textField bind="port" text="Port"/>
				<qeweb:textField bind="name" text="Database"/>
				<qeweb:radio bind="type" text="数据库类型"/>
			</qeweb:form>
		</qeweb:sheet>
		<qeweb:sheet id="s2" text="连接池">
			<qeweb:form id="dataSourceForm2" bind="dataSourceBO" layout="1" text="" param="required">
				<qeweb:textField bind="maxActive" text="最大连接数"/>
				<qeweb:textField bind="maxIdle" text="最大空闲连接数"/>
				<qeweb:textField bind="maxWait" text="等待时间"/>
				<qeweb:radio bind="defaultAutoCommit" text="事物是否自动提交"/>
			</qeweb:form>
		</qeweb:sheet>
	</qeweb:tab>
</qeweb:page>