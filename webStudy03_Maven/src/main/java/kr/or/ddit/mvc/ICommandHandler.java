package kr.or.ddit.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * FrontController 뒷단에서 동작하는 각 커맨드를 처리할 커맨드 핸들러의 추상화 
 **/
public interface ICommandHandler {
	/**
	 * 각 커맨드마다 구체적인 작업을 정의한 커맨드 핸들러 메소드
	 * @param req
	 * @param resp
	 * @return 논리적인 View name(prefix와 suffix가 제거된 형태)
	 * @throws IOException
	 * @throws ServletException
	 */
	public String Process(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException;
}
