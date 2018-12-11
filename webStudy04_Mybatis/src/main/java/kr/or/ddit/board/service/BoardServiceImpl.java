package kr.or.ddit.board.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.BoardException;
import kr.or.ddit.board.dao.BoardDAOImpl;
import kr.or.ddit.board.dao.IBoardDAO;
import kr.or.ddit.board.dao.IPdsDAO;
import kr.or.ddit.board.dao.PdsDAOImpl;
import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.PdsVO;

public class BoardServiceImpl implements IBoardService {
	IBoardDAO boardDAO = new BoardDAOImpl();
	IPdsDAO pdsDAO = new PdsDAOImpl();
	
	//저장 위치insert
	File saveFolder = new File("d:/boardFiles");
	{
		if (!saveFolder.exists()) saveFolder.mkdirs();
	}
	private int processFiles(BoardVO board, SqlSession session) {
		int rowCnt = 0;

		List<PdsVO> pdsList = board.getPdsList();
		if (pdsList != null) {
//			check += pdsList.size();
			rowCnt += pdsDAO.insertPdsList(board, session);
			//파일저장 //실제파일을 webserver에 저장
			for (PdsVO pds : pdsList) {
				try (InputStream in = pds.getItem().getInputStream();) {
					FileUtils.copyInputStreamToFile(in, new File(saveFolder, pds.getPds_savename()));
				} catch (IOException e) {

				}
			}
		}
		Long[] delFiles = board.getDelFiles();//배열을 하나 만들고 빽업을할라구..
		
		if (delFiles != null) {//지우려고 하는 파일이 있는지없는지 먼저 확인
			String[] saveNames = new String[delFiles.length];//getPds_savename빽업한거임
			for (int idx = 0; idx < delFiles.length; idx++) {
				saveNames[idx] = pdsDAO.selectPds(delFiles[idx]).getPds_savename();
				rowCnt += pdsDAO.deletePds(delFiles[idx], session);
			}
			// 파일삭제 //진짜 데이터 지우기 -> 빽업위에 떠둔걸 활용
			for (String savename : saveNames) {
				FileUtils.deleteQuietly(new File(saveFolder, savename));
			}
		}
		
		return rowCnt;
	}

	@Override
	public ServiceResult createBoard(BoardVO board) {
		try (SqlSession session = CustomSqlSessionFactoryBuilder.getSqlSessionFactory().openSession();) {
			int rowCnt = boardDAO.insertBoard(board, session);
			int check = 1;
			if (rowCnt > 0) {
				if(board.getPdsList()!=null) 
				check += board.getPdsList().size();
				rowCnt += processFiles(board, session);
			}
			ServiceResult result = ServiceResult.FAILED;
			if (rowCnt >= check) {
				result = ServiceResult.OK;
				session.commit();
			}
			return result;
		}
	}

	@Override
	public long retriveBoardCount(PagingInfoVO<BoardVO> pagingVO) {
		return boardDAO.selectTotalRecord(pagingVO);
	}

	@Override
	public List<BoardVO> retriveBoardList(PagingInfoVO<BoardVO> pagingVO) {
		List<BoardVO> result = boardDAO.selectBoardList(pagingVO);
		return result;
	}

	@Override
	public BoardVO retriveBoard(long bo_no) {
		BoardVO result = boardDAO.selectBoard(bo_no);
		if (result == null) {
			throw new BoardException();
		}
		return result;
	}

	@Override
	public ServiceResult modifyBoard(BoardVO board) {
		try (
				SqlSession session = CustomSqlSessionFactoryBuilder.getSqlSessionFactory().openSession(false);
		) {
			BoardVO saveBoard = retriveBoard(board.getBo_no());//없으면 여기서 boardexception이 내부에서 발생할것 
			ServiceResult result = null;
			
			if (saveBoard.getBo_pass().equals(board.getBo_pass())) {//비번이 서로 같다면 ..
				//이제 수정 시작 .. 트랜젝션 시작이라는 게 필요 //-> 위 try()문 -> 커밋을 하면서 트랜잭션 종료를 자동으로 하기위해 넣어둔것 ! 
				int rowCnt = boardDAO.updateBoard(board, session);
				int check = rowCnt;
				if (rowCnt > 0) {//게시글 수정 성공
					if(board.getPdsList()!=null) {
						check+= board.getPdsList().size();
					}
					if(board.getDelFiles()!=null) {
						check += board.getDelFiles().length;
					}
					rowCnt += processFiles(board, session);
				}
				if (rowCnt >= check) {
					session.commit();
					result = ServiceResult.OK;
				} else {
					result = ServiceResult.FAILED;
				} // rowCnt 체크 if end
			} else {//비번이 틀리다면 . . 
				result = ServiceResult.INVALIDPASSWORD;
			}

			return result;
		}
	}

	@Override
	public ServiceResult removeBoard(BoardVO board) {
		try(
				SqlSession session = CustomSqlSessionFactoryBuilder.getSqlSessionFactory().openSession(false);
				//트랜잭션을 관리하는 구조로 만듬
		){
			ServiceResult result = ServiceResult.FAILED;
			BoardVO checkBoard = retriveBoard(board.getBo_no());
			//1. 자식들까지 조회할수있는 쿼리문 작성해야함 -> 그 후 자식까지 일일히 지우기
			//2. 정크파일(쓰레기) 데이타가 끊겨있는 파일을 일정기간마다 점검해서.. batchjob 
			
			if(board.getBo_pass().equals(checkBoard.getBo_pass())) {
				int rowCnt = boardDAO.deleteBoard(board.getBo_no(), session);
				if(rowCnt>0) {
					List<PdsVO> pdsList = checkBoard.getPdsList();
					if(pdsList!=null) {
						for(PdsVO pds : pdsList) {
							FileUtils.deleteQuietly(new File(saveFolder, pds.getPds_savename()));
						}
					}//첨부파일 체크 if end
					result = ServiceResult.OK;
					session.commit();
				}else {
					result = ServiceResult.FAILED;
				}
			}else {
				result = ServiceResult.INVALIDPASSWORD;
			}
			return result;
		}
	}

	@Override
	public PdsVO downloadPds(long pds_no) {
		PdsVO pds = pdsDAO.selectPds(pds_no);
		if (pds == null) {
			throw new BoardException();
		}
		return pds;
	}

}
