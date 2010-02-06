package com.tomato.jef.dao.dialect;

import com.tomato.jef.gen.GenBundle;

class MSSQLDialect implements Dialect{
	String getTableSchemaQuery(List tableList){
		def query = new StringBuffer()
		query << """ SELECT T.NAME   AS TABLE_NAME
						  , C.NAME   AS COLUMN_NAME
						  , P.NAME   AS COLUMN_TYPE
						  , (CASE WHEN C.ISNULLABLE=1 THEN 'N' ELSE 'Y' END) AS IS_NULL
						FROM SYSOBJECTS T
							 JOIN SYSCOLUMNS C ON C.ID = T.ID
							 LEFT JOIN SYSTYPES P ON C.XUSERTYPE = P.XUSERTYPE
					 WHERE OBJECTPROPERTY(T.ID, 'IsUserTable') = 1 
					   AND T.TYPE = 'U'
				 """;
		
		if(tableList && tableList.size() > 0){
			query << "  AND TNAME IN( '${tableList[0]}' "
			for(idx in 1..<tableList.size()){
				query << ", '${tableList[idx]}' "
			}
			query << ' )\n'
		}
		
		query << ' ORDER BY T.NAME ASC'
		
		return query
	}
	
	String getDbms(){
		return 'MSSQL'
	}
	
	
	public static void main(String[] args) {
		def list = ['CMN_ACAD_CFG', 'PPS_STAFF_BASE_INFO']
		def dialet = new MSSQLDialect();
		dialet.getTableSchemaQuery(list);
	}
}
