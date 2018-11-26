package kr.or.ddit.prod.service;

import java.util.List;

import kr.or.ddit.ServiceResult;
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
 * 2018. 11. 26.      KGH       상품관리를 위한 Business Logic Layer
 * Copyright (c) 2018 by DDIT All right reserved
 * </pre>
 */

public interface IProdService {
	
	/**
	 * 상품 신규 등록
	 * @param prod 등록할 상품 정보
	 * @return OK, FAILED
	 */
	public ServiceResult creatProd(ProdVO prod);
	
	/**
	 * 상품 상제 조회
	 * @param prod_id 조회할 상품 코드
	 * @return 존재하지 않는 경우, CommonException 발생
	 */
	public ProdVO retrieveProd(String prod_id);
	
	/**
	 * 상품 레코드 수 
	 * @param pagingVO
	 * @return
	 */
	public long retrieveProdCount(PagingInfoVO<ProdVO> pagingVO);
	
	/**
	 * 페이징 처리된 상품 목록 조회
	 * @param pagingVO
	 * @return 존재하지 않으면, size()==0
	 */
	public List<ProdVO> retrieveProdList(PagingInfoVO<ProdVO> pagingVO);
	
	/**
	 * 상품 정보 수정
	 * @param prod
	 * @return CommonException, OK, FAILED
	 */
	public ServiceResult modifyPord(ProdVO prod);
}
