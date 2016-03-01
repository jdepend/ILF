<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 执行自定义JS示例1 -->
<qeweb:page onLoad="table.sysFresh" bind="demExeJsBO1">
	<qeweb:commandButton name="doJS_1" operate="doJS_1" text="doJS_1"/>
	<qeweb:group relations="form:table">
		<qeweb:form id="form" bind="demExeJsBO1" layout="3;C2(purchaseTime)" text="通过按钮触发关联">
			<qeweb:textField bind="purchaseNo" text="采购单号"/>
			<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
			<qeweb:select bind="publishStatus" text="发布状态" />
			<qeweb:expend>
				<qeweb:dateField bind="purchaseTime" text="采购时间"/>
			</qeweb:expend>
			<qeweb:commandButton name="doJS_2" operate="doJS_1" text="doJS_2"/>
		</qeweb:form>
		
		<qeweb:table id="table" bind="demExeJsBO1" text="查询结果" sm="empty">
			<qeweb:expend>
				<qeweb:commandButton name="doJS_3" operate="doJS_1" text="doJS_3"/>
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
		</qeweb:table>
	</qeweb:group>
</qeweb:page>

<script type="text/javascript">
<!--
	function doJS_1(btnEvent) {
		var poNo = getFCValue(btnEvent, 'purchaseNo');
		var vendorCode = getFCValue(btnEvent, 'vendor.orgCode');
		alert("采购单号:" + poNo + "   " + "供应商编码:" + vendorCode);
		
		//如果按钮不在执行其它js或后台方法, 返回true
		return true;
	}
//-->
</script>
