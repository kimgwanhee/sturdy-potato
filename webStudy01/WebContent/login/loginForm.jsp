<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.util.Objects"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//**11월13일
	//아이디 체크
	String failedId = request.getParameter("mem_id"); //인증 실패후 가져온 아이디값을 의미
	String message = (String)session.getAttribute("message");//다운캐스팅필요 int x 객체이므로 Integer
	//변경error에서 message로..
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login/loginForm.jsp</title>
<script type="text/javascript">
	<%
	
		if(StringUtils.isNotBlank(message)){//&& error.trim().length()>0 이건 문자열의 경우 
	%>
			alert("<%=message%>"); //꺼낸다음 지워주기
	<%
			session.removeAttribute("message");//session을 참조해서 한번썻으니 지울라구
		}
	%>

</script>

</head>
<body>
<form action="<%= request.getContextPath() %>/login/loginCheck.jsp" method="post">
	<ul>
		<li>
			아이디 : <input type="text" name="mem_id" value="<%=Objects.toString(failedId, "")%>"/>		
		</li>
		<li>
			비밀번호: <input type="password" name="mem_pass" />
			<input type="submit" value="로그인"/>
		</li>
	</ul>
</form>
</body>
</html>