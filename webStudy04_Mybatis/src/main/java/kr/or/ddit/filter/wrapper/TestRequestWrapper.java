package kr.or.ddit.filter.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class TestRequestWrapper extends HttpServletRequestWrapper {

	public TestRequestWrapper(HttpServletRequest request) {
		super(request);
		
		
	}
	
	@Override
	public String getParameter(String name) {
		if("who".equals(name)) {
			return "b001";
		}
		return super.getParameter(name);
	}
		
	
}
