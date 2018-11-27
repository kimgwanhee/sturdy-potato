<%@page import="java.util.Map.Entry"%>
<%@page import="javax.swing.text.html.parser.Entity"%>
<%@page import="kr.or.ddit.vo.BuyerVO"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="kr.or.ddit.vo.ProdVO"%>
<%@page import="kr.or.ddit.vo.PagingInfoVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	PagingInfoVO<ProdVO> pagingVO = (PagingInfoVO<ProdVO>)request.getAttribute("pagingVO");
	List<ProdVO> prodList = pagingVO.getDataList();
	
	Map<String, String> lprodList = (Map) request.getAttribute("lprodList");
	List<BuyerVO> buyerList = (List) request.getAttribute("buyerList");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script type="text/javascript">
	function <%=pagingVO.getFuncName()%>(page){
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
			location.href = "<%=request.getContextPath()%>/prod/prodView.do?what="+prod_id;
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
				success:function(resp){//resp 는 json데이타가 언마샬링되어있는..?
					var prodList = resp.dataList;
					var html="";
					if(prodList){
						$.each(prodList, function(idx, prod){
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
		<%
			for(Entry<String, String> entry : lprodList.entrySet()){
				%>
				<option value="<%=entry.getKey()%>"><%=entry.getValue()%></option>
				<%
			}
		%>
	</select>
	<select name="prod_buyer">
		<option value="">거래처선택</option>
		<%
			for(BuyerVO buyer : buyerList){
			%>
				<option value="<%=buyer.getBuyer_id()%>" class="<%=buyer.getBuyer_lgu()%>"><%=buyer.getBuyer_name()%></option>
			<%	
			}
		%>
	</select>
	<input type="text" name="prod_name" value="${pagingVO.searchVO.prod_name}"/>
	<input type="submit" name="검색" />
</form>
<input type="button" class="btn btn-info" value="신규 상품 등록" onclick="location.href='<%=request.getContextPath()%>/prod/prodInsert.do';" 
/>

<table class="table">
	<thead>
		<tr>
			<th>상품코드</th>
			<th>상품명</th>
			<th>분류명</th>
			<th>거래처명</th>
			<th>판매가</th>
			<th>상품개요</th>
			<th>마일리지</th>
		</tr>
	</thead>
	
	<tbody id="listBody">
	<%
		if(prodList.size()>0){
			for(ProdVO prod : prodList){
				%>
				<tr>
					<td><%=prod.getProd_id() %></td>
					<td><%=prod.getProd_name() %></td>
					<td><%=prod.getLprod_nm() %></td>
					<td><%=prod.getBuyer_name() %></td>
					<td><%=prod.getProd_price()%></td>
					<td><%=prod.getProd_outline() %></td>
					<td><%=prod.getProd_mileage() %></td>
				</tr>
				<%
			}
		}else{
			%>
			<tr>
				<td colspan="7">조건에 맞는 상품이 없음.</td>
			</tr>
			<%
		}
	%>
	</tbody>
	<tfoot>
		<tr>
			<td colspan = "7">
				<nav aria-label="Page navigation example" id="pagenav">
			 	<%= pagingVO.getPagingHTML() %>
				</nav>
			</td>
		</tr>
	</tfoot>
</table>
</body>
</html>