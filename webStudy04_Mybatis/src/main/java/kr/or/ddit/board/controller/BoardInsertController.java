package kr.or.ddit.board.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Insert;

import kr.or.ddit.CommonException;
import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.filter.wrapper.FileUploadRequestWrapper;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.validator.GeneralValidator;
import kr.or.ddit.validator.InsertGroup;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PdsVO;

@CommandHandler
public class BoardInsertController {
	
	
	@URIMapping(value="/board/boardInsert.do", method=HttpMethod.GET)
	public String doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		return "board/boardForm";
	}

	@URIMapping(value="/board/boardInsert.do", method=HttpMethod.POST)
	public String doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		// 1. V.L : board/boardForm
		// 2. 게시글에 첨부파일이 최대 3건 - 파트명 필요(파트가 전달되야함 partname=bo_file)
		// 3. 첨부파일 저장위치 : d:/boardFiles
		BoardVO board = new BoardVO();
		req.setAttribute("board", board);

		try {
			BeanUtils.populate(board, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new CommonException();
		}
		
		String goPage = null;
		Map<String, List<CharSequence>> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		GeneralValidator validator = new GeneralValidator();
		boolean valid = validator.validate(board, errors, InsertGroup.class);
		
		if (valid) {
			if (req instanceof FileUploadRequestWrapper) {
				List<FileItem> fileitems = ((FileUploadRequestWrapper) req).getFileItems("bo_file");
				if(fileitems != null) {
					board.setItemList(fileitems);
				}
			}
			IBoardService service = new BoardServiceImpl();
			ServiceResult result = service.createBoard(board);
			switch (result) {
			case OK:
				goPage = "redirect:/board/boardList.do";
				break;
			case FAILED:
				goPage = "board/boardForm";
				break;
			}
		}else {
			goPage = "board/boardForm";
		}
		return goPage;
	}
}
