package kr.or.ddit.buyer.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.buyer.service.BuyerServiceImpl;
import kr.or.ddit.buyer.service.IBuyerService;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.vo.BuyerVO;

public class BuyerListController implements ICommandHandler{

	//비즈니스모델 선택
	//모델생성
	//뷰선택 후 주소를 frontcontroller로 보내기
	@Override
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		//doget dopost로 구분
		IBuyerService service = new BuyerServiceImpl();
		List<BuyerVO>  buyerList = service.retrieveBuyerList();
//		if("get".equalsIgnoreCase(req.getMethod())) {
//		}
		String view = "buyer/buyerList";
		req.setAttribute("buyerList", buyerList);
		return view;
	}

}
