package com.tomato.jef.dao.dialect;

import com.tomato.jef.gen.GenBundle;

class OracleDialect implements Dialect{
	/**
	 * 각 DB 고유의 DB내의 테이블들에 대한 메타정보를 조회하기 위한 질의문을 반환한다.
	 * 
	 * @param tableList 특정 테이블에 대해서만 메타정보를 얻고자하는 경우, 테이블명이 인자로 주어진다.
	 */
	String getTableSchemaQuery(List tableList){
		def dbOwner = GenBundle.getTableOwner()
		
		def query = new StringBuffer();
		query << """ SELECT TABLE_NAME 	AS TABLE_NAME
						  , COLUMN_NAME AS COLUMN_NAME
						  , DATA_TYPE 	AS COLUMN_TYPE
						  , NULLABLE 	AS IS_NULL
						  , DATA_DEFAULT AS DEFAULT_VALUE
						FROM ALL_TAB_COLUMNS 
					 WHERE TABLE_NAME NOT LIKE '%=%' 
					   AND OWNER = '$dbOwner'
				 """;
		
		if(tableList && tableList.size() > 0){
			query << "  AND TABLE_NAME IN( '${tableList[0]}' "
			for(idx in 1..<tableList.size()){
				query << ", '${tableList[idx]}' "
			}
			query << ' )\n'
		}
		
		query << ' ORDER BY TABLE_NAME ASC'
		
		return query;
	}
	
	String getDbms(){
		return 'ORACLE'
	}
	
	
	public static void main(String[] args) {
		def list = ['CMN_ACAD_CFG', 'PPS_STAFF_BASE_INFO']
		def dialet = new OracleDialect();
		dialet.getTableSchemaQuery(list);
	}
}
