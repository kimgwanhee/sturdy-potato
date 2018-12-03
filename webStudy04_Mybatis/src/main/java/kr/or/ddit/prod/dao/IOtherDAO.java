package kr.or.ddit.prod.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.BuyerVO;

public interface IOtherDAO {
	/**
	 * 상품 카테고리 조회
	 * @return key : 상품코드, value : 상품명
	 */
	public List<Map<String, Object>> selectLprodList();
	
	/**
	 * 특정 상품분류에 해당하는 거래처 목록 조회
	 * @param buyer_lgu
	 * @return
	 */
	public List<BuyerVO> selectBuyerList(@Param("buyer_lgu") String buyer_lgu);
}
