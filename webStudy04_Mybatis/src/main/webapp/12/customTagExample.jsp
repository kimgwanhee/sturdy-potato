<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.ddit.or.kr/loopTag" prefix="loop"%>
<%@taglib uri="http://www.ddit.or.kr/makeSelect" prefix="ms" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>.
0

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<loop:myforEach loopCount="5" data="테스트"/>
	<hr/>
	<c:set var="test" value="locParam"></c:set>
	<ms:makeLocaleSelect name="${test}"/>
<%-- 	<ms:makeTimeZoneSelect name="timeZoneParam" onchangeFunc="changeHandler"/> --%>
	<ms:makeTimeZoneSelect name="time"/>
</body>
</html>