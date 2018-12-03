<%@page import="java.util.List"%>
<%@page import="kr.or.ddit.vo.ProdVO"%>
<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@page import="kr.or.ddit.member.service.MemberServiceImpl"%>
<%@page import="kr.or.ddit.member.service.IMemberService"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/memberView.jsp</title>
<link rel="stylesheet"   href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet"   href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"   href="https://jqueryui.com/resources/demos/style.css">
<script src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script   src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"   integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
   crossorigin="anonymous"></script>
<script   src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"   integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
   crossorigin="anonymous"></script>
  
<script type="text/javascript">
$(function(){
	<c:if test="${not empty message }">
		alert('${message }');
		<c:remove var="message" scope="session"/>		
	</c:if>
	
	$(".date").datepicker({
		dateFormat : "yy-mm-dd"
	});
	
	$("#delBtn").on("click",function(){
		var chk=confirm(" 정말로 탈퇴 ? ");
		if(chk){
			var password = prompt("비밀번호입력");
			if(password){
				document.delForm.mem_pass.value=password;
// 				$("[name=ma'm_pass']").val(password); //위랑 똑같
				document.delForm.submit();		
			}
		}
	});
});

</script>
</head>
<body>

<c:set var="mutable" value="false"/>
<c:if test="${not empty sessionScope.authMember and 'ROLE_ADMIN' ne sessionScope.authMember.mem_auth }">
	<c:if  test="${sessionScope.authMember.mem_id eq member.mem_id }">
		<c:set var="mutable" value="true"/>
	</c:if>
</c:if>
		<form name="delForm" method="post" action="<%= request.getContextPath()%>/member/memberDelete.do"> 
 			<input type="hidden" name="mem_id" value="${member.mem_id }"/> 
 			<input type="hidden" name="mem_pass" /> 
 		</form> 

<c:if test="${mutable }">		
<form action = "<%=request.getContextPath()%>/member/memberUpdate.do" method="post">
</c:if>
<h4>회원정보 상세조회 및 수정폼</h4>
	<div>
		<table>
			<tr>
				<th>회원아이디</th>
				<td>${member.mem_id }<input type="hidden" name="mem_id" value="${member.mem_id }" />
					<span class="error">${errors.mem_id}</span></td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><input type="text" name="mem_pass" value="${member.mem_pass }" />
					<span class="error">${errors.mem_pass }</span></td>
			</tr>
			<tr>
				<th>회원명</th>
				<td><input type="text" name="mem_name" value="${member.mem_name }" />
					<span class="error">${errors.mem_name }</span></td>
			</tr>
			<tr>
				<th>주민번호1</th>
				<td><input type="text" name="mem_regno1" value="${member.mem_regno1 }" disabled="disabled"/>
					<span class="error">${errors.mem_regno1 }</span></td>
			</tr>
			<tr>
				<th>주민번호2</th>
				<td><input type="text" name="mem_regno2" value="${member.mem_regno2 }" disabled="disabled"/>
					<span class="error">${errors.mem_regno2 }</span></td>
			</tr>
			<tr>
				<th>생일</th>
				<td><input type="text" name="mem_bir" value="${member.mem_bir }" disabled="disabled"/>
					<span class="error">${errors.mem_bir }</span></td>
			</tr>
			<tr>
				<th>우편번호</th>
				<td><input type="text" name="mem_zip" value="${member.mem_zip }" />
					<span class="error">${errors.mem_zip }</span></td>
			</tr>
			<tr>
				<th>주소1</th>
				<td><input type="text" name="mem_add1" value="${member.mem_id }" />
					<span class="error">${errors.mem_add1 }</span></td>
			</tr>
			<tr>
				<th>주소2</th>
				<td><input type="text" name="mem_add2" value="${member.mem_add2}" />
				<span class="error">${errors.mem_add2 }</span></td>
			</tr>
			<tr>
				<th>집전화</th>
				<td><input type="text" name="mem_hometel" value="${member.mem_hometel }" />
					<span class="error">${errors.mem_hometel }</span></td>
			</tr>
			<tr>
				<th>회사전화</th>
				<td><input type="text" name="mem_comtel" value="${member.mem_comtel }" />
					<span class="error">${errors.mem_comtel }</span></td>
			</tr>
			<tr>
				<th>휴대폰</th>
				<td><input type="text" name="mem_hp" value="${member.mem_hp }" />
				<span class="error">${errors.mem_hp }</span></td>
			</tr>
			<tr>
				<th>이메일</th>
				<td><input type="text" name="mem_mail"	 value="${member.mem_mail }" />
				<span class="error">${errors.mem_mail}</span></td>
			</tr>
			<tr>
				<th>직업</th>
				<td><input type="text" name="mem_job" value="${member.mem_job }" />
				<span class="error">${errors.mem_job}</span></td>
			</tr>
			<tr>
				<th>취미</th>
				<td><input type="text" name="mem_like" value="${member.mem_like}" />
				<span class="error">${errors.mem_like }</span></td>
			</tr>
			<tr>
				<th>기념일</th>
				<td><input type="text" name="mem_memorial" value="${member.mem_memorial }" />
				<span class="error">${errors.mem_memorial }</span></td>
			</tr>
			<tr>
				<th>기념일자</th>
				<td><input type="text" name="mem_memorialday" value="${member.mem_memorialday }" />
				<span class="error">${errors.mem_memorialday}</span></td>
			</tr>
			<tr>
				<th>마일리지</th>
				<td>${errors.mem_mileage}</td>
			</tr>
			<tr>
				<th>탈퇴여부</th>
				<td>${member.mem_delete eq 'Y'?"탈퇴":"활동중" }</td>
			</tr>
			
			<tr>
				<td colspan="2">
					<input type="button" class="btn btn-info" value="뒤로가기" onclick="history.back();"/>
					
					<c:if test="${mutable }">
						<input type="submit" value="수정"/>
						<input type="reset" value="취소"/>
						<input type="button" value="탈퇴" id="delBtn"/>
					</c:if>
				</td>
			</tr>
		</table>
		${mutable}-------------------------------
		<c:if test="${mutable }">
		</div>
	</form>
</c:if>
<h4>회원의 구매상품 목록</h4>
<table>
	<thead>
		<tr>
			<th>PROD_ID</th>
			<th>PROD_NAME</th>
			<th>PROD_COST</th>
			<th>PROD_PRICE</th>
			<th>PROD_OUTLINE</th>
		</tr>
	</thead>
	<tbody>
		<c:set var="prodList" value="${member.prodList }"/>
		<c:choose>
			<c:when test="${not empty prodList}">
				<c:forEach items="${prodList}" var="prod">
					<tr>
						<td>${prod.prod_id}</td>
						<td>${prod.prod_name}</td>
						<td>${prod.prod_cost}</td>
						<td>${prod.prod_price}</td>
						<td>${prod.prod_outline}</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
				<td colspan="5">구매상품없음</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>
</body>
</html>