package kr.or.ddit.buyer.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingInfoVO;

public class BuyerDAOImpl implements IBuyerDAO{
	
	SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();

	@Override
	public BuyerVO selectBuyer(String buyer_id) {
		try(
				SqlSession session = sqlSessionFactory.openSession();
		){
			IBuyerDAO mapper =session.getMapper(IBuyerDAO.class);
			return mapper.selectBuyer(buyer_id);
		}
	}
	
	@Override
	public List<BuyerVO> selectBuyerList(PagingInfoVO pagingVO) {
		try(
			SqlSession session = sqlSessionFactory.openSession();
				){
			IBuyerDAO mapper = session.getMapper(IBuyerDAO.class);
			return mapper.selectBuyerList(pagingVO);
		}
	}
	

	@Override
	public long selectTotalCount(PagingInfoVO pagingVO) {
		try(
				SqlSession session =  sqlSessionFactory.openSession();
				){
			IBuyerDAO mapper =session.getMapper(IBuyerDAO.class);
			return mapper.selectTotalCount(pagingVO);
		}
	}

	@Override
	public int insertBuyer(BuyerVO buyer) {
		try(
			SqlSession session = sqlSessionFactory.openSession();
		){
			IBuyerDAO mapper=session.getMapper(IBuyerDAO.class);
			int rowCount = mapper.insertBuyer(buyer);
			if(rowCount>0) {
				session.commit();
			}
			return rowCount;
		}
	}

	@Override
	public long countBuyer(String buyer_lgu) {
		try(
			SqlSession session = sqlSessionFactory.openSession();
		){
			IBuyerDAO mapper =session.getMapper(IBuyerDAO.class);
			return mapper.countBuyer(buyer_lgu);
		}
	}

	@Override
	public int updateBuyer(BuyerVO buyer) {
		try(
				SqlSession session= sqlSessionFactory.openSession();
		){
			IBuyerDAO mapper = session.getMapper(IBuyerDAO.class);
			int rowCount = mapper.updateBuyer(buyer);
			if(rowCount>0) {
				session.commit();
			}
			return rowCount;
		}
	}
}
