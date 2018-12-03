package kr.or.ddit.prod.dao;

import java.util.List;

import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.ProdVO;

/**
 * 
 * @author KGH
 * @since 2018. 11. 26.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2018. 11. 26.      KGH       상품관리를 위한 Persistence Layer
 * Copyright (c) 2018 by DDIT All right reserved
 * </pre>
 */
public interface IProdDAO {
	/**
	 * 신규 상품 등록
	 * @param prod 등록할 상품의 정보를 가진 VO
	 * @return 신규 등록된 상품코드
	 * 상품의 코드는 입력받지 않고 상품코드를 생성 -> 나중에 return값으로 내보내서 신규등록을 하자 .
	 */
	public int insertProd(ProdVO prod);
	
	/**
	 * 상품정보 상세조회
	 * @param prod_id 조회할 상품 코드
	 * @return 존재하지 않으면, null반환
	 */
	public ProdVO selectProd(String prod_id);
	
	/**
	 * 페이징 처리를 위해 전체 상품 수 조회
	 * @param pagingVO
	 * @return
	 */
	public long selectTotalRecord(PagingInfoVO<ProdVO> pagingVO);
	
	/**
	 * 페이징 처리된 상품 목록 조회
	 * @param pagingVO
	 * @return 존재하지 않으면, size() == 0 
	 */
	public List<ProdVO> selectPordList(PagingInfoVO<ProdVO> pagingVO);
	
	/**
	 * 상품 정보 수정
	 * @param prod 수정할 정보를 가진 VO
	 * @return 수정 성공 여부 (>0)
	 */
	public int updateProd(ProdVO prod);
}
