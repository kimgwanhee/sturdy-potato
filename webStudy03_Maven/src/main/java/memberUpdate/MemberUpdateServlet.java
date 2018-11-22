package memberUpdate;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
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

@WebServlet("/member/memberUpdate.do")
public class MemberUpdateServlet extends HttpServlet {
	/*
	 * //1. 요청을 받으려면 등록, 매핑하기
	 * 
	 * //2. 요청 분석(1. 주소를분석 2. 파라미터 3. 메소드 4. 헤더넘겨서 헤더도 분석...)
	 * 
	 * //3. 비즈니스로직객체service와 의존관계형성 new~ IMemberService service = new
	 * MemberServiceImpl();
	 * 
	 * //4. 로직을 하나 선택 // service.retrieveMemberList(); 
	 * //5. 컨텐츠(Model) 확보
	 * List<MemberVO> memberList = service.retrieveMemberList(); 
	 * //6. 뷰 선택 String
	 * view = "/WEB-INF/views/member/memberList.jsp";//서버사이드 절대경로 
	 * //7. Scope를 통해
	 * Model공유//위 경로때문에 DISPATCH밖에안됨 이동 req.setAttribute("memberList", memberList);
	 * //8. 이동 방식을 결정하고, V.L로 이동. RequestDispatcher rd =
	 * req.getRequestDispatcher(view); rd.forward(req, resp);
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 8가지동작
		// POST에서 제일 먼저 특수문자 처리
		req.setCharacterEncoding("UTF-8");

		// MEMBER 담을 객체 생성
		MemberVO member = new MemberVO();
		
		//7.Scope를 통해
		req.setAttribute("member", member);
		
		try {
			BeanUtils.populate(member, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new CommonException(e);
		}

		// 3번 검증 의존관계형성
		String goPage = null;
		boolean redirect = false;
		String message = null;
		Map<String, String> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		boolean valid = validate(member, errors);
		
		//검증
		if(valid) {
			//4번5번 로직을 선택해서 컨텐츠확보
			IMemberService service = new MemberServiceImpl();
			ServiceResult result = service.modifyMember(member);
			switch (result) {//검증엔 성공
			case INVALIDPASSWORD:
				//6번 뷰선택
				goPage = "/WEB-INF/views/member/memberView.jsp";
				message = "비밀번호 틀림";
				break;
				
			case FAILED:
				goPage = "/WEB-INF/views/member/memberView.jsp";
				message = "서버 오류로 인한 실패";
				break;
							
			case OK:
//				goPage = "/member/memberView.do?who="+member.getMem_id();
				goPage = "/member/mypage.do";
				redirect=true;
				break;
			}
			req.setAttribute("message", message);
		}else {//검증실패
			goPage="/WEB-INF/views/member/memberView.jsp";
		}
		
		if(redirect) {
			resp.sendRedirect(req.getContextPath()+goPage);
		}else {
			RequestDispatcher rd = req.getRequestDispatcher(goPage);
			rd.forward(req, resp);
		}
	}

	private boolean validate(MemberVO member, Map<String, String> errors) {// 콜바이레퍼런스..?머죠
		boolean valid = true;
		// 검증 룰
		if (StringUtils.isBlank(member.getMem_name())) {
			valid = false;
			errors.put("mem_name", "회원명누락");
		}
		if (StringUtils.isBlank(member.getMem_zip())) {
			valid = false;
			errors.put("mem_zip", "우편번호누락");
		}
		if (StringUtils.isBlank(member.getMem_add1())) {
			valid = false;
			errors.put("mem_add1", "주소1누락");
		}
		if (StringUtils.isBlank(member.getMem_add2())) {
			valid = false;
			errors.put("mem_add2", "주소2누락");
		}
		if (StringUtils.isBlank(member.getMem_mail())) {
			valid = false;
			errors.put("mem_mail", "이메일누락");
		}
		if (StringUtils.isNotBlank(member.getMem_bir())) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			// formatting : 특정 타입의 데이터를 일정 형식의 문자열로 변환.
			// parsing의 차이점.. : 일정한 형식의 문자열을 특정 타입의 데이터로 변환.
			try {
				formatter.parse(member.getMem_bir());
			} catch (ParseException e) {
				valid = false;
				errors.put("mem_bir", "날짜 형식 확인");
			}
		}
		return valid;
	}

}
