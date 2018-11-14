<%@page import="java.io.FilenameFilter"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
//내가 접근해도 남이 접근해도 여기사용 -> 모든유저(서블릿)들에서 공통으로 사용-> 어플리케이션 스코프 
	//							-> 그럼 언제넣을까? -> 미리넣고시작-> jsp로는 요청들어와야사용하니까 안됨 -> 어플리케이션이 시작되자마자..web.xml로이동->
	String contentFolder = application.getInitParameter("contentFolder");//11.14
// 	File folder = new File(contentFolder);//11.14
	File folder = (File)application.getAttribute("contentFolder");//11.14
	
	String[] names = folder.list((dir, name)->{
						return application.getMimeType(name).startsWith("text");});//마임타입이 들어옴.. 트루이면 text파일이 들어옴
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>02/musicForm.jsp</title>

<script
  src="https://code.jquery.com/jquery-3.3.1.min.js"
  integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
  crossorigin="anonymous"></script>
<script type="text/javascript">
//  **11월12일
	$(function(){
		var songForm = $("#songForm");
		var resultArea = $("#resultArea");
		$("[name='music']").on("change", function(){
			var song = $(this).val();
			//먼저 url을 채워야하므로 폼에 대한 리퍼런스 필요->위에요청전에 미리만들기
			
			var url = songForm.attr("action");
			var method = songForm.attr("method");
			$.ajax({//주소(URL), 메서드, 파라미터(데이타), 내가응답데이타는 어떤형식으로 받을수있는지(html json..등의 데이타타입)가 필요..
				url : url,
				method : method,
				data : {
					song : song
				},
				dataType : "html",//결정해주는 헤더가 하나있다=> request의 "accept : text/html" => mime도
				success : function(resp) {//처리에 성공했을때..
					resultArea.html(resp);//inner html?
				},
				error : function(resp) {//에러도 하나의 응답이니깐 resp로..
					console.log(resp.responseText); //이때 클라이언트console
				}
			});
		});
	});

</script>
  
</head>
<body>
<form id="songForm" action="<%= request.getContextPath() %>/song" method="post">
	<select name="music">
		<option value="">가사 선택</option>
		<%
			for(String name : names){
				out.println(String.format(
					"<option>%s</option>", name
						));
			}
		%>
	</select>
</form>
<div id="resultArea"></div>
</body>
</html>