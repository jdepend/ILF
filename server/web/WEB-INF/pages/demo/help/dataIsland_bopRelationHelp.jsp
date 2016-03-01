<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=ctx %>/css/styleTemp.css">

<html>
<body>
	<br>
	<center>
	<table align='right' class="helpTable">
		<tr>
			<th rowspan="3" width="10%">帮助</th>
			<th width="15%">执行步骤</th>
			<td align="left">1. 选择某个具体“省份”<br>
							 2. 选择某个具体“城市”
			</td>
		</tr>
		<tr>
			<th>执行结果</th>
			<td align="left">1. 选择具体“省份”时，系统加载了“城市”的数据<br>
							 2. 选择具体“城市”时，系统加载了“区”的数据（仅吉林市有数据）
			</td>
		</tr>
		<tr>
			<th>执行原理</th>
			<td align="left">1. 查看JSP源码时，页面中没有任何Ajax的代码，系统实现后台驱动<br>
							 2. 系统中配置了省市区三个BOP的的关联关系<br>
        					 3. 当省BOP的值发生变化时，系统会查出与之相关联的所有BOP（这里是市BOP），进行相应的更新<br>
        					 4. 将页面中市BOP的值进行更新<br>
        					 5. 当市BOP的值发生变化时，系统会查出与之相关联的所有BOP（这里是区BOP），进行相应的更新<br>
        					 6. 将页面中区BOP的值进行更新
			</td>
		</tr>
    </table>
    </center>
	<br>
	
	
</body>
</html>

 