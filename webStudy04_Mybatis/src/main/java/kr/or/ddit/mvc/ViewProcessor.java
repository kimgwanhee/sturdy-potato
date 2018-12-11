package kr.or.ddit.mvc;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewProcessor {
	private String prefix;
	private String suffix;
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	/**
	 * 논리적이 view name을 받아서 해당 view layer로 forwarding 이나 redirecting을 하기위한 메소드
	 * @param viewName
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void viewProcess(String view, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		if(view!=null) {
			boolean redirect = view.startsWith("redirect:");
			if(redirect) {
				view = view.substring("redirect:".length());//이 이후의 경로를짤라서 다시 view에 넣기
				resp.sendRedirect(req.getContextPath()+view);//앞 커멘더에서 어떤 뷰를 선택했는지 받아와야하므로
			}else {
				RequestDispatcher rd = req.getRequestDispatcher(prefix+view+suffix); //9번 forward
				rd.forward(req, resp);
			}
		}else {
			if(!resp.isCommitted()) {//isCommitted응답이 이미 결정됬다라면
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "커맨드 핸들러에서 뷰가 선택되지 않았습니다.");
			}
		}
	}
}
