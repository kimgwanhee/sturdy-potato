<%@page import="javax.servlet.descriptor.TaglibDescriptor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.green {
	color: green;
}

.yellow {
	color: yellow;
}

.red {
	color: red;
}

.silver {
	color: silver;
}

.apua {
	color: apua;
}

.orange {
	color: orange;
}
</style>
</head>
<body>
	<form >
		주소입력 : <input type="url" name="siteUrl" value="${siteUrl}" placeholder="https://www.naver.com"/>
		<label><input type="checkbox" name="toSource" value="source"/>소스로보기</label>
		<input type="submit" value="GO!!!"/>
	</form>
	<br><br><br><br><br>
	<c:if test="${not empty param.siteUrl }">
		<c:import url="${param.siteUrl}" var="url" />
		<div style="border : 1px solid black;">
			<c:out value="${url}" escapeXml="${empty param.toSource ? false : true}"/>
		</div>
	</c:if>
	
	<form>
		당신의 나이는? <input type="number" name="age" value="${param.age}" /> <input
			type="submit" value="전송" />
	</form>
	<!-- 	age파라미터가 있다면,  -->
	<!-- 	1. age가 20대면 "방갑다 칭구!" green -->
	<!-- 	2. age가 30대면 "나이 너무많타.." yellow-->
	<!-- 	3. age가 40대면 '담배끊고 건강관리~' red-->
	<!-- 	4. age가 50대면 '아빠?' silver-->
	<!-- 	5. age가 그 이상이면...? '무슨일로 오셨나염?' aqua-->
	<!-- 	6. age가 20대 미만이면 ,'나가 놀아라 ~ ' orange-->

	<!-- 	조건 -->

	<c:set var="age" value="${param.age}" />

	<c:if test="${not empty age}">
		<c:choose>
			<c:when test="${age lt 20}">
				<c:set var="message" value="나가 놀아라 ~" />
				<c:set var="clz" value="green" />
			</c:when>
			<c:when test="${age ge 20 and age lt 30 }">
				<c:set var="message" value="방갑다 칭구!" />
				<c:set var="clz" value="yellow" />
			</c:when>
			<c:when test="${age ge 30 and age lt 40 }">
				<c:set var="message" value="재익아자씨?..." />
				<c:set var="clz" value="red" />
			</c:when>
			<c:when test="${age ge 40 and age lt 50 }">
				<c:set var="message" value="담배끊고 건강관리~" />
				<c:set var="clz" value="silver" />
			</c:when>
			<c:when test="${age ge 50 and age lt 60 }">
				<c:set var="message" value="아빠?" />
				<c:set var="clz" value="aqua" />
			</c:when>

			<c:otherwise>
				<c:set var="message" value="무슨일로 오셨나염?" />
				<c:set var="clz" value="orange" />
			</c:otherwise>
		</c:choose>


		<div id="msgArea" class="${clz}">${message}</div>
	</c:if>

	<!-- 	2단부터 9단까지 구구단을  승수1~9까지 table 태그로 출력. -->
	<!-- 	첫번째 단은 파란색배경 -->
	<!-- 	네번째 단은 빨간색배경 -->

	<c:set var="minDan" value="${empty param.minDan?2:param.minDan}"></c:set>
	<c:set var="maxDan" value="${empty param.maxDan?9:param.maxDan}"></c:set>
	<form>
		최소단 : <input type="number" name="minDan" value="${minDan }"/>
		최소단 : <input type="number" name="maxDan" value="${maxDan }"/>
		<input type="submit" value="구구단"/>
	</form>
	<table>
		<c:forEach var="dan" begin="${minDan }" end="${maxDan }" varStatus="lts">
			<c:set var="colorClz" value="normal"></c:set>
			<c:if test="${lts.first}">
				<c:set var="colorClz" value="red"></c:set>
			</c:if>
			<c:if test="${lts.count eq 4}">
				<c:set var="colorClz" value="orange"></c:set>
			</c:if>
			<tr class="${colorClz }">
				<c:forEach var="mul" begin="1" end="9">
					<td>${dan}*${mul}=${dan*mul}</td>
				</c:forEach>
			<tr>
		</c:forEach>
	</table>
</body>
</html>