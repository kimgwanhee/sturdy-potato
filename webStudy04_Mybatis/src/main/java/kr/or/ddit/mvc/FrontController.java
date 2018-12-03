package kr.or.ddit.mvc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.member.controller.MemberListController;
import kr.or.ddit.member.controller.MemberUpdateController;
import kr.or.ddit.member.controller.MemberViewController;
import kr.or.ddit.member.controller.MyPageController;

public class FrontController extends HttpServlet {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	private Map<String, ICommandHandler> handlerMap;	
	private String mappingInfo;
	
	//딱 한번만 호출될수 있는
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		//마지막으이제 핸들러맵에 객체 생성
		handlerMap = new HashMap<>();
		mappingInfo = config.getInitParameter("mappingInfo");
		ResourceBundle bundle = ResourceBundle.getBundle(mappingInfo);
		Set<String> keySet = bundle.keySet();
		for(String uri : keySet) {
			String qualifiedName = bundle.getString(uri);
			Class<ICommandHandler> handlerClz;
			try {
				handlerClz = (Class<ICommandHandler>) Class.forName(qualifiedName.trim());
				ICommandHandler handler = handlerClz.newInstance();
				
				
				handlerMap.put(uri.trim(), handler);
				logger.info(" {}에 대한 핸들러 {} 등록", uri, qualifiedName); //적어도 info와 동일하거나 더 낮아야 메세지가 들어옴 {}<메세지아규먼트
				
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
//				System.err.printf("%s에 대한 핸들러 : %s에서 문제발생 %s\n", uri, qualifiedName, e.getMessage());//이것도 로깅프레임웍으로 변경
				logger.error("{}에 대한 핸들러 : {}에서 문제발생 {}\n", uri, qualifiedName, e.getMessage());
				continue;//다음번 반복으로 그냥 넘어가??
			}
		}
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		1. 요청 매핑 설정 - web.xml가서 끝
//		2. 요청 분석(주소, 파라미터, 메소드, 헤더...)-주소 분석하기 위해서 주소 꺼내는데 req안에있다.
		req.setCharacterEncoding("UTF-8");
		String uri = req.getRequestURI();
//		/webStudy03_Maven/member/memberList.do
		int cpLength = req.getContextPath().length();
		
		//앞에 짤리고 /member/memberList.do;jsessionid = asdfasf하고 있을수도..
		uri = uri.substring(cpLength).split(";")[0];
		System.out.println(uri);///member/memberList.do필요한 주소만 짤림
		ICommandHandler handler = handlerMap.get(uri);
		
		
		/*
	.	ICommandHandler handler = null;//이제 부모 //다형성
		if("/member/memberList.do".equals(uri)) {
			 handler = new MemberListController();
		}else if("/member/memberView.do".equals(uri)){
			 handler = new MemberViewController();
		}else if("/member/mypage.do".equals(uri)) {
			 handler = new MyPageController();
		}else if("/member/memberUpdate.do".equals(uri)) {
			 handler = new MemberUpdateController();
		}
		*/
		
		if(handler == null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND,"해당 서비스는 제공하지 않습니다.");
			return;
		}
		
//		3. 의존관계 형성(우리가 x 계속 달라지므로 뒷단에 커맨더핸들러에서 해결)
//		4. 로직 선택(우리가 x)
//		5. 모델 확보(우리가 x)
//		6. 뷰 선택(우리가 x)
//		7. 모델 공유(우리가 x)
		//여기선 7. 뷰선택.
//		8. 뷰로 이동
		
		String view = handler.Process(req,resp);	
		String prefix = "/WEB-INF/views/";
		String suffix = ".jsp";
		if(view!=null) {
			boolean redirect = view.startsWith("redirect:");
			if(redirect) {
				view = view.substring("redirect:".length());//이 이후의 경로를짤라서 다시 view에 넣기
				resp.sendRedirect(req.getContextPath()+view);//앞 커멘더에서 어떤 뷰를 선택했는지 받아와야하므로
			}else {
				RequestDispatcher rd = req.getRequestDispatcher(prefix+view+suffix);
				rd.forward(req, resp);
			}
		}else {
			if(!resp.isCommitted()) {//isCommitted응답이 이미 결정됬다라면
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "커맨드 핸들러에서 뷰가 선택되지 않았습니다.");
			}
		}
	}
}
