package kr.or.ddit.mvc;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.mvc.annotation.HandlerInvoker;
import kr.or.ddit.mvc.annotation.HandlerMapper;
import kr.or.ddit.mvc.annotation.URIMappingInfo;

public class FrontController extends HttpServlet {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	private HandlerMapper handlerMapper;
	private HandlerInvoker handlerInvoker;
	private ViewProcessor viewProcessor;
	
	//딱 한번만 호출될수 있는
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String basePackage = config.getInitParameter("basePackage");
		logger.info("{}", basePackage);
		handlerMapper = new HandlerMapper(basePackage);
		handlerInvoker = new HandlerInvoker();
		viewProcessor = new ViewProcessor();
		viewProcessor.setPrefix(config.getInitParameter("prefix"));
		viewProcessor.setSuffix(config.getInitParameter("suffix"));
		
		/*핸들러 부분 지움 ..위
		//마지막으이제 핸들러맵에 객체 생성
		handlerMap = new HashMap<>();
		mappingInfo = config.getInitParameter("mappingInfo");
		ResourceBundle bundle = ResourceBundle.getBundle(mappingInfo);
		Set<String> keySet = bundle.keySet();
		for(String uri : keySet) {
			String qualifiedName = bundle.getString(uri);
			Class<ICommandHandler> handlerClz;
			try {
				handlerClz = (Class<ICommandHandler>) Class.forName(qualifiedName.trim());
				ICommandHandler handler = handlerClz.newInstance();
				
				handlerMap.put(uri.trim(), handler);
				logger.info(" {}에 대한 핸들러 {} 등록", uri, qualifiedName); //적어도 info와 동일하거나 더 낮아야 메세지가 들어옴 {}<메세지아규먼트
				
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
//				System.err.printf("%s에 대한 핸들러 : %s에서 문제발생 %s\n", uri, qualifiedName, e.getMessage());//이것도 로깅프레임웍으로 변경
				logger.error("{}에 대한 핸들러 : {}에서 문제발생 {}\n", uri, qualifiedName, e.getMessage());
				continue;//다음번 반복으로 그냥 넘어가??
			}
		}
		*/
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		URIMappingInfo mappingInfo = handlerMapper.findCommandHandler(req);
		if(mappingInfo != null) {
			String viewName = handlerInvoker.invokeHandler(mappingInfo, req, resp);
			viewProcessor.viewProcess(viewName, req, resp);
		}else {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, req.getRequestURI());
		}
	}
}
