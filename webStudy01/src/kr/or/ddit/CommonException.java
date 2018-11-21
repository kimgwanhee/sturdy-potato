package kr.or.ddit;
/**
 * 
 * @author KGH
 * @since 2018. 11. 21.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2018. 11. 21.      KGH       어플리케이션에서 전역적으로 활용할 unchecked exception
 * Copyright (c) 2018 by DDIT All right reserved
 * </pre>
 */
public class CommonException extends RuntimeException {

	public CommonException() {
		super();
	}

	public CommonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CommonException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommonException(String message) {
		super(message);
	}

	public CommonException(Throwable cause) {
		super(cause);
	}

}
