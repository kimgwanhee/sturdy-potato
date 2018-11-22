package kr.or.ddit.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

import org.apache.commons.lang3.StringUtils;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import jdk.nashorn.internal.ir.RuntimeNode.Request;
import kr.or.ddit.vo.AlbasengVO;

@WebServlet(value="/albamon", loadOnStartup = 1)
public class SimpleFormProcessServlet extends HttpServlet {
	//11월14일 -8 stataic 전부다 빼버림.. 어플리케이션 스코프를 이용해서 공유할려고.
	////**11월13일
	public  Map<String, String> gradeMap;
	public  Map<String, String> licenseMap;
	
	 {//static코드블럭-SimpleFormProcessServlet클래스가 메모리에 적재될때 실행됨
		gradeMap = new HashMap<String, String>(); //맵은 순서가 있는 집합이 x
		licenseMap = new LinkedHashMap<>();	//차이점..	
		
		gradeMap.put("G001", "고졸");
		gradeMap.put("G002", "대졸");
		gradeMap.put("G003", "석사");
		gradeMap.put("G004", "박사");
		
		licenseMap.put("L001", "정보처리산업기사");
		licenseMap.put("L002", "정보처리기사");
		licenseMap.put("L003", "정보보안산업기사");
		licenseMap.put("L004", "정보보안기사");
		licenseMap.put("L005", "SQLD");
		licenseMap.put("L006", "SQLP");
	}
	public  Map<String, AlbasengVO> albasengs = new LinkedHashMap<>();
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);//제거하면안됨
		getServletContext().setAttribute("gradeMap", gradeMap);
		getServletContext().setAttribute("licenseMap", licenseMap);
		getServletContext().setAttribute("albasengs", albasengs);//이제 모든어플리케이션에서 쓸수있게됨
		System.out.println(getClass().getSimpleName()+"초기화");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");//바디가 있는경우에만 동작-> get x-> 서버(server.xml)에가서 미리 처리
		
		/*
		VO객체 생성. -> 파라미터 셋팅(할당)
		VO를 대상으로 검증 
		(이름, 주소, 전화번호 필수데이터 검증)
		1)통과
			code 생성("alba_001")
			map.put(code, vo)
			이동(/05/albaList.jsp, 요청 처리 완료 후 이동)
		2)불통(누락)
			이동(/01/simpleForm.jsp, 기존 입력데이터를 전달한채 이동)
		
		 */
		
		AlbasengVO av = new AlbasengVO();
		
		av.setName(req.getParameter("name"));
		av.setAddress(req.getParameter("address"));
		av.setCareer(req.getParameter("career"));
		av.setGender(req.getParameter("gender"));
		av.setGrade(req.getParameter("grade"));
		av.setLicense(req.getParameterValues("license"));
		av.setTel(req.getParameter("tel"));
		
		// 리다이렉트로 이동할땐 버리고 디스패치일땐 가져가자
		// 페이지, 어플리케이션(항상가져가므로) 탈락
		// 리다이렉트 신규등록이 끝났을땐 안가져가도 됨 -> request
		req.setAttribute("albaVO", av); //중간에 응답데이터가 나갓을때 없어짐그럼.
		
		boolean valid = true;
		
		if(req.getParameter("age")!=null && (req.getParameter("age")).matches("\\d{1,2}")) {//한자리부터 두자리 .. 
			av.setAge(Integer.valueOf((req.getParameter("age"))));
		}
		
		Map<String, String> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		
		if(StringUtils.isBlank(av.getName()) ) {
			valid = false;
			errors.put("name", "이름누락");//알바생 vo가 가지고 있는 프로퍼티명을 사용
		}
		if(StringUtils.isBlank(av.getTel()) ) {
			valid = false;
			errors.put("tel", "연락처 누락");
		}
		if(StringUtils.isBlank(av.getAddress()) ) {
			valid = false;
			errors.put("address", "주소 누락");
		}
		boolean redirect = false;
		String goPage = null;
		
		if(valid) {
			av.setCode(String.format("alba_%03d", albasengs.size()+1));//숫자를 세자리 수로 넣고 한자리일땐 앞에 두글자 0으로 채우겠다
			albasengs.put(av.getCode(), av);
			goPage = "/05/albaList.jsp";//리다이렉트 이미 등록이 끝낫을때 등록된 리스트를 보여줄것-> 입력된데이타 가져갈필요없음->
			redirect = true;
		}else {
			goPage = "/01/simpleForm.jsp";//디스패치	기존에 데이타가있으면그걸살려서보여줘야함 -> alvaVO안에있고 그걸 스코프안에 담았음->
		}
		
		if(redirect) {
			resp.sendRedirect(req.getContentType() + goPage);
		}else {
			RequestDispatcher rd = req.getRequestDispatcher(goPage);
			rd.forward(req, resp);
		}
		
		/*
			RequestDispatcher rd = req.getRequestDispatcher("/01/simpleForm.jsp");
			rd.forward(req, resp);
		}else {
			av.setCode(String.valueOf("alba_00"+albasengs.size()+1));
			albasengs.put(av.getCode(), av);
			System.out.println("뱌뱌뱌뱌");
			resp.sendRedirect(req.getContextPath()+"/05/albaList.jsp");
		}
		
		*/
		
		
//		System.out.printf("alba_%03d", albasengs.size()+1);
		
		/*=================================================
//		System.out.printf("%s : %s\n", "name", name);
//		System.out.printf("%s : %s\n", "license", Arrays.toString(license));
		Enumeration<String> names = req.getParameterNames();
		while (names.hasMoreElements()) {//커서
			String name = (String) names.nextElement();
			String[] values = req.getParameterValues(name);
			System.out.printf("%s : %s\n", name, Arrays.toString(values));
		}
		
		Map<String, String[]> parameterMap = req.getParameterMap();
		for(Entry<String, String[]> entry : parameterMap.entrySet()) {//임시블럭변수의 타입 : parameterMap.두개의 데이타를 묶어서 entry
			String name = entry.getKey();
			String[] values = entry.getValue();
			System.out.printf("%s : %s\n", name, Arrays.toString(values));
			
		}

		===================================================*/
	}
}