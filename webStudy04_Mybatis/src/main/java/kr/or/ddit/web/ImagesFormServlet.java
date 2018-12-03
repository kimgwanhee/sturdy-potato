package kr.or.ddit.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.utils.CookieUtil;

public class ImagesFormServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//마임설정 !! 관행적으로 맨 위에! 
		resp.setContentType("text/html;charset=UTF-8");
		
		ServletContext context = req.getServletContext();
		String contentFolder = context.getInitParameter("contentFolder");//11월14일
		
		
		//1.이미지 가져올수 있도록 파일객체 생성
//		File folder = new File("d:/contents");
		File folder = new File(contentFolder);//11월14일
		
		
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
		/*
		BufferedReader br = new BufferedReader(isr);
		
		//3. 템플릿을 관리하기위한 변수가 필요
		StringBuffer html = new StringBuffer();//템플릿으로 부터 읽어들인 모든소스가 있을것
		String temp = null;
		
		//2. 읽어들일때마다 값이 차곡차곡 쌓일것.. 다 읽을때까지 반복해라->3번으로
		while((temp = br.readLine()) != null){
			html.append(temp+"\n");
		}
		*/
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
		
		//5.치환?을 하고 만들어내기 
//		int start = html.indexOf("@img");
//		int end = start+ "@img".length();
		
//		String replace = sb.toString();
		
	
		//4. 무엇을 찾아서 무엇을 바꿀건지?.. start, end,바꿀위치 -> replace 최종적으로 바꾸는것 -> 5번으로
//		html.replace(start, end, replace);
		
		req.setAttribute("optionsAttr", sb.toString());//11.16
		
		//11월16일
		//먼저 클라이언트쪽에 저장되어있는 쿠키를 꺼내온다.
		String imgCookieValue = new CookieUtil(req).getCookieValue("imageCookie");
		StringBuffer imgTags = new StringBuffer();//for문을 벗어나면 모든 img파일이 imgTags안에 들어가게 될것
		if(imgCookieValue != null) {
			//unmarshalling
			ObjectMapper mapper = new ObjectMapper();
//			mapper.readValue(imgCookieValue, String[].class);//마샬링의 반대로 read
			
			String[] imgNames = mapper.readValue(imgCookieValue, String[].class);//이미지 파일명들이 들어오겠종
			String imgPattern = "<img src=\'image.do?selname=%s'/>";
			for(String imgName : imgNames) {
				imgTags.append(String.format(imgPattern, imgName));
			}
		}
		req.setAttribute("imgTags",imgTags);//11.16
		
//		start = html.indexOf("@images");
//		end = start+ "@images".length();
//		html.replace(start, end, imgTags.toString());//for문을 벗어나면 모든 img파일이 imgTags안에 들어가게 될것
		
		String view = "/WEB-INF/views/image.jsp";	//아래 빨간줄 이제 view가 피료
		//이제 주석처리하고 dispatch를 해준다 11-16 마지막
		RequestDispatcher rd = req.getRequestDispatcher(view);
		rd.include(req, resp);
		
		//7. 응답데이터로 내보내야하므로..resp한테 가져오기
//		PrintWriter out = resp.getWriter();
//		out.println(html);//최종적으로 완성된 소스는 html이 가지고있으므로
//		out.close();//닫기
		
	}
}
