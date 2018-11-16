package kr.or.ddit.web.model2;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class FileList {
	public List<File> getFileList(String fileNmae) {
		File folder= new File(fileNmae);//받아온 경로의 파일객체생성
		
		File[] folderList=folder.listFiles();//그 파일안에 있는 모든 파일을 가져옴 listFiles()의 반환타입은 파일[]임
		System.out.println(folderList.length);
		List<File> filist=new ArrayList<>();
		if(!folder.getName().equals("webStudy01")) {//웹스터디01위로는 보여주면안되기떄문에 폴더의 이름이 
			//웹스터디01경우 이로직은 안탐 
			//상위폴더 생성해주는곳
			filist.add(folder.getParentFile());//만약 이 조건에 들어오면 경로의 상위폴더를 리스트0번째에 추가
		}
		if (folder.isDirectory()) {//만약 생성해준 파일객체가 폴더면 true아니면 false
			for(File tmp:folderList) {
				filist.add(tmp);
			}//폴더일경우 그 폴더에있는 모든 파일들을 리스트에 담아줌
			
		}
		
		return filist;//담아준 값을 반환
		}
}
