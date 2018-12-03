package kr.or.ddit.member.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;


import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingInfoVO;

public class MemberDAOImpl implements IMemberDAO{
   
   SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();
   
   @Override
   public int insertMember(MemberVO member) {
      try(
            SqlSession sqlSession =  sqlSessionFactory.openSession();
            
      ){
         IMemberDAO mapper= sqlSession.getMapper(IMemberDAO.class);
         int result = mapper.insertMember(member);
         if(result>0) sqlSession.commit();
         
         return result;
      }
   }

   @Override
   public MemberVO selectMember(String mem_id) {
      try(
            SqlSession sqlSession =  sqlSessionFactory.openSession();
      ){
         //getMapper -> 
         IMemberDAO mapper= sqlSession.getMapper(IMemberDAO.class);
         return mapper.selectMember(mem_id);
      }
       
   }

   @Override
   public long selectTotalRecord(PagingInfoVO vo) {
      try(
         SqlSession sqlSession =  sqlSessionFactory.openSession();
      ){
         IMemberDAO mapper = sqlSession.getMapper(IMemberDAO.class);
         return mapper.selectTotalRecord(vo);
//         return sqlSession.selectOne("kr.or.ddit.member.dao.IMemberDAO.selectTotalRecord",vo);
      }
   }

   @Override
   public List<MemberVO> selecteMemberList(PagingInfoVO pagingVO) {
      try(
         SqlSession sqlSession =  sqlSessionFactory.openSession();
      ){
         IMemberDAO mapper = sqlSession.getMapper(IMemberDAO.class);
         return mapper.selecteMemberList(pagingVO);
         
//         return sqlSession.selectList("kr.or.ddit.member.dao.IMemberDAO.selectAllMember",pagingVO);
      }
   }

   @Override
   public int updateMember(MemberVO member) {
      try(
            SqlSession sqlSession =  sqlSessionFactory.openSession();
         ){
            IMemberDAO mapper = sqlSession.getMapper(IMemberDAO.class);
            int result = mapper.updateMember(member);
            if(result>0) sqlSession.commit();
            
            return result;
//            return sqlSession.selectList("kr.or.ddit.member.dao.IMemberDAO.selectAllMember",pagingVO);
         }
   }

   @Override
   public int deleteMember(String mem_id) {
      try(
            SqlSession sqlSession =  sqlSessionFactory.openSession();
         ){
            IMemberDAO mapper = sqlSession.getMapper(IMemberDAO.class);
            int result =  mapper.deleteMember(mem_id);
            if(result>0) sqlSession.commit();
            
            return result;
//            return sqlSession.selectList("kr.or.ddit.member.dao.IMemberDAO.selectAllMember",pagingVO);
         }
   }

    
}