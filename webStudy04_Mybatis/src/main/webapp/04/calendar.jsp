<%@page import="java.util.Locale"%>
<%@page import="java.text.DateFormatSymbols"%>
<%@page import="java.time.DayOfWeek"%>
<%@page import="java.util.Calendar"%>
<%@page import="static java.util.Calendar.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>02/calendar.jsp</title>
<style type="text/css">
	.sunday{
		background-color: red;
	}
	.saturday{
		background-color: blue;
	}
	table{
		width : 100%;
		height: 500px;
		border-collapse: collapse;
	}
	td, th{
		border:1px solid black;
	}
</style>
<script type="text/javascript">
	function eventHandler(year, month){
		var form = document.calForm;
		if((year && month) || month==0){//널이나 공백 false
			form.year.value= year;
			form.year.value = year;
			form.month.value = month;
		}
		form.submit();
		return false;
	}
</script>
</head>
<body>
<%
	//  **11월12일
	request.setCharacterEncoding("UTF-8");//get방식에서도 이용하고싶으면 서버에서 post로 먼저바꾸고하자~..
	String yearStr=request.getParameter("year");
	String monthStr=request.getParameter("month");
	String language = request.getParameter("language");
	
	
	Locale clientLocale = request.getLocale();//2.클라이언트의 모든정보를 가지고있는건 request이므로
	if(language != null && language.trim().length()>0){
		clientLocale= Locale.forLanguageTag(language);//그리고 이 로케일 객체를 clientLocaledp에 주면됨
	}
	DateFormatSymbols symbols = new DateFormatSymbols(clientLocale);//1.일단 현재 서버가 한글위에서 돌기때문에 기본설정이 한글로되어있음//근데 클라이언트에 따라 바뀌어야함
	
	
	Calendar cal = getInstance();
	if(yearStr != null && yearStr.matches("\\d{4}") && monthStr != null && monthStr.matches("1[0-1]|\\d")){
		cal.set(YEAR, Integer.parseInt(yearStr));
		cal.set(MONTH, Integer.parseInt(monthStr));
	}
	
	
	int currentYear = cal.get(YEAR);//Calendar.YEAR상수를꺼낸다는거
	int currentMonth = cal.get(MONTH);
	cal.set(DAY_OF_MONTH, 1);//cal의 날짜를 11월1일로 정해놓고 요일꺼내기 
	int firstDayOfWeek = cal.get(DAY_OF_WEEK);//첫날의 요일데이터 //get이 int로 되어있으므로 ..일요일이 1일임
	int offset = firstDayOfWeek -1; //1일 요일의 앞 공백
	int lastDate = cal.getActualMaximum(DAY_OF_MONTH);//30이라는 숫자
	//달력뽑아내기위한 모든데이터는 끝
	
	cal.add(MONTH, -1);//캘린더의 상태변경을 해도 이젠 상관없으므로 변경
	int beforeYear = cal.get(YEAR);//전달이 몇년
	int beforeMonth = cal.get(MONTH);//전달이 몇일 인지 잡았음
	
	cal.add(MONTH, 2);//전달로 와있으므로 다음달은 +2
	int nextYear = cal.get(YEAR);
	int nextMonth = cal.get(MONTH);
	
	Locale[] locales = Locale.getAvailableLocales();
	
	//<a href="<%=/04/calendar.jsp?">이전달</a> <!-- 클라이언트사이드 절대경로로 표기해야함 -->//?은 get의 형태로 쿼리스트링으로 서버에..
%>
<form name="calForm">
<input type="hidden" name="command" value="calendar"/>
<h4>
<a href = "javascript:eventHandler(<%=beforeYear%>, <%=beforeMonth%>);">이전달</a> 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type = "number" name ="year" value = "<%=currentYear%>"
	onblur="eventHandler()"/>년
<select name="month" onchange="eventHandler(<%=nextYear%>, <%=nextMonth%>);">
	<%
		String[] monthStrings = symbols.getShortMonths();
		for(int idx = 0; idx < monthStrings.length-1; idx++){
			out.println(String.format("<option value='%d' %s>%s</option>",
					idx, idx == currentMonth? "selected":"",monthStrings[idx]));//selected를 넣을건지 말건지 삼항연산자
		}
	
	%>
</select>
<select name ="language" onchange="eventHandler();">
	<%
		for(Locale tmp : locales){
			out.println(String.format("<option value='%s' %s>%s</option>",
										tmp.toLanguageTag(), 
										tmp.equals(clientLocale)?"selected" : "",
										tmp.getDisplayLanguage(clientLocale)
					));
		}
	%>
</select>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a onclick="eventHandler(<%=nextYear%>, <%=nextMonth%>);">다음달</a>
</h4>
</form>
<table>
<thead>
	<tr>
	<% 			
			String[] dateStrings = symbols.getShortWeekdays(); //0요일은 없음 인덱스0은 비어있어서 8?
			for(int idx = Calendar.SUNDAY; idx <=Calendar.SATURDAY; idx++){
				out.println(String.format("<th>%s</th>", dateStrings[idx]));
			}
	%>		
	</tr>
</thead>
<tbody>
<%
//행과 열 42개 필요
	int dayCount = 1;
	for(int row=1; row<=6; row++){//행
		%>
		<tr>
		<% 
		for(int col=1;col<=7;col++){
			int dateChar = dayCount++ -offset;
			if(dateChar < 1 || dateChar > lastDate){
				out.println("<td>&nbsp;</td>");
			}else{
				String clzValue = "normal";
				if(col==1){
					clzValue = "sunday";
				}else if(col==7){
					clzValue = "saturday";
				}
				out.println(String.format(
						"<td class = '%s'>%d</td>", clzValue, dateChar
						));
			}
		}
		%>
		</tr>
		<% 
	}
%>
</tbody>
</table>
</body>
</html>