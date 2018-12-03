<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>11/elCollection.jsp</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
	var memberVO = {};// 객체를 만드는데 프로퍼티가 없다.
	console.log(JSON.stringify(memberVO));//마샬링
	memberVO.mem_id = "a001";
	console.log(JSON.stringify(memberVO));
	memberVO["mem_name"]="김은대";//memberVO.mem_id = "a001";과 동일
	console.log(JSON.stringify(memberVO));
	
	for(var prop in memberVO){//자바스크립트에서의 향상된for문
		console.log(prop + " : " + memberVO[prop]);
	}
	console.log("=================");
	$.each(memberVO, function(prop, propValue){
		console.log(prop+":"+propValue+":"+memberVO[prop]);
	});
	
</script>
</head>
<body>
<h4>EL에서의 집합객체 사용</h4>
<pre>
	<%
		String[] array = new String[]{"element1", "element2", "element3"};
		pageContext.setAttribute("array", array);
		List<String> list = new ArrayList<>();
		list.add("listValue1");
		list.add("listValue2");
		list.add("listValue3");
		pageContext.setAttribute("list", list);
		Map<String, Object> map = new HashMap<>();
		map.put("key1", "value1");
		map.put("key-2", new Date());
		pageContext.setAttribute("map", map);
		MemberVO member = new MemberVO("a001", "asdf");
		pageContext.setAttribute("member", member);
		Set<String> set = new HashSet<>();
		set.add("setValue1");
		set.add("setValue2");
		set.add("setValue1");
		pageContext.setAttribute("set", set);
	%>
	${array[5]} //없는 index 화이트스페이스로 바꿈
<%-- 	<%=array[5] %> --%>
	${list.get(1) }//list.get(5)-> exception발생, 자바언어의 특성을 그대로 쓰기때문
	${list[1] }, ${list[5] }//화이트스페이스
	${map.get("key1") }, ${map.key1 }//옛날버전에선 메서드호출x므로 이렇게씀
	${map.get("key-2") }, ${map["key-2"] }//["key-2"]첨자인것처럼 사용
	${member.getMem_id() }//왠만하면 이방법 x,
	${member.mem_id },${member["mem_id"] }
	${set }
	
	
</pre>
</body>
</html>