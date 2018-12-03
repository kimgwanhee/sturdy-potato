<%@page import="java.util.Objects"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="kr.or.ddit.web.SimpleFormProcessServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- //**11월13일 -->
<!-- 알바몬에서 알바생의 프로필을 입력받으려고 함. -->
<!-- 	이름, 나이, 주소, 전화번호, 성별,  -->
<!-- 	경력, 학력, 자격증 -->

<form action ="<%= request.getContextPath() %>/albamon" 
		method="post">
	<table>
		<tr>
			<th>이름</th>
			<td>
				<input type="text" name="name" value="<%= Objects.toString(request.getParameter("name"), "")%>"/>
			</td>
		</tr>
		
		<tr>
			<th>나이</th>
			<td>
				<input type="number" name="age" min="15" max="40" value="<%= Objects.toString(request.getParameter("age"), "")%>"/>
			</td>
		</tr>
		
		<tr>
			<th>전번</th>
			<td>
				<input type="text" name="tel" placeholder="000-0000-0000"
					pattern="\d{3}-[0-9]{3,4}-\d{4}" value="<%= Objects.toString(request.getParameter("tel"), "")%>"
				/>
			</td>
		</tr>
		
		<tr>
			<th>주소</th>
			<td>
				<input type="text" name="address" value="<%= Objects.toString(request.getParameter("address"), "")%>"/>
			</td>
		</tr>
		
		<tr>
			<th>성별</th>
			<td>
				<label><input type="radio" name="gender" value="M" <%="M".equals("gender")?"checked":""%>/>남</label>
				<label><input type="radio" name="gender" value="F"/>여</label>
			</td>
		</tr>
		
	
		<tr>
			<th>학력</th>
			<td>
				<select name="grade">
					<option value="">학력</option>
						<%
						//스크립플릿?기호
							SimpleFormProcessServlet servlet = new SimpleFormProcessServlet();
							String pattern = "<option value= '%s' %s>%s</option>";
							for(Entry<String, String> entry : servlet.gradeMap.entrySet()){
								
								String name = entry.getKey();
								String value = entry.getValue();
								out.println(String.format(pattern, name, value));
							}
						
						%>
				</select>
			</td>
		</tr>
		
		<tr>
			<th>경력</th>
			<td>
				<textarea rows="3" cols="100" name="career"></textarea>
			</td>
		</tr>
		
		<tr>
			<th>자격증</th>
			<td>
				<select name="license" multiple="multiple" size="10">
					<%
						for(Entry<String, String> entry : servlet.licenseMap.entrySet()){
							String name = entry.getKey();
							String value = entry.getValue();
							out.println(String.format(pattern, name, value));
						}
					 %>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" value="등록" />
				<input type="reset" value="취소" />
				<input type="button" value="그냥 버튼"/>
			</td>
		</tr>
		
	</table>
</form>

</body>
</html>