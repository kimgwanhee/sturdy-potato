<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- <meta http-equiv="Refresh" content="3;url=http://www.naver.com"> -->
<title>04/autoRequest.jsp</title>
<script type="text/javascript" src ="<%=request.getContextPath() %>/js/jquery-3.3.1.min.js"></script>

</head>
<body>
<h4> 자동 요청을 발생시키는 방법 </h4>
<h4><span id="countArea"></span>초 뒤에 네이버로 이동함.</h4>
<!-- getServerTime.jsp쪽으로 1초마다 비동기 요청을 발생시키고, -->
<!-- JSON형태로 전송된 응답데이터에서 serverTime프로퍼티로부터 확보. -->
<!-- serverClock span태그에 현재 서버의 시각이 갱신되도록.. -->
<pre>
	  **11월12일
	1. 서버 사이드
		<%
	// 		response.setIntHeader("Refresh", 1);
		//<%=new Date() 
		%>
	
		현재 서버의 시간 : <span id="serverClock"></span>
	2. 클라이언트 사이드
		HTML : meta 태그 이용
		Javascript : 스케줄링 함수 이용(interval, timeout 두가지종류있음)
</pre>
<script type="text/javascript">
	var serverClock = $("#serverClock");
	setInterval(function(){
		$.ajax({//주소(URL), 메서드, 파라미터(데이타), 내가응답데이타는 어떤형식으로 받을수있는지(html json..등의 데이타타입)가 필요..
			url : "<%=request.getContextPath()%>/05/getServerTime.jsp",  	//클라이언트 사이드방식으로 표기해야함 절대경로-슬러시로 시작 
			dataType : "json",//응답데이타를 어떤형식? -> JSON 여기서 결정되는 헤더가 두개 request에서 1.accept되는 헤더, 응답데이터는 JSON으로 와야함 표현해주는건 2.Content-Type
			success : function(resp) {//처리에 성공했을때..
				serverClock.html(resp.serverTime);
			},
			error : function(resp) {//에러도 하나의 응답이니깐 resp로..
				console.log(resp.status + ", "+resp.responseText);
			}
		});
	}, 1000);
/* 
 묶어두기
	var wait = 1;
	var count = wait;
	var spanTag = document.getElementById("countArea");//자바언어의 특성때문에 아래로 내려야함
	setInterval(function(){//이렇게 설정하는것을 콜백함수
		count--;
		spanTag.innerHTML = count;
	}, 1000);
	setTimeout(function(){
		location.reload();
// 		location.href="http://www.naver.com";//네이버로 요청을 보내서 새로운 응답데이터를 보낸다음 도큐먼트를 가져오라는?
	}, wait*1000);//대기시간 3초
*/
</script>
</body>
</html>