package kr.or.ddit.buyer.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.mvc.ICommandHandler;

public class BuyerInsertController implements ICommandHandler{
	
	@Override
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String view = null;
		if("get".equalsIgnoreCase(req.getMethod())) {	//그냥 가입될때//페이지만 보여줄때 doget
			view = doGet(req, resp);
		}else {//post실행
			view = doPost(req, resp);
		}
		return view;
	}

	private String doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		return "buyer/buyerForm";//서버사이드
	}
	private String doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		return null;
		
	}
}
