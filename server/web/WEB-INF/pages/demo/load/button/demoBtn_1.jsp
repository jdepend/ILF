<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 表单示例:全部按钮样式-->
<qeweb:page>
	<qeweb:form bind="demoButtonBO" id="a" text="">
		<qeweb:commandButton name="t1" icon="back" text="后退"/>
		<qeweb:commandButton name="t2" icon="claer" text="清空"/>
		<qeweb:commandButton name="t22" icon="reset" text="重置"/>
		<qeweb:commandButton name="t21" icon="restore" text="还原"/>
		<qeweb:commandButton name="t3" icon="edit" text="修改"/>
		<qeweb:commandButton name="t4" icon="view" text="查看"/>
		<qeweb:commandButton name="t5" icon="remove" text="删除"/>
		<qeweb:commandButton name="t6" icon="save" text="保存"/>
		<qeweb:commandButton name="t66" icon="add" text="新增"/>
		<qeweb:commandButton name="t7" icon="search" text="查询"/>
	</qeweb:form>
	<qeweb:form bind="demoButtonBO" id='b' text="">
		<qeweb:commandButton name="t124" icon="file" text="文件"/>
		<qeweb:commandButton name="t125" icon="folder" text="文件夹"/>
		<qeweb:commandButton name="t11" icon="print" text="打印"/>
		<qeweb:commandButton name="t12" icon="concurrency" text="并发"/>
		<qeweb:commandButton name="t13" icon="resetPwd" text="重置密码"/>
		<qeweb:commandButton name="t14" icon="setting" text="设置"/>
		<qeweb:commandButton name="t15" icon="no" text="no"/>
		<qeweb:commandButton name="t16" icon="yes" text="yes"/>
		<qeweb:commandButton name="t17" icon="hmenu-lock" text="锁定"/>
		<qeweb:commandButton name="t18" icon="hmenu-unlock" text="解锁"/>
		<qeweb:commandButton name="t19" icon="check" text="选择"/>
	</qeweb:form>
	<qeweb:form bind="demoButtonBO" id="c" text="">
		<qeweb:commandButton name="t20" icon="prev" text="上一步"/>
		<qeweb:commandButton name="t19" icon="next" text="下一步"/>
		<qeweb:commandButton name="t21" icon="refresh" text="刷新"/>
		<qeweb:commandButton name="t22" icon="expExl" text="导出Exl"/>
		<qeweb:commandButton name="t23" icon="chart" text="图表"/>
		<qeweb:commandButton name="t24" icon="imp" text="导入"/>
		<qeweb:commandButton name="t25" icon="exp" text="导出"/>
		<qeweb:commandButton name="t26" icon="redo" text="重做"/>
		<qeweb:commandButton name="t27" icon="redo2" text="重做"/>
		<qeweb:commandButton name="t28" icon="applicationIcon" text="窗口"/>
		<qeweb:commandButton name="t29" icon="application_doubleIcon" text="双窗口"/>
		<qeweb:commandButton name="t30" icon="application_cascadeIcon" text="叠加窗口"/>
	</qeweb:form>
	<qeweb:form id="d" bind="demoButtonBO" text="">
		<qeweb:commandButton name="t7" icon="upload" text="上传"/>
		<qeweb:commandButton name="t8" icon="downLoad" text="下载"/>
		<qeweb:commandButton name="t9" icon="execute" text="执行"/>
		<qeweb:commandButton name="t10" icon="exit" text="退出"/>
		<qeweb:commandButton name="t11" icon="clear" text="清空"/>
		<qeweb:commandButton name="t122" icon="warning" text="警告"/>
		<qeweb:commandButton name="t123" icon="syn" text="同步"/>
		<qeweb:commandButton name="t27" icon="icon-expand-all" text="展开"/>
		<qeweb:commandButton name="t28" icon="icon-collapse-all" text="收缩"/>
		<qeweb:commandButton name="t26" icon="config" text="配置"/>
		<qeweb:commandButton name="t25" icon="keyIcon" text="钥匙"/>
		<qeweb:commandButton name="t30" icon="commentsIcon" text="消息"/>
	</qeweb:form>
</qeweb:page>
