package kr.or.ddit.buyer.service;

import java.util.List;

import kr.or.ddit.CommonException;
import kr.or.ddit.ServiceResult;
import kr.or.ddit.buyer.dao.BuyerDAOImpl;
import kr.or.ddit.buyer.dao.IBuyerDAO;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingInfoVO;

public class BuyerServiceImpl implements IBuyerService {
	IBuyerDAO buyerDao = new BuyerDAOImpl();


	@Override
	public BuyerVO retrieveBuyer(String buyer_id) {
		BuyerVO result = buyerDao.selectBuyer(buyer_id);
		if (result == null) {
			throw new CommonException();
		}
		return result;
	}

	@Override
	public ServiceResult registBuyer(BuyerVO buyer) {
		ServiceResult result = ServiceResult.FAILED;
		long count = buyerDao.countBuyer(buyer.getBuyer_lgu());
		String code = String.format("%s%02d", buyer.getBuyer_lgu(), count + 1);
		buyer.setBuyer_id(code);
		int check = buyerDao.insertBuyer(buyer);
		if (check > 0) {
			result = ServiceResult.OK;
		}
		return result;
	}

	@Override
	public ServiceResult modifyBuyer(BuyerVO buyer) {
		ServiceResult result = ServiceResult.FAILED;
		int check = buyerDao.updateBuyer(buyer);
		if(check>0) {
			result = ServiceResult.OK;
		}
		return result;
	}

	@Override
	public List<BuyerVO> retrieveBuyerList(PagingInfoVO pagingVO) {
		List<BuyerVO> result = buyerDao.selectBuyerList(pagingVO);
		return result;
	}

	@Override
	public long retrieveBuyerCount(PagingInfoVO pagingVO) {
		return buyerDao.selectTotalCount(pagingVO);
	}
}
