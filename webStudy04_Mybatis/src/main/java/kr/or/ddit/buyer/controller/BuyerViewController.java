package kr.or.ddit.buyer.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.buyer.service.BuyerServiceImpl;
import kr.or.ddit.buyer.service.IBuyerService;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.vo.BuyerVO;

@CommandHandler
public class BuyerViewController {

	@URIMapping("/buyer/buyerView.do")
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		//model 선택하고
		//model 데이터를 생성
		//view 결정하기
		String buyer_id = req.getParameter("who");
		if(StringUtils.isBlank(buyer_id)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);//잘못된 요청
			return null;
		}
		Map<String, String> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		
		IBuyerService service = new BuyerServiceImpl();
		BuyerVO buyerVO =  service.retrieveBuyer(buyer_id);
		
		req.setAttribute("buyer", buyerVO);
		String view = "buyer/buyerView";
		return view;
	}
}
