
package kr.or.ddit.board.service;

import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.BoardException;
import kr.or.ddit.board.dao.IReplyDAO;
import kr.or.ddit.board.dao.ReplyDAOImpl;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.ReplyVO;

public class ReplyServiceImpl implements IReplyService {
	IReplyDAO replyDAO = new ReplyDAOImpl();

	@Override
	public ServiceResult createReply(ReplyVO reply) {
		ServiceResult sr = ServiceResult.FAILED;
		int result = replyDAO.insertReply(reply);
		if (result > 0) {
			sr = ServiceResult.OK;
		}
		return sr;
	}

	@Override
	public long retriveReplyCount(PagingInfoVO<ReplyVO> pagingVO) {
		return replyDAO.selectTotalRecord(pagingVO);
	}

	@Override
	public List<ReplyVO> retriveReplyList(PagingInfoVO<ReplyVO> pagingVO) {
		return replyDAO.selectReplyList(pagingVO);
	}

	@Override
	public ServiceResult modifyReply(ReplyVO reply) {
		ServiceResult sr = ServiceResult.FAILED;
		ReplyVO checkVO = replyDAO.selectReply(reply.getRep_no());
		if(checkVO == null) {
			throw new BoardException();
		}
		if(reply.getRep_no().equals(checkVO.getRep_no())) {
			int rowCount = replyDAO.updateReply(reply);
			if(rowCount >0) {
				sr = ServiceResult.OK;
			}
		}else {
			sr = ServiceResult.INVALIDPASSWORD;
		}
		return sr;
	}

	@Override
	public ServiceResult removeReply(ReplyVO reply) {
		ServiceResult result = ServiceResult.FAILED;
		ReplyVO checkVO = replyDAO.selectReply(reply.getRep_no());
		if (checkVO == null) {
			throw new BoardException();
		}
		if (reply.getRep_pass().equals(checkVO.getRep_pass())) {
			int rowCount = replyDAO.deleteReply(reply.getRep_no());
			if (rowCount > 0) {
				result = ServiceResult.OK;
			}
		} else {
			result = ServiceResult.INVALIDPASSWORD;
		}
		return result;
	}
}
