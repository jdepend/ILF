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
			<td align="left">1. 在“输入串”的文本域中输入任意字符串<br>
							 2. 点击“查询”按钮
			</td>
		</tr>
		<tr>
			<th>执行结果</th>
			<td align="left">在“输出串”的文本域中显示为“数据:”+输入串的值</td>
		</tr>
		<tr>
			<th>执行原理</th>
			<td align="left">1. 系统中配置了form1绑定的BO1与form2绑定的BO2之间的关联关系<br>
        					 2. 当BO1的值发生变化时，系统会查找出与之相关联的所有BO，进行相应的更新<br>
        					 3. 此范例中，BO2所做的更新为取得BO1中的数据值，组合成新字符串，赋值给自己的属性<br>
        					 4. 将BO2中的属性值返回页面
			</td>
		</tr>
    </table>
    </center>
	<br>
	
	
</body>
</html>

 