<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<html>
	<body>
		在此处添加项目页面<br>
		path:/pages/sysmanage/login/ext/workbench.jsp
	</body>
</html>

<script type="text/javascript">
Ext.onReady(function() {
	new Ext.ux.TipWindow({
		title : '<span class=commoncss>提示</span>',
		html : '您有[0]条未读信息<a href=http://www.baidu.com>baiduc</a>',
		iconCls : 'commentsIcon'
	}).show(Ext.getBody());
});
</script>