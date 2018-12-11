package kr.or.ddit.board.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.filter.wrapper.FileUploadRequestWrapper;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.web.calculate.Mime;

@CommandHandler
public class UploadImageController{

	@URIMapping(value="/board/uploadImage.do", method=HttpMethod.POST)
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		String boardImagesUrl = "/boardImages";
		String boardImagesPath = req.getServletContext().getRealPath(boardImagesUrl);
		File prodImagesFolder = new File(boardImagesPath);

		if (req instanceof FileUploadRequestWrapper) {
			FileItem fileItem = ((FileUploadRequestWrapper) req).getFileItem("upload");

			resp.setContentType(Mime.JSON.getContentType());
			Map<String, Object> shadow = new LinkedHashMap<>();
			if (fileItem != null) {
				// 상품의 이미지를 어딘가에 저장해야하고 이름만 꺼내서 db에 넣어줘야하는 상황
				String savename = UUID.randomUUID().toString();

				File saveFile = new File(prodImagesFolder, savename);

				shadow.put("fileName", fileItem.getName());
				shadow.put("uploaded", "1");
				shadow.put("error", "aa");
				shadow.put("url", req.getContextPath() + "/boardImages/" + savename);

				try (
						InputStream in = fileItem.getInputStream();
				) {
					FileUtils.copyInputStreamToFile(in, saveFile);
				}
			} else {
				shadow.put("number", 400);
				shadow.put("message", "업로드한 이미지가 없음.");
			}
			try (
				PrintWriter out = resp.getWriter();
			) {
				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(out, shadow);
			}
		}
		return null;
	}

}
