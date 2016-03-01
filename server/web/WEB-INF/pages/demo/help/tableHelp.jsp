<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=ctx %>/css/styleTemp.css">

<html>
<body>
	<br>
	<center>
	<table class="helpTable" width="100%">
		<tr>
			<td align="left">
				<p>菜单树MenuBO是BO的一种,它是数据结构是树,每一个节点都看作一个MenuBO.</p>
				<p>菜单通过数据库配置,其结构如下:</p>
				<p><img src="<%=ctx %>/images/demo/menu.jpg"></p>
				<br>
				<p>业务流程通过流程配置工具实现,每个控制区组件都绑定一个BO的方法,单击控制区按钮后将跳转到流程管理器GA,</p>
				<p>GA执行相应的BO方法后根据流程映射文件跳转到用户指定的页面.</p>
				<p>流程映射数据库结构如下:</p>
				<p><img src="<%=ctx %>/images/demo/ga.jpg"></p>
			</td>
		</tr>
    </table>
    </center>
	<br>
	
	
</body>
</html>

 