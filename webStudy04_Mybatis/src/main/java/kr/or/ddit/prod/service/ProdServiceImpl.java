package kr.or.ddit.prod.service;

import java.util.List;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

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
		int result = prodDAO.insertProd(prod);
		if(result >0) {
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
		retrieveProd(prod.getProd_id());
		//상품이 존재할경우
		int rowCnt = prodDAO.updateProd(prod);
		ServiceResult result = null;
		if(rowCnt >0) {
			result= ServiceResult.OK;
		}else {
			result= ServiceResult.FAILED;
		}
		return result;
	}

}
