<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 变量如何影响组件    修改页面 -->
<qeweb:page bind="varPageAllBO">
	<qeweb:commandButton name="save" operate="save,varPageBO.show" text="form.save"/>
	<qeweb:commandButton name="goBack" operate="varPageBO.show" text="form.back"/>
	
	<qeweb:form id="varPageForm" bind="varPageBO" text="关联信息" layout="3;C2(pageURL)" param="required">
		<qeweb:label bind="relateName" text="关联名称"/>
		<qeweb:label bind="vars" text="变量名称"/>
		<qeweb:label bind="containerType" text="粗粒度组件类型"/>
		<qeweb:label bind="containerId" text="粗粒度组件ID"/>
		<qeweb:label bind="bind" text="绑定标识"/>
		<qeweb:label bind="pageURL" text="页面路径"/>
		<qeweb:hidden bind="id"/>
	</qeweb:form>
	
	<qeweb:form id="varPageItemForm" bind="varPageItemBO" text="变量值-组件关联信息" param="required">
		<qeweb:hidden bind="id"/>
		<qeweb:textField bind="relateName" text="变量值-组件关联名称" width="150" />
	</qeweb:form>
	
	<qeweb:table id="varConfTable" bind="varConfBO" text="变量设置" hasBbar="false" sm="empty" param="required"
		display="varName=table;valueSetCode=table:edit;valueStr=table:edit">
		<qeweb:hidden bind="id" />
		<qeweb:textField bind="varName" text="变量" width="200" align="center" />
		<qeweb:select bind="valueSetCode" text="值集编码" width="200" align="center" />
		<qeweb:textField bind="valueStr" text="值" width="200" align="center" />
	</qeweb:table>

	<qeweb:table id="varVCTable" bind="varVCBO" text="组件信息" hasBbar="false" param="required"
		display="vcType=table;bind=table;readonly=table:edit;disable=table:edit;hidden=table:edit;valueStr=table:edit">
		<qeweb:commandButton name="selectComponents" text="引入组件">
			<qeweb:source bindBo="analyzeComponentBO"
				bindBop="vcType:vcType;bind:bind"
				operate="analyzeComponentBO.findAnaCompBOList" sm="checkbox" />
		</qeweb:commandButton>
		<qeweb:commandButton name="delRow" operate="sysDelRow" />
		<qeweb:hidden bind="id" />
		<qeweb:textField bind="vcType" text="组件类型" width="150" />
		<qeweb:textField bind="bind" text="绑定标识" width="300" />
		<qeweb:select bind="readonly" text="只读" width="100" align="center" />
		<qeweb:select bind="disable" text="禁用" width="100" align="center" />
		<qeweb:select bind="hidden" text="隐藏" width="100" align="center" />
		<qeweb:textField bind="valueStr" text="值" width="100" />
	</qeweb:table>
</qeweb:page>