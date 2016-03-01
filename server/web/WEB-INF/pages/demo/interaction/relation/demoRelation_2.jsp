<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 细粒度关联粗粒度示例 -->
<qeweb:page onLoad="demoFCRelationBO2.sysFresh">

	
	<qeweb:table id="demoFCRelationBO2" bind="demoFCRelationBO"
		editCell="length,width,money,percent,total" text="表格中的细粒度关联粗粒度">
		<qeweb:commandButton name="sysAddRow" operate="sysAddRow" />
		<qeweb:commandButton name="sysDelRow" operate="sysDelRow" />
		<qeweb:commandButton name="save" operate="save" text="form.save"/>
		<qeweb:expend>
			<qeweb:commandButton name="submit" operate="submit" text="提交"></qeweb:commandButton>
		</qeweb:expend>
		<qeweb:hidden bind="id"/>
		<qeweb:textField bind="length" text="长"/>
		<qeweb:textField bind="width" text="宽"/>
		<qeweb:textField bind="acreage" text="面积"/>
		<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
		<qeweb:textField bind="money" text="金额"/>
		<qeweb:select bind="percent" text="百分比"/>
		<qeweb:label bind="total" text="总额"/>
		<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
		<qeweb:textField bind="vendor.orgName" text="供应商名称"/>
		<qeweb:dateField bind="purchaseTime" align="center" text="采购时间" width="100"/>
		<qeweb:select bind="publishStatus" text="发布状态" align="center"/>
	</qeweb:table>
</qeweb:page>

