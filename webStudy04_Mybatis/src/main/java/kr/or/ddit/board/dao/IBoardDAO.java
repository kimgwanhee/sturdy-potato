package kr.or.ddit.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingInfoVO;

/**
 * @author KGH
 * @since 2018. 12. 4.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2018. 12. 4.      KGH       게시글 관리를 위한 Persistence Layer
 * Copyright (c) 2018 by DDIT All right reserved
 * </pre>
 */
public interface IBoardDAO {
	
	/**
	 * 게시글 작성
	 * @param board
	 * @param session TODO
	 * @return
	 */
	public int insertBoard(BoardVO board, SqlSession session);
	
	/**
	 * 검색과 페이징 처리를 위해 검색조건에 맞는 게시글 수 조회
	 * @param pagingVO 검색조건을 가진 VO
	 * @return 조건에 맞는 글이 없다면, 0 반환.
	 */
	public long selectTotalRecord(PagingInfoVO<BoardVO> pagingVO);
	
	/**
	 * 검색 조건에 맞는 게시글 목록 조회
	 * @param pagingInfoVO 검색조건과 페이징 속성을 가진 VO
	 * @return 조건에 맞는 글이 없다면 , size()==0
	 */
	public List<BoardVO> selectBoardList(PagingInfoVO<BoardVO> pagingInfoVO);
	
	/**
	 * 게시글 조회
	 * @param bo_no 글번호
	 * @return 없다면 , null반환
	 */
	public BoardVO selectBoard(long bo_no);
	
	/**
	 * 글 조회수 증가
	 * @param bo_no
	 * @return 
	 */
	public void incrementHit(long bo_no);
	
	/**
	 * 글 추천수 증가
	 * @param bo_no
	 * @return 
	 */
	public int incrementRcmd(long bo_no);
	
	/**
	 * 글 수정
	 * @param board
	 * @param session TODO
	 * @return row count
	 */
	public int updateBoard(BoardVO board, SqlSession session);
	
	/**
	 * 글 삭제
	 * @param bo_no
	 * @param session TODO
	 * @return row count
	 */
	public int deleteBoard(long bo_no, SqlSession session);
}
