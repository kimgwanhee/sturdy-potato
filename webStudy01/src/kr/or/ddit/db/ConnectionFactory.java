package kr.or.ddit.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import com.sun.swing.internal.plaf.basic.resources.basic;

import oracle.jdbc.pool.OracleDataSource;
import sun.java2d.pipe.SpanShapeRenderer.Simple;

public class ConnectionFactory  {
	private static String url;
	private static String user;
	private static String password;
	private static DataSource dataSource;
	private static String driverClassName;
	
	static {//클래스 로딩 타임에 한번만 실행할려고
		try {//옛날방식......
			//다시 컴파일 할 필요가 없는것은 설정파일에서 여기로 뺌
//			Class.forName("oracle.jdbc.driver.OracleDriver");//드라이버 로딩(드라이버 클래스를 로딩)//드라이버매니저 풀링땐 필요x
			ResourceBundle bundle = ResourceBundle .getBundle("kr.or.ddit.db.dbInfo");//res는 다른이름으로 classpath
			driverClassName = bundle.getString("driverClassName");
			url = bundle.getString("url");//하드코딩="jdbc:oracle:thin:@localhost:1521:xe";//어느위치어떤프로그램..등 
			user = bundle.getString("user");
			password = bundle.getString("password");

			/*
				DriverManager(Simple JDBC)와 DataSource(Pooling)의 차이
				Simple JDBC 방식 : Connection이 필요할 때 그 즉시 생성.
				Pooling 방식 : 미리 일정 개수의 Connection을 생성하고 , 
							Pool을 통해 관리하다, 필요할 때 배분해서 사용.
			*/
			
//			OracleDataSource oracleDS = new OracleDataSource();	//pooling방식 - 일정 갯수의 connection을 미리 만들고 아래에서 배분해서 사용
//			oracleDS.setURL(url);
//			oracleDS.setUser(user);
//			oracleDS.setPassword(password);
//			dataSource = oracleDS;
			
			
			// DBMS에 종속되지 않는 플링 시스템을 사용
			BasicDataSource basicDS = new BasicDataSource();
			basicDS.setDriverClassName(driverClassName);
			basicDS.setUrl(url);
			basicDS.setUsername(user);
			basicDS.setPassword(password);
			//하드코딩 x
			int initialSize =Integer.parseInt(bundle.getString("initialSize"));
			int maxActive = Integer.parseInt(bundle.getString("maxActive"));
			long maxWait = Integer.parseInt(bundle.getString("maxWait"));
			basicDS.setInitialSize(initialSize);//첨엔 나중엔 5개를 만들어서 운영하겠지만
			basicDS.setMaxActive(maxActive);//나중에 더 필요하면 10개까지..
			basicDS.setMaxWait(maxWait);//요청이 들어오면 일단 3초 wait 
									//반납이안되면 새로 만든다 위의 10개가 될때까지..
			dataSource = basicDS;
			
		} catch (Exception e) {//정상적으로 드라이버 로딩 x db사용불가
			throw new RuntimeException(e); //예외 새로 만듬->하지만 원본예외를 전달해야한다! 
		} 
	}
	public static Connection getConnection() throws SQLException{
//		Connection conn = DriverManager.getConnection(url, user, password);//simple jdbc방법
		Connection conn = dataSource.getConnection(); //미리 커넥션이 만들어져있다면 그걸 가져가겠다.
		return conn;
	}
}
