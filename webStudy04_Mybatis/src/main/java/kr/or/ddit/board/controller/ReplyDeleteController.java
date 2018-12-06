package kr.or.ddit.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.service.IReplyService;
import kr.or.ddit.board.service.ReplyServiceImpl;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.vo.ReplyVO;
import kr.or.ddit.web.calculate.Mime;

public class ReplyDeleteController implements ICommandHandler {

	@Override
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		String rep_noStr = req.getParameter("rep_no");
		String rep_pass = req.getParameter("rep_pass");
		String bo_noStr = req.getParameter("bo_no");
		
		if(!StringUtils.isNumeric(rep_noStr)||
				StringUtils.isBlank(rep_pass)|| 
				!StringUtils.isNumeric(bo_noStr)) {
			resp.sendError(400);
			return null;
		}
		
		//자바빈의 setter를 통해 객체의 상태를 설정 - JavaBean Pattern -> Builder Pattern
		ReplyVO reply = new ReplyVO();
		reply.setRep_no(Long.parseLong(rep_noStr));
		reply.setBo_no(Long.parseLong(bo_noStr));
		reply.setRep_pass(rep_pass);
		
		//위 검증 통과 후 삭제 -> 이제service필요
		IReplyService service = new ReplyServiceImpl();
		ServiceResult result = service.removeReply(reply); //커맨드 오브젝트???
		String view = null;
		Map<String, String> errors = new HashMap<>();
		boolean requireMarshalling = false;
		
		switch (result) {
		case OK:
			view = "redirect:/reply/replyList.do?bo_no"+reply.getBo_no();
			break;
		case INVALIDPASSWORD:
			errors.put("error", "true");
			errors.put("message", "비밀번호 오류");
			requireMarshalling = true;
			break;
		default:	//FAILED
			errors.put("error", "true");
			errors.put("message", "서버 오류, 쫌따 다시");
			requireMarshalling = true;
		}
		if(requireMarshalling) {
			//마샬링과 직렬화해서 내보내기-> 마임먼저
			resp.setContentType(Mime.JSON.getContentType());
			ObjectMapper mapper = new ObjectMapper();
			try(
				PrintWriter out = resp.getWriter();
			){
				//이제 마샬링 -> writeValue계열
				mapper.writeValue(out, errors);
			}
		}
		return view;
	}
}
