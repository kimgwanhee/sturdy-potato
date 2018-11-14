package kr.or.ddit.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//1. form태그에서 발생하는 메소드를 처리하기위해.. 
@WebServlet(value = "/image.do", loadOnStartup=4)
public class ImageServiceServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 클라이언트가 어떤이미지를 선택해서 보냈는지 먼저.. 요청파라미터확보: 파라미터명(image)
		
		//2. 파라미터명은 입력태그의 name속성값에 의해 결정된다.
		String img = req.getParameter("selname");
		
		
		
		//3. 값이 정상으로 넘어왔는지 먼저 확인
		if(img == null|| img.trim().length()==0) {
			//오류나믄..
			resp.sendError(400);
			return; //제어를 멈추려면 return
		}
		
		// 이미지 스트리밍..서비스를 제공해야함
		
		/* 4. ***서버사이드 데이터검증을위한코드
		 File folder = new File("d:/contents");
		 File imgFile = new File(folder, imgName);
		 */
		
		resp.setContentType("image/jpeg");
		File imgFile = new File("d:/contents/"+img); 
		 
		if(!imgFile.exists()){
			resp.sendError(404);
			return;
		}
		 
		ServletContext context = req.getServletContext();
		resp.setContentType(context.getMimeType(img));
		
		
		//5. 
		int pointer = -1;
		byte[] buffer = new byte[1024];
		
		FileInputStream fis = new FileInputStream(imgFile);
		OutputStream out = resp.getOutputStream();
		while((pointer = fis.read(buffer))!= -1) {	//-1 : EOF문자
//			out.write(buffer);//사실 이코드는 위험..
							//안전하게 복사를 뜨려면..
			out.write(buffer, 0, pointer);
		}
		//읽어들이기위한 스트림 닫기
		fis.close();
		out.close();
		//현재우리는 이미지를 서비스하고있으므로 마임설정 ㄱㄱ
		//출력스트림(OutputStream out = resp.getOutputStream();)을 개방하기 전에 ! 
	}

}
