package kr.or.ddit.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class FirstLoopTag extends SimpleTagSupport {
	private int loopCount;
	public void setLoopCount(int loopCount) {
		this.loopCount = loopCount;
	}
	
	private String data;
	public void setData(String data) {//얘가 하나 늘어났다면 속성도 늘어나야함 -> tld로 ㄱㄱ
		this.data = data;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		StringBuffer printData = new StringBuffer();
		for (int count = 0; count <= loopCount ; count++) {
			printData.append(data);
		}
		getJspContext().getOut().println(printData);//페이지 컨텍스트 ?특징- 나머지를 다 가져올수있다는 특징
	}
}
