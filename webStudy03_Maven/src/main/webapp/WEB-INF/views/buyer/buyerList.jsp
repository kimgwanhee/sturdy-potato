<%@page import="kr.or.ddit.vo.BuyerVO"%>
<%@page import="java.util.List"%>
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
	List<BuyerVO> buyerList = (List<BuyerVO>)request.getAttribute("buyerList");//다운캐스팅해서 받기
%>
<body>
<h4>Buyer List</h4>
<input type="button" class="button" value="신규등록" 
			onclick="location.href='<%=request.getContextPath()%>/buyer/buyerInsert.do';"/>

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
	<tbody>
		<%
			if(buyerList.size()!=0){
				for(BuyerVO buyerVO :buyerList){
				%>
					<tr>
						<td><%=buyerVO.getBuyer_id() %></td>
						<td><a href="<%=request.getContextPath()%>/buyer/buyerView.do?who=<%=buyerVO.getBuyer_id()%>"><%=buyerVO.getBuyer_name() %></a></td>
						<td><%=buyerVO.getBuyer_zip() %></td>
						<td><%=buyerVO.getBuyer_add1()+buyerVO.getBuyer_add2()%></td>
						<td><%=buyerVO.getBuyer_comtel() %></td>
						<td><%=buyerVO.getBuyer_fax() %></td>
						<td><%=buyerVO.getBuyer_mail() %></td>
					</tr>
				<% 
				}
			}else{
			%>
				<tr>
					<h5>정보가 존재하지 않습니다.</h5>
				</tr>
			<%
			}
		 %>
	</tbody>
	<tfoot>
		<tr>
			<td>
			
			</td>
		</tr>
	</tfoot>
</table>
</body>
</html>