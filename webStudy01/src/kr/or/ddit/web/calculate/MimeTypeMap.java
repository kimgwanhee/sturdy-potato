package kr.or.ddit.web.calculate;

import java.util.HashMap;
import java.util.Map;

public enum MimeTypeMap {
	PLAIN((mime,result)->{ Map<String, String> res=new HashMap<>();
		res.put("mime", "text/plain;charset=UTF-8");
		res.put("result", result);
		return res;}),
	HTML((miem,result)->{
		Map<String, String> res=new HashMap<>();
		res.put("mime", "text/html;charset=UTF-8");
		res.put("result", "<p>"+result+"</p>");
		return res;}),
	JAVASCTIPT((miem,result)->{
		Map<String, String> res=new HashMap<>();
		res.put("mime", "text/javascript;charset=UTF-8");
		res.put("result", "<%"+result+"%>");
		return res;
	}),
	JSON((miem,result)->{
		Map<String, String> res=new HashMap<>();
		res.put("mime", "application/json;charset=UTF-8");
		res.put("result", "{\"result\":\""+result+"\"}");
		return res;
	});
	private RealMimeTypeMap realMimeType;
	private String mime;
	MimeTypeMap(RealMimeTypeMap realMimeType){
		this.realMimeType=realMimeType;
	}
	
	public String getMime() {
		return mime;
	}

	public Map<String, String> mimeType(String mime,String result) {
		return realMimeType.mimeType(mime,result);
	}
	
}
