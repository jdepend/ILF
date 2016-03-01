<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page bind="bp_consoleBO_page">
	<qeweb:commandButton name="save" operate="save" text="form.save"/>
	<qeweb:tab id="tab">
		<qeweb:sheet id="sheet1" text="订单管理">
			<qeweb:form id="purchaseOrder" bind="bp_consoleBO"
				layout="5;C2(poImpExl,publishMode,closeMode,closeStatus,lockStatus,poConfirm,poGoodsPlan,poConfirmPlan,feedback),C5(poImpIA),
					C3(poImpExlText,publishModeText,closeModeText,closeStatusText,lockStatusText,poConfirmText,poGoodsPlanText,poConfirmPlanText,feedbackText,poConfirmText,feedbackText)">
				<qeweb:radio bind="poImpExl"/>
				<qeweb:textArea bind="poImpExlText"/>
				<qeweb:radio bind="poImpIA"/>
				<qeweb:radio bind="publishMode"/>
				<qeweb:textArea bind="publishModeText"/>
				<qeweb:radio bind="closeMode"/>
				<qeweb:textArea bind="closeModeText"/>
				<qeweb:radio bind="closeStatus"/>
				<qeweb:textArea bind="closeStatusText"/>
				<qeweb:radio bind="lockStatus"/>
				<qeweb:textArea bind="lockStatusText"/>
				<qeweb:radio bind="poConfirm"/>
				<qeweb:textArea bind="poConfirmText"/>
				<qeweb:radio bind="poGoodsPlan"/>
				<qeweb:textArea bind="poGoodsPlanText"/>
				<qeweb:radio bind="poConfirmPlan"/>
				<qeweb:textArea bind="poConfirmPlanText"/>
				<qeweb:radio bind="feedback"/>
				<qeweb:textArea bind="feedbackText"/>
			</qeweb:form>
		</qeweb:sheet>
		<qeweb:sheet id="sheet2" text="物流管理">
			<qeweb:form id="goodsMag" bind="bp_consoleBO"
				layout="5;C2(deliveryMode,deliverySameOrder,excessDelivery,deliveryFIFO,verifyDelivery,printDlvBarcode,receiveImpExl),
					C3(deliveryModeText,deliverySameOrderText,deliveryFIFOText,
					excessDeliveryText,verifyDeliveryText,printDlvBarcodeText,vendorMagTypeText,receiveImpExlText)">
				<qeweb:radio bind="deliveryMode"/>
				<qeweb:textArea bind="deliveryModeText"/>
				<qeweb:radio bind="deliverySameOrder"/>
				<qeweb:textArea bind="deliverySameOrderText"/>
				<qeweb:radio bind="excessDelivery"/>
				<qeweb:textArea bind="excessDeliveryText"/>
				<qeweb:radio bind="deliveryFIFO"/>
				<qeweb:textArea bind="deliveryFIFOText"/>
				<qeweb:radio bind="verifyDelivery"/>
				<qeweb:textArea bind="verifyDeliveryText"/>
				<qeweb:radio bind="printDlvBarcode"/>
				<qeweb:textArea bind="printDlvBarcodeText"/>
				<qeweb:radio bind="receiveImpExl"/>
				<qeweb:textArea bind="receiveImpExlText"/>
			</qeweb:form>
		</qeweb:sheet>
		<qeweb:sheet id="sheet3" text="主数据管理">
			<qeweb:form id="masterDataMag" bind="bp_consoleBO" layout="5;C2(materialImpExl),C3(materialImpExlText)">
				<qeweb:radio bind="materialImpExl"/>
				<qeweb:textArea bind="materialImpExlText"/>
			</qeweb:form>
		</qeweb:sheet>
		<qeweb:sheet id="sheet4" text="供应商管理">
			<qeweb:form id="vdndorMag" bind="bp_consoleBO" layout="5;C2(vendorMagType),C3(vendorMagTypeText)">
				<qeweb:radio bind="vendorMagType"/>
				<qeweb:textArea bind="vendorMagTypeText"/>
			</qeweb:form>
		</qeweb:sheet>
		<qeweb:sheet id="sheet5" text="预警设置">
			<qeweb:form id="pricewarning" bind="bp_consoleBO" layout="5;C2(pricewarnMaterial),C3(pricewarnMaterialText)"
				text="价格预警设置">
				<qeweb:textField bind="pricewarnMaterial" text="物料价格预警比例%"/>
				<qeweb:textArea bind="pricewarnMaterialText"/>
			</qeweb:form>
		</qeweb:sheet>
		<qeweb:sheet id="sheet6" text="FTP配置">
			<qeweb:form id="ftpMag" bind="bp_FTPConfBO" layout="1" text="FTP配置">
				<qeweb:textField bind="hostName" text="hostname"/>
				<qeweb:textField bind="port" text="port"/>
				<qeweb:textField bind="userName" text="userName"/>
				<qeweb:password bind="password" text="password"/>
				<qeweb:textField bind="uploadPath" text="上传路径"/>
				<qeweb:textField bind="downloadPath" text="下载路径"/>
			</qeweb:form>
		</qeweb:sheet>
	</qeweb:tab>
</qeweb:page>
