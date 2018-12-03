package kr.or.ddit.member.service;

import java.util.List;

import kr.or.ddit.CommonException;
import kr.or.ddit.ServiceResult;
import kr.or.ddit.member.dao.IMemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingInfoVO;

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
	public List<MemberVO> retrieveMemberList(PagingInfoVO pagingVO) {
		List<MemberVO> memberList = memberDAO.selecteMemberList(pagingVO);
		return memberList;
	}

	@Override
	public MemberVO retrieveMember(String mem_id) {
		MemberVO mv = memberDAO.selectMember(mem_id);
		if(mv == null) {
			throw new CommonException(mem_id+"에 해당하는 회원없음.");//CommonException이것도 runtimeException이지만 저렇게하면 창에 CommonException이라도 띄워지니 찾기쉬움
			//사용자 정의 예외발생
		}
		return mv;
	}

	@Override
	public ServiceResult modifyMember(MemberVO member) {
		//->INVALIDPASS..비번틀리면 memberview.jsp 돌아오는결과 기존비번입력데이타, 메시지
		//-> Fail ->기존입력데이타, 죄송합니다 메시지->를 가지고 memberview
		//-> ok -> 수정성공했으면 memberview로 이동 (redirect로이동) 이동에따라 .jsp나 .do가 될것.
		//통과 못하면- > 정상적으로 수정할수있는곳 memberview.jsp -> 갖고가야할것두가지 기존입력데이타, 왜못했는지 에러메세지
		//memberid를 가지고 거기에 해당하는 객체를 가져와
		//가져온 객체의 비밀번호랑 매개변수로 받은 비밀번호가 일치
		//일치하지 않으면 inbali~내보내기
		//일치하면 update
		
		//성공
			//0>
		//실패
		ServiceResult result = null;
		MemberVO checkMember =  retrieveMember(member.getMem_id());
		
		 if(checkMember.getMem_pass().equals(member.getMem_pass())) {
			 int rowCnt = memberDAO.updateMember(member);
				if(rowCnt >0) {//성공
					result=ServiceResult.OK;
				}else {//실패
					result=ServiceResult.FAILED;
				}
			}else {
				result = ServiceResult.INVALIDPASSWORD;
			}
			return result;
		}
	

	@Override
	public ServiceResult removeMember(MemberVO member) {
		ServiceResult result = null;
		MemberVO checkMember =  retrieveMember(member.getMem_id());
		
		 if(checkMember.getMem_pass().equals(member.getMem_pass())) {
			 int rowCnt = memberDAO.deleteMember(member.getMem_id());
				if(rowCnt >0) {//성공
					result=ServiceResult.OK;
				}else {//실패
					result=ServiceResult.FAILED;
				}
			}else {
				result = ServiceResult.INVALIDPASSWORD;
			}
			return result;
		}



	@Override
	public long retrieveMemberCount(PagingInfoVO pagingVO) {
		return memberDAO.selectTotalRecord(pagingVO);//전체 리코드가 다시 리턴될것
	}
}
