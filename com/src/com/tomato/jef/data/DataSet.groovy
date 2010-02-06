package com.tomato.jef.data;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tomato.jef.db.util.ResultSetUtil;
import com.tomato.jef.exception.DataTypeException;

/**
 * DATA를 주고 받기 위한 클래스.
 * Map 기반의 Database Table의 한 Row에 해당하는 Data를 Capsule화 하여 각 Tier간 Data를
 * 전달하는 Value Object Pattern의 구현체.
 * <P>
 * DATA를 주고 받기 위해 DataSet 객체에 값을 저장할때는 <CODE>put()</CODE> 메소드를 이용하고,<br>
 * 저장된 DATA를 빼내기 위해서는 <CODE>getString()</CODE>, <CODE>getInt()</CODE>, ... 메소들처럼<br>
 * Type에 맞는 geter 메소드를 사용한다.<br>
 * geter 메소드를 지원하지 않은 객체 타입의 값은 <CODE>getObject()</CODE>로 받아와 형변환한다.
 * </P>
 *
 * <P><BLOCKQUOTE><PRE>
 *     // DATA 저장<br>
 *     DataSet dataSet = new DataSet();<br>
 *     dataSet.put("STRING_VALUE", "String 타입의 값을 저장");<br>
 *     dataSet.put("DATE_VALUE", new Date());<br>
 *     dataSet.put("INT_VALUE", 0);<br>
 *     ...
 *
 *     // DATA 빼내기<br>
 *     String strData1 = dataSet.getString("STRING_VALUE");<br>
 *     Date dtData2 = (Date)dataSet.getObject("DATE_VALUE");<br>
 *     int intData3 = dataSet.getInt("INT_VALUE");<br>
 *     ...
 * </PRE></BLOCKQUOTE></P>
 *
 * <P>
 * DataSet에 저장된 값이 없을 경우 geter로 값을 빼내면, 각각 초기값으로 return한다.<br>
 * <CODE>String은 빈문자(<CODE>""</CODE>)로, <CODE>boolean</CODE>은 <CODE>false</CODE>로,<br>
 * <CODE>byte[]</CODE>는 <CODE>new byte[0]</CODE>로 return하며,<br>
 * <CODE>byte</CODE>, <CODE>char</CODE>, <CODE>double</CODE>, <CODE>float</CODE>,<br>
 * <CODE>int</CODE>, <CODE>long</CODE>, <CODE>short</CODE>은 0으로 return한다.<br>
 * </P>
 *
 * <P>
 * Type별로 geter해올때 초기값으로 return되어, 실제 값이 없는것인지 초기값과 같은 값인지<br>
 * 알 수 없으므로, 이를 확인하고자 한다면, <CODE>getObject()</CODE> 메소드를 통해<br>
 * <CODE>Object</CODE> 값을 가져온 후 해당 객체타입으로 형변환해서 확인해야 한다.
 * </P>
 *
 * <P><BLOCKQUOTE><PRE>
 *     String strData1 = (String)dataSet.getObject("STRING_VALUE");<br>
 *     Date dtData2 = (String)dataSet.getObject("DATE_VALUE");<br>
 *     Integer integerData3 = (Integer)dataSet.getObject("INT_VALUE");<br>
 *     ...
 *
 *     if (strData1 == null)  // String 객체에 대한 null 체크<br>
 *     if (dtData2 == null)  // Date 객체에 대한 null 체크<br>
 *     if (integerData3 == null)  // int, long 처럼 primitive type의 경우에는 해당 객체 타입으로 확인해야 함.
 *
 * </PRE></BLOCKQUOTE></P>
 *
 * <P>
 * @version 1.0, 2003-12-06
 * @author  yb2u
 * @re-version 2009.12.20 sulmoiho
 */
class DataSet extends HashMap {
	static final long serialVersionUID = -8279819469534483368L;

	/**
     * <CODE>Object</CODE>를 저장하는 메소드
     *
     * <P>
     * @param   key    저장할 key값
     * @param   value  저장할 <CODE>Object</CODE> value값
     */
	void put(String key, Object value) {
		super.put(key.toLowerCase(), value)
	}
	
	/**
	 * <CODE>boolean</CODE> primitive type을 저장하는 메소드.
	 *
	 * <P>
	 * @param   key    저장할 key값
	 * @param   value  저장할 <CODE>boolean</CODE> value값
	 */
	void put(String key, boolean value) {
		super.put(key.toLowerCase(), Boolean.valueOf(value))
	}
	
	/**
	 * <CODE>byte</CODE> primitive type을 저장하는 메소드.
	 *
	 * <P>
	 * @param   key    저장할 key값
	 * @param   value  저장할 <CODE>byte</CODE> value값
	 */
	void put(String key, byte value) {
		super.put(key.toLowerCase(), Byte.valueOf(value))
	}
	
	/**
	 * <CODE>byte[]</CODE> type을 저장하는 메소드
	 *
	 * <P>
	 * @param   key    저장할 key값
	 * @param   value  저장할 <CODE>byte[]</CODE> value값
	 */
	void put(String key, byte[] value) {
		super.put(key.toLowerCase(), value)
	}
	
	/**
	 * <CODE>char</CODE> primitive type을 저장하는 메소드.
	 *
	 * <P>
	 * @param   key    저장할 key값
	 * @param   value  저장할 <CODE>char</CODE> value값
	 */
	void put(String key, char value) {
		super.put(key.toLowerCase(), Character.valueOf(value))
	}
	
	/**
	 * <CODE>double</CODE> primitive type을 저장하는 메소드.
	 *
	 * <P>
	 * @param   key    저장할 key값
	 * @param   value  저장할 <CODE>double</CODE> value값
	 */
	void put(String key, double value) {
		super.put(key.toLowerCase(), Double.valueOf(value))
	}
	
	/**
	 * <CODE>float</CODE> primitive type을 저장하는 메소드.
	 *
	 * <P>
	 * @param   key    저장할 key값
	 * @param   value  저장할 <CODE>float</CODE> value값
	 */
	void put(String key, float value) {
		super.put(key.toLowerCase(), Float.valueOf(value))
	}
	
	/**
	 * <CODE>int</CODE> primitive type을 저장하는 메소드.
	 *
	 * <P>
	 * @param   key    저장할 key값
	 * @param   value  저장할 <CODE>int</CODE> value값
	 */
	void put(String key, int value) {
		super.put(key.toLowerCase(), Integer.valueOf(value))
	}
	
	/**
	 * <CODE>long</CODE> primitive type을 저장하는 메소드.
	 *
	 * <P>
	 * @param   key    저장할 key값
	 * @param   value  저장할 <CODE>long</CODE> value값
	 */
	void put(String key, long value) {
		super.put(key.toLowerCase(), Long.valueOf(value))
	}
	
	/**
	 * <CODE>short</CODE> primitive type을 저장하는 메소드.
	 *
	 * <P>
	 * @param   key    저장할 key값
	 * @param   value  저장할 <CODE>short</CODE> value값
	 */
	void put(String key, short value) {
		super.put(key.toLowerCase(), Short.valueOf(value))
	}
	
	/**
	 * <CODE>DataSet</CODE>에 저장된 값을 <CODE>Object</CODE> type으로 빼내는 메소드.
	 *
	 * <P>
	 * @param   key    저장된 key값
	 * @return  저장된 <CODE>Object</CODE> value값
	 */
	Object getObject(String key) {
		if(key == null) return null
		
		return super.get(key.toLowerCase())
	}
	
	/**
	 * <CODE>DataSet</CODE>에 저장된 값을 <CODE>String</CODE> type으로 빼내는 메소드.
	 *
	 * <P>
	 * @param   key    저장된 key값
	 * @return  저장된 <CODE>String</CODE> value값
	 * @see     com.tomato.jef.exception.DataTypeException
	 */
	String getString(String key) throws DataTypeException {
		def objValue = getObject(key)
		
		if (objValue == null) {
			return ''
		} else {
			return objValue.toString()
		}
	}
	
	/**
	 * <CODE>DataSet</CODE>에 저장된 값을 <CODE>boolean</CODE> primitive type으로 빼내는 메소드.
	 *
	 * <P>
	 * @param   key    저장된 key값
	 * @return  저장된 <CODE>boolean</CODE> value값
	 * @see     com.tomato.jef.exception.DataTypeException
	 */
	boolean getBoolean(String key) throws DataTypeException {
		def objValue = getObject(key)
		
		if(objValue == null) return false
		
		if(objValue instanceof Boolean) return ((Boolean)objValue).booleanValue()
		else if(objValue instanceof String) return (Boolean.valueOf((String)objValue)).booleanValue()
		else throw new DataTypeException()
	}
	
	/**
	 * <CODE>DataSet</CODE>에 저장된 값을 <CODE>byte</CODE> primitive type으로 빼내는 메소드.
	 *
	 * <P>
	 * @param   key    저장된 key값
	 * @return  저장된 <CODE>byte</CODE> value값
	 * @see     com.tomato.jef.exception.DataTypeException
	 */
	byte getByte(String key) throws DataTypeException {
		def objValue = getObject(key)
		
		if(objValue == null) return 0
		
		if(objValue instanceof Number) return ((Number)objValue).byteValue()
		else if(objValue instanceof String) return (Byte.valueOf((String)objValue)).byteValue()
		else throw new DataTypeException()
	}
	
	/**
	 * <CODE>DataSet</CODE>에 저장된 값을 <CODE>byte[]</CODE> type으로 빼내는 메소드.
	 *
	 * <P>
	 * @param   key    저장된 key값
	 * @return  저장된 <CODE>byte[]</CODE> value값
	 * @see     com.tomato.jef.exception.DataTypeException
	 */
	byte[] getBytes(String key) throws DataTypeException {
		def objValue = getObject(key)
		
		if(objValue == null) return byte[0]
		
		if(objValue instanceof byte[]) return (byte[])objValue
		else if(objValue instanceof String) return ((String)objValue).getBytes()
		else if(objValue instanceof Number) return objValue.toString().getBytes()
		else throw new DataTypeException()
	}
	
	/**
	 * <CODE>DataSet</CODE>에 저장된 값을 <CODE>char</CODE> primitive type으로 빼내는 메소드.
	 *
	 * <P>
	 * @param   key    저장된 key값
	 * @return  저장된 <CODE>char</CODE> value값
	 * @see     com.tomato.jef.exception.DataTypeException
	 */
	char getChar(String key) throws DataTypeException {
		def objValue = getObject(key)
		
		if(objValue == null) return 0
		
		if(objValue instanceof Character) return ((Character)objValue).charValue()
		else if(objValue instanceof String) return ((String)objValue).charAt(0)
		else throw new DataTypeException()
	}
	
	/**
	 * <CODE>DataSet</CODE>에 저장된 값을 <CODE>double</CODE> primitive type으로 빼내는 메소드.
	 *
	 * <P>
	 * @param   key    저장된 key값
	 * @return  저장된 <CODE>double</CODE> value값
	 * @see     com.tomato.jef.exception.DataTypeException
	 */
	double getDouble(String key) throws DataTypeException {
		def objValue = getObject(key)
		
		if(objValue == null) return 0
		
		if(objValue instanceof Number) return ((Number)objValue).doubleValue()
		else if(objValue instanceof String) return Double.parseDouble((String)objValue)
		else throw new DataTypeException()
	}
	
	/**
	 * <CODE>DataSet</CODE>에 저장된 값을 <CODE>float</CODE> primitive type으로 빼내는 메소드.
	 *
	 * <P>
	 * @param   key    저장된 key값
	 * @return  저장된 <CODE>float</CODE> value값
	 * @see     com.tomato.jef.exception.DataTypeException
	 */
	float getFloat(String key) throws DataTypeException {
		def objValue = getObject(key)
		
		if(objValue == null) return 0
		
		if(objValue instanceof Number) return ((Number)objValue).floatValue()
		else if(objValue instanceof String) return Float.parseFloat((String)objValue)
		else throw new DataTypeException()
	}
	
	/**
	 * <CODE>DataSet</CODE>에 저장된 값을 <CODE>int</CODE> primitive type으로 빼내는 메소드.
	 *
	 * <P>
	 * @param   key    저장된 key값
	 * @return  저장된 <CODE>int</CODE> value값
	 * @see     com.tomato.jef.exception.DataTypeException
	 */
	int getInt(String key) throws DataTypeException {
		def objValue = getObject(key)
		
		if(objValue == null) return 0
		
		if(objValue instanceof Number) return ((Number)objValue).intValue()
		else if(objValue instanceof String) return Integer.parseInt((String)objValue)
		else throw new DataTypeException()
	}
	
	/**
	 * <CODE>DataSet</CODE>에 저장된 값을 <CODE>long</CODE> primitive type으로 빼내는 메소드.
	 *
	 * <P>
	 * @param   key    저장된 key값
	 * @return  저장된 <CODE>long</CODE> value값
	 * @see     com.tomato.jef.exception.DataTypeException
	 */
	long getLong(String key) throws DataTypeException {
		def objValue = getObject(key)
		
		if(objValue == null) return 0
		
		if(objValue instanceof Number) return ((Number)objValue).longValue()
		else if(objValue instanceof String) return Long.parseLong((String)objValue)
		else throw new DataTypeException()
	}
	
	/**
	 * <CODE>DataSet</CODE>에 저장된 값을 <CODE>short</CODE> primitive type으로 빼내는 메소드.
	 *
	 * <P>
	 * @param   key    저장된 key값
	 * @return  저장된 <CODE>short</CODE> value값
	 * @see     com.tomato.jef.exception.DataTypeException
	 */
	short getShort(String key) throws DataTypeException {
		def objValue = getObject(key)
		
		if(objValue == null) return 0
		
		if(objValue instanceof Number) return ((Number)objValue).shortValue()
		else if(objValue instanceof String) return Short.parseShort((String)objValue)
		else throw new DataTypeException()
	}
	
	/**
	 * <CODE>DataSet</CODE>에 저장된 값을 <CODE>Date</CODE> type으로 빼내는 메소드
	 * <P>
	 * @param   key    저장된 key값
	 * @return  저장된 <CODE>Date</CODE> value값
	 * @see     com.tomato.jef.exception.DataTypeException
	 */
	Date getDate(String key) throws DataTypeException {
		def objValue = getObject(key)
		
		if(objValue == null) return null
		
		if(objValue instanceof Timestamp) return (Timestamp) objValue
		else if(objValue instanceof Date) return (Date) objValue
		else if(objValue instanceof Long) return new Date(((Long)objValue).longValue())
		else if(objValue instanceof String) {
			String date = (String) objValue
			try {
				return new SimpleDateFormat('yyyy-MM-dd').parse(date)
			} catch(ParseException pe) {
				throw new DataTypeException()
			}
		}else throw new DataTypeException()		
	}
	
	/**
	 * ResultSet을 DataSet에 담는다.
	 * @param rs ResultSet
	 * @throws SQLException
	 */
	void store(ResultSet rs) throws SQLException {
		if(rs.next()){
			ResultSetMetaData rsMeta = rs.metaData
			
			def columnName = null
			ResultSetUtil resultSetUtil = ResultSetUtil.getInstance()

			for(i in 1..rsMeta.columnCount){
				columnName = rsMeta.getColumnName(i)
				super.put(columnName, resultSetUtil.getColumnData(rs, columnName, rsMeta.getColumnTypeName(i)))
			}
		}
	}
	
	String toString() {
		def strbf = new StringBuffer()
		
		try {
			super.each { key, value ->
				strbf << 'Key: ' << key << '\t' << 'value: ' << value.toString() << '\t'
			}
		} catch(Exception e) {
			e.printStackTrace()
		}
		
		return strbf.toString()
	}

}
