package kr.or.ddit.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.ReplyVO;

public class ReplyDAOImpl implements IReplyDAO {

	SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();

	@Override
	public int insertReply(ReplyVO reply) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			IReplyDAO mapper = session.getMapper(IReplyDAO.class);
			int rowCount = mapper.insertReply(reply);
			if (rowCount > 0) {
				session.commit();
			}
			return rowCount;
		}
	}

	@Override
	public long selectTotalRecord(PagingInfoVO<ReplyVO> pagingVO) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			IReplyDAO result = session.getMapper(IReplyDAO.class);
			return result.selectTotalRecord(pagingVO);
		}
	}

	@Override
	public List<ReplyVO> selectReplyList(PagingInfoVO<ReplyVO> pagingVO) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			IReplyDAO mapper = session.getMapper(IReplyDAO.class);
			return mapper.selectReplyList(pagingVO);
		}
	}

	@Override
	public ReplyVO selectReply(long rep_no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateReply(ReplyVO reply) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteReply(long rep_no) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			IReplyDAO mapper = session.getMapper(IReplyDAO.class);
			int rowCount = mapper.deleteReply(rep_no);
			if (rowCount > 0)
				session.commit();
			return rowCount;
		}
	}

}
