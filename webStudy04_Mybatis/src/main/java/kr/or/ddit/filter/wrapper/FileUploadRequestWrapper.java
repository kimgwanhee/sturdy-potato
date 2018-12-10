package kr.or.ddit.filter.wrapper;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;

public class FileUploadRequestWrapper extends HttpServletRequestWrapper {
	//request의 문자형 파라미터를 담아줄 맵 생성
	private Map<String, String[]> parameterMap;
	
	//File아이템 객체를 받아줄 맵생성
	/*
	 * FileItem 인터페이스
	 * void - delete() : 임시 디렉토리에 있는 임시파일을 삭제
	 * byte[] - get() : 파일 아이템을 byte배열로 반환
	 * String - getContentType : 콘텐츠 타입을 반환(ex: image/jpg)
	 * String - getFieldName() : 필드 이름을 반환(input 태그의 name 속성 값을 반환)
	 * String - getName() : 클라이언트에 저장되어있던 파일의 이름을 반환,업로드 파일이름
	 * long - getSize() : 파일의 사이즈를 반환
	 * String - getString() : 기본 문자셋으로 파일 아이템 내용을 반환
	 * String - getString(Encoding) : 지정한 인코딩으로 아이템 내용을 반환
	 * boolean - isFormField() : 일반 파라미터인지 여부 반환 (true 반환시 일반 파라미터)
	 * boolean - isInMemory() : 메모리에만 있는지 여부를 반환
	 * void - write(File) : 업로드 된 파일을 디스크에 저장
	 */
	private Map<String, List<FileItem>> fileItemMap;
	
	//생성자 (매개변수로 request만 받아서 기본값으로 sizeThreshold 는 -1, repository 는 null로 지정
	public FileUploadRequestWrapper(HttpServletRequest request) throws IOException{
		this(request,-1,null);
	}
	
	//request, sizeThreshold, repositroy를 모두 매개변수로 받는 생성자
	public FileUploadRequestWrapper(HttpServletRequest request,int sizeThreshold, File repository) throws IOException {
		super(request);
		parameterMap = new LinkedHashMap<>();
		//request에 담겨져있는 파라미터맵으로 모든 파라미터를 뽑아와 전역변수인 parameterMap에 넣어준다.
		parameterMap.putAll(request.getParameterMap());
		fileItemMap = new LinkedHashMap<>();
		parseRequest(request,sizeThreshold,repository);
	}

	private void parseRequest(HttpServletRequest request , int sizeThreshold, File repository) throws IOException {
		// 문자 Part 는 parameterMap 에 누적
		// 파일 Part 는 fileItemMap 에 누적
		// 1. commons-fileupload 라이브러리 추가(파일업로드를 행하는 프로그램을 간단히 작성하게 해주는 라이브러리)
		// 2. 파일 업로드 핸들러 객체 생성
		
		//FileItem 오브젝트를 생성하는 클래스
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();//요술봉
		
		if(sizeThreshold!=-1) {
			fileItemFactory.setSizeThreshold(sizeThreshold);
		}
		if(repository!= null) {
			fileItemFactory.setRepository(repository);
		}
		//Servlet을 경유하고 파일데이터를 취득하는 클래스
		ServletFileUpload handler = new ServletFileUpload(fileItemFactory);
		
		// 3. 핸들러 객체를 이용해 현재 요청 파싱(Part -> FileItem)
		request.setCharacterEncoding("UTF-8");
		try {
			//List<FileItem> parseRequest(request) : 보내진 데이터를 FileItem 오브젝트에 분활하고 List로 반환
			//(매개변수로 보내져온 request에서 데이터 취득)
			List<FileItem> fileitems = handler.parseRequest(request);
		
		// 4. FileItem 들을 대상으로 반복 실행.
			if(fileitems!=null) {
				for(FileItem item : fileitems) {
					String partname = item.getFieldName();
					//item.isFormField() 
					//true -> string false-> file기반
					if(item.isFormField()) {
						// 5. 일반 문자열 기반의 FileItem 에 대한 처리
						//UTF-8형식으로 파일 아이템 내용을 반환
						String parameterValue = null;
						if(request.getCharacterEncoding()!=null) {
							parameterValue = item.getString(request.getCharacterEncoding());
						}else {
							parameterValue = item.getString();
						}
						
						//request의 파라미터맵값이 들어가있는 parameterMap에서 필드네임의 키값을 이용해 정보가 있는지 화깅ㄴ
						String[] alreadyValues = parameterMap.get(partname);
						//맵에 새롭게 넣어줄 String배열 
						String[] values= null;
						//parameter맵에 필드이름의 값이 없으면 길이1의 새로운 배열 생성
						if(alreadyValues==null) {
							values= new String [1];
						//parameter맵에 필드 이릅의 값이 있으면 원래 가지고있던 배열의 길이에서 1만큼 더한 배열 생성
						}else {
							values= new String[alreadyValues.length+1];
							//System객체에 있는 배열 복사 메서드
							System.arraycopy(alreadyValues, 0, values, 0, alreadyValues.length);//alreadyValues의 길이만큼 - 전체를복사한다는..
						}
						//새롭게 가져온 parameterValue를 values 배열에 담아주고 맵에 저장해준다.
						
						values[values.length-1] = parameterValue;
						parameterMap.put(partname, values);
					
					//파일기반의 FileItem에 대한 처리
					}else {
						// 파일을 선택하지 않은 비어있는 part를 skip.
						if(StringUtils.isBlank(item.getName())) continue;
						
						// 파일 기반의 FileItem 에 대한 처리를 수행
						// 파일아이템의 필드이름에 해당하는 값이 있는지 확인
						List<FileItem> alreadyItems = fileItemMap.get(partname);
						//FileItemMap에 해당 필드명의 값이 없으면 새로운 배열 생성
						if(alreadyItems==null) {
							alreadyItems = new ArrayList<>();
						}
						alreadyItems.add(item);
						fileItemMap.put(partname,alreadyItems);
					}
				}
			}
		} catch (FileUploadException e) {
			throw new IOException(e);
		}
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return parameterMap;
	}
	
	@Override
	public String getParameter(String name) {
		String [] values= parameterMap.get(name);
		if(values==null) {
			return null;
		}else {
			return values[0];
		}
	}
	
	@Override
	public String[] getParameterValues(String name) {
		return parameterMap.get(name);
	}
	
	@Override
	public Enumeration<String> getParameterNames() {
		final Iterator<String> it =  parameterMap.keySet().iterator();
		return new Enumeration<String>() {
			@Override
			public boolean hasMoreElements() {
				return it.hasNext();
			}
			@Override
			public String nextElement() {
				return it.next();
			}
		};
	}
	
	//원하는 필드명에 해당하는 FileItem을 가져오는 메서드(첫번째 값의 FileItem 하나만 가져온다)
	public FileItem getFileItem(String partname){
		List<FileItem> itemList =  fileItemMap.get(partname);
		if(itemList!=null && itemList.size()>0) {
			return itemList.get(0);
		}else {
			return null;
		}
	}
	
	//해당하는 필드명의 FileItem 정보 전체를 List형태로 반환받는다.
	public List<FileItem> getFileItems(String partname){
		return fileItemMap.get(partname);
	}
	
	public Map<String, List<FileItem>> getFileItemMap() {
		return fileItemMap;
	}
	
	public Enumeration<String> getFileItemNames(){
		final Iterator<String> it =  fileItemMap.keySet().iterator();
		return new Enumeration<String>() {
			@Override
			public boolean hasMoreElements() {
				return it.hasNext();
			}
			@Override
			public String nextElement() {
				return it.next();
			}
		};
	}
	//임시파일 삭제
	public void deleteAllTempFile(){
		for (Entry<String, List<FileItem>> entry : fileItemMap.entrySet()) {
			for(FileItem tmp : entry.getValue()) {
				tmp.delete();
			}
		}
	}
}
