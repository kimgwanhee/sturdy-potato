package kr.or.ddit.web;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.web.model2.FileList;

//	11월15일 -7

//요청은 이녀석으로 들어오고 응답은 jsp로 나가야함->등록해야함
@WebServlet("/fileBrowser.do")
public class ServerFileBrowser extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		  //파일 목록을 보여주고 
	      //그게 디렉토리라면 그폴더안에있는 목록보여주고
	      //..링크 추가하면 다시 상위폴더로
	      //파라미터로 넘겨줌
	      String path= req.getParameter("path");//클라이언트 요청시작할때 path라는 파라미터를가져옴
	      String name= req.getParameter("name");//이하동문
	      if(path==null) {//만약 path가 널이면
	         
	         path=getServletContext().getRealPath("/");// path에 서블릿컨텍스트의 경로로 세팅해줌
	         
	      }else {
	         if(name.contains(".")) {//만약 네임에 .이들어가면 폴더가 아니므로 열리면 안되므로 기존의 경로를 재활용해줌 
	            File file=new File(path);//파일을 상위 폴더로 만들어주고 시작함
	            path=file.getParentFile().getAbsolutePath();
	         }
	      }
	      FileList fileList=new FileList();//파일리스트 객체생성 그 폴더안에 있는 모든 파일을 담아오기위해서
	      List<File> filefile=fileList.getFileList(path);//겟파일리스트에 경로만 지정해줘서 메소드 호출/반환값을 리스트<파일임>
	      if(filefile!=null) {
	         
	         req.setAttribute("file", filefile);
	      }else {
	         
	      }
	      String view="/WEB-INF/views/fileBrowser.jsp";
	      RequestDispatcher rd= req.getRequestDispatcher(view);
	      rd.forward(req, resp);
	   }
	}
