package kr.or.ddit.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.dao.IBoardDAO;
import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.vo.BoardVO;

public class BoardDeleteController implements ICommandHandler {

	@Override
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String bonoStr = req.getParameter("bo_no");
		String bo_pass = req.getParameter("bo_pass");
		
		if(StringUtils.isNumeric(bonoStr)||StringUtils.isBlank(bo_pass)) {
			resp.sendError(400);
			return null;
		}
		
		BoardVO board = new BoardVO();
		board.setBo_no(Long.parseLong(bonoStr));
		board.setBo_pass(bo_pass);
		req.setAttribute("board", board);
		
		IBoardService service = new BoardServiceImpl();
		ServiceResult result = service.removeBoard(board);
		String goPage = null;
		switch (result) {
		case OK:
			goPage = "redirect:/board/boardList.do";
			break;
		case FAILED:
			goPage = "board/boardView";
			break;
		case INVALIDPASSWORD:
			goPage = "board/boardView";
			break;
		}		
		return goPage;
	}
}
