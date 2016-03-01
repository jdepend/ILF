<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 树示例: 选择树 -->
<qeweb:page layout="3">
	<qeweb:checkTree bind="demoTreeBO2" id="demoTreeBO_1" text="选择树-多选树"/>
	<qeweb:checkTree bind="demoTreeBO_2_check_singleOpt" id="demoTreeBO_3" text="选择树-复选树-仅叶子节点可选择"/>
	<qeweb:checkTree bind="demoTreeBO_2_check_noCascade" id="demoTreeBO_5" text="选择树-复选树-非级联"/>
	
	<qeweb:checkTree bind="demoTreeBO_2_radio" id="demoTreeBO_2" text="选择树-单选树"/>
	<qeweb:checkTree bind="demoTreeBO_2_radio_singleOpt" id="demoTreeBO_4" text="选择树-单选树-仅叶子节点可选择"/>
</qeweb:page>