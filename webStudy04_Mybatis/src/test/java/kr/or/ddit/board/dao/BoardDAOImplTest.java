package kr.or.ddit.board.dao;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingInfoVO;

public class BoardDAOImplTest {
	static Logger logger = LoggerFactory.getLogger(BoardDAOImplTest.class);
	IBoardDAO boardDAO = new BoardDAOImpl();
	PagingInfoVO<BoardVO> pagingVO;
	BoardVO board;
	SqlSession session;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
//		logger.debug("{} 테스트 클래스 loading", BoardDAOImplTest.class.getSimpleName());
		System.out.printf("%s 테스트 클래스 loading \n", BoardDAOImplTest.class.getSimpleName());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
//		logger.debug("{} 테스트 클래스 unloading", BoardDAOImplTest.class.getSimpleName());
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
		session = CustomSqlSessionFactoryBuilder.getSqlSessionFactory().openSession(false);
		
	}

	@After
	public void tearDown() throws Exception {
		System.out.printf("단위 테스트 모듈 수행 후 !! \n");
		session.close();
		
	}

	@Test
	public void testInsertBoard() {
		int rowCnt = boardDAO.insertBoard(board, session);
		assertEquals(1, rowCnt);
	}

	@Test
	public void testSelectTotalRecord() {
		long totalRecord = boardDAO.selectTotalRecord(pagingVO);
		assertNotSame(0, totalRecord);//기대하는값, 실제값
	}

	@Test
	public void testSelectBoardList() {
		pagingVO.setCurrentPage(1);
		List<BoardVO> boardList = boardDAO.selectBoardList(pagingVO);
				//위와 같은 객체 아니다. setUp()에서 pagingvo를 만드는데 단위테스트마다 달라짐->새로만듬
		assertNotNull(boardList);//boardList가 null이 아닐꺼다 -> 성공하면 텍스트에 떠야겠져
		System.out.println(boardList.size());
		assertNotSame(0, boardList.size());//기대하는값, 실제값
		assertThat(boardList.size(), greaterThan(0));
		BoardVO test = new BoardVO();
		test.setBo_no((long)183);
		test.setBo_writer("관희");
		assertThat(boardList, hasItem(test));
	}

	@Test
	public void testSelectBoard() {
		BoardVO board = boardDAO.selectBoard(183);
		assertNotNull(board);
//		assertNull("조회된 글은 null이 아닌것 같음.", board);
		assertThat(board, instanceOf(BoardVO.class));
		assertThat(board, notNullValue());
	}

//	@Test(timeout=1, expected=SQLException.class)
	@Test(timeout=1000)
	public void testIncrementHit() {
		boardDAO.incrementHit(183);
	}

	@Test
	public void testUpdateBoard() {
		int rowCnt = boardDAO.updateBoard(board, session);
		assertEquals(1, rowCnt);
		
	}

	@Test
	public void testDeleteBoard() {
		int rowCnt = boardDAO.deleteBoard(board.getBo_no(), session);
		assertEquals(1, rowCnt);
	}

}
