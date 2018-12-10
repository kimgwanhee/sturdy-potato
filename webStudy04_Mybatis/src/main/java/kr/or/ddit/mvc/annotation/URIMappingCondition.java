package kr.or.ddit.mvc.annotation;

import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;

/**
 * 웹상의 요청을 받을 수 있는 조건을 주소(uri)와 http 메소드(method)로 설정해서 가진 객체.
 */
public class URIMappingCondition {//이뮤터블..?객체???? -> 수정불가 
	private String url;
	private HttpMethod method;
	
	public URIMappingCondition(String url, HttpMethod method) {
		super();
		this.url = url;
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public HttpMethod getMethod() {
		return method;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		URIMappingCondition other = (URIMappingCondition) obj;
		if (method != other.method)
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	
	
	
	

}
