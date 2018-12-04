package kr.or.ddit.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingInfoVO;

public class BoardDAOImpl implements IBoardDAO {
	SqlSessionFactory sqlSessionFactory = 
			CustomSqlSessionFactoryBuilder.getSqlSessionFactory();

	@Override
	public int insertBoard(BoardVO board) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long selectTotalRecord(PagingInfoVO<BoardVO> pagingVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<BoardVO> selectBoardList(PagingInfoVO<BoardVO> pagingInfoVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoardVO selectBoard(long bo_no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void incrementHit(long bo_no) {
		// TODO Auto-generated method stub

	}

	@Override
	public void incrementRcmd(long bo_no) {
		// TODO Auto-generated method stub

	}

	@Override
	public int updateBoard(BoardVO board) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteBoard(int bo_no) {
		// TODO Auto-generated method stub
		return 0;
	}

}
