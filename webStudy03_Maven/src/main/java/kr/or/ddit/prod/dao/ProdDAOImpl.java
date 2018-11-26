package kr.or.ddit.prod.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.db.ibatis.CustomSqlMapClientBuilder;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.ProdVO;

public class ProdDAOImpl implements IProdDAO {
	SqlMapClient sqlMapClient = CustomSqlMapClientBuilder.getSqlMapClient(); //빌더로 부터 받아옴
	//sqlMapClient로 queryforob~ list~등 사용가능해짐

	@Override
	public String insertProd(ProdVO prod) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProdVO selectProd(String prod_id) {
		try {
				return (ProdVO) sqlMapClient.queryForObject("Prod.selectProd", prod_id);
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
	}

	@Override
	public long selectTotalRecord(PagingInfoVO<ProdVO> pagingVO) {
		try {
				return (Long) sqlMapClient.queryForObject("Prod.selectTotalRecord", pagingVO);//Long 랩퍼타입..?
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
	}

	@Override
	public List<ProdVO> selectPordList(PagingInfoVO<ProdVO> pagingVO) {
		try {
				return sqlMapClient.queryForList("Prod.selectProdList", pagingVO);
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
	}

	@Override
	public int updateProd(ProdVO prod) {
		// TODO Auto-generated method stub
		return 0;
	}

}
