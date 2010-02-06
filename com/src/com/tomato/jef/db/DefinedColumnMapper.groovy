package com.tomato.jef.db;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import com.tomato.jef.log.Log;
import com.tomato.jef.jndi.JNDIHelper;
import com.tomato.jef.util.CookieUtil;

/**
 * 데이터베이스 테이블의 관리 컬럼와 같이 Auto-Save 기능을 위한 자동으로 저장될 필드들을 매핑하여
 * 관리하기 위한 클래스이다. 
 * <P>
 * Auto-Save 컬럼의 정의는 설정파일(common.properties)에 정의된다.
 * 관리필드의 예로는 CRT_DTHR, CRT_USER_ID, CRT_IP_ADDR과 같은 것들이 있다.
 * </P>
 */
class DefinedColumnMapper {
	protected final static String PROPERTY_NAME = "auto_crud_columns";
	/*
	 * ThreadLocal 은 현재 수행되고 있는 각각의 Thread 상에서 고유한 저장소를 제공해 주는 클래스로
	 * 싱글턴 구현이나 프레임웍에서 환경변수 공유등등에 유용하게 사용될 수 있다.
	 */
	protected final static ThreadLocal mapper = new ThreadLocal() {
		protected Object initialValue() {
			return new DefinedColumnMapper()
		}
	};

	protected Map webColumn;
	protected Map daoColumn;
	
	static void initAutoColumn(HttpServletRequest request) {
		DefinedColumnMapper mapper = DefinedColumnMapper.getInstance()
		DefinedAttribute attribute = null

		mapper.webColumn.each { key, value ->
			attribute = (DefinedAttribute)mapper.webColumn[key]
			if(attribute == null) return
			
			int attType = attribute.type.typeCd
			if(attType == DefinedAttribute.ATTR_TYPE.typeCd){
				attribute.currentValue = request.getAttribute(attribute.value)
			}else if(attType == DefinedAttribute.SESSION_TYPE.typeCd){
				HttpSession session = request.getSession(false)
				if(session == null) {
					Log.warning("Session is null Auto Column Process Column Name : ${colName} Match Session Value : ${attribute.getValue()}")
					return
				}
				attribute.currentValue = session.getAttribute(attribute.value)
			}else if(attType == DefinedAttribute.PARAM_TYPE.typeCd){
				attribute.currentValue = request.getParameter(attribute.value)
			}else if(attType == DefinedAttribute.COOKIE_TYPE.typeCd){
				attribute.currentValue = CookieUtil.getCookie(request, null, attribute.value)
			}else if(attType == DefinedAttribute.HEADER_TYPE.typeCd){
				attribute.currentValue = request.getHeader(attribute.value)
			}else{
				Log.warning("Auto Column Processing Type : ${attribute.getType()} is Not Support")
				return
			}
		}
	}
	
	/**
	 * 생성자
	 */
	private DefinedColumnMapper() {
		this.webColumn = [:]
		this.daoColumn = [:]
		parse(JNDIHelper.getString(PROPERTY_NAME))
	}
	
	/**
	 * 싱글톤 객체 반환
	 */
	static DefinedColumnMapper getInstance() {
		return (DefinedColumnMapper)DefinedColumnMapper.mapper.get()
	}
	
	boolean isAutoProcessColumnForDAO(String columnName) {
		return this.daoColumn.containsKey(columnName)
	}
	
	//CRUD Type Add 2009-05-04
	boolean isAutoProcessColumnForDAO(String columnName, String crudType) {
		DefinedAttribute attribute = (DefinedAttribute)this.daoColumn[columnName]
		if(attribute == null) return false
		else {
			String CRUD_TYPE = attribute.crudType
			if(CRUD_TYPE == null || crudType == null) return true
			else if(DefinedAttribute.CRUD_TYPE_ALL.equalsIgnoreCase(CRUD_TYPE)) return true
			else if(CRUD_TYPE.equalsIgnoreCase(crudType)) return true
			else return false
		}
	}
	
	DefinedAttribute getAttributeForDAO(String columnName) {
		return (DefinedAttribute)this.daoColumn[columnName]
	}
	
	boolean isAutoProcessColumnForProcessor(String columnName) {
		return this.webColumn.containsKey(columnName)
	}
	
	//CRUD Type Add 2009-05-04
	boolean isAutoProcessColumnForProcessor(String columnName, String crudType) {
		DefinedAttribute attribute = (DefinedAttribute)this.webColumn[columnName]
		if(attribute == null) return false
		else {
			String CRUD_TYPE = attribute.crudType
			if(CRUD_TYPE == null || crudType == null) return true
			else if(DefinedAttribute.CRUD_TYPE_ALL.equalsIgnoreCase(CRUD_TYPE)) return true
			else if(CRUD_TYPE.equalsIgnoreCase(crudType)) return true
			else return false
		}
	}
	
	DefinedAttribute getAttributeForProcessor(String columnName) {
		return (DefinedAttribute)this.webColumn[columnName]
	}
	
	protected void parse(String syntax) {
		DefinedAttribute attribute = null
		
		def att = new StringBuffer()
		String data = null
		final int strSize = syntax.size()
		syntax.eachWithIndex { atChar, i -> 
			switch(atChar){
				case '{' :
					attribute = null
					att.delete(0, att.size())
					break;
				case '}' :
					data = att.toString()
					if(data.startsWith('\"'))
						data = data[1..<data.size()]
					if(data.endsWith('\"'))
						data = data[0..<data.size()-1]
					
					def tuple = data.split('[\"][\\s]*[,][\\s]*[\"]')
					attribute = new DefinedAttribute()
					attribute.key = tuple[0].toUpperCase()
					attribute.value = tuple[1]
					attribute.setType(tuple[2])
					if(tuple.size() > 3) attribute.crudType = tuple[3]
					if(tuple.size() > 4) attribute.setForce(tuple[4])

					int typeCd = attribute.type.typeCd
					if(DefinedAttribute.QUERY_TYPE.typeCd == typeCd 
					|| DefinedAttribute.CONST_TYPE.typeCd == typeCd) {
						this.daoColumn.put(attribute.key, attribute)
					} else if(DefinedAttribute.COOKIE_TYPE.typeCd == typeCd
					|| DefinedAttribute.HEADER_TYPE.typeCd == typeCd
					|| DefinedAttribute.PARAM_TYPE.typeCd == typeCd
					|| DefinedAttribute.ATTR_TYPE.typeCd == typeCd
					|| DefinedAttribute.SESSION_TYPE.typeCd == typeCd) {
						this.webColumn.put(attribute.key, attribute);
					}
					
					break;
				default :
					att << atChar
			}
		}
	}
	
	public static void main(String[] args) {
		def mapper = DefinedColumnMapper.getInstance()

		mapper.parse(JNDIHelper.getString('auto_crud_columns'))
		
		mapper.daoColumn.each { key, value ->
			println key + ' : ' + value
		}
	}
}
