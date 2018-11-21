<%@page import="java.util.Map"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.util.Objects"%>
<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>

<jsp:useBean id="member" class="kr.or.ddit.vo.MemberVO" scope="request"></jsp:useBean>
<jsp:useBean id="error" class="java.lang.String" scope="request"></jsp:useBean>
<jsp:useBean id="errors" class="java.util.LinkedHashMap" scope="request"></jsp:useBean>

<%
   
%>
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
      <%
         String message = (String)request.getAttribute("message");
         if(StringUtils.isNotBlank(message)){
      %>
            alert("<%=message%>");      
      <%
         }
      %>
      
      $("[type='date']").datepicker({
         dateFormat : "yy-mm-dd"
      });

   });
</script>
</head>
<body>
   <form action="<%=request.getContextPath()%>/member/memberInsert.jsp"
      method="post">
      <table>

         <tr>
            <th>회원아이디</th>
            <td>
               <input type="text" name="mem_id" value="<%=member.getMem_id()%>" /> 
            </td><td>   
               <span class="error"><%=errors.get("mem_id")%></span>
            </td>
         </tr>

         <tr>
            <th>비밀번호</th>
            <td>
               <input type="text" name="mem_pass" value="<%=member.getMem_pass()%>" /> 
            </td><td>   
               <span class="error"><%=errors.get("mem_pass")%></span>
            </td>
         </tr>

         <tr>
            <th>회원명</th>
            <td>
               <input type="text" name="mem_name" value="<%=member.getMem_name()%>" /> 
            </td><td>   
               <span class="error"><%=errors.get("mem_name")%></span>
            </td>
         </tr>

         <tr>
            <th>주민번호1</th>
            <td>
               <input type="text" name="mem_regno1" value="<%=member.getMem_regno1()%>" /> 
            </td><td>   
               <span class="error"><%=errors.get("mem_regno1")%></span>
            </td>
         </tr>

         <tr>
            <th>주민번호2</th>
            <td>
               <input type="text" name="mem_regno2" value="<%=member.getMem_regno2()%>" /> 
            </td><td>   
               <span class="error"><%=errors.get("mem_regno2")%></span>
            </td>
         </tr>

         <tr>
            <th>생일</th>
            <td>
               <input type="date" name="mem_bir" value="<%=member.getMem_bir()%>" /> 
            </td><td>   
               <span class="error"><%=errors.get("mem_bir")%></span>
            </td>
         </tr>

         <tr>
            <th>우편번호</th>
            <td>
               <input type="text" name="mem_zip" value="<%=member.getMem_zip()%>" /> 
            </td><td>   
               <span class="error"></span><%=errors.get("mem_zip")%>
            </td>
         </tr>

         <tr>
            <th>주소1</th>
            <td>
               <input type="text" name="mem_add1" value="<%=member.getMem_add1()%>" /> 
            </td><td>
               <span class="error"><%=errors.get("mem_add1")%></span>
            </td>
         </tr>

         <tr>
            <th>주소2</th>
            <td>
               <input type="text" name="mem_add2" value="<%=member.getMem_add2()%>" />
            </td><td>    
               <span class="error"><%=errors.get("mem_add2")%></span>
            </td>
         </tr>

         <tr>
            <th>집번호</th>
            <td>
               <input type="text" name="mem_hometel" value="<%=member.getMem_hometel()%>" /> 
            </td><td>   
               <span class="error"><%=errors.get("mem_hometel")%></span>
            </td>
         </tr>

         <tr>
            <th>회사번호</th>
            <td>
               <input type="text" name="mem_comtel" value="<%=member.getMem_comtel()%>" /> 
            </td><td>   
               <span class="error"><%=errors.get("mem_comtel")%></span>
            </td>
         </tr>

         <tr>
            <th>휴대폰</th>
            <td>
               <input type="text" name="mem_hp" value="<%=member.getMem_hp()%>" /> 
            </td><td>   
               <span class="error"></span><%=errors.get("mem_hp")%>
            </td>
         </tr>

         <tr>
            <th>이메일</th>
            <td>
               <input type="text" name="mem_mail" value="<%=member.getMem_mail()%>" /> 
            </td><td>   
               <span class="error"><%=errors.get("mem_mail")%></span>
            </td>
         </tr>

         <tr>
            <th>직업</th>
            <td>
               <input type="text" name="mem_job" value="<%=member.getMem_job()%>" /> 
            </td><td>   
               <span class="error"><%=errors.get("mem_job")%></span>
            </td>
         </tr>

         <tr>
            <th>취미</th>
            <td>
               <input type="text" name="mem_like" value="<%=member.getMem_like()%>" /> 
            </td><td>   
               <span class="error"><%=errors.get("mem_like")%></span>
            </td>
         </tr>

         <tr>
            <th>기념일</th>
            <td>
               <input type="text" name="mem_memorial" value="<%=member.getMem_memorial()%>" /> 
            </td><td>   
               <span class="error"><%=errors.get("mem_memorial")%></span>
            </td>
         </tr>

         <tr>
            <th>기념일자</th>
            <td>
               <input type="date" name="mem_memorialday" value="<%=member.getMem_memorialday()%>" /> 
            </td><td>   
               <span class="error"><%=errors.get("mem_memorialday")%></span>
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