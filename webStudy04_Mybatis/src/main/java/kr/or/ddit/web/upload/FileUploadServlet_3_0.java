package kr.or.ddit.web.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.lang3.StringUtils;
import org.junit.runner.Request;

import kr.or.ddit.vo.FileVO;

//@WebServlet("/upload")
//@MultipartConfig //그 파트에 대해서 전처리를하고 문자열을 꺼내서 비어있는 파라미터를 채워준다.
//위 전부 주석하고 web.xml에서 등록
//location 청크 저장 임시 저장위치
//file-size-threshold 한번에 업로드가 끝날때 어떤기준크기미만으로 들어오면 임시저장x 메모리에서끝
public class FileUploadServlet_3_0 extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
//		File saveFolder = new File("D:\\saveFiles");//저장위치
//		/saveFiles에 저장 
		String saveURL = "/saveFiles";//-서버사이드절대경로
		String savePath = getServletContext().getRealPath(saveURL);//-서버사이드절대경로
		File saveFolder = new File(savePath);
		if(!saveFolder.exists()) {
			saveFolder.mkdirs();//존재하지않는 폴더를 하나 생성해줌
		}
		
		String uploader = req.getParameter("uploader");
		Map<String, String[]> parameterMap = req.getParameterMap();
		System.out.println(uploader);
		System.out.println(parameterMap.size());
//		System.out.println(parameterMap.get("uploadFile"));
//		Part uploader = req.getPart("uploader");//input 속성의 name값  문자열 x
		Part uploadFile = req.getPart("uploadFile");
		
		String filemime = uploadFile.getContentType();//마임체크
		if(!StringUtils.startsWithIgnoreCase(filemime, "image/")) {//이미지가 아니면
			resp.sendError(400);
			return;
		}
		
		
//		Content-Disposition: form-data; name="uploadFile"; filename="Hydrangeas.jpg"
		String header = uploadFile.getHeader("Content-Disposition");//이 안에 위에것들 들어있음 
		int idx = header.lastIndexOf("filename=")+"filename=".length();
		
		String originalFilename = header.substring(idx).replace("\"", "");
		System.out.println(originalFilename);
		String savename = UUID.randomUUID().toString(); //파일명이 겹치지 않도록 uuid사용
		
		//Middle tier에 파일의 body를 저장.
		File saveFile = new File(saveFolder, savename);//originalFilename 원본파일명을 사용하면 x
		byte[] buffer = new byte[1024];
		int pointer = -1;//현재 포인터를 확인할수 있도록 
		try(
			InputStream in = uploadFile.getInputStream();
			FileOutputStream fos = new FileOutputStream(saveFile);
		){
			while((pointer = in.read(buffer)) != -1) {
				fos.write(buffer, 0, pointer);//기록
			}
		}
		
		//기록하려면 outputstream 인데 어떤 파일하나를 만들어서..(saveFolder)
		Collection<Part> parts = req.getParts();
		System.out.println(parts.size());
		
		//Database에 파일의 메타데이터를 저장.
		long filesize = uploadFile.getSize();
		
		System.out.printf("데이터베이스에 저장할 메타데이터 : 업로더(%s), 원본명(%s),\n 파일크기(%d), 파일종류(%s), 저장위치(%s), 저장URL(%s)"
							, uploader, originalFilename, filesize, filemime, saveFile.getAbsolutePath(), saveURL+"/"+savename);
		
		FileVO fileVO = new FileVO();
		fileVO.setUploader(uploader);
		fileVO.setOriginalFilename(originalFilename);
		fileVO.setSaveFilename(savename);
		//웹리소스로 저장하는 경우에만 생성되는 메타데이터.
		fileVO.setSaveFileUrl(saveURL+"/"+savename);
		fileVO.setSaveFilePath(saveFile.getAbsolutePath());
		fileVO.setFilesize(filesize);
		fileVO.setFilemime(filemime);
		
		req.getSession().setAttribute("fileVO", fileVO);
		
		resp.sendRedirect(req.getContextPath()+"/13/fileForm.jsp");
		
		/*
		Map<String, Object> metaData = new HashMap<>();
		metaData.put("uploader", uploader);
		metaData.put("originalFilename", originalFilename);
		metaData.put("filesize", filesize);
		metaData.put("filemime", filemime);
		metaData.put("saveFile", saveFile.getAbsolutePath());
		metaData.put("saveURL", saveURL+"/"+savename);
		RequestDispatcher rd = req.getRequestDispatcher("/13/fileForm.jsp");
		rd.forward(req, resp);
		*/
		
		
		
	}
}
