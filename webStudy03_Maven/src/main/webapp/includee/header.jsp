<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 일부 jsp므로 위 지우기 -->
<ul>
	<li><a href="<%=request.getContextPath() %>/member/memberList.do">회원관리</a></li>
	<li><a href = "<%=request.getContextPath()%>/prod/prodList.do">상품관리</a></li>
	<li><a href="<%=request.getContextPath() %>/buyer/buyerList.do">거래처관리</a></li>
	<li>방명록</li>
	<li>자유게시판</li>
</ul>

</body>
</html>