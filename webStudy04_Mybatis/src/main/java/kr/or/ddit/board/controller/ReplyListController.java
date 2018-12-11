package kr.or.ddit.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.language.bm.Rule.RPattern;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.board.service.IReplyService;
import kr.or.ddit.board.service.ReplyServiceImpl;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.ReplyVO;
import kr.or.ddit.web.calculate.Mime;

@CommandHandler
public class ReplyListController {

	@URIMapping("/reply/replyList.do")
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		//bo_no, page 이 두가지를 먼저 뽑아내야할것
		
		String bo_noStr = req.getParameter("bo_no");
		String page = req.getParameter("page");
		
		if(!StringUtils.isNumeric(bo_noStr)) {
			resp.sendError(400);
			return null;
		}
		
		long currentPage = 1;
		if(StringUtils.isNumeric(page)) {
			currentPage = Long.parseLong(page);
		}
		
		PagingInfoVO<ReplyVO> pagingVO = new PagingInfoVO<>();
		pagingVO.setCurrentPage(currentPage);
		IReplyService replyService = new ReplyServiceImpl();
		ReplyVO searchVO = new ReplyVO();
		searchVO.setBo_no(Long.parseLong(bo_noStr));
		pagingVO.setSearchVO(searchVO);
		long totalRecord = replyService.retriveReplyCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		List<ReplyVO> replyList = replyService.retriveReplyList(pagingVO);
		pagingVO.setDataList(replyList);
		
		resp.setContentType(Mime.JSON.getContentType());
		ObjectMapper mapper = new ObjectMapper();
		try(
				PrintWriter out = resp.getWriter();
		){
			mapper.writeValue(out, pagingVO);
		}
		return null;
	}
}
