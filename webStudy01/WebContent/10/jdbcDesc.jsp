<%@page import="kr.or.ddit.db.ConnectionFactory"%>
<%@page import="kr.or.ddit.vo.DataBasePropertyVO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSetMetaData"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="sun.font.CreatedFontTracker"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="oracle.jdbc.OracleDriver"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>10/jdbcDesc.jsp</title>
</head>
<body>
<h4>JDBC(Java DataBase Connectivity)</h4>
<pre>
	데이터베이스 연결 프로그래밍 단계
	1. 드라이버를 빌드패스에 추가
	2. 드라이버 클래스 로딩
	3. 연결 객체(Connection) 생성 
	4. 쿼리 객체를 생성
		Statement
		PreparedStatement
		CallableStatement
	5. 쿼리실행(CRUD)
		ResultSet executeQuery : Select
		int(실행에 영향을 받은 레코드 수) executeUpdate : insert/update/delete
	6. 결과 집합 사용
	7. 자원의 해제 : finally블럭 / try~with~resource 구문
		
	<%
		List<DataBasePropertyVO> dataList = new ArrayList<>();  //레코드 들을 담아야함 -> 또다른객체필요
		String[] headers=null;
		try(//try가 되는순간 자동으로()안에 close됨 
			Connection conn =ConnectionFactory.getConnection();
			Statement stmt = conn.createStatement();
		){
			out.println(conn.getClass());//conn는 인터페이스지만 확인차원해서 클래스이름 확인
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT PROPERTY_NAME, PROPERTY_VALUE, DESCRIPTION");//토큰(단어)이 구분되기 위해 뒤에 공백
			sql.append(" FROM DATABASE_PROPERTIES");
			ResultSet rs = stmt.executeQuery(sql.toString());
			ResultSetMetaData rsmd = rs.getMetaData();
			headers = new String[rsmd.getColumnCount()];//배열은 사이즈고정- 동적으로 가져와야한다 matadata가 가지고있음
			
			for(int idx=1; idx<=rsmd.getColumnCount(); idx++){
				headers[idx-1] = rsmd.getColumnClassName(idx);
			}
			while(rs.next()){	//rs.next() 조회된 레코드..다음것 없으면 빠져나옴 //boolean타입
				DataBasePropertyVO vo = new DataBasePropertyVO();
				//레코드 한건 == VO
				vo.setProperty_name(rs.getString("PROPERTY_NAME"));
				vo.setProperty_value(rs.getString("PROPERTY_VALUE"));
				vo.setDescription(rs.getString("DESCRIPTION"));
				dataList.add(vo);
			}//while end
		}//try block end
				
	%>
</pre>
<table>
	<thead>
		<tr>
			<%
				for(String head : headers){
					%>
					<th><%=head %></th>
					<%
				}
			%>
		</tr>
	</thead>
	<tbody>
			<%
				for(DataBasePropertyVO vo : dataList){
					%>
					<tr>
						<td><%=vo.getProperty_name() %></td>
						<td><%=vo.getProperty_value() %></td>
						<td><%=vo.getDescription() %></td>
					</tr>
					<%
				}
			%>
	</tbody>
</table>

</body>
</html>