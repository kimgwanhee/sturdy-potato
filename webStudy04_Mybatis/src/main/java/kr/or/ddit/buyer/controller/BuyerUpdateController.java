package kr.or.ddit.buyer.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.buyer.dao.IBuyerDAO;
import kr.or.ddit.buyer.service.BuyerServiceImpl;
import kr.or.ddit.buyer.service.IBuyerService;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.vo.BuyerVO;

public class BuyerUpdateController implements ICommandHandler {

	@Override
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		//REQUEST 파라미터로 받은값을 BUYER객체에 담아주기
		BuyerVO buyer = new BuyerVO();
		// 힌트 : populate
		try {
			BeanUtils.populate(buyer,  req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		//request set attribute buyer
		req.setAttribute("buyer", buyer);
		
		//검증 validate()
		boolean valid = true;
		String goPage = null;
		if(valid) {
			//의존관계 서비스뚫기
			//스위치 
			IBuyerService service = new BuyerServiceImpl();
			ServiceResult result = service.modifyBuyer(buyer);
			switch (result) {
			case FAILED:
				goPage = "/buyer/buyerView";	// 디스패치-서버사이드
				break;
			case OK:
				goPage = "redirect:/buyer/buyerList.do";	// 리다이렉트-클라이언트
				break;
			}
		}else {
			goPage = "/buyer/buyerView";	// 디스패치-서버사이드
		}
		return goPage;
	}
	
	
//	private boolean validate(buyer , errors){
	
//	}
	
	
}
