<%@page import="org.apache.tomcat.jni.Local"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	String lang = request.getParameter("lang");
	Locale currentLocale = request.getLocale();//기본값할당해두고 display..가져올때 사용할라고
	if(lang != null && lang.trim().length()!=0){
		currentLocale = Locale.forLanguageTag(lang);
	}
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>03/requestHeader.jsp</title>
</head>
<script type="text/javascript">
	function changeHandler() {
		document.langForm.submit();
	}

</script>
<body>
<%
	Locale[] locales = Locale.getAvailableLocales();//사용가능한 Locale들을 배열상태로 가져온다는것
	
	
%>
<form name="langForm">
<select name="lang" onchange="changeHandler();">
	<option value="" selected>언어 선택</option>
	<%			//문자열기반 selected 선택상태에 있을수있게
	//도큐먼트 객체의 프로퍼티로 사용된다 form id=""
		String optPattern = "<option value='%s' %s>%s</option>";
		
		for(Locale tmp :locales){
			String value = tmp.toLanguageTag();//로케일 문자(<option value="ar-Jo"아랍어같은<-)열객체를 만들기 위한
			String text = tmp.getDisplayLanguage();//"한국어.."등 이렇게 찍힐 옵션의 텍스트에 해당
			String selected="";
			if(currentLocale.equals(tmp)){
				selected = "selected";
			}
			
			if(text != null && text.trim().length()!=0){//언어가 있을 때 //다음주부터는 커스텀라이브러리 가져다쓸거임
				out.println(String.format(optPattern, value, selected, text));
			}
		}
	%>
</select>
</form>
<ul>
	<li>
		현재 사용자의 언어와 국가 정보
		: <%= request.getHeader("accept-language") %>
	</li>
	<li>
		<%
// 			ex) ko_KR : 로케일문자 언어_지역(국가)
			Locale locale =  request.getLocale(); 
			String country = locale.getDisplayCountry(currentLocale); //서버의언어(일본클라이언트에서 실행해도 한글언어로 나가게됨 그러므로 locale넣어주기)
			String language = locale.getDisplayLanguage(currentLocale);
		%>
		Locale객체 활용
		: <%= language %>, <%= country %>
	</li>
</ul>

<table>
	<thead>
		<tr>
			<th>헤더명</th>
			<th>헤더값</th>
		</tr>
	</thead>
	<tbody>
		<%
			Enumeration<String> names =  request.getHeaderNames();
			String pattern = "<tr><td>%s</td><td>%s</td></tr>";
			while(names.hasMoreElements()){
				String headerName = names.nextElement(); //이걸로 다시 헤더의 값을 찾아내야하므로
				String headerValue = request.getHeader(headerName); //문자열로 되어있으니
				out.println(String.format(pattern, headerName, headerValue));
			}
		%>	
	</tbody>
</table>

</body>
</html>