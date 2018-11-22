<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@page import="kr.or.ddit.web.modulize.ServiceType"%>
<%@page import="kr.or.ddit.web.IndexType"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%
	MemberVO authMember = (MemberVO) session.getAttribute("authMember");
	String command = request.getParameter("command");//일단 비교하려 가져오려는 커맨드가져오기
	//1. 파라미터없는경우
	//2. 있는데 그런서비스를 제공하지않는경우
	//3. 파라미터넘어왔고 서비스도 제공하는경우
	//검증
	int statusCode = 0;
	String includePage = "";
	if (StringUtils.isNotBlank(command)) {//1. 있다
		try {
			ServiceType sType = ServiceType.valueOf(command.toUpperCase());
			//들어왔을 때 어디로 include해줄건지 주소가 필요
			includePage = sType.getServicePage();
		} catch (IllegalArgumentException e) {
			statusCode = HttpServletResponse.SC_NOT_FOUND;
		}
	}
	if (statusCode != 0) {
		response.sendError(statusCode);
		return;
	}
	//이제 include가 null일경우와 아닐경우로 나뉨 아래로..
%>
<meta charset="UTF-8">
<title>/index.jsp</title>
<link href="<%=request.getContextPath()%>/css/main.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.3.1.min.js"
	integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
	crossorigin="anonymous"></script>

</head>
<body>
	<div id="top">
		<jsp:include page="/includee/header.jsp"></jsp:include>
	</div>
	<div id="left">
		<jsp:include page="/includee/left.jsp"></jsp:include>
	</div>
	<div id="body">
		<%
			if (StringUtils.isNotBlank(includePage)) {
				pageContext.include(includePage);
			} else {
		%>
		<h4>웰컴 페이지</h4>
		<pre>
			<!-- 1. 파라미터가있을때.. -->
			<!-- 2. 제공할수 있는 서비스면 동적으로 이동  -->
			<!-- 	제공할수 없는 서비스면 찾고있는 리소스가 우리한테없다는것 notfound내보내기 -->
			<!-- 	enum이라는 문법 활용해보기  -->
				11월 14일 -7
				처음부터 웰컴 페이지로 접속하거나,
				로그인에 성공해서 웰컴 페이지로 접속하는 경우의 수가 있음.
				
				<%
				if (authMember!=null) {
				%>
				<a href="<%=request.getContextPath()%>/member/mypage.do"><%=authMember.getMem_name()%></a>님(<%=authMember.getMem_auth() %>) 로그인상태, 
				<a	href="<%=request.getContextPath()%>/login/logout.jsp">로그아웃</a>
				<%
					} else {
				%>
					<a href="javascript:goIndex('LOGIN');">로그인 하러가기</a>
			<%
				}
			%>
		<%
			}
		%>
</pre>
	</div>
	<div id="footer">
		<%
			pageContext.include("/includee/footer.jsp");
		%>
	</div>
</body>
</html>