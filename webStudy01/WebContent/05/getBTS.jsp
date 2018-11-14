<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 1. 파라미터 확보 -->
<!-- 2. 검증(필수 데이터 검증, 유효데이터 검증) -->
<!-- 3. 불통 -->
<!-- 	1) 필수데이터 누락 -클라이언트 잘못 : 400(bad request) -->
<!-- 	2) 우리가 관리하지 않는 멤버를 요구한 경우 : 404(not found) -->
<!-- 4. 통과 -->
<!-- 	이동(맵에 있는 개인페이지, 클라이언트가 멤버 개인페이지의 주소를 모르도록.) -->
<!-- 	이동(맵에 있는 개인페이지, getBTS에서 원본 요청을 모두 처리했을 경우, UI페이지로 이동할 때.) -->
<%!
	Map<String, String[]> singerMap = new LinkedHashMap<>();
{
	singerMap.put("B001", new String[]{"뷔", "/WEB-INF/bts/v.jsp"});
	singerMap.put("B002", new String[]{"지민", "/WEB-INF/bts/jimin.jsp"});
	singerMap.put("B003", new String[]{"진", "/WEB-INF/bts/jin.jsp"});
	singerMap.put("B004", new String[]{"정국", "/WEB-INF/bts/jungguk.jsp"});
}
%>

<%
	RequestDispatcher rd = null;
	String member = request.getParameter("member");
	int statusCode = 0;

	if(member==null || member.trim().length()==0){
// 		response.sendError(400);
		statusCode = HttpServletResponse.SC_BAD_REQUEST;
	}else if(!singerMap.containsKey(member)){//그런 키가 없다면
		statusCode = HttpServletResponse.SC_NOT_FOUND;
		/*/////
		for(Entry<String, String[]> entry : singerMap.entrySet()){
			if(member.equals(entry.getKey())){
				rd= request.getRequestDispatcher(entry.getValue()[1]);
				rd.forward(request, response);
// 				response.sendRedirect(request.getContextPath()+entry.getValue()[1]);

		*/
	}
	if(statusCode != 0){
		response.sendError(statusCode);
		return;
	}
	String[] value = singerMap.get(member);//이미 키가 포함되있는것을 위에서 검증했기때문에 널체크 ㄴㄴ
	String gopage = value[1];
	
	//1번
// 	request.getRequestDispatcher(gopage);
// 	rd.forward(request, response);

	response.sendRedirect(request.getContextPath()+gopage);//클라이언트사이드방식
	
%>
