package kr.or.ddit.db.ibatis;

import java.io.IOException;
import java.io.Reader;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

//이제 이 ibatis를 어디에서 사용하는가 -> DAO
public class CustomSqlMapClientBuilder {
	private static SqlMapClient sqlMapClient;
	static {
		try(
				Reader reader = Resources.getResourceAsReader("kr/or/ddit/db/ibatis/SqlMapConfig.xml");
			) {
				sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);//static의 코드블럭이니깐 한번만 실행(싱글톤으로 관리됨)
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static SqlMapClient getSqlMapClient() {
		return sqlMapClient;
			
	}
}
