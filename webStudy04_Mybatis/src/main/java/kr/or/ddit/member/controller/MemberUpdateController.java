package kr.or.ddit.member.controller;

import java.io.File;
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
import javax.sound.midi.Sequence;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.CommonException;
import kr.or.ddit.ServiceResult;
import kr.or.ddit.filter.wrapper.FileUploadRequestWrapper;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.validator.GeneralValidator;
import kr.or.ddit.validator.UpdateGroup;
import kr.or.ddit.vo.MemberVO;

@CommandHandler
public class MemberUpdateController {
	/*
	 * //1. 요청을 받으려면 등록, 매핑하기
	 * 
	 * //2. 요청 분석(1. 주소를분석 2. 파라미터 3. 메소드 4. 헤더넘겨서 헤더도 분석...)
	 * 
	 * //3. 비즈니스로직객체service와 의존관계형성 new~ IMemberService service = new
	 * MemberServiceImpl();
	 * 
	 * //4. 로직을 하나 선택 // service.retrieveMemberList(); //5. 컨텐츠(Model) 확보
	 * List<MemberVO> memberList = service.retrieveMemberList(); //6. 뷰 선택 String
	 * view = "/WEB-INF/views/member/memberList.jsp";//서버사이드 절대경로 //7. Scope를 통해
	 * Model공유//위 경로때문에 DISPATCH밖에안됨 이동 req.setAttribute("memberList", memberList);
	 * //8. 이동 방식을 결정하고, V.L로 이동. RequestDispatcher rd =
	 * req.getRequestDispatcher(view); rd.forward(req, resp);
	 */
	@URIMapping(value="/member/memberUpdate.do", method=HttpMethod.POST)
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 8가지동작
		// POST에서 제일 먼저 특수문자 처리
//		req.setCharacterEncoding("UTF-8");//이제 요청을 제일먼저 갖고있는 프론트로 이동

		// MEMBER 담을 객체 생성
		MemberVO member = new MemberVO();

		// 7.Scope를 통해
		req.setAttribute("member", member);

		try {
			BeanUtils.populate(member, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new CommonException(e);
		}

		// 3번 검증 의존관계형성
		String goPage = null;
		String message = null;
		Map<String, List<CharSequence>> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		GeneralValidator validator = new GeneralValidator();

		boolean valid = validator.validate(member, errors, UpdateGroup.class);
		// 검증
		if (valid) {

			if (req instanceof FileUploadRequestWrapper) {
				FileItem fileitem = ((FileUploadRequestWrapper) req).getFileItem("mem_image");

				if (fileitem != null) {
					member.setMem_img(fileitem.get());
				}
			}

			// 4번5번 로직을 선택해서 컨텐츠확보
			IMemberService service = new MemberServiceImpl();
			ServiceResult result = service.modifyMember(member);
			switch (result) {// 검증엔 성공
			case INVALIDPASSWORD:
				// 6번 뷰선택
				goPage = "member/memberView";
				message = "비밀번호 틀림";
				break;

			case FAILED:
				goPage = "member/memberView";
				message = "서버 오류로 인한 실패";
				break;

			case OK:
//				goPage = "/member/memberView.do?who="+member.getMem_id();
				goPage = "redirect:/member/mypage.do";// redirect:라는 규칙성을 줌
				break;
			}
			req.setAttribute("message", message);
		} else {// 검증실패
			goPage = "member/memberView";
		}
		return goPage;
	}
}
