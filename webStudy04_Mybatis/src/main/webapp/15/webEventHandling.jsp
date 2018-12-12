<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>15/webEventHandling</title>
</head>
<body>
<h4>서버사이드 웹 어플리케이션의 이벤트 처리</h4>
<pre>
	** 이벤트 처리 단계
	1. 이벤트 타켓 결정(button) : 웹 어플리케이션(context)
	2. 이벤트 종류 결정(click) : 
		1) request : 생성/소멸(lifecycle event), 
					requestScope(add, remove, replace)
		2) session : 생성(클라이언트가 요청을 보냈을때)/소멸(브라우저껏을때, 세션만료시간동안 새로운요청없을때 자동소멸, 명시적으로 로그아웃버튼을 눌렀을때 invalidate실행됬을때)(lifecycle event)
					sessionScope(add, remove, replace)
		3) application(ServletContext) : 생성(서버 구동될때)/소멸(서버 끌때, 서버가 리로딩될때)(lifecycle event)
					applicationScope(add, remove, replace)
					
	3. 이벤트 처리 핸들러 작성(function, method, listener)
		1) request
			lifeCycle : ServletRequestListener
			scope : ServletRequestAttributeListener
		2) session
			lifeCycle : HttpSessionListener
			scope : HttpSessionAttributeListener
			Object binding : HttpSessionBindingListener
		3) application
			lifeCycle : ServletContextListener
			scope : ServletContextAttrivuteListener
	4. 이벤트 타겟에게 핸들러를 부착(onclick, on(jquery), addlistener(java))
	
	
</pre>
</body>
</html>