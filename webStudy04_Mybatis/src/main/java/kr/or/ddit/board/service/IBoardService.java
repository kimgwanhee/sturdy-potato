package kr.or.ddit.board.service;

import java.util.List;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.PdsVO;

/**
 * 
 * @author KGH
 * @since 2018. 12. 4.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2018. 12. 4.      KGH       게시글 관리를 위한 Business Logic Layer
 * Copyright (c) 2018 by DDIT All right reserved
 * </pre>
 */
public interface IBoardService {
	
	/**
	 * 새글작성
	 * @param board
	 * @return OK, FAILED
	 */
	public ServiceResult createBoard(BoardVO board);
	
	/**
	 * 검색 조건에 맞는 게시글 수
	 * @param pagingVO 검색조건을 가진 VO
	 * @return 없다면 , 0을 반환
	 */
	public long  retriveBoardCount(PagingInfoVO<BoardVO> pagingVO);
	
	/**
	 * 검색 조건에 맞는 게시글 목록
	 * @param pagingVO 검색조건과 페이징 속성을 가진 VO
	 * @return 없다면 , size()==0
	 */
	public  List<BoardVO> retriveBoardList(PagingInfoVO<BoardVO> pagingVO);
	
	/**
	 * 글 조회
	 * @param bo_no 글번호
	 * @return 없으면, BoardException (unchecked exception) 발생
	 */
	public BoardVO retriveBoard(long bo_no);
	
	/**
	 * 글 수정
	 * @param board
	 * @return BoardException, INVALIDPASSWORD, OK, FAILED
	 */
	public ServiceResult modifyBoard(BoardVO board);
	
	/**
	 * 글 삭제
	 * @param board
	 * @return BoardException, INVALIDPASSWORD, OK, FAILED
	 */
	public ServiceResult removeBoard(BoardVO board);
	
	/**
	 * 첨부파일 다운로드용 메소드
	 * @param pds_no 파일 번호
	 * @return 없다면, BoardException 발생
	 */
	public PdsVO downloadPds(long pds_no);
}
