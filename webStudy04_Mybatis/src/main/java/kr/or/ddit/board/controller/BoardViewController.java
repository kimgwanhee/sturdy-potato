package kr.or.ddit.board.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.board.dao.BoardDAOImpl;
import kr.or.ddit.board.dao.IBoardDAO;
import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.board.service.IReplyService;
import kr.or.ddit.board.service.ReplyServiceImpl;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.ReplyVO;
import kr.or.ddit.web.calculate.Mime;

@CommandHandler
public class BoardViewController{
	IBoardService service = new BoardServiceImpl();
	IBoardDAO boardDao = new BoardDAOImpl();

	@URIMapping("/board/boardView.do")
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String bonoStr = req.getParameter("what");
		
		if(StringUtils.isBlank(bonoStr)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		long bo_no = Long.parseLong(bonoStr);
		
		boardDao.incrementHit(bo_no);
		
		BoardVO boardVO = service.retriveBoard(bo_no);
		req.setAttribute("board", boardVO);
		
		return "board/boardView";
	}

}
