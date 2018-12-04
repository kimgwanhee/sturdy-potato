package kr.or.ddit.filter.auth;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.ddit.vo.MemberVO;
/**
 * @author KGH
 * @since 2018. 12. 4.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2018. 12. 4.      KGH       보호자원에 접근하고 있는 유저에 부여된 권한과 자원에 설정된 권한 매칭 여부 확인.
 * Copyright (c) 2018 by DDIT All right reserved
 * </pre>
 */

public class AuthorizationFilter implements Filter {
	
	//권한이 없다면 401과 403
	//반드시 AuthenticationFilter다음에서 동작할수 있도록 등록
	
	//보호자명 : 권한들
	private Map<String, String[]> securedResources;
		
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {//와스가 내부적으로 호출하는 콜백
		securedResources = new LinkedHashMap<>();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		securedResources = 
				(Map<String, String[]>) request.getServletContext().getAttribute(AuthenticationFilter.SECUREDRESOURCEATTR);
		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();
		int cpLength = req.getContextPath().length();
		uri = uri.substring(cpLength).split(";")[0];
		boolean pass = true;
		
		if(securedResources.containsKey(uri)) {//보호되고있는자원이라는것
			MemberVO authMember = (MemberVO) req.getSession().getAttribute("authMember");
			String mem_auth = authMember.getMem_auth();
			String[] resAuthes = securedResources.get(uri);//자원에 설정된 권한
			
			if(Arrays.binarySearch(resAuthes, mem_auth) < 0) {//resAuthes에 mem_auth가 포함되어있는지..
				pass = false;
			}
		}
		
		if(pass) {//관리자이면
			chain.doFilter(request, response);
		}else {
			HttpServletResponse resp = (HttpServletResponse) response;
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);//허가되지않은
		}
	}

	@Override
	public void destroy() {
		
		
	}
}
