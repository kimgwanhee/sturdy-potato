package kr.or.ddit.board.dao;

import kr.or.ddit.vo.PdsVO;

/**
 * @author KGH
 * @since 2018. 12. 4.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2018. 12. 4.      KGH       첨부파일 관리를 위한 persistence layer
 * Copyright (c) 2018 by DDIT All right reserved
 * </pre>
 */
public interface IPdsDAO {
	
	/**
	 * 파일 등록(메타데이터만..)
	 * @param pds
	 * @return row count
	 */
	public int insertPds(PdsVO pds);
	
	/**
	 * 다운로드용으로 사용될 조회 메소드
	 * @param pds_no
	 * @return 존재하지 않는다면, null반환
	 */
	public PdsVO selectPds(long pds_no);
	
	/**
	 * 게시글 수정시 파일 삭제를 위한 메소드
	 * @param pds_no
	 * @return row count
	 */
	public int deletePds(long pds_no);
	
}
