package kr.or.ddit.filter;

import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.filter.wrapper.FileUploadRequestWrapper;

public class FileUploadCheckFilter implements Filter {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("{} 필터 초기화");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//request에서 contentType을 구해온다.
		String contentType = request.getContentType();
		
		//contentType의 시작이 뭔지 판별 (form태그에 enctype="Multipart/form-data"이라고 설정하면 contentType 제일 시작이 multipart로 시작된다.)
		if(contentType!=null && contentType.startsWith("multipart/")) {
			
			//servletRequest는 HttpServletRequest를 포괄하고 있으므로 request값을 downcast해준다.
			HttpServletRequest req = (HttpServletRequest)request;
			
			//파일 임계크기 지정
			int sizeThreshold = 10240;
			
			//임시 저장소 지정
			File repository = new File("d:/temp");
			
			//따로 만들어논 request wrapper 클래스 호출해서 wrapper객체를 반환받는다.
			FileUploadRequestWrapper wrapper = new FileUploadRequestWrapper(req,sizeThreshold,repository);
			
			logger.info("{}에서 multipart request가 {}로 변경됨", 
					getClass().getSimpleName(),wrapper.getClass().getSimpleName());
			//원래가지고있던 request를 변형시킨 wrapper를 전송
			chain.doFilter(wrapper, response);// 기점으로 전은 요청 후는 응답으로 나뉨
		}else {
			//content type이 multipart로 시작하지 않으면 원래그대로의 요청과 응답데이터를 전송
			chain.doFilter(request, response);
		}
		
		
		
		
	}

	@Override
	public void destroy() {
		logger.info("{} 필터 소멸");
	}

}
