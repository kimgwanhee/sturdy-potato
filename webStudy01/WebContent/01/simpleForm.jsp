<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Arrays"%>
<%@page import="kr.or.ddit.vo.AlbasengVO"%>
<%@page import="java.util.Objects"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Set"%>
<%-- <%@page import="kr.or.ddit.web.SimpleFormProcessServlet"%> 이제 필요없음 써먹을수없다--%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.error{
	color : red;
}

</style>
<%
Map<String, String> gradeMap = (Map<String, String>) application.getAttribute("gradeMap");
Map<String, String> licenseMap = (Map<String, String>) application.getAttribute("licenseMap");
AlbasengVO albaVO = (AlbasengVO)request.getAttribute("albaVO");//이거때문에 널포인트에러발생
Map<String, String> errors = (Map<String, String>)request.getAttribute("errors");//누락된거잡을려고 제일마지막
if(albaVO == null){
	albaVO = new AlbasengVO();
}
if(errors ==null){
	errors = new LinkedHashMap<>();
}
/*11월14일-9
//**11월13일
String name = request.getParameter("name");
String age = request.getParameter("age");
String tel = request.getParameter("tel");
String address = request.getParameter("address");
String gender = request.getParameter("gender");
String grade = request.getParameter("grade");
String carrer = request.getParameter("carrer");
*/
String license[] = request.getParameterValues("license");
// 	AlbasengVO vo = new AlbasengVO();
// 	if(request.getAttribute("vo")!=null){
// 		vo = (AlbasengVO)request.getAttribute("vo");
// 	}
%>
</head>
<body>
<!-- 알바몬에서 알바생의 프로필을 입력받으려고 함. -->
<!-- 이름, 나이, 주소, 전번, 성별,  -->
<!-- 경력, 학력, 자기소개서, 자격증 -->
<form action="<%=request.getContextPath() %>/albamon" method="post">
<table>
	<tr>
		<th>이름</th>
		<td>
			<input type="text" name="name" value="<%=Objects.toString(albaVO.getName(),"") %>"
			/>
			<span class="error">
				<%=Objects.toString(errors.get("name"),"") %>
			</span>
		</td>
	</tr>
	
	<tr>
		<th>나이</th>
		<td>
			<input type="number" name="age" min="15" max="40" value="<%=Objects.toString(albaVO.getAge(),"") %>"/>
			
			<span class="error">
				<%=Objects.toString(errors.get("tel"),"") %>
			</span>
		</td>
	</tr>
	
	<tr>
		<th>전번</th>
		<td>
			<input type="text" name="tel" placeholder="000-0000-0000"
				pattern="\d{3}-\d{3,4}-\d{4}" value="<%=Objects.toString(albaVO.getTel(),"") %>"
				required="required"/>
		</td>
	</tr>
	
	<tr>
		<th>주소</th>
		<td>
			<input type="text" name="address" value="<%=Objects.toString(albaVO.getAddress(),"") %>"
			required="required"/>
			
			<span class="error">
				<%=Objects.toString(errors.get("address"),"") %>
			</span>
			
		</td>
	</tr>
	
	<tr>
		<th>성별</th>
		<td>
			<label><input type="radio" name="gender" value="M" <%="M".equals(albaVO.getGender())?"checked":"" %>/>남</label>
			<label><input type="radio" name="gender" value="F" <%="F".equals(albaVO.getGender())?"checked":"" %>/>여</label>
		</td>
	</tr>
	
	<tr>
		<th>학력</th>
		<td>
			<select name="grade">
				<option value="">학력</option>
				<%
					String pattern = "<option value='%s' %s> %s </option>";
					for(Entry<String,String> entry :  gradeMap.entrySet()){//gradeMap를 먼저 만들어줘야함 꺼내와야함먼저그럼
						String selected = "";
						if(entry.getKey().equals(albaVO.getGrade())){
							selected = "selected";
						}
						out.println(String.format(pattern,entry.getKey(),selected,entry.getValue()));
					}
					
				%>
			</select>
		</td>
	</tr>	
	
	<tr>
		<th>경력</th>
		<td>
			<textarea rows="3" cols="100" name="carrer"><%=Objects.toString(albaVO.getCareer(),"") %></textarea>
		</td>
	</tr>
	
	<tr>
		<th>자격증</th>
		<td>
			<select name="license" multiple="multiple" size="10">
				<option value="">자격증 </option>
				<%
						if(license!=null){
							Arrays.sort(license);
						}
				for(Entry<String,String> entry :  licenseMap.entrySet()){
						String selected = "";
						if(license!=null && Arrays.binarySearch(license, entry.getKey())>-1){
							selected = "selected";
						}
						out.println(String.format(pattern,entry.getKey(),selected,entry.getValue()));
					}
				%>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value="등록">
			<input type="reset" value="취소">
			<input type="button" value="버튼아닌버튼">
		</td>
	</tr>
</table>
</form>
</body>
</html>