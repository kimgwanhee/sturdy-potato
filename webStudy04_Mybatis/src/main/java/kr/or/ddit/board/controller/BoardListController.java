package kr.or.ddit.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.web.calculate.Mime;

@CommandHandler//현재 이건 자동으로 등록만하고 요청정보는 없음
public class BoardListController {
	
	@URIMapping("/board/boardList.do")//메서드 숨어있고 get방식으로 설정 - uri에 등록된 한줄이 만들어진것
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		String page = req.getParameter("page");
		String searchType = req.getParameter("searchType");
		String searchWord = req.getParameter("searchWord");
		BoardVO searchVO = new BoardVO();
		if(StringUtils.isNotBlank(searchWord)) {
			if(StringUtils.isBlank(searchType)) {
				searchVO.setBo_writer(searchWord);
				searchVO.setBo_title(searchWord);
				searchVO.setBo_content(searchWord);
			}else {
				switch (searchType) {
				case "writer":
					searchVO.setBo_writer(searchWord);
					break;
				case "title":
					searchVO.setBo_title(searchWord);
					break;
				case "content":
					searchVO.setBo_content(searchWord);	
					break;
				}
			}			
		}
		long currentPage = 1;
		if(StringUtils.isNumeric(page)) {
			currentPage = Long.parseLong(page);
		}
		
		
		PagingInfoVO<BoardVO> pagingVO = new PagingInfoVO<>();
		pagingVO.setCurrentPage(currentPage);
		/* 검색 정보 */
		pagingVO.setSearchVO(searchVO);
		IBoardService service = new BoardServiceImpl();
		long totalRecord = service.retriveBoardCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		
		List<BoardVO> boardList = service.retriveBoardList(pagingVO);
		pagingVO.setDataList(boardList);
		
		String accept = req.getHeader("Accept");
		if(StringUtils.containsIgnoreCase(accept, "json")) {
			resp.setContentType(Mime.JSON.getContentType()); 
			ObjectMapper mapper = new ObjectMapper();
			try(
				PrintWriter out = resp.getWriter();
			){
				mapper.writeValue(out, pagingVO);				
				return null;
			}
		}else {
		// HTML
			req.setAttribute("pagingVO", pagingVO);
			return "board/boardList";
		}
	}
}