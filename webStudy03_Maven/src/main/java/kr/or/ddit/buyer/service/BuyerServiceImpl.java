package kr.or.ddit.buyer.service;

import java.util.List;

import kr.or.ddit.CommonException;
import kr.or.ddit.ServiceResult;
import kr.or.ddit.buyer.dao.BuyerDAOImpl;
import kr.or.ddit.buyer.dao.IBuyerDAO;
import kr.or.ddit.vo.BuyerVO;

public class BuyerServiceImpl implements IBuyerService{
	IBuyerDAO buyerDao = new BuyerDAOImpl();
	
	@Override
	public List<BuyerVO> retrieveBuyerList() {
		List<BuyerVO> result = buyerDao.selectBuyerList();
		return result;
	}

	@Override
	public BuyerVO retrieveBuyer(String buyer_id) {
		BuyerVO result = buyerDao.selectBuyer(buyer_id);
		if(result==null) {
			throw new CommonException();
		}
		return result;
	}

	@Override
	public ServiceResult registBuyer(BuyerVO buyer) {
		ServiceResult result = ServiceResult.FAILED;
		BuyerVO checkBuyer = retrieveBuyer(buyer.getBuyer_id());
		if(checkBuyer!=null) {
			result = ServiceResult.PKDUPLICATED;
		}else {
			int check = buyerDao.insertBuyer(buyer);
			if(check>0) {
				result = ServiceResult.OK;
			}
		}
		return result;
	}
	
}
