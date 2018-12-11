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
public class prodUpdateController {

	@URIMapping(value="/prod/prodUpdate.do", method=HttpMethod.POST)
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		
		//1. 메소드 꺼내기
		String method = req.getMethod();
		IOtherDAO otherDAO = new OtherDAOImpl();
		List<Map<String, Object>> lprodList = otherDAO.selectLprodList();
		req.setAttribute("lprodList", lprodList);
		
		if("get".equalsIgnoreCase(method)) {
			String prod_id = req.getParameter("what");//어떤상품이라는 pk가됨
			if(StringUtils.isBlank(prod_id)) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return null;
			}
			IProdService service = new ProdServiceImpl();
			ProdVO prod = service.retrieveProd(prod_id);
			req.setAttribute("prod", prod);
			return "prod/prodForm";
		}else if("post".equalsIgnoreCase(method)){
			ProdVO prod = new ProdVO();
			req.setAttribute("prod", prod);
			try {
				BeanUtils.populate(prod, req.getParameterMap());
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String goPage = null;
			Map<String, String> errors = new LinkedHashMap<>();
			req.setAttribute("errors", errors);
			boolean vaild = vaildate(prod, errors);
			if (vaild) {
				if(req instanceof FileUploadRequestWrapper) {
					String prodImagesUrl = "/prodImages";
					String prodImagesPath = req.getServletContext().getRealPath(prodImagesUrl);
					File prodImagesFolder = new File(prodImagesPath);
					if(!prodImagesFolder.exists()) {//없다면 저장할 위치 잡기 
						prodImagesFolder.mkdirs();//mkdirs는 대충구조에 따라서 상위폴더까지 만들어줌
					}	
					FileItem fileItem = ((FileUploadRequestWrapper) req).getFileItem("prod_image");
					if(fileItem != null) {
						String savename = UUID.randomUUID().toString();
						File saveFile = new File(prodImagesFolder, savename);
						try(
							InputStream in = fileItem.getInputStream();
						){
							FileUtils.copyInputStreamToFile(in, saveFile);
						}//여기까지 오면 저장된것
						prod.setProd_img(savename);//디비에 넣기
					}
				}
				
				IProdService service = new ProdServiceImpl();
				ServiceResult result = service.modifyPord(prod);
				switch (result) {
				case OK:
					goPage = "redirect:/prod/prodView.do?what="+prod.getProd_id();
					break;
				case FAILED:
					goPage = "/prod/prodForm";
					break;
				}
			} else {
				goPage = "/prod/prodForm";
			}
			return goPage;
			
		}else {
			resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
		return null;
	}
		/*
	}
	*/

	private boolean vaildate(ProdVO prod, Map<String, String> errors) {
		boolean valid = true;
		if (StringUtils.isBlank(prod.getProd_name())) {
			valid = false;
			errors.put("prod_name", "상품명 누락");
		}
//		if (StringUtils.isBlank(prod.getProd_cost())) {
//			valid = false;
//			errors.put("prod_cost", "비용 누락");
//		}
//		if (StringUtils.isBlank(prod.getProd_price())) {
//			valid = false;
//			errors.put("prod_price", "판매가 누락");
//		}
//		if (StringUtils.isBlank(prod.getProd_sale())) {
//			valid = false;
//			errors.put("prod_sale", "세일가 누락");
//		}
		if (StringUtils.isBlank(prod.getProd_outline())) {
			valid = false;
			errors.put("prod_outline", "상품개요 누락");
		}
		if (StringUtils.isBlank(prod.getProd_img())) {
			valid = false;
			errors.put("prod_img", "이미지경로 누락");
		}
//		if (StringUtils.isBlank(prod.getProd_totalstock())) {
//			valid = false;
//			errors.put("prod_totalstock", "재고량 누락");
//		}
//		if (StringUtils.isBlank(prod.getProd_properstock())) {
//			valid = false;
//			errors.put("prod_properstock", "적정재고 누락");
//		}
		return false;

	}

}
