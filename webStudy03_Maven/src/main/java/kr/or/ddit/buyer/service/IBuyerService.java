package kr.or.ddit.buyer.service;

import java.util.List;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingInfoVO;

public interface IBuyerService {

	/**
	 * buyer 목록 조회
	 * @param pagingVO TODO
	 * @return 존재하지 않을 때, size()==0
	 */
	public List<BuyerVO> retrieveBuyerList();
	
	/**
	 * buyer 정보 상세 조회
	 * @param buyer_id 조회할 회원의 아이디
	 * @return 존재하지 않는다면, CommonException발생
	 */
	public BuyerVO retrieveBuyer(String buyer_id);
	
	/**
	 * buyer 신규 등록
	 * @param buyer
	 * @return PKDUPLICATED, OK, FAILED
	 */
	public ServiceResult registBuyer(BuyerVO buyer);
}
