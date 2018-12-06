<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
	integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
	integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
	crossorigin="anonymous"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>
<script type="text/javascript" src="<c:url value='/js/replyProcess.js'/>"></script>
<script type="text/javascript">
	$.getContextPath = function(){
		return "${pageContext.request.contextPath}";
	}
</script>
</head>
<body>
	<div class="container">
		<table class="table">
			<thead class="thead-dark">
				<tr>
					<th>항목</th>
					<th>정보</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th>BO_NO</th>
					<td>${board.bo_no}</td>
				</tr>
				<tr>
					<th>BO_WRITER</th>
					<td>${board.bo_writer}</td>
				</tr>

				<tr>
					<th>BO_PASS</th>
					<td>${board.bo_pass}</td>
				</tr>
				<tr>
					<th>BO_IP</th>
					<td>${board.bo_ip}</td>
				</tr>
				<tr>
					<th>BO_MAIL</th>
					<td>${board.bo_mail}</td>
				</tr>
				<tr>
					<th>BO_TITLE</th>
					<td>${board.bo_title}</td>
				</tr>
				<tr>
					<th>BO_CONTENT</th>
					<td>${board.bo_content}</td>
				</tr>
				<tr>
					<th>BO_DATE</th>
					<td>${board.bo_date}</td>
				</tr>
				<tr>
					<th>BO_HIT</th>
					<td>${board.bo_hit}</td>
				</tr>
				<tr>
					<th>BO_RCMD</th>
					<td>${board.bo_rcmd}</td>
				</tr>
			</tbody>
		</table>

		<table class="table">
			<thead class="thead-dark">
				<tr>
					<th>7</th>
					<th>번</th>
					<th>째</th>
					<th>그</th>
					<th>림</th>
					<th>자</th>
					<th>?</th>
				</tr>
			</thead>
			<tbody id="listBody">
				<c:choose>
					<c:when test="${not empty board.replyList}">
						<c:forEach items="${board.replyList }" var="replyVO">
							<tr>
								<td>${replyVO.rep_no }</td>
								<td>${replyVO.bo_no }</td>
								<td>${replyVO.rep_writer }</td>
								<td>${replyVO.rep_ip }</td>
								<td>${replyVO.rep_pass }</td>
								<td>${replyVO.rep_content }</td>
								<td>${replyVO.rep_date }</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="7">정보 없음</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
			<tfoot>
				<tr>

					<td colspan="7">
						<div class="d-flex justify-content-center">
							<nav aria-label="Page navigation example" id="pagingArea">
								${pagingVO.pagingHTML}</nav>
						</div>
					</td>
				</tr>


				<tr>
					<td colspan="2"><input type="button" value="뒤로"
						onclick="history.back();" /> <input type="reset" value="취소" /> <%-- 				<c:if test="${mutable}"> --%>
						<input type="submit" value="수정" /> <input type="button" value="삭제"
						id="delBtn" /> <%-- 					</c:if> --%></td>
				</tr>
			</tfoot>
		</table>
		<form action="${pageContext.request.contextPath}/reply/replyInsert.do" name="replyForm" method="post">
			<div>
				<input type="hidden" name="rep_no" /> 
				<input type="hidden" name="bo_no" value="${board.bo_no}" /> 
				<input type="hidden" name="rep_ip" value="${pageContext.request.remoteAddr }" />
				작성자 : <input type="text" name="rep_writer" />
				비밀번호 : <input type="text" name="rep_pass" />
				내용 : <input type="text" name="rep_content" />
				<input type="submit" value="등록" /></td>
			</div>
		</form>
	</div>
	<br>

	<!-- Modal -->
	<div class="modal fade" id="replyDeleteModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	      	<form onsubmit="return false" id="modalForm">
<!-- 	      	onsubmit는 절대 전송하지않겠다 전송용x , 그냥 리셋시킬용-->
		      <input type="hidden"  id="bo_no" value="${board.bo_no}"/>
		      <input type="text" id="rep_no" />
		      	비밀번호 : <input type="text" id="rep_pass" />
		    </form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" id="modalBtn">삭제</button>
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
	      </div>
	    </div>
	  </div>
	</div>
	
<script type="text/javascript">
	function paging(page){
		pagingReply(page, ${board.bo_no});
	}
	paging(1);
</script>
</body>
</html>