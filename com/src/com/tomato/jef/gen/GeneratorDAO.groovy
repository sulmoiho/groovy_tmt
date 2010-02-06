package com.tomato.jef.gen;


import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import groovy.sql.Sql;

import java.text.SimpleDateFormat;

import com.tomato.jef.dao.dialect.*;

class GeneratorDAO {
	private Dialect dialect;	//사용하는 DB 스키마정보에 대한 DB-Dialect 객체
	private PrintWriter logWriter;	//생성시 출력을 위한 PrintWriter

	//생성자
	GeneratorDAO(String dbms){
		dbms = dbms ? dbms.toUpperCase() : GenBundle.getDBTypeName().toUpperCase()

		if(dbms == 'SYB' || dbms == 'SYBASE'){
			this.dialect = new SybaseDialect()
		}else if(dbms == 'MS' || dbms == 'MSSQL'){
			this.dialect = new MSSQLDialect()
		}else{
			this.dialect = new OracleDialect()
		}

		//log Directory
		try {
			def logDir = System.properties.'java.io.tmpdir'
			def logFileNm = 'DAOgen.log'
			
			logWriter = new PrintWriter(new FileWriter("$logDir${File.separator}$logFileNm", true), true)
			log("#Log File : $logDir${File.separator}$logFileNm")
		} catch (Exception e){
			e.printStackTrace()
		} finally {
			logWriter.close()
		}
	}
	
	/**
	 * General DAO 클래스를 생성한다.
	 */
	void generate(){
		def jdbcDriver 	= GenBundle.getJdbcDriver()
		def jdbcUrl 	= GenBundle.getJdbcUrl()
		def jdbsUser 	= GenBundle.getDbUser()
		def jdbcPassWd 	= GenBundle.getDbPassword()
		
		def sdf = new SimpleDateFormat('yyyy-MM-dd HH:mm:ss')
		log("#############################################")
		log("#                 FROM DB                   #")
		log("#############################################")
		log("# gen start date : ${sdf.format(new Date(System.currentTimeMillis()))}")
		log("# jdbc url : $jdbcUrl")
		log("# jdbc driver : $jdbcDriver")

		//DB Connection 객체 생성
		def db = Sql.newInstance(jdbcUrl, jdbsUser, jdbcPassWd, jdbcDriver)

		//특정테이블명이 주어진 경우, 해당 테이블에 대해서만 DAO를 생성한다.
		def tables = GenBundle.getTables()
		if(tables){
			tables = tables.toList()
		}

		def metaTables = [:]		//테이블의 메타정보를 담고있는 객체에 대한 맵

		def tableName, columnName, columnType, isNull, defaultVal
		def tableInfo = null

		//테이블에 대한 메타정보 조회
		def stmt = dialect.getTableSchemaQuery(tables)
		db.eachRow(stmt){
			table -> tableName	= table.table_name		//테이블명
					 columnName = table.column_name		//컬럼명
					 columnType	= table.column_type		//컬럼타입
					 isNull 	= table.is_null			//널값허용여부
					 defaultVal	= table.default_value	//컬럼디폴트값

			//테이블에 대한 메타정보를 저장
			if(metaTables.containsKey(tableName)){
				 tableInfo = metaTables[tableName]
			}else{
				 //Log
				 if(tableInfo) log("# Table Name : ${tableInfo.tableName}, Column Count : ${tableInfo.getColumnCount()}")
				
				 tableInfo = new TableMetadata(tableName)
				 metaTables.put(tableName, tableInfo)
			}
			
			tableInfo.addColumn(new ColumnMetadata(['columnName': columnName, 'columnType': columnType, 'nullable': (isNull == 'Y'), 'defaultValue': defaultVal]))
		}
		if(tableInfo) log("# Table Name : ${tableInfo.tableName}, Column Count : ${tableInfo.getColumnCount()}")
		
		if(metaTables.size() == 0) {
			log('처리할 테이블이 없습니다.')
			System.exit(0)
		}

		//DAO 파일을 저장할 디렉토리 체크.
		def rootDir = GenBundle.getBasePath();
		File root = new File(rootDir)
		if(root.exists() == false){
			if(!root.mkdirs()){
				throw new RuntimeException("Dir create Fail : $rootDir")
			}
		}else{
			if(root.isFile()) throw new RuntimeException("DAO Gen Path is file : $rootDir")
		}
		
		//DAO 빌더 객체를 통해, DAO 소스 파일 생성
		def daoWriter = null
		tableInfo = null
		def toFile = null
		
		def builder = new DAOBuilder()
		metaTables.each { 
			entry -> tableInfo = entry.value
					
			toFile = new File(root, "${tableInfo.getCamelNotationTableName()}DAO.java")
			if(toFile.exists()) toFile.delete()

			//템플릿으로부터 생성한 소스를 자바파일로 저장
			daoWriter = new FileWriter(toFile, false)
			daoWriter.write(builder.build(entry.value))
			daoWriter.flush()
			
			daoWriter.close()
		}

		log("#############################################")
		log("# gen end date : ${sdf.format(new Date(System.currentTimeMillis()))}")
		log("#############################################")
	}
	

	//로그 출력
	private void log(String msg){
		println msg;
		
		if(logWriter) logWriter.println (msg)
	}
	
	//출력 스트림 닫기
	void end(){
		if(logWriter) logWriter.close()
	}
	
	
	
	public static void main(String[] args) {
		def generator = new GeneratorDAO()
		generator.generate()
		generator.end()
	}
}
