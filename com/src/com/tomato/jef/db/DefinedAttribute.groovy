package com.tomato.jef.db;

import java.util.MissingResourceException;

/**
 * 관리필드와 같은 정의된 속성들을 나타내는 클래스이다.
 */
class DefinedAttribute {
	//Type 객체(불변객체)
	public final static DefinedType QUERY_TYPE   = new DefinedType('QUERY'  , 101);
	public final static DefinedType HEADER_TYPE  = new DefinedType('HEADER' , 102);
	public final static DefinedType SESSION_TYPE = new DefinedType('SESSION', 103);
	public final static DefinedType PARAM_TYPE   = new DefinedType('PARAM'  , 104);
	public final static DefinedType ATTR_TYPE    = new DefinedType('ATTR'   , 105);
	public final static DefinedType COOKIE_TYPE  = new DefinedType('COOKIE' , 106);
	public final static DefinedType CONST_TYPE   = new DefinedType('CONST'  , 107);

	//ALL CRUD Type Add 2009-05-04
	public final static String CRUD_TYPE_ALL    = "ALL"; 
	public final static String CRUD_TYPE_UPDATE = "U";
	public final static String CRUD_TYPE_INSERT = "I";
	
	private String key;
	private String value;
	private DefinedType type;
	private String crudType; 		//CRUD Type Add 2009-05-04
	private boolean force = false; 	//Assign Force Add 2009-05-05
	private Object currentValue; 	//Current Value 2009-05-05
	
	//생성자
//	DefinedAttribute() {
//	}
	
	String getKey() {
		return key
	}
	
	void setKey(String key) {
		this.key = key
	}
	
	String getValue() {
		return value
	}
	
	void setValue(String value) {
		this.value = value
	}
	
	Object getCurrentValue() {
		return this.currentValue
	}
	
	void setCurrentValue(Object currentValue) {
		this.currentValue = currentValue
	}
	
	void setForce(String force) {
		this.force = "Y".equalsIgnoreCase(force)
	}
	
	void setForce(boolean force) {
		this.force = force
	}
	
	DefinedType getType() {
		return type
	}
	
	void setType(String type){
		if(QUERY_TYPE.typeName.equalsIgnoreCase(type)) {
			this.type = QUERY_TYPE
		} else if(HEADER_TYPE.typeName.equalsIgnoreCase(type)) {
			this.type = HEADER_TYPE
		} else if(SESSION_TYPE.typeName.equalsIgnoreCase(type)) {
			this.type = SESSION_TYPE
		} else if(PARAM_TYPE.typeName.equalsIgnoreCase(type)) {
			this.type = PARAM_TYPE
		} else if(ATTR_TYPE.typeName.equalsIgnoreCase(type)) {
			this.type = ATTR_TYPE
		} else if(COOKIE_TYPE.typeName.equalsIgnoreCase(type)) {
			this.type = COOKIE_TYPE
		} else if(CONST_TYPE.typeName.equalsIgnoreCase(type)) {
			this.type = CONST_TYPE
		} else {
			throw new MissingResourceException("Missing Attribute Type : $type", this.class.name, type)
		}
	}
	
	void setType(int type) {
		if(QUERY_TYPE.typeCd == type) {
			this.type = QUERY_TYPE
		} else if(HEADER_TYPE.typeCd == type) {
			this.type = HEADER_TYPE
		} else if(SESSION_TYPE.typeCd == type) {
			this.type = SESSION_TYPE
		} else if(PARAM_TYPE.typeCd == type) {
			this.type = PARAM_TYPE
		} else if(ATTR_TYPE.typeCd == type) {
			this.type = ATTR_TYPE
		} else if(COOKIE_TYPE.typeCd == type) {
			this.type = COOKIE_TYPE
		} else if(CONST_TYPE.typeCd == type) {
			this.type = CONST_TYPE
		} else {
			throw new MissingResourceException("Missing Attribute Type : $type" , this.class.name, String.valueOf(type))
		}
	}
	
	//CRUD Type Add 2009-05-04
	String getCrudType() { 
		return this.crudType
	}
	
	//CRUD Type Add 2009-05-04
	void setCrudType(String crudType) {
		if(crudType == null) {
			this.crudType = DefinedAttribute.CRUD_TYPE_ALL
		} else {
			crudType = crudType.trim().toUpperCase()
			
			if(DefinedAttribute.CRUD_TYPE_ALL == crudType) this.crudType = DefinedAttribute.CRUD_TYPE_ALL
			else if(DefinedAttribute.CRUD_TYPE_INSERT == crudType) this.crudType = DefinedAttribute.CRUD_TYPE_INSERT
			else if(DefinedAttribute.CRUD_TYPE_UPDATE == crudType) this.crudType = DefinedAttribute.CRUD_TYPE_UPDATE
			else throw new RuntimeException("Unsupported CRUD Type : ${crudType} \nin common.properties")
		}
	}
	
	boolean isForce() {
		return this.force
	}

	int hashCode() {
		final int prime = 31
		int result = 1
		result = prime * result + (crudType ? crudType.hashCode() : 0)
		result = prime * result + (currentValue ? currentValue.hashCode() : 0)
		result = prime * result + (force ? 1231 : 1237)
		result = prime * result + (key ? key.hashCode() : 0)
		result = prime * result + (type ? type.hashCode() : 0)
		result = prime * result + (value ? value.hashCode() : 0)
		
		return result
	}

	boolean equals(Object obj) {
		if(this.is(obj)) return true
		if(obj == null) return false
		if(getClass() != obj.getClass()) return false
		DefinedAttribute other = (DefinedAttribute) obj
		if(crudType == null) {
			if(other.crudType != null) return false
		} else if(crudType != other.crudType) return false
		if(currentValue == null) {
			if(other.currentValue != null) return false
		} else if(currentValue != other.currentValue) return false
		if(force != other.force) return false
		if(key == null) {
			if(other.key != null) return false
		} else if(key != other.key) return false
		if(type == null) {
			if(other.type != null) return false
		} else if(type != other.type) return false
		if(value == null) {
			if(other.value != null) return false
		} else if(value != other.value) return false
	
		return true
	}
	
	String toString() {
		return "{\"${this.key}\", \"${this.value}\", \"${this.type.typeName}\", \"${this.crudType}\", \"${this.currentValue}\"}"
	}
}
