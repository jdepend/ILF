<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 全局设置 --%>
<qeweb:page bind="preferenceSetGlobalBO">
	<qeweb:commandButton name="save" operate="save,swapStyleSheet,gotoLocation" text="form.save"/>
	<qeweb:tab id="myTab">
		<qeweb:sheet id="s1" text="com.qeweb.sysmanage.preference.bo.PreferenceSetBO.sheet1">
			<qeweb:form id="preferenceSetForm1" bind="preferenceSetGlobalBO" layout="3;C2(themeImg)" text="">
				<qeweb:radio bind="themeType" columns="1" text="com.qeweb.sysmanage.preference.bo.PreferenceSetBO.themeType"/>
				<qeweb:img bind="themeImg" text=""/>
			</qeweb:form>
		</qeweb:sheet>
		<qeweb:sheet id="s2" text="com.qeweb.sysmanage.preference.bo.PreferenceSetBO.sheet2">
			<qeweb:form id="preferenceSetForm2" bind="preferenceSetGlobalBO" layout="4;C3(menuImg,progressBarPagerImg),C4(msgTarget,msgTargetImg)" text="">
				<qeweb:radio bind="menuType" columns="1" text="com.qeweb.sysmanage.preference.bo.PreferenceSetBO.menuType"/>
				<qeweb:img bind="menuImg" text=""/>
				<qeweb:radio bind="progressBarPager" columns="1"/>
				<qeweb:img bind="progressBarPagerImg" text=""/>
				<qeweb:radio bind="msgTarget" columns="1" text="com.qeweb.sysmanage.preference.bo.PreferenceSetBO.msgTarget"/>
				<qeweb:img bind="msgTargetImg" text=""/>
			</qeweb:form>
		</qeweb:sheet>
		<qeweb:sheet id="s3" text="com.qeweb.sysmanage.preference.bo.PreferenceSetBO.sheet3">
			<qeweb:form id="preferenceSetForm3" bind="preferenceSetGlobalBO" text="">
				<qeweb:radio bind="confirmStatus" text="com.qeweb.sysmanage.preference.bo.PreferenceSetBO.confirmStatus"/>
				<qeweb:blank bind="b1"/>
				<qeweb:blank bind="b2"/>
				<qeweb:radio bind="popupStatus" text="com.qeweb.sysmanage.preference.bo.PreferenceSetBO.popupStatus"/>
				<qeweb:blank bind="b3"/>
				<qeweb:blank bind="b4"/>
				<qeweb:radio bind="tipType" columns="1" text="com.qeweb.sysmanage.preference.bo.PreferenceSetBO.tipType"/>
				<qeweb:blank bind="b5"/>
				<qeweb:blank bind="b6"/>
				<qeweb:select bind="showTipsDelay" width="50" text="com.qeweb.sysmanage.preference.bo.PreferenceSetBO.showTipsDelay"/>
			</qeweb:form>
		</qeweb:sheet>
		<qeweb:sheet id="s4" text="com.qeweb.sysmanage.preference.bo.PreferenceSetBO.sheet4">
			<qeweb:form id="preferenceSetForm4" bind="preferenceSetGlobalBO" text="">
				<qeweb:radio bind="language" text="com.qeweb.sysmanage.preference.bo.PreferenceSetBO.language"/>
			</qeweb:form>
		</qeweb:sheet>
		<qeweb:sheet id="s5" text="com.qeweb.sysmanage.preference.bo.PreferenceSetBO.sheet5">
			<qeweb:form id="preferenceSetForm5" bind="preferenceSetGlobalBO" layout="1" text="">
				<qeweb:radio bind="logoType" text="com.qeweb.sysmanage.preference.bo.PreferenceSetBO.logoType"/>
				<qeweb:fileField bind="logoImg" operate="upload" text="com.qeweb.sysmanage.preference.bo.PreferenceSetBO.logoImg"/>
				<qeweb:label bind="logoImgSize" text=""/>
				<qeweb:textField bind="bottomMsg" text="com.qeweb.sysmanage.preference.bo.PreferenceSetBO.bottomMsg"/>
			</qeweb:form>
		</qeweb:sheet>
		<qeweb:sheet id="s6" text="com.qeweb.sysmanage.preference.bo.PreferenceSetBO.sheet6">
			<qeweb:form id="preferenceSetForm6" bind="preferenceSetGlobalBO" 
				layout="3;C2(allowMaxSize,allowedType,notAllowedType,multifileMaxNum,multifileMaxSize,uploadPath)" text="">
				<qeweb:textField bind="allowMaxSize"/>
				<qeweb:textField bind="allowedType"/>
				<qeweb:textField bind="notAllowedType"/>
				<qeweb:textField bind="multifileMaxNum"/>
				<qeweb:textField bind="multifileMaxSize"/>
				<qeweb:textField bind="uploadPath"/>
			</qeweb:form>
		</qeweb:sheet>
		<qeweb:sheet id="s7" text="com.qeweb.sysmanage.onlineuser.OnlineUserMgt">
			<qeweb:form id="onlineUserMgt" bind="onlineUserMgt" param="required" queryRange="true" layout="2;C2(remark)" text="">
				<qeweb:textField bind="timeoutDisplay" />
				<qeweb:label bind="remark"/>
			</qeweb:form>
		</qeweb:sheet>
		<qeweb:sheet id="s8" text="com.qeweb.sysmanage.preference.bo.PreferenceSetBO.sheet7">
			<qeweb:form id="preferenceSetForm7" bind="preferenceSetGlobalBO" text="">
				<qeweb:select bind="tablePageSize"/>
				<qeweb:blank bind="blank1"/>
				<qeweb:blank bind="blank2"/>
				<qeweb:radio bind="tableColumnMove"/>
				<qeweb:blank bind="blank3"/>
				<qeweb:blank bind="blank4"/>
				<qeweb:radio bind="hasSort"/>
				<qeweb:blank bind="blank5"/>
				<qeweb:blank bind="blank6"/>
				<qeweb:radio bind="tbtnAlign"/>
				<qeweb:blank bind="blank7"/>
				<qeweb:blank bind="blank8"/>
				<qeweb:radio bind="rbtnAlign"/>
				<qeweb:blank bind="blank9"/>
				<qeweb:blank bind="blank10"/>
				<qeweb:radio bind="saveCase"/>
				<qeweb:blank bind="blank11"/>
				<qeweb:blank bind="blank12"/>
				<qeweb:radio bind="autoSearch"/>
				<qeweb:blank bind="blank13"/>
				<qeweb:blank bind="blank14"/>
				<qeweb:radio bind="tableSetting"/>
				<qeweb:blank bind="blank15"/>
				<qeweb:blank bind="blank16"/>
				<qeweb:radio bind="hasCloseBtn"/>
				<qeweb:blank bind="blank17"/>
				<qeweb:blank bind="blank18"/>
				<qeweb:textField bind="numberScale"/>
				<qeweb:blank bind="blank19"/>
				<qeweb:blank bind="blank20"/>
			</qeweb:form>
		</qeweb:sheet>
	</qeweb:tab>
</qeweb:page>

<script type="text/javascript">
	var swapFlag = false;
	
	/**
	 * 动态改变页面样式
	 */
	function swapStyleSheet() {
		var css = appConfig.ctx + '/framework/js/ext/ext3.2/resources/css/xtheme-' +
			Ext.getCmp('preferenceSetForm1-preferenceSetGlobalBO-themeType').getValue().value + ".css";
		
		//使用borderlayout时
		if(typeof window.parent.isUseBorderLayout == 'function' && window.parent.isUseBorderLayout()) {
			if(confirm(I18N.CONFIRM_THEMESET)) {
				swapFlag = true;
			}
			else {
				return false;
			} 
		}
		else {
			Ext.util.CSS.swapStyleSheet('theme', css);
			window.parent["iframeLeft"].Ext.util.CSS.swapStyleSheet('theme', css);
			window.parent["iframeTop"].Ext.util.CSS.swapStyleSheet('theme', css);
		}
	}
	
	function gotoLocation() {
		if(swapFlag)
			window.parent.location.href = appConfig.ctx + actionURL.getRelogin();
	}
</script>