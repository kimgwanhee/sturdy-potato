package kr.or.ddit.member.service;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.member.dao.IMemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.member.dao.MemberDAOImpl_Simple;
import kr.or.ddit.vo.MemberVO;

public class AuthenticateServiceImpl implements IAuthenticateService{
	IMemberDAO memberDAO = new MemberDAOImpl();//서비스가 다오를 의존한다. 의존객체생성
	
//	public static enum ServiceResult{ PKNOTFOUND, INVALIDPASSWORD }
	@Override
	public Object authenticate(MemberVO member) {//Object오류남-> 시그니처는 인터페이스에서 결정 alt+shift+c에서 object로 변경
		Object result = null;
		MemberVO savedMember = memberDAO.selectMember(member.getMem_id());
		if(savedMember!=null) {
			if(savedMember.getMem_pass().equals(member.getMem_pass())) {
				result = savedMember;
			}else {//아이디는 있는데 비번은 틀림
				result = ServiceResult.INVALIDPASSWORD;
			}
		}else {//해당아이디없음
			result = ServiceResult.PKNOTFOUND;
		}
		return result;
	}
}
