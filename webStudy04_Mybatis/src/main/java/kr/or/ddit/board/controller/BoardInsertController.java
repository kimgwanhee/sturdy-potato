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
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.validator.GeneralValidator;
import kr.or.ddit.validator.InsertGroup;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PdsVO;

public class BoardInsertController implements ICommandHandler {
	@Override
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		String method = req.getMethod();
		String view = null;
		if ("get".equalsIgnoreCase(method)) {
			view = doGet(req, resp);// 논리적이 뷰 네임
		} else if ("post".equalsIgnoreCase(method)) {
			view = doPost(req, resp);
		} else {
			resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
		return view;
	}

	public String doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		return "board/boardForm";
	}

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
				board.setItemList(fileitems);
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
