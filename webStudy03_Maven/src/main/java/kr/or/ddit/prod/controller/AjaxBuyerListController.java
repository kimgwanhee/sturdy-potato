package kr.or.ddit.prod.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.prod.dao.IOtherDAO;
import kr.or.ddit.prod.dao.OtherDAOImpl;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.web.calculate.Mime;

public class AjaxBuyerListController implements ICommandHandler {

	@Override
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String prod_lgu = req.getParameter("prod_lgu");
		
		IOtherDAO otherDAO = new OtherDAOImpl();
		List<BuyerVO> buyerList = otherDAO.selectBuyerList(prod_lgu);
		//마샬링..??ㅇ?ㅇ?ㅇ?ㅇ?
		ObjectMapper mapper = new ObjectMapper();
		resp.setContentType(Mime.JSON.getContentType());
		try(
		PrintWriter out = resp.getWriter();
		){
		mapper.writeValue(out, buyerList);//json은 문자기반이기 때문에 write를 넘기려고 하는것//이메소드안은 자동close해줌 하지만 안전하게 
		//언마샬링 - reader객체? 마샬링- write계열 메서드사용?
		}
		return null;//여기서 나가는 데이터는 json데이터다 설정필요-> 마임-출력스트림 열리기전에
	}
}
