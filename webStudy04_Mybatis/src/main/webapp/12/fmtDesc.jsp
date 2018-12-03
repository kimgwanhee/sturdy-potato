<%@page import="java.util.TimeZone"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>	
<head>	
<meta charset="UTF-8">
<title>12/fmtDesc.jsp</title>
<script type="text/javascript">

</script>
<style type="text/css">
	input[type='image']{
		width: 50px;
		height: 50px;
	}
</style>
</head>
<body>
<h4>국제화 태그(format 태그, fmt 태그)</h4>
<input type="image" src='<c:url value="/images/korea.png"/>' onclick="location.href='?locale=ko_KR';"/>

<input type="image" src='<c:url value="/images/america.png"/>' onclick="location.href='?locale=en_US';"/>
<pre>
	1. 국제화(i18n, internationalization)
		setLocale, setBundle, message
		  1) 언어 결정
	      2) 메시지 번들 작성(basename_locale) : 로케일 정보는 basename 포함되지 않음
	      ** setLocale : 로딩할 메시지 번들의 로케일을 결정
	      3) 메시지 번들 로딩(setBundle/bundle)
	      4) 메시지 키를 이용해 메시지를 출력(message)
		
		로딩먼저해주기
		<fmt:setLocale value="${param.locale}"/>
		<fmt:bundle basename="kr.or.ddit.msgs.message">
			<fmt:message key="bow"/>
		</fmt:bundle>
		<%
			String[] zoneIds = TimeZone.getAvailableIDs();		
			TimeZone  zone = TimeZone.getTimeZone(zoneIds[2]);
		%>
		<%=zone.getDisplayName() %>
		
		<c:if test="${not empty param.locale }">
			<fmt:message key="bow"></fmt:message>
		</c:if>
	2. 지역화(l10n, localization)
		파싱(perseNumber, parseDate) //문자열로부터 날짜를 찾아냄, 포맷팅(formatNumber, formatDate)
		<fmt:formatDate value="<%=new Date()%>" type="both" timeZone="<%=zone %>" dateStyle="long" timeStyle="long"/>
		<fmt:formatNumber value="100000" type="currency" var="numStr"/>
		${numStr}----------------
		<fmt:parseNumber value="${numStr}" parseLocale="${param.locale}" type="currency" var="number"/>
		${number * 3 }
</pre>
</body>
</html>