package com.tomato.jef.gen;

import java.util.ResourceBundle;

class GenBundle {
	private static final ResourceBundle bundle
	
	static {
		try {
			bundle = ResourceBundle.getBundle("com.tomato.jef.gen.config.genconfig")
		} catch(Exception e) {
			e.printStackTrace()
		}
	}
	
	static String getProperty(def key) {
		return bundle.getString(key)
	}
	
	//DAO 파일 출력 위치
	static String getBasePath() {
		return getProperty('default.to.path')
	}
	
	/*** JDBC 정보 반환 ***/
	//DB 타입
	static String getDBTypeName() {
		return getProperty('db.type.name')
	}
	
	//테이블 OWNER
	static String getTableOwner() {
		return getProperty('db.table.owner')
	}
	
	//JDBC 드라이버
	static String getJdbcDriver() {
		return getProperty('db.jdbcDriver')
	}
	
	//DB URL
	static String getJdbcUrl() {
		return getProperty('db.jdbcUrl')
	}
	
	//DB 유저
	static String getDbUser() {
		return getProperty('db.user')
	}
	
	//DB 패스워드
	static String getDbPassword() {
		return getProperty('db.password')
	}
	
	//특정테이블에 대해서만 DAO를 생성할 경우, 테이블명 반환
	static String[] getTables() {
		String tables = getProperty('db.tables')
		if(!tables || tables.trim() == '') return null

		def temp = tables.split(',')

		return temp.collect { item -> item = item.toString().trim() }
	}

	/*** DAO 생성에 대한 정보 ***/
	static String getTemplatePath() {
		return getProperty('template.path')
	}

	static String getDAOTemplate() {
		return getProperty('dao.template')
	}

	static String getDAOPackage() {
		return getProperty('dao.package')
	}
}
