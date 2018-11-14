package kr.or.ddit.web.useragent;

public enum BrowserType {
	
	//2. 아래코드작성후 이와같은 메시지를 넣을수 있음
	Chrome("크롬"), Firefox("파이어폭스"), iPhone("아이폰"), OTHER("기타");
	private String browserName;	//1.한글 메시지를 내보내는 것이 필요
	private BrowserType(String browserName) {
		this.browserName = browserName;
	}
	//jsp에서 ..캡슐화되어있는 ? 불러올수있는 메서드 만들어야함? 
	
	
	
	//3. 브라우저 선택 후 리턴타입으로 돌려주겟다 리턴값은 브라우저타입이 될것 
	//현재 static키워드가 없으므로 위에서 4개중 하나가 골라져야 실행됨
	//선택되기전에 이 메소드가 실행되어야 함 그러므로 public(외부에서 실행되기위해) static 붙여주기
	//그 후 상수의 이름들을 비교해야함 
	public static BrowserType getBrowserType(String userAgent) {
		BrowserType result =  BrowserType.OTHER;//for문에서 아무것도 찾지 못햇으면 other 기타 가 기본값으로 나가도록함
		BrowserType[] types = values();//4개 받아두고
		for(BrowserType tmp : types) {
			if(userAgent.toUpperCase().contains(tmp.name())) {//모든 이넘은 자기 상수와 같은 이름의 값을 가지고있어서 name을 쓰면 상수이름 가지고올수있음
				result = tmp;
				break;
			}
		}
		return result;
		
	}

	public String getBrowserName() {
		return browserName;
	}
}
