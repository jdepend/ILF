<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 持久化示例:导出 -->
<qeweb:page onLoad="form.query">
	<qeweb:group relations="form:table">
		<qeweb:form id="form" bind="demoExpBO" text="导出功能示例">
			<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
			<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
			<qeweb:select bind="confirmStatus" text="确认状态" />
			<qeweb:commandButton name="exp" operate="expExl" text="导出Exl" icon="exp"></qeweb:commandButton>
			<qeweb:commandButton name="query" operate="query" />
			<qeweb:commandButton name="sysReset" operate="sysReset" />
		</qeweb:form>
		
		<qeweb:table id="table" bind="demoExpBO" text="导出功能示例">
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
			<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
			<qeweb:dateField bind="purchaseTime" align="center" text="采购时间" width="100"/>
			<qeweb:select bind="publishStatus" text="发布状态" />
			<qeweb:select bind="confirmStatus" text="确认状态" />
		</qeweb:table>
	</qeweb:group>
</qeweb:page>
