<%@page import="java.math.BigDecimal"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>02/factorial.jsp</title>
</head>
<body>
<form action="<%= request.getContextPath()%>/02/factorial.jsp">
	Factorial operand : <input type="number" min="1" max="100" 
							name="operand" value="<%= request.getParameter("operand") %>" />
	<button type="submit">전송</button>
</form>
<%
	String op = request.getParameter("operand");
//검증
	if(op==null || !op.matches("\\d{2}|100")){
		return;
	}

	int num = Integer.parseInt(op);
%>

<%! 
	BigDecimal factorial(int num){
		if(num<0){
			// 런타임x IllegalArgumentException
			throw new IllegalArgumentException("음수는 팩토리얼 연산 불가");
		}else if (num <= 1){
			return new BigDecimal(num-1);
		}else{ 
			return new  BigDecimal(num).multiply(factorial(num-1));
		}
	}
%>
<div>
	<%= factorial(num)%>
</div>
</body>
</html>