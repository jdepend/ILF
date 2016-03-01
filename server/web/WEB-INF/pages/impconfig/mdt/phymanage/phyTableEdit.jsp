<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 新增物理表页面 --%>
<qeweb:page bind="phyAllBO">
	<qeweb:commandButton name="save" operate="update" text="form.save"/>
	<qeweb:commandButton name="viewAlterSql" operate="phyAllBO.viewAlterTableSql" text="查看SQL"/>
	<qeweb:commandButton name="goBack" text="form.back"/>
	
	<qeweb:tab id="tableTab">
		<qeweb:sheet id="tableInfo" text="表信息 ">
			<qeweb:form id="phyTableBOForm" bind="phyTableBO" text="" layout="2;C2(remark)" param="required" >
				<qeweb:textField bind="name" text="物理表名称"/>
				<qeweb:textField bind="alias" text="物理表别名"/>
				<qeweb:textArea bind="remark" text="备注" />
				<qeweb:hidden bind="id"/>
			</qeweb:form>
		</qeweb:sheet>
		<qeweb:sheet id="phyColumnBO" text="列信息 ">
			<qeweb:table id="phyTphyColumnBOTable" bind="phyColumnBO" hasBbar="false" text="" param="required"
				display="name=table:edit;alias=table:edit;
					dataType=table:edit;maxLength=table:edit;
					defValue=table:edit;isNotNull=table:edit;remark=table:edit">
				<qeweb:commandButton name="addRow" operate="sysAddRow"/>
				<qeweb:commandButton name="delRow" operate="sysDelRow"/>
				<qeweb:hidden bind="id"/>
				<qeweb:textField bind="name" text="字段名称" />
				<qeweb:textField bind="alias" text="字段别名" />
				<qeweb:select bind="dataType" text="字段类型" />
				<qeweb:textField bind="maxLength" text="最大长度" />
				<qeweb:textField bind="defValue" text="默认值" />
				<qeweb:select bind="isNotNull" text="是否非空" />
				<qeweb:textArea bind="remark" text="备注" />
			</qeweb:table>
		</qeweb:sheet>
	</qeweb:tab>
	
</qeweb:page>