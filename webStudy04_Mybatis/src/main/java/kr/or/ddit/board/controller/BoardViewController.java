package kr.or.ddit.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.board.service.IReplyService;
import kr.or.ddit.board.service.ReplyServiceImpl;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.ReplyVO;

public class BoardViewController implements ICommandHandler{

	@Override
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String bonoStr = req.getParameter("what");
		
		if(StringUtils.isBlank(bonoStr)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		long bo_no = Long.parseLong(bonoStr);
		
		IBoardService service = new BoardServiceImpl();
		
		BoardVO boardVO = service.retriveBoard(bo_no);
		req.setAttribute("board", boardVO);
		
		return "board/boardView";
	}

}
