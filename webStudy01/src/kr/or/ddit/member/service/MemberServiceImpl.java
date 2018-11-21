package kr.or.ddit.member.service;

import java.util.List;

import kr.or.ddit.CommonException;
import kr.or.ddit.ServiceResult;
import kr.or.ddit.member.dao.IMemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.vo.MemberVO;

public class MemberServiceImpl implements IMemberService {
	IMemberDAO memberDAO = new MemberDAOImpl();//실행코드의 캡슐화 !!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	@Override
	public ServiceResult registMember(MemberVO member) {
		ServiceResult result = null;
		if(memberDAO.selectMember(member.getMem_id())==null) {
			int rowCnt = memberDAO.insertMember(member);
			if(rowCnt >0) {
				result=ServiceResult.OK;
			}else {
				result=ServiceResult.FAILED;
			}
		}else {
			result = ServiceResult.PKDUPLICATED;
		}
		return result;
	}

	@Override
	public List<MemberVO> retrieveMemberList() {
		List<MemberVO> memberList = memberDAO.selecteMemberList();
		return memberList;
	}

	@Override
	public MemberVO retrieveMember(String mem_id) {
		MemberVO mv = memberDAO.selectMember(mem_id);
		if(mv == null) {
			throw new CommonException();//CommonException이것도 runtimeException이지만 저렇게하면 창에 CommonException이라도 띄워지니 찾기쉬움
			//사용자 정의 예외발생
		}
		return mv;
	}

	@Override
	public ServiceResult modifyMember(MemberVO member) {
		return null;
	}

	@Override
	public ServiceResult removeMember(MemberVO member) {
		return null;
	}

}
