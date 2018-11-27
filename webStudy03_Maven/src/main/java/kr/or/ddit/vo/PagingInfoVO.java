package kr.or.ddit.vo;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * 	totalRecord와 currentPage를 결정하면, 나머지 속성들이 연산됨.
 * 	setToalRecord/setCurrentPage 호출 필요.
 * @param <T>
 * 
 * 
 */
@Data
@NoArgsConstructor
public class PagingInfoVO<T> {
	public PagingInfoVO(int screenSize, int blockSize) {
		super();
		this.screenSize = screenSize;
		this.blockSize = blockSize;
	}
	
	private long totalRecord;
	private int screenSize = 10;
	private int blockSize = 5;
	private long currentPage;
	private long totalPage;
	private long startPage;
	private long endPage;
	private long startRow;
	private long endRow;
//	private List<MemberVO> dataList;//고정이 되있다면 거래처목록, 상품목록에 사용하는 페이징vo를 각각 만들어야함.. -> 제너릭타입이라는 문법을 적용할거임
	private List<T> dataList;//T-타입변수라고 부름 
	//제너릭타입-> 구체적인 타입이 컴파일할때는 안정해져있지만 런타임때는 정해지는 것 ..
	private T searchVO;
	private String searchWord;
	private String searchType;
	private String funcName = "paging";
	
	public List<MemberVO> getDataList() {
		return dataList;
	}
	public void setDataList(List<MemberVO> dataList) {
		this.dataList = dataList;
	}
	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
		totalPage = totalRecord%screenSize==0?totalRecord/screenSize
												:totalRecord/screenSize +1;
	}
	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
		endRow = currentPage * screenSize;
		startRow = endRow - (screenSize-1);
		startPage = (currentPage-1)/blockSize * blockSize+1;
		endPage = startPage + (blockSize-1);
	}
	
//	 <ul class="pagination">
//	    
//	    <li class="page-item"><a class="page-link" href="?page=1">1</a></li>
//	    <li class="page-item"><a class="page-link" href="?page=2">2</a></li>
//	    <li class="page-item"><a class="page-link" href="?page=3">3</a></li>
//	  </ul>
	
	public String getPagingHTML() {
		String pattern = "<li class=\"page-item %s\"><a class=\"page-link\" href='javascript:"+funcName+"(%d);'>%s</a></li>";
		StringBuffer html = new StringBuffer();
		html.append("<ul class='pagination'>");
		if(startPage > 1) {
			html.append(String.format(pattern, "", (startPage-1),"이전"));
		}
		
		if(endPage > totalPage) endPage = totalPage;
		//next를 넣을거냐 말거냐 
		for(long page = startPage; page <= endPage; page++) {
			String active = "";
			String pageStr = page + "";
			if(page == currentPage) { 
				active = "active";
				pageStr += " <span class=\"sr-only\">(current)</span>";
			}
			html.append(String.format(pattern,active, page, pageStr));
		}
		
		if(endPage < totalPage) {
			html.append(String.format(pattern, "", endPage+1, "다음"));
		}
		html.append("</ul>");
		return html.toString();
	}
}
