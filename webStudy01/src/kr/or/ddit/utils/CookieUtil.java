package kr.or.ddit.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


/**
 * @author KGH
 * @since 2018. 11. 16.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2018. 11. 16.      KGH       쿠키의 생성과 획득을 지원하는 유틸리티 클래스 *
 *							(현재 쿠키는 request안에->꺼내야함-> 쿠키util이 request를 써야함-> a가 b를 사용한다는것을 다른말로..dependency의존관계 )
 *							하지만 reqeust는 인터페이스 근접할수없다.new x 			
 * Copyright (c) 2018 by DDIT All right reserved
 * </pre>
 */
public class CookieUtil {
	Map<String, Cookie> cookieMap = new LinkedHashMap<>();
	
	private HttpServletRequest request;//1.이런식으로 dependency어쩌구 불러와야할때..
	public void setRequest(HttpServletRequest request) {
		
		this.request = request;
		generateCookieMap(request);
	
		
	}//1.이렇게 이용 -> 더좋은방법은 -> 생성자 이용
	
	//2.alt+shift+o 생성자이용 //외부에서 생성된걸 받아서 쓴다해서 dependency인덱션?
	public CookieUtil(HttpServletRequest request) {
		super();
		this.request = request;
		generateCookieMap(request);
	}
	
	private void generateCookieMap(HttpServletRequest request){
		cookieMap.clear();//그안의 뭘 비워준다..
		Cookie[] cookies = request.getCookies();
			
		if(cookies != null) {
			for( Cookie tmp: cookies) {
				cookieMap.put(tmp.getName(), tmp);
			}
		}
	}

	private static String enc = "UTF-8";//기본값
	// 다른인코딩 방법을 쓰고싶을때도 있을테니 //이 메서드 부르면됨
	public static void setEnc(String enc) {
		CookieUtil.enc = enc;
	}
	
	/**
	 * 이름으로 쿠키 검색
	 * @param name
	 * @return 존재하지 않는 경우, null반환.
	 */
	public Cookie getCookie(String name){
		return cookieMap.get(name);
	}
	
	
	/**
	 * 이름으로 검색 후 value 획득(UTF-8 방식의 디코딩)
	 * @param name
	 * @return
	 */
	
	public String getCookieValue(String name){//쿠키를 찾아야함 -> 이름으로 받기 value니깐 리턴타입 문자열
		try {
			String value = null;
			//해당 쿠키가 있는지 체크
			if(cookieMap.containsKey(name)) {
				value = URLDecoder.decode(cookieMap.get(name).getValue(), enc);
			}
			return value;
		}catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);//꼭 원본 exception넘겨줘야함 ! 
		}
		
	}
	
/**
 * 쿠키 생성
 * @param 쿠키명
 * @param 쿠키값, 기본 UTF-8방식으로 인코딩됨.
 * @return
 */

	public static Cookie createCookie(String name, String value)  {
		//throws UnsupportedEncodingException해버리면 호출자에게 넘어감-> jsp가 호출하는건 was하지만 꼭 이런방법만있는것은아님 지우고 trycatch
		try {
			value = URLEncoder.encode(value, "UTF-8");
			Cookie cookie = new Cookie(name, value);
			return cookie;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 쿠키 생성
	 * @param name 쿠키명
	 * @param value 쿠키값(UTF-8인코딩)
	 * @param maxAge 초단위 만료 시간
	 * @return
	 */
	
	//이름 값 maxage
	public static Cookie createCookie(String name, String value, int maxAge){
		Cookie cookie = createCookie(name, value);
		cookie.setMaxAge(maxAge);
		return cookie;
	}

	
	public static enum TextType{PATH, DOMAIN}
	/**
	 * 쿠키 생성
	 * @param name 이름
	 * @param value 값
	 * @param text 도메인이나 경로로 사용할 문자열
	 * @param type text 파라미터를 도메인으로 사용할지 경로 사용할지를 결정하는 상수
	 * @return
	 */
	public static Cookie createCookie(String name, String value, String text, TextType type){
		Cookie cookie = createCookie(name, value);
		if(type == TextType.PATH) {
			cookie.setPath(text);
		}else if(type == TextType.DOMAIN) {
			cookie.setDomain(text);
		}
		return cookie;
	}
	
	/**
	 * 이름 값 경로/도메인과 만료시간 설정
	 * @param name
	 * @param value
	 * @param text
	 * @param type
	 * @param maxAge
	 * @return
	 */
	
	//이름 값 경로(도메인) maxAge
	public static Cookie createCookie(String name, String value, String text, TextType type, int maxAge){
		Cookie cookie = createCookie(name, value, text, type);
		cookie.setMaxAge(maxAge);
		return cookie;
	}
	
	/**
	 * 이름 값 도메인 경로
	 * @param name
	 * @param value
	 * @param domain
	 * @param path
	 * @return
	 */
	
	public static Cookie createCookie(String name, String value, String domain, String path){
		Cookie cookie = createCookie(name, value, domain, path);
		cookie.setPath(path);
		cookie.setDomain(domain);
		return cookie;
	}
	
	/**
	 * 이름 값 도메인 경로 만료시간 설정
	 * @param name
	 * @param value
	 * @param domain
	 * @param path
	 * @param maxAge
	 * @return
	 */
	public static Cookie createCookie(String name, String value, String domain, String path, int maxAge){
		Cookie cookie = createCookie(name, value, domain, path);//
		cookie.setMaxAge(maxAge);
		return cookie;
	}
	
}
