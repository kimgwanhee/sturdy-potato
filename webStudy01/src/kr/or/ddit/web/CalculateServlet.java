package kr.or.ddit.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.web.calculate.MimeTypeMap;
import kr.or.ddit.web.calculate.Operator;



public class CalculateServlet extends HttpServlet {
	
	//  **11월9일
//	enum Operator{
//		
//		
//		
//	}//열거형 상수 , 상수 집합
	@Override
	public void init(ServletConfig config) throws ServletException {//11월14일..
		super.init(config);
		ServletContext application = getServletContext();
		String contentFolder = application.getInitParameter("contentFolder");
		File folder = new File(contentFolder);
		application.setAttribute("contentFolder", folder);
		System.out.println(getClass().getSimpleName()+"초기화");
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//파라미터 확보
		
		String leftOp=req.getParameter("leftOp");
		String operator=req.getParameter("operator");
		String rightOp=req.getParameter("rightOp");
		int left=0;
		int right=0;
		boolean valid =true;
		//검증
		if(leftOp==null||leftOp.trim().length()==0||!leftOp.matches("\\d+")
				||rightOp==null||rightOp.trim().length()==0||!rightOp.matches("\\d{1,6}")) {
			valid=false;
		}else {
			left=Integer.parseInt(leftOp);
			right=Integer.parseInt(rightOp);
		}
		
//		if(operator==null) {
//			valid=false;
//			resp.sendError(400);
//			return;
//		}//널체킹을 위한 비효율
		Operator operator2=null;
		try {
			operator2= Operator.valueOf(operator.toUpperCase());// enum의 상수명과 일치하는 명을 반환해서 op2에 넣어줌
			
			
		} catch (Exception e) {
			valid=false;
		
		}//enum타입의 예외처리를 위한 트라이 케치문 . 
		if(!valid) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		} //모든 검증을 끝내후 에러 400을 출력해주기 위한 if문 
		System.out.println(operator);
		String result;
		//통과
		//연산자에 따라 연산 수행
		String pattern="%d %s %d= %d";
			
//			result=left+right;
			result= String.format(pattern, left,operator2.getSign(),right,operator2.operate(left, right));
			String accept=req.getHeader("Accept");
			
			//요기부분 enum 적용하여 이프문 한문장으로 줄이기 ...
			String mime=null;
			MimeTypeMap mimeType=null;
			int start=accept.indexOf("/")+1;
			int end=accept.indexOf(",");
			
			String mimeSub=accept.substring(start, end);//accept에서 받아온 값을 짤라주기위함 
			//ex) application/json 형식이므로 json만 짜름
			boolean resE=true;
			try {
				mimeType=mimeType.valueOf(mimeSub.toUpperCase());//대문자로 맞춰서 세팅
			} catch (Exception e) {
				resE=false;
			}
			System.out.println(mimeType.mimeType(mimeSub,result));
//			if (accept.contains("plain")) {
//				mime="text/plain;charset=UTF-8";
//			}else if(accept.contains("json")) {
//				
//				mime="application/json;charset=UTF-8";
//				result="{\"result\":\""+result+"\"}";
//			}else {
//				mime="text/html;charset=UTF-8";
//				result="<p>"+result+"</p>";
//				
//			}
		Map<String, String> mimeMap=mimeType.mimeType(mimeSub, result);
		//인터페이스를 돌아서 맵형태를 반환받음.
		resp.setContentType(mimeMap.get("mime"));
		//mime을 키값으로하는 마임타입 세팅
		PrintWriter out= resp.getWriter();
		out.println(mimeMap.get("result"));
		//마임타입에 따른 결과값 세팅
		out.close();
		//일반  텍스트의 형태로 연산 결과를 제공
		//연산결과 : 2 * 3 = 6 
		//불통 400에러 발생
		//1번 서블릿의 의미
		//2 http
	}
}
