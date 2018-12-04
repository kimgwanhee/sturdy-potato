package kr.or.ddit.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.PdsVO;

public class PdsDAOImpl implements IPdsDAO {
	SqlSessionFactory sqlSessionFactory = 
			CustomSqlSessionFactoryBuilder.getSqlSessionFactory();

	@Override
	public int insertPds(PdsVO pds) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PdsVO selectPds(long pds_no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deletePds(long pds_no) {
		// TODO Auto-generated method stub
		return 0;
	}


}
