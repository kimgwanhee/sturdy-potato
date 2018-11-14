package kr.or.ddit.web.useragent;

public enum SystemType {
	DESKTOP(new String[] {"windows nt", "linux"}, "데스크탑"), 
	MOBILE(new String[] {"android", "iphone"}, "모바일"),
	OTHER(new String[] {} , "기타등등");
	private String[] keywords;
	private String systemName;

	private SystemType(String[] keywords, String systemName) {
		this.keywords = keywords;
		this.systemName = systemName;
	}


	public String getSystemName() {
		return systemName;
	}


	//마지막
	public boolean matches(String userAgent) {
		//키워드가 전부 소문자로 되어있으므로 useragent를소문자로 바꿔주기
		userAgent = userAgent.toLowerCase();
		boolean result = false;
		//키워드들을 대상으로 
		for(String word : keywords) {
			//그안에 이제 word가 포함되어있는지만 잘 보면됨
			result = userAgent.contains(word);
			if(result) break;
		}
		return result;

	}
	
	public static SystemType getSystemType(String userAgent){
		SystemType result = OTHER;
		//거의마지막으로 매칭작업
		for(SystemType tmp :values()) {
			//키워드하고 비교해서 맞는작업 그래서 메서드 matches하나더 만듬
			if(tmp.matches(userAgent)) {
				result = tmp;
				break;
			}
		}
		return result;
	}
}
