<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 弹出选择示例1 表单中的普通弹出 -->
<qeweb:page>
	<qeweb:form id="form" bind="demoPopSelectBO1" layout="3;C2(orgName1,orgName2,nodeName1,nodeName2)" text="弹出选择">
		<qeweb:textField bind="orgCode1" text="供应商编码(单选)"/>
		<qeweb:textField bind="orgName1" text="供应商名称(单选)">
			<qeweb:source bindBo="orgBO" bindBop="orgCode1:orgCode;orgName1:orgName" text="选择组织(单选)"/>
		</qeweb:textField>
		
		<qeweb:textField bind="orgCode2" text="供应商编码(多选)"/>
		<qeweb:textField bind="orgName2" text="供应商名称(多选)">
			<qeweb:source bindBo="orgBO" bindBop="orgCode2:orgCode;orgName2:orgName" sm="checkbox" text="选择组织(多选)"/>
		</qeweb:textField>
		
		<qeweb:textField bind="nodeId1" text="树id(单选)"/>
		<qeweb:textField bind="nodeName1" text="树节点(单选)">
			<qeweb:source bindBo="demoTreeBO" bindBop="nodeId1:id;nodeName1:value" text="选择树节点(单选)" />
		</qeweb:textField>
		
		<qeweb:textField bind="nodeId2" text="树id(多选)"/>
		<qeweb:textField bind="nodeName2" text="树节点(多选)">
			<qeweb:source bindBo="demoTreeBO" bindBop="nodeId2:id;nodeName2:value" sm="checkbox" text="选择树节点(多选)"/>
		</qeweb:textField>
		<qeweb:commandButton name="save" operate="save" text="form.save" />
		<qeweb:commandButton name="sysReset" operate="sysReset" />
	</qeweb:form>
</qeweb:page>

