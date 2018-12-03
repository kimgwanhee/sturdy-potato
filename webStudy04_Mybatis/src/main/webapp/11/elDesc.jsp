<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>11/elDesc.jsp</title>
</head>
<body>
<h4>EL(표현언어, Expression Language)</h4>
<pre>
	: 데이터를 출력할 못적의 스크립트 언어.
	\${속성명(EL변수) } //xml에서는 프로퍼티명을 사용하지만 여기선 속성명
	1. 네가지 scope 내의 attribute 데이터에 대한 접근방법 제공(**)
			: 속성을 검색시, 영역에 대한 명시가 없는 경우, 네가지 영역을 순차적으로 검색.
			: 직접 접근시, pageScope, requestScope, sessionScope, applicationScope 객체사용.
	2. EL 연산자 제공 : 피연산자로  리터럴이나 속성만 사용.
		1) 산술연산자 : +,-,/,*,% 기본으로 실수 연산 수행. "+"연산자는 산술연산만 수행
			${3%2 },${ 2/3}, ${"1"+"1" }//el에선 무조건 + 산술연산, 펌킨연산? x
			${"1"+1 },  ${"3"/2 }
		2) 논리연산자 : AND(&&), OR(||, or), NOT(!, not)
		<%
			Boolean bool = new Boolean(true);
			pageContext.setAttribute("bool", bool);
		%>
		${bool2 } | ${ bool2 and true }//변수 사용 안됨 -> false -> bool을 속성으로 변경하면 true | ${not bool }
		//${bool2 } - object의 toString처럼 화이트스페이스로 바꿈 
		3) 비교연산자 : ==(eq), !=(ne), &gt;(gt), &lt;(lt), &gt;=(ge), &lt;=(le)
			${3 gt 2 }, ${bool ne false }
		4) 단항연산자 : ++/--(불가, EL 3.0부터 지원), empty(***) //있는지없는지부터 보고 null체킹
		<%
			String test = "";//String의 length 그게 0보다 크면 false 아니면 true , trim적용은 x
			pageContext.setAttribute("testAttr", test);
			//List<String> testList = Arrays.asList();//현재 사이즈 0-> true
			List<String> testList = Arrays.asList("a","b");//현재 사이즈 0-> true
			pageContext.setAttribute("testList", testList);
		%>
			${empty testAttr }, ${empty testList }
		5) 삼항연산자 : 조건식?참표현:거짓표현
			${empty testList?"컬렉션이 비어있음":"비어있지 않음"}
		
	3. 자바객체의 메소드에 대한 접근 방법 제공(since EL 2.2 version)
		<%
			MemberVO member = new MemberVO("a001", "asdf");
			pageContext.setAttribute("member", member);
			member.setMem_add1("대전");
			member.setMem_add1("대흥동");
		%>
		${empty member } //객체가있냐없냐 판단-> false
		${member.getMem_id() }, ${member.mem_id }
		${member.getAddress() }, ${member.addressTest }
	4. 집합객체의 요소에 대한 접근 방법 제공
	5. EL의 기본객체 제공
	
	<%
		Date today = new Date();
		pageContext.setAttribute("today", today + " pageScope");	//무조건 속성만 사용된다
// 		request.setAttribute("today", today+ "requestScope");
		
	%>
	<%=today%>, ${requestScope.today}
	
	
</pre>
</body>
</html>