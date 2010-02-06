package com.tomato.jef.db;

class BaseProperties {
	private map = null;
	/**
	 * 생성자
	 */
	BaseProperties() {
		map = [:]
	}

	/**
	 * key에 해당하는 속성(파라메터)을 반환한다.
	 * @param key java.lang.String 키 스트링
	 * @return java.lang.String 속성값 문자열
	 */
	String getProperty(String key) {
		if(key == null) return null
		return (String)map.get(key)
	}
	
	/**
	 * key에 해당하는 속성(파라메터)을 반환한다.
	 * @param key java.lang.String 키 스트링
	 * @return java.lang.Object 속성값
	 */
	Object getObject(String key) {
		if(key == null) return null
		return map.get(key)
	}
	
	/**
	 * key에 해당하는 속성값을 설정하여 맵에 저장한다.
	 * 
	 * @param key java.lang.String 키 스트링
	 * @param key java.lang.Object 속성값
	 * @return java.lang.boolean 
	 */
	boolean put(String key, Object value) {
		if(value == null) value = ''
		
		map.put(key, value)
		return true
	}
	
	/**
	 * added by sulmoiho
	 * 
	 * 맵에 저장된 key 리스트를 반환한다.
	 * @return java.util.List 키목록
	 */
	List getPropertiesNames() {
		return map.keySet().toList()
	}
	
	/**
	 * added by sulmoiho
	 * 
	 * 맵에 저장된 value 리스트를 반환한다.
	 * @return java.util.List 값목록
	 */
	List getPropertiesValues() {
		return map.values().toList()
	}
}