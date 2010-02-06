package com.tomato.jef.gen;

class TableMetadata {
	private String tableName;
	private List columns
	
	//생성자
	TableMetadata(String tableName){
		this.tableName = tableName
		this.columns = []
	}
	
	void setTableName(String tableName) {
		this.tableName = tableName
	}
	
	String getTableName() {
		return this.tableName
	}
	
	/**
	 * 해당 테이블에 대한 DAO 클래스를 위한 클래스명을 반환한다.
	 * 
	 * @return notation 자바 클래스 소스 파일명
	 */
	String getCamelNotationTableName() {
		if(!this.tableName) return ''
		
		if(tableName.indexOf('_') == -1) {
			def chArray = this.tableName.toCharArray()
			def chrSize = chArray.size()
			
			chArray[0] = Character.toUpperCase(chArray[0])
			for(i in 1..<chrSize){
				chArray[i] = Character.toLowerCase(chArray[i])
			}
			return chArray.toString()
		}
		
		String[] chars = tableName.split('[_]')
		String tmp
		def notation = new StringBuffer()
		for(i in 0..< chars.length) {
			if(chars[i].length() == 0) continue
			tmp = chars[i].toLowerCase()
			notation << tmp.substring(0, 1).toUpperCase()
			notation << tmp.substring(1)
		}
		
		return notation.toString()
	}
	
	/**
	 * 컬럼정보 추가
	 * 
	 * @param column 컬럼 정보를 담고있는 ColumnMetadata 객체
	 */
	void addColumn(ColumnMetadata column) {
		this.columns.add(column)
	}
	
	/**
	 * 컬럼정보 반환
	 * 
	 * @param i 인덱스
	 * @return ColumnMetadata 해당 테이블 내의 인덱스 번째 컬럼정보를 담고있는 객체를 반환한다.
	 */
	ColumnMetadata getColumn(int i) {
		return (ColumnMetadata) this.columns.get(i)
	}
	
	/**
	 * 해당 테이블의 모든 컬럼정보 삭제
	 */
	void removeAll() {
		this.columns.clear()
	}
	
	/**
	 * 해당 테이블의 컬럼 사이즈를 반환
	 */
	int getColumnCount() {
		return this.columns.size()
	}
	
	public static void main(String[] args) {
		def tab = new TableMetadata()
		tab.tableName = 'ZTEMP_WS'
		
		println tab.getCamelNotationTableName()
	}
}
