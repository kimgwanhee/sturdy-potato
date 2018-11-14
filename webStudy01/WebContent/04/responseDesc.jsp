<%@page import="java.util.Calendar"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
// 	response.setHeader("Content-Type", "text/html;charset=UTF-8");
	response.setContentType("text/html;charset=UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>04/responseDesc.jsp</title>
</head>
<body>
<h4>Http Response</h4>
<pre>
    **11월12일
	Http의 response 패키징 방식
	1) Response Line : Response Status Code, Protocol/ver
		*Response Status Code(응답 상태 코드)
		- 100 : ing...., HTTP1.1 부터 하위 프로토콜로 websocket이 사용됨(connectful)
			** HTTP : Connectless, Stateless 
		- 200 : OK, SUCCESS(정상 처리)
		- 300 : 클라이언트의 추가 액션이 필요한 경우,
			304(Not Modified)
			301/302/307(Moved)
		- 400 : Client쪽 문제로 처리 실패
			404(Not Found), 400(Bad Request),
			405(Method Not Allowed),
			401(UnAuthorized), 403(Forbidden)
		- 500 : Server쪽 문제로 처리 실패
			500(Internal Server Error)
			
		HTTP -> conectless
		WebSocket -> poll방식
		
	2) Response Header : 서버나 응답데이터에 대한 부가정보(metadata) ->
		response header name : header value
		
	3) Response Body(Message Body) : 응답 컨텐츠 
	
	** HttpServletResponse를 통한 응답 제어
		: 서버에서 클라이언트로 전송되는 응답에 대한 모든 데이터를 가진 객체.
		1) 응답데이터를 전송하기 위한 출력 스트림 확보
			char stream(PrintWriter) getWriter(), 
			byte stream(ServletOutputStream) getOutputStream()
		2) setStatus(status_code) : 200/300 사용
		   sendError(status_code) : 400/500사용, 서버의 에러페이지로 자동 연결.
		3) set[add]Header(header_name, header_value)
		   set[add]IntHeader(header_name, header_value)
		   set[add]DateHeader(header_name, long header_value)
		   
		   응답 헤더를 설정하는 경우.
		   a) MIME 설정 :  e마임을 지정할때 사용하는 Content-Type..
		   		setHeader, setContentType, page 지시자의 contentType 속성.
		   b) Cache 제어 : Cache-control(HTTP/1.1), Pragma(HTTP/1.0), Expires(만기시간)
		   
		   웹 표준화..전략 -> 모든걸 다 사용해서 캐시제어해야한다..(옛버전인지 신버전인지 모름)

		   <%
		   //캐시를 저장하지 않도록 설정
// 				response.setHeader("Pragma", "no-cache");			   
// 				response.setHeader("Cache-control", "no-cache");
// 				response.addHeader("Cache-control", "no-store");//기존에 추가가능
// 				response.setDateHeader("Expires", 0);//value타입이면 ms long타입이면 second
			//캐시를 저장하도록 설정
			int maxAge = 60*60*24*7;
			response.setHeader("Pragma", "public;max-age="+maxAge);
			response.setHeader("Cache-Control", "public;max-age="+maxAge);
			Calendar today = Calendar.getInstance();
			today.add(Calendar.SECOND, maxAge);//today라는게 일주일뒤로 셋팅이됬을것
			response.setDateHeader("Expires", today.getTimeInMillis());
		   %>
		   
		   c) Auto Request : Refresh
		   d) Page Move(Flow Control) : Location//11.13일 시작   
		   
		   
</pre>
</body>
</html>