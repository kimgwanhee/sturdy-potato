<%@page import="java.util.TimeZone"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Locale"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.ddit.or.kr/dateFn" prefix="dateFn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>12/fmtExample.jsp</title>
<script type="text/javascript">
	function changeHandler(){
		document.localeForm.submit();
	}
</script>
</head>
<body>
<h4>Locale에 따른 포맷팅 처리</h4>
<!-- //현재 클라이언트의 기본 로케일 , 현재 서버의 기존타임존 셋팅-->
<c:set var="clientLocale" value="${pageContext.request.locale}" />
<c:set var="clientTimezone" value="${dateFn:getDefaultTimeZone() }" />
<c:choose>
	<c:when test="${not empty param.locale }">
		<c:set var="pageLocale" value="${clientLocale.forLanguageTag(param.locale)}" ></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="pageLocale" value="${clientLocale}"></c:set>
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${not empty param.timezone}">
		<c:set var="pageTimeZone" value="${clientTimezone.getTimeZone(param.timezone)}" />
	</c:when>
	<c:otherwise>
		<c:set var="pageTimeZone" value="${clientTimezone}" />
	</c:otherwise>
</c:choose>

<form name="localeForm">
	<select name="locale" onchange="changeHandler();">
		<option value="">로케일선택</option>
		<c:forEach items="${dateFn:getAvaliableLocales() }" var="locale">
			<c:if test="${not empty locale.displayName }">
			<option value="${locale.toLanguageTag()}" >${locale.displayName }</option>
			</c:if>
		</c:forEach>
	</select>
	
	<select name="timezone" onchange="changeHandler();">
		<option value="">타임존 선택</option>
		<c:forEach items="${dateFn:getAvailableIDs() }" var="zoneId">
			<option value='${zoneId}'>${clientTimezone.getTimeZone(zoneId).displayName}</option>
<!-- 			이제 clientTimezone객체를 통해 메서드 호출 -> 그리고 저안에서 타임존 객체가 만들어짐.displayName -->
		</c:forEach>
	</select>
</form>
<script type="text/javascript">
	document.localeForm.locale.value="${pageLocale.toLanguageTag() }";
	document.localeForm.timezone.value="${pageTimeZone.ID}";
</script>
<fmt:setLocale value="${pageLocale }"/>
<table>
	<thead>
		<tr>
			<th>언어</th>
			<th>지역</th>
			<th>해당 지역의 통화(1000을 기준)</th>
			<th>현재 시각을 해당 언어 방식으로</th>
			<th>선택한 타임존의 현재 시각</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>${pageLocale.displayLanguage}</td>
			<td>${pageLocale.displayCountry}</td>
			<td>
				<fmt:formatNumber value="1000" type="currency"/>
			</td>
			<td>
				<fmt:formatDate value="${dateFn:getNow()}" type="both"/>
			</td>
			<td>
				${pageTimeZone.displayName}
				<br>
				<fmt:formatDate value="${dateFn:getNow() }" type="both" timeZone="${pageTimeZone}"/>
			</td>
		</tr>
	</tbody>
</table>
</body>
</html>