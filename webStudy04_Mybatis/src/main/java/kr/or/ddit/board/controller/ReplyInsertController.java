package kr.or.ddit.board.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.service.IReplyService;
import kr.or.ddit.board.service.ReplyServiceImpl;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.vo.ReplyVO;

public class ReplyInsertController implements ICommandHandler {

	@Override
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
		
		req.setAttribute("replyVO", replyVO);
		boolean valid = true;
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
				goPage = "board/boardView";
				break;
			}
		}else {
			goPage = "board/boardView";
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
