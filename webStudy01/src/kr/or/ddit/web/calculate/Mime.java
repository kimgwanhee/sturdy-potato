package kr.or.ddit.web.calculate;

import com.oracle.jrockit.jfr.ContentType;

public enum Mime {
	PLAIN("text/plain;charset=UTF-8"),
	HTML("text/html;charset=UTF-8"),
	XML("application/xml;charset=UTF-8"),
	JSON("application/json;charset=UTF-8");

	//상수값을 얻어올수 있는 변수선언
	public String contentType;
	
	//밖에서 불러다 쓸..메서드
	public String getContentType() {
		return this.contentType;
	}
	
	//생성자
	private Mime(String contentType) {
		this.contentType = contentType;
	}

	//메서드 생성 
	//매개변수로 받은게 있는지없는지 검증 String type
	//매개변수랑 mime에 있는 상수랑 비교 돌아가면서 for 
	//리턴타입= enum타입으로 가져가므로 ..
	public static Mime MimeType(String type) {
		Mime mime = PLAIN;
		if(type != null && type.trim().length()==0) {
			for(Mime m : mime.values()) {
				if(true) {
					
				}
			}
		}
		return mime;
	}
}
