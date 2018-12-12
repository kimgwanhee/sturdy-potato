package kr.or.ddit.buyer.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.buyer.service.BuyerServiceImpl;
import kr.or.ddit.buyer.service.IBuyerService;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.web.calculate.Mime;

@CommandHandler
public class BuyerListController{

	//비즈니스모델 선택
	//모델생성
	//뷰선택 후 주소를 frontcontroller로 보내기
	@URIMapping("/buyer/buyerList.do")
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		String searchWord = req.getParameter("searchWord");
		String searchType = req.getParameter("searchType");
		//doget dopost로 구분
		
		int currentPage = 1; 
		String page = req.getParameter("page");	
		if(StringUtils.isNotBlank(page)) {//페이지가 있다면
			currentPage = Integer.parseInt(page);
		}
		
		PagingInfoVO<BuyerVO> pagingVO = new PagingInfoVO<BuyerVO>(5,2); // 
		pagingVO.setSearchWord(searchWord);
		pagingVO.setSearchType(searchType);
		pagingVO.setCurrentPage(currentPage);
		IBuyerService service = new BuyerServiceImpl();
		long totalRecord = service.retrieveBuyerCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		
		List<BuyerVO>  buyerList = service.retrieveBuyerList(pagingVO);
		pagingVO.setDataList(buyerList);
		
		req.setAttribute("pagingVO", pagingVO);
		String accept = req.getHeader("Accept");
		//application/json, text/javascript, */*; q=0.01
		if(StringUtils.containsIgnoreCase(accept, "json")) {
			//JSON
			resp.setContentType(Mime.JSON.getContentType());
			ObjectMapper mapper = new ObjectMapper();
			//자바를 json으로 바꾸려면 마샬링
			
			
			try(
			PrintWriter out = resp.getWriter();
			){
			mapper.writeValue(out, pagingVO);
			}
			return null;
		}else{
			//HTML
			req.setAttribute("pagingVO", pagingVO);
			
			return "buyer/buyerList";
		}
	}
}
