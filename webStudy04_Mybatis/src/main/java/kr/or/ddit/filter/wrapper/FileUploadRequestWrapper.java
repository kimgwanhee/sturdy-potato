package kr.or.ddit.filter.wrapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;


public class FileUploadRequestWrapper extends HttpServletRequestWrapper {
	private Map<String, String[]> parameterMap;
	private Map<String, List<FileItem>> fileItemMap;
	
	public FileUploadRequestWrapper(HttpServletRequest request) throws IOException{
		//생성자안에서 다른 오버로딩을 호출 = this
		 this(request, -1, null);
	}
	public FileUploadRequestWrapper(HttpServletRequest request,  int sizeThreshold, File repository) throws IOException {
		super(request);
		
		parameterMap = new LinkedHashMap<>();
		parameterMap.putAll(request.getParameterMap());//혹시라도 쿼리스트링에 붙어있는 라인 데이타까지도 버리지않고 저장
		fileItemMap = new LinkedHashMap<>();
		
		parseRequest(request, sizeThreshold, repository);
//		문자 Part는 parameterMap 에 누적 
//		파일 Part는 fileItemMap 에 누적 
	}

	private void parseRequest(HttpServletRequest req, int sizeThreshold, File repository) throws IOException {

		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
//				new DiskFileItemFactory(10240, new File("d:/temp")); //디스크에 임시저장객체를 만들어두고 파트데이타를 관리 - 파일아이템타입으로 관리하겠다.

		if(sizeThreshold != -1) {
			fileItemFactory.setSizeThreshold(sizeThreshold);
		}
		if(repository != null) {
			fileItemFactory.setRepository(repository);
		}
		ServletFileUpload handler = new ServletFileUpload(fileItemFactory);//기본 속성을 변경하고싶을때 이방법을 사용? ?
		
//		fileItemFactory.setRepository();//set을 통해서도 기본속성변경가능하다 !
		//3. 핸들러 객체를 이용해 현재 요청 파싱(multipart 하나당 -> 각 FileItem이 만들어짐)
		req.setCharacterEncoding("UTF-8");
		
		try {
			List<FileItem> fileItems = handler.parseRequest(req); //문자데이터와 파일데이타가 섞여있음 그래서 위에서 따로관리
			//4. FileItem들을 대상으로 반복 실행
			if(fileItems != null) {
				for(FileItem item : fileItems) {
					String partname = item.getFieldName();
					if(item.isFormField()) {
						//5. 일반 문자열 기반의 FileItem에 대한 처리와 
						String parameterValue = item.getString(req.getCharacterEncoding());
						String[] alreadyValues = parameterMap.get(partname);//혹시 이전에 같은이름으로 들어간게 없는지 먼저 확인
						String[] values = null;
						if(alreadyValues==null) {//같은이름의 데이타가 없는조건
							values = new String[1]; //한개짜리 데이터가 만들어질것..
						}else {
							//배열은 사이즈 변경불가하므로 - 새로운 배열을 만들고 카피를 떠서 새로잡은 value를 넣어준다
							values = new String[alreadyValues.length+1];
							System.arraycopy(alreadyValues, 0, values, 0, alreadyValues.length);
						}
						values[values.length-1] = parameterValue;
						parameterMap.put(partname, values);
					}else {
						if(StringUtils.isBlank(item.getName())) { //원본파일명이 돌아온다
							//파일 선택하지 않은 비어있는 part를 skip.
							continue;
						}
						
						//파일 기반의 FileItem에 대한 처리를 수행
						List<FileItem> alreadyItems = fileItemMap.get(partname);
						if(alreadyItems==null) {
							alreadyItems = new ArrayList<>();
						}
						alreadyItems.add(item);
						
						fileItemMap.put(partname, alreadyItems);
					}
					
				}//for end
			}//if end
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
		String[] values = parameterMap.get(name);
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
	public Enumeration<String> getParameterNames() {//키만 가져갈것
		final Iterator<String> it = parameterMap.keySet().iterator();
		return new Enumeration<String>() {//익명객체 선언
			@Override
			public boolean hasMoreElements() {//Iterator의 hasnext와 똑같
				return it.hasNext();
			}
			
			@Override
			public String nextElement() {//Iterator의 next와 똑같
				return it.next();
			}
		};
	}
	
	public FileItem getFileItem(String partname) {
		List<FileItem> itemList = fileItemMap.get(partname);
		if(itemList != null && itemList.size()>0) {
			return itemList.get(0);
		}else {//그 파트네임으로 업로드된 파일이 없다는것
			return null;
		}
	}
	
	public List<FileItem> getFileItems(String partname){
		return fileItemMap.get(partname);
	}	
		
	public Map<String, List<FileItem>> getFileItemMap() {
		return fileItemMap;
	}	
		
	public  Enumeration<String> getFileItemNames() {
		final Iterator<String> it = fileItemMap.keySet().iterator();
		return new Enumeration<String>() {//익명객체 선언
			@Override
			public boolean hasMoreElements() {//Iterator의 hasnext와 똑같
				return it.hasNext();
			}
			
			@Override
			public String nextElement() {//Iterator의 next와 똑같
				return it.next();
			}
		};
	}
	
	public void deleteAllTempFile(){
		for (Entry<String, List<FileItem>> entry : fileItemMap.entrySet()) {
			for(FileItem tmp : entry.getValue()) {
				tmp.delete();
			}
		}
	}
}
