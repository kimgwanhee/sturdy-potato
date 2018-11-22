<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="kr.or.ddit.db.ConnectionFactory"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>10/ConnJob.jsp</title>
</head>
<body>
<%
	long startTime = System.currentTimeMillis();
		try(// 이부분은 가져오기위해서 한번 커넥션을 한것 
			Connection conn = ConnectionFactory.getConnection();		
			Statement stmt = conn.createStatement();
		){
			String sql = "SELECT MEM_NAME FROM MEMBER WHERE MEM_ID = 'a001'";
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){//next로 이동할게 있다면 (한건이라도 조회가 됬다면)
				for(int count =1; count <=100; count++){
				out.println(rs.getString(1));
				}
			}
		}
	long endTime = System.currentTimeMillis();
%>
소요 시간 : <%= endTime-startTime%>ms

</body>
</html>