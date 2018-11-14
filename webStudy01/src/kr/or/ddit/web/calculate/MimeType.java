package kr.or.ddit.web.calculate;


public enum MimeType {
	PLAIN((mime) -> {
		return "text/plain;charset=UTF-8";
	}), HTML((miem) -> {
		return "text/html;charset=UTF-8";
	}), JAVASCTIPT((miem) -> {
		return "text/javascript;charset=UTF-8";
	}), JSON((miem) -> {
		return "application/json;charset=UTF-8";
	});
	private RealMimeType realMimeType;

	MimeType(RealMimeType realMimeType){
		this.realMimeType=realMimeType;
	}

	public String mimeType(String mime) {
		return realMimeType.mimeType(mime);
	}
}
