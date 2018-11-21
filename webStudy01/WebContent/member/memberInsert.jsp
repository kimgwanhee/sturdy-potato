<%@page import="java.text.ParseException"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="sun.java2d.pipe.SpanShapeRenderer.Simple"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="kr.or.ddit.ServiceResult"%>
<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@page import="kr.or.ddit.member.service.MemberServiceImpl"%>
<%@page import="kr.or.ddit.member.service.IMemberService"%>
<%@page import="kr.or.ddit.member.dao.IMemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
private boolean validate(MemberVO member, Map<String, String> errors){//콜바이레퍼런스..?머죠
	boolean valid = true;
//검증 룰
	if(StringUtils.isBlank(member.getMem_id())){ valid=false; errors.put("mem_id", "회원아이디누락");}
	if(StringUtils.isBlank(member.getMem_pass())){ valid=false; errors.put("mem_pass", "비밀번호누락");}
	if(StringUtils.isBlank(member.getMem_name())){ valid=false; errors.put("mem_name", "회원명누락");}
	if(StringUtils.isBlank(member.getMem_zip())){ valid=false; errors.put("mem_zip", "우편번호누락");}
	if(StringUtils.isBlank(member.getMem_add1())){ valid=false; errors.put("mem_add1", "주소1누락");}
	if(StringUtils.isBlank(member.getMem_add2())){ valid=false; errors.put("mem_add2", "주소2누락");}
	if(StringUtils.isBlank(member.getMem_mail())){ valid=false; errors.put("mem_mail", "이메일누락");}
	if(StringUtils.isNotBlank(member.getMem_bir())){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		//formatting : 특정 타입의 데이터를 일정 형식의 문자열로 변환.
		//parsing의 차이점.. : 일정한 형식의 문자열을 특정 타입의 데이터로 변환.
		try{
			formatter.parse(member.getMem_bir());
		}catch(ParseException e){
			valid = false;
			errors.put("mem_bir", "날짜 형식 확인");
		}
	}
	return valid;
}
%>
<%
	
	request.setCharacterEncoding("UTF-8");
// 	MemberVO member = new MemberVO();
// 	request.setAttribute("member", member);

%>
<jsp:useBean id="member" class="kr.or.ddit.vo.MemberVO" scope="request"></jsp:useBean>
<jsp:setProperty name="member" property="*" ></jsp:setProperty>
<%
	String goPage = null;
	boolean redirect = false;
	String message= null;
	Map<String, String> errors = new LinkedHashMap<>();
	request.setAttribute("errors", errors);// 이 request안에있는값이 바뀌었다?
	boolean valid = validate(member, errors);
	System.err.println(errors.size());
	if(valid){
		IMemberService service = new MemberServiceImpl();
		ServiceResult result = service.registMember(member);
		switch(result){
		case PKDUPLICATED : 
			goPage = "/member/memberForm.jsp";
			message = "아이디 중복, 바꾸셈.";
			break;
		case FAILED : 
			goPage = "/member/memberForm.jsp";
			message = "서버 오류로 인한 실패, 잠시뒤 다시하셈.";
			break;
		case OK : 
			goPage="/member/memberList.jsp";
			redirect = true;
			break;
		}
		request.setAttribute("message", message);
	}else{
		goPage = "/member/memberForm.jsp";
	}
	
	if(redirect){
		response.sendRedirect(request.getContextPath()+goPage);	
	}else{
		RequestDispatcher rd = request.getRequestDispatcher(goPage);
		rd.forward(request, response);
	}
%>