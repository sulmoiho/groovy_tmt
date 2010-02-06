package com.tomato.jef.dao.dialect;


interface Dialect {
	/**
	 * DBMS별로 테이블 및 컬럼을 Dictionary 테이블을 이용하여 조회하는 쿼리를 생성한다.
	 * 몇몇 테이블만 조회할 경우 파라미터로 테이블의 갯수를 전달한다.
	 * 테이블의 갯수가 0이면 전체 테이블의 컬럼을 조회한다.
	 * <br/>리턴 컬럼
	 * <li>TABLE_NAME : Table 명</li>
	 * <li>COLUMN_NAME : Column 명</li>
	 * <li>COLUMN_TYPE : Column Type</li>
	 * <li>IS_NULL : Null 허용 여부(Yes/No)</li>
	 * @param inTCnt 특정 테이블만 조회할 경우 조건이 될 테이블의 갯수를 전달한다. 0 일경우 전체 테이블을 조회 한다.
	 * @return DBMS별 Dictionary 테이블을 조회하는 쿼리.
	 */
	public String getTableSchemaQuery(List inTables);
	
	public String getDbms();
}
