package kr.or.ddit.web;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Http 프로토콜의  request packaging 규칙
 * Request Line : URL, Http Method, Protocol/version
 * 	Http Method(Request Method) : 현재 요청의 목적/의도/수단/방식
 *  GET : 조회 (R)
 *  	Body가 존재하지 않기때문에, 전송할 최소한의 데이터를 Line의 URL에 덧붙임.
 *  	ex)http://localhost/webStudy01/gugudan.do?minDan=4&maxDan=7 
 *  	주소 : 포트넘버/세부경로 ? QueryString
 *  	QueryString : segment1 & segment2 & ....
 *  	segment : param_name=param_value
 *  POST : (신규)전송 및 등록 (C)
 *  PUT : 전송 및 수정 (U)
 *  DELETE : 삭제 (D)
 *  TRACE : 디버깅
 *  OPTIONS : ex) http method의 지원여부 확인
 *  HEAD : 응답데이터를 body가 없는 형태로 받는다.
 * Request Header : 클라이언트와 요청에 관한 부가 정보(metadata)
 * 		헤더명 : 헤더값 ...<-형식 
 * Request Body(Contents, Message body) : mothod가 POST일 경우에만 형성.
 * 		: 클라이언트가 서버로 전송할 진짜 데이터들....(요청 파라미터)
 * 
 * *** client의 요청정보를 서버에서 확인하는 방법
 * HttpServletRequest를 활용
 * 1) 파라미터 확보 : 기본적으로 모든 파라미터는 문자열의 형태로 전송됨.
 * 		String getParameter(name) : 하나의 파라미터명으로 하나의 값이 전송.
 * 		String[] getParameterValues(name) : 동일한 파라미터명으로 여러개의 값이 전송.
 * 		Enumeration getParameterNames()
 * 		** Map<String, String[]> getParameterMap()
 * 				파라미터명, 값[]
 * 		** 요청 파라미터에 특수문자가 포함된 경우, 
 * 		IETF에서 정의한 RFC2396 규약에 따라
 * 		모든 특수문자는 *URLEncoding(Percent Encoding) 방식으로 인코딩되어 전송.
 * 		인코딩된 특수문자 확보
 * 		GET : 서버의 설정을 통한 디코딩 설정. 
 * 				server.xml -> URIEncoding : 특정 인코딩 방식으로 고정.
 * 							useBodyEncodingForURI : POST방식에서 사용하는 request.setCharacterEncoding(encoding)를 사용.
 * 		POST : request.setCharacterEncoding(encoding) - 파라미터를 꺼내기 전에 설정함.
 * 2) 요청 헤더의 확보
 * 		String getHeader(name) : 모든 헤더는 문자열로 전송
 * 		int getIntHeader(name) 
 * 		long getDateHeader(name) : 날짜 확인시, new Date(long)
 * 		Enumeration getHeaders(name) : 
 * 		Enumeration getHeaderNames() : 헤더의 이름들만 가져옴
 * 
 * 3) 기타 요청과 관련된 메소드들...
 * 		service 메소드에 기술됨(참고)
 * 		
 * 		
 */
@WebServlet("/httpReq.do")
public class HttpRequestDescription extends HttpServlet {
    
	public void init(ServletConfig config) throws ServletException {
		//객체 초기화 직후
		
	}

	public void destroy() {
		//객체 소멸 전
		
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {//모든 요청에대해서 get,post상관없이 호출
		System.out.println(request.getCharacterEncoding());//null -> get방식이라 body가 안만들어져서
		System.out.println(request.getContentType());//null -> get방식이기때문에 클라이언트가 서버로 가는게없음
		System.out.println(request.getContentLength());	//-1
		System.out.println(request.getContextPath());	// /webStudy01 -> 서버가 도메인을 저장하므로 도메인명 생략
		System.out.println(request.getLocalAddr());	//서버ip
		System.out.println(request.getRemoteAddr());	// 클라이언트의 ip
		System.out.println(request.getRemotePort());
		//requestLine 정보
		System.out.println(request.getMethod()); 
		System.out.println(request.getRequestURI());
		System.out.println(request.getProtocol());
		
		
		// 매 요청시 마다 호출
		// Http method에 상관없이 실행될 코드를 넣는다.
		super.service(request, response);
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}

}
