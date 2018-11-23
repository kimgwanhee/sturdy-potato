<%@page import="kr.or.ddit.vo.PagingInfoVO"%>
<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@page import="java.util.List"%>
<%@page import="kr.or.ddit.member.service.MemberServiceImpl"%>
<%@page import="kr.or.ddit.member.service.IMemberService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 	IMemberService service = new MemberServiceImpl();
// 	List<MemberVO> memberList = service.retrieveMemberList();
//이제 서블릿에 있으므로 스코프부르는것부터 시작

	PagingInfoVO pagingVO = (PagingInfoVO)request.getAttribute("pagingVO");
	List<MemberVO> memberList = pagingVO.getDataList();
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/memberList.jsp</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

</head>
<body>
<h4> 회원 목록 </h4>
<input type="button" class="button" value="신규등록" onclick="location.href='<%=request.getContextPath()%>/member/memberInsert.do';"/>
<table class="table">
	<thead class="thead-dark">
		<tr>
			<th>회원 아이디</th>
			<th>회원명</th>
			<th>주소</th>
			<th>휴대폰</th>
			<th>이메일</th>
			<th>마일리지</th>
		</tr>
	</thead>
	<tbody>

		<%
			if(memberList.size()==0){
		%>
				<tr><td colspan="6">회원의 목록이 없음</td></tr>
		<%
			}else{
				for(MemberVO mv:memberList){
		%>
				<tr><td><%=mv.getMem_id()%></td>				
				<td><a href="<%=request.getContextPath()%>/member/memberView.do?who=<%=mv.getMem_id() %>" ><%=mv.getMem_name()%></a></td>				
				<td><%=mv.getAddress()%></td>				
				<td><%=mv.getMem_hp()%></td>				
				<td><%=mv.getMem_mail()%></td>		
				<td><%=mv.getMem_mileage()%></td></tr>				
		<% 
				}
			}
		%>
	
	</tbody>
	<tfoot>
		<tr>
			<td colspan="6">
				<nav aria-label="Page navigation example">
				 	<%= pagingVO.getPagingHTML() %>
				</nav>
			</td>
		</tr>
	</tfoot>
</table>
</body>
</html>