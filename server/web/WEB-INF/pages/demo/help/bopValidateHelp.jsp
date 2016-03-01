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
			<td align="left">1. 前台：必须输入数字，数字从5开始，步长为5，不得超过60，例如：5、10、15...<br>
      						 2. 后台：数据必须输入500
			</td>
		</tr>
		<tr>
			<th>执行结果</th>
			<td align="left">1. 前台：如果不符合规则，系统会实时通过红色波浪线提示错误，如果符合规则，则正常显示<br>
        					 2. 后台：如果不符合规则，当焦点离开时会通过红色波浪线提示错误，如果符合规则，则正常显示
			</td>
		</tr>
		<tr>
			<th>执行原理</th>
			<td align="left">1. 前台：系统根据取值规则，实时校验用户输入的正确性，此校验通过前台页面直接完成<br>
        					 2. 后台：焦点离开时，系统将用户的输入返回到后台，经校验函数校验，将结果返回前台页面显示
			</td>
		</tr>
    </table>
    </center>
	<br>
	
	
</body>
</html>

 