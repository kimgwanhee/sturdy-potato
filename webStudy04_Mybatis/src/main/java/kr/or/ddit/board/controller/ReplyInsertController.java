package kr.or.ddit.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.service.IReplyService;
import kr.or.ddit.board.service.ReplyServiceImpl;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.vo.ReplyVO;
import kr.or.ddit.web.calculate.Mime;

@CommandHandler
public class ReplyInsertController  {
	
	@URIMapping(value="/reply/replyInsert.do", method=HttpMethod.POST)
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		//파라미터받기
		String writer = req.getParameter("rep_writer");
		String pass = req.getParameter("rep_pass");
		String content = req.getParameter("rep_content");
		String bo_no = req.getParameter("bo_no");
		//replyvo 생성후 셋팅
		ReplyVO replyVO = new ReplyVO();//command object
		replyVO.setRep_writer(writer);
		replyVO.setRep_pass(pass);
		replyVO.setRep_content(content);
		replyVO.setBo_no(Long.parseLong(bo_no));
		replyVO.setRep_ip(req.getLocalAddr());
		
		Map<String, String> errors = new LinkedHashMap<>();
		req.setAttribute("replyVO", replyVO);
		boolean valid = validate(replyVO, errors);
		String goPage = null;
		
		//등록 성공 
		if(valid) {
			//replyservice 객체생성
			IReplyService service = new ReplyServiceImpl();
			//service.create~
			ServiceResult result = service.createReply(replyVO);
			
			switch (result) {
			case OK:
				goPage = "redirect:/reply/replyList.do?bo_no="+bo_no;
				break;
			case FAILED:
				errors.put("message","서버오류");
				errors.put("error","true");
				break;
			}
		}else {
			errors.put("error","true");
			errors.put("message","검증오류");
		}
		if(errors.size() >0 ) {
			resp.setContentType(Mime.JSON.getContentType());
			ObjectMapper mapper = new ObjectMapper();
			try(
					PrintWriter out = resp.getWriter();
			){
				mapper.writeValue(out, errors);
			}
		}
		return goPage;
	}
	
	private boolean validate(ReplyVO reply, Map<String, String> errors) {
	      boolean valid = true;
	      if (reply.getBo_no() == null || reply.getBo_no() < 0) {
	         valid = false;
	         errors.put("bo_no", "게시글 번호 누락");
	      }
	      if (StringUtils.isBlank(reply.getRep_writer())) {
	         valid = false;
	         errors.put("rep_writer", "작성자 누락");
	      }
	      if (StringUtils.isBlank(reply.getRep_pass())) {
	         valid = false;
	         errors.put("rep_pass", "비밀번호 누락 누락");
	      }

	      if (reply.getRep_content() != null && reply.getRep_content().length() > 100) {
	         valid = false;
	         errors.put("rep_content", "내용의 길이는 100글자 미만으로만 해주세요");
	      }
	      return valid;
	   }
	
}
