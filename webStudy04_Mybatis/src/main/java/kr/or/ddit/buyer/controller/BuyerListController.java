package kr.or.ddit.buyer.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.buyer.service.BuyerServiceImpl;
import kr.or.ddit.buyer.service.IBuyerService;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingInfoVO;

public class BuyerListController implements ICommandHandler{

	//비즈니스모델 선택
	//모델생성
	//뷰선택 후 주소를 frontcontroller로 보내기
	@Override
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
		
//		if("get".equalsIgnoreCase(req.getMethod())) {
//		}
		String view = "buyer/buyerList";
		return view;
	}
}
