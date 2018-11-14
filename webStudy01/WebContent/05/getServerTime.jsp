<%@page import="java.util.Objects"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@page import="kr.or.ddit.web.calculate.MimeType"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
    <%!
    //  **11월12일
    //마샬링 한거임 //타겟은 map 결과로는 문자열만들어짐 -> 다음단계 응답데이터로 내보내기
    	String marshalling(Map<String, Object> originalData){//맵의 요소 하나하나 꺼내와야함 entry
    		StringBuffer result = new StringBuffer();
    		result.append("{");
    		String jsonPattern = "\"%s\":\"%s\",";
    		for(Entry<String, Object> entry : originalData.entrySet()){
    			String propName = entry.getKey();
//     			String propValue = entry.getValue().toString();//nullpoint발생
    			String propValue = Objects.toString(entry.getValue(), "");//자바 1.7이상부터 사용가능한?
    			result.append(
    				String.format(jsonPattern, propName, propValue)
    					);
    		}
    		int lastIndex = result.lastIndexOf(",");
    		result.deleteCharAt(lastIndex);
    		
    		result.append("}");
    		return result.toString();
    }
    %>
<%
// 	trimDirectiveWhitespaces?
//  1.매 1초마다 현재 서버의 시각을 JSON형태로 전송.
//  2.마임을 바꾸기 위해 위 먼저 수정
//  3.응답으로 전송될 JSON객체 내에는 현재 서버의 시각을의미하는 serverTime이라는
// 프로퍼티가 있음.
//"프로퍼티 네임" :  "값"({이건 제이슨} [배열]..등)  문자열로 줘야함

	response.setHeader("Refresh", "1");
	Date now = new Date();
	Map<String, Object> javaObject = new LinkedHashMap<>();//마샬링의 대상이 되야함 그래서 아래 이제 마샬러를 만들거임
	javaObject.put("serverTime", now);
	String json = marshalling(javaObject);
	
	out.print(json);//이 작업이 마지막 - 직렬화
%>
