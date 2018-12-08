<%@page import="kr.or.ddit.vo.BuyerVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script src="http://malsup.github.com/jquery.form.js"></script> 

<script type="text/javascript">
	function ${pagingVO.funcName}(page){
		searchForm.find("[name='page']").val(page);
		searchForm.submit();//함수 호출하고 submit 
	// 	document.searchForm.page.value = d;
	// 	document.searchForm.submit();
	}
	
	function createBody(paging){
		var buyerList = paging.dataList;
		var html="";
		if(buyerList){
			$.each(buyerList, function(idx,buyer){
				html += "<tr>";
				html += "<td>"+buyer.buyer_id+"</td>";
				html += "<td><a href='${pageContext.request.contextPath}/buyer/buyerView.do?who="+buyer.buyer_id+"'>"+buyer.buyer_name+"</a></td>";
				html += "<td>"+buyer.buyer_zip+"</td>";
				html += "<td>"+buyer.buyer_add1 + buyer.buyer_add2+"</td>";
				html += "<td>"+buyer.buyer_comtel+"</td>";
				html += "<td>"+buyer.buyer_fax+"</td>";
				html += "<td>"+buyer.buyer_mail+"</td>";
				html += "</tr>";
			});
			
		}else{
			html+="<tr><td colspan = '7'> 거래처 정보 없음. </td></tr>";
		}
		bodyList.html(html);
		pagenav.html(paging.pagingHTML);
		$("[name='page']").val("");
	}

	$(function(){//ready function
		searchForm = $("[name='searchForm']");
		bodyList = $("#bodyList");
		pagenav = $("#pagenav");
		searchForm.ajaxForm({//ajaxForm 동기요청을 비동기로바꿈
			dataType : 'json',
			//여기까지가 요청부분
			success : createBody
		});
		
	});

</script>

</head>
<body>
<h4>Buyer List</h4>
<input type="button" class="button" value="신규등록" 
			onclick="location.href='${pageContext.request.contextPath}/buyer/buyerInsert.do';"/>
			
	<form name="searchForm" action="">
		<select name="searchType">
			<option value="all">전체</option>
			<option value="name">이름</option>
			<option value="lprodNm">분류명</option>
		</select>
		<script type="text/javascript">
			document.searchForm.searchType.value="${!empty pagingVO.searchType ? pagingVO.searchType : 'all'}";
		</script>
		<input type="text" name="searchWord" value="${pagingVO.searchWord}"/>
		<input type="submit" value="검색">
		<input type="hidden" name="page"/>
	</form>

	<table class="table">
	<thead class="thead-dark">
		<tr>
			<th>Buyer Id</th>
			<th>Buyer Name</th>
			<th>Buyer Zip</th>
			<th>ADDRESS</th>
			<th>Buyer Comtel</th>
			<th>Buyer Fax</th>
			<th>Buyer Mail</th>
		</tr>
	</thead>
	<tbody id="bodyList">
		<c:choose>
			<c:when test="${not empty pagingVO.dataList}">
				<c:forEach var="buyerVO" items="${pagingVO.dataList}">
					<tr>
						<c:url value="/buyer/buyerView.do" var="viewURL">
							<c:param name="who" value="${buyerVO.buyer_id }"/>
						</c:url>
						<td>${buyerVO.buyer_id}</td>
						<td><a href="${viewURL}">${buyerVO.buyer_name}</a></td>
						<td>${buyerVO.buyer_zip}</td>
						<td>${buyerVO.buyer_add1 } ${buyerVO.buyer_add2}</td>
						<td>${buyerVO.buyer_comtel}</td>
						<td>${buyerVO.buyer_fax}</td>
						<td>${buyerVO.buyer_mail}</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td colspan="7">바이어정보가 존재하지않습니다!</td>
				</tr>
			</c:otherwise>
		</c:choose>
	
	</tbody>
	<tfoot>
		<tr>
				<td colspan="6">
				<nav aria-label="Page navigation example" id="pagenav">
					${pagingVO.pagingHTML}
				</nav>
			</td>
		</tr>
	</tfoot>
</table>
</body>
</html>