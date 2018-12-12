package kr.or.ddit.listener;

import java.util.LinkedHashSet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.vo.MemberVO;

public class CustomServletContextListener implements ServletContextListener {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("{} 어플리케이션 초기화", sce.getServletContext().getContextPath());//어떤 어플리케이션인지 확인 
		sce.getServletContext().setAttribute("usercount", new Integer(0));
		sce.getServletContext().setAttribute("cPath", sce.getServletContext().getContextPath());
		sce.getServletContext().setAttribute("applicationUsers", new LinkedHashSet<MemberVO>());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("{} 어플리케이션 소멸", sce.getServletContext().getContextPath());//어떤 어플리케이션인지 확인 
	}

}
