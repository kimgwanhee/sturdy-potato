package kr.or.ddit.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.vo.MemberVO;

@CommandHandler
public class MyPageController {
	
	@URIMapping("/member/mypage.do")
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if(session==null || session.isNew()) {//마이페이지는 ..이게 절대 최초의 요청이면안되므로
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"로그인한 이후에만 마이페이지 요청 가능.");
			return null;//위에서 응답결정됬으니 null내보내기
		}
		MemberVO authMember = (MemberVO) session.getAttribute("authMember");
		// 마이페이지 컨트롤러가 안전하게 동작하기 위해서는
		// 전단계에서 로그인 여부를 미리 확인해야함. --Filter 활용
		String mem_id = authMember.getMem_id();
		IMemberService service =  new MemberServiceImpl();
		MemberVO member = service.retrieveMember(mem_id);
		String view = "member/memberView";
		req.setAttribute("member", member);//스코프
		//이동방식 결정 필요없어짐
		return view;
	}
}

