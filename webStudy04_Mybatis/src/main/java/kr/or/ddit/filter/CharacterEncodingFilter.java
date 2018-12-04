package kr.or.ddit.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Filter구현
 * 1. javax.servlet.Filter 구현체 작성
 * 2. lifecycle 메소드 : init, destroy
 	요청 filtering 메소드 : doFilter - ** chain.doFilter 메소드 호출 전에 요청 필터링 
 									** chain.doFilter 메소드 호출 후에 요청 필터링
  													요청이 필터링 되는 순서와 응답이 필터링 되는 순서는 역순이됨.
 * 3. W.A.S에 해당 필터를 등록( web.xml -> filter ) : WAS에 의해 FilterChain이 형성됨.
 *                                            3.0 @webFilter로 해도됨 -> 하지만 반드시 순서를 결정(가능하면 사용 X)
 * 4. 필터링 할 수 있는 요청과의 매핑 설정(web.xml -> filter-mapping)
 */
public class CharacterEncodingFilter implements Filter {
	private String encoding = "UTF-8";
	
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//기존 인코딩은 "UTF-8" 이지만 초기화파라미터를 통해서 다른 인코딩이 만들어지면 그게 적용이 될수도있다
		encoding = filterConfig.getInitParameter("encoding");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding(encoding);
		//여기서 끝나버리면 다음필터로 전달안됨 doFilter한번은 호출되야함
		chain.doFilter(request, response);//CharacterEncoding이 결정되버림.
		
		
	}

	@Override
	public void destroy() {

	}

}
