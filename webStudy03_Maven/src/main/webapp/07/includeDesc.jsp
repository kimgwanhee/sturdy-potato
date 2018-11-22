<%@page import="kr.or.ddit.vo.AlbasengVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>07/includeDesc.jsp</title>
</head>
<body>
<h4> Include </h4>
<pre>
		//11월15일 -4
		1)시점
		2)가지고오는대상을 비교해서 볼것
		 
	1. 동적 include : 실행 중(runtime), 실행 결과물들이 include   //버퍼안에만 가져왔던것 소스는 사라지고 이미없다. 그래서 아래 지시자는 에러남
					서버사이드 페이지 모듈화에 주로 사용됨.
					1) RequestDispatcher를 사용
					2) PageContext를 사용
					3) 액션 태그를 사용
					JSP스펙에 따라 기본적으로 제공되는 커스텀 태그.
					%lt;prefix : tagname attributes..../&gt;
						-forward : request dispatch 방식의 forward
						-include : request dispatch 방식의 include
						-useBean
						(setProperty/getProperty)
						//AlbasengVO 곡주석확인해보기
					<!--  아래 jsp:useBean jsp주석으로 묶임->서버사이드					 -->
					<jsp:useBean id="albaVO" class="kr.or.ddit.vo.AlbasengVO" scope="page"></jsp:useBean>
					
					<jsp:setProperty property="name" name="albaVO" value="이름"/>
					
<!-- 					age라는 파라미터를 잡은다음 그값을 넣는다???? -->
<%-- 					<jsp:setProperty property="age" name="albaVO" param="age"/> --%>
					<jsp:setProperty property="*" name="albaVO"/> <%-- 위와비교 -알아서 넣어준다..파라미터를 잡아서 vo로 바인딩할때 이렇게활용!!--%>
					
<!-- 					그리고 이제 꺼내기  alva.getname이라는게 만들어진다?-->
					<jsp:getProperty property="name" name="albaVO"/>
					<jsp:getProperty property="age" name="albaVO"/>

					<%--
						AlbasengVO albaVO = (AlbasengVO)pageContext.getAttribute("albaVO");
						if(albaVO==null){//없다는것
							//그러면 생성
							albaVO = new AlbasengVO();
							//그리고 해당스코프에 넣어주기
							pageContext.setAttribute("albaVO", albaVO);
						}
						albaVO.setName("이름");
						
						albaVO.setAge(new Integer(request.getParameter("get")));//자바코드로 따지면 이런것
					--%>
						<%=albaVO %>
		<%
			pageContext.include("/includee/codeFragment.jsp");
			
		%> 
	2. 정적 include(static) : 실행 전(소스만 존재), 소스 자체가 include되는것
							include 지시자 이용
							중복코드 해결에 주로 사용.
							웹어플리케이션에 전역적으로 정적 include를 할때ㅡ
							web.xml의 include-code/pleud등이 활용됨(비추!!!).
<%-- 							<%@ include file="/includee/codeFragment.jsp"%> --%>
<%-- 							<%= varAtFrag.length() %> --%>
							//정적 - 위껄 주석처리하면 위의소스자체가 안돌아서 varAtFrag가 에러남
							//그래서 web.xml가서 <jsp-config>추가 -> 그다지 권장사항은 아니다.
		
	
	
</pre>
</body>
</html>