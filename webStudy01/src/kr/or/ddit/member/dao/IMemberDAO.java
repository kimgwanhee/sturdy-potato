package kr.or.ddit.member.dao;

import java.util.List;

import kr.or.ddit.vo.MemberVO;
/**
 * 
 * @author KGH
 * @since 2018. 11. 21.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2018. 11. 21.      KGH       회원관리를 위한 
 * Copyright (c) 2018 by DDIT All right reserved
 * </pre>
 */


public interface IMemberDAO {
	/**
	 * 회원 신규 등록
	 * @param member 등록할 회원의 정보를 가진 VO
	 * @return 성공(return >0), 실패 
	 */
	public int insertMember(MemberVO member);	//mybatis는 아아디를 기준으로 mapper가 만들어진다
	
	/**
	 * 회원 목록 조회
	 * @return 존재하지 않는다면 , size()==0
	 */
	public List<MemberVO> selecteMemberList();
	/**
	 * 회원정보 상세 조회
	 * @param mem_id 조회할 회원의 아이디
	 * @return 존재하지 않는다면, null반환(queryForObject특징)
	 */
	public MemberVO selectMember(String mem_id);//리턴타입은 조회되는 데이타를 다 담을 VO
	
	/**
	 * 회원 정보 수정
	 * @param member 수정할 정보를 가진 VO
	 * @return 성공(>0) 실페
	 */
	public int updateMember(MemberVO member);
	
	/**
	 * 회원 정보 삭제
	 * @param mem_id 삭제할 회원의 아이디
	 * @return 성공(>0) 실패
	 */
	public int deleteMember(String mem_id);

}
