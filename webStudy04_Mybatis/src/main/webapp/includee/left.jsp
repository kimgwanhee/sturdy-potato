<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">
	function goIndex(command){
		var form = document.leftForm;
		form.command.value=command;
		form.submit();		
	}

</script>

<ul>
	<li><a href="javascript:goIndex('gugudan');">구구단</a></li>
	<li><a href="javascript:goIndex('music');">가사파일</a></li>
	<li><a href="javascript:goIndex('calender');">달력</a></li>
	<li><a href="javascript:goIndex('image');">이미지뷰어</a></li>
	<li>누적 방문자수 : ${applicationScope.usercount }</li>
	<li>
		접속자 리스트<br/>
		<c:forEach items="${applicationUsers}" var="user">
			${user.mem_name }<br/>
		</c:forEach>
		
	</li>
</ul>

<form name='leftForm'action ="${cPath }/" method="post">
	<input name="command" value="" type="hidden" />
</form>
