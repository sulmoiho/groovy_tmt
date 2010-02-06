package com.tomato.jef.db;

/**
 * 관리필드들에 대한 속성 타입을 나타내는 클래스이다.
 * <P>
 * 원래는 DefinedAttribute 클래스 내에 정적중복클래스로 정의되었으나
 * 중복클래스를 정의할 수 없는 그루비의 특성상 별도의 클래스로 분리되었다.
 * </P>
 */
protected final class DefinedType {
	private final String typeName;
	private final int typeCd;
	
	/**
	 * 생성자
	 */
	private DefinedType(String typeName, int typeCd) {
		this.typeName = typeName
		this.typeCd = typeCd
	}
	
	String getTypeName() {
		return typeName
	}
	
	int getTypeCd() {
		return typeCd
	}
	
	int hashCode() {
		final int prime = 31
		int result = 1
		result = prime * result + typeCd
		result = prime * result + (typeName ? typeName.hashCode() : 0)
		
		return result
	}
	
	boolean equals(Object obj) {
		if(this.is(obj)) return true
		if(obj == null) return false
		if(this.getClass() != obj.getClass()) return false
		final DefinedType other = (DefinedType) obj
		if(this.typeCd != other.typeCd) return false
		if(this.typeName == null) {
			if(other.typeName != null) return false
		} else if(this.typeName != other.typeName) return false
	
		return true
	}
	
	String toString() {
		return "TYPE=${this.typeName} [${this.typeCd}]"
	}
}
