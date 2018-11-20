<%@page import="kr.or.ddit.utils.CookieUtil"%>
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
	
	String idChecked= new CookieUtil(request).getCookieValue("id");//있다라면 아이디가 들어가고 없으면 null값이 들어갈것
   //쿠키를 받아옴
   
	String cookie = new CookieUtil(request).getCookieValue("indexId");
   
//    if(mem_id==null || mem_id.trim().length()==0){
//       mem_id="";
//    }
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
			아이디 : <input type="text" name="mem_id" value="<%=StringUtils.isNotBlank(cookie)?cookie:""%>"/>
					
			<label>
				<input type="checkbox" name="idChecked" value="idSaved" <%=StringUtils.isNotBlank(cookie)? "checked":"" %> />아이디 기억하기 
			</label>
<!-- 			체크하면 3개가넘어가고 3번째 넘어가는 파라미터이름이 idChecked으로 넘어갈텐데 넘어가면서 이름은 idSaved로 넘어감  -->
<!-- 			체크를 안하면 두개의 파라미터만 넘어감// 체크안하면 파라미터 자체 형성이 안됨 -->
		</li>
		<li>
			비밀번호: <input type="password" name="mem_pass" />
			<input type="submit" value="로그인"/>
		</li>
	</ul>
</form>
</body>
</html>