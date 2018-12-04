package kr.or.ddit.member.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
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
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.vo.MemberVO;

public class MemberInsertController implements ICommandHandler {// 여긴 하나의 주소로 두개의 메서드 문제가됨..->

	@Override
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String method = req.getMethod();
		String view = null;
		if ("get".equalsIgnoreCase(method)) {// 메소드가 대문자로올지 소문자로올지 몰라서..equalsIgnoreCase
			view = doGet(req, resp);
		} else if ("post".equalsIgnoreCase(method)) {
			view = doPost(req, resp);
		} else {// 여기에 걸리면 우리가 처리할수있는 메소드 x -> 잘못된메서드 요청한 클라이언트잘못 400번대에러
			resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
		return view;
	}

	public String doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String view = "member/memberForm";// 6번..
		return view;
	}

	protected String doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 요청분석
		// 검증
		// 로직과의 의존관계형성
		// MemberInsert.jsp에서 가져옴
		MemberVO member = new MemberVO();// 커맨더 오브젝트
		req.setAttribute("member", member);//foward방식에서 요청정보 남아있어야하므로 ..

//		member.setMem_id(req.getParameter("mem_id"));

		// 요청뽑아내기
//	<jsp:setProperty name="member" property="*" ></jsp:setProperty> //member.setMem_id(req.getParameter("mem_id"));을 대신해주는것
		try {
			BeanUtils.populate(member, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {//IllegalAccessException 잘못된 접근 //InvocationTargetException는 vo에 set을 안해줘서
			throw new CommonException(e);
		}

		// 검증
		String goPage = null;
		String message = null;
		
		Map<String, String> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);// 이 request안에있는값이 바뀌었다?
		boolean valid = validate(member, errors);
		if (valid) {
			if(req instanceof FileUploadRequestWrapper) {
				//이미지 데이타 뽑아내기 mem_image
				FileItem fileItem = ((FileUploadRequestWrapper)req).getFileItem("mem_image");
				if(fileItem != null) {
					//바이트배열 찾아내기
					//db에 넣기위해서 넘기는 member객체 안에 넣어주기
					member.setMem_img(fileItem.get());
				}
				//마이바티스에선 insert쿼리 바뀌기 
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

	private boolean validate(MemberVO member, Map<String, String> errors) {// 콜바이레퍼런스..?- 검증하려고 따로 뺀 메서드
		boolean valid = true;
		// 검증 룰
		if (StringUtils.isBlank(member.getMem_id())) {
			valid = false;
			errors.put("mem_id", "회원아이디누락");
		}
		if (StringUtils.isBlank(member.getMem_pass())) {
			valid = false;
			errors.put("mem_pass", "비밀번호누락");
		}
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
