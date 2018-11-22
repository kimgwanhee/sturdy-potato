package kr.or.ddit.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.concurrent.locks.Condition;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.SynthSeparatorUI;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.utils.CookieUtil;
import kr.or.ddit.utils.CookieUtil.TextType;

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
		
		//2.쿠키가 그전께 없어져서 만들음 . -> 어떤방식으로 저장할건가를 결정 
		//쿠키값 : A, B , 로 구분하자 그리고 그전에 NULL이 아닌지도 구분 
		String imgCookieValue = new CookieUtil(req).getCookieValue("imageCookie");
		String[] cookieValues = null;
		
		// marshalling 현재 우리 객체는 이미지 모든정보를 가지고 있는 cookieValues(마샬링의 대상이 되는 자바객체)
		ObjectMapper mapper = new ObjectMapper();
		
		if(StringUtils.isBlank(imgCookieValue)) {//여기에 저장되면 이미지를 처음 본것-> 저장되있던 쿠키가 없는것 
			cookieValues = new String[] {imgFile.getName()};
		}else {//그전에 이미지를 몇번 본것 -> 저장되있던 쿠키
			
			String[] cValues = mapper.readValue(imgCookieValue, String[].class);
			cookieValues = new String[cValues.length+1];//아직은 비어있으니 아래에서 복!사!해서  배열에 넣어주어야함 안에 값들을 모두 카피 떠야하니 딥카피?
			System.arraycopy(cValues, 0, cookieValues, 0, cValues.length);
			cookieValues[cookieValues.length-1] = imgFile.getName();//?..
		}
		//마샬링때문에 잠시 주석 11.16
//		imgCookieValue = Arrays.toString(cookieValues);	 
//		imgCookieValue =  imgCookieValue.replaceAll("[\\[\\]\\s]", "");//우린 요 []을 찾아서 지워야함 (화이트스페이스)
		imgCookieValue = mapper.writeValueAsString(cookieValues);
		
		System.out.println(imgCookieValue);
		
		//11-16 1.쿠키저장하기 
		Cookie imgCookie = CookieUtil().createCookie("imageCookie",imgCookieValue , req.getContextPath(), TextType.PATH, 60*60*24*3);
		//위 value가 어떤게 들어올지 모르니 인코딩을 해야함
		//만료시간을 기록해야 살아있음,  req.getContextPath(), TextType.PATH<-를 경로로 사용하겠다라고 기입 
		
		resp.addCookie(imgCookie);//이걸 내보내기.
		
		ServletContext context = req.getServletContext();//어떤 파일이 넘어올지 모르므로 
		
		//쿠키가져오는방법
		Cookie[] cookies = req.getCookies();
		for(Cookie cookie2 : cookies){
			if(cookie2.getName().contains(".")) {
				
			}
		}
		
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

	private CookieUtil CookieUtil() {
		// TODO Auto-generated method stub
		return null;
	}

}
