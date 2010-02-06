package com.tomato.jef.gen;

final class ColumnMetadata {
	private String columnName = null
	private String columnType = null
	private boolean nullable = false
	private String defaultValue = null
	
	//컬럼명
	String getColumnName() {
		return columnName
	}

	//컬럼타입
	String getColumnType() {
		return columnType
	}
	
	//NULL 허용여부
	boolean isNullable() {
		return nullable
	}
	
	//디폴트 컬럼값
	String getDefaultValue() {
		return defaultValue ? defaultValue.replaceAll("'", '') : ''
	}
	
	//해쉬코드
	int hashCode() {
		final def prime = 31
		def result = 1
		result = prime * result + (columnName ? columnName.hashCode() : 0)
		result = prime * result + (columnType ? columnType.hashCode() : 0)
		result = prime * result + (defaultValue ? defaultValue.hashCode() : 0)
		result = prime * result + (nullable ? 1231 : 1237)
		
		return result
	}
	
	boolean equals(def obj) {
		if(this.is(obj)) return true
		if(!obj) return false
		if(getClass() != obj.getClass()) return false
		final ColumnMetadata other = (ColumnMetadata) obj
		if(!columnName) {
			if(other.columnName) return false
		} else if(columnName != other.columnName) return false
		if(!columnType) {
			if(other.columnType) return false
		} else if(columnType != other.columnType) return false
		if(nullable != other.nullable) return false
		if(!defaultValue) {
			if(other.defaultValue) return false
		} else if(defaultValue != other.defaultValue) return false
		
		return true
	}
}
