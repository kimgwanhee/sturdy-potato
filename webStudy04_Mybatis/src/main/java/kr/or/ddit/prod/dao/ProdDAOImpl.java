package kr.or.ddit.prod.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;


import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.ProdVO;

public class ProdDAOImpl implements IProdDAO {
	SqlSessionFactory sqlSessionFactory =  CustomSqlSessionFactoryBuilder.getSqlSessionFactory();

	@Override
	public int insertProd(ProdVO prod) {
		try(
				SqlSession session = sqlSessionFactory.openSession();
		){
			IProdDAO mapper = session.getMapper(IProdDAO.class);
			int rowcount = mapper.insertProd(prod);
			if(rowcount>0) {
				session.commit();
			}
			return rowcount;
		}
	}

	@Override
	public ProdVO selectProd(String prod_id) {
		try(
				SqlSession session = sqlSessionFactory.openSession();
		){
			IProdDAO mapper = session.getMapper(IProdDAO.class);
			return mapper.selectProd(prod_id);
		}
	}

	@Override
	public long selectTotalRecord(PagingInfoVO<ProdVO> pagingVO) {
		try(
				SqlSession session = sqlSessionFactory.openSession();
		){
			IProdDAO mapper = session.getMapper(IProdDAO.class);
			return mapper.selectTotalRecord(pagingVO);
		}
	}

	@Override
	public List<ProdVO> selectPordList(PagingInfoVO<ProdVO> pagingVO) {
		try(
				SqlSession session = sqlSessionFactory.openSession();
		){
			IProdDAO mapper = session.getMapper(IProdDAO.class);
			return mapper.selectPordList(pagingVO);
		}
	}

	@Override
	public int updateProd(ProdVO prod) {
		try (
				SqlSession session = sqlSessionFactory.openSession(false);
		){
			IProdDAO mapper = session.getMapper(IProdDAO.class);
			int rowCnt = mapper.updateProd(prod);
			if(rowCnt>0) session.commit();
			return rowCnt;
		}
	}
}
