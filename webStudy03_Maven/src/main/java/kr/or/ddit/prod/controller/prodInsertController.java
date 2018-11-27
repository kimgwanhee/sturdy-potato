package kr.or.ddit.prod.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.prod.dao.IOtherDAO;
import kr.or.ddit.prod.dao.OtherDAOImpl;
import kr.or.ddit.prod.service.IProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.vo.ProdVO;

public class prodInsertController implements ICommandHandler {

	@Override
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String method =req.getMethod();
		IOtherDAO otherDAO = new OtherDAOImpl();
		Map<String, String> lprodList =  otherDAO.selectLprodList();
		req.setAttribute("lprodList", lprodList);
		
		String view = null;
		if("get".equalsIgnoreCase(method)) {
			 view = doGet(req, resp);//논리적이 뷰 네임
		}else if("post".equalsIgnoreCase(method)){
			 view = doPost(req, resp);
		}else {
			resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
		return view;
	}
	
	private String doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		return "prod/prodForm";
	}
	
	private String doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		ProdVO prod = new ProdVO();
		req.setAttribute("prod", prod);
		
		try {
			BeanUtils.populate(prod, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		String goPage = null;
		Map<String, String> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		boolean valid = validate(prod, errors);
		System.out.println(valid);
		if(valid) {
			IProdService service = new ProdServiceImpl();
			ServiceResult result = service.creatProd(prod);
			switch (result) {
			case FAILED:
				goPage="prod/prodForm";//디스패치 - 서버사이드방식
				req.setAttribute("message", "등록실패! ㅠㅠ");
				break;
				
			case OK:
				goPage="redirect:/prod/prodView.do?what="+ prod.getProd_id();//리다이렉트 - 클라이언트사이드방식
				break;
			}
		}else {
			goPage="prod/prodForm";//디스패치 - 서버사이드방식
		}
		return goPage;
	}
	
	private boolean validate(ProdVO prod, Map<String, String> errors) {
		boolean vaild = true;
		if(prod.getProd_cost() == null || prod.getProd_cost() <0){
			vaild = false;
			errors.put("prod_cost", "비용 누락");
		}
		if(prod.getProd_sale() == null || prod.getProd_sale() <0){
			vaild = false;
			errors.put("prod_sale", "세일가 누락");
		}
		if(prod.getProd_totalstock() == null || prod.getProd_totalstock() <0){
			vaild = false;
			errors.put("prod_totalstock", "재고량 누락");
		}
		if(prod.getProd_properstock() == null || prod.getProd_properstock() <0){
			vaild = false;
			errors.put("prod_properstock", "적정재고 누락");
		}
		if(prod.getProd_price() == null || prod.getProd_price() <0){
			vaild = false;
			errors.put("prod_price", "판매가 누락");
		}
		
		if(StringUtils.isBlank(prod.getProd_lgu())){
			errors.put("prod_lgu", "누락");
			vaild = false;
		}
		if(StringUtils.isBlank(prod.getProd_name())){
			errors.put("prod_name", "상품명 누락");
			vaild = false;
		}
		if(StringUtils.isBlank(prod.getProd_outline())){
			errors.put("prod_outline", "상품개요 누락");
			vaild = false;
		}
		if(StringUtils.isBlank(prod.getProd_buyer())){
			errors.put("prod_buyer", "판매가 누락");
			vaild = false;
		}
		if(StringUtils.isBlank(prod.getProd_img())){
			errors.put("prod_img", "이미지 경로 누락");
			vaild = false;
		}
		for(String key : errors.keySet()) {
			System.out.println(key);
		}
		return vaild;
	}
}
