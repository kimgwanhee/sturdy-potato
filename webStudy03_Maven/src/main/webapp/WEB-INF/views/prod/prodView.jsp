<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@page import="kr.or.ddit.vo.ProdVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%
	ProdVO prod =  (ProdVO)request.getAttribute("prod");
%>
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
				<td><input type="text" name="prod_id" value="<%=prod.getProd_id()%>" />
					</td>
			</tr>
			<tr>
				<th>PROD_NAME</th>
				<td><input type="text" name="prod_name" value="<%=prod.getProd_name()%>" />
					</td>
			</tr>
			<tr>
				<th>분류명</th>
				<td><input type="text" name="prod_lgu" value="<%=prod.getProd_lgu()%>" />
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
								<td><%=prod.getBuyer().getBuyer_name() %></td>
								<td><%=prod.getBuyer().getBuyer_add1() %></td>
								<td><%=prod.getBuyer().getBuyer_charger() %></td>
								<td><%=prod.getBuyer().getBuyer_comtel()%></td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<th>PROD_COST</th>
				<td><input type="text" name="prod_cost" value="<%=prod.getProd_cost()%>"/>
					</td>
			</tr>
			<tr>
				<th>PROD_PRICE</th>
				<td><input type="text" name="prod_price" value="<%=prod.getProd_price()%>" />
					</td>
			</tr>
			<tr>
				<th>PROD_SALE</th>
				<td><input type="text" name="prod_sale" value="<%=prod.getProd_sale()%>" />
					</td>
			</tr>
			<tr>
				<th>PROD_OUTLINE</th>
				<td><input type="text" name="prod_outline" value="<%=prod.getProd_outline()%>" />
					</td>
			</tr>
			<tr>
				<th>PROD_DETAIL</th>
				<td><input type="text" name="prod_detail" value="<%=prod.getProd_detail()%>" />
					</td>
			</tr>
			<tr>
				<th>PROD_TOTALSTOCK</th>
				<td><input type="text" name="prod_totalstock" value="<%=prod.getProd_totalstock()%>" />
					</td>
			</tr>
			<tr>
				<th>PROD_INSDATE</th>
				<td><input type="text" name="prod_insdate" value="<%=prod.getProd_insdate()%>" />
					</td>
			</tr>
			<tr>
				<th>PROD_PROPERSTOCK</th>
				<td><input type="text" name="prod_properstock"	 value="<%=prod.getProd_properstock()%>" />
					</td>
			</tr>
			<tr>
				<th>PROD_SIZE</th>
				<td><input type="text" name="prod_size"	 value="<%=prod.getProd_size()%>" />
					</td>
			</tr>
			<tr>
				<th>PROD_COLOR</th>
				<td><input type="text" name="prod_color"	 value="<%=prod.getProd_color()%>" />
					</td>
			</tr>
			<tr>
				<th>PROD_DELIVERY</th>
				<td><input type="text" name="prod_delivery"	 value="<%=prod.getProd_delivery()%>" />
					</td>
			</tr>
			<tr>
				<th>PROD_UNIT</th>
				<td><input type="text" name="prod_unit"	 value="<%=prod.getProd_unit()%>" />
					</td>
			</tr>
			<tr>
				<th>PROD_QTYIN</th>
				<td><input type="text" name="prod_qtyin"	 value="<%=prod.getProd_qtyin()%>" />
					</td>
			</tr>
			<tr>
				<th>PROD_QTYSALE</th>
				<td><input type="text" name="prod_qtysale"	 value="<%=prod.getProd_qtysale()%>" />
					</td>
			</tr>
			<tr>
				<th>PROD_MILEAGE</th>
				<td><input type="text" name="prod_mileage"	 value="<%=prod.getProd_mileage()%>" />
					</td>
			</tr>
		</tbody>
	</table>
	<%
		boolean authorized = false;
		MemberVO authMember = (MemberVO) session.getAttribute("authMember");
		authorized = authMember!=null && "ROLE_ADMIN".equals(authMember.getMem_auth());
		if(authorized){
	%>
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
		<%
			List<MemberVO> customers = prod.getCustomers();
			if(customers != null && customers.size() > 0 ){
				for(MemberVO tmp : customers){
					%>
					<tr>
						<td><%=tmp.getMem_id() %></td>
						<td><%=tmp.getMem_name() %></td>
						<td><%=tmp.getAddress() %></td>
						<td><%=tmp.getMem_hp() %></td>
						<td><%=tmp.getMem_mail() %></td>
					</tr>
					<%
				}
			}else{
				%>
				<tr>
					<td colspan = "5">구매자가 없음.</td>
				</tr>
				<%
			}
		%>
		</tbody>
	</table>
	<%
	}
	%>
</body>
</html>