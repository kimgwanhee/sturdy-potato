package kr.or.ddit.web.model2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileListGenerartor {
	
	// url을 매개변수로 받아 거기에 해당하는 폴더를 생성해주고
	// 그 폴더 경로에 있는 파일리스트를 가져오는 메서드 
	public File[] getFileList(String url) {
		File folder = new File(url);
		return folder.listFiles();
	}
	
	//파일 복사하는 메서드
	public void copyFile(String start, String destin) throws IOException{
		  InputStream inStream = null;	
		  OutputStream outStream = null;
		  File infile = new File(start);	// 복사하려는파일대상
		  File outfile = new File(destin);	// 복사되는 파일경로?(도착지)
		  try{
		      inStream = new FileInputStream(infile);	//원본파일
		      outStream = new FileOutputStream(outfile+"/"+infile.getName()); 
		      //복사해서 내보낼 때-> (복사되는 파일경로"/"앞에선있는infile.getName()해서 이름만 가져와서 복사)
		 
		      byte[] buffer = new byte[1024];	
		      int length;
		      
		      while ((length = inStream.read(buffer)) > 0){ //원본파일을 읽어서 byte[] 버퍼에담는다.
		    	  outStream.write(buffer, 0, length);	// 위 outStream에 불러온 버퍼를 넣는다.
		      }
		  }catch(IOException e){
		      e.printStackTrace();
		  }finally{
			  inStream.close();	//항상 리소스객체는 닫아준다. (깨끗한 상태로 쓰려고)
		      outStream.close();
		  }
		}
	
	//파일 이동
	public void moveFile(String start, String destin) throws IOException{
		  InputStream inStream = null;
		  OutputStream outStream = null;
		  File infile = new File(start);
		  File outfile = new File(destin);
		  try{
		      inStream = new FileInputStream(infile); //원본파일
		      outStream = new FileOutputStream(outfile+"/"+infile.getName()); //이동시킬 위치
		 
		      byte[] buffer = new byte[1024];
		      int length;
		      
		      while ((length = inStream.read(buffer)) > 0){
		      outStream.write(buffer, 0, length);
		      }
		      
		      infile.delete();	// 원본파일은 삭제한다.
		  }catch(IOException e){
		      e.printStackTrace();
		  }finally{
			  inStream.close();
		      outStream.close();
		  }
		}
}
