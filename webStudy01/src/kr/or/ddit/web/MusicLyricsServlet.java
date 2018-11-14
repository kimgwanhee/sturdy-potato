package kr.or.ddit.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/song")
public class MusicLyricsServlet extends HttpServlet{
	//  **11월12일
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String song = req.getParameter("song");//jsp에서 data의 : song
		int status = 0;	//상태하나 만듬
		String message = null;
		
		if(song == null || song.trim().length()==0) {// 비정상적상황
			status = HttpServletResponse.SC_BAD_REQUEST;
			message = "가사파일을 선택해주세요.";
		}
//		File folder = new File("d:/contents"); //이 컨텐츠타입을 사용하기 위해서 가져옴jsp에서
		
		File folder = (File)getServletContext().getAttribute("contentFolder"); //11월14일 다 바꿔주기
		File songFile = new File(folder, song);
		if(!songFile.exists()) {//이 파일이 존재하지 않는다면..
			status = HttpServletResponse.SC_NOT_FOUND;
			message = "해당 곡은 가사가 없습니다.";
		}
		if(status != 0) {//상태변수 사용
			resp.sendError(status, message);
			return;
		}
		//이제 html코드 만들기 -> 먼저 mimetype
		resp.setContentType("text/html;charset=UTF-8");
//		java1.7 : try with resource 구문
		
		try (
//				Closable 객체를 할당
				//현재 우리가 읽으려는건 text파일-> html파일로 변경 근데 한줄씩 읽어야하므로 bufferedreader의 reader.readLine()필요
				FileInputStream fis = new FileInputStream(songFile);//이건 바이트로밖에 못읽음 그러므로 젠더필요
				
				//바이트와 바이트를 묶을때 어떤인코딩으로 묶을건지 선택을 해야하는데..->그래도깨짐->다른인코딩방식으로 문서가 저장이됬을것이다
				InputStreamReader isr = new InputStreamReader(fis, "MS949");//얘로 읽으려면 한글자씩밖에 못읽음 그래서 bufferedreader필요
				BufferedReader reader = new BufferedReader(isr);
				PrintWriter out = resp.getWriter();
				){
			
			String temp = null;
			StringBuffer html=new StringBuffer();//한줄씩 읽을때마다 저장할 곳 필요
			while((temp=reader.readLine())!=null) {
				html.append("<p>"+temp+"</p>");
				System.out.println(temp);//인코딩잘못됨 
			}
			//이제 응답을 내보내면 출력 스트림이 필요
			out.println(html);
		}
		/*1.7부터..쓰지않아도됨
		finally {//catch 위에서 이미 IOextion을 줘서 컴파일에러가 안생긴것 
			if(reader!=null) reader.close(); //nullpointextion뜰수있기때문
			if(reader!=null) out.close(); //nullpointextion뜰수있기때문
		}
		//망한코드..ㅠ => 	reader.close(); , out.close(); 
		//중간에 에러가 나면 reader라는게 close가 안됨.. 무조건 맨 마지막엔 close가 되도록 해야함 try구문안에 넣어주기
	*/
	}
}
