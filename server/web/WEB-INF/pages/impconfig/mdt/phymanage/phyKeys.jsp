<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 主外键设置 --%>
<qeweb:page onLoad="phyKeysBO.sysFresh">
	<qeweb:group relations="phyTableBO:phyKeysBO">
		<qeweb:form id="phyTableBO" bind="phyTableBO" layout="2;C2(remark)" param="required" text="物理表信息">
			<qeweb:label bind="name" text="物理表名称"/>
			<qeweb:label bind="alias" text="物理表别名"/>
			<qeweb:textArea bind="remark" text="备注" />
			<qeweb:hidden bind="id"/>
		</qeweb:form>
		
		<qeweb:table id="phyKeysBO" bind="phyKeysBO" hasBbar="false" text="主外键信息"
			display="name=table:edit;type=table:edit;columnId=table:edit;
				referenceTableId=table:edit;referenceColumnId=table:edit;
				remark=table:edit;">
			<qeweb:commandButton name="addRow" operate="sysAddRow"/>
			<qeweb:commandButton name="delete" operate="sysDelRow"/>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="name" text="名称"/>
			<qeweb:select bind="type" text="类型"/>
			<qeweb:select bind="columnId" text="column"/>
			<qeweb:select bind="referenceTableId" text="reference table"/>
			<qeweb:select bind="referenceColumnId" text="reference column" />
			<qeweb:textArea bind="remark" text="备注" />
		</qeweb:table>
	</qeweb:group>
</qeweb:page>