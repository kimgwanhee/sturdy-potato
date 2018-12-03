<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%> 
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
<script type="text/javascript">
   $(function() {
	   <c:if test="${not empty message}">
	   		alert("${message}");
	   </c:if>
      
      $("[type='date']").datepicker({
         dateFormat : "yy-mm-dd"
      });
   });
</script>
</head>
<body>
   <form
      method="post" enctype="multipart/form-data">
      <table>

         <tr>
            <th>회원아이디</th>
            <td>
               <input type="text" name="mem_id" value="${member.mem_id}" /> 
            </td><td>   
               <span class="error">${errors.mem_id}</span>
            </td>
         </tr>

         <tr>
            <th>비밀번호</th>
            <td>
               <input type="text" name="mem_pass" value="${member.mem_pass}" /> 
            </td><td>   
               <span class="error">${member.mem_pass}</span>
            </td>
         </tr>

         <tr>
            <th>회원명</th>
            <td>
               <input type="text" name="mem_name" value="${member.mem_name}" /> 
            </td><td>   
               <span class="error">${member.mem_name}</span>
            </td>
         </tr>

         <tr>
            <th>주민번호1</th>
            <td>
               <input type="text" name="mem_regno1" value="${member.mem_regno1}" /> 
            </td><td>   
               <span class="error">${member.mem_regno1}</span>
            </td>
         </tr>

         <tr>
            <th>주민번호2</th>
            <td>
               <input type="text" name="mem_regno2" value="${member.mem_regno2}" /> 
            </td><td>   
               <span class="error">${member.mem_regno2}</span>
            </td>
         </tr>

         <tr>
            <th>생일</th>
            <td>
               <input type="date" name="mem_bir" value="${member.mem_bir}" /> 
            </td><td>   
               <span class="error">${member.mem_bir}</span>
            </td>
         </tr>

         <tr>
            <th>우편번호</th>
            <td>
               <input type="text" name="mem_zip" value="${member.mem_zip}" /> 
            </td><td>   
               <span class="error">${member.mem_zip}</span>
            </td>
         </tr>

         <tr>
            <th>주소1</th>
            <td>
               <input type="text" name="mem_add1" value="${member.mem_add1}" /> 
            </td><td>
               <span class="error">${member.mem_add1}</span>
            </td>
         </tr>

         <tr>
            <th>주소2</th>
            <td>
               <input type="text" name="mem_add2" value="${member.mem_add2}" />
            </td><td>    
               <span class="error">${member.mem_add2}</span>
            </td>
         </tr>

         <tr>
            <th>집번호</th>
            <td>
               <input type="text" name="mem_hometel" value="${member.mem_hometel}" /> 
            </td><td>   
               <span class="error">${member.mem_hometel}</span>
            </td>
         </tr>

         <tr>
            <th>회사번호</th>
            <td>
               <input type="text" name="mem_comtel" value="${member.mem_comtel}" /> 
            </td><td>   
               <span class="error">${member.mem_comtel}</span>
            </td>
         </tr>

         <tr>
            <th>휴대폰</th>
            <td>
               <input type="text" name="mem_hp" value="${member.mem_hp}" /> 
            </td><td>   
               <span class="error">${errors.mem_hp}</span>
            </td>
         </tr>

         <tr>
            <th>이메일</th>
            <td>
               <input type="text" name="mem_mail" value="${member.mem_mail}" /> 
            </td><td>   
               <span class="error">${errors.mem_mail}</span>
            </td>
         </tr>

         <tr>
            <th>직업</th>
            <td>
               <input type="text" name="mem_job" value="${member.mem_job}" /> 
            </td><td>   
               <span class="error">${errors.mem_job}</span>
            </td>
         </tr>

         <tr>
            <th>취미</th>
            <td>
               <input type="text" name="mem_like" value="${member.mem_like}" /> 
            </td><td>   
               <span class="error">${errors.mem_like}</span>
            </td>
         </tr>

         <tr>
            <th>기념일</th>
            <td>
               <input type="text" name="mem_memorial" value="${member.mem_memorial}" /> 
            </td><td>   
               <span class="error">${errors.mem_memorial}</span>
            </td>
         </tr>

         <tr>
            <th>기념일자</th>
            <td>
               <input type="date" name="mem_memorialday" value="${member.mem_memorialday}" /> 
            </td><td>   
               <span class="error">${errors.mem_memorialday}</span>
            </td>
         </tr>
         <tr>
            <td colspan="2"><input type="submit" value="등록" /> <input
               type="reset" value="취소" /></td>
         </tr>
      </table>
   </form>
</body>
</html>