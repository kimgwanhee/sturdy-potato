<%@page import="java.util.Map.Entry"%>
<%@page import="javax.swing.text.html.parser.Entity"%>
<%@page import="kr.or.ddit.vo.BuyerVO"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="kr.or.ddit.vo.ProdVO"%>
<%@page import="kr.or.ddit.vo.PagingInfoVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<c:url value="/prod/prodView.do" var="prodView"/>
<script type="text/javascript">
	function ${pagingVO.funcName}(page){
		$("[name='searchForm']").find("[name='page']").val(page);
// 		document.searchForm.page.value = page; 위코드와 똑같 
		$("[name='searchForm']").submit();//함수 호출하고 submit 
// 		document.searchForm.submit(); 위코드와 똑같지만 함수 호출만 하고 끝 (동기방식 먹통됨)
	}
	
	$(function(){
		var prod_lguTag = $("[name='prod_lgu']");
		var prod_buyerTag = $("[name = 'prod_buyer']");
		prod_lguTag.val("${pagingVO.searchVO.prod_lgu}");
		prod_buyerTag.val("${pagingVO.searchVO.prod_buyer}");
		prod_lguTag.on("change", function(){
			var lprod_gu = $(this).val();
			var buyerOptions = prod_buyerTag.find("option");
			$(buyerOptions).hide();
			if(lprod_gu){
				var buyerOptions2 = prod_buyerTag.find("option."+lprod_gu);
				$(buyerOptions2).show();
			}else{
				$(buyerOptions).show();
			}
		});
		$("#listBody").on("click", "tr" ,function(){
			var prod_id = $(this).find("td:first").text();//자식중에서 특정조건에 맞는애들만 찾는다는..
			location.href = "${prodView}?what="+prod_id;
		});
		
		var tbodyTag = $('#listBody');
		var navTag = $('#pagenav');
		
		$("[name='searchForm']").on("submit", function(event){
			event.preventDefault();
			var data = $(this).serialize();//queryString 생성
			$.ajax({
// 				url : "",//어디로 요청을 보낼거냐 - 생략 - 현재브라우저가 가지고있는 주소 그대로 쓴다(prodList.do)
// 				method : "",//get방식을 쓰겠다. - form태그를 비동기로 쓰고싶은데 그  form태그의 액션 메소드 모두 그대로 쓰겠다 없음->나도생략
				data : data,
				dataType:"json",//공통표현방식? ? ? ? ? ? ? ? ? ? ? ? ? ? ?
// 				data:{//page prodlgu prodbuyer proname  4개 가지고감//이 값들은 아래에있는 searchform에서 어떤값들을 가지고있냐에따라 달라짐
// 				},

				//보내는 정보
				
				success:function(resp){//resp 는 json데이타가 언마샬링되어있는..?
					var prodList = resp.dataList;
					var html="";
					if(prodList){
						$.each(prodList, function(idx,prod){
							html += "<tr>";
							html += "<td>"+prod.prod_id+"</td>";
							html += "<td>"+prod.prod_name+"</td>";
							html += "<td>"+prod.lprod_nm+"</td>";
							html += "<td>"+prod.buyer_name+"</td>";
							html += "<td>"+prod.prod_cost+"</td>";
							html += "<td>"+prod.prod_outline+"</td>";
							html += "<td>"+prod.prod_mileage+"</td>";
							html += "</tr>";
						});
						
					}else{
						html+="<tr><td colspan = '7'> 상품이 없음. </td></tr>";
					}
					tbodyTag.html(html);
					navTag.html(resp.pagingHTML);
					$("[name='page']").val("");
				},
				error:function(){
					
				}
			});
			return false
		});
	});
</script>
</head>
<body>
<!-- 스크린사이즈 7 -->
<!-- 블럭사이즈 4 -->
<!-- gui이벤트.. on계열은 이벤트핸들러를 jquery쓰기전에 자바스크립트로 넣은것 -->
<form name="searchForm" onsubmit="return false;">
	<input type="text" name="page" />
	<select name="prod_lgu">
		<option value="">분류선택</option>
		
		<c:forEach var="lprod" items="${lprodList}">
				<option value="${lprod.LPROD_GU}">${lprod.LPROD_NM}</option>
		</c:forEach>
		
	</select>
	
	<select name="prod_buyer">
		<option value="">거래처선택</option>
		<c:forEach var="buyer" items="${buyerList}">
			<option value="${buyer.buyer_id}" class="${buyer.buyer_lgu}">${buyer.buyer_name}</option>
		</c:forEach>
		
	</select>
	<input type="text" name="prod_name" value="${pagingVO.searchVO.prod_name}"/>
	<input type="submit" name="검색" />
</form>
<input type="button" class="btn btn-info" value="신규 상품 등록" onclick="location.href='<%=request.getContextPath()%>/prod/prodInsert.do';" 
/>

<input type="image" src='<c:url value="/images/korea.png"/>' onclick="location.href='?locale=ko';"/>

<input type="image" src='<c:url value="/images/america.png"/>' onclick="location.href='?locale=en';"/>

<c:if test="${not empty param.locale }">
			<fmt:message key="bow"></fmt:message>
</c:if>

<table class="table">
	<thead>
	<c:if test="${not empty param.locale }">
	<fmt:setLocale value="${param.locale}"/>
	<fmt:bundle basename="kr.or.ddit.msgs.message">
		<tr>
			<th><fmt:message key="prod.prod_id"/></th>
			<th><fmt:message key="prod.prod_name"/></th>
			<th><fmt:message key="prod.prod_lgu"/></th>
			<th><fmt:message key="prod.prod_buyer"/></th>
			<th><fmt:message key="prod.prod_price"/></th>
			<th><fmt:message key="prod.prod_outline"/></th>
			<th><fmt:message key="prod.prod_mileage"/></th>
		</tr>
	</fmt:bundle>
	</c:if>
	</thead>
	
	<tbody id="listBody">
	
	<c:choose>
		<c:when test="${not empty pagingVO.dataList }">
			<c:forEach var="prod" items="${pagingVO.dataList}">
				<tr>
					<td>${prod.prod_id}</td>
					<td>${prod.prod_name}</td>
					<td>${prod.lprod_nm}</td>
					<td>${prod.buyer_name}</td>
					<td>${prod.prod_price}</td>
					<td>${prod.prod_outline}</td>
					<td>${prod.prod_mileage}</td>
				</tr>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<tr>
				<td colspan="7">조건에 맞는 상품이 없음.</td>
			</tr>
		</c:otherwise>
	</c:choose>
	
	</tbody>
	<tfoot>
		<tr>
			<td colspan = "7">
				<nav aria-label="Page navigation example" id="pagenav">
			 	${pagingVO.pagingHTML}
				</nav>
			</td>
		</tr>
	</tfoot>
</table>
</body>
</html>