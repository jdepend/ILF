<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="table.sysFresh">
	<qeweb:form id="demoDateBO" bind="demoDateBO" text="日期示例" layout="2">
		<qeweb:dateField bind="date1" text="Y-m-d"/>
		<qeweb:dateField bind="date2" text="Y-m-d H"/>
		<qeweb:dateField bind="date3" text="Y-m-d H:i"/>
		<qeweb:dateField bind="date4" text="Y-m-d H:i:s"/>
		<qeweb:dateField bind="date5" text="Y-m"/>
		<qeweb:dateField bind="date6" text="Y"/>
		<qeweb:dateField bind="date7" text="disable[0,6]"/>
		<qeweb:expend>
			<qeweb:dateField bind="date8" text="date8"/>
		</qeweb:expend>
		<qeweb:commandButton name="jump" operate="demoDateBO.toPage" text="弹出其它页面"/>
		<qeweb:commandButton name="save" operate="save" text="form.save"/>
		<qeweb:commandButton name="sysReset" operate="sysReset"/>
	</qeweb:form>
	
	<qeweb:table id="table" bind="demoDateBO" text="表格中的高亮显示" 
		display="purchaseNo=table;purchaseTime=table:edit;confirmTime=table:edit;publishTime=table:edit">
		<qeweb:hidden bind="id"/>
		<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
		<qeweb:dateField bind="purchaseTime" align="center" text="采购时间" width="100"/>
		<qeweb:dateField bind="confirmTime" align="center" text="确认时间" width="120"/>
		<qeweb:dateField bind="publishTime" align="center" text="发布时间" width="150"/>
	</qeweb:table>
</qeweb:page>