package com.tomato.jef.db.util;

import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Blob;
import java.sql.ResultSet;

import com.tomato.jef.resource.Collector;

final class LobUtil {
	private static final int BUFFER_SIZE = 512;
	
	/**
	 * 생성자
	 * 인스턴스 생성을 못하도록 막음
	 */
	private LobUtil(){
		super()
	}

	/**
	 * Clob의 정보를 String으로 받아내서 리턴한다.
	 * @param clob javax.sql.Clob
	 * @return java.lang.String Clob의 데이타
	 * @throws Exception
	 */
	static String convertClobToString(Clob clob) throws Exception {
		if(clob == null) return ''
		
		def result = null
		Reader reader = null
		try {
			reader = clob.getCharacterStream()
			if(reader == null) return ''
			
			result = new StringBuffer()

			char[] buffer = new char[BUFFER_SIZE]
			for(int readSize = reader.read(buffer); readSize != -1; readSize = reader.read(buffer)) {
				for(i in 0..<readSize) result << buffer[i]
			}
		} finally {
			Collector.release(reader)
		}
		
		return result.toString()
	}
	
	/**
	 * LONG형의 정보를 String으로 받아내서 리턴한다.
	 * @param rs javax.sql.ResultSet
	 * @param columnName 해당하는 컬럼명
	 * @return java.lang.String Long의 데이타
	 * @throws javax.sql.SQLException
	 */
	static String convertLongToString(ResultSet rs, String columnName) throws SQLException {
		if(columnName == null) return ''
		
		def result = null
		Reader reader = null
		try {
			reader = rs.getCharacterStream(columnName)
			if(reader == null) return ''
			
			result = new StringBuffer()

			char[] buffer = new char[BUFFER_SIZE]
			for(int readSize = reader.read(buffer); readSize != -1; readSize = reader.read(buffer)) {
				for(i in 0..<readSize) result << buffer[i]
			}
		} catch(IOException ioe) {
			throw new SQLException(ioe.getMessage())
		} finally {
			Collector.release(reader)
		}
		
		return result.toString()
	}
	
	/**
	 * LONG형의 정보를 String으로 받아내서 리턴한다.
	 * @param rs javax.sql.ResultSet
	 * @param columnIndex 해당하는 컬럼인덱스
	 * @return java.lang.String Long의 데이타
	 * @throws javax.sql.SQLException
	 */
	static String convertLongToString(ResultSet rs, int columnIndex) throws SQLException {
		if(columnIndex == 0) return ''
		
		def result = null
		Reader reader = null
		try {
			reader = rs.getCharacterStream(columnIndex)
			if(reader == null) return ''
			
			result = new StringBuffer()
			
			char[] buffer = new char[BUFFER_SIZE]
			for(int readSize = reader.read(buffer); readSize != -1; readSize = reader.read(buffer)) {
				for(i in 0..<readSize) result << buffer[i]
			}
		} catch(IOException ioe) {
			throw new SQLException(ioe.getMessage())
		} finally {
			Collector.release(reader)
		}
		
		return result.toString()
	}
	
	/**
	 * BLOB형 데이타를 BASE64로 인코딩하여 리턴한다.
	 * @param blob java.sql.Blob
	 * @return BASE64로 인코딩된 Blob Byte Data
	 * @throws Exception
	 */
	static String convertBlobToString(Blob blob) throws Exception {
		if(blob == null) return ''
		
		ByteArrayOutputStream bout = null
		InputStream bin = null
		String result = null
		
		try {
			bin = blob.getBinaryStream()
			bout = new ByteArrayOutputStream()
			
			byte[] buffer = new byte[BUFFER_SIZE]
			for(int readSize = bin.read(buffer); readSize != -1; readSize = bin.read(buffer)){
				bout.write(buffer, 0, readSize)
			}
			
			byte[] blobData = bout.toByteArray()
			if(blobData && blobData.size() > 0){
				result = blobData.encodeBase64().toString()
			}
		} finally {
			Collector.release(bout)
			Collector.release(bin)
		}
		
		return result
	}
	
	
	public static void main(String[] args) {
		String str = 'ttttt'
		
		println str.getBytes().encodeBase64().toString()
	}
}
