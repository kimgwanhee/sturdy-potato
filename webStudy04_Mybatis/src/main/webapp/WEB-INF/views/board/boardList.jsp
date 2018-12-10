<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script src="http://malsup.github.com/jquery.form.js"></script> 
    
<script type="text/javascript">

	function ${pagingVO.funcName}(d){
		var searchForm = $('[name="searchForm"]');
		 searchForm.find('[name="page"]').val(d);
		 searchForm.submit();
	}
	
	function createBody(data){
		
		   var tbodyTag = $('#bodylist');
		   var navTag = $('#pagenav');
		   var body="";
		   var boardList = data.dataList;
		   var searchForm = $('[name="searchForm"]');
		   
		   if(boardList){
		      $.each(boardList,function(i, res){
		         body+="<tr>";
		         body+="<td>"+res.bo_no+"</td>";
		         body+="<td>"+res.bo_writer+"</td>";
		         body+="<td>"+res.bo_title+"</td>";
		         body+="<td>"+res.bo_date+"</td>";
		         body+="<td>"+res.bo_hit+"</td></tr>";
		      })
		   }else{
		      body+="<tr><td colspan='5'>상품이 없음</td></tr>";
		   }
		   tbodyTag.html(body);
		   navTag.html(data.pagingHTML);
		   var pageNum = searchForm.find('[name="page"]').val();
		   var queryString = searchForm.serialize();
		   
		   //비동기 처리 성공 -> push state on history
		   history.pushState(data, pageNum+"페이지", "?"+queryString);
		   $("[name='page']").val("");
		}
</script>
<c:url value="/board/boardView.do" var="boardURL"></c:url>
<script type="text/javascript">

$(function(){
	var searchForm = $('[name="searchForm"]');
	$(window).on("popstate", function(event){
		   console.log(event);
		   if(event.originalEvent.state){
			   var pagingVO = event.originalEvent.state;
			   
			   var tbodyTag = $('#bodylist');
			   var navTag = $('#pagenav');
			   var body="";
			   var boardList = pagingVO.dataList;
			   
			   if(boardList){
			      $.each(boardList,function(i, res){
			    	  body+="<tr>";
				         body+="<td>"+res.bo_no+"</td>";
				         body+="<td>"+res.bo_writer+"</td>";
				         body+="<td>"+res.bo_title+"</td>";
				         body+="<td>"+res.bo_date+"</td>";
				         body+="<td>"+res.bo_hit+"</td></tr>";
			      })
			   }else{
			      body+="<tr><td colspan='5'>상품이 없음</td></tr>";
			   }
			   navTag.html(event.state.pagingHTML);
			   tbodyTag.html(body);
		   }else{//동기요청의 history인것
			   location.reload();
		   }
	   });
	
	$("#bodylist").on("click","tr", function(){//bodylist없어지는 이벤트아니라 클릭이벤트 언제든지 되는데 "tr"? ? ?
		var what = $(this).find("td:nth-child(1)").text();
		location.href = "${boardURL}?what="+what;
	});

	searchForm.ajaxForm({
	      dataType:  'json', 
	       success: createBody 
	   });
});

</script>
</head>
<body>
	<h4>자유게시판 ! ! </h4>
	<form  name="searchForm" action="">
		<select name="searchType">
			<option value="all">전체</option>
			<option value="writer">작성자</option>
			<option value="title">제목</option>
			<option value="content">내용</option>
		</select>
		
		<script type="text/javascript">
			document.searchForm.searchType.value="${empty pagingVO.searchType ? 'all' : pagingVO.searchType}";
		</script>
		
		<input type="text" name="searchWord" value="${pagingVO.searchWord}" />
		<input type="submit" value="검색">
		<input type="button" value="새글쓰기" onclick="location.href='${pageContext.request.contextPath}/board/boardInsert.do';">
		<input type="hidden" name="page"/>
	</form>

	<table class="table">
	<thead class="thead-dark">
		<tr>
			<th>글번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>조회수</th>
		</tr>
	</thead>
	<tbody id="bodylist">
		<c:choose>
			<c:when test="${not empty pagingVO.dataList }">
				<c:forEach items="${pagingVO.dataList}" var="boardVO">
					<tr>
						<td>${boardVO.bo_no }</td>
						<td>${boardVO.bo_title }</td>
						<td>${boardVO.bo_writer }</td>
						<td>${boardVO.bo_date }</td>
						<td>${boardVO.bo_hit }</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td colspan="6">정보 없음</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</tbody>
	
	<tfoot>
		<tr>
			<td colspan="6">
				<nav aria-label="Page navigation example" id="pagenav">
					${pagingVO.pagingHTML}
				</nav>
			</td>
		</tr>
	</tfoot>
	
	</table>

</body>
</html>