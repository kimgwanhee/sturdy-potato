package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.vo.MemberVO;

@WebServlet("/member/memberList.do")
public class MemberListServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1. 요청을 받으려면 등록, 매핑하기
		
		//2. 요청 분석(1. 주소를분석 2. 파라미터 3. 메소드 4. 헤더넘겨서 헤더도 분석...)
		
		//3. 비즈니스로직객체service와 의존관계형성 new~
		IMemberService service = new MemberServiceImpl();
		
		//4. 로직을 하나 선택
//		service.retrieveMemberList();
		//5. 컨텐츠(Model) 확보
		List<MemberVO> memberList = service.retrieveMemberList();
		//6. 뷰 선택
		String view = "/WEB-INF/views/member/memberList.jsp";//서버사이드 절대경로
		//7. Scope를 통해 Model공유//위 경로때문에 DISPATCH밖에안됨 이동
		req.setAttribute("memberList", memberList);
		//8. 이동 방식을 결정하고, V.L로 이동.
		RequestDispatcher rd = req.getRequestDispatcher(view);
		rd.forward(req, resp);
		
	}
}
