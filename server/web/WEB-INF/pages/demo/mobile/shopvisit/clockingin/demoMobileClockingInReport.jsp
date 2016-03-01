<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/pages/common/mapScript.jsp"%>

<qeweb:page onLoad="queryForm.formQuery">
   <qeweb:group relations="queryForm:resultList">
	  <qeweb:form id="queryForm" bind="DemoMobileClockingInBO" queryRange="true" layout="3;C2(visitDate)">
		<qeweb:textField bind="shopCode" />
		<qeweb:textField bind="shopName" />
		<qeweb:textField bind="userBO.userCode" text="督导员编码" />
		<qeweb:textField bind="userBO.userName" text="督导员名称" />
		<qeweb:expend>
		   <qeweb:dateField bind="visitDate"/>
		</qeweb:expend>
		<qeweb:commandButton name="formQuery" operate="query" text="form.query"/>
		<qeweb:commandButton name="sysReset" operate="sysReset"/>
	  </qeweb:form>
	  <qeweb:table id="resultList" bind="DemoMobileClockingInBO">
	   <qeweb:commandButton name="delete" operate="delete"/>
		<qeweb:expend>
		   <qeweb:commandButton name="showDetail" operate="DemoMobileClockingInBO.showDetail" width="50" text="view"/>
		</qeweb:expend>	
		<qeweb:hidden bind="id"/>
	    <qeweb:textField bind="shopCode" />
		<qeweb:textField bind="shopName" />
		<qeweb:dateField bind="visitDate" text="拜访时间"/>
		<qeweb:textField bind="userBO.userCode" text="督导员编码" />
		<qeweb:textField bind="userBO.userName" text="督导员名称" />
		<qeweb:textField bind="arrivalTime" />
		<qeweb:textField bind="leaveTime" />
		<qeweb:anchor bind="locationStr" text="地址"/>
	  </qeweb:table>
   </qeweb:group>
</qeweb:page>