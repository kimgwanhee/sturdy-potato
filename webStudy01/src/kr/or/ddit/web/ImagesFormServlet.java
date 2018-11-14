package kr.or.ddit.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImagesFormServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//마임설정 !! 관행적으로 맨 위에! 
		resp.setContentType("text/html;charset=UTF-8");
		
		ServletContext context = req.getServletContext();
		String contentFolder = context.getInitParameter("contentFolder");//11월14일
		
		
		//1.이미지 가져올수 있도록 파일객체 생성
//		File folder = new File("d:/contents");
		File folder = new File("contentFolder");//11월14일
		
		
		//FilenameFilter인터페이스므로 익명객체생성
		String[] filenames = folder.list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				String mime = context.getMimeType(name);
				return mime.startsWith("image/");
			}
		});
		////////////////////////////////문제//
		
		
		//action 속성의 값은 context/imageService, method="get"
		
		//1. 템플릿파일 먼저 읽기 -> calsspath 안에있음.. 
		//1-1. 경로를 정확하게 일치하게.. ImagesFormServlet과 image.205는 현재 같은위치에있음!
		// 그리고나서 InputStream로 받기
		//중간에 byteStream에서 InputStream으로 변환해야함 -> InputStreamReader사용
		InputStream in = this.getClass().getResourceAsStream("image.html");
		//특수문자를 사용할수도..UTF-8로 인코딩해주기
		InputStreamReader isr = new InputStreamReader(in, "UTF-8");
		//속도가 떨어지니까 내부적으로 Buffer를 쓰자 -> BufferedReader사용
		BufferedReader br = new BufferedReader(isr);
		
		//3. 템플릿을 관리하기위한 변수가 필요
		StringBuffer html = new StringBuffer();
		String temp = null;
		
		//2. 읽어들일때마다 값이 차곡차곡 쌓일것.. 다 읽을때까지 반복해라->3번으로
		while((temp = br.readLine()) != null){
			html.append(temp+"\n");
		}
		//끝나면 html 안에 템플릿이 들어있음
		
		//6. 
		/*
		StringBuffer options = new StringBuffer();
		String pattern = "<option>%s</option>\n";
		for( String name : filenames){ 
			option.append(String.format(pattern, filenames[i]));
		}
		*/
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < filenames.length; i++) {
			sb.append("<option>"+filenames[i]+"</option>");
		}
		
		//5.
		int start = html.indexOf("@img");
		int end = start+ "@img".length();
		
		String replace = sb.toString();
	
		//4. 무엇을 찾아서 무엇을 바꿀건지?.. start, end,바꿀위치 -> replace 최종적으로 바꾸는것 -> 5번으로
		html.replace(start, end, replace);
		
		//7. 응답데이터로 내보내야하므로..resp한테 가져오기
		PrintWriter out = resp.getWriter();
		out.print(html.toString());//최종적으로 완성된 소스는 html이 가지고있으므로
		out.close();//닫기
		
	}
}
