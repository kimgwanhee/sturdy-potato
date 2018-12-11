package kr.or.ddit.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.dao.IBoardDAO;
import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.vo.BoardVO;

@CommandHandler
public class BoardDeleteController{
	IBoardService service = new BoardServiceImpl();

	@URIMapping(value="/board/boardDelete.do", method=HttpMethod.POST)
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String bonoStr = req.getParameter("bo_no");
		String bo_pass = req.getParameter("bo_pass");
		
		if(!StringUtils.isNumeric(bonoStr)||StringUtils.isBlank(bo_pass)) {
			resp.sendError(400);
			return null;
		}
		
		BoardVO board = new BoardVO();
		board.setBo_no(Long.parseLong(bonoStr));
		board.setBo_pass(bo_pass);
		req.setAttribute("board", board);
		
		ServiceResult result = service.removeBoard(board);
		String goPage = null;
		HttpSession session = req.getSession();
		switch (result) {
		case OK:
			goPage = "redirect:/board/boardList.do";
			break;
		case FAILED:
			goPage = "redirect:/board/boardView.do?what="+bonoStr;
			session.setAttribute("message", "서버 오류");
			break;
		case INVALIDPASSWORD:
			goPage = "redirect:/board/boardView.do?what="+bonoStr;
			//forward를 사용하면boarddelete.do가 남아있는데 f5되면 잘못된 정보를가지고 또 삭제를 실행해서 안됨 -> 인증실패-> redirect
			session.setAttribute("message", "비번 오류");
			break;
		}		
		return goPage;
	}
}
