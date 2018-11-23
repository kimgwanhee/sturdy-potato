package kr.or.ddit.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.vo.MemberVO;

public class MemberViewController implements ICommandHandler{//상속성을 부여.
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mem_id = req.getParameter("who");
		
		if(StringUtils.isBlank(mem_id)){
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;//여기선 뷰 자체가 결정될 필요가 없으므로 null
		}
		IMemberService service = new MemberServiceImpl();
		MemberVO member = service.retrieveMember(mem_id);
		String view = "member/memberView";//보여주려는 뷰 선택
		req.setAttribute("member", member);
		return view;//여기서 결정한 뷰를 controller에 보내기위해 .
	}
}
