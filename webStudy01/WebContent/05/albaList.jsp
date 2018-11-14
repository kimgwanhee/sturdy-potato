<%@page import="kr.or.ddit.vo.AlbasengVO"%>
<%@page import="kr.or.ddit.web.SimpleFormProcessServlet"%>
<%@page import="java.util.Map.Entry"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>05/albaList.jsp</title>
</head>
<body>
<table>
	<thead>
		<tr>
			<th>알바생코드</th>
			<th>이름</th>
			<th>주소</th>
			<th>연락처</th>
		</tr>
	</thead>
	<tbody>
<!-- 		//여긴 맵의 사이즈만큼.. -->
<!-- 		//맵안에서 엔트리의 key와 value를 뽑으면.. 정보나옴  -->
	<%
	//**11월13일
		String pattern="<td>%s</td>";
		for(Entry<String,AlbasengVO> entry: SimpleFormProcessServlet.albasengs.entrySet()){
			out.print("<tr>");
			out.print(String.format(pattern, entry.getValue().getCode()));
			out.print(String.format(pattern, entry.getValue().getName()));
			out.print(String.format(pattern, entry.getValue().getAddress()));
			out.print(String.format(pattern, entry.getValue().getTel()));
			out.print("</tr>");
			
		}
	%>
	</tbody>
</table>
</body>
</html>