package kr.or.ddit.prod.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.prod.dao.IOtherDAO;
import kr.or.ddit.prod.dao.OtherDAOImpl;
import kr.or.ddit.prod.service.IProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.ProdVO;
import kr.or.ddit.web.calculate.Mime;

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
		
		String accept = req.getHeader("Accept");
		if(StringUtils.containsIgnoreCase(accept, "json")) {
			//JSON
			resp.setContentType(Mime.JSON.contentType);
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
			return "prod/prodList";
		}		
		//6. 공유하기
		//7. 뷰선택
	}
}
