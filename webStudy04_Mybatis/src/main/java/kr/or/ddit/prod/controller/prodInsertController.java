package kr.or.ddit.prod.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.CommonException;
import kr.or.ddit.ServiceResult;
import kr.or.ddit.filter.wrapper.FileUploadRequestWrapper;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.prod.dao.IOtherDAO;
import kr.or.ddit.prod.dao.OtherDAOImpl;
import kr.or.ddit.prod.service.IProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.vo.ProdVO;

@CommandHandler
public class prodInsertController {
	IProdService service = new ProdServiceImpl();
	IOtherDAO otherDAO = new OtherDAOImpl();
	
	public void makeLprodList(HttpServletRequest req) {
		List<Map<String, Object>> lprodList = otherDAO.selectLprodList();
		req.setAttribute("lprodList", lprodList);
	}

	@URIMapping(value="/prod/prodInsert.do", method=HttpMethod.GET)
	public String doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		makeLprodList(req);
		return "prod/prodForm";
	}
	
	@URIMapping(value="/prod/prodInsert.do", method=HttpMethod.POST)
	public String doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		makeLprodList(req);
		ProdVO prod = new ProdVO();
		req.setAttribute("prod", prod);
		
		try {
			BeanUtils.populate(prod, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new CommonException();
		}
		
		String goPage = null;
		Map<String, String> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		boolean valid = validate(prod, errors);
		System.out.println(valid);
		if(valid) {
			if(req instanceof FileUploadRequestWrapper) {
				String prodImagesUrl = "/prodImages";
				String prodImagesPath = req.getServletContext().getRealPath(prodImagesUrl);
				File prodImagesFolder = new File(prodImagesPath);
				
				FileItem fileitem = ((FileUploadRequestWrapper)req).getFileItem("prod_image");
				if(fileitem != null) {
					//상품의 이미지를 어딘가에 저장해야하고 이름만 꺼내서 db에 넣어줘야하는 상황
					String savename = UUID.randomUUID().toString();
					File saveFile = new File(prodImagesFolder, savename);
					try(
						InputStream in = fileitem.getInputStream();
					){
						FileUtils.copyInputStreamToFile(in, saveFile);
						prod.setProd_img(savename);
					}
				}
			}
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
		for(String key : errors.keySet()) {
			System.out.println(key);
		}
		return vaild;
	}
}
