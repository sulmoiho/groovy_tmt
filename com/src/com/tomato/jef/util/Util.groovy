package com.tomato.jef.util;

import java.util.prefs.Base64;

final class Util {
	/**
	 * <pre>
	 * Constructor
	 * 객체의 인스턴스가 허용되지 않음
	 * </pre>
	 */
	private Util(){}
	
	/**
	 * <pre>
	 * 아큐먼트 값이 null인지 체크한다.
	 * 만약 null인 경우 빈문자열('')을반환하고, null이 아닌경우 해당 객체의 toString()값을 반환한다.
	 * </pre>
	 * @param field java.Object 객체
	 * @return java.lang.String 변경된 문자열
	 */
	static String fixNull(def field){
		return field != null ? field.toString() : ''
	}
	
	/**
	 * <pre>
	 * Query문 용 : 문자열의 앞뒤에 ' 를 붙이는 메소드
	 * 아규먼트 문자열이 null인 경우 ""로 변경함.
	 * </pre>
	 * @param field java.lang.String 문자열
	 * @return java.lang.String 변경된 문자열
	 */
	static String addQuote(String field){
		return "'${translateSql(fixNull(field))}'"
	}
	
	/**
	 * <pre>
	 * Query문 용 : 문자열의 앞뒤에 '%%' 를 붙이는 메소드
	 * 아규먼트 문자열이 null인 경우 ""로 변경함.
	 * </pre>
	 * @param field java.lang.String 문자열
	 * @return java.lang.String 변경된 문자열
	 */
	static String addQLike(String field){
		return "'%${translateSql(fixNull(field))}%'"
	}
	
	/**
	 * <pre>
	 * Statement문의 insert, update 쿼리문자열에서 '가 들어갈 경우 에러가 발생하므로  Single-Quotation(')를 Double-Quotation(")로 치환하는 메소드
	 * </pre>
	 * @param strSource
	 * @see java.lang.String 쿼리 문자열
	 * @return java.lang.String 변환된 쿼리 문자열
	 */
	private static String translateSql(String strSource){
		def chArray = strSource.toCharArray()
		def intSize = chArray.size()
		
		for(i in 0..<intSize){
			if(chArray[i] == '\'') chArray[i] = '\"'
		}
		
		return chArray.toString()
	}
	
	public static void main(String[] args){
		println Util.fixNull(null)
	}
}