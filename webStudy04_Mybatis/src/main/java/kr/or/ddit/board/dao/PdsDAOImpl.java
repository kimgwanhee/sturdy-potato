package kr.or.ddit.board.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.PdsVO;

public class PdsDAOImpl implements IPdsDAO {
	SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();

	@Override
	public int insertPds(PdsVO pds) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			IPdsDAO mapper = session.getMapper(IPdsDAO.class);
			int rowCount = mapper.insertPds(pds);
			if (rowCount > 0) {
				session.commit();
			}
			return rowCount;
		}
	}

	@Override
	public PdsVO selectPds(long pds_no) {
		try(
			SqlSession session = sqlSessionFactory.openSession();
		){
			IPdsDAO mapper = session.getMapper(IPdsDAO.class);
			return mapper.selectPds(pds_no);
		}
	}

	@Override
	public int deletePds(long pds_no) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertPdsList(BoardVO board, SqlSession session) {
//		IPdsDAO mapper = session.getMapper(IPdsDAO.class);
//		int rowCount = mapper.insertPdsList(board, session);
//		return rowCount;
		
		return session.insert("kr.or.ddit.board.dao.IPdsDAO.insertPdsList", board);
	}
}
