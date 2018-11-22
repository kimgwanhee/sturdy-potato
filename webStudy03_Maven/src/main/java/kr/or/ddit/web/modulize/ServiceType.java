package kr.or.ddit.web.modulize;

import org.apache.commons.lang3.StringUtils;

public enum ServiceType {
	
	//현재 우리서블릿에서 제공할 수있는 서비스 
	//모든요청은 웰컴페이지 인덱스로 넘어감 -> 인덱스로 넘어가서 COMMAND라는 이름의 파라미터뽑아주기
	
	GUGUDAN("/01/gugudanForm.html"), 
	GUGUDANPROCESES("/gugudan.do"),//실제 구구단이 처리되는곳은 SERVLET -이넘상수를 COMMAND파라미터랑 비교해야함이제
	CALENDAR("/04/calendar.jsp"),
	LOGIN("/login/loginForm.jsp"),
	MUSIC("02/musicForm.jsp"), 
	IMAGE("/imageForm");
	
	
	private String servicePage;

	public String getServicePage() {
		return this.servicePage;
	}
	
	ServiceType(String servicePage) {
		this.servicePage = servicePage;
	}
	
	public static ServiceType getServiceType(String type) {
		ServiceType result = LOGIN;
		if(StringUtils.isNotBlank(type)) {
			for(ServiceType st : ServiceType.values()) {
				if(type.toUpperCase().equals(st.name())) {
					result = st;
				}
			}
		}
		return result;
	}
	
}
