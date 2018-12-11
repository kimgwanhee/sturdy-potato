package kr.or.ddit.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.vo.MemberVO;

@CommandHandler
public class MemberDeleteController {
	//post
	//탈퇴를 시키려면 필요한데이타 id와 pass 대한 검증 
	//둘중 하나라도 누락되면 bad request발생
	//통과하면 탈퇴 -> 로직객체 필요 로직선택 -removemember
	
	//removemember에서 검증
	//그전에 memberservice완성..
	@URIMapping(value="/member/memberDelete.do",method=HttpMethod.POST)
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String id = req.getParameter("mem_id");
		String pass = req.getParameter("mem_pass");
		
		if(StringUtils.isBlank(id)||StringUtils.isBlank(pass)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		
		// 3번 검증 의존관계형성
		IMemberService service = new MemberServiceImpl();
		
		//4번5번 로직을 선택해서 컨텐츠확보
//		MemberVO member = new MemberVO();
//		member.setMem_id(id);//셋팅
//		member.setMem_pass(pass);
//		ServiceResult result = service.removeMember(member);
		
		ServiceResult result = service.removeMember(new MemberVO(id, pass));
		boolean redirect = false;
		String message = null;
		String goPage = null;
		
		switch (result) {//검증엔 성공
		case INVALIDPASSWORD:
			//6번 뷰선택
//			goPage = "/member/memberView.do?who="+id;
			goPage = "redirect:/member/mypage.do";
			message = "비밀번호 틀림";
			break;
			
		case FAILED:
			goPage = "redirect:/member/mypage.do";
			message = "서버 오류로 인한 실패";
			break;
						
		case OK:
//			goPage = "/member/memberView.do?who="+id;
			goPage = "redirect:/common/message.jsp";
			message = "탈퇴약관 : 일주일이내에서 절대 !! 같은 아이디로 재가입 불가 ";
			req.getSession().setAttribute("goLink", "/");//세션을 만료시키기위해서 아래에
			req.getSession().setAttribute("isRemoved", new Boolean(true));
//			req.getSession().invalidate();//이제 세션만료 d이거 메시지 jsp로 가기
			break;
		}
		req.getSession().setAttribute("message", message);//열어서 본다음 바로 삭제해야함 -> 그 구조를 플래쉬
		
		return goPage;
	}
}
