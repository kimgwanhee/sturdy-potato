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
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.validator.GeneralValidator;
import kr.or.ddit.validator.InsertGroup;
import kr.or.ddit.vo.BoardVO;

@CommandHandler
public class BoardUpdateController {
	IBoardService service = new BoardServiceImpl();

	@URIMapping("/board/boardUpdate.do")
	public String getProcess(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String bonoStr = req.getParameter("what");
		if (!StringUtils.isNumeric(bonoStr)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		BoardVO board = service.retriveBoard(Long.parseLong(bonoStr));
		req.setAttribute("board", board);
		
		return "board/boardForm";
	}

	@URIMapping(value="/board/boardUpdate.do", method=HttpMethod.POST )
	public String doPost(HttpServletRequest req, HttpServletResponse resp) {

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
