<%@page import="java.io.InputStream"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.security.Principal"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>06/applicationDesc.jsp</title>
</head>
<body>
<h4>ServletContext</h4>
<pre>
	11월14일 -4
	<%= application.hashCode() %>
	<a href="<%=request.getContextPath() %>/06/implicitObject.jsp">implicitObject.jsp로 이동</a>
	<a href="<%=request.getContextPath() %>/desc">DescriptionServlet으로 가기</a>
	: 컨텍스트와 해당 컨텍스트를 운영(관리)중인 서버에 대한 정보를 가진 객체.
	
	1. 서버에 대한 정보 획득
		<%=application == getServletContext() %> //같은객체다 확인
		<%=application.getServerInfo() %>	//서버에 대한 정보
		<%=application.getMajorVersion() %>.<%=application.getMinorVersion() %> //메이저버전.마이너버전 버전업이될때마다// 새로운 버전이 나오는데 현재서버버전확인할때(api버전확인할때)
		<%=application.getMimeType("test.hwp") %>//이 파일의 확장자를 가지고 마임타입을 확인
	2. 로그 기록(logging) - 클라이언트를 위한 메시지가 아님 그래서 브라우저엔 안보인다 //콘솔에보임
		<%
			application.log("명시적으로 기록한 로그 메시지");
		%>
	3. 컨텍스트(어플리케이션) 파라미터(어플리케이션의 초기화 파라미터) 획득
		<%=application.getInitParameter("param1") %>
		<%
			Enumeration<String> names = application.getInitParameterNames();
			while(names.hasMoreElements()){
				out.println((names.nextElement()));
			}
		%>
		
	4. 웹리소스를 획득할 때 :/images/Koala.jpg -> 자바코드 -> 서버에서 사용되므로 서버사이드경로로 푶기
	 								//http://localhost/webStrudy01 생략가능
		<%=application.getRealPath("/images/Koala.jpg") %>
		<%
		//11월14일 -5
			
			//하드코딩하지않아도된다 ! 
			String fileSystemPath = application.getRealPath("/images/Koala.jpg");
			String fileSystemPath2 = application.getRealPath("/06");
			
			File srcFile = new File(fileSystemPath);
// 			out.println(srcFile.getAbsolutePath());
			File targetFolder = new File(fileSystemPath2);//6번폴더가 타겟 //알고있는건 URL
			File targetFile = new File(targetFolder, srcFile.getName());
						
			int pointer = -1;
			byte[] buffer = new byte[1024];//속도 높이기위해
			
			//이제 이 중간에서 읽고쓰고 작업 그리고 닫아주기 close를 쓰기위해 try문
			try(//try리드 리소스구문?
// 				FileInputStream fis = new FileInputStream(srcFile);
				InputStream fis = application.getResourceAsStream("/images/Koala.jpg");//위에 껄 더 줄이기
				FileOutputStream fos = new FileOutputStream(targetFile);
			){
				while((pointer = fis.read(buffer)) != -1){
					fos.write(buffer, 0, pointer);//스트림 카피
				}
			}//여길 벗어나자마자 close가 될것. 
			System.out.println(targetFile.exists());
			
		%>
		
</pre>
<img src="<%=request.getContextPath() %>/06/Koala.jpg" />
<img src="<%=request.getContextPath() %>/images/Koala.jpg" />
</body>
</html>