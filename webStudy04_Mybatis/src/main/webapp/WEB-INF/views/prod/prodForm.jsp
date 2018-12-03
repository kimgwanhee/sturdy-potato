<%@page import="java.util.List"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<jsp:useBean id="prod" class="kr.or.ddit.vo.ProdVO" scope="request"></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
   href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet"
   href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
   href="https://jqueryui.com/resources/demos/style.css">

<script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script
   src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
   integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
   crossorigin="anonymous"></script>
<script
   src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
   integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
   crossorigin="anonymous"></script>
<script type="text/javascript">
	$(function(){
		<c:if test="${not empty message }">
			alert("${message }");
		</c:if>
		$("[name$='date']").datepicker({
			dateFormat:"yy-mm-dd"
		});
		var prod_lguTag = $("[name='prod_lgu']");
		var pattern = "<option value='%V'>%T</option>";
		var prod_buyerTag = $("[name='prod_buyer']");
		$("[name='prod_lgu']").on("change", function(){
			var prod_lgu = $(this).val();
			$.ajax({//주소(URL), 메서드, 파라미터(데이타), 내가응답데이타는 어떤형식으로 받을수있는지(html json..등의 데이타타입)가 필요..
				url : "${pageContext.request.contextPath}/prod/getBuyerList.do",
				data : {
					prod_lgu:prod_lgu//파라미터명 : 값value
				},
// 				method : "",//쿼리스트링으로 붙일건지 바디로 붙일건지..등등 만약 안쓰면 ->get방식
				dataType : "json",//xml이나 json(가벼운방식으로 표현할 땐..)
				//요청정보 안에 들어있는 header가 json으로 내보낸다는것-> accept타입 , mime설정 -> content-type
				success : function(resp) {//처리에 성공했을때..//언마샬링..?뭐징
					var options = "";
					$.each(resp, function(idx, buyer){//1. index와 2. 엘리먼트접근할때 쓸수있는 블럭변수
						options += pattern.replace("%V", buyer.buyer_id)
											.replace("%T", buyer.buyer_name);
					});
					prod_buyerTag.html(options);
// 					prod_buyerTag.val("${prod.prod_buyer}");
				},
				error : function(resp) {//에러도 하나의 응답이니깐 resp로..

				}
			});
		});
		prod_lguTag.val("${prod.prod_lgu}");
		prod_lguTag.trigger("change");
	});
</script>
</head>
<body>
	<form method="post" action="${pageContext.request.contextPath}/prod/prodInsert.do" enctype="multipart/form-data">
	<input type="hidden" name="prod_id" value="${prod.prod_id}"/>
		<table>
			<tr>
				<th>상품명</th>
				<td><input type="text" name="prod_name"
					value="${prod.prod_name}" /><span class="error">${prod.prod_name}</span></td>
			</tr>
			<tr>
				<th>분류코드</th>
				<td><div class="input-group">
					<select name="prod_lgu">
						<option value="">분류선택</option>
						
						<c:forEach var="lprod" items="${lprodList}">
							<option value="${lprod.LPROD_GU}">${lprod.LPROD_NM}</option>
						</c:forEach>
					</select>
			</tr>
			<tr>
				<th>거래처</th>
				<td><div class="input-group">
					<select name="prod_buyer">
						<option value="">거래처선택</option>
					</select>
				<span class="error">${error.prod_buyer}</span></div></td>
			</tr>
			<tr>
				<th>비용</th>
				<td><input type="text" name="prod_cost"
					value="${prod.prod_cost}" /><span class="error">${error.prod_cost}</span></td>
			</tr>
			<tr>
				<th>판매가</th>
				<td><input type="text" name="prod_price"
					value="${prod.prod_price}" /><span class="error">${error.prod_price}</span></td>
			</tr>
			<tr>
				<th>세일가</th>
				<td><input type="text" name="prod_sale"
					value="${prod.prod_sale}" /><span class="error">${error.prod_sale}</span></td>
			</tr>
			<tr>
				<th>상품개요</th>
				<td><input type="text" name="prod_outline"
					value="${prod.prod_outline}" /><span class="error">${error.prod_outline}</span></td>
			</tr>
			<tr>
				<th>상세정보</th>
				<td><input type="text" name="prod_detail"
					value="${prod.prod_detail}" /><span class="error">${error.prod_detail}</span></td>
			</tr>
			<tr>
				<th>이미지</th>
				<td><input type="file" name="prod_image" />
					<span class="error">${error["prod_image"]}</span>
				</td>
			</tr>
			<tr>
				<th>재고량</th>
				<td><input type="text" name="prod_totalstock"
					value="${prod.prod_totalstock}" /><span class="error">${error.prod_totalstock}</span></td>
			</tr>
			<tr>
				<th>입고일</th>
				<td><input type="date" name="prod_insdate"
					value="${prod.prod_insdate}" /><span class="error">${error.prod_insdate}</span></td>
			</tr>
			<tr>
				<th>적정재고</th>
				<td><input type="text" name="prod_properstock"
					value="${prod.prod_properstock}" /><span class="error">${error.prod_properstock}</span></td>
			</tr>
			<tr>
				<th>상품크기</th>
				<td><input type="text" name="prod_size"
					value="${prod.prod_size}" /><span class="error">${error.prod_size}</span></td>
			</tr>
			<tr>
				<th>상품색상</th>
				<td><input type="text" name="prod_color"
					value="${prod.prod_color}" /><span class="error">${error.prod_color}</span></td>
			</tr>
			<tr>
				<th>배송방법</th>
				<td><input type="text" name="prod_delivery"
					value="${prod.prod_delivery}" /><span class="error">${error.prod_delivery}</span></td>
			</tr>
			<tr>
				<th>단위</th>
				<td><input type="text" name="prod_unit"
					value="${prod.prod_unit}" /><span class="error">${error.prod_unit}</span></td>
			</tr>
			<tr>
				<th>입고량</th>
				<td><input type="text" name="prod_qtyin"
					value="${prod.prod_qtyin}" /><span class="error">${error.prod_qtyin}</span></td>
			</tr>
			<tr>
				<th>판매량</th>
				<td><input type="text" name="prod_qtysale"
					value="${prod.prod_qtysale}" /><span class="error">${error.prod_qtysale}</span></td>
			</tr>
			<tr>
				<th>마일리지</th>
				<td><input type="text" name="prod_mileage"
					value="${prod.prod_mileage}" /><span class="error">${error.prod_mileage}</span></td>
			</tr>
			
			<tr>
				<td colspan="2"><input type="submit" value="전송" /> 
				<input type="reset" value="취소" /> <input type="button" value="목록으로"
					onclick="Location.href='${pageContext.request.contextPath}/prod/prodList.do';" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>