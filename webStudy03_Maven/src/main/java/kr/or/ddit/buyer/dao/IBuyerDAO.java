package kr.or.ddit.buyer.dao;

import java.util.List;

import kr.or.ddit.vo.BuyerVO;

public interface IBuyerDAO {
	/**
	 * Buyer의 상세정보를 검색
	 * @param buyer_id 검색하려는 buyer_id
	 * @return 검색하려는 buyer의 상세정보
	 */
	public BuyerVO selectBuyer(String buyer_id);
	
	/**
	 * Buyer 전체 리스트 출력
	 * @return buyer리스트
	 */
	public List<BuyerVO> selectBuyerList();
	
	/**
	 * Buyer 등록
	 * @param buyer_id
	 * @return 
	 */
	public int insertBuyer(BuyerVO buyer);
}
