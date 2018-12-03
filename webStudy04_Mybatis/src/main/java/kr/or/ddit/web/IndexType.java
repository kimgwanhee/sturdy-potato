package kr.or.ddit.web;

public enum IndexType {
	
	//2번
	GUGUDAN("/01/gugudanForm.html"), 
	CALENDER("/04/calendar.jsp"), 
	MUSIC("02/musicForm.jsp"), 
	IMAGE("/imageForm");
	
	private String iName;//4번
	
	//이제 이 안에 서비스 페이지를 파라미터로 받을 수 있는 생성자를 하나 만들기
	private IndexType(String iName) {//생성자에서 셋팅 //3번
		this.iName = iName;
	}
	
	//활용하려면
	
	//1번
	public static IndexType getIndexType(String select) {//선택한메뉴
		IndexType result = null;
		IndexType[] value = values();
		for(IndexType tmp : value) {
			if(select.toUpperCase().contains(tmp.name())){
					result = tmp;
					break;
			}
		}
		return result;
	}
	
	
	public String getIndexName() {
		return iName;
	}
}
