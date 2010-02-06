package com.tomato.jef.dao.dialect;

class SybaseDialect implements Dialect{
	String getTableSchemaQuery(List tableList){
		def query = new StringBuffer();
		query << """ SELECT TABLE_NAME  AS TABLE_NAME
						  , COLUMN_NAME AS COLUMN_NAME
						  , DOMAIN_NAME AS COLUMN_TYPE
						  , (IF NULLS = 'Y' THEN 'Yes' ELSE 'No' ENDIF) AS IS_NULL
						FROM SYSCOLUMN, SYSDOMAIN, SYSTABLE 
					 WHERE SYSDOMAIN.DOMAIN_ID = SYSCOLUMN.DOMAIN_ID 
					   AND SYSTABLE.TABLE_ID   = SYSCOLUMN.TABLE_ID
				 """;
		
		if(tableList && tableList.size() > 0){
			query << "  AND SYSTABLE.TABLE_ID IN( '${tableList[0]}' "
			for(idx in 1..<tableList.size()){
				query << ", '${tableList[idx]}' "
			}
			query << ' )\n'
		}
		
		query << ' ORDER BY TABLE_NAME ASC'
		
		return query;
	}
	
	String getDbms(){
		return 'SYBASE'
	}
	
	
	public static void main(String[] args) {
		def list = ['CMN_ACAD_CFG', 'PPS_STAFF_BASE_INFO']
		def dialet = new SybaseDialect();
		dialet.getTableSchemaQuery(list);
	}
}
