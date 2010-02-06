package com.tomato.jef.gen;

import groovy.text.GStringTemplateEngine;

class DAOBuilder {
	private def template
	
	//생성자
	//DAO 파일에 대한 템플릿을 읽어서 Template 객체를 생성한다.
	DAOBuilder(){
		def daoGenPath	= GenBundle.getTemplatePath()
		def daoTempNm	= GenBundle.getDAOTemplate()
		
		ClassLoader loader = this.getClass().getClassLoader()
		template = new GStringTemplateEngine().createTemplate(new InputStreamReader(loader.getResourceAsStream("$daoGenPath${File.separator}$daoTempNm")))
	}

	/**
	 * 템플릿으로부터 각 테이블에 대한 DAO 클래스 문자열 생성
	 * 
	 * @param tableInfo 해당 테이블에 대한 메타정보를 담고 있는 객체
	 * @return 
	 */
	String build(TableMetadata tableInfo){
		//테이블 컬럼 정보
		def columns = []
		
		def columnInfo
		for(idx in 0..<tableInfo.getColumnCount()){
			columnInfo = tableInfo.getColumn(idx)

			columns << "{'${columnInfo.columnName}', '${columnInfo.columnType}', '${columnInfo.defaultValue}'}"
		}
		
		//템플릿에 대한 바인딩 값
		def binding = ['dao_package'	: GenBundle.getDAOPackage(),
		               'dao_dbms'		: GenBundle.getDBTypeName(),
		               'dao_class'		: tableInfo.getCamelNotationTableName(),
		               'dao_table'		: tableInfo.getTableName(),
		               'dao_column_cnt'	: tableInfo.getColumnCount(),
		               'columns'		: columns
		              ]
		
		return template.make(binding)
	}
}
