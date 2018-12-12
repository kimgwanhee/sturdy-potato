<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>15/errorProcess.jsp</title>
</head>
<body>
	<h4>웹 어플리케이션의 에러 처리 방법</h4>
	<pre>
		1. 지역적 처리(각 JSP에 대해 직접 에러 처리 페이지를 설정. page지시자의 errorPage 속성)
		2. 전역적 처리(web.xml에 어플리케이션 자체를 대상으로 에러 처리 페이지 설정 - error-page 태그)
			1) 예외 타입별 처리(exception-type)
			2) 에러 상태 코드별 처리(error-code)
	
		**지역처리 > 예외타입별 처리 > 에러 상태 코드별 처리
	</pre>
</body>
</html>