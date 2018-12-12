package kr.or.ddit.board.service;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.BoardException;
import kr.or.ddit.board.dao.BoardDAOImplTest;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.PdsVO;

public class BoardServiceImplTest {
	static Logger logger = LoggerFactory.getLogger(BoardDAOImplTest.class);
	IBoardService service = new BoardServiceImpl();
	PagingInfoVO<BoardVO> pagingVO;
	BoardVO board;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.printf("%s 테스트 클래스 loading \n", BoardDAOImplTest.class.getSimpleName());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.printf("%s 테스트 클래스 unloading \n", BoardDAOImplTest.class.getSimpleName());
	}

	@Before
	public void setUp() throws Exception {
		System.out.printf("단위 테스트 모듈 수행 전 !! \n ");
		pagingVO = new PagingInfoVO<>();
		pagingVO.setSearchType("content");
		pagingVO.setSearchWord("은대");
		board = new BoardVO();
		board.setBo_no(new Long(183));
		board.setBo_title("수정할 제목");
		board.setBo_content("수정할 내용");
		board.setBo_mail("aaa@aaa");
		board.setBo_writer("송재익");
		board.setBo_pass("1234");
		board.setBo_ip("192.168.205.123");
	}

	@After
	public void tearDown() throws Exception {
		System.out.printf("단위 테스트 모듈 수행 후 !! \n");
	}

	@Test
	public void testCreateBoard() {
		ServiceResult result = service.createBoard(board);
		assertEquals(ServiceResult.OK, result);
	}

	@Test
	public void testRetriveBoardCount() {
		long result = service.retriveBoardCount(pagingVO);
		assertThat(result, greaterThan((long)0));
	}

	@Test
	public void testRetriveBoardList() {
		pagingVO.setCurrentPage(1);
		List<BoardVO> boardList = service.retriveBoardList(pagingVO);
		assertThat(boardList.size(), greaterThan(0));
		assertNotNull(boardList);
	}

	@Test
	public void testRetriveBoard() {
		BoardVO board = service.retriveBoard(169);
		assertNotNull(board);
		assertThat(board, instanceOf(BoardVO.class));
		assertThat(board, notNullValue());
	}

	@Test
	public void testModifyBoard() {
		ServiceResult result = service.modifyBoard(board);
		assertEquals(ServiceResult.OK, result);
	}

	@Test(expected=BoardException.class)
	public void testRemoveBoard() {
		BoardVO board = new BoardVO();
		board.setBo_no(new Long(102));
		board.setBo_pass("1234");
		ServiceResult result = service.removeBoard(board);
		assertEquals(ServiceResult.OK, result);
	}

	@Test(expected=BoardException.class)
	public void testDownloadPds() {
		PdsVO pds = service.downloadPds(new Long(1));
		assertNull(pds);
	}

}
