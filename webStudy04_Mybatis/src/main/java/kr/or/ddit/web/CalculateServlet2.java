package kr.or.ddit.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.web.calculate.Operator;

public class CalculateServlet2 extends HttpServlet {
	
	@Override
	public void init(ServletConfig config) throws ServletException {//11월14일..
		super.init(config);
		ServletContext application = getServletContext();
		String contentFolder = application.getInitParameter("contentFolder");
		File folder = new File(contentFolder);
		application.setAttribute("contentFolder", folder);
		System.out.println(getClass().getSimpleName()+"초기화");
	}
	
	//  **11월9일
	//등록 - >매핑 web.xml에서..
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 파라미터 확보(입력태그의 name 속성값으로 이름 결정)
		String left = req.getParameter("leftOp");
		String right = req.getParameter("rightOp");
		String op = req.getParameter("operator");
		
		// 검증
		int leftOp, rightOp;
		Boolean valid = true;
		
		if(left==null || !left.matches("\\d+")|| right==null || !right.matches("\\d{1,6}")) {
			// 불통  400 에러 발생
			valid = false;
		} 
		
		//우리가 만들어둔 상수 value들 검증
		Operator operator = null;
		try {
			operator = Operator.valueOf(op.toUpperCase());//타입은 enum자체가 되야한다.. 
		}catch(Exception e){//상위exception으로 nullpoint까지 잡기
			valid = false;
		}
		
		if(!valid) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		// 통과
		int num1 = Integer.parseInt(left);
		int num2 = Integer.parseInt(right);
		String pattern = "%d %s %d = %d";
		//	두개의 피연산자 가지고 사칙연산 결정 - 연산자에 따라 연산수행
		String result = String.format(pattern, num1, operator.getSign(), num2, operator.operate(num1, num2));//현재응답데이타로 내보낼것
		
		String accept = req.getHeader("Accept");//헤더는 네임과 값이 같이들어감
		String mime = null;
		//json.org -> json문법 정리
		if(accept.contains("plain")) {
			mime = "text/plain;charset=UTF-8";
		}else if(accept.contains("json")) {
			mime = "application/json;charset=UTF-8";
			result = "{\"result\":\""+ result+"\"}";//json표현방식으로 응답데이타 내보냄
		}else {
			mime = "text/html;charset=UTF-8";
			result = "<p>"+result+"<p>";
		}
		
		
		
		/*
		switch (operator) {
			case ADD://operator안에 있는 enum상수로 ADD를 사용가능하게 됨
				result = String.format(pattern, num1, operator.getSign(), num2, (num1+num2));
				break;
			case MINUS:
				result = String.format(pattern, num1, operator.getSign(), num2, (num1-num2));
				break;
			case MULTIPLY:
				result = String.format(pattern, num1, operator.getSign(), num2, (num1*num2));
				break;
			case DIVIDE:
				result = String.format(pattern, num1, operator.getSign(), num2, (num1/num2));;
				break;
		}
		*/
		
		//	일반 텍스트의 형태로 연산 결과를 제공. -> 응답데이터가 html로 나가는것이 아니다. mime을 설정해서 !
		resp.setContentType(mime);// ↓출력스트림을 개방하기 전에 반드시 설정할것 //특수문자가 포함되어있다면 => ;charset=UTF-8
		
		//7. 응답데이터로 내보내야하므로..resp한테 가져오기
		PrintWriter out = resp.getWriter();
		out.print(result);//최종적으로 완성된 소스는 html이 가지고있으므로
		out.close();//닫기
		
		//연산결과 : 2*3=6과 같은 형태로..
		
		
		
	}

}
