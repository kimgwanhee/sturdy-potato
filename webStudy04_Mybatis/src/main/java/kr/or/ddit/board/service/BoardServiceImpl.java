package kr.or.ddit.board.service;

import java.util.List;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.BoardException;
import kr.or.ddit.board.dao.BoardDAOImpl;
import kr.or.ddit.board.dao.IBoardDAO;
import kr.or.ddit.board.dao.IPdsDAO;
import kr.or.ddit.board.dao.PdsDAOImpl;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.PdsVO;

public class BoardServiceImpl implements IBoardService{
	IBoardDAO boardDAO = new BoardDAOImpl();
	IPdsDAO pdsDAO = new PdsDAOImpl();

	@Override
	public ServiceResult createBoard(BoardVO board) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long retriveBoardCount(PagingInfoVO<BoardVO> pagingVO) {
		return boardDAO.selectTotalRecord(pagingVO);
	}

	@Override
	public List<BoardVO> retriveBoardList(PagingInfoVO<BoardVO> pagingVO) {
		List<BoardVO> result =boardDAO.selectBoardList(pagingVO);
		return result;
	}

	@Override
	public BoardVO retriveBoard(long bo_no) {
		BoardVO result = boardDAO.selectBoard(bo_no);
		if(result == null) {
			throw new BoardException();
		}
		return result;
	}

	@Override
	public ServiceResult modifyBoard(BoardVO board) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResult removeBoard(BoardVO board) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PdsVO downloadPds(long pds_no) {
		// TODO Auto-generated method stub
		return null;
	}

}
