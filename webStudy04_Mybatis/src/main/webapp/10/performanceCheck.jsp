<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>10/performanceCheck.jsp</title>
</head>
<body>
<h4> 어플리케이션 처리 속도 </h4>
	소요 시간(response time)=latency time(w.a.s===d.s사이의 지연시간..99.9999%)+processing time(w.a.s 가공시간.. 거의0%소요)
	1. 한번 가져와서(latency) 한번 처리(processing)하는 모듈 : 30ms정도..(oneConnOneJob에서 보면) -> 0ms(풀링후!)
	2. 100번 가져와서 100번 처리하는 모듈 : 1000ms -> 40ms
	3. 한번 가졍와서 100번 처리하는 모듈 : 30ms정도 -> 0ms


</body>
</html>