<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>06/implicitObject.jsp</title>
</head>
<body>
<h4> 기본 객체(내장 객체) </h4>
<pre>
11월14일 -1
					타입
	** pageContext(PageContext) : JSP페이지와 관련된 모든 정보를 가진 객체
	request(HttpServletRequest) : 요청과 클라이언트에 정보를 캡슐화한 객체
	response(HttpServletResponse) : 응답과 관련된 모든 정보를 캡슐화한 객체
	out(JSPWriter) : 출력 버퍼에 데이터를 기록하거나 버퍼를 제어하기 위해서 사용되는 출력 스트림.
	 - 출력스트림(문자스트림)
	session(HttpSession) : 한세션 내에서 발생하는 모든 정보를 캡슐화한 객체
	 - 데이터베이스를 이용한다는건 쿼리문사용- 쿼리를 실행할수있는 연결 커넥션필요
		-> 연결통로를 개방한때부터 끊을때까지 그 시간//그리고 그 모든걸 가질수 있는 객체를 session 
	application(ServletContext) : 컨텍스트(어플리케이션)와 서버에 대한 정보를 가진 객체
	 - application는 기본객체는 하나의 서버사이드 정보를가지고있는..-> 서버사이드어플리케이션이 스탑되는순가 언제죽나? 서버가 셧다운이되면 동시에 종료됨
	 <%= application.hashCode()%> //주소확인
	config(ServletConfig) : 서블릿 등록과 관련된 정보를 가진 객체 
	 - jsp에서 ServletConfig는.. jsp는 진짜소스가 ㄴㄴ -> jsp도 servlet-> 등록되어있거나 매핑되어있다면 ServletConfig도 들어와있다는것
	page(Object) : 현재 JSP페이지에 대한 레퍼런스
	 - jsp는 와스가 싱글톤객체를 만들어두는데 그게 page(자기자신 this) -> 공통된타입때문 object -> this랑 비슷 근데 타입때문에 불편
		-> object가 가지고있는 멤버들만 접근 가능함 -> 제대로쓸려면 다운캐스팅을 해야함-> 클래스를알아야함-> 모름-> 캐스팅안됨
		-> this사용이 더 좋다-> *커스텀태그를 적용해서 사용할땐 page사용해야함
	exception(Throwable) : 발생한 예외에 대한 정보를 가진 객체
						 : 예외(에러)가 발생한 경우, 에러를 처리하는 페이지에서 사용됨.(page 지시자의 isErrorPage로 활성화함.)
	 - 어떤 exception이 발생했을 때 처리할 페이지가 따로있을것-처음엔안보임-에러를처리할페이지다 설정해주면 보임
	 
	 <%=exception %>
</pre>
</body>
</html>