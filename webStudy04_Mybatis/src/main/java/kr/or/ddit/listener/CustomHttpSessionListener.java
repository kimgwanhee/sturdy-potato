package kr.or.ddit.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomHttpSessionListener implements HttpSessionListener {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		logger.info("{} 세션 생성", se.getSession().getId());
		Integer usercount = (Integer) se.getSession().getServletContext().getAttribute("usercount");
		se.getSession().getServletContext().setAttribute("usercount", usercount+1);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		
	}

}
