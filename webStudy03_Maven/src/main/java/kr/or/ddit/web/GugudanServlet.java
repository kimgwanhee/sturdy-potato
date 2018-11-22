package kr.or.ddit.web;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.util.*;
import javax.servlet.annotation.*;

@WebServlet(value = "/gugudan.do", loadOnStartup=3)
public class GugudanServlet extends HttpServlet{

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String minDanStr = req.getParameter("minDan");
		String maxDanStr = req.getParameter("maxDan");
		
		int minDan = 2;
		int maxDan = 9;
		
		if(minDanStr!=null && minDanStr.matches("\\d")) {
			minDan = Integer.parseInt(minDanStr);
		}
		if(maxDanStr!=null && maxDanStr.matches("[0-9]")) {
			maxDan = Integer.parseInt(maxDanStr);
		}
		// 2~9단 까지의 구구단을 table태그를 이용하여 출력
		// 단, 한행에 한단씩 ...
		// 테스트시에 /gugudan.do요청을 사용.
		// web.xml을 사용하지 말것 ..
		resp.setContentType("text/html;charset=UTF-8");
		InputStream in = this.getClass().getResourceAsStream("gugudan.205");
		InputStreamReader isr = new InputStreamReader(in, "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		StringBuffer html = new StringBuffer();
		String temp = null;
		
		while((temp = br.readLine()) != null){
			html.append(temp);
		}
			
		StringBuffer sb = new StringBuffer();
		
		for(int dan=minDan; dan <=maxDan; dan++){
			sb.append("<tr>");
			for(int mul=1; mul<=9; mul++){
				sb.append(String.format("<td>%d * %d = %d</td>", dan, mul, dan*mul));
			}
			sb.append("</tr>");
		}
		
		int start = html.indexOf("@gugudan");
		int end = start+ "@gugudan".length();
		String replace = sb.toString();
	
		html.replace(start, end, replace);
		
		PrintWriter out = resp.getWriter();
		out.print(html.toString());
//		out.close();
	}
}