package kr.or.ddit.board.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.CommonException;
import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.filter.wrapper.FileUploadRequestWrapper;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.validator.GeneralValidator;
import kr.or.ddit.validator.InsertGroup;
import kr.or.ddit.vo.BoardVO;

public class BoardUpdateController implements ICommandHandler {

	@Override
	public String Process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String method = req.getMethod();
		String view = null;

		// 메소드가 뭔지 판단 get이면-> 양식제공

		// 신규등록과 수정양식의 차이점은 기존의데이타를 초기값으로 셋팅하고 수정해야함

		// 1. v.l : "board/boardForm"
		// 2. 기존 첨부파일이 있다면, 삭제 가능하도록.
		// 3. 새로운 첨부파일이 있다면, 업로드//기존첨부파일위치랑 같은 위치..
		if ("get".equalsIgnoreCase(method)) {
			view = doGet(req, resp);
		} else if ("post".equalsIgnoreCase(method)) {
			view = doPost(req, resp);
		} else {

		}
		return view;

	}

	public String doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String bonoStr = req.getParameter("what");
		if (!StringUtils.isNumeric(bonoStr)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		IBoardService service = new BoardServiceImpl();
		BoardVO board = service.retriveBoard(Long.parseLong(bonoStr));
		req.setAttribute("board", board);
		
		return "board/boardForm";
	}

	public String doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");

		BoardVO board = new BoardVO();
		req.setAttribute("board", board);

		try {
			BeanUtils.populate(board, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new CommonException();
		}
		
		String goPage = null;
		Map<String, List<CharSequence>> errors = new LinkedHashMap<>();
		req.setAttribute("board", board);
		req.setAttribute("errors", errors);
		GeneralValidator validator = new GeneralValidator();
		boolean valid = validator.validate(board, errors, InsertGroup.class);
		String message = null;

		if (valid) {
			if (req instanceof FileUploadRequestWrapper) {
				List<FileItem> fileitems = ((FileUploadRequestWrapper) req).getFileItems("bo_file");
				if(fileitems!=null) {
					board.setItemList(fileitems);
				}
			}
			IBoardService service = new BoardServiceImpl();
			ServiceResult result = service.modifyBoard(board);
			switch (result) {
			case OK:
				goPage = "redirect:/board/boardList.do";
				
				break;
			case FAILED:
				goPage = "board/boardForm";
				message = "수정에 실패";
				break;
			case INVALIDPASSWORD:
				goPage = "board/boardForm";
				message="비밀번호 일치하지않음";
			}
		} else {
			goPage = "board/boardForm";
			message="서버문제";
		}
		req.setAttribute("message", message);
		return goPage;
	}
}
