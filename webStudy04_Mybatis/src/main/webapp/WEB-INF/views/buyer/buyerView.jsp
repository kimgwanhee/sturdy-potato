<%@page import="kr.or.ddit.vo.BuyerVO"%>
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
</head>
<body>

<c:set var="mutable" value="false"/>
<c:if test="${not empty sessionScope.authMember and 'ROLE_ADMIN' eq sessionScope.authMember.mem_auth }">
		<c:set var="mutable" value="true"/>
</c:if>

<h4>Buyer 상세정보</h4>
<form name="" method="post" action="${pageContext.request.contextPath}/buyer/buyerUpdate.do">
	<table class="table">
		<thead class="thead-dark">
		
		</thead>
		<tbody>
			<tr>
				<th>BUYER_ID</th>
				<td><input type="text" readonly="readonly" name="buyer_id" value="${buyer.buyer_id}" />
					</td>
			</tr>
			<tr>
				<th>BUYER_NAME</th>
				<td><input type="text" name="buyer_name" value="${buyer.buyer_name}"/>
					<span class="error">${errors.buyer_name}</span> 
				</td>
			</tr>
			
			<tr>
				<th>BUYER_LGU</th>
				<td><input type="text" readonly="readonly" name="buyer_lgu" value="${buyer.buyer_lgu}" />
					<span class="error">${errors.buyer_lgu}</span> 
				</td>
			</tr>
			<tr>
				<th>BUYER_BANK</th>
				<td><input type="text" name="buyer_bank" value="${buyer.buyer_bank}"/>
					<span class="error">${errors.buyer_bank}</span>
				</td>
			</tr>
			<tr>
				<th>BUYER_BANKNO</th>
				<td><input type="text" name="buyer_bankno" value="${buyer.buyer_bankno}"/>
					<span class="error">${errors.buyer_bankno}</span>
				</td>
			</tr>
			<tr>
				<th>BUYER_BANKNAME</th>
				<td><input type="text" name="buyer_bankname" value="${buyer.buyer_bankname}" />
				<span class="error">${errors.buyer_bankname}</span>
				</td>
			</tr>
			<tr>
				<th>BUYER_ZIP</th>
				<td><input type="text" name="buyer_zip" value="${buyer.buyer_zip}" />
				<span class="error">${errors.buyer_zip}</span>
				</td>
			</tr>
			<tr>
				<th>BUYER_ADD1</th>
				<td><input type="text" name="buyer_add1" value="${buyer.buyer_add1}" />
				<span class="error">${errors.buyer_add1}</span>
				</td>
			</tr>
			<tr>
				<th>BUYER_ADD2</th>
				<td><input type="text" name="buyer_add2" value="${buyer.buyer_add2}" />
					<span class="error">${errors.buyer_add2}</span>
				</td>
			</tr>
			<tr>
				<th>BUYER_COMTEL</th>
				<td><input type="text" name="buyer_comtel" value="${buyer.buyer_comtel}" />
					<span class="error">${errors.buyer_comtel}</span>
				</td>
			</tr>
			<tr>
				<th>BUYER_FAX</th>
				<td><input type="text" name="buyer_fax" value="${buyer.buyer_fax}" />
					<span class="error">${errors.buyer_fax}</span>
				</td>
			</tr>
			<tr>
				<th>BUYER_MAIL</th>
				<td><input type="text" name="buyer_mail" value="${buyer.buyer_mail}" />
					<span class="error">${errors.buyer_mail}</span>
				</td>
			</tr>
			<tr>
				<th>BUYER_CHARGER</th>
				<td><input type="text" name="buyer_charger"	 value="${buyer.buyer_charger}" />
					<span class="error">${errors.buyer_charger}</span>
				</td>
			</tr>
			
		</tbody>
		<tfoot>
			<tr>
				<td colspan="2">
					<input type="button" value="뒤로" onclick="history.back();"/>
					<input type="reset" value="취소" />
					<c:if test="${mutable}">
						<input type="submit" value="수정"/>
						<input type="button" value="삭제" id="delBtn"/>
					</c:if>
				</td>
			</tr>
		</tfoot>
	</table>
</form>

<table class="table">
		<thead class="thead-dark">
		<tr>
			<th>PROD_ID</th>
			<th>PROD_NAME</th>
			<th>PROD_LGU</th>
			<th>PROD_OUTLINE</th>
		</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty buyer.prodList}">
					<c:forEach items="${buyer.prodList}" var="prod">
						<tr>
							<td>${prod.prod_id}</td>
							<td>${prod.prod_name}</td>
							<td>${prod.prod_lgu}</td>
							<td>${prod.prod_outline}</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="4"> 상품 없음 ! !</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
</table>
</body>
</html>