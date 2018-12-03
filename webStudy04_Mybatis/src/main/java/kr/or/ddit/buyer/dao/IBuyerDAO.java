package kr.or.ddit.buyer.dao;

import java.util.List;

import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingInfoVO;

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
	public List<BuyerVO> selectBuyerList(PagingInfoVO pagingVO);
	
	
	public long selectTotalCount(PagingInfoVO pagingVO);
	
	/**
	 * Buyer 등록
	 * @param buyer_id
	 * @return 
	 */
	public int insertBuyer(BuyerVO buyer);
	
	/**
	 * 코드 생성해주기 위한 buyer_lgu count
	 * @param buyer_lgu
	 * @return
	 */
	public long countBuyer(String buyer_lgu);
	
	/**
	 * Buyer를 수정해주기 위한 메서드
	 * @param buyer_id
	 * @return
	 */
	public int updateBuyer(BuyerVO buyer);
}
