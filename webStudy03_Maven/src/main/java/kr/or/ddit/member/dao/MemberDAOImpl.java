package kr.or.ddit.member.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.db.ibatis.CustomSqlMapClientBuilder;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingInfoVO;

public class MemberDAOImpl implements IMemberDAO{

	SqlMapClient sqlMapClient = CustomSqlMapClientBuilder.getSqlMapClient();
	@Override
	public MemberVO selectMember(String mem_id) {
		try {
			MemberVO member = (MemberVO) sqlMapClient.queryForObject("Member.selectMember", mem_id);
			return member;
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public int insertMember(MemberVO member) {//매개변수가 object라면 inset말고 update, delete를 사용하자..ibtis입장에선 내부적으론 어차피 excuteupdate를 사용하기때문
											  //리턴타입 pk를 직접 생성하면 insert(<selectKey>)
		try {
				return sqlMapClient.update("Member.insertMember", member);//update로쓴것은int를 long으로 담아오기위해서..?
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
	}
	@Override
	public List<MemberVO> selecteMemberList(PagingInfoVO pagingVO) {
		try {
			return sqlMapClient.queryForList("Member.selecteMemberList", pagingVO);
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public int updateMember(MemberVO member) {
		try {
			return sqlMapClient.update("Member.updateMember", member);
		}catch (SQLException e) {	//내가 만든 쿼리나 오라클디비 예외들잡는것 
			throw new RuntimeException(e);
		}
	}
	@Override
	public int deleteMember(String mem_id) {
		try {
			return sqlMapClient.delete("Member.deleteMember", mem_id);
		}catch (SQLException e) {	//내가 만든 쿼리나 오라클디비 예외들잡는것 
			throw new RuntimeException(e);
		}
	}
	@Override
	public long selectTotalRecord(PagingInfoVO pagingVO) {
		try {
			return (Long) sqlMapClient.queryForObject("Member.selectTotalRecord");
		}catch (SQLException e) {
			throw new RuntimeException();
		}
	}
}
