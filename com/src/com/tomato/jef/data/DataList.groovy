package com.tomato.jef.data;

import java.sql.SQLException;
import java.util.Map;

import java.sql.ResultSetMetaData;
import java.sql.ResultSet;

import com.tomato.jef.db.util.ResultSetUtil;
import com.tomato.jef.jndi.JNDIHelper;
import com.tomato.jef.log.Log;

/**
 * ArrayList기반의 Database Table의 Multi Row에 해당하는 Data를 Capsule화 하여
 * 각 Tier간 Data List를 전달하는 Value List Pattern의 구현체.
 * CH00 : common.properties에 max_row_size의 값이 선언되어 있을 경우 최대 조회 가능 건수를 정의된 건수만큼 제한한다.
 *        선언되어 있지 않거나 -1로 선언되어 있을 경우 전체를 조회한다.
 * @author TomatoSystem
 * @since TmtFrameWork Version 2.0 (jsp/xbuilder)
 */
class DataList extends ArrayList {
	static final long serialVersionUID = 1516741870737232863L;
	
	private static int MAX_ROW_SIZE
	
	static {
		try {
			String maxRowSize = JNDIHelper.getString('max_row_size')
			if(maxRowSize == null || maxRowSize == '') maxRowSize = '-1'
			MAX_ROW_SIZE = Integer.parseInt(maxRowSize);
		} catch(Throwable t) {
			Log.error(t.getMessage());
			MAX_ROW_SIZE = -1;
		}
	}
	
	//디폴트 생성자
	DataList(){
		super()
	}

	/**
	 * 생성자
	 * @param initialCapacity ArrayList의 초기 Capacity
	 */
	DataList(int initialCapacity){
		super(initialCapacity)
	}

	/**
	 * 생성자
	 * @param c java.util.Collection 초기 데이터
	 * 컬렉션으로 넘어온 데이터로 ArrayList를 초기화시킨다.
	 */
	DataList(Collection c){
		super(c)
	}

	/**
	 * DataList에서 인덱스에 해당하는 번째의 DataSet 객체를 반환한다.
	 * @param index 얻고자하는 인덱스 번호
	 * @return DataSet com.tomato.jef.data.DataSet
	 */
	DataSet getData(int index){
		def data = get(index)
		
		if(data == null) return null
		
		if(data instanceof DataSet){
			return (DataSet)data
		}else{
			throw new ClassCastException("Index $i Object is Instance Of ${obj.class.name}")
		}
	}
	
	/**
	 * DataList에서 인덱스에 해당하는 번째의 DataList 객체를 반환한다.
	 * @param index 얻고자하는 인덱스 번호
	 * @return DataList com.tomato.jef.data.DataList
	 */
	DataSet getDataList(int index){
		def data = get(index)
		
		if(data == null) return null
		
		if(data instanceof DataList){
			return (DataList)data
		}else{
			throw new ClassCastException("Index $i Object is Instance Of ${obj.class.name}")
		}
	}
	
	/**
	 * DataSet에 들어있는 값 객체들을 DataSet 배열로 반환한다.
	 * @return DataSet[] com.tomato.jef.data.DataSet
	 */
	DataSet[] toDataSetArray(){
		return (DataSet[])super.toArray(new DataSet[0])
	}
	
	/**
	 * DataList의 각 Row에 들어가 있는 DataSet에 넘겨 받은 Argument를 추가한다.
	 * Argument중 boolean값으로 받은 force값에 따라 기존재 하는 Column이 있을때 처리를 판단한다.<br>
	 * force true -> 기존재 하는 Column을 삭제하고 넘겨 받은 entryValue를 입력한다.<br>
	 * force false - > 기존재 하는 Column이 있을경우 entryValue를 덮어쓰지 않는다.<br>
	 * call by reference 로 구동
	 * @param entryKey 추가할 Column의 Key값
	 * @param entryValue 추가할 Column의 Value 값
	 * @param force 추가하려고 하는 Column이 존재할때 덮어쓸지 여부
	 */
	void addEntry(String entryKey, String entryValue, boolean force) {
		DataList.addEntry(this, entryKey, entryValue, force)
	}
	
	/**
	 * 설정된 Key값을 제거한다.
	 * call by reference 로 구동
	 * @param entryKey 제거할 Key값
	 */
	void removeEntry(String entryKey) {
		DataList.removeEntry(this, entryKey)
	}
	
	/**
	 * ResultSet을 DataList에 담는다.
	 * @param rs javax.sql.ResultSet 데이터베이스로부터 질의한 결과를 담고 있는 ResultSet 객체
	 * @throws SQLException
	 */
	void store(ResultSet rs) throws SQLException {
		store(rs, MAX_ROW_SIZE)
	}
	
	/**
	 * DataList의 각 Row에 들어가 있는 DataSet에 넘겨 받은 Argument를 추가한다.
	 * Argument중 boolean값으로 받은 force값에 따라 기존재 하는 Column이 있을때 처리를 판단한다.<br>
	 * force true -> 기존재 하는 Column을 삭제하고 넘겨 받은 entryValue를 입력한다.<br>
	 * force false - > 기존재 하는 Column이 있을경우 entryValue를 덮어쓰지 않는다.
	 * @param list 추가할 대상 DataList
	 * @param entryKey 추가할 Column의 Key값
	 * @param entryValue 추가할 Column의 Value 값
	 * @param force 추가하려고 하는 Column이 존재할때 덮어쓸지 여부
	 */
	static void addEntry(DataList list, String entryKey, Object entryValue, boolean force) {
		if(list.size() < 1) return
		
		list.each { item ->
			if(item instanceof Map){
				def mapObj = (Map) item
				if(force){
					mapObj.remove(entryKey)
					mapObj.put(entryKey, entryValue)
				}else{
					if(mapObj.containsKey(entryKey) == false) mapObj.put(entryKey, entryValue)
				}
			}else if(item instanceof DataList){
				addEntry((DataList) item, entryKey, entryValue, force)
			}
		}
	}
	
	/**
	 * 설정된 Key값을 제거한다.
	 * @param list 제거할 리스트
	 * @param entryKey 제거할 Key값
	 */
	static void removeEntry(DataList list, String entryKey) {
		if(list.size() < 1) return
		
		list.each { item ->
			if(item instanceof Map){
				def mapObj = (Map) item
				if(mapObj.containsKey(entryKey)) mapObj.remove(entryKey)
			}else if(item instanceof DataList){
				removeEntry((DataList) item, entryKey)
			}
		}
	}
	
	/**
	 * ResultSet을 DataList에 정해진 row수 만큼 담는다.
	 * @param rs ResultSet
	 * @param rowSize int Row Count
	 * @throws SQLException
	 */
	void store(ResultSet rs, int rowSize) throws SQLException {
		if(rowSize < 1) return

		ResultSetMetaData rsMeta = rs.metaData
		
		//20070201 일부 JDBC 드라이버에서 지원안함
		ResultSetUtil resultSetUtil = ResultSetUtil.getInstance();
		
		int loop = 0
		def columnName = null
		for(DataSet row; rs.next() && (loop < rowSize); ) {
			row = new DataSet()
			for(i in 1..rsMeta.columnCount){
				columnName = rsMeta.getColumnName(i)
				row.put(columnName, resultSetUtil.getColumnData(rs, columnName, rsMeta.getColumnTypeName(i)))
			}
		    super.add(row)
		    loop++
		}
	}

	/*public static void main(String[] args) {
		def datals = new DataList()
		
		def jdbcDriver 	= GenBundle.getJdbcDriver()
		def jdbcUrl 	= GenBundle.getJdbcUrl()
		def jdbsUser 	= GenBundle.getDbUser()
		def jdbcPassWd 	= GenBundle.getDbPassword()
		def db = Sql.newInstance(jdbcUrl, jdbsUser, jdbcPassWd, jdbcDriver)
		
		db.query( "SELECT * FROM CMN_USE_USER WHERE USER_DIV_CD = '05'", { resultSet -> datals.store(resultSet, 10)})

		datals.removeEntry('efft_st_dt')
		datals.each {
			item ->
			println item
		}
	}*/
}
