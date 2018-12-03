<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>WEB-INF/views/model2sampleVeiw.jsp</title>
</head>
<body>
<pre>
	//11월15일-2
	모델 2구조를 이용하고 있고,
	컨트롤러를 거친 이후에 응답이 생성되는 영역 - view layer.
	controller끝나고나면 이제 그 모델데이터 받아야함
	컨트롤러가 전달해준 모델 : <%= request.getAttribute("model") %>
</pre> 
</body>
</html>