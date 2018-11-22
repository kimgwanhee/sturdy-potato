package kr.or.ddit.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.web.model2.SampleModelGenerator;

@WebServlet("/model2Sample.do")
public class Model2SampleController extends HttpServlet{
	//11월15일-1
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. 요청 분석
		// 2. contents를 생성해야함. 내가하면 책임이 한곳에 몰아짐 그러므로 객체를만들어 분산  - SampleModelGenerator.java
		//	(의존 객체 생성)
		SampleModelGenerator generator = new SampleModelGenerator();
		// 3. generator(로직메소드)를 호출 ! 모델데이터를 생성하기위한 로직이 들어있으니깐
		String model = generator.generate();//한마디로 샌드위치를 가지고있음 그런데 내가 먹을게아님 클라이언트로 내보내야함
		// 4. 데이터 공유 (6번하고 젤 마지막에했음)
		req.setAttribute("model", model);
		
		// 5. 뷰레이어를 선택 
		String view = "/WEB-INF/views/model2SampleView.jsp";//현재 우리가 사용할 뷰 /WebContent는 임시폴더므로 지우기
		// 6. 이동
		RequestDispatcher rd = req.getRequestDispatcher(view);
		//서버 안에서 이동하므로 서버사이드 방식 - contextpath 경로 지워야함 /webStudy01
		rd.forward(req, resp);
	}
}
