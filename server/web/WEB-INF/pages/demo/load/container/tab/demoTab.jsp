<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 标签页示例  注意, 各粗粒度组件的id不能重复-->
<qeweb:page bind="demoTabBO" onLoad="table_1.sysFresh;form.query">
	<qeweb:commandButton name="save" operate="save" icon="save" text="form.save"/>
	<qeweb:tab id="tab">
		<qeweb:sheet id="s1" text="表单">
			<qeweb:form bind="demoFormBO" id="demoFormBO_1" layout="3;C2(textArea)" text="text型控件" icon="file">
				<qeweb:textField bind="textField" text="textField"/>
				<qeweb:label bind="label" text="label"/>
				<qeweb:password bind="password" text="password"/>
				<qeweb:textArea bind="textArea" text="textArea"/>
			</qeweb:form>
		</qeweb:sheet>
		<qeweb:sheet id="s2" text="细粒度组件关联">
			<qeweb:form bind="demoFCRelationBO" id="demoFCRelationBO" layout="4;C3(employeeName,demoLogo),C2(employeeSex,demoIncome,demoIncomeDesc,software)" text="表单中的细粒度组件关联">
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
				<qeweb:commandButton name="save" operate="save" text="form.save"/>
				<qeweb:commandButton name="sysReset" operate="sysReset"/>
			</qeweb:form>
		</qeweb:sheet>
		<qeweb:sheet id="s3" text="表格">
			<qeweb:table id="table_1" bind="demoTableBO" pageSize="3" text="普通表格">
				<qeweb:hidden bind="id"/>
				<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
				<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
				<qeweb:textField bind="vendor.orgName" text="供应商名称"/>
				<qeweb:dateField bind="purchaseTime" align="center" text="采购时间" width="100"/>
				<qeweb:select bind="publishStatus" text="发布状态" align="center"/>
				<qeweb:select bind="confirmStatus" text="确认状态" align="center"/>
				<qeweb:select bind="deliveryStatus" text="发货状态" align="center"/>
				<qeweb:select bind="receiveStatus" text="收货状态" align="center"/>
				<qeweb:select bind="closeStatus" text="关闭状态" align="center"/>
				<qeweb:select bind="lockStatus" text="锁定状态" align="center"/>
			</qeweb:table>
		</qeweb:sheet>
		<qeweb:sheet id="s4" text="树">
			<qeweb:tree bind="demoTreeBO" id="demoTreeBO_1" text="展示树"/>
		</qeweb:sheet>
		<qeweb:sheet id="s5" text="查询">
			<qeweb:group relations="form:table">
				<qeweb:form id="form" bind="demoTableBO" layout="3;C2(purchaseTime)" text="通过按钮触发关联">
					<qeweb:textField bind="purchaseNo" text="采购单号"/>
					<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
					<qeweb:select bind="publishStatus" text="发布状态" />
					<qeweb:commandButton name="query" operate="query" />
					<qeweb:commandButton name="sysReset" operate="sysReset" />
				</qeweb:form>
				
				<qeweb:table id="table" bind="demoTableBO" text="查询结果">
					<qeweb:hidden bind="id"/>
					<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
					<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
					<qeweb:textField bind="vendor.orgName" text="供应商名称"/>
					<qeweb:dateField bind="purchaseTime" align="center" text="采购时间" width="100"/>
					<qeweb:select bind="publishStatus" text="发布状态" align="center"/>
					<qeweb:select bind="receiveStatus" text="收货状态" align="center"/>
					<qeweb:select bind="closeStatus" text="关闭状态" align="center"/>
					<qeweb:select bind="lockStatus" text="锁定状态" align="center"/>
				</qeweb:table>
			</qeweb:group>
		</qeweb:sheet>
		
	</qeweb:tab>
</qeweb:page>