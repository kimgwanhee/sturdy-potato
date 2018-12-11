package kr.or.ddit.common.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.member.service.AuthenticateServiceImpl;
import kr.or.ddit.member.service.IAuthenticateService;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.utils.CookieUtil;
import kr.or.ddit.utils.CookieUtil.TextType;
import kr.or.ddit.vo.MemberVO;

@CommandHandler
public class LoginController {
	IAuthenticateService service = new AuthenticateServiceImpl();

	@URIMapping(value = "/login/loginCheck.do", method = HttpMethod.POST)
	public String loginCheck(HttpServletRequest request, HttpServletResponse response) {
		
		String mem_id = request.getParameter("mem_id");
		String mem_pass = request.getParameter("mem_pass");
		String idChecked = request.getParameter("idChecked");
		int age = 0;// 만료시간

		String goPage = null;
		boolean redirect = false;

		if (mem_id == null || mem_id.trim().length() == 0 || mem_pass == null || mem_pass.trim().length() == 0) {
			goPage = "redirect:/?command=login";// -> "redirect:/login/loginForm.jsp";
			redirect = true;
//	 			session.setAttribute("error", 1);//속성.. -> 쿼리스트링 네임, 값//세션스코프 11월14일 
			request.getSession().setAttribute("message", "아이디나 비번 누락");// 11.14 이제 위는 굳이 쓸필요 없어짐
		} else {
			Object result = service.authenticate(new MemberVO(mem_id, mem_pass));

			// **과제 아이디 인증 끝나면 쿠키 생성
			if (result instanceof MemberVO) {
				goPage = "redirect:/";
				redirect = true;
				request.getSession().setAttribute("authMember", result);
				if ("idSaved".equals(idChecked)) {
					age = 60 * 60 * 24 * 7;
				}
				Cookie cookie = CookieUtil.createCookie("indexId", mem_id, request.getContextPath(), TextType.PATH,
						age);
				response.addCookie(cookie);
			} else if (result == ServiceResult.PKNOTFOUND) {
				goPage = "redirect:/?command=login";
				redirect = true;
//	 			session.setAttribute("error", 1);//11.14 
				request.getSession().setAttribute("message", "존재하지 않는 회원");// 11.14
			} else {
				goPage = "redirect:/?command=login";
				redirect = true;
				request.getSession().setAttribute("message", "비번 오류로 인증실패");// 11.14
			}
		}
		return goPage;
	}
	
	@URIMapping("/login/logout.do")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/";
		
	}
}
