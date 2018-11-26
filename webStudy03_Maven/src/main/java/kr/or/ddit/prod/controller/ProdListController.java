package kr.or.ddit.prod.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.prod.dao.IOtherDAO;
import kr.or.ddit.prod.dao.OtherDAOImpl;
import kr.or.ddit.prod.service.IProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.ProdVO;

public class ProdListController implements ICommandHandler {

	@Override
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		//1. 매핑 설정 해야함 -> urihandler~
		//2. 요청분석
		ProdVO searchVO = new ProdVO();
		searchVO.setProd_lgu(req.getParameter("prod_lgu"));
		searchVO.setProd_buyer(req.getParameter("prod_buyer"));
		searchVO.setProd_name(req.getParameter("prod_name"));
		
		String page = req.getParameter("page");
		int currentPage=1;
		
		if(StringUtils.isNumeric(page)) {//정규표현식 사용안해도 숫자여부 확인
			currentPage = Integer.parseInt(page);//page를 파싱..?
		}
		PagingInfoVO<ProdVO> pagingVO = new PagingInfoVO(7,4);
		pagingVO.setCurrentPage(currentPage);
		pagingVO.setSearchVO(searchVO);
		
		//3. 의존관계형성
		IProdService service = new ProdServiceImpl();
		IOtherDAO otherDAO = new OtherDAOImpl();
		Map<String, String> lprodList = otherDAO.selectLprodList();
		List<BuyerVO> buyerList = otherDAO.selectBuyerList(null);
		req.setAttribute("lprodList", lprodList);
		req.setAttribute("buyerList", buyerList);
		//4. 로직선택
//		service.retrieveProdList(pagingVO);
		//5. 로직을 돌려주는 데이타를 갖고 
		long totalRecord = service.retrieveProdCount(pagingVO);
		pagingVO.setTotalPage(totalRecord);
		
		List<ProdVO> prodList = service.retrieveProdList(pagingVO);
		pagingVO.setDataList(prodList);
		
		req.setAttribute("pagingVO", pagingVO);
		
		
		//6. 공유하기
		//7. 뷰선택
		
		
		return "prod/prodList";
	}
}
