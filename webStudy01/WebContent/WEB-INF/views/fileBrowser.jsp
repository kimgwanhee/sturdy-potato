<%@page import="kr.or.ddit.web.model2.FileListGenerartor"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%	
	FileListGenerartor flg = new FileListGenerartor();
	//서블릿에서 request의 attribute로 설정해준 파일 리스트를 가져온다.
	File[] files = (File[])request.getAttribute("files");
%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	//a태그 submit 액션주는 메서드
	// 파라미터 : 파일의 절대경로
	function moveFile(fileAddr){
		var fform = document.fileForm;
		fform.fileAddress.value=fileAddr;
		fform.submit();
	}
</script>
<style>
	img{
		width : 20px;
		height : 20px;
	}
	li{
		list-style: none;
	}
	ul{
		width : 500px;
	}
	h4{
		float:left;
	}
	#backa{
		float : right;
	}
	span{
		font-weight: bold;
	}
	#back{
		cursor: pointer;
	}
</style>
</head>
<body>
	<form name="fileForm" action="<%=request.getContextPath()%>/fileBrowser.do">
	<ul>
		<li>
		<div>
		폴더 : <span><%=files[0].getParentFile().getName()%></span>&nbsp;&nbsp;
		
		
	<%	
		//현재 받아온 파일리스트의 부모파일을 얻어오고 그의 절대경로를 얻어온다.
		String backurl = files[0].getParentFile().getAbsolutePath();
		//스트링객체에서 역슬러쉬의 위치를 구해와 처음부터 그위치까지 잘라준다.
		backurl = backurl.substring(0,backurl.lastIndexOf("\\"));
		backurl= backurl.replaceAll("\\\\","/");
		//최상위 폴더가 WebContent이기 때문에 그보다 더 상위 폴더는 못가도록 조건문을 준다.
		
		if(backurl.contains("webStudy01")){
	%>	
		<img id="back" onclick="javascript:moveFile('<%=backurl%>');" src="<%=request.getContextPath()%>/images/backfile.png">
	<% 
		}
	%>
		<input type="checkbox" name="deletebox" value="deleteok"/>삭제
		<input type="checkbox" name="copybox" value="copyok"/>복사
		<input type="checkbox" name="movebox" value="moveok"/>이동
		</div></li><br>
	<%
		//for문을 이용해서 파일리스트들을 li태그안에 a태그로 생성
		for(File tmp : files){
			//파일의 절대경로를 얻어온다.
			String url = tmp.getAbsolutePath();
			// 자바에서는 역슬러쉬가 안먹기때문에 replaceall로 슬러쉬로 바꿔준다.
			url = url.replaceAll("\\\\","/");
			// 만약 파일이 폴더면 href에 스크립트에 정의해준 function을 타도록 설정
			// 폴더가 아니라 파일이면 href에 alert를 이용해 경고 메시지를 출력한다.
			if(tmp.isDirectory()){
	%>	
			<li>
			<input type="checkbox" name="folderbox" value="<%=url%>"/>
			<img src="<%=request.getContextPath()%>/images/folder.png">
			<a href="javascript:moveFile('<%=url%>');"><%=tmp.getName()%></a>
			</li><hr>
	<%			
			}else{
	%>
			<li>
			<input type="checkbox" name="folderbox" value="<%=url%>"/>
			<img src="<%=request.getContextPath()%>/images/file.png">
			<a href="javascript:moveFile('<%=url%>');"><%=tmp.getName()%></a>
			</li><hr>
	<%
			}
		}
	%>
	</ul>
	<input value="" name="fileAddress" type="hidden"/>
	</form>
</body>
</html>