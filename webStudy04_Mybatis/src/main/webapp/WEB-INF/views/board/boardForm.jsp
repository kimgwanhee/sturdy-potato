<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cPath" value="${pageContext.request.contextPath }" scope="application"/>
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

<script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script
   src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
   integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
   crossorigin="anonymous"></script>
<script
   src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
   integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
   crossorigin="anonymous"></script>
   
<script type="text/javascript" src="${cPath}/js/ckeditor/ckeditor.js"></script>
   
</head>
<c:if test="${not empty message}">
	<script type="text/javascript">
		alert("${message}");
	</script>
</c:if>
<body>
	<h4>게시판 등록하기</h4>
	<form id="boardForm" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<th>제목</th>
				<td><input type="text" name="bo_title"
					value="${board.bo_title}" /><span class="error">${error.bo_title}</span></td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><input type="text" name="bo_pass"
					value="${board.bo_pass}" /><span class="error">${error.bo_pass}</span></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td><input type="text" name="bo_writer"
					value="${board.bo_writer}" /><span class="error">${error.bo_writer}</span></td>
			</tr>
			<tr>
				<th>이메일</th>
				<td><input type="text" name="bo_mail"
					value="${board.bo_mail}" /><span class="error">${error.bo_mail}</span></td>
			</tr>
			
			<tr>
				<th>파일</th>
				<td>
					<input type="file" name="bo_file"/>
					<input type="file" name="bo_file"/>
					<input type="file" name="bo_file"/>
				</td>
			</tr>
			
			<tr>
				<th>내용</th>
				<td>
					<div class="input-group">
					<textarea rows="10" cols="30" name="bo_content" id="bo_content" >${board.bo_content}</textarea>
					<span class="error">${board.bo_content}</span>
					</div>
				</td>
			</tr>
			<tr>
				<th>기존파일</th>
				<td>
					<c:forEach items="${board.pdsList}" var="pds">
						<span>
							${pds.pds_filename } &nbsp; <span class="pdsDelete" id="span_${pds.pds_no}">[삭제]</span>   
							<br><hr>            
			            </span>
               </c:forEach>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="등록" /> 
					<input type="button" value="수정" id="updateBtn"/> 
					<input type="reset" value="취소" /> 
					<input type="button" value="뒤로가기" onclick="history.back();"/> 
				</td>
			</tr>
		</table>
		<input type="hidden" name="bo_no" value="${board.bo_no}">
		<input type="hidden" name="bo_ip" value="${pageContext.request.remoteAddr}">
		<input type="hidden" name="bo_parent" value="${param.parent}">
		
		<script type="text/javascript">
			var boardForm = $("#boardForm");	
			var inputTag = "<input type='text' name='delFiles' value='%v' />";
			$(".pdsDelete").on("click", function(){
				$(this).parent().hide();
				var regex = /span_(\d+)/ig;
				var pds_no = regex.exec($(this).prop("id"))[1];
				boardForm.append(inputTag.replace("%v", pds_no));
			});
			
			$("#updateBtn").on("click", function(){
				boardForm.attr("action", "${pageContext.request.contextPath}/board/boardUpdate.do");
				boardForm.submit();
			});
		
		  CKEDITOR.replace('bo_content', {
		      extraAllowedContent: 'h3{clear};h2{line-height};h2 h3{margin-left,margin-top}',

		      // Adding drag and drop image upload.
		      extraPlugins: 'uploadimage',
		      uploadUrl: '${pageContext.request.contextPath}/board/uploadImage.do',

		      // Configure your file manager integration. This example uses CKFinder 3 for PHP.
		      filebrowserImageUploadUrl: '${pageContext.request.contextPath}/board/uploadImage.do',

		      height: 560,

		      removeDialogTabs: 'image:advanced;link:advanced'
		    });
		</script>
	</form>
</body>
</html>