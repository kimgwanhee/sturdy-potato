package kr.or.ddit.web.upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;

import lombok.val;

//2.5버전에서는 multipart-config사용불가
public class FileUploadServlet_2_5 extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, String[]> parameterMap = new LinkedHashMap<>();
		Map<String, List<FileItem>> fileItemMap = new LinkedHashMap<>(); //파일데이타만 따로 관리해서 넣어줄것임
//		InputStream in = req.getInputStream();//우리가 직접 파싱작업을 해야함
		//1. commons-fileupload 라이브러리 추가
		//2. 파일 업로드 핸들러 객체 생성
		DiskFileItemFactory fileItemFactory =
				new DiskFileItemFactory(10240, new File("d:/temp")); //디스크에 임시저장객체를 만들어두고 파트데이타를 관리 - 파일아이템타입으로 관리하겠다.
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
			System.out.println(parameterMap.get("uploader")[0]);
			System.out.println(fileItemMap.get("uploadFile").get(0));
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}
}
