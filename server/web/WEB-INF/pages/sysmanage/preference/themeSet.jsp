<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 个性化设置 --%>
<qeweb:page bind="preferenceSetBO">
	<qeweb:commandButton name="save" operate="save,swapStyleSheet,gotoLocation" text="form.save"/>
	<qeweb:commandButton name="replyDefault" operate="replayStyleSheet,replyDefault,gotoLocation" icon="reset"/>
	<qeweb:tab id="myTab">
		<qeweb:sheet id="s1" text="com.qeweb.sysmanage.preference.bo.PreferenceSetBO.sheet1">
			<qeweb:form id="preferenceSetForm1" bind="preferenceSetBO" layout="3;C2(themeImg)" text="">
				<qeweb:radio bind="themeType" columns="1" />
				<qeweb:img bind="themeImg" text=""/>
			</qeweb:form>
		</qeweb:sheet>
		<qeweb:sheet id="s2" text="com.qeweb.sysmanage.preference.bo.PreferenceSetBO.sheet2">
			<qeweb:form id="preferenceSetForm2" bind="preferenceSetBO" layout="4;C3(menuImg,progressBarPagerImg)" text="">
				<qeweb:radio bind="menuType" columns="1" />
				<qeweb:img bind="menuImg" text=""/>
				<qeweb:radio bind="progressBarPager" columns="1" text="com.qeweb.sysmanage.preference.bo.PreferenceSetGlobalBO.progressBarPager"/>
				<qeweb:img bind="progressBarPagerImg" text=""/>
			</qeweb:form>
		</qeweb:sheet>
		<qeweb:sheet id="s3" text="com.qeweb.sysmanage.preference.bo.PreferenceSetBO.sheet3">
			<qeweb:form id="preferenceSetForm3" bind="preferenceSetBO" text="">
				<qeweb:radio bind="confirmStatus" width="150" />
				<qeweb:blank bind="b1"/>
				<qeweb:blank bind="b2"/>
				<qeweb:radio bind="popupStatus"/>
				<qeweb:blank bind="b3"/>
				<qeweb:blank bind="b4"/>
				<qeweb:radio bind="tipType" columns="1"/>
				<qeweb:blank bind="b5"/>
				<qeweb:blank bind="b6"/>
				<qeweb:select bind="showTipsDelay" />
			</qeweb:form>
		</qeweb:sheet>
		<qeweb:sheet id="s4" text="com.qeweb.sysmanage.preference.bo.PreferenceSetBO.sheet4">
			<qeweb:form id="preferenceSetForm4" bind="preferenceSetBO" text="">
				<qeweb:radio bind="language"/>
			</qeweb:form>
		</qeweb:sheet>
		<qeweb:sheet id="s5" text="com.qeweb.sysmanage.preference.bo.PreferenceSetBO.sheet5">
			<qeweb:form id="preferenceSetForm5" bind="preferenceSetBO" layout="1" text="">
				<qeweb:radio bind="logoType"/>
				<qeweb:fileField bind="logoImg" operate="upload"/>
				<qeweb:label bind="logoImgSize" text=""/>
				<qeweb:textField bind="bottomMsg"/>
			</qeweb:form>
		</qeweb:sheet>
	</qeweb:tab>
</qeweb:page>

<script type="text/javascript">
<!--
	var swapFlag = false;

	/**
	 * 动态改变页面样式
	 */
	function swapStyleSheet() {
		var css = appConfig.ctx + '/framework/js/ext/ext3.2/resources/css/xtheme-' +
			Ext.getCmp('preferenceSetForm1-preferenceSetBO-themeType').getValue().value + ".css";
		return chageCss(css);
	}
	
	/**
	 * 回复默认样式
	 */
	function replayStyleSheet() {
		var css = appConfig.ctx + '/framework/js/ext/ext3.2/resources/css/xtheme-' + '<%=DisplayType.getThemeType() %>' + ".css";
		return chageCss(css);
	}
	
	function chageCss(css) {
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
//-->
</script>