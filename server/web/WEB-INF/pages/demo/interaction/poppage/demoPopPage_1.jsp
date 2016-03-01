<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 弹出页面1 目标页面接收参数 -->
<qeweb:page onLoad="table1.sysFresh">
	<qeweb:form id="form" bind="demoPopPageBO1" layout="2" text="弹出页面-目标页面接收参数">
		<qeweb:textField bind="test1" text="test1"/>
		<qeweb:textField bind="test2" text="test2"/>
		<qeweb:textField bind="orgCode" text="供应商编码(单选)"/>
		<qeweb:textField bind="orgName" text="供应商名称(单选)">
			<qeweb:source bindBo="demoPopPageBO1" bindBop="orgCode:orgCode;orgName:orgName" 
				operate="demoSelectOrgBO1.toPopOrg" text="选择组织"/>
		</qeweb:textField>
		<qeweb:commandButton name="save" operate="save" text="form.save"/>
		<qeweb:commandButton name="sysReset" operate="sysReset" />
	</qeweb:form>
	
	<qeweb:table id="table1" bind="demoSelectOrgBO1" text="弹出页面-目标页面接收参数">
		<qeweb:commandButton name="selectOrgBtn" text="选择" icon="folder">
			<qeweb:source bindBo="demoPopPageBO1" bindBop="orgCode:orgCode;orgName:orgName" 
				operate="demoSelectOrgBO1.toPopOrg_2" text="选择组织"/>
		</qeweb:commandButton>
		<qeweb:hidden bind="id"/>
		<qeweb:textField bind="orgCode" text="供应商编码"/>
		<qeweb:textField bind="orgName" text="供应商名称"/>
		<qeweb:select bind="orgType" text="组织类型" />
	</qeweb:table>
</qeweb:page>
