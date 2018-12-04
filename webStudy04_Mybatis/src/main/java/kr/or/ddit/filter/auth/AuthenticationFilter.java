package kr.or.ddit.filter.auth;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author KGH
 * @since 2018. 12. 4.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2018. 12. 4.      KGH      보호가 필요한 자원에 대해 접근을 시도하는 유저가 인증이 되어있는지 여부를 확인.
 * Copyright (c) 2018 by DDIT All right reserved
 * </pre>
 */
public class AuthenticationFilter implements Filter {
	
	private Map<String, String[]> securedResources; //모든 자원정보와, 권한정보가 여러개인것도 있었으므롱
	//한번읽어서 한번 로딩했다면 그대로 유지 init- 여러번 할 필요 x(계속 호출 x)
	private String securedResourceInfo;
	public static final String SECUREDRESOURCEATTR = "securedResources";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		securedResources = new LinkedHashMap<>();
		filterConfig.getServletContext().setAttribute(SECUREDRESOURCEATTR, securedResources);//어느영역에서 가져와서 싱글톤으로 들어감
		securedResourceInfo = filterConfig.getInitParameter("securedResourcesInfo");
		ResourceBundle bundle = ResourceBundle.getBundle(securedResourceInfo);
		//위 baseName은 클래스패스, 확장자를 제외한 나머지 경로 들어가야함 , 로케일에 대한정보는 포함x
		Enumeration<String> keys = bundle.getKeys();//이게 이제 맵의 키로 사용되야함
		while(keys.hasMoreElements()) {//키가 다음에 있을때까지..
			String uri = keys.nextElement();
			String valueString =bundle.getString(uri);
			String[] auth = valueString.split(",");
			Arrays.sort(auth);//검색할때 쉬우도록 자동 정렬되도록
			//이제 맵에넣기
			securedResources.put(uri.trim(), auth);
		}
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
//		2. 요청 분석(주소, 파라미터, 메소드, 헤더...)-주소 분석하기 위해서 주소 꺼내는데 req안에있다.
		String uri = req.getRequestURI();
		int cpLength = req.getContextPath().length();
		uri = uri.substring(cpLength).split(";")[0];
		boolean pass = true;
		//맵을 하나 만들어서 관리하자 ~
		if(securedResources.containsKey(uri)) {//vo가 필요한 자원에접근 -> 로그인여부 확인 
			HttpSession session = req.getSession();
			if(session.getAttribute("authMember")==null) {//vo가 필요한데 로그인은 x -> 문제
				pass = false;
			}
		}
		
		if(pass) {
			chain.doFilter(request, response); 
		}else {//다시 로그인페이지로 이동
			String goPage = "/login/loginForm.jsp";
//			response //지금 할아버지로 되어있음위에
			HttpServletResponse resp = (HttpServletResponse)response;
			resp.sendRedirect(req.getContextPath()+goPage);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
