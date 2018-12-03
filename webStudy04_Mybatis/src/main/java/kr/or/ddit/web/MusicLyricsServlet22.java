package kr.or.ddit.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MusicLyricsServlet22 extends HttpServlet {
   
   // 제일 먼저 실행되는 부분 doGet
   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      resp.setContentType("text/html;charset=UTF-8"); // html을 보내준다 (resp는 보내주는것)

      ServletContext context = req.getServletContext(); 
      File folder = new File("d:/contents"); // d:/contents로 폴더 지정 (옵션에서 이름을 가져오기 위해)
      String[] filenames = folder.list(new FilenameFilter() { // 배열로 만들어서 text파일만 필터링

         @Override
         public boolean accept(File dir, String name) { 
            String mime = context.getMimeType(name); 
            return mime.startsWith("text/"); // text만 가져옴
         }
      });

      InputStream is = this.getClass().getResourceAsStream("music.html"); // html폼을 byte스트림으로 읽어온다
      InputStreamReader isr = new InputStreamReader(is); // 읽어온 byte스트림을 char로 변환한다
      BufferedReader br = new BufferedReader(isr); // 버퍼이용
      StringBuffer html = new StringBuffer(); // 객체를 만듬
      String temp = null; // 한줄씩 읽어올 객체 

      while ((temp = br.readLine()) != null) { // 힌줄씩 while문으로 html을 넣어준다
         html.append(temp);
      }

      StringBuffer code = new StringBuffer(); // @를 바꿔줄것을 담을 곳 
      String pattern = "<option>%s</option>\n"; // 패턴

      for (int i = 0; i < filenames.length; i++) { // 옵션에 나오는 파일명 돌림
         code.append(String.format(pattern, filenames[i])); // %s에 filenames[i]을 넣어준다
      }

      int start = html.indexOf("@music"); // replaceText로 시작점을 담음
      int end = start + "@music".length(); // replaceText로 끝점을 담음
      String replaceText = code.toString(); // 코드로 바꿈

      html.replace(start, end, replaceText); // 위에껄 담음

      PrintWriter out = resp.getWriter(); // 사용자의 브라우저에 보여줄 객체
      out.print(html.toString()); // html을 문자열로 바꿔서 줌
      out.close(); // 닫음
   }
   
   // 전송 버튼을 눌렀을 때 발생하는 메소드
   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      req.setCharacterEncoding("UTF-8"); // 한글인식하기 위해서 씀
      resp.setContentType("text/html;charset=UTF-8"); // 가사를 받을 형식을 html로 받아오기 때문에 씀
      // server-side 데이터 검증
      // 요청 파라미터 확보 : 파라미터명(image)
      String musicName = req.getParameter("musicname"); // 옵션에서 문자열을 받아옴
      if (musicName == null || musicName.trim().length() == 0) { // 선택이 안되어있으면 오류
         resp.sendError(400);
         return;
      }
      File folder = new File("d:/contents"); // 파일을 선택할 폴더를 지정
      File lyricsFile = new File(folder, musicName); // 폴더를 선택한곳에서 선택한 파일을 가져온다
      if (!lyricsFile.exists()) { // 파일이 없을경우 에러
         resp.sendError(404);
         return;
      }
      
      
      FileInputStream fis = new FileInputStream(lyricsFile); // byte스트림 
      InputStreamReader isr = new InputStreamReader(fis, "EUC-KR"); // 메모장에 한글로 되어있을경우 안됨 //바이트를 캐릭터로
      BufferedReader br = new BufferedReader(isr); // 버퍼이용
      String pattern = "%s %s"; //패턴만듬
      String temp = null;
      
      StringBuffer html = new StringBuffer(); 
      html.append("<html>");
      html.append("<head>");
      html.append("</head>");
      html.append("<body>");
      
      while ((temp = br.readLine()) != null) { //가사 한줄 + <br>
         html.append(String.format(pattern, temp,"<br>"));
      }
      br.close();
      html.append("</body>");
      html.append("</html>");
      
      PrintWriter out = resp.getWriter();
      out.print(html);
      out.close();
   }
}