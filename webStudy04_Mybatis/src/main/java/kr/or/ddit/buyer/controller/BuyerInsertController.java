package kr.or.ddit.buyer.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import kr.or.ddit.prod.dao.IOtherDAO;
import kr.or.ddit.prod.dao.OtherDAOImpl;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.MemberVO;

public class BuyerInsertController implements ICommandHandler{
	
	@Override
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		IOtherDAO otherDao = new OtherDAOImpl();
		List<Map<String, Object>> lprodList=otherDao.selectLprodList();
		req.setAttribute("lprodList", lprodList);
		String view = null;
		if("get".equalsIgnoreCase(req.getMethod())) {	//그냥 가입될때//페이지만 보여줄때 doget
			view = doGet(req, resp);
		}else if("post".equalsIgnoreCase(req.getMethod())){//post실행
			view = doPost(req, resp);
		}else {
			resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
		return view;
	}

	private String doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		return "buyer/buyerForm";//서버사이드
	}
	private String doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		BuyerVO buyer = new BuyerVO();
		req.setAttribute("buyer", buyer);
		
		try {
			BeanUtils.populate(buyer, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		//검증
		String goPage = null;
		String message = null;
		
		Map<String, String> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		boolean valid = validate(buyer, errors);
		if(valid) {
			IBuyerService service = new BuyerServiceImpl();
			ServiceResult result = service.registBuyer(buyer);
			
			switch (result) {
			case OK:
				goPage = "redirect:/buyer/buyerList.do";	//클라이언트사이드방식
				break;
			case FAILED:
				goPage = "buyer/buyerForm";	//서버사이드방식
				message = "등록에 실패..";
				break;
			}
		}else {
			goPage = "buyer/buyerForm";
		}
		return goPage;
	}
	
	
	private boolean validate(BuyerVO buyer, Map<String, String> errors) {
		boolean valid = true;
		if(StringUtils.isBlank(buyer.getBuyer_name())) {
			valid = false;
			errors.put("buyer_name", "거래처명 누락");
		}
		if(StringUtils.isBlank(buyer.getBuyer_lgu())) {
			valid = false;
			errors.put("buyer_lgu", "분류코드 누락");
		}
		if(StringUtils.isBlank(buyer.getBuyer_comtel())) {
			valid = false;
			errors.put("buyer_comtel", "회사번호 누락");
		}
		if(StringUtils.isBlank(buyer.getBuyer_fax())) {
			valid = false;
			errors.put("buyer_fax", "팩스번호 누락");
		}
		if(StringUtils.isBlank(buyer.getBuyer_mail())) {
			valid = false;
			errors.put("buyer_mail", "메일 누락");
		}
		return valid;
		
	}
}
