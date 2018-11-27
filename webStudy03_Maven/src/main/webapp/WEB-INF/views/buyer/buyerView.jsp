<%@page import="kr.or.ddit.vo.BuyerVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

</head>
<%
	BuyerVO buyer = (BuyerVO)request.getAttribute("buyer");
%>
<body>
<h4>Buyer 상세정보</h4>
<form name="" method="post" action="<%=request.getContextPath()%>/buyer/buyerUpdate.do">
	<table class="table">
		<thead class="thead-dark">
		
		</thead>
		<tbody>
			<tr>
				<th>BUYER_ID</th>
				<td><input type="text" readonly="readonly" name="buyer_id" value="<%=buyer.getBuyer_id()%>" />
					</td>
			</tr>
			<tr>
				<th>BUYER_NAME</th>
				<td><input type="text" name="buyer_name" value="<%=buyer.getBuyer_name()%>" />
					</td>
			</tr>
			<tr>
				<th>BUYER_LGU</th>
				<td><input type="text" readonly="readonly" name="buyer_lgu" value="<%=buyer.getBuyer_lgu()%>" />
					</td>
			</tr>
			<tr>
				<th>BUYER_BANK</th>
				<td><input type="text" name="buyer_bank" value="<%=buyer.getBuyer_bank()%>"/>
					</td>
			</tr>
			<tr>
				<th>BUYER_BANKNO</th>
				<td><input type="text" name="buyer_bankno" value="<%=buyer.getBuyer_bankno()%>"/>
					</td>
			</tr>
			<tr>
				<th>BUYER_BANKNAME</th>
				<td><input type="text" name="buyer_bankname" value="<%=buyer.getBuyer_bankname()%>" />
					</td>
			</tr>
			<tr>
				<th>BUYER_ZIP</th>
				<td><input type="text" name="buyer_zip" value="<%=buyer.getBuyer_zip()%>" />
					</td>
			</tr>
			<tr>
				<th>BUYER_ADD1</th>
				<td><input type="text" name="buyer_add1" value="<%=buyer.getBuyer_add1()%>" />
					</td>
			</tr>
			<tr>
				<th>BUYER_ADD2</th>
				<td><input type="text" name="buyer_add2" value="<%=buyer.getBuyer_add2()%>" />
					</td>
			</tr>
			<tr>
				<th>BUYER_COMTEL</th>
				<td><input type="text" name="buyer_comtel" value="<%=buyer.getBuyer_comtel()%>" />
					</td>
			</tr>
			<tr>
				<th>BUYER_FAX</th>
				<td><input type="text" name="buyer_fax" value="<%=buyer.getBuyer_fax()%>" />
					</td>
			</tr>
			<tr>
				<th>BUYER_MAIL</th>
				<td><input type="text" name="buyer_mail" value="<%=buyer.getBuyer_mail()%>" />
					</td>
			</tr>
			<tr>
				<th>BUYER_CHARGER</th>
				<td><input type="text" name="buyer_charger"	 value="<%=buyer.getBuyer_charger()%>" />
					</td>
			</tr>
			
		</tbody>
		<tfoot>
			<tr>
				<td colspan="2">
					<input type="button" value="뒤로" onclick="history.back();"/>
					<input type="reset" value="취소"/>
					<input type="submit" value="수정"/>
					<input type="button" value="삭제" id="delBtn"/>
				</td>
			</tr>
		</tfoot>
	</table>
</form>
</body>
</html>