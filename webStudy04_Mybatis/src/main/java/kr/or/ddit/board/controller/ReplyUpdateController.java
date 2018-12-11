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
public class ReplyUpdateController{

	@URIMapping(value="/reply/replyUpdate.do", method=HttpMethod.POST)
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String rep_noStr = req.getParameter("rep_no");
		String rep_writer = req.getParameter("rep_writer");
		String rep_content = req.getParameter("rep_content");
		String rep_pass = req.getParameter("rep_pass");
		String bo_no = req.getParameter("bo_no");
		

		if (!StringUtils.isNumeric(rep_noStr) || StringUtils.isBlank(rep_pass) || StringUtils.isBlank(rep_content)
				|| StringUtils.isBlank(rep_writer)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		ReplyVO replyVO = new ReplyVO();
		replyVO.setRep_no(Long.parseLong(rep_noStr));
		replyVO.setRep_content(rep_content);
		replyVO.setRep_pass(rep_pass);
		replyVO.setRep_writer(rep_writer);

		Map<String, String> errors = new LinkedHashMap<>();
		req.setAttribute("replyVO", replyVO);
		String goPage = null;

		// 등록 성공
		// replyservice 객체생성
		IReplyService service = new ReplyServiceImpl();
		// service.create~
		ServiceResult result = service.modifyReply(replyVO);

		switch (result) {
		case OK:
			goPage = "redirect:/board/boardView.do?what="+Long.parseLong(bo_no);
			break;
		case FAILED:
			errors.put("message", "서버오류");
			errors.put("error", "true");
			break;
		}

		if (errors.size() > 0) {
			resp.setContentType(Mime.JSON.getContentType());
			ObjectMapper mapper = new ObjectMapper();
			try (PrintWriter out = resp.getWriter();) {
				mapper.writeValue(out, errors);
			}
		}
		return goPage;
	}
}
