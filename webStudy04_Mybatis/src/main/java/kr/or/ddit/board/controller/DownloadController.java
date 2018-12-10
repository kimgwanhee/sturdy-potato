package kr.or.ddit.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.vo.PdsVO;
import kr.or.ddit.web.calculate.Mime;

public class DownloadController implements ICommandHandler {

	@Override
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		//파라미터 다운받을거 잡기
		String pdsnoStr = req.getParameter("what");
		//검증하기
		if(!StringUtils.isNumeric(pdsnoStr)) {
			resp.sendError(400);
			return null;
		}
	     //서비스에서 pds객체 받아오기
	      IBoardService service = new BoardServiceImpl();
	      //pds 번호에 해당하는 pds객체 가져오기
	      PdsVO pds =  service.downloadPds(Long.parseLong(pdsnoStr));
	      
	      //파일 객체 생성
	      File file = new File("D:/boardFiles", pds.getPds_savename());
	       try(
	          OutputStream out = resp.getOutputStream();
	          InputStream in = new FileInputStream(file);
	       ){
	          //헤더 초기화해주기
	           resp.reset() ;
	           
	           //오리지날 파일 인코딩 설정
	           String orginalfilename = new String(pds.getPds_filename().getBytes("utf-8"),"iso-8859-1");//pds.getPds_filename()에는 원본파일명.do
	           
	           //마임타입 설정하기
	           resp.setContentType(Mime.OCTET.getContentType());
	           		//구체적인 마임이 뭔지 모를때.. octet가 8 - stream 8비트 -byte로 나감
	           String agent = req.getHeader("User-Agent");
	           
	           if(StringUtils.containsIgnoreCase(agent, "msie")||StringUtils.containsIgnoreCase(agent, "trident")) {
	        	   orginalfilename = URLEncoder.encode(orginalfilename, "UTF-8");
	           }else {
	        	   
	           }
	           //응답데이터에 보내는 파일 정보 설정
	           resp.setHeader("Content-Disposition", "attachment; filename=\"" + orginalfilename + "\"");//어떤이름으로 내보내야한다 ~라는것 안그롬 .do로나감 
	           																						//\"은 파일네임을 ""으로 묶어줘서 공백제거
	           //다운얼마나됬는진..
	           resp.setHeader("Content-Length", pds.getPds_size()+"");
	           //바이트 버퍼를 통해 출력 제어 
	           byte buffer[] = new byte[(int)file.length()];
	           int pointer = 0;
	           while( (pointer = in.read(buffer)) > 0 ){
	              out.write(buffer,0,pointer);
	           }
	       }catch(Exception e){
	         e.printStackTrace();
	       }
	      return null;
	}

}
