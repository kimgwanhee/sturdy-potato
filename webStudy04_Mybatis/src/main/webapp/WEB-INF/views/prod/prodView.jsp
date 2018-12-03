<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@page import="kr.or.ddit.vo.ProdVO"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

<script src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script
   src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
   integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
   crossorigin="anonymous"></script>
<script
   src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
   integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
   crossorigin="anonymous"></script>
</head>
<body>
<table>
	<thead>
		<tr>
			<th>상품코드</th>
		</tr>
	</thead>
	<tbody>
			<tr>
				<th>PROD_ID</th>
				<td><input type="text" name="prod_id" value="${prod.prod_id}" />
					</td>
			</tr>
			<tr>
				<th>PROD_NAME</th>
				<td><input type="text" name="prod_name" value="${prod.prod_name}" />
					</td>
			</tr>
			<tr>
				<th>분류명</th>
				<td><input type="text" name="prod_lgu" value="${prod.prod_lgu}" />
					</td>
			</tr>
			<tr>
				<th>거래처정보</th>
				<td>
					<table>
						<thead>
							<tr>
								<th>거래처명</th>
								<th>소재지</th>
								<th>담당자명</th>
								<th>연락처</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>${prod.buyer.buyer_name}</td>
								<td>${prod.buyer.buyer_add1}</td>
								<td>${prod.buyer.buyer_charger}</td>
								<td>${prod.buyer.buyer_comtel}</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<th>PROD_COST</th>
				<td><input type="text" name="prod_cost" value="${prod.prod_cost}"/>
					</td>
			</tr>
			<tr>
				<th>PROD_PRICE</th>
				<td><input type="text" name="prod_price" value="${prod.prod_price}" />
					</td>
			</tr>
			<tr>
				<th>PROD_SALE</th>
				<td><input type="text" name="prod_sale" value="${prod.prod_sale}" />
					</td>
			</tr>
			<tr>
				<th>PROD_OUTLINE</th>
				<td><input type="text" name="prod_outline" value="${prod.prod_outline}" />
					</td>
			</tr>
			<tr>
				<th>PROD_DETAIL</th>
				<td><input type="text" name="prod_detail" value="${prod.prod_detail}" />
					</td>
			</tr>
			<tr>
				<th>PROD_TOTALSTOCK</th>
				<td><input type="text" name="prod_totalstock" value="${prod.prod_totalstock}" />
					</td>
			</tr>
			<tr>
				<th>PROD_INSDATE</th>
				<td><input type="text" name="prod_insdate" value="${prod.prod_insdate}" />
					</td>
			</tr>
			<tr>
				<th>PROD_PROPERSTOCK</th>
				<td><input type="text" name="prod_properstock"	 value="${prod.prod_properstock}" />
					</td>
			</tr>
			<tr>
				<th>PROD_SIZE</th>
				<td><input type="text" name="prod_size"	 value="${prod.prod_size}" />
					</td>
			</tr>
			<tr>
				<th>PROD_COLOR</th>
				<td><input type="text" name="prod_color"	 value="${prod.prod_color}" />
					</td>
			</tr>
			<tr>
				<th>PROD_DELIVERY</th>
				<td><input type="text" name="prod_delivery"	 value="${prod.prod_delivery}" />
					</td>
			</tr>
			<tr>
				<th>PROD_UNIT</th>
				<td><input type="text" name="prod_unit"	 value="${prod.prod_unit}" />
					</td>
			</tr>
			<tr>
				<th>PROD_QTYIN</th>
				<td><input type="text" name="prod_qtyin"	 value="${prod.prod_qtyin}" />
					</td>
			</tr>
			<tr>
				<th>PROD_QTYSALE</th>
				<td><input type="text" name="prod_qtysale"	 value="${prod.prod_qtysale}" />
					</td>
			</tr>
			<tr>
				<th>PROD_MILEAGE</th>
				<td><input type="text" name="prod_mileage"	 value="${prod.prod_mileage}" />
					</td>
			</tr>
			<tr>
				<th>PROD_IMAGE</th>
				<td>
				<img src="<c:url value = '/prodImages/${prod.prod_img}'/>"/>
				</td>
			</tr>
			
		</tbody>
	</table>
	<c:set var="authMember" value='${not empty sessionScope.authMember and "ROLE_ADMIN" eq sessionScope.authMember.mem_auth }'/>
			<input type="button" value="상품수정" onclick='location.href="${pageContext.request.contextPath}/prod/prodUpdate.do?what=${prod.prod_id}"'/>
<%-- 		'location.href="${pageContext.request.contextPath}/prod/prodUpdate.do?what="+"${prod.prod_id}"' --%>
<c:if test="${authMember }">
	<h4>구매자 목록</h4>
	<table>
		<thead>
			<tr>
				<th>회원아이디</th>
				<th>회원명</th>
				<th>주소</th>
				<th>연락처</th>
				<th>이메일</th>
			</tr>
		</thead>
		<tbody>
		<c:choose>
			<c:when test="${not empty prod.customers}">
				<c:forEach items="${prod.customers }" var="tmp">
					<tr>
						<td>${tmp.mem_id}</td>
						<td>${tmp.mem_name}</td>
						<td>${tmp.address}</td>
						<td>${tmp.mem_hp}</td>
						<td>${tmp.mem_mail}</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td colspan = "5">구매자가 없음.</td>
				</tr>
			
			</c:otherwise>
		</c:choose>
		</tbody>
	</table>
</c:if>
</body>
</html>