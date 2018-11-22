package kr.or.ddit.member.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.CommonException;
import kr.or.ddit.ServiceResult;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.vo.MemberVO;

@WebServlet("/member/memberInsert.do")//URL X -> URI
public class MemberInsertServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String view = "/WEB-INF/views/member/memberForm.jsp";//6번..
		RequestDispatcher rd = req.getRequestDispatcher(view);
		rd.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//요청분석
		//검증
		//로직과의 의존관계형성
		//MemberInsert.jsp에서 가져옴
		req.setCharacterEncoding("UTF-8");
		MemberVO member = new MemberVO();//커맨더 오브젝트
		req.setAttribute("member", member);
		
		member.setMem_id(req.getParameter("mem_id"));

	//요청뽑아내기
//	<jsp:setProperty name="member" property="*" ></jsp:setProperty> //member.setMem_id(req.getParameter("mem_id"));을 대신해주는것
		try {
			BeanUtils.populate(member, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new CommonException(e);
		}
		
	//검증
		String goPage = null;
		boolean redirect = false;
		String message= null;
		Map<String, String> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);// 이 request안에있는값이 바뀌었다?
		boolean valid = validate(member, errors);
		System.err.println(errors.size());
		if(valid){
			//의존관계형성
			IMemberService service = new MemberServiceImpl();
			//데이터 돌려주기?
			ServiceResult result = service.registMember(member);
			switch(result){
			case PKDUPLICATED : 
				goPage = "/WEB-INF/views/member/memberForm.jsp";
				message = "아이디 중복, 바꾸셈.";
				break;
			case FAILED : 
				goPage = "/WEB-INF/views/member/memberForm.jsp";
				message = "서버 오류로 인한 실패, 잠시뒤 다시하셈.";
				break;
			case OK : 
				goPage="/member/memberList.do";
				redirect = true;
				break;
			}
			req.setAttribute("message", message);
		}else{
			goPage = "/WEB-INF/views/member/memberForm.jsp";
		}
		
		if(redirect){
			resp.sendRedirect(req.getContextPath()+goPage);	
		}else{
			RequestDispatcher rd = req.getRequestDispatcher(goPage);
			rd.forward(req, resp);
		}
	
	}
	
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
}
