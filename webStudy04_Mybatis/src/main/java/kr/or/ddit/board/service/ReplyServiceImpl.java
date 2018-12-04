package kr.or.ddit.board.service;

import java.util.List;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.dao.IReplyDAO;
import kr.or.ddit.board.dao.ReplyDAOImpl;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.ReplyVO;

public class ReplyServiceImpl implements IReplyService {
	IReplyDAO replyDAO = new ReplyDAOImpl();

	@Override
	public ServiceResult createReply(ReplyVO reply) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long retriveReplyCount(PagingInfoVO<ReplyVO> pagingVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ReplyVO> retriveReplyList(PagingInfoVO<ReplyVO> pagingVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResult modifyReply(ReplyVO reply) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResult removeReply(ReplyVO reply) {
		// TODO Auto-generated method stub
		return null;
	}

}
