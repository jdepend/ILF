<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 页面流示例2 -->
<qeweb:page onLoad="form.query">
	<qeweb:group relations="form:table">
		<qeweb:form id="form" bind="demoPageflowBO1" layout="3;C2(purchaseTime)" text="">
			<qeweb:textField bind="purchaseNo" text="采购单号"/>
			<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
			<qeweb:select bind="publishStatus" text="发布状态" />
			<qeweb:select bind="country" text="国家" align="center"/>
			<qeweb:select bind="province" text="省份" align="center"/>
			<qeweb:select bind="city" text="城市" align="center"/>
			<qeweb:expend>
				<qeweb:dateField bind="purchaseTime" text="采购时间"/>
			</qeweb:expend>
			<qeweb:commandButton name="query" operate="query" />
			<qeweb:commandButton name="sysReset" operate="sysReset" />
		</qeweb:form>
		
		<qeweb:table id="table" bind="demoPageflowBO1" text="">
			<qeweb:expend>
				<qeweb:commandButton name="showDesc" operate="demoPageflowBO1.toPage" text="查看明细"/>
				<qeweb:commandButton name="showDesc2" text="使用load指定的方法"/>
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
			<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
			<qeweb:textField bind="vendor.orgName" text="供应商名称"/>
			<qeweb:dateField bind="purchaseTime" align="center" text="采购时间" width="100"/>
			<qeweb:select bind="publishStatus" text="发布状态" align="center"/>
			<qeweb:select bind="receiveStatus" text="收货状态" align="center"/>
			<qeweb:select bind="closeStatus" text="关闭状态" align="center"/>
			<qeweb:select bind="lockStatus" text="锁定状态" align="center"/>
			<qeweb:select bind="country" text="国家" align="center"/>
			<qeweb:select bind="province" text="省份" align="center"/>
			<qeweb:select bind="city" text="城市" align="center"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>
