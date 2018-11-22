<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	table{
		border : 1px solid blue;
	}

</style>
</head>
<body>
<%
String minStr = request.getParameter("minDan");
String maxStr = request.getParameter("maxDan");

//	파싱하기전에 꼭 검증!
if(minStr == null || !minStr.matches("\\d")
		|| maxStr==null || !maxStr.matches("\\d")){
	response.sendError(400);
	return;	//return 타입 void
}
%>
<form> 
	최소단 : <input type="number" name="minDan" value="<%=minStr%>"/>
	최대단 : <input type="number" name="maxDan" value="<%=maxStr%>"/>
	<input type="submit" value="구구단"/>
</form>
<table>
		<%
		int minDan = Integer.parseInt(minStr);
		int maxDan = Integer.parseInt(maxStr);

		String pattern = "%d*%d=%d";
		for(int i=minDan; i<=maxDan; i++){
			%>
			<tr>
			<% 
			for(int j=1; j<=9; j++){
			%>
				<td><%= String.format(pattern, i, j, (i*j)) %></td>
				<% 
			}
			%>
			</tr>
			<%
		}
	%>
	
</table>
<ul>
	<%
		for(int number=1; number<=50; number++){
			%>
			<li><%=number %></li>


			<%
		}
	%>
	<li>1</li>
	<li>2</li>
</ul>
</body>
</html>