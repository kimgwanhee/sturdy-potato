package kr.or.ddit.prod.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.w3c.dom.css.CSSMediaRule;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.db.ibatis.CustomSqlMapClientBuilder;
import kr.or.ddit.vo.BuyerVO;

public class OtherDAOImpl implements IOtherDAO {
	SqlMapClient sqlMapClient = CustomSqlMapClientBuilder.getSqlMapClient();

	@Override
	public Map<String, String> selectLprodList() {
		try {
				return sqlMapClient.queryForMap("Other.selectLprodList", null, "LPROD_GU", "LPROD_NM");
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
	}

	@Override
	public List<BuyerVO> selectBuyerList(String buyer_lgu) {
		try {
				return sqlMapClient.queryForList("Other.selectBuyerList", buyer_lgu);
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
	}
}
