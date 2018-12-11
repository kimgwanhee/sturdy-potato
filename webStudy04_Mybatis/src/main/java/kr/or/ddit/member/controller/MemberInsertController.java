package kr.or.ddit.member.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import kr.or.ddit.validator.InsertGroup;
import kr.or.ddit.vo.MemberVO;

@CommandHandler
public class MemberInsertController  {// 여긴 하나의 주소로 두개의 메서드 문제가됨..->

	@URIMapping(value="/member/memberInsert.do", method=HttpMethod.GET)
	public String doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String view = "member/memberForm";// 6번..
		return view;
	}

	@URIMapping(value="/member/memberInsert.do",method=HttpMethod.POST)
	public String doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 요청분석
		// 검증
		// 로직과의 의존관계형성
		// MemberInsert.jsp에서 가져옴
		MemberVO member = new MemberVO();// 커맨더 오브젝트
		req.setAttribute("member", member);// foward방식에서 요청정보 남아있어야하므로 ..

//		member.setMem_id(req.getParameter("mem_id"));

		// 요청뽑아내기
//	<jsp:setProperty name="member" property="*" ></jsp:setProperty> //member.setMem_id(req.getParameter("mem_id"));을 대신해주는것
		try {
			BeanUtils.populate(member, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {// IllegalAccessException 잘못된 접근
																		// //InvocationTargetException는 vo에 set을 안해줘서
			throw new CommonException(e);
		}

		// 검증
		String goPage = null;
		String message = null;

		Map<String, List<CharSequence>> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);// 이 request안에있는값이 바뀌었다?
		GeneralValidator validator = new GeneralValidator();
		boolean valid = validator.validate(member, errors, InsertGroup.class);
		if (valid) {
			if (req instanceof FileUploadRequestWrapper) {
				// 이미지 데이타 뽑아내기 mem_image
				FileItem fileItem = ((FileUploadRequestWrapper) req).getFileItem("mem_image");
				if (fileItem != null) {
					// 바이트배열 찾아내기
					// db에 넣기위해서 넘기는 member객체 안에 넣어주기
					member.setMem_img(fileItem.get());
				}
				// 마이바티스에선 insert쿼리 바뀌기
			}

			// 의존관계형성
			IMemberService service = new MemberServiceImpl();
			// 데이터 돌려주기?
			ServiceResult result = service.registMember(member);

			switch (result) {
			case PKDUPLICATED:
				goPage = "member/memberForm";
				message = "아이디 중복, 바꾸셈.";
				break;
			case FAILED:
				goPage = "member/memberForm";
				message = "서버 오류로 인한 실패, 잠시뒤 다시하셈.";
				break;
			case OK:
				goPage = "redirect:/member/memberList.do";
				break;
			}
			req.setAttribute("message", message);
		} else {
			goPage = "member/memberForm";
		}
		return goPage;
	}

}
