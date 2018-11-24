package kr.or.ddit.buyer.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.db.ibatis.CustomSqlMapClientBuilder;
import kr.or.ddit.vo.BuyerVO;

public class BuyerDAOImpl implements IBuyerDAO{
	SqlMapClient sqlMapClient = CustomSqlMapClientBuilder.getSqlMapClient();
	
	@Override
	public BuyerVO selectBuyer(String buyer_id) {
		try {
				return (BuyerVO) sqlMapClient.queryForObject("Buyer.selectBuyer", buyer_id);
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
	}

	@Override
	public List<BuyerVO> selectBuyerList() {
		try {
				return sqlMapClient.queryForList("Buyer.selectBuyerList");
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
	}

	@Override
	public int insertBuyer(BuyerVO buyer) {
		try {
				return sqlMapClient.update("Buyer.insertBuyer",buyer);
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
	}
}
