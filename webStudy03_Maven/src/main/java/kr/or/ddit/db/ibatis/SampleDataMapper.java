package kr.or.ddit.db.ibatis;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import kr.or.ddit.vo.MemberVO;

public class SampleDataMapper {
	public static Object resultSetToJavaObject(ResultSet rs, Class resultClass) throws Exception{
		Object resultObject = resultClass.newInstance();
		ResultSetMetaData rsmd = rs.getMetaData();
		for(int cnt = 1; cnt<=rsmd.getColumnCount(); cnt ++) {
			String colName = rsmd.getColumnName(cnt);
			
			try {
				PropertyDescriptor pd = 
						new PropertyDescriptor(colName.toLowerCase(), resultClass);
				Method setter = pd.getWriteMethod();
				Object setterValue = rs.getObject(colName);
				if(setterValue instanceof CharSequence) {//이건 문자열이라는 이야기
					
				}else if(setterValue instanceof BigDecimal) {
					setterValue = ((BigDecimal)setterValue).intValue();
				}else {//나도잘모르겠다는 
					
				}
				 setter.invoke(resultObject, setterValue);
			} catch (IntrospectionException | IllegalArgumentException | InvocationTargetException e) {
				continue;
			}
		}
		return resultObject;
	}
}
