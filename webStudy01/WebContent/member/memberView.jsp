<%@page import="kr.or.ddit.member.service.MemberServiceImpl"%>
<%@page import="kr.or.ddit.member.service.IMemberService"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="member" class="kr.or.ddit.vo.MemberVO" scope="request"></jsp:useBean>
<%
	IMemberService service = new MemberServiceImpl();

// 1.누구라는걸 잡으려면 파라미터로 잡아야함
	member.setMem_id(request.getParameter("who"));
	if(StringUtils.isNotBlank(member.getMem_id())){
		member = service.retrieveMember(member.getMem_id());
	}
	
//2.상세정보를 보일려면 누구라는게 반드시 필요 위에게 있으면..(검증)
//요청이 잘못됫다면 클라이언트 잘못 400번대 에러


//3.파라미터가있다면 db에서 그사람 정보 가져와서 ui만들어주기 
//db가져오려면 dao를 사용하게되면 가공하지않은 데이타가 그대로 오니깐 retrieveMemberList에 로직객체에 의존

//매핑파일에 한명을 조회할수있는 쿼리문이 필요 - 이미있음
//dao에 해당 메소드가 구현되어있어야함- 이미했음
// 그 로직을 구현하고 사용하면됨..

%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/memberView.jsp</title>
</head>
<body>

	<div>
		<table>
			<thead>
				<tr>
					<th>항목</th> 
					<th>정보</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th>아이디</th>
					<td><%=member.getMem_id() %></td>				
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><%=member.getMem_pass() %></td>				
				</tr>
				<tr>
					<th>이름</th>
					<td><%=member.getMem_name() %></td>				
				</tr>
				<tr>
					<th>주민번호</th>
					<td><%=member.getMem_regno1()+"-"+member.getMem_regno2()%></td>				
				</tr>
				<tr>
					<th>생일</th>
					<td><%=member.getMem_bir() %></td>				
				</tr>
				<tr>
					<th>우편번호</th>
					<td><%=member.getMem_zip()%></td>				
				</tr>
				<tr>
					<th>주소</th>
					<td><%=member.getAddress() %></td>				
				</tr>
				<tr>
					<th>집번호</th>
					<td><%=member.getMem_hometel()%></td>				
				</tr>
				<tr>
					<th>회사번호</th>
					<td><%=member.getMem_comtel()%></td>				
				</tr>
				<tr>
					<th>폰번호</th>
					<td><%= member.getMem_hp()%></td>				
				</tr>
				<tr>
					<th>이메일</th>
					<td><%=member.getMem_mail()%></td>				
				</tr>
				<tr>
					<th>직업</th>
					<td><%=member.getMem_job()%></td>				
				</tr>
				<tr>
					<th>취미</th>
					<td><%=member.getMem_like() %></td>				
				</tr>
				<tr>
					<th>기념일</th>
					<td><%=member.getMem_memorial() %></td>				
				</tr>
				<tr>
					<th>기념일자</th>
					<td><%=member.getMem_memorialday() %></td>				
				</tr>
				<tr>
					<th>마일리지</th>
					<td><%= member.getMem_mileage()%></td>				
				</tr>
			</tbody>
		</table>
	</div>



</body>
</html>