<%@page import="java.util.Map.Entry"%>
<%@page import="kr.or.ddit.web.SimpleFormProcessServlet"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--     **11월13일 -->
<%!

	public Map<String, String[]> singerMap = new LinkedHashMap<>();
{
	singerMap.put("B001", new String[]{"뷔", "/WEB-INF/bts/v.jsp"});
	singerMap.put("B002", new String[]{"지민", "/WEB-INF/bts/jimin.jsp"});
	singerMap.put("B003", new String[]{"진", "/WEB-INF/bts/jin.jsp"});
	singerMap.put("B004", new String[]{"정국", "/WEB-INF/bts/jungguk.jsp"});
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>05/btsForm.jsp</title>
<script type="text/javascript">
	function eventHandler(){
		// <form name = "btsFrom" ..document의 새로운 프로퍼티명이 된다
// 		document.btsForm.submit();
		document.forms[0].submit();
	}
</script>
</head>
<body>
<form name = "btsFrom" action= "<%=request.getContextPath() %>/05/getBTS.jsp" method="post">
	<select name="member" onchange="eventHandler();">
		<option value="">멤버선택</option>
		<%
			String pattern = "<option value='%s'>%s</option>";
			
			for(Entry<String, String[]> entry : singerMap.entrySet()){
				String key = entry.getKey();
				String value= entry.getValue()[0];
				out.println(String.format(pattern, key, value));
			}
		%>
	</select>
</form>
	
</body>
</html>