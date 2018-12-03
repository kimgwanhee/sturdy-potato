<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
// 	IMemberService service = new MemberServiceImpl();
// 	List<MemberVO> memberList = service.retrieveMemberList();
//이제 서블릿에 있으므로 스코프부르는것부터 시작

// 	PagingInfoVO pagingVO = (PagingInfoVO)request.getAttribute("pagingVO");
// 	List<MemberVO> memberList = pagingVO.getDataList();
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/memberList.jsp</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script type="text/javascript">
	function ${pagingVO.funcName} (page){
		document.searchForm.page.value = page;
		document.searchForm.submit();
	}
</script>
</head>
<body>
<h4> 회원 목록 </h4>
<input type="button" class="button" value="신규등록" 
	onclick="location.href='<%=request.getContextPath()%>/member/memberInsert.do';"/>
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
		<c:set var="memberList" value="${pagingVO.dataList }"/>
		<c:if test="${not empty memberList }">
			<c:forEach items="${memberList}" var="member" >
				<tr>
					<c:url value="/member/memberView.do" var="viewURL">
						<c:param name="who" value="${member.mem_id }"/>
					</c:url>
					
					<td>${member.mem_id }</td>		
					<td><a href="${viewURL }">${member.mem_name}</a></td>		
					<td>${member.address }</td>				
					<td>${member.mem_hp}</td>				
					<td>${member.mem_mail}</td>		
					<td>${member.mem_mileage }</td>
				</tr>		
			</c:forEach>
		</c:if>
		<c:if test="${empty memberList }">
			<tr>
				<td colspan="6">회원 목록 없음!</td>
			</tr>
		</c:if>
		
	</tbody>
	<tfoot>
		<tr>
			<td colspan="6">
				<nav aria-label="Page navigation example">
				 	${pagingVO.pagingHTML }
				</nav>
				<form name="searchForm">
					<input type="text" name="page" value=""/>
					<select name="searchType">
						<option value="all">전체</option>
						<option value="name">이름</option>
						<option value="address">지역</option>
					</select>
					<script type="text/javascript">
						
						document.searchForm.searchType.value='${!empty pagingVO.searchType ? pagingVO.searchType : "all"}'';
					</script>
					<input type="text" name="searchWord" value="${pagingVO.searchWord }"/>
					<input type="submit" name="검색" />
				</form>
			</td>
		</tr>
	</tfoot>
</table>
</body>
</html>