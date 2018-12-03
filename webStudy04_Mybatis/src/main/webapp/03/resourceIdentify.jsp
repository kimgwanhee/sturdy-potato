<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>03/ressourceIdentify.jsp</title>
</head>
<body>
<h4> 자원의 식별 </h4>
<pre>

1. 파일 시스템 자원 : 파일시스템 경로를 통한 식별 
2. 클래스패스(calsspath) 자원 : classpath 기준 경로를 통한 식별
3. 웹리소스(url) 식별 : 
	URI(Uniform Resource Identifier)//가상경로
	URL(Uniform Resource Locator)
	URN(Uniform Resource Name)
	URC(Uniform Resource Content)
	
	URL
	scheme://domain:port/context/depth1/depth2..../resource_name
	
	*절대경로 =URL형태가 완전한것 : http://localhost:80/webStrudy01/images/Hydrangeas.jpg
		client-side방식 의 절대경로 :  브라우저는 출처정보를 기억.. (http://localhost:80/) => /webStudy01(contextpath)/images/Hydrangeas.jpg
		server-side방식 두가지다 : /desc(contextPath를 제외한 이후 경로 표기)
	*상대경로 =경로를 판단할 기준 위치필요 : 현재브라우저의 주소값
		../images/Hydrangeas.jpg
</pre>
	<img src="<%=request.getContextPath() %>/images/Koala.jpg"/>
	<img src="http://localhost:80/webStudy01/images/Koala.jpg"/>
	<img src="../images/Koala.jpg"/>

</body>
</html>