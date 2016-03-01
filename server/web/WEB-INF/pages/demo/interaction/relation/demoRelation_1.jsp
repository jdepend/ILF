<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 细粒度关联细粒度示例 -->
<qeweb:page>
	<qeweb:form bind="demoFCRelationBO" id="demoFCRelationBO" 
		layout="4;C2(allSoftware,attachment,multiAttachment),C3(employeeName,demoLogo),C2(employeeSex,demoIncome,demoIncomeDesc,software)" text="表单中的细粒度组件关联">
		
		<qeweb:select bind="country" text="国家"/>
		<qeweb:select bind="province" text="省"/>
		<qeweb:select bind="city" text="市"/>
		<qeweb:select bind="area" text="区"/>
		<qeweb:radio bind="employeeSex" text="员工性别"/>
		<qeweb:checkBox bind="employeeName" text="员工姓名"/>
		<qeweb:select bind="demoIncome" text="收入"/>
		<qeweb:textField bind="demoIncomeDesc" text="其他请输入"/>
		<qeweb:radio bind="demoCheckLogo" columns="1"  text="logo"/>
		<qeweb:img bind="demoLogo" text=""/>
		<qeweb:checkBox bind="software" text="软件类型"/>
		<qeweb:textField bind="otherSoftware" text="其它软件"/>
		<qeweb:checkBox bind="allSoftware" text="软件类型展示"/>
		<qeweb:blank bind="blank1"/>
		<qeweb:blank bind="blank2"/>
		<qeweb:radio bind="attachmentFlag" text="是否上传附件"/>
		<qeweb:fileField bind="attachment" operate="upload" text="附件"/>	
		<qeweb:fileField bind="multiAttachment" operate="upload" text="多附件"/>
			
		<qeweb:commandButton name="save" operate="save" text="form.save"/>
		<qeweb:commandButton name="sysReset" operate="sysReset"/>
	</qeweb:form>
</qeweb:page>

