package com.tomato.jef.db.util;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 데이터베이스에 대한 질의결과인 ResultSet에 대한 유틸 클래스이다.
 */
final class ResultSetUtil {
	private static ResultSetUtil resultSetUtil = new ResultSetUtil();
	
	/**
	 * 생성자
	 * 인스턴스 생성을 못하도록 막음
	 */
	private ResultSetUtil(){
		super()
	}

	/**
	 * 싱글톤 객체 반환
	 */
	static ResultSetUtil getInstance() {
		return resultSetUtil
	}
	
	/**
	 * ResultSet의 해당 컬럼값을 반환한다.
	 * 
	 * @param rs javax.sql.ResultSet 해당 쿼리에 대한  결과값을 갖고 있는 ResultSet 객체
	 * @param columnName 해당 ResultSet에서 얻고자 하는 컬럼명
	 * @param columnType java.lang.String 해당 컬럼의 데이터 타입
	 */
	Object getColumnData(ResultSet rs, String columnName, String columnType) throws SQLException {
		def result = null
		if(columnType.equalsIgnoreCase('VARCHAR2')
			|| columnType.equalsIgnoreCase('VARCHAR')
			|| columnType.equalsIgnoreCase('CHAR')) result = rs.getString(columnName)
		else if(columnType.equalsIgnoreCase('LONG')) result = LobUtil.convertLongToString(rs, columnName)
		else if(columnType.equalsIgnoreCase('DATE')) result = rs.getTimestamp(columnName)
		else if(columnType.equalsIgnoreCase('NUMBER')) result = rs.getBigDecimal(columnName)
		else if(columnType.equalsIgnoreCase('CLOB')) result = LobUtil.convertClobToString(rs.getClob(columnName))
		else if(columnType.equalsIgnoreCase('BLOB')) result = LobUtil.convertBlobToString(rs.getBlob(columnName))
		
		return result
	}
	
	/**
	 * ResultSet의 해당 컬럼값을 반환한다.
	 * 
	 * @param rs javax.sql.ResultSet 해당 쿼리에 대한  결과값을 갖고 있는 ResultSet 객체
	 * @param columnIndex 해당 ResultSet에서 얻고자 하는 컬럼의 인덱스
	 * @param columnType java.lang.String 해당 컬럼의 데이터 타입
	 */
	Object getColumnData(ResultSet rs, int columnIndex, String columnType) throws SQLException {
		def result = null
		if(columnType.equalsIgnoreCase('VARCHAR2')
		|| columnType.equalsIgnoreCase('VARCHAR')
		|| columnType.equalsIgnoreCase('CHAR')) result = rs.getString(columnIndex)
		else if(columnType.equalsIgnoreCase('LONG')) result = LobUtil.convertLongToString(rs, columnIndex)
		else if(columnType.equalsIgnoreCase('DATE')) result = rs.getTimestamp(columnIndex)
		else if(columnType.equalsIgnoreCase('NUMBER')) result = rs.getBigDecimal(columnIndex)
		else if(columnType.equalsIgnoreCase('CLOB')) result = LobUtil.convertClobToString(rs.getClob(columnIndex))
		else if(columnType.equalsIgnoreCase('BLOB')) result = LobUtil.convertBlobToString(rs.getBlob(columnIndex))
		
		return result
	}
}
