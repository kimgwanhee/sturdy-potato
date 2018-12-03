<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Objects"%>
<%@page import="kr.or.ddit.vo.BuyerVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<script src="${pageContext.request.contextPath }/js/jquery-3.3.1.min.js"></script>
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
<jsp:useBean id="buyer" class="kr.or.ddit.vo.BuyerVO" scope="request"></jsp:useBean>

<body>
 <form method="post" action="${pageContext.request.contextPath }/buyer/buyerInsert.do">
      <table>
         <tr>
            <th>BUYER_NAME</th>
            <td>
               <input type="text" name="buyer_name" value="${buyer.buyer_name}" /> 
            </td>
            <td>   
               <span class="error">${errors.buyer_name}</span>
            </td>
         </tr>

         <tr>
            <th>BUYER_LGU</th>
            <td>
               <select name="buyer_lgu" >
	               <c:forEach items="${lprodList}" var="lprod">
	           			<option value="${lprod.LPROD_GU }">${lprod.LPROD_NM}</option>
	               </c:forEach>
               </select>
            </td>
            <td>   
               <span class="error">${errors.buyer_lgu}</span>
            </td>
         </tr>

         <tr>
            <th>BUYER_BANK</th>
            <td>
               <input type="text" name="buyer_bank" value="${buyer.buyer_bank}" /> 
            </td>
            <td>   
               <span class="error">${errors.buyer_bank}</span>
            </td>
         </tr>

         <tr>
            <th>BUYER_BANKNO</th>
            <td>
               <input type="text" name="buyer_bankno" value="${buyer.buyer_bankno}" /> 
            </td>
            <td>   
               <span class="error">${errors.buyer_bankno}</span>
            </td>
         </tr>

         <tr>
            <th>BUYER_BANKNAME</th>
            <td>
               <input type="text" name="buyer_bankname" value="${buyer.buyer_bankname}" /> 
            </td>
            <td>   
               <span class="error">${errors.buyer_bankname}</span>
            </td>
         </tr>

         <tr>
            <th>BUYER_ZIP</th>
            <td>
               <input type="text" name="buyer_zip" value="${buyer.buyer_zip}" /> 
            </td>
            <td>   
               <span class="error">${errors.buyer_zip}</span>
            </td>
         </tr>

         <tr>
            <th>BUYER_ADD1</th>
            <td>
               <input type="text" name="buyer_add1" value="${buyer.buyer_add1}" /> 
            </td>
            <td>
               <span class="error">${errors.buyer_add1}</span>
            </td>
         </tr>

         <tr>
            <th>BUYER_ADD2</th>
            <td>
               <input type="text" name="buyer_add2" value="${buyer.buyer_add2}" />
            </td>
            <td>    
               <span class="error">${errors.buyer_add2}</span>
            </td>
         </tr>

         <tr>
            <th>BUYER_COMTEL</th>
            <td>
               <input type="text" name="buyer_comtel" value="${buyer.buyer_comtel}" /> 
            </td>
            <td>   
               <span class="error">${errors.buyer_comtel}</span>
            </td>
         </tr>

         <tr>
            <th>BUYER_FAX</th>
            <td>
               <input type="text" name="buyer_fax" value="${buyer.buyer_fax}" /> 
            </td>
            <td>   
               <span class="error">${errors.buyer_fax}</span>
            </td>
         </tr>

         <tr>
            <th>BUYER_MAIL</th>
            <td>
               <input type="text" name="buyer_mail" value="${buyer.buyer_mail}" /> 
            </td>
            <td>   
               <span class="error">${errors.buyer_mail}</span>
            </td>
         </tr>

         <tr>
            <th>BUYER_CHARGER</th>
            <td>
               <input type="text" name="buyer_charger" value="${buyer.buyer_charger}" /> 
            </td>
            <td>   
               <span class="error">${errors.buyer_charger}</span>
            </td>
         </tr>

         
          <tr>
            <td colspan="2"><input type="submit" value="등록" />
             <input type="reset" value="취소" /></td>
         </tr>
      </table>
   </form>
</body>
</html>