package kr.or.ddit.prod.service;

import java.util.List;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.prod.dao.IProdDAO;
import kr.or.ddit.prod.dao.ProdDAOImpl;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.ProdVO;

public class ProdServiceImpl implements IProdService {
	
	IProdDAO prodDAO = new ProdDAOImpl();

	@Override
	public ServiceResult creatProd(ProdVO prod) {
		ServiceResult sr = ServiceResult.FAILED;
		String result = prodDAO.insertProd(prod);
		if(result != null) {
			sr = ServiceResult.OK;
		}
		return sr;
	}

	@Override
	public ProdVO retrieveProd(String prod_id) {
		return prodDAO.selectProd(prod_id);
	}

	@Override
	public long retrieveProdCount(PagingInfoVO<ProdVO> pagingVO) {
		return prodDAO.selectTotalRecord(pagingVO);
	}

	@Override
	public List<ProdVO> retrieveProdList(PagingInfoVO<ProdVO> pagingVO) {
		List<ProdVO> prodList = prodDAO.selectPordList(pagingVO);
		return prodList;
	}

	@Override
	public ServiceResult modifyPord(ProdVO prod) {
		// TODO Auto-generated method stub
		return null;
	}

}
