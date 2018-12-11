package kr.or.ddit.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.board.dao.BoardDAOImpl;
import kr.or.ddit.board.dao.IBoardDAO;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.web.calculate.Mime;

@CommandHandler
public class BoardRecommandController {
	IBoardDAO boardDAO = new BoardDAOImpl();
	
	@URIMapping("/board/boardRecommand.do")
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String bonoStr = req.getParameter("bo_no");
		
		if(!StringUtils.isNumeric(bonoStr)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		
		int rowCnt = boardDAO.incrementRcmd(Long.parseLong(bonoStr));
		
		Map<String, String> message = new LinkedHashMap<>();
		if(rowCnt > 0) {
			 message.put("message", "ok");
		}
		
		resp.setContentType(Mime.JSON.getContentType());
		try(
				PrintWriter out = resp.getWriter();
		){
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(out, message);
		}
		return null;
	}
}
