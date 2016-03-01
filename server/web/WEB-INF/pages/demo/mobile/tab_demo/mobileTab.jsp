<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page bind="DemoMobileTabBO">
	<qeweb:commandButton name="pageSelect" operate="showAll" text="保存打分" />
	<qeweb:tab id="myTab" bind="DemoMobileTabBO">
		<qeweb:sheet id="s1" text="服务">
			<qeweb:form bind="DemoMobileTabBO" id="showAll">
				<qeweb:radio bind="server_quality" text="服务质量"/>
				<qeweb:radio bind="server_speed" text="服务速度、响应"/>
				<qeweb:radio bind="server_satisfied" text="服务满意度"/>
			</qeweb:form>
		</qeweb:sheet>

		<qeweb:sheet id="s2" text="店面">
			<qeweb:form bind="DemoMobileTabBO" id="showAll">
				<qeweb:radio bind="surr_display" text="店面陈列"/>
				<qeweb:radio bind="surr_clean" text="店面清洁"/>
				<qeweb:radio bind="surr_poster" text="店面海报"/>
			</qeweb:form>
		</qeweb:sheet>

		<qeweb:sheet id="s3" text="人员">
			<qeweb:form bind="DemoMobileTabBO" id="all">
				<qeweb:radio bind="seller_num" text="人员数量充足"/>
				<qeweb:radio bind="seller_colth" text="人员着装统一"/>
			</qeweb:form>
		</qeweb:sheet>
	</qeweb:tab>
</qeweb:page>