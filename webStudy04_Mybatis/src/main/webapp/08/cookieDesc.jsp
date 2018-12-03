<%@page import="kr.or.ddit.utils.CookieUtil"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>08/cookieDesc.jsp</title>
</head>
<body>
<h4> Cookie(HttpCookie)활용</h4>
<pre>
//11월15일 -5
	: Http의 stateless 특성을 보완하여 최소한의 상태정보를 
	  클라이언트 쪽에 저장하는 개념
	1. 쿠키 객체 생성(쿠키명/쿠키값) 
	2. 생성된 쿠키를 클라이언트쪽으로 전송
	<%
// 		String cookieValue = URLEncoder.encode("한글 쿠키값", "UTF-8");
// 		out.println(cookieValue);
		//쿠키는 사실 헤더이다.-> 특별한 설정을 하지 않는한 문자열로 전달
// 		Cookie cookie = new Cookie("koreanCookie", cookieValue);//인코딩을 해서 쿠키를 생성해야 illefalargum~ 에러안남 
		//F12눌러보면 application에서 koreanCookie로 저장되걸 확인할 수 있음
		Cookie cookie = CookieUtil.createCookie("koreanCookie", "한글 쿠키값");
		response.addCookie(cookie);//클라이언트가 쿠키를 저장해야하므로 response에 쿠키를 실어서 보냄
	%>
	
	3. 브라우저가 자기 쿠키저장소에 응답이 실려있는 쿠키를 저장.(우리가 할일이 아니다)
	4. 다음번 요청이 발생 시 저장되어있던 쿠키가 서버쪽으로 재전송.(우리가 할일이 아니다)
	
	5. 전송된 쿠키를 통해 상태 복원 //리퀘스트에있는 헤더를 뽑아낸다는것
	<%
	/*
		Cookie[] cookies = request.getCookies();
		if(cookies != null){//쿠키를 클리어하고나면 에러
			//배열이라는건 한번의 요청에 여러개가 올수 있다라는것
			for(Cookie tmp:cookies){
				if("koreanCookie".equals(tmp.getName())){//이렇게 짜면 안전한 코드이다.
					out.println(URLDecoder.decode(tmp.getValue(), "UTF-8"));//위에서 인코딩하고 이걸 꺼낸 후에는 디코딩을 해주어야함 알아볼수있게
				}
			}
		}
		*/
		
		//라이브러리 넣고 //굳이 반복문으로 안돌려도 이젠
		out.println(new CookieUtil(request).getCookieValue("koreanCookie"));//아직 저장전이라 처음엔 null 새로고침
		
	%>
	
	
	 
</pre>
</body>
</html>