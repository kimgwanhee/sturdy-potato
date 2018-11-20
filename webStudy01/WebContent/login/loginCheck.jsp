<%@page import="kr.or.ddit.member.service.AuthenticateServiceImpl.ServiceResult"%>
<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@page import="kr.or.ddit.member.service.AuthenticateServiceImpl"%>
<%@page import="kr.or.ddit.member.service.IAuthenticateService"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.SQLException"%>
<%@page import="kr.or.ddit.db.ConnectionFactory"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="sun.text.normalizer.ICUBinary.Authenticate"%>
<%@page import="kr.or.ddit.utils.CookieUtil.TextType"%>
<%@page import="kr.or.ddit.utils.CookieUtil"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- 1. 파라미터 확보 -->
<!-- 2. 검증(필수 데이터) -->
<!-- 3. 불통(누락되는 데이터가 발생하면 다시 loginform.jsp로 이동해야된다는소리) -->
<!-- 	이동(->도착지 loginform.jsp, 이동하는방식은 기존에 입력했던 아이디를 그대로 전달할 수 있는 방식(Dispatch)) -->
<!-- 4. 통과 -->
<!-- 	4-1. 인증(아이디==비번) -->
<!-- 		4-2. 인증 성공 : 웰컴 페이지로 이동(원본요청을 제거하고 이동) -> 이동방식 -> redirect  -->
<!-- 		4-3. 인증 실패(무조건 비번이 틀렸다고 가정) : 이동(loginForm.jsp, 기존에 입력했던 아이디를 그대로 전달할 수 있는 방식(Dispatch)) -->
<%
	//**11월13일
	request.setCharacterEncoding("UTF-8");
	
	RequestDispatcher rd = request.getRequestDispatcher("/login/loginForm.jsp");	
	
	String mem_id = request.getParameter("mem_id");
	String mem_pass = request.getParameter("mem_pass");
	String idChecked= request.getParameter("idChecked");
	int age = 0;//만료시간
	
	String goPage=null;
	boolean redirect = false;

	
	if(mem_id==null || mem_id.trim().length()==0 ||
			mem_pass==null || mem_pass.trim().length()==0){
			goPage = "/?command=login";
			redirect = true;
// 			session.setAttribute("error", 1);//속성.. -> 쿼리스트링 네임, 값//세션스코프 11월14일 
			session.setAttribute("message", "아이디나 비번 누락");//11.14 이제 위는 굳이 쓸필요 없어짐
	}else{
		IAuthenticateService service = new AuthenticateServiceImpl();
		Object result = service.authenticate(new MemberVO(mem_id, mem_pass));
		
		
	//**과제 아이디 인증 끝나면 쿠키 생성
		if(result instanceof MemberVO){ 
			goPage = "/";
			redirect = true;
			session.setAttribute("authMember", result);
			if("idSaved".equals(idChecked)){
				age = 60*60*24*7;
			}
			Cookie cookie = CookieUtil.createCookie("indexId", mem_id, request.getContextPath(), TextType.PATH, age);
			response.addCookie(cookie);
		}else if(result == ServiceResult.PKNOTFOUND){
			goPage = "/?command=login";
			redirect = true;
// 			session.setAttribute("error", 1);//11.14 
			session.setAttribute("message", "존재하지 않는 회원");//11.14 
		}else{
			goPage = "/?command=login";
			redirect = true;
			session.setAttribute("message", "비번 오류로 인증실패");//11.14 
		}
	}
	
	if(redirect){
		response.sendRedirect(request.getContextPath()+goPage);
	}else{
		rd.forward(request, response);		
	}
%>