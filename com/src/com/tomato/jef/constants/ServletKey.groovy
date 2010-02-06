package com.tomato.jef.constants;

final class ServletKey implements java.io.Serializable {
	//xtm xml document key
	static final String KEY_RETUEN = '_reDiv'
	
	//xtm return manager key 
	static final String KEY_RETUEN_YN = '_reDivYN'
	
	//command 
	static final String KEY_COMMAND  = 'strCommand'
	
	
	//charter set
	static final String EN_CHARTER_SET = 'UTF-8'
	
	/**
	 * 생성자 : static fields만을 담고 있기 때문에 객체 생성을 금지함.
	 */
	private ServletKey() {}
}
