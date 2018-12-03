package kr.or.ddit.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirstFilter implements Filter {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("{} 필터 초기화", getClass().getSimpleName());
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.info("요청 필터링");
		chain.doFilter(request, response); // --> 다음 필터나 최종 자원쪽으로 제어 이동.
		logger.info("응답 필터링");
	}

	@Override
	public void destroy() {
		logger.info("{} 필터 소멸", getClass().getSimpleName());
	}

}
