<%@page import="kr.or.ddit.utils.CookieUtil"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.util.Objects"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login/loginForm.jsp</title>

<script type="text/javascript">
	<c:if test="${not empty session.message }">
		alert("${session.message}"); //꺼낸다음 지워주기 //flash Attribute
		<c:remove var="message" scope="session"/>
	</c:if>
</script>

</head>
<body>
<form action = "<c:url value='/login/loginCheck.do'/>" method="post">
	<ul>
		<li>
			아이디 : <input type="text" name="mem_id" value="${cookie['indexId']['value']}"/>
			<label>
				<input type="checkbox" name="idChecked" value="idSaved" ${not empty cookie.indexId ? "checked": ""}/>아이디 기억하기 
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