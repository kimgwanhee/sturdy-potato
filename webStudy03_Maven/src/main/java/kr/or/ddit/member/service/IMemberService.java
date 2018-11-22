package kr.or.ddit.member.service;

import java.util.List;

import kr.or.ddit.ServiceResult;
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
 * 2018. 11. 21.      KGH      회원관리를 위한 Business Logic Layer
 * Copyright (c) 2018 by DDIT All right reserved
 * </pre>
 */

public interface IMemberService {
	
	/**
	 * 회원 신규 등록
	 * @param member
	 * @return PKDUPLICATED, OK, FAILED
	 */
	public ServiceResult registMember(MemberVO member);
	
	/**
	 * 회원 목록 조회
	 * @return 존재하지 않을 때, size()==0
	 */
	public List<MemberVO> retrieveMemberList();
	
	/**
	 * 회원 정보 상세 조회
	 * @param mem_id 조회할 회원의 아이디
	 * @return 존재하지 않는다면, CommonException발생
	 */
	public MemberVO retrieveMember(String mem_id);
	
	/**
	 * 회원 정보 수정
	 * @param member
	 * @return CommonException, INVALIDPASSWORD, OK, FAILED
	 */
	public ServiceResult modifyMember(MemberVO member);
	
	//처음 존재하지않는지..CommonException
	//본인맞는지 인증 INVALIDPASSWORD
	//그 후 탈퇴 > 성공아니면 실패
	/**
	 * 회원 탈퇴
	 * @param member
	 * @return CommonException, INVALIDPASSWORD, OK, FAILED
	 */
	public ServiceResult removeMember(MemberVO member);
	
}
